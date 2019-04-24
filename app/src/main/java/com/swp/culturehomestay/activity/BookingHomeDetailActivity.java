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
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
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
    List<Date> listDateBooking = new ArrayList<>();
    String homeStayId;
    int guest = 1;
    IApi mService;
    Date dStart;
    Date dEnd;


//    List<Date> dateList;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.REQUEST_CODE) {
            if(resultCode==RESULT_OK){
                listDateBooking = (List<Date>) data.getSerializableExtra(Constants.LIST_DATE_BOOKING);
                loadHomestayFromID(homeStayId);
            } else if(resultCode ==Constants.RESULT_CODE_CHANGE_GUEST) {
                guest = data.getIntExtra("totalGuest",minGuest);
                txtTotalGuest.setText(String.valueOf(guest));
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_detail);
        ButterKnife.bind(this);
        mService = Utils.getAPI();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");
        listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        homeStayId = bundle.getString(Constants.HOMESTAY_ID);
        loadHomestayFromID(homeStayId);
        Log.d("cultureIdListDetail", "onClick: " + Constants.cultureIdList);

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
                changeGuestNumber();
                break;
            case R.id.btnTotalPrice:
                showPriceDetailActivity();
                break;
            case R.id.btnDateBooking:
                showPickDateActivity();
                break;
            case R.id.btnNext:
                Intent intentNext = new Intent(BookingHomeDetailActivity.this, BookingHomeConfirmActivity.class);
                startActivity(intentNext);
                break;
        }
    }

    private void showPickDateActivity() {
        Intent intentDate = new Intent(BookingHomeDetailActivity.this, PickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME,Constants.BOOKINGHOMEDETAILACTIVITY);
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        intentDate.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intentDate, Constants.REQUEST_CODE);
    }

    private void showPriceDetailActivity() {
        Intent intent = new Intent(BookingHomeDetailActivity.this, ShowPriceDetailActivity.class);
        Bundle bundlePrice = new Bundle();
        bundlePrice.putString(Constants.HOMESTAY_ID, homeStayId);
        bundlePrice.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        intent.putExtra(Constants.BUNDLE, bundlePrice);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void changeGuestNumber() {
        Intent intentGuest = new Intent(BookingHomeDetailActivity.this, ChangeNumberOfGuestActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min",minGuest);
        bundleGuest.putInt("Max",maxGuest);
        bundleGuest.putInt("Guest",guest);
        bundleGuest.putString(Constants.ACTIVITY_NAME,Constants.BOOKINGHOMEDETAILACTIVITY);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest,Constants.REQUEST_CODE);
    }


    public void loadHomestayFromID(String homestaysID) {

        Call<HomeStay> call = mService.getHomeById(homestaysID,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    HomeStay homeStay = response.body();
                    Utils.loadImge(BookingHomeDetailActivity.this, ivHome, Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    txtHomeName.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
                    txtCodelist.setText(homeStay.getHouseCode());
                    txtLocation.setText(homeStay.getAddress().getAddressFull());
                    minGuest = homeStay.getStandartGuest();
                    maxGuest = homeStay.getMaximunGuest();
                    priceNightly = homeStay.getPriceNightly();
                    priceWeekend = homeStay.getPriceWeekend();
                    priceLongTerm = homeStay.getPriceLongTerm()==null?0:homeStay.getPriceLongTerm();
                    guest = minGuest;
                    txtTotalGuest.setText(String.valueOf(homeStay.getStandartGuest()));
                    dStart = listDateBooking.get(0);
                    dEnd = listDateBooking.get(listDateBooking.size() - 1);
                    txtDateBooking.setText(Utils.formatDateShort(dStart) + " - "+Utils.formatDateShort(dEnd));
                    getTotalPrice();
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
                Log.d("Fail", "onFailure: "+t.getMessage());

            }
        });
    }
    public void getTotalPrice(){
        PricePost pricePost = new PricePost(Constants.cultureIdList,homeStayId,guest,dStart.getTime(),dEnd.getTime());
        Call<PriceGet> call = mService.getPrice(pricePost,Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
              if(response.isSuccessful() && response.body()!= null){
                  if(response.body().getCode().equals(Constants.CODE_OK)){
                      txtTotalPrice.setText(Utils.formatPrice(response.body().getContent().getTotal()));
                      Log.d("Price", "onSucces code: "+response.body().getCode()+"\n"+"PricePost: "+ pricePost.toString());
                  } else {
                      Log.d("Price", "onFailure code: "+response.body().getCode()+"\n"+"PricePost: "+ pricePost.toString());
                  }

              }
            }

            @Override
            public void onFailure(Call<PriceGet> call, Throwable t) {
                Log.d("Price", "onFailure: "+ t.getMessage());
            }
        });
    }

}
