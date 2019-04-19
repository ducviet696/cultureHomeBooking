package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPriceDetailActivity extends AppCompatActivity {

//    List<Date> dateListBooking;
//    List<Date> weeklyListBooking = new ArrayList<>();
//    List<Date> dayListBooking = new ArrayList<>();
    @BindView(R.id.layoutNormalDay)
    RelativeLayout layoutNormalDay;
    @BindView(R.id.layoutWeekly)
    RelativeLayout layoutWeekly;
    @BindView(R.id.tvTotalPricePerNight)
    TextView tvTotalPricePerNight;
    @BindView(R.id.tvTotalPricePerWeekly)
    TextView tvTotalPricePerWeekly;
    @BindView(R.id.tvCalPriceDay)
    TextView tvCalPriceDay;
    @BindView(R.id.tvCalWeekly)
    TextView tvCalWeekly;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    int priceNightly, priceWeekend, priceLongTerm, totalPrice, totalPricePerNight, totalPricePerWeekly, numDayNormal,numDayWeekly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_price_detail);
        ButterKnife.bind(this);
        getData();
        bindDataToView();



    }
    public void bindDataToView(){
        if(numDayNormal > 0) {
            totalPricePerNight = numDayNormal * priceNightly;
            tvTotalPricePerNight.setText(Utils.formatPrice(totalPricePerNight));
            tvCalPriceDay.setText(Utils.formatPrice(priceNightly)+" x "+ String.valueOf(numDayNormal) +" night(s)");
        } else {
            totalPricePerNight = 0;
            layoutNormalDay.setVisibility(View.GONE);
        }
        if(numDayWeekly > 0) {
            totalPricePerWeekly = numDayWeekly * priceWeekend;
            tvTotalPricePerWeekly.setText(Utils.formatPrice(totalPricePerWeekly));
            tvCalWeekly.setText(Utils.formatPrice(priceWeekend)+" x "+ String.valueOf(numDayWeekly) +" night(s)");
        } else {
            totalPricePerWeekly = 0;
            layoutWeekly.setVisibility(View.GONE);
        }

        totalPrice = totalPricePerNight + totalPricePerWeekly;
        tvTotalPrice.setText(Utils.formatPrice(totalPrice));
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        priceNightly = bundle.getInt("priceNightly");
        priceWeekend = bundle.getInt("priceWeekend");
        priceLongTerm = bundle.getInt("priceLongTerm");
        numDayNormal = bundle.getInt("numDayNormal");
        numDayWeekly = bundle.getInt("numDayWeekly");
        totalPrice = bundle.getInt("totalPrice");
    }


    @OnClick(R.id.tvClose)
    public void closeClick() {
        finish();
    }
}
