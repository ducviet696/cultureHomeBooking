package com.swp.culturehomestay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;

public class SettingActivity extends AppCompatActivity {
    ImageView btnSettingBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnSettingBack = (ImageView) findViewById(R.id.settingBack);
        btnSettingBack.setOnClickListener(onBackClick);
    }
    View.OnClickListener onBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
