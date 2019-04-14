package com.swp.culturehomestay.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.AuthenticatioModel;
import com.swp.culturehomestay.models.LoginCredentials;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String TAG = "Login";
    private Button loginBtn;
    private EditText username;
    private EditText password;
    private CallbackManager callbackManager;
    AuthenticatioModel authenticatioModel;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        loginBtn = (Button) findViewById(R.id.btn_login);
        username = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        loginBtn.setOnClickListener(doLogin);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//        callbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = (LoginButton) findViewById(R.id.btn_facebook_login);
//        loginButton.setReadPermissions("user_friends");
//        loginButton.setReadPermissions("public_profile");
//        loginButton.setReadPermissions("email");
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("checkFragment",3);
//                startActivity(intent);
//                Log.d(TAG, "fb Login success.");
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//                Log.d(TAG, "fb Login attempt canceled.");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                Log.e(TAG, "fb Login attempt failed." + e);
//                Toast.makeText(LoginActivity.this,R.string.str_please_check_your_internet_connection,Toast.LENGTH_LONG).show();
//            }
//        });
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    View.OnClickListener doLogin = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        LoginCredentials loginCredentials = new LoginCredentials(username.getText().toString(),password.getText().toString(),"pass");
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<AuthenticatioModel> call = iApi.doLogin(loginCredentials);
        call.enqueue(new Callback<AuthenticatioModel>() {
            @Override
            public void onResponse(Call<AuthenticatioModel> call, Response<AuthenticatioModel> response) {
                try{
                    if (response.isSuccessful() && response.body() != null) {
                        authenticatioModel =response.body();
                        saveData(authenticatioModel);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("checkFragment",3);
                        startActivity(intent);
                        loadData();
                    }else {
                        String errorCode;
                        switch (response.code()) {
                            case 404:
                                errorCode = "404 not found";
                                break;
                            case 500:
                                errorCode = "500 server broken";
                                break;
                            default:
                                errorCode = "unknown error";
                                break;
                        }
                        Log.d(TAG, errorCode);
                    }
                }catch (Exception e){
                    Log.d(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AuthenticatioModel> call, Throwable t) {

            }
        });
    }
};
    private void saveData(AuthenticatioModel authenticatioModel){
        SharedPreferences.Editor editor =  sharedpreferences.edit();
        editor.putString("userId", authenticatioModel.getUserId());
        editor.commit();
    }
    private void loadData(){
        Log.d(TAG, "loadData: " + sharedpreferences.getString("userId", ""));
    }

}