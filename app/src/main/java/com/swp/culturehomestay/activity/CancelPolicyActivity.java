package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CancelPolicyActivity extends AppCompatActivity {

    @BindView(R.id.layoutFlex)
    LinearLayout layoutFlex;
    @BindView(R.id.layoutMod)
    LinearLayout layoutMod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_policy);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String cancelType = intent.getStringExtra(Constants.CANCEL_TYPE);
        if(cancelType.equals(Constants.CANCEL_FLEX)) {
            layoutFlex.setVisibility(View.VISIBLE);
            layoutMod.setVisibility(View.GONE);
        } else {
            layoutFlex.setVisibility(View.GONE);
            layoutMod.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.tvClose)
    public void onClickBack()
    {
        finish();
    }
}
