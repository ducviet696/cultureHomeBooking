package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomRateActivity extends AppCompatActivity {

    String standartGuest, maximunGuest, priceNightly, priceWeekend, priceLongTerm;
    @BindView(R.id.tvMinGuest)
    TextView txtStandartGuest;
    @BindView(R.id.tvMaxGuest)
    TextView txtMaximunGuest;
    @BindView(R.id.tvPricePerNight)
    TextView txttvPricePerNight;
    @BindView(R.id.tvPricePerWeekly)
    TextView txtPriceWeekend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_rate);
        ButterKnife.bind(this);
        getData();

    }
    @OnClick(R.id.tvClose)
    public void onClickBack()
    {
        finish();
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        standartGuest = bundle.getString("standartGuest");
        maximunGuest = bundle.getString("maximunGuest");
        priceNightly = bundle.getString("priceNightly");
        priceWeekend = bundle.getString("priceWeekend");
        priceLongTerm = bundle.getString("priceWeekend");

        txtStandartGuest.setText(standartGuest);
        txtMaximunGuest.setText(maximunGuest);
        txttvPricePerNight.setText(priceNightly);
        txtPriceWeekend.setText(priceWeekend);
    }
}
