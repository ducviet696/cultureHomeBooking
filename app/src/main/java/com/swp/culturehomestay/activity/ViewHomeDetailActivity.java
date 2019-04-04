package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomestayMulti;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHomeDetailActivity extends AppCompatActivity {

    String homestayName ="";
    @BindView(R.id.ivHomeProfile)
    ImageView ivHomeProfile;
    @BindView(R.id.tvTypeHome)
    TextView txtType;
    @BindView(R.id.tvNameHome)
    TextView txtName;
    @BindView(R.id.tvLocation)
    TextView txtLocation;
    @BindView(R.id.tvCodeList)
    TextView txtCode;
    @BindView(R.id.BedRoomNum)
    TextView txtBedroomNum;
    @BindView(R.id.tvHostName)
    TextView txtHost;
    @BindView(R.id.tvPeople)
    TextView txtMaximunGuest;
    @BindView(R.id.tvRoomNm)
    TextView txtnumberRoom;
    @BindView(R.id.tvBedNum)
    TextView txtBedNum;
    @BindView(R.id.tvBathNum)
    TextView txtBathNum;
    @BindView(R.id.tvAboutHome)
    TextView txtAboutHome;
    @BindView(R.id.tvPrice)
    TextView txtprice;
    @BindView(R.id.btnBooking)
    Button btnBooking;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_detail);
        // Bind widget
        ButterKnife.bind(this);
        //get homestayid from wishlistFragment
        Intent intent = getIntent();
        String homestayID = intent.getStringExtra("homestayId");

        loadJson(homestayID);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // event when clicked Check Now btn
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHomeDetailActivity.this, BookingHomePickDateActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadJson(String homestaysID) {
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<HomeStay> call = iApi.getHomeById(homestaysID,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    HomeStay homeStay = response.body();
                    Utils.loadImge(ViewHomeDetailActivity.this,ivHomeProfile, Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    HomestayMulti homestayMulti = homeStay.getHomestayMultis().get(0);
                    homestayName = homestayMulti.getHomestayName();
                    CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.main_collapsing);
                    mCollapsingToolbarLayout.setTitle(homestayName);
                    txtName.setText(homestayName);
                    txtType.setText(homeStay.getType());
                    txtBedroomNum.setText(" \u25CF "+String.valueOf(homeStay.getNumberRoom()) + " Bed Room");
                    txtCode.setText(homestaysID);
                    txtLocation.setText(homeStay.getAddress().getAddressFull());
                    txtHost.setText(homeStay.getHostEmail());
                    txtMaximunGuest.setText(homeStay.getMaximunGuest().toString());
                    txtnumberRoom.setText(String.valueOf(homeStay.getNumberRoom()));
                    txtBedNum.setText(String.valueOf(homeStay.getNumberRoom()*2));
                    Utils.checkStringNull(txtBathNum, homeStay.getBathRoom().toString());
                    txtAboutHome.setText(homestayMulti.getDescription()
                            +"\n" + homestayMulti.getHouseRule());
                    txtprice.setText(String.valueOf(homeStay.getPriceNightly()) + "$/night");

                } else {
                    Toast.makeText(ViewHomeDetailActivity.this, "No result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
                Log.d("AAA", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //add menu share + add wishlist
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homedetail, menu);
        return true;
    }


}
