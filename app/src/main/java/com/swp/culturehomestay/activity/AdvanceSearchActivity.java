package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.VerticalListSearchAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.SearchHomeGet;
import com.swp.culturehomestay.models.SearchHomePost;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvanceSearchActivity extends AppCompatActivity {

    public static final int INDEX_PAGE = 0;
    public static final int SIZE_PAGE = 10;
    @BindView(R.id.edSearch)
    EditText edSearch;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.btnGuest)
    Button btnGuest;
    @BindView(R.id.btnBedroom)
    Button btnBedroom;
    @BindView(R.id.btnFilter)
    Button btnFilter;
    @BindView(R.id.rvListHomeSearch)
    RecyclerView rvListHomeSearch;
    String previousActtivity;
    List<Date> listDateBooking = new ArrayList<>();
    int guest = 1;
    int room = 0;
    int maxPrice = Constants.MAX_PRICE;
    int minPrice = Constants.MIN_PRICE;
    String bookingMethod="";
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    ArrayList<Integer> amenityIdList = new ArrayList<>();
    ArrayList<String> homeTypeList = new ArrayList<>();
    ArrayList<String> roomTypeList = new ArrayList<>();
    VerticalListSearchAdapter listSearchAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.REQUEST_CODE_ADVANCE_SEARCH) {
            if(resultCode == Constants.RESULT_CODE_CHANGE_ROOM){
                onChangeRoomResult(data);
                loadHomeBySearch();
            } else if(resultCode == Constants.RESULT_CODE_CHANGE_GUEST) {
                onChangeGuestResult(data);
                loadHomeBySearch();
            } else if(resultCode == RESULT_OK){
                onChangeDateResult(data);
                loadHomeBySearch();
            } else if(resultCode == Constants.RESULT_CODE_CHANGE_FILTER){
                onChangeFilterResult(data);
                loadHomeBySearch();
            }
        }
    }

    private void onChangeFilterResult(@Nullable Intent data) {
        btnFilter.setTextColor(getResources().getColor(R.color.colorWhite));
        btnFilter.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
        Bundle bundle = data.getBundleExtra(Constants.BUNDLE);
        minPrice = bundle.getInt("minPrice");
        maxPrice = bundle.getInt("maxPrice");
        cultureIdList = bundle.getIntegerArrayList(Constants.LIST_CULTURE_SELECTED);
        amenityIdList = bundle.getIntegerArrayList(Constants.LIST_AMENITY_SELECTED);
        homeTypeList = bundle.getStringArrayList(Constants.LIST_HOME_TYPE);
        roomTypeList = bundle.getStringArrayList(Constants.LIST_ROOM_TYPE);
        bookingMethod = bundle.getString(Constants.BOOKING_INS);
    }

    private void onChangeDateResult(@Nullable Intent data) {
        listDateBooking = (List<Date>)data.getSerializableExtra(Constants.LIST_DATE_BOOKING);
       if(listDateBooking!=null && !listDateBooking.isEmpty()){
           btnDate.setTextColor(getResources().getColor(R.color.colorWhite));
           btnDate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
           String dStart = Utils.formatDateShort(listDateBooking.get(0));
           String dEnd = Utils.formatDateShort(listDateBooking.get(listDateBooking.size()-1));
           Date dateStart = listDateBooking.get(0);
           Date dateEnd = listDateBooking.get(listDateBooking.size()-1);
           if(listDateBooking.size()>1) {
               if(dateStart.getMonth()== dateEnd.getMonth()){
                   btnDate.setText(String.format("%s - %s",dStart
                           ,new SimpleDateFormat("dd").format(dateEnd)));
               } else {
                   btnDate.setText(String.format("%s - %s",dStart,dEnd));
               }

           } else {
               btnDate.setText(String.format("%s", dStart));
           }
       } else {
           buttonDefault(btnDate,"Date");
       }
    }

    private void buttonDefault(@NonNull Button btn, String btnText) {
        btn.setTextColor(getResources().getColor(R.color.colorBlack));
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        btn.setText(btnText);
    }

    private void onChangeGuestResult(@Nullable Intent data) {
        guest = data.getIntExtra("totalGuest",1);
        if(guest>1){
            btnGuest.setTextColor(getResources().getColor(R.color.colorWhite));
            btnGuest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
            btnGuest.setText(guest+" Guests");
        } else {
            buttonDefault(btnGuest,"Guest");
        }
    }

    private void onChangeRoomResult(@Nullable Intent data) {
        room = data.getIntExtra("totalRoom",0);
        if(room!=0){
            btnBedroom.setTextColor(getResources().getColor(R.color.colorWhite));
            btnBedroom.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
            btnBedroom.setText(room+" Bedrooms");
        } else {
            buttonDefault(btnBedroom,"Bedroom");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        ButterKnife.bind(this);
        getDataFromPreActivity();
        loadHomeBySearch();
    }

    private void getDataFromPreActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        guest = bundle.getInt(Constants.GUEST);
        listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
    }
    @OnClick({R.id.tvBack, R.id.btnDate, R.id.btnGuest, R.id.btnBedroom, R.id.btnFilter})
    void onClickEvent(View view){
       switch (view.getId()){
           case R.id.tvBack:
               finish();
               break;
           case R.id.btnDate:
               onDateClick();
               break;
           case R.id.btnGuest:
               onGuestClick();
               break;
           case R.id.btnBedroom:
               onBedRoomClick();
               break;
           case R.id.btnFilter:
               onFilterClick();
               break;
       }
    }

    private void onFilterClick() {
        Intent intent = new Intent(AdvanceSearchActivity.this,FilterSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("minPrice",minPrice);
        bundle.putInt("maxPrice",maxPrice);
        bundle.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED,cultureIdList);
        bundle.putIntegerArrayList(Constants.LIST_AMENITY_SELECTED,amenityIdList);
        bundle.putStringArrayList(Constants.LIST_HOME_TYPE,homeTypeList);
        bundle.putStringArrayList(Constants.LIST_ROOM_TYPE,roomTypeList);
        bundle.putString(Constants.BOOKING_INS,bookingMethod);
        intent.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }

    private void onGuestClick() {
        Intent intentGuest = new Intent(AdvanceSearchActivity.this, ChangeNumberOfGuestActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min",1);
        bundleGuest.putInt("Max",15);
        bundleGuest.putInt("Guest",guest);
        bundleGuest.putString(Constants.ACTIVITY_NAME,Constants.ADVANCE_SEARCH_ACTIVITY);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest,Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }

    private void onBedRoomClick() {
        Intent intentGuest = new Intent(AdvanceSearchActivity.this, ChangeNumberOfRoomActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min",0);
        bundleGuest.putInt("Max",12);
        bundleGuest.putInt("Room",room);
        bundleGuest.putString(Constants.ACTIVITY_NAME,Constants.ADVANCE_SEARCH_ACTIVITY);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest,Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }


    private void onDateClick() {
        Intent intent = new Intent(AdvanceSearchActivity.this,PickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME, Constants.ADVANCE_SEARCH_ACTIVITY);
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        intent.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }
    public void loadHomeBySearch(){
        Date dStart =null;
        Date dEnd =null;
        if(listDateBooking!=null && !listDateBooking.isEmpty()){
             dStart = listDateBooking.get(0);
             dEnd = listDateBooking.get(listDateBooking.size()-1);
        }

//        SearchHomePost searchHomePost = new SearchHomePost(INDEX_PAGE, SIZE_PAGE,1,"","");
        SearchHomePost searchHomePost = new SearchHomePost(dStart, dEnd, bookingMethod, homeTypeList, roomTypeList, room, amenityIdList, cultureIdList, guest, minPrice, maxPrice, INDEX_PAGE, SIZE_PAGE,1,"","");
        Call<SearchHomeGet> call = Utils.getAPI().getHomeBySearch(searchHomePost, Constants.LANG);
        call.enqueue(new Callback<SearchHomeGet>() {
            @Override
            public void onResponse(Call<SearchHomeGet> call, Response<SearchHomeGet> response) {
                if(response.isSuccessful() && response.body()!=null ) {
                    List<HomeStay> homeStays = response.body().getHomeStayList();
                    Log.d("homeStays", "onResponse: "+ homeStays.size());
                    listSearchAdapter = new VerticalListSearchAdapter(homeStays,AdvanceSearchActivity.this);
                    rvListHomeSearch.setLayoutManager(new LinearLayoutManager(AdvanceSearchActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvListHomeSearch.setAdapter(listSearchAdapter);
                    listSearchAdapter.notifyDataSetChanged();
                    onClickHomestay(homeStays);
                }
            }

            @Override
            public void onFailure(Call<SearchHomeGet> call, Throwable t) {
                Log.d("homeStays", "onFailure: "+t.getMessage());
            }
        });

    }
    //event when click homestay
    private void onClickHomestay(List<HomeStay> homeStays) {

        listSearchAdapter.setOnItemClickListener(new VerticalListSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AdvanceSearchActivity.this, ViewHomeDetailActivity.class);
                HomeStay homeStay = homeStays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });

    }

}
