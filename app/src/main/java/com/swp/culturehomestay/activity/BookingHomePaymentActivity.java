package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.PaymentGet;
import com.swp.culturehomestay.models.PaymentPost;
import com.swp.culturehomestay.models.PaymentWantBean;
import com.swp.culturehomestay.models.Reservation;
import com.swp.culturehomestay.models.ReservationContent;
import com.swp.culturehomestay.models.ReservationGet;
import com.swp.culturehomestay.models.ResultGetReservationModel;
import com.swp.culturehomestay.models.UserDetailModel;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.ConstantsWant;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHomePaymentActivity extends AppCompatActivity {

    public static final String TENANT_TO_WANT = "ttw";
    public static final String PURPOSE_RESERVATION = "re";
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
    @BindView(R.id.wantWl)
    TextView tvWantWl;
    @BindView(R.id.rgPayment)
    RadioGroup groupPayment;
    int numberPeople, numRoom, totalPrice, basicFee, cultureServiceFee, holidayFee;
    long dStart, dEnd;
    String homestayId, hostEmail, interact, method, type, purpose, reservationCode, status, previousActivity, reservationId;
    @BindView(R.id.layout_ins)
    LinearLayout layoutIns;
    @BindView(R.id.layout_req)
    LinearLayout layoutReq;
    ArrayList<Integer> listCultureChoice = new ArrayList<>();
    @BindView(R.id.btnPayment)
    Button btnPayment;
    double balance, balanceTranfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_payment);
        ButterKnife.bind(this);
        getData();
    }
    private void loadBalance() {
        String userId = Utils.getUserId(BookingHomePaymentActivity.this);
        Call<UserDetailModel> call = Utils.getAPI().getUserDetailById(userId);
        call.enqueue(new Callback<UserDetailModel>() {
            @Override
            public void onResponse(retrofit2.Call<UserDetailModel> call, Response<UserDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    balance = response.body().getTenant().getBalance();
                    balanceTranfer = response.body().getTenant().getBalanceTranfer();
                    double totalBalance = balance+balanceTranfer;
                    tvWantWl.setText("Want Wallet($"+totalBalance+")");
                }
            }

            @Override
            public void onFailure(Call<UserDetailModel> call, Throwable t) {

            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        previousActivity = intent.getStringExtra(Constants.ACTIVITY_NAME);
        if (Constants.BOOKING_FRAGMENT.equals(previousActivity)) {
            reservationId = intent.getStringExtra("reservationId");
            loadBalance();
            Call<ResultGetReservationModel> call = Utils.getAPI().getReservationById(reservationId);
            call.enqueue(new Callback<ResultGetReservationModel>() {
                @Override
                public void onResponse(Call<ResultGetReservationModel> call, Response<ResultGetReservationModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResultGetReservationModel body = response.body();
                        Reservation reservation = body.getContent();
                        Log.d("reservation", "onResponse: "+reservation);
                        homestayId = reservation.getHomestayId();
                        hostEmail = reservation.getHostEmail();
                        interact = Utils.getUserId(BookingHomePaymentActivity.this);
                        type = TENANT_TO_WANT;
                        numRoom = reservation.getNumRoom()!=null?reservation.getNumRoom():1;
                        numberPeople = reservation.getNumberPeople();
                        purpose = reservation.getPurpose();
                        reservationCode = reservation.getReservationCode();
                        dStart = reservation.getDStart();
                        dEnd = reservation.getDEnd();
                        status = reservation.getStatus();

                        String cultureServiceChoices = reservation.getCultureServiceChoices();
                        if(cultureServiceChoices!= null && !cultureServiceChoices.isEmpty()){
                            String[] cultureArr = cultureServiceChoices.split(",");
                            for (String cultureId : cultureArr) {
                                listCultureChoice.add(Integer.valueOf(cultureId));
                            }
                        } else {
                            listCultureChoice = new ArrayList<>();
                        }
                        totalPrice = reservation.getTotalFee();
                        basicFee = reservation.getBasicFee();
                        cultureServiceFee = reservation.getCultureServiceFee();
                        holidayFee = reservation.getHolidayFee();
                        showPriceDetail(totalPrice, basicFee, cultureServiceFee, holidayFee);

                        //click
                        PaymentWantBean paymentWantBean = new PaymentWantBean(method, interact, type, PURPOSE_RESERVATION, reservationCode);
                        PaymentPost paymentPost = new PaymentPost(listCultureChoice, numberPeople, dStart, dEnd, homestayId, hostEmail, paymentWantBean);
                        btnPayment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onPaymentClick(paymentPost);
                            }
                        });

                        if (status.equals(ConstantsWant.Homestay.Status.PENDDING)) {
                            layoutIns.setVisibility(View.GONE);
                            layoutReq.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResultGetReservationModel> call, Throwable t) {
                    Log.d("onFailure", "onFailure: "+t.getMessage());

                }
            });
        } else if (Constants.BOOKING_HOME_CONFIRM.equals(previousActivity)) {
//            balance = intent.getDoubleExtra("balance",0);
            loadBalance();
            ReservationContent reservationContent = (ReservationContent) intent.getSerializableExtra("reservationContent");
            ReservationGet reservationGet = reservationContent.getContent();
            Log.d("ReservationBean", "getData: " + reservationContent.toString());
            //get Data
            homestayId = reservationGet.getHomestayId();
            hostEmail = reservationGet.getHostEmail();
            interact = Utils.getUserId(BookingHomePaymentActivity.this);
            type = TENANT_TO_WANT;
            numRoom = reservationGet.getNumRoom();
            numberPeople = reservationGet.getNumberPeople();
            purpose = reservationGet.getPurpose();
            reservationCode = reservationGet.getReservationCode();
            dStart = reservationGet.getDStart();
            dEnd = reservationGet.getDEnd();
            status = reservationGet.getStatus();
            totalPrice = reservationGet.getTotalFee();
            basicFee = reservationGet.getBasicFee();
            cultureServiceFee = reservationGet.getCultureServiceFee();
            holidayFee = reservationGet.getHolidayFee();
            showPriceDetail(totalPrice, basicFee, cultureServiceFee, holidayFee);
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPaymentClick();
                }
            });

            if (status.equals(ConstantsWant.Homestay.Status.PENDDING)) {
                layoutIns.setVisibility(View.GONE);
                layoutReq.setVisibility(View.VISIBLE);
            }
        }


    }

    private void showPriceDetail(int totalPrice, int basicFee, int cultureServiceFee, int holidayFee) {
        tvTotalPrice.setText(Utils.formatPrice(totalPrice));
        tvTotalPricePerNight.setText(Utils.formatPrice(basicFee));
        tvTotalPriceCultureFee.setText(Utils.formatPrice(cultureServiceFee));
        tvTotalPricePerWeekly.setText(Utils.formatPrice(holidayFee));
    }


    private void getSelectedRadioButton() {
        int selectedId = groupPayment.getCheckedRadioButtonId();
        RadioButton rbPayment = (RadioButton) findViewById(selectedId);
        switch (rbPayment.getId()) {
            case R.id.rbPaypal:
                method = ConstantsWant.Transaction.PaymentMethod.type.PAYPAL;
                break;
            case R.id.rbWantpayment:
                method = ConstantsWant.Transaction.PaymentMethod.type.WANT_PAYMENT;
                break;
        }
    }

    @OnClick({R.id.btnPayment, R.id.tvBack, R.id.btnBackHome})
    void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.btnBackHome:
                onBackHomeClick();
        }
    }

    private void onBackHomeClick() {
        Intent intent = new Intent(BookingHomePaymentActivity.this,MainActivity.class);
        intent.putExtra("checkFragment",0);
        startActivity(intent);
        finish();
    }

    private void onPaymentClick() {
        getSelectedRadioButton();
        PaymentWantBean paymentWantBean = new PaymentWantBean(method, interact, type, PURPOSE_RESERVATION, reservationCode);
        PaymentPost paymentPost = new PaymentPost(Constants.cultureIdList, numberPeople, dStart, dEnd, homestayId, hostEmail, paymentWantBean);
        Log.d("PaymentPost", "onPaymentClick: " + paymentPost.toString());
        Call<PaymentGet> call = Utils.getAPI().getLinkPayment(paymentPost);
        call.enqueue(new Callback<PaymentGet>() {
            @Override
            public void onResponse(Call<PaymentGet> call, Response<PaymentGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PaymentGet paymentGet = response.body();
                    if (paymentGet.getCode().equals(Constants.CODE_OK)) {
                        if (method.equals(ConstantsWant.Transaction.PaymentMethod.type.PAYPAL)) {
                            String url = paymentGet.getContent();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(BookingHomePaymentActivity.this,MainActivity.class);
                            intent.putExtra("checkFragment",2);
                            startActivity(intent);
                            Toast.makeText(BookingHomePaymentActivity.this, "Payment Successfully ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        switch (paymentGet.getCode()) {
                            case "08":
                                Toast.makeText(BookingHomePaymentActivity.this, "Not enough money, please recharge your account!!! ", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<PaymentGet> call, Throwable t) {
                Log.d("Payment", "onFailure: " + t.getMessage());

            }
        });
    }

    private void onPaymentClick(@NonNull PaymentPost paymentPost) {
        getSelectedRadioButton();
        paymentPost.getPaymentWantBean().setMethod(method);
        Log.d("PaymentPost", "onPaymentClick: " + paymentPost.toString());
        Call<PaymentGet> call = Utils.getAPI().getLinkPayment(paymentPost);
        call.enqueue(new Callback<PaymentGet>() {
            @Override
            public void onResponse(Call<PaymentGet> call, Response<PaymentGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PaymentGet paymentGet = response.body();
                    if (paymentGet.getCode().equals("00")) {
                        if (method.equals(ConstantsWant.Transaction.PaymentMethod.type.PAYPAL)) {
                            String url = paymentGet.getContent();
                            Log.d("Payment", "onResponse: " + url);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(BookingHomePaymentActivity.this,MainActivity.class);
                            intent.putExtra("checkFragment",2);
                            startActivity(intent);
                            Toast.makeText(BookingHomePaymentActivity.this, "Payment Successfully ", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        switch (paymentGet.getCode()) {
                            case "08":
                                Toast.makeText(BookingHomePaymentActivity.this, "Not enough money, please recharge your account!!! ", Toast.LENGTH_SHORT).show();
                        }
                    }


                } else {
                        Toast.makeText(BookingHomePaymentActivity.this, "This homestay was already booked by others, please choose other homestay or other booking date.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PaymentGet> call, Throwable t) {
                Log.d("Payment", "onFailure: " + t.getMessage());

            }
        });
    }


}
