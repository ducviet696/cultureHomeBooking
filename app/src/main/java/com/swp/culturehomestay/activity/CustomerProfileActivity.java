package com.swp.culturehomestay.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.UserDetailModel;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CustomerProfileActivity extends AppCompatActivity {

    Spinner genderSpinner;
    UserDetailModel userDetailModel;
    TextInputEditText fullName;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText address;
    TextInputEditText description;
    OkHttpClient client;
    String result;
    Button btnCusBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        fullName = (TextInputEditText) findViewById(R.id.fullName);
        email = (TextInputEditText) findViewById(R.id.email);
        phoneNumber = (TextInputEditText) findViewById(R.id.phoneNumber);
        address = (TextInputEditText) findViewById(R.id.address);
        description = (TextInputEditText) findViewById(R.id.description);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        userDetailModel = new UserDetailModel("anhndv","Viet Anh","Nguyen Dung","anhndvse04243@gmail.com", new Date(),true,"","+84333834191","","Hanoi, Vietnam" );
        fillDataCustomer(userDetailModel);
        btnCusBack = (Button) findViewById(R.id.cusBack);
        btnCusBack.setOnClickListener(onBackClick);
    }

    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    public void fillDataCustomer(UserDetailModel userDetailModel){
        if(userDetailModel.isGender()){
            genderSpinner.setSelection(0);
        }else{
            genderSpinner.setSelection(1);
        }
        fullName.setText(userDetailModel.getFirstName());
        email.setText(userDetailModel.getEmail());
        phoneNumber.setText(userDetailModel.getPhoneNumber());
        address.setText(userDetailModel.getAddress());
        description.setText(userDetailModel.getInfoDescription());

    }
}
