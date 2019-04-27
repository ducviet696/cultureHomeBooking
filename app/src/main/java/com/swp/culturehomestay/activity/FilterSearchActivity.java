package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.gson.annotations.Until;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.AmenityFilterAdapter;
import com.swp.culturehomestay.adapter.CultureAdapter;
import com.swp.culturehomestay.adapter.CultureFilterAdapter;
import com.swp.culturehomestay.models.Amenity;
import com.swp.culturehomestay.models.CultureService;
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

public class FilterSearchActivity extends AppCompatActivity {

    @BindView(R.id.rvAllAmenity)
    RecyclerView rvAllAmenity;
    @BindView(R.id.rvAllCulture)
    RecyclerView rvAllCulture;
    @BindView(R.id.rsbPrice)
    RangeSeekBar rsbPrice;
    @BindView(R.id.tvMinPrice)
    TextView tvMinPrice;
    @BindView(R.id.tvMaxPrice)
    TextView tvMaxPrice;
    @BindView(R.id.cbHomeTypeAp)
    CheckBox cbHomeTypeAp;
    @BindView(R.id.cbHomeTypeHou)
    CheckBox cbHomeTypeHou;
    @BindView(R.id.cbHomeTypeSap)
    CheckBox cbHomeTypeSap;
    @BindView(R.id.cbHomeTypeVla)
    CheckBox cbHomeTypeVla;
    @BindView(R.id.cbRoomTypeEnt)
    CheckBox cbRoomTypeEnt;
    @BindView(R.id.cbRoomTypeSinge)
    CheckBox cbRoomTypeSinge;
    @BindView(R.id.swIsInstant)
    Switch swIsInstant;
    IApi mService;
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    ArrayList<Integer> amenityIdList = new ArrayList<>();
    ArrayList<String> homeTypeList = new ArrayList<>();
    ArrayList<String> roomTypeList = new ArrayList<>();
    int minPrice;
    int maxPrice;
    String bookingMethod="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_search);
        ButterKnife.bind(this);
        getData();
        mService = Utils.getAPI();
        loadAllCultureService();
        loadAllAmenity();
        onChangePrice();
        onChangeHomeType();
        onChangeRoomType();
        swIsInstant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!swIsInstant.isChecked()){
                    swIsInstant.setChecked(false);
                    bookingMethod = "";
                } else {
                    swIsInstant.setChecked(true);
                    bookingMethod = Constants.BOOKING_INS;
                }
            }
        });
    }

    private void onChangeRoomType() {
        onChangeHomeType(cbRoomTypeEnt, Constants.ROOMTYPE_ENTIRE, roomTypeList);
        onChangeHomeType(cbRoomTypeSinge, Constants.ROOMTYPE_SINGLE, roomTypeList);
    }

    private void onChangeHomeType() {
        onChangeHomeType(cbHomeTypeAp, Constants.HOMETYPE_AP, homeTypeList);
        onChangeHomeType(cbHomeTypeHou, Constants.HOMETYPE_ENT, homeTypeList);
        onChangeHomeType(cbHomeTypeSap, Constants.HOMETYPE_SAP, homeTypeList);
        onChangeHomeType(cbHomeTypeVla, Constants.HOMETYPE_VLA, homeTypeList);
    }

    private void onChangeHomeType(@NonNull CheckBox checkBox, String typeHome, ArrayList<String> list) {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    list.remove(typeHome);
                } else {
                    checkBox.setChecked(true);
                    list.add(typeHome);
                }
                Log.d("homeTypeList", "onClick: " + list);
            }
        });
    }

    private void onChangePrice() {
        rsbPrice.setRange(Constants.MIN_PRICE, Constants.MAX_PRICE);
        tvMinPrice.setText(Utils.formatPrice(minPrice));
        tvMaxPrice.setText(Utils.formatPrice(maxPrice));
        rsbPrice.setValue(minPrice, maxPrice);
        rsbPrice.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                minPrice = (int) leftValue;
                maxPrice = (int) rightValue;
                tvMinPrice.setText(Utils.formatPrice(minPrice));
                tvMaxPrice.setText(Utils.formatPrice(maxPrice));
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        if (bundle.getIntegerArrayList(Constants.LIST_CULTURE_SELECTED) != null) {
            cultureIdList = bundle.getIntegerArrayList(Constants.LIST_CULTURE_SELECTED);
        }
        if (bundle.getIntegerArrayList(Constants.LIST_AMENITY_SELECTED) != null) {
            amenityIdList = bundle.getIntegerArrayList(Constants.LIST_AMENITY_SELECTED);
        }
        if (bundle.getStringArrayList(Constants.LIST_HOME_TYPE) != null) {
            homeTypeList = bundle.getStringArrayList(Constants.LIST_HOME_TYPE);
            if (homeTypeList.contains(Constants.HOMETYPE_AP)) {
                cbHomeTypeAp.setChecked(true);
            }
            if (homeTypeList.contains(Constants.HOMETYPE_ENT)) {
                cbHomeTypeHou.setChecked(true);
            }
            if (homeTypeList.contains(Constants.HOMETYPE_SAP)) {
                cbHomeTypeSap.setChecked(true);
            }
            if (homeTypeList.contains(Constants.HOMETYPE_VLA)) {
                cbHomeTypeVla.setChecked(true);
            }
        }
        if (bundle.getStringArrayList(Constants.LIST_ROOM_TYPE) != null) {
            roomTypeList = bundle.getStringArrayList(Constants.LIST_ROOM_TYPE);
            if (roomTypeList.contains(Constants.ROOMTYPE_ENTIRE)) {
                cbRoomTypeEnt.setChecked(true);
            }
            if (roomTypeList.contains(Constants.ROOMTYPE_SINGLE)) {
                cbRoomTypeSinge.setChecked(true);
            }
            minPrice = bundle.getInt("minPrice");
            maxPrice = bundle.getInt("maxPrice");
            if(bundle.getString(Constants.BOOKING_INS).equals(Constants.BOOKING_INS)){
                swIsInstant.setChecked(true);
            }
        }
    }

    private void loadAllAmenity() {
        Call<List<Amenity>> call = mService.getAllAmenity();
        call.enqueue(new Callback<List<Amenity>>() {
            @Override
            public void onResponse(Call<List<Amenity>> call, Response<List<Amenity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Amenity> amenityList = response.body();
                    AmenityFilterAdapter adapter = new AmenityFilterAdapter(amenityList, amenityIdList, FilterSearchActivity.this);
                    rvAllAmenity.setLayoutManager(new LinearLayoutManager(FilterSearchActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvAllAmenity.setAdapter(adapter);
                    adapter.setCheckboxCheckedListener(new AmenityFilterAdapter.CheckboxCheckedListener() {
                        @Override
                        public void onCheckboxClick(int position) {
                            String amenityId = amenityList.get(position).getAmenityId();
                            Log.d("amenityIdList", "onCheckboxClick: " + amenityIdList + "amenityId: " + amenityId);
                            if (amenityIdList.contains(Integer.valueOf(amenityId))) {
                                amenityIdList.remove(Integer.valueOf(amenityId));
                            } else {
                                amenityIdList.add(Integer.valueOf(amenityId));
                            }
                            Log.d("amenityIdList", "onClick: " + amenityIdList);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Amenity>> call, Throwable t) {

            }
        });

    }

    private void loadAllCultureService() {
        Call<List<CultureService>> call = mService.getAllCultureService();
        call.enqueue(new Callback<List<CultureService>>() {
            @Override
            public void onResponse(Call<List<CultureService>> call, Response<List<CultureService>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CultureService> cultureServiceList = response.body();
                    CultureFilterAdapter adapter = new CultureFilterAdapter(cultureServiceList, cultureIdList, FilterSearchActivity.this);
                    rvAllCulture.setLayoutManager(new LinearLayoutManager(FilterSearchActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvAllCulture.setAdapter(adapter);
                    adapter.setCheckboxCheckedListener(new CultureFilterAdapter.CheckboxCheckedListener() {
                        @Override
                        public void onCheckboxClick(int position) {
                            String cultureServiceId = cultureServiceList.get(position).getCultureServiceId();
                            if (cultureIdList.contains(Integer.valueOf(cultureServiceId))) {
                                cultureIdList.remove(Integer.valueOf(cultureServiceId));
                            } else {
                                cultureIdList.add(Integer.valueOf(cultureServiceId));
                            }
                            Log.d("cultureIdList", "onClick: " + cultureIdList);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CultureService>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.tvClose, R.id.btnSave, R.id.btnReset})
    public void onEventClick(View view) {
        switch (view.getId()) {
            case R.id.tvClose:
                finish();
                break;
            case R.id.btnSave:
                onSaveClick();
                break;
            case  R.id.btnReset:
                onResetClick();
                break;
        }
    }

    private void onResetClick() {
        roomTypeList.clear();
        homeTypeList.clear();
        checkboxDefault();
        cultureIdList.clear();
        amenityIdList.clear();
        loadAllAmenity();
        loadAllCultureService();
        tvMinPrice.setText(Utils.formatPrice(Constants.MIN_PRICE));
        tvMaxPrice.setText(Utils.formatPrice(Constants.MAX_PRICE));
        rsbPrice.setValue(Constants.MIN_PRICE, Constants.MAX_PRICE);
        swIsInstant.setChecked(false);
        bookingMethod="";

    }

    private void checkboxDefault() {
        cbHomeTypeVla.setChecked(false);
        cbHomeTypeSap.setChecked(false);
        cbHomeTypeHou.setChecked(false);
        cbHomeTypeAp.setChecked(false);
        cbRoomTypeSinge.setChecked(false);
        cbRoomTypeEnt.setChecked(false);
    }

    private void onSaveClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED, cultureIdList);
        bundle.putIntegerArrayList(Constants.LIST_AMENITY_SELECTED, amenityIdList);
        bundle.putInt("minPrice", minPrice);
        bundle.putInt("maxPrice", maxPrice);
        bundle.putStringArrayList(Constants.LIST_HOME_TYPE, homeTypeList);
        bundle.putStringArrayList(Constants.LIST_ROOM_TYPE, roomTypeList);
        bundle.putString(Constants.BOOKING_INS,bookingMethod);
        intent.putExtra(Constants.BUNDLE, bundle);
        setResult(Constants.RESULT_CODE_CHANGE_FILTER, intent);
        finish();
    }
}
