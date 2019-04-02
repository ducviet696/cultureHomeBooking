package com.swp.culturehomestay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swp.culturehomestay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BookingHomeConfirmActivity extends AppCompatActivity {

    @BindView(R.id.btnPaypal)
    ImageButton btnPaypal;
//    @BindView(R.id.tvBack)
//    TextView txtBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_confirm);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.tvBack)
    public void backClick()
    {
        finish();
    }
}
