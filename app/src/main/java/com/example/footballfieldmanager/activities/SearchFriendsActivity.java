package com.example.footballfieldmanager.activities;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.user.LDMUserManager;
import com.example.footballfieldmanager.controller.user.UserDelegate;
import com.example.footballfieldmanager.controller.user.UserIF;
import com.example.footballfieldmanager.fragments.UserFragment;
import com.example.footballfieldmanager.model.FootballFieldRent;
import com.example.footballfieldmanager.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.footballfieldmanager.R;

import java.util.List;

public class SearchFriendsActivity extends AppCompatActivity implements UserFragment.OnListFragmentInteractionListener, UserDelegate {

    private EditText usernameEditText;
    private UserIF userIF;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        setSupportActionBar(toolbar);
        userFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.user_fragment);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userIF = new LDMUserManager();
                userIF.setDelegate(SearchFriendsActivity.this);
                String username = usernameEditText.getText().toString();
                RequestQueue queue = RequestQueueSingleton.getInstance(SearchFriendsActivity.this).getQueue();
                userIF.searchFriends(username, queue);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(User item) {
        Intent intent = new Intent(this, FriendRentsActivity.class);
        intent.putExtra("user", item);
        startActivity(intent);
    }

    @Override
    public void userCorrectlyDownloaded(List<User> userList) {
        userFragment.setList(userList, this);
    }

    @Override
    public void userFutureRentsDownloaded(List<FootballFieldRent> rents) {
//                NOT USED
    }

    @Override
    public void userOperationFailed(Exception exc) {
        Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
