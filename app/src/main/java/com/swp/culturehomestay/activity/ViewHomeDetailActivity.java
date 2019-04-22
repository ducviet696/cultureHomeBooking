package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.HorizontalListHomeAdapter;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.fragments.main.HomeFragment;
import com.swp.culturehomestay.models.Amenity;
import com.swp.culturehomestay.models.FeedBack;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomestayImage;
import com.swp.culturehomestay.models.HomestayMulti;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.services.WishlistService;
import com.swp.culturehomestay.utils.AmenityCollection;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
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
    @BindView(R.id.btnAddWishlist)
    FloatingActionButton btnAddWishlist;
    @BindView(R.id.tvTotalCulture)
    TextView txtTotalCul;
    @BindView(R.id.ratingBar2)
    RatingBar ratingBar;
    public static List<HomestayImage> listHomeImg = new ArrayList<>();
    public ArrayList<String> listUrlImg = new ArrayList<>();
    public ArrayList<String> listAmen = new ArrayList<>();
    public List<Amenity> listAmenity = new ArrayList<>();
    public List<HomeStay> homeStays = new ArrayList<>();
    public List<FeedBack> feedBackList = new ArrayList<>();
    String homestayID;
    int totalRate;
    String cancelType, standartGuest, maximunGuest, priceNightly, priceWeekend, priceLongTerm;
    private HorizontalListHomeAdapter horAdapter;
    IApi mService;
    WishlistService wishlistService;
    @BindView(R.id.layout_review)
    LinearLayout layoutReview;
    @BindView(R.id.tvUserEmailFb)
    TextView tvUserEmailFb;
    @BindView(R.id.tvStar)
    TextView tvStar;
    @BindView(R.id.tvDateFeedback)
    TextView tvDateFeedback;
    @BindView(R.id.tvDesReview)
    TextView tvDesReview;
    @BindView(R.id.btnShowAllReview)
    TextView btnShowAllReview;
    @BindView(R.id.iconBooking)
    ImageView iconBooking;
    @BindView(R.id.typeBooking)
    TextView tvTypeBooking;
//    ArrayList<String> cultureIdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_detail);
        // Bind widget
        ButterKnife.bind(this);
        Constants.cultureIdList = new ArrayList<>();
        mService = Utils.getAPI();
        wishlistService = new WishlistService();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==Constants.REQUEST_CODE)
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Constants.cultureIdList = data.getIntegerArrayListExtra(Constants.CULTURE_ID_LIST);
        }
    }

    private void loadJson(String wishlistsID) {
        Call<HomeStay> call = mService.getHomeById(wishlistsID,"en");
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

                    if(wishlistService.checkIfHomestayInCurrentUserWishList(homeStay.getHomestayId()))
                        btnAddWishlist.setImageDrawable(getDrawable(R.drawable.ic_fav_act_35dp));
                    else
                        btnAddWishlist.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_black_24dp));
                    txtName.setText(homestayName);
                    txtType.setText(AmenityCollection.homeType().get(homeStay.getType()).toUpperCase());
                    txtBedroomNum.setText(" \u25CF "+String.valueOf(homeStay.getNumberRoom()) + " BED ROOM");
                    txtCode.setText(homeStay.getHouseCode());
                    txtLocation.setText(homeStay.getAddress().getAddressFull());
                    txtHost.setText(homeStay.getHostEmail());
                    txtMaximunGuest.setText(homeStay.getMaximunGuest().toString());
                    txtnumberRoom.setText(String.valueOf(homeStay.getNumberRoom()));
                    txtBedNum.setText(String.valueOf(homeStay.getNumberRoom()*2));
                    Utils.checkStringNull(txtBathNum, String.valueOf(homeStay.getBathRoom()));
                    txtAboutHome.setText(homestayMulti.getDescription());
                    txtprice.setText(Utils.formatPrice(homeStay.getPriceNightly()) + "/night");
                    txtTotalAmen.setText("+ "+ listAmenity.size());
                    txtTotalCul.setText("+ "+ homeStay.getHomestayCultures().size());
