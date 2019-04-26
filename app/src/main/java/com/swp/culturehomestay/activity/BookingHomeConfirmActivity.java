package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.UserWant;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingHomeConfirmActivity extends AppCompatActivity {

    @BindView(R.id.btnPaypal)
    ImageButton btnPaypal;
    @BindView(R.id.ivHomeProfile)
    ImageView ivHomeProfile;
    @BindView(R.id.tvNameHome)
    TextView tvNameHome;
    @BindView(R.id.tvCheckinDate)
    TextView tvCheckinDate;
    @BindView(R.id.tvCheckoutDate)
    TextView tvCheckoutDate;
    @BindView(R.id.tvTotalGuest)
    TextView tvTotalGuest;
    @BindView(R.id.tvCancelType)
    TextView tvCancelType;
    @BindView(R.id.totalPrice)
    TextView tvtotalPrice;
    String homestayId, userId;
    List<Date> listDateBooking = new ArrayList<>();
    int guest, totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_confirm);
        ButterKnife.bind(this);
        getData();
        loadHomestayById();
        LoadUserById();
    }

    private void LoadUserById() {
        Call<UserWant> call = Utils.getAPI().getUserById(userId);
    }

    private void loadHomestayById() {
        Call<HomeStay> call = Utils.getAPI().getHomeById(homestayId,Constants.LANG);
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!=null){
                    HomeStay homeStay = response.body();
                    Utils.loadImge(BookingHomeConfirmActivity.this,ivHomeProfile,Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    tvNameHome.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
                    tvCancelType.setText(homeStay.getCancelPolicy().toUpperCase());
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {

            }
        });
    }

    private void getData() {
        userId = Utils.getUserId(BookingHomeConfirmActivity.this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        homestayId = bundle.getString(Constants.HOMESTAY_ID);
        guest = bundle.getInt("guest");
        totalPrice = bundle.getInt("totalPrice");
        listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        Date dStart = listDateBooking.get(0);
        Date dEnd = listDateBooking.get(listDateBooking.size()-1);
        tvCheckinDate.setText(Utils.formatDate(dStart));
        tvCheckoutDate.setText(Utils.formatDate(dEnd));
        tvTotalGuest.setText(String.valueOf(guest));
        tvtotalPrice.setText(Utils.formatPrice(totalPrice));
    }

    @OnClick(R.id.tvBack)
    public void backClick()
    {
        finish();
    }
}
