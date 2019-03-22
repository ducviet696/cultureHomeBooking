package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.swp.culturehomestay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingHomeDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvBack)
    TextView txtBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_detail);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.tvBack)
    public void backClick()
    {
        finish();
    }
    @OnClick(R.id.btnNext)
    public void onClickNext()
    {
        Intent intent = new Intent(BookingHomeDetailActivity.this, BookingHomeConfirmActivity.class);
        startActivity(intent);
    }

}
