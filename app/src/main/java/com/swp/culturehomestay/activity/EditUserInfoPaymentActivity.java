package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserInfoPaymentActivity extends AppCompatActivity {

    @BindView(R.id.text_input_email)
    TextInputLayout textInputEmail;
    @BindView(R.id.text_input_username)
    TextInputLayout textInputUsername;
    @BindView(R.id.text_input_phone)
    TextInputLayout textInputPhone;
    String fullName,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info_payment);
        ButterKnife.bind(this);
        getData();

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        fullName =  bundle.getString("fullName");
        phone = bundle.getString("phone");
        textInputPhone.getEditText().setText(phone);
        textInputUsername.getEditText().setText(fullName);
    }


    private boolean validateUsername() {
        String usernameInput = textInputUsername.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            textInputUsername.setError("Username too long");
            return false;
        } else {
            textInputUsername.setError(null);
            fullName = textInputUsername.getEditText().getText().toString();
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPhone.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPhone.setError("Field can't be empty");
            return false;
        } else {
            textInputPhone.setError(null);
            phone = textInputPhone.getEditText().getText().toString();
            return true;
        }
    }

    public void confirmInput(View v) {
        if ( !validateUsername() | !validatePassword()) {
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("fullName",fullName);
        bundle.putString("phone",phone);
        intent.putExtra(Constants.BUNDLE,bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
    @OnClick(R.id.tvBack)
     void onBackClick(){
        finish();
    }
}
