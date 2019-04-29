package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.fragments.main.HomeFragment;
import com.swp.culturehomestay.models.Content;
import com.swp.culturehomestay.models.PaymentGet;
import com.swp.culturehomestay.models.PaymentPost;
import com.swp.culturehomestay.models.PaymentWantBean;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
import com.swp.culturehomestay.models.ReservationContent;
import com.swp.culturehomestay.models.ReservationGet;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.ConstantsWant;
import com.swp.culturehomestay.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHomePaymentActivity extends AppCompatActivity {

    public static final String TENANT_TO_WANT = "ttw";
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
    @BindView(R.id.rgPayment)
    RadioGroup groupPayment;
    int numberPeople, numRoom;
    long dStart, dEnd;
    String homestayId, hostEmail, interact, method, type, purpose, reservationCode, status;
    @BindView(R.id.layout_ins)
    LinearLayout layoutIns;
    @BindView(R.id.layout_req)
    LinearLayout layoutReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_home_payment);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
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
        if(status.equals(ConstantsWant.Homestay.Status.PENDDING)){
            layoutIns.setVisibility(View.GONE);
            layoutReq.setVisibility(View.VISIBLE);
        }

        //get Price detail
        PricePost pricePost = new PricePost(Constants.cultureIdList, homestayId, numRoom, numberPeople, dStart, dEnd);
        Call<PriceGet> call = Utils.getAPI().getPrice(pricePost, Constants.LANG);
        call.enqueue(new Callback<PriceGet>() {
            @Override
            public void onResponse(Call<PriceGet> call, Response<PriceGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode().equals(Constants.CODE_OK)) {
                        Content content = response.body().getContent();
                        tvTotalPrice.setText(Utils.formatPrice(content.getTotal()));
                        tvTotalPricePerNight.setText(Utils.formatPrice(content.getBasicFee()));
                        tvTotalPriceCultureFee.setText(Utils.formatPrice(content.getCultureFee()));
                        tvTotalPricePerWeekly.setText(Utils.formatPrice(content.getHollidayFee()));
                        Log.d("Price", "onResponse: " + pricePost + content);
                    } else {
                        Log.d("Price", "onFailure code: " + response.body().getCode() + "\n" + "PricePost: " + pricePost.toString());
                    }

                }

            }

            @Override
            public void onFailure(Call<PriceGet> call, Throwable t) {
                Log.d("price", "onFailure: " + t.getMessage());

            }
        });
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

    @OnClick({R.id.btnPayment, R.id.tvBack,R.id.btnBackHome})
    void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.btnPayment:
                onPaymentClick();
                break;
            case R.id.btnBackHome:
                onBackHomeClick();
        }
    }

    private void onBackHomeClick() {
        Intent intent = new Intent(BookingHomePaymentActivity.this, AdvanceSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME,"bookinghome");
        intent.putExtra(Constants.BUNDLE,bundle);
        startActivity(intent);
        finish();
    }

    private void onPaymentClick() {
        getSelectedRadioButton();
        PaymentWantBean paymentWantBean = new PaymentWantBean(method, interact, type, purpose, reservationCode);
        PaymentPost paymentPost = new PaymentPost(Constants.cultureIdList, numberPeople, dStart, dEnd, homestayId, hostEmail, paymentWantBean);
        Log.d("PaymentPost", "onPaymentClick: "+ paymentPost.toString());
        Call<PaymentGet> call = Utils.getAPI().getLinkPayment(paymentPost);
        call.enqueue(new Callback<PaymentGet>() {
            @Override
            public void onResponse(Call<PaymentGet> call, Response<PaymentGet> response) {
                if(response.isSuccessful() && response.body()!= null){
                    PaymentGet paymentGet = response.body();
                    if(paymentGet.getCode().equals("00")){
                        String url = paymentGet.getContent();
                        Log.d("Payment", "onResponse: "+ url);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                    else {
                        switch (paymentGet.getCode()){
                            case "08":
                                Toast.makeText(BookingHomePaymentActivity.this, "Not enough money, please recharge your account!!! ", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
                Log.d("Payment", "onResponse: null");
            }

            @Override
            public void onFailure(Call<PaymentGet> call, Throwable t) {
                Log.d("Payment", "onFailure: "+ t.getMessage());

            }
        });
    }

}
