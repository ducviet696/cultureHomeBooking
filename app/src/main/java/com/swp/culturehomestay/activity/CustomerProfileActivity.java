package com.swp.culturehomestay.activity;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.UserDetailModel;
import com.swp.culturehomestay.utils.Utils;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerProfileActivity extends AppCompatActivity {

    private static String TAG ="CustomerProfile";
    Spinner genderSpinner;
    UserDetailModel userDetailModel;
    TextInputEditText fullName;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText address;
    String result;
    Button btnCusBack;
    Button btnCusSave;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        context = this;
        fullName = (TextInputEditText) findViewById(R.id.fullName);
        email = (TextInputEditText) findViewById(R.id.email);
        phoneNumber = (TextInputEditText) findViewById(R.id.phoneNumber);
        address = (TextInputEditText) findViewById(R.id.address);
        btnCusBack = (Button) findViewById(R.id.cusBack);
        btnCusBack.setOnClickListener(onBackClick);
        btnCusSave = (Button) findViewById(R.id.cusSave);
        btnCusSave.setOnClickListener(onSaveClick);
        loadData();
    }

    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    View.OnClickListener onSaveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userDetailModel.setAddress(address.getText().toString().trim());
            userDetailModel.setFullName(fullName.getText().toString().trim());
            userDetailModel.setEmail(email.getText().toString().trim());
            userDetailModel.setPhone(phoneNumber.getText().toString().trim());
            retrofit2.Call<UserDetailModel> call = Utils.getAPI().updateUserInfo(userDetailModel);
            call.enqueue(new Callback<UserDetailModel>() {
                @Override
                public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast toast = Toast.makeText(getApplicationContext(),"Update user information successfully",Toast.LENGTH_SHORT);
                            toast.show();
                            Log.d(TAG, "Update successfully");
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
                            Toast toast = Toast.makeText(getApplicationContext(),errorCode,Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "onResponse: " + e.getMessage());
                        Toast toast = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<UserDetailModel> call, Throwable t) {

                }
            });
        }
    };

    private void loadData() {
        userDetailModel = new UserDetailModel();
        String userId = Utils.getUserId(context);
        retrofit2.Call<UserDetailModel> call = Utils.getAPI().getUserDetailById(userId);
        call.enqueue(new Callback<UserDetailModel>() {
            @Override
            public void onResponse(retrofit2.Call<UserDetailModel> call, Response<UserDetailModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        userDetailModel = response.body();
                        if (!Utils.isNullOrEmpty(userDetailModel.getImangeUrl())) {
//                        new LoadImage().execute(userDetailModel.getImangeUrl());
                            Log.d(TAG, userDetailModel.getImangeUrl());
                        }
                        if (!Utils.isNullOrEmpty(userDetailModel.getFullName())) {
                            fullName.setText(userDetailModel.getFullName());
                        }
                        if(!Utils.isNullOrEmpty(userDetailModel.getAddress())){
                            address.setText(userDetailModel.getAddress());
                        }
                        if(!Utils.isNullOrEmpty(userDetailModel.getEmail())){
                            email.setText(userDetailModel.getEmail());
                        }
                        if(!Utils.isNullOrEmpty(userDetailModel.getPhone())){
                            phoneNumber.setText(userDetailModel.getPhone());
                        }
                    } else {
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
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {
                Log.d(TAG, "onResponse: " + t.getMessage());
            }
        });
    }
}
