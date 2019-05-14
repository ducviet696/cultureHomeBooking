package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xw.repo.BubbleSeekBar;

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
    @BindView(R.id.tvRoomNumber)
    TextView tvRoomNumber;
    @BindView(R.id.layout_Seekbar)
    LinearLayout layout_Seekbar;
    @BindView(R.id.sbRommNum)
    BubbleSeekBar sbRommNum;
    int minGuest, maxGuest;
    int totalPrice;
    List<Date> listDateBooking = new ArrayList<>();
    List<Date> listDateDisable = new ArrayList<>();
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    String homeStayId;
    int guest = 1;
    int roomNum = 1;
    IApi mService;
    Date dStart;
    Date dEnd;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.REQUEST_CODE) {
            if(resultCode==RESULT_OK){
                listDateBooking = (List<Date>) data.getSerializableExtra(Constants.LIST_DATE_BOOKING);
                dStart = listDateBooking.get(0);
                dEnd = listDateBooking.get(listDateBooking.size() - 1);
                txtDateBooking.setText(Utils.formatDateShort(dStart) + " - "+Utils.formatDateShort(dEnd));
                getTotalPrice();
            } else if(resultCode ==Constants.RESULT_CODE_CHANGE_GUEST) {
                guest = data.getIntExtra(Constants.GUEST,minGuest);
                txtTotalGuest.setText(String.valueOf(guest));
                getTotalPrice();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_detail);
        ButterKnife.bind(this);
        mService = Utils.getAPI();
        getData();
        loadHomestayFromID(homeStayId);
        Log.d("cultureIdListDetail", "onClick: " + Constants.cultureIdList);

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        listDateDisable = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_DISABLE);
        cultureIdList = bundle.getIntegerArrayList(Constants.LIST_CULTURE_SELECTED);
        roomNum = bundle.getInt(Constants.ROOM);
        homeStayId = bundle.getString(Constants.HOMESTAY_ID);
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
                onNextClick();
                break;
        }
    }

    private void onNextClick() {
        Intent intentNext = new Intent(BookingHomeDetailActivity.this, BookingHomeConfirmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.LIST_DATE_BOOKING,(Serializable)listDateBooking);
        bundle.putInt(Constants.GUEST,guest);
        bundle.putInt(Constants.ROOM,roomNum);
        bundle.putString(Constants.HOMESTAY_ID,homeStayId);
        bundle.putInt("totalPrice",totalPrice);
        bundle.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED,cultureIdList);
        intentNext.putExtra(Constants.BUNDLE,bundle);
        startActivity(intentNext);
    }

    private void showPickDateActivity() {
        Intent intentDate = new Intent(BookingHomeDetailActivity.this, PickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME,Constants.BOOKINGHOMEDETAILACTIVITY);
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        bundle.putSerializable(Constants.LIST_DATE_DISABLE, (Serializable) listDateDisable);
        intentDate.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intentDate, Constants.REQUEST_CODE);
    }

    private void showPriceDetailActivity() {
        Intent intent = new Intent(BookingHomeDetailActivity.this, ShowPriceDetailActivity.class);
        Bundle bundlePrice = new Bundle();
        bundlePrice.putString(Constants.HOMESTAY_ID, homeStayId);
        bundlePrice.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        bundlePrice.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED,cultureIdList);
        bundlePrice.putInt(Constants.GUEST,guest);
        bundlePrice.putInt(Constants.ROOM,roomNum);
        intent.putExtra(Constants.BUNDLE, bundlePrice);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void changeGuestNumber() {
        Intent intentGuest = new Intent(BookingHomeDetailActivity.this, ChangeNumberOfGuestActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min",minGuest);
        bundleGuest.putInt("Max",maxGuest);
        bundleGuest.putInt(Constants.GUEST,guest);
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
                    minGuest = 1;
                    maxGuest = homeStay.getMaximunGuest();
                    guest = minGuest;
                    txtTotalGuest.setText(String.valueOf(guest));
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
        PricePost pricePost = new PricePost(cultureIdList,homeStayId,roomNum,guest,dStart.getTime(),dEnd.getTime());
        Call<PriceGet> call = mService.getPrice(pricePost,Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
              if(response.isSuccessful() && response.body()!= null){
                  if(response.body().getCode().equals(Constants.CODE_OK)){
                      totalPrice = response.body().getContent().getTotal();
                      txtTotalPrice.setText(Utils.formatPrice(totalPrice));
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
