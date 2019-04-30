package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.ReservationBean;
import com.swp.culturehomestay.models.ReservationContent;
import com.swp.culturehomestay.models.ReservationPost;
import com.swp.culturehomestay.models.UserWant;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.ConstantsWant;
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

    @BindView(R.id.groupPurpose)
    RadioGroup groupPurpose;
    RadioButton rbPurpose;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.tvHouseCode)
    TextView tvHouseCode;
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
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserMail)
    TextView tvUserMail;
    @BindView(R.id.tvUserPhone)
    TextView tvUserPhone;
    String homestayId, userId;
    List<Date> listDateBooking = new ArrayList<>();
    int guest, totalPrice, numRoom;
    @BindView(R.id.tvEditInfo)
    TextView tvEditInfo;
    String fullName,email,phone, hostId, purpose, houseCode,hostEmail,tenantId;
    String status;
    Date dStart, dEnd;
    ReservationContent reservationContent;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Constants.REQUEST_CODE_BOOKING){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getBundleExtra(Constants.BUNDLE);
                fullName =  bundle.getString("fullName");
                email = bundle.getString("email");
                phone = bundle.getString("phone");
                tvUserName.setText(fullName);
                tvUserMail.setText(email);
                tvUserPhone.setText(phone);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_confirm);
        ButterKnife.bind(this);
        getSelectedRadioButton();
        getData();
        loadHomestayById();
        LoadUserById();
    }

    private void getSelectedRadioButton() {
        int selectedId = groupPurpose.getCheckedRadioButtonId();
        rbPurpose = (RadioButton) findViewById(selectedId);
        switch (rbPurpose.getId()){
            case R.id.rbFa:
                purpose = ConstantsWant.Transaction.Reservation.Purpose.FAMILY;
                break;
            case R.id.rbPar:
                purpose = ConstantsWant.Transaction.Reservation.Purpose.PARTY;
                break;
            case R.id.rbMe:
                purpose = ConstantsWant.Transaction.Reservation.Purpose.MEETING;
                break;
            case R.id.rbOt:
                purpose = ConstantsWant.Transaction.Reservation.Purpose.OTHER;
                break;
        }
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
                    tvHouseCode.setText("House code: "+homeStay.getHouseCode());
                    fullName = tvUserName.getText().toString();
                    email = tvUserMail.getText().toString();
                    phone = tvUserPhone.getText().toString();
                    hostId = homeStay.getHostId();
                    houseCode = homeStay.getHouseCode();
                    hostEmail = homeStay.getHostEmail();
                    if(homeStay.getBookingMethod().equals(Constants.BOOKING_INS)){
                        status = "na";
                    } else {
                        status = "pen";
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {

            }
        });
    }
    private void getData() {
        tenantId = Utils.getUserId(BookingHomeConfirmActivity.this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        homestayId = bundle.getString(Constants.HOMESTAY_ID);
        guest = bundle.getInt(Constants.GUEST);
        numRoom = bundle.getInt(Constants.ROOM);
        totalPrice = bundle.getInt("totalPrice");
        listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
        dStart = listDateBooking.get(0);
        dEnd = listDateBooking.get(listDateBooking.size()-1);
        tvCheckinDate.setText(Utils.formatDate(dStart));
        tvCheckoutDate.setText(Utils.formatDate(dEnd));
        tvTotalGuest.setText(String.valueOf(guest));
        tvtotalPrice.setText(Utils.formatPrice(totalPrice));
    }

  
    @OnClick({R.id.tvEditInfo,R.id.btnNext,R.id.tvBack})
     void onButtonClick(View view){
        switch (view.getId()){
            case R.id.tvEditInfo:
                onEditClick();
                break;
            case R.id.btnNext:
                onNextClick();
                break;
            case R.id.tvBack:
                finish();
                break;
        }
    }

    private void onNextClick() {
        getSelectedRadioButton();
        ReservationBean reservationBean = new ReservationBean(homestayId,phone,fullName,hostId,purpose,email,houseCode,status);
        ReservationPost reservationPost = new ReservationPost(Constants.cultureIdList,guest,numRoom,dStart.getTime(),dEnd.getTime(),homestayId,hostEmail,tenantId,reservationBean);
        Log.d("ReservationBean", "ReservationBean: "+ reservationPost.toString());
        Call<ReservationContent> call = Utils.getAPI().postResvervation(reservationPost);
        call.enqueue(new Callback<ReservationContent>() {
            @Override
            public void onResponse(Call<ReservationContent> call, Response<ReservationContent> response) {
                if(response.isSuccessful() && response.body()!= null){
                    reservationContent = response.body();
                    Intent intent = new Intent(BookingHomeConfirmActivity.this,BookingHomePaymentActivity.class);
                    intent.putExtra(Constants.ACTIVITY_NAME,Constants.BOOKING_HOME_CONFIRM);
                    intent.putExtra("reservationContent",reservationContent);
                    startActivity(intent);
                    Log.d("ReservationBean", "onResponse: "+reservationContent.toString());

                }
                Log.d("ReservationBean", "onResponse: response null");
            }

            @Override
            public void onFailure(Call<ReservationContent> call, Throwable t) {
                Log.d("ReservationBean", "onFailure: "+ t.getMessage());
            }
        });
    }

    public  void onEditClick(){
        Intent intent = new Intent(BookingHomeConfirmActivity.this, EditUserInfoPaymentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fullName",fullName);
        bundle.putString("email",email);
        bundle.putString("phone",phone);
        intent.putExtra(Constants.BUNDLE,bundle);
        startActivityForResult(intent,Constants.REQUEST_CODE_BOOKING);
    }
}
