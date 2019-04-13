package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
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

public class BookingHomeDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvBack)
    TextView txtBack;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tvNameHomeB)
    TextView txtHomeName;
    @BindView(R.id.tvCodeList)
    TextView txtCodelist;
    @BindView(R.id.tvLocation)
    TextView txtLocation;
    @BindView(R.id.ivHomeProfile)
    ImageView ivHome;
    @BindView(R.id.tvDateBooking)
    TextView txtDateBooking;
    @BindView(R.id.tvTotalGuest)
    TextView txtTotalGuest;
    @BindView(R.id.tvTotalPrice)
    TextView txtTotalPrice;
    int minGuest, maxGuest;
    int priceNightly, priceWeekend, priceLongTerm, totalPrice, totalPricePerNight, totalPricePerWeekly;
    List<Date> weeklyListBooking = new ArrayList<>();
    List<Date> dayListBooking = new ArrayList<>();
    String homeStayId;
    int guest = 1;

//    List<Date> dateList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.REQUEST_CODE) {
            if(resultCode==RESULT_OK){
                txtDateBooking.setText(Utils.formatDateShort(Constants.dateList.get(0)) + " - "+Utils.formatDateShort(Constants.dateList.get(Constants.dateList.size() - 1)));
                weeklyListBooking.clear();
                dayListBooking.clear();
                countDayWeekly();
                loadJson(homeStayId);
            } else if(resultCode ==Constants.RESULT_CODE_CHANGE_GUEST) {
                guest = data.getIntExtra("totalGuest",minGuest);
                txtTotalGuest.setText(String.valueOf(guest));
            }
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");
        homeStayId = bundle.getString(Constants.HOMESTAY_ID);
        txtDateBooking.setText(Utils.formatDateShort(Constants.dateList.get(0)) + " - "+Utils.formatDateShort(Constants.dateList.get(Constants.dateList.size() - 1)));
        countDayWeekly();
        loadJson(homeStayId);

    }
    @OnClick(R.id.tvBack)
    public void backClick()
    {
        finish();
    }

    //Event when click button
    @OnClick({R.id.btnTotalGuest, R.id.btnTotalPrice, R.id.btnDateBooking, R.id.btnNext})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnTotalGuest:
                Intent intentGuest = new Intent(BookingHomeDetailActivity.this, ChangeNumberOfGuestActivity.class);
                Bundle bundleGuest = new Bundle();
                bundleGuest.putInt("Min",minGuest);
                bundleGuest.putInt("Max",maxGuest);
                bundleGuest.putInt("Guest",guest);
                intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
                startActivityForResult(intentGuest,Constants.REQUEST_CODE);
                break;
            case R.id.btnTotalPrice:
                Intent intent = new Intent(BookingHomeDetailActivity.this, ShowPriceDetailActivity.class);
                Bundle bundlePrice = new Bundle();
                bundlePrice.putInt("priceNightly", priceNightly);
                bundlePrice.putInt("priceWeekend", priceWeekend);
                bundlePrice.putInt("priceLongTerm", priceLongTerm);
                bundlePrice.putInt("numDayNormal", dayListBooking.size());
                bundlePrice.putInt("numDayWeekly", weeklyListBooking.size());
                bundlePrice.putInt("totalPrice", totalPrice);
                intent.putExtra(Constants.BUNDLE, bundlePrice);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.btnDateBooking:
                Intent intentDate = new Intent(BookingHomeDetailActivity.this, PickDateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ACTIVITY_NAME,Constants.BOOKINGHOMEDETAILACTIVITY);
                intentDate.putExtra(Constants.BUNDLE, bundle);
                startActivityForResult(intentDate, Constants.REQUEST_CODE);
                break;
            case R.id.btnNext:
                Intent intentNext = new Intent(BookingHomeDetailActivity.this, BookingHomeConfirmActivity.class);
                startActivity(intentNext);
                break;
        }
    }


    //get homestay
    public void loadJson(String homestaysID) {
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<HomeStay> call = iApi.getHomeById(homestaysID,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    HomeStay homeStay = response.body();
                    Utils.loadImge(BookingHomeDetailActivity.this, ivHome, Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    txtHomeName.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
                    txtCodelist.setText(homestaysID);
                    txtLocation.setText(homeStay.getAddress().getAddressFull());
                    minGuest = homeStay.getStandartGuest();
                    maxGuest = homeStay.getMaximunGuest();
                    priceNightly = homeStay.getPriceNightly();
                    priceWeekend = homeStay.getPriceWeekend();
                    priceLongTerm = homeStay.getPriceLongTerm();
                    guest = minGuest;
                    txtTotalGuest.setText(String.valueOf(homeStay.getStandartGuest()));
                    totalPrice = getTotalPrice();
                    txtTotalPrice.setText(Utils.formatPrice(totalPrice));

                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
                Log.d("Fail", "onFailure: "+t.getMessage());

            }
        });
    }

    public void  countDayWeekly(){
        String[] weekly = {"Fri","Sat","Sun"};
        ArrayList<String> listWeekly = new ArrayList<String>(Arrays.asList(weekly));
        for(Date date : Constants.dateList) {
            if(listWeekly.contains(Utils.formatDayOfWeek(date))){
                weeklyListBooking.add(date);
            } else {
                dayListBooking.add(date);
            }
        }
    }

    public int getTotalPrice(){
        totalPricePerNight = dayListBooking.size() * priceNightly;
        totalPricePerWeekly = weeklyListBooking.size() * priceWeekend;
        return totalPricePerNight + totalPricePerWeekly;
    }

}
