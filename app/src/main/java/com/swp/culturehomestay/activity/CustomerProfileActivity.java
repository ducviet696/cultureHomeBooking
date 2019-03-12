package com.swp.culturehomestay.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.UserDetail;

import java.util.Date;

public class CustomerProfileActivity extends AppCompatActivity {

    Spinner genderSpinner;
    UserDetail userDetail;
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText address;
    TextInputEditText description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        genderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        firstName = (TextInputEditText) findViewById(R.id.firstName);
        lastName = (TextInputEditText) findViewById(R.id.lastName);
        email = (TextInputEditText) findViewById(R.id.email);
        phoneNumber = (TextInputEditText) findViewById(R.id.phoneNumber);
        address = (TextInputEditText) findViewById(R.id.address);
        description = (TextInputEditText) findViewById(R.id.description);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrayAdapter);
        userDetail = new UserDetail("anhndv","Viet Anh","Nguyen Dung","anhndvse04243@gmail.com", new Date(),true,"","+84333834191","","Hanoi, Vietnam" );
        fillDataCustomer(userDetail);
    }

    public void fillDataCustomer(UserDetail userDetail){
        if(userDetail.isGender()){
            genderSpinner.setSelection(0);
        }else{
            genderSpinner.setSelection(1);
        }
        firstName.setText(userDetail.getFirstName());
        lastName.setText(userDetail.getLastName());
        email.setText(userDetail.getEmail());
        phoneNumber.setText(userDetail.getPhoneNumber());
        address.setText(userDetail.getAddress());
        description.setText(userDetail.getInfoDescription());

    }
}
