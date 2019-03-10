package com.swp.culturehomestay.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.swp.culturehomestay.R;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String TAG = "Login";

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.btn_facebook_login);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("checkFragment",3);
                startActivity(intent);
                Log.d(TAG, "fb Login success.");
            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "fb Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.e(TAG, "fb Login attempt failed." + e);
                Toast.makeText(LoginActivity.this,R.string.str_please_check_your_internet_connection,Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}