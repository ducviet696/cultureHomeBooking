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
//        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        genderSpinner.setAdapter(arrayAdapter);
//        userDetailModel = new UserDetailModel("anhndv","Viet Anh","Nguyen Dung","anhndvse04243@gmail.com", new Date(),true,"","+84333834191","","Hanoi, Vietnam" );
        fillDataCustomer(userDetailModel);
        btnCusBack = (Button) findViewById(R.id.cusBack);
        btnCusBack.setOnClickListener(onBackClick);
        loadData();
    }

    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    public void fillDataCustomer(UserDetailModel userDetailModel) {
//        if(userDetailModel.isGender()){
//            genderSpinner.setSelection(0);
//        }else{
//            genderSpinner.setSelection(1);
//        }
//        fullName.setText(userDetailModel.getFirstName());
//        email.setText(userDetailModel.getEmail());
//        phoneNumber.setText(userDetailModel.getPhoneNumber());
//        address.setText(userDetailModel.getAddress());
//        description.setText(userDetailModel.getInfoDescription());

    }

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
