package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.Content;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPriceDetailActivity extends AppCompatActivity {

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
    String homestayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_price_detail);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        homestayId = bundle.getString(Constants.HOMESTAY_ID);
        int guest = bundle.getInt("guest");
        int roomNum = bundle.getInt("roomNum");
        List<Date> listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        PricePost pricePost = new PricePost(Constants.cultureIdList,homestayId,roomNum,guest,listDateBooking.get(0)
                .getTime(),listDateBooking.get(listDateBooking.size()-1).getTime());
        Call<PriceGet> call = Utils.getAPI().getPrice(pricePost,Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
                if(response.isSuccessful() && response.body()!=null){
                    if(response.body().getCode().equals(Constants.CODE_OK)) {
                        Content content = response.body().getContent();
                        tvTotalPrice.setText(Utils.formatPrice(content.getTotal()));
                        tvTotalPricePerNight.setText(Utils.formatPrice(content.getBasicFee()));
                        tvTotalPriceCultureFee.setText(Utils.formatPrice(content.getCultureFee()));
                        tvTotalPricePerWeekly.setText(Utils.formatPrice(content.getHollidayFee()));
                        Log.d("Price", "onResponse: "+ pricePost + content);
                    } else {
                        Log.d("Price", "onFailure code: "+response.body().getCode()+"\n"+"PricePost: "+ pricePost.toString());
                    }

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
