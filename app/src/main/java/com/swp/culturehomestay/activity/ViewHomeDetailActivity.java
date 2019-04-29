package com.swp.culturehomestay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import com.swp.culturehomestay.models.DateBooked;
import com.swp.culturehomestay.models.FeedBack;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomestayImage;
import com.swp.culturehomestay.models.HomestayMulti;
import com.swp.culturehomestay.models.SearchHomeGet;
import com.swp.culturehomestay.models.SearchHomePost;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.services.WishlistService;
import com.swp.culturehomestay.utils.AmenityCollection;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @BindView(R.id.tvRoomType)
    TextView tvRoomType;
    @BindView(R.id.btnHostDetail)
    LinearLayout btnHostDetail;
    HomeStay homeStay;
    String hostId, bookingMethod, roomType;
    String cityId ="";
    int maxRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_home_detail);
        // Bind widget
        ButterKnife.bind(this);
        Constants.cultureIdList = new ArrayList<>();
        mService = Utils.getAPI();
        wishlistService = new WishlistService();
        Intent intent = getIntent();
        homestayID = intent.getStringExtra(Constants.HOMESTAY_ID);
        Log.d("homestayID", "onCreate: "+homestayID);
        loadHomeStayById(homestayID);
//        loadHomeBySearch(cityId);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // event when clicked Check Now btn
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookingMethod.equals(Constants.BOOKING_RES)){
                    showDialogReqBook();
                } else {
                    startPickDateActivity();
                }
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

    private void loadHomeStayById(String homestayID) {
        Call<HomeStay> call = mService.getHomeById(homestayID,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    homeStay = response.body();
                    cityId =  homeStay.getAddress().getCityId();
                    loadJsonSimilarList(cityId);
                    maxRoom = homeStay.getNumberRoom();
                    roomType = homeStay.getRoomType();
                    listHomeImg = homeStay.getHomestayImages();
                    listAmenity = homeStay.getAmenities();
                    Utils.loadImge(ViewHomeDetailActivity.this,ivHomeProfile, Constants.BASE_URLIMG+homeStay.getImageProfileUrl());
                    HomestayMulti homestayMulti = homeStay.getHomestayMultis().get(0);
                    String homestayName = homestayMulti.getHomestayName();
                    CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.main_collapsing);
                    mCollapsingToolbarLayout.setTitle(homestayName);
                    hostId = homeStay.getHostId();
                    if(!Utils.checkLogin(ViewHomeDetailActivity.this)){
                        btnAddWishlist.hide();
                        btnAddWishlist.setVisibility(View.GONE);
                    } else {
                        btnAddWishlist.show();
                        wishlistService.checkIfHomestayInCurrentUserWishList(Utils.getUserId(ViewHomeDetailActivity.this),homeStay.getHomestayId(),
                                ViewHomeDetailActivity.this,btnAddWishlist,R.drawable.ic_fav_act_35dp,R.drawable.ic_favorite_border_black_24dp);
                    }

                    txtName.setText(homestayName);
                    txtType.setText(AmenityCollection.homeType().get(homeStay.getType()).toUpperCase());
                    txtBedroomNum.setText(" \u25CF "+String.valueOf(homeStay.getNumberRoom()) + " BED ROOM");
                    txtCode.setText(homeStay.getHouseCode());
                    txtLocation.setText(homeStay.getAddress().getAddressFull());
                    txtHost.setText(homeStay.getHostEmail());
                    tvRoomType.setText(homeStay.getRoomType().toUpperCase());
                    txtMaximunGuest.setText(homeStay.getMaximunGuest().toString()+ " Persons");
                    txtnumberRoom.setText(String.valueOf(homeStay.getNumberRoom() + " Rooms"));
                    txtBedNum.setText(String.valueOf(homeStay.getNumberRoom()*2) + " Beds");
                    Utils.checkStringNull(txtBathNum, String.valueOf(homeStay.getBathRoom()));
                    txtBathNum.setText(homeStay.getBathRoom()!=null? String.valueOf(homeStay.getBathRoom())+" Bathrooms": "1 Bathrooms");
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
                    bookingMethod = homeStay.getBookingMethod();
                    if(bookingMethod.equals(Constants.BOOKING_RES)){
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

    public void loadJsonSimilarList(String cityId){
        SearchHomePost searchHomePost = new SearchHomePost(0,5,1,cityId,"");
        Call<SearchHomeGet> call = mService.getHomeBySearch(searchHomePost, Constants.LANG);
        call.enqueue(new Callback<SearchHomeGet>() {
            @Override
            public void onResponse(Call<SearchHomeGet> call, Response<SearchHomeGet> response) {
                if(response.isSuccessful() && response.body()!=null ) {
                    List<HomeStay> homeStays = response.body().getHomeStayList();
                    Log.d("homeStays", "onResponse: "+ homeStays.size());
                    horAdapter = new HorizontalListHomeAdapter(ViewHomeDetailActivity.this,homeStays);
                    rvSimilarListing.setLayoutManager(new LinearLayoutManager(ViewHomeDetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    rvSimilarListing.setAdapter(horAdapter);
                    horAdapter.notifyDataSetChanged();
                    onClickHomestay(homeStays);
                }
            }

            @Override
            public void onFailure(Call<SearchHomeGet> call, Throwable t) {
                Log.d("homeStays", "onFailure: "+t.getMessage());
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
    @OnClick({R.id.btnHostDetail, R.id.btnCancelPolicy, R.id.btnShowAlbumPhoto, R.id.btnRoomRate, R.id.btnAmenity,R.id.btnAddWishlist,R.id.btnCulture
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
                intent2.putExtra(Constants.HOMESTAY_ID,homestayID);
                startActivity(intent2);
                break;
            case R.id.btnAddWishlist:
                wishlistService.addOrDelWishlist(Utils.getUserId(ViewHomeDetailActivity.this), homestayID,ViewHomeDetailActivity.this,btnAddWishlist);
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
            case R.id.btnHostDetail:
                Intent intentHost = new Intent(ViewHomeDetailActivity.this,ShowHomeByHost.class);
                intentHost.putExtra(Constants.HOST_ID,hostId);
                startActivity(intentHost);
                break;


        }
    }


    //event when click homestay
    private void onClickHomestay(List<HomeStay> homeStays) {

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
    private void showDialogReqBook(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewHomeDetailActivity.this);
        builder1.setMessage(R.string.warning);
        builder1.setTitle("Warning");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Next",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startPickDateActivity();
                        dialog.dismiss();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void startPickDateActivity() {
        Intent intent = new Intent(ViewHomeDetailActivity.this, BookingHomePickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.HOMESTAY_ID, homestayID);
        bundle.putInt("Max",maxRoom);
        bundle.putString(Constants.ACTIVITY_NAME,Constants.ADVANCE_SEARCH_ACTIVITY);
        bundle.putString("roomType",roomType);
        intent.putExtra(Constants.BUNDLE, bundle);
        startActivity(intent);
    }


}
