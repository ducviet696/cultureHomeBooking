package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.AuthenticationRole;
import com.swp.culturehomestay.models.AuthorizationMethodBean;
import com.swp.culturehomestay.models.SignUpCredentials;
import com.swp.culturehomestay.models.SignUpResModel;
import com.swp.culturehomestay.models.UserWant;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG="SIGNUP";
    private Button backBtn;
    private Button createBtn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SignUpResModel signUpResModel;
    Spinner genderSpinner;
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText password;
    TextInputEditText repassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        backBtn = (Button) findViewById(R.id.signUpBack);
        backBtn.setOnClickListener(onBackClick);
        email = (TextInputEditText) findViewById(R.id.email_create);
        password = (TextInputEditText) findViewById(R.id.password_create);
        repassword = (TextInputEditText) findViewById(R.id.repassword_create);
        createBtn = (Button) findViewById(R.id.createAccBtn);
        createBtn.setOnClickListener(onCreateClick);
    }

    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    View.OnClickListener onCreateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validateFormSignUp()){
                AuthenticationRole authenticationRole = new AuthenticationRole("2");
                List<AuthenticationRole> list = new ArrayList<>();
                list.add(authenticationRole);
                AuthorizationMethodBean authorizationMethodBean = new AuthorizationMethodBean("pass",email.getText().toString(),password.getText().toString(),"TENANT", new Date().getTime(),new Date().getTime(),list);
                UserWant userWant = new UserWant(authorizationMethodBean);
                SignUpCredentials signUpCredentials = new SignUpCredentials(userWant);
                IApi iApi = ApiClient.getApiClient().create(IApi.class);
                Call<SignUpResModel> call = iApi.createAccount(signUpCredentials);
                call.enqueue(new Callback<SignUpResModel>() {
                    @Override
                    public void onResponse(Call<SignUpResModel> call, Response<SignUpResModel> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                signUpResModel = response.body();
                                if(signUpResModel.getCode().equals("00")){
                                    Toast toast = Toast.makeText(getApplicationContext(),"Create Account Successfully.",Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    intent.putExtra("emailCreate",email.getText().toString());
                                    startActivity(intent);
                                }else{
                                    Toast toast = Toast.makeText(getApplicationContext(),signUpResModel.getMessage(),Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }else{
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

                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpResModel> call, Throwable t) {

                    }
                });
            }
        }
    };

    private boolean validateFormSignUp(){
        if(email.getText().toString().trim().matches(emailPattern) && email.getText().toString().trim().length() > 0){

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Invalid email format.",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if(!repassword.getText().toString().equals(password.getText().toString())){
            Toast toast = Toast.makeText(getApplicationContext(),"Passwords do not match.",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }
}
