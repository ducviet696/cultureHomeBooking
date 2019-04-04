package com.swp.culturehomestay.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    TextInputEditText firstName;
    TextInputEditText lastName;
    TextInputEditText email;
    TextInputEditText phoneNumber;
    TextInputEditText address;
    TextInputEditText description;
    OkHttpClient client;
    String result;
    ImageView btnCusBack;
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
        client = new OkHttpClient();
        doGetRequest("http://dummy.restapiexample.com/api/v1/employees");
        userDetailModel = new UserDetailModel("anhndv","Viet Anh","Nguyen Dung","anhndvse04243@gmail.com", new Date(),true,"","+84333834191","","Hanoi, Vietnam" );
        fillDataCustomer(userDetailModel);
        btnCusBack = (ImageView) findViewById(R.id.cusBack);
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
        firstName.setText(userDetailModel.getFirstName());
        lastName.setText(userDetailModel.getLastName());
        email.setText(userDetailModel.getEmail());
        phoneNumber.setText(userDetailModel.getPhoneNumber());
        address.setText(userDetailModel.getAddress());
        description.setText(userDetailModel.getInfoDescription());

    }

    private void doGetRequest (String url){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result = "false";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().toString();
            }
        });
    }
}
