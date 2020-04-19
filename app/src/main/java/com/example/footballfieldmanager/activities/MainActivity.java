package com.example.footballfieldmanager.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.footballfieldmanager.R;
import com.example.footballfieldmanager.RequestQueueSingleton;
import com.example.footballfieldmanager.controller.login.LDMLogin;
import com.example.footballfieldmanager.controller.login.LoginDelegate;
import com.example.footballfieldmanager.controller.login.LoginIF;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity implements LoginDelegate {

    private Button loginButton = null;
    private EditText idEditText = null, passwordEditText =null;
    private LoginButton facebookLoginButton = null;
    private LoginIF loginIF = new LDMLogin();
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginIF.setDelegate(this);
        loginButton = (Button)findViewById(R.id.login_button);
        facebookLoginButton = (LoginButton)findViewById(R.id.facebook_login);
        idEditText = (EditText)findViewById(R.id.id_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);
//        TODO: REMOVE THIS LINE
        passwordEditText.setText("3232");

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.setReadPermissions("email");
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SharedPrefencesManager manager = SharedPrefencesManager.getInstance(MainActivity.this.getApplicationContext());
                manager.saveString("user_id", loginResult.getAccessToken().getUserId());
                manager.saveString("token", loginResult.getAccessToken().getToken());
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                MainActivity.this.startActivity(intent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_LONG ).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.loginIF = new LDMLogin();
                MainActivity.this.loginIF.setDelegate(MainActivity.this);
                String id = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                RequestQueue queue = RequestQueueSingleton.getInstance(MainActivity.this).getQueue();
                loginIF.login(id, password, queue);
            }
        });

    }

    @Override
    public void isLoginSuccessfullyCompleted(String token, String userId) {
        SharedPrefencesManager manager = SharedPrefencesManager.getInstance(MainActivity.this.getApplicationContext());
        manager.saveString("user_id", userId);
        manager.saveString("token", token);
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        MainActivity.this.startActivity(intent);
    }

    @Override
    public void hasLoginFailed(Exception exc) {
        if( exc.getLocalizedMessage() == null || exc.getLocalizedMessage().equals("") ){
            Toast.makeText(this, "Error in authentication", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
