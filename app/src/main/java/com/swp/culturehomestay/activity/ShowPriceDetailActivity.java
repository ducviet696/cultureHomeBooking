package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.Content;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.tvTotalPriceCultureFee)
    TextView tvTotalPriceCultureFee;
    int priceNightly, priceWeekend, priceLongTerm, totalPrice, totalPricePerNight, totalPricePerWeekly, numDayNormal,numDayWeekly;
    String homestayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_price_detail);
        ButterKnife.bind(this);
        getData();

//        bindDataToView();



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
        homestayId = bundle.getString(Constants.HOMESTAY_ID);
        PricePost pricePost = new PricePost(Constants.cultureIdList,homestayId,Constants.dateList.get(0)
                .getTime(),Constants.dateList.get(Constants.dateList.size()-1).getTime());
        Call<PriceGet> call = Utils.getAPI().getPrice(pricePost,Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Content content = response.body().getContent();
                    tvTotalPrice.setText(Utils.formatPrice(content.getTotal()));
                    tvTotalPricePerNight.setText(Utils.formatPrice(content.getBasicFee()));
//                    tvCalPriceDay.setText(Utils.formatPrice(priceNightly)+"normal day(s) x "+ String.valueOf(numDayNormal) +" night(s), " +
//                            Utils.formatPrice(priceWeekend)+" weekly x "+ String.valueOf(numDayWeekly) +" night(s)");
//                    tvCalPriceDay.setText(R.string.ni);
                    tvTotalPriceCultureFee.setText(Utils.formatPrice(content.getCultureFee()));
                    tvTotalPricePerWeekly.setText(Utils.formatPrice(content.getHollidayFee()));
//                    Log.d("price", "dStart: " + Constants.dateList.get(0)
//                            .getTime()+"\ndEnd"+ Constants.dateList.get(Constants.dateList.size()-1).getTime());
                    Log.d("Price", "onResponse: "+ pricePost + content);
                }

            }

            @Override
            public void onFailure(Call<PriceGet> call, Throwable t) {
                Log.d("price", "onFailure: "+t.getMessage());

            }
        });
    }


    @OnClick(R.id.tvClose)
    public void closeClick() {
        finish();
    }
}
