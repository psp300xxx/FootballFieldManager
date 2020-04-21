package com.example.footballfieldmanager.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.rent.LDMRent;
import com.example.footballfieldmanager.controller.rent.RentDelegate;
import com.example.footballfieldmanager.controller.rent.RentIF;
import com.example.footballfieldmanager.controller.user.LDMUserManager;
import com.example.footballfieldmanager.controller.user.UserDelegate;
import com.example.footballfieldmanager.controller.user.UserIF;
import com.example.footballfieldmanager.fragments.FootballFieldRentFragment;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.User;

import java.util.List;

public class FriendRentsActivity extends AppCompatActivity implements FootballFieldRentFragment.OnListFragmentInteractionListener, UserDelegate , RentDelegate {

    private FootballFieldRentFragment footballFieldRentFragment;
    private User user;
    private UserIF userIF;
    private RentIF rentIF = new LDMRent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_rents);
        footballFieldRentFragment = (FootballFieldRentFragment)getSupportFragmentManager().findFragmentById(R.id.football_field_rent_fragment);
        user = (User)getIntent().getSerializableExtra("user");
        loadUserFutureRents();
    }

    private void loadUserFutureRents(){
        userIF = new LDMUserManager();
        userIF.setDelegate(this);
        RequestQueue queue = RequestQueueSingleton.getInstance(this).getQueue();
        userIF.searchUserFutureRents(user.getId(), queue);

    }

    @Override
    public void onListFragmentInteraction(final FootballFieldRent item) {
        android.app.AlertDialog.Builder builder=  new android.app.AlertDialog.Builder(FriendRentsActivity.this).setMessage(R.string.question_add_in_rent);
        builder.setTitle(R.string.question_add_in_rent_title);
        builder.setPositiveButton(R.string.yes_source, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Adding this user into the match
                    rentIF.setDelegate(FriendRentsActivity.this);
                    SharedPrefencesManager manager = SharedPrefencesManager.getInstance(FriendRentsActivity.this);
                    String userId = manager.getString("user_id");
                    String token = manager.getString("token");
                    RequestQueue queue = RequestQueueSingleton.getInstance(FriendRentsActivity.this).getQueue();
                    rentIF.addIntoRent(item, userId,token, item.getFieldId(), queue);
            }
        });
        builder.setNegativeButton(R.string.no_source, null);
        builder.show();
    }

    @Override
    public void userCorrectlyDownloaded(List<User> userList) {

    }

    @Override
    public void userFutureRentsDownloaded(List<FootballFieldRent> rents) {
        footballFieldRentFragment.setList(rents, this);
    }

    @Override
    public void userOperationFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void futureRentDownloaded(List<FootballFieldRent> list) {
//        NOT USED
    }

    @Override
    public void downloadFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void fieldbookedCorrectly() {
        Toast.makeText(this, getResources().getText(R.string.game_booked_correctly), Toast.LENGTH_LONG).show();
    }
}
