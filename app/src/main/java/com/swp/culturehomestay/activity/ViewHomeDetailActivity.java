package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.HorizontalListHomeAdapter;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.models.Amenity;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomestayImage;
import com.swp.culturehomestay.models.HomestayMulti;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHomeDetailActivity extends AppCompatActivity {

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
    @BindView(R.id.tvPhotos)
    TextView tvTotalPhoto;
    @BindView(R.id.layoutFeature)
    LinearLayout layoutFeatur;
    @BindView(R.id.tvFeature)
    TextView txtFeature;
    @BindView(R.id.tvTotalAmen)
    TextView txtTotalAmen;
    @BindView(R.id.tvCancelType)
    TextView txtCancelType;
    @BindView(R.id.rvSimilarListing)
    RecyclerView rvSimilarListing;
    public static List<HomestayImage> listHomeImg = new ArrayList<>();
    public ArrayList<String> listUrlImg = new ArrayList<>();
    public ArrayList<String> listAmen = new ArrayList<>();
    public List<Amenity> listAmenity = new ArrayList<>();
    public List<HomeStay> homestays = new ArrayList<>();
    String homestayID;
    String cancelType, standartGuest, maximunGuest, priceNightly, priceWeekend, priceLongTerm;
    private HorizontalListHomeAdapter horAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_detail);
        // Bind widget
        ButterKnife.bind(this);
        //get homestayid from wishlistFragment
        Intent intent = getIntent();
        homestayID = intent.getStringExtra(Constants.HOMESTAY_ID);
        loadJson(homestayID);
        loadJsonSimilarList();
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // event when clicked Check Now btn
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHomeDetailActivity.this, BookingHomePickDateActivity.class);
                intent.putExtra(Constants.HOMESTAY_ID, homestayID);
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
                    listHomeImg = homeStay.getHomestayImages();
                    listAmenity = homeStay.getAmenities();
                    Utils.loadImge(ViewHomeDetailActivity.this,ivHomeProfile, Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    HomestayMulti homestayMulti = homeStay.getHomestayMultis().get(0);
                    String homestayName = homestayMulti.getHomestayName();
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
                    Utils.checkStringNull(txtBathNum, String.valueOf(homeStay.getBathRoom()));
                    txtAboutHome.setText(homestayMulti.getDescription());
                    txtprice.setText(Utils.formatPrice(homeStay.getPriceNightly()) + "/night");
                    txtTotalAmen.setText("+ "+String.valueOf(listAmenity.size()));
                    tvTotalPhoto.setText(String.valueOf("+ "+listHomeImg.size()));
                    cancelType = homeStay.getCancelPolicy();
                    txtCancelType.setText(cancelType.toUpperCase());

                    if(!Utils.isNullOrEmpty(homestayMulti.getDesUnifuture())) {
                        layoutFeatur.setVisibility(View.VISIBLE);
                        txtFeature.setText(homestayMulti.getDesUnifuture());
                    } else {
                        layoutFeatur.setVisibility(View.GONE);

                    }
                    for(HomestayImage hom : listHomeImg){
                        listUrlImg.add(hom.getImageUrl());
                    }
                    for(Amenity amenity : homeStay.getAmenities()){
                        listAmen.add(amenity.getEnglishName());
                    }
                    standartGuest = String.valueOf(homeStay.getStandartGuest());
                    maximunGuest = String.valueOf(homeStay.getMaximunGuest());
                    priceNightly = Utils.formatPrice(homeStay.getPriceNightly());
                    priceWeekend = Utils.formatPrice(homeStay.getPriceWeekend());
                    priceLongTerm = Utils.formatPrice(homeStay.getPriceLongTerm());

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

    public void loadJsonSimilarList(){
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<List<HomeStay>> call = iApi.getWishList(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if(response.isSuccessful() && response.body()!=null ) {
                    homestays = response.body();
                    horAdapter = new HorizontalListHomeAdapter(ViewHomeDetailActivity.this,homestays);
                    rvSimilarListing.setLayoutManager(new LinearLayoutManager(ViewHomeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    rvSimilarListing.setAdapter(horAdapter);
                    horAdapter.notifyDataSetChanged();
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<List<HomeStay>> call, Throwable t) {

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

    //Event when click button
    @OnClick({R.id.btnCancelPolicy, R.id.btnShowAlbumPhoto, R.id.btnRoomRate, R.id.btnAmenity})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btnCancelPolicy:
                Intent intentCancel = new Intent(ViewHomeDetailActivity.this, CancelPolicyActivity.class);
                intentCancel.putExtra(Constants.CANCEL_TYPE, cancelType);
                startActivity(intentCancel);
                break;
            case R.id.btnShowAlbumPhoto:
                Intent intentPhoto = new Intent(ViewHomeDetailActivity.this, ShowAlbumPhotoActivity.class);
                intentPhoto.putStringArrayListExtra("listUrlImg", listUrlImg);
                startActivity(intentPhoto);
                break;
            case R.id.btnRoomRate:
                Intent intentRate = new Intent(ViewHomeDetailActivity.this, RoomRateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("standartGuest", standartGuest);
                bundle.putString("maximunGuest", maximunGuest);
                bundle.putString("priceNightly", priceNightly);
                bundle.putString("priceWeekend", priceWeekend);
                bundle.putString("priceLongTerm", priceLongTerm);
                intentRate.putExtra(Constants.BUNDLE, bundle);
                startActivity(intentRate);
                break;
            case R.id.btnAmenity:
                Intent intent2 = new Intent(ViewHomeDetailActivity.this, ShowAmenityActivity.class);
//                intent2.putStringArrayListExtra("listAmen",listAmen);
                intent2.putExtra(Constants.HOMESTAY_ID,homestayID);
                startActivity(intent2);
                break;
        }
    }


    //event when click homestay
    private void initListener() {

        horAdapter.setOnItemClickListener(new HorizontalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ViewHomeDetailActivity.this, ViewHomeDetailActivity.class);
                HomeStay homeStay = homestays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);

            }
        });

    }


}
