package com.swp.culturehomestay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

public class SettingActivity extends AppCompatActivity {
    ImageView btnSettingBack;
    RelativeLayout logoutBtn;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        logoutBtn = (RelativeLayout) findViewById(R.id.btn_logout);
        btnSettingBack = (ImageView) findViewById(R.id.settingBack);
        btnSettingBack.setOnClickListener(onBackClick);
        logoutBtn.setOnClickListener(onClickLogout);
    }
    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
    View.OnClickListener onClickLogout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.putExtra("checkFragment",3);
            startActivity(intent);
        }
    };
}
