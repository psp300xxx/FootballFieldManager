package com.example.footballfieldmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.user.LDMUserManager;
import com.example.footballfieldmanager.controller.user.UserDelegate;
import com.example.footballfieldmanager.controller.user.UserIF;
import com.example.footballfieldmanager.fragments.FootballFieldRentFragment;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.User;

import java.util.List;

public class FriendRentsActivity extends AppCompatActivity implements FootballFieldRentFragment.OnListFragmentInteractionListener, UserDelegate {

    private FootballFieldRentFragment footballFieldRentFragment;
    private User user;
    private UserIF userIF;

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
    public void onListFragmentInteraction(FootballFieldRent item) {

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
}