//                    txtTotalCul.setText("+ "+ homeStay.getHomestayCultures().get(0).getCultureService().getEnglisgName());
                    tvTotalPhoto.setText("+ "+ listHomeImg.size());
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

                    feedBackList = homeStay.getFeedBack();
                    if(feedBackList.isEmpty()){
                        layoutReview.setVisibility(View.GONE);
                        ratingBar.setVisibility(View.GONE);
                    } else
                    {
                        FeedBack feedBack = feedBackList.get(0);
                        for(FeedBack feedBack1 : feedBackList){
                            totalRate += feedBack1.getStart();
                        }
                        ratingBar.setRating(Float.valueOf(totalRate/feedBackList.size()));
                        tvDateFeedback.setText(Utils.formatDate(Utils.convertLongToDate(feedBack.getCreatedDate())));
                        tvUserEmailFb.setText(feedBack.getUserEmail());
                        tvStar.setText(String.valueOf(feedBack.getStart()));
                        tvDesReview.setText(feedBack.getComment());
                        if(feedBackList.size()<=1){
                            btnShowAllReview.setVisibility(View.GONE);
                        } else {
                            btnShowAllReview.setText("Show "+feedBackList.size()+" reviews  >>");
                        }
                    }
                    if(homeStay.getBookingMethod().equals(Constants.BOOKING_RES)){
                        btnBooking.setText("Send Inquiry");
                        iconBooking.setImageResource(R.drawable.ic_confirm);
                        tvTypeBooking.setText("CONFIRMATION");
                    } else {
                        btnBooking.setText("Book Now");
                        iconBooking.setImageResource(R.drawable.ic_booknow);
                        tvTypeBooking.setText("INSTANT");
                    }


                } else {
                    Toast.makeText(ViewHomeDetailActivity.this, "No result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
            }
        });
    }

    public void loadJsonSimilarList(){

        Call<List<HomeStay>> call = mService.getListHomestayByHostId(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if(response.isSuccessful() && response.body()!=null ) {
                    homeStays = response.body();
                    horAdapter = new HorizontalListHomeAdapter(ViewHomeDetailActivity.this,homeStays);
                    rvSimilarListing.setLayoutManager(new LinearLayoutManager(ViewHomeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    rvSimilarListing.setAdapter(horAdapter);
//                    rvSimilarListing.setLayoutManager(new GridLayoutManager(ViewHomeDetailActivity.this, 2));
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
//        getMenuInflater().inflate(R.menu.menu_homedetail, menu);
        return true;
    }

    //Event when click button
    @OnClick({R.id.btnCancelPolicy, R.id.btnShowAlbumPhoto, R.id.btnRoomRate, R.id.btnAmenity,R.id.btnAddWishlist,R.id.btnCulture
    ,R.id.btnShowAllReview})
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
            case R.id.btnAddWishlist:

                wishlistService.addOrDelWishlist(Constants.USER_ID, homestayID,ViewHomeDetailActivity.this,btnAddWishlist);
                break;
            case  R.id.btnCulture:
                Intent intentCul = new Intent(ViewHomeDetailActivity.this,ShowHomeCultureActivity.class);
                intentCul.putExtra(Constants.HOMESTAY_ID,homestayID);
                startActivityForResult(intentCul,Constants.REQUEST_CODE);
                break;
            case  R.id.btnShowAllReview:
                Intent intentRv = new Intent(ViewHomeDetailActivity.this,ShowReviewActivity.class);
                intentRv.putExtra(Constants.HOMESTAY_ID,homestayID);
                startActivity(intentRv);
                break;

        }
    }


    //event when click homestay
    private void initListener() {

        horAdapter.setOnItemClickListener(new HorizontalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ViewHomeDetailActivity.this, ViewHomeDetailActivity.class);
                HomeStay homeStay = homeStays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });

    }


}
