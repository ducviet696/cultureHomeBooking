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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.AutoCompleteSearchAdapter;
import com.swp.culturehomestay.adapter.VerticalListSearchAdapter;
import com.swp.culturehomestay.models.AutocompleteBean;
import com.swp.culturehomestay.models.ContentHomeName;
import com.swp.culturehomestay.models.HomeNameGet;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.SearchHomeGet;
import com.swp.culturehomestay.models.SearchHomePost;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    public static final int SIZE_PAGE = 20;
    public static final int NUM_BETH_ROOM = 0;
    @BindView(R.id.edSearch)
    AutoCompleteTextView edSearch;
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
    int room = 1;
    int maxPrice = Constants.MAX_PRICE;
    int minPrice = Constants.MIN_PRICE;
    String bookingMethod = "";
    String cityId, districtId, fullText;
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    ArrayList<Integer> amenityIdList = new ArrayList<>();
    ArrayList<String> homeTypeList = new ArrayList<>();
    ArrayList<String> roomTypeList = new ArrayList<>();
    VerticalListSearchAdapter listSearchAdapter;
    ArrayList<String> listSearch = new ArrayList<>();
    List<AutocompleteBean> results = new ArrayList<AutocompleteBean>();
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;
    @BindView(R.id.errorImage)
    ImageView errorImage;
    @BindView(R.id.errorTitle)
    TextView errorTitle;
    @BindView(R.id.errorMessage)
    TextView errorMessage;
    @BindView(R.id.btnRetry)
    Button btnRetry;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ADVANCE_SEARCH) {
            if (resultCode == Constants.RESULT_CODE_CHANGE_ROOM) {
                onChangeRoomResult(data);
                loadHomeBySearch();
            } else if (resultCode == Constants.RESULT_CODE_CHANGE_GUEST) {
                onChangeGuestResult(data);
                loadHomeBySearch();
            } else if (resultCode == RESULT_OK) {
                onChangeDateResult(data);
                loadHomeBySearch();
            } else if (resultCode == Constants.RESULT_CODE_CHANGE_FILTER) {
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
        listDateBooking = (List<Date>) data.getSerializableExtra(Constants.LIST_DATE_BOOKING);
        dateFilter(listDateBooking);
    }

    private void dateFilter(List<Date> listDateBooking) {
        if (listDateBooking != null && !listDateBooking.isEmpty()) {
            btnDate.setTextColor(getResources().getColor(R.color.colorWhite));
            btnDate.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
            String dStart = Utils.formatDateShort(listDateBooking.get(0));
            String dEnd = Utils.formatDateShort(listDateBooking.get(listDateBooking.size() - 1));
            Date dateStart = listDateBooking.get(0);
            Date dateEnd = listDateBooking.get(listDateBooking.size() - 1);
            if (listDateBooking.size() > 1) {
                if (dateStart.getMonth() == dateEnd.getMonth()) {
                    btnDate.setText(String.format("%s - %s", dStart
                            , new SimpleDateFormat("dd").format(dateEnd)));
                } else {
                    btnDate.setText(String.format("%s - %s", dStart, dEnd));
                }

            } else {
                btnDate.setText(String.format("%s", dStart));
            }
        } else {
            buttonDefault(btnDate, "Date");
        }
    }

    private void buttonDefault(@NonNull Button btn, String btnText) {
        btn.setTextColor(getResources().getColor(R.color.colorBlack));
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
        btn.setText(btnText);
    }

    private void onChangeGuestResult(@Nullable Intent data) {
        guest = data.getIntExtra(Constants.GUEST, 1);
        guestFilter(guest);
    }

    private void guestFilter(int guest) {
        if (guest > 1) {
            btnGuest.setTextColor(getResources().getColor(R.color.colorWhite));
            btnGuest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
            btnGuest.setText(guest + " Guests");
        } else {
            buttonDefault(btnGuest, "Guest");
        }
    }

    private void onChangeRoomResult(@Nullable Intent data) {
        room = data.getIntExtra("totalRoom", 0);
        if (room != 1) {
            btnBedroom.setTextColor(getResources().getColor(R.color.colorWhite));
            btnBedroom.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f4511e")));
            btnBedroom.setText(room + " Bedrooms");
        } else {
            buttonDefault(btnBedroom, "Bedroom");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        ButterKnife.bind(this);
        getDataFromPreActivity();
        getDataForSearch();
        loadHomeBySearch();

    }

    private void getDataFromPreActivity() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.BUNDLE);
        String previousActivity = bundle.getString(Constants.ACTIVITY_NAME);
        if (previousActivity.equals(Constants.HOME_FRAGMENT)) {
            guest = bundle.getInt(Constants.GUEST);
            guestFilter(guest);
            listDateBooking = (List<Date>) bundle.getSerializable(Constants.LIST_DATE_BOOKING);
            dateFilter(listDateBooking);
        }

    }

    void getDataForSearch() {
        String[] cities = getResources().getStringArray(R.array.cities);
        String[] districts = getResources().getStringArray(R.array.districts);
        for (String city : cities) {
            AutocompleteBean adding = new AutocompleteBean();
            adding.setValue(city);
            adding.setTitle(city + ", Việt Nam");
            adding.setGroup("City");
            results.add(adding);
        }
        for (String district : districts) {
            AutocompleteBean adding = new AutocompleteBean();
            adding.setValue(district.trim());
            adding.setTitle(district.trim());
            adding.setGroup("District");
            results.add(adding);
//            listSearch.add("Quận "+district+", Việt Nam");
        }
        Call<HomeNameGet> call = Utils.getAPI().getAllHomestayName(Constants.LANG, "", 100);
        call.enqueue(new Callback<HomeNameGet>() {
            @Override
            public void onResponse(Call<HomeNameGet> call, Response<HomeNameGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    HomeNameGet homeNameGet = response.body();
                    Log.d("HomeNameGet", "onResponse: " + homeNameGet.getCode());
                    if (homeNameGet.getCode().equals(Constants.CODE_OK)) {
                        List<ContentHomeName> contentHomeNames = homeNameGet.getContent();
                        for (ContentHomeName contentHomeName : contentHomeNames) {
                            AutocompleteBean adding = new AutocompleteBean();
                            adding.setValue(contentHomeName.getHomestayId());
                            adding.setTitle(contentHomeName.getHomestayMultis().get(0).getHomestayName());
                            adding.setGroup("Homestay");
                            results.add(adding);
                        }
                    }
                    AutoCompleteSearchAdapter adapter = new AutoCompleteSearchAdapter(AdvanceSearchActivity.this, results);
                    edSearch.setAdapter(adapter);
                    edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            switch (actionId) {
                                case EditorInfo.IME_ACTION_SEARCH:
                                    fullText = edSearch.getText().toString();
                                    cityId="";
                                    districtId="";
                                    loadHomeBySearch();
                                    break;
                            }
                            return false;
                        }
                    });
                    edSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (results.get(position).getGroup().equals("Homestay")) {
                                Intent intent = new Intent(AdvanceSearchActivity.this, ViewHomeDetailActivity.class);
                                intent.putExtra(Constants.HOMESTAY_ID, results.get(position).getValue());
                                startActivity(intent);
                            } else if (results.get(position).getGroup().equals("District")) {
                                districtId = results.get(position).getValue();
                                fullText ="";
                                loadHomeBySearch();
                            } else if (results.get(position).getGroup().equals("City")) {
                                cityId = results.get(position).getValue();
                                fullText ="";
                                loadHomeBySearch();
                            }

                        }


                    });
                }
            }

            @Override
            public void onFailure(Call<HomeNameGet> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.tvBack, R.id.btnDate, R.id.btnGuest, R.id.btnBedroom, R.id.btnFilter})
    void onClickEvent(View view) {
        switch (view.getId()) {
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
        Intent intent = new Intent(AdvanceSearchActivity.this, FilterSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("minPrice", minPrice);
        bundle.putInt("maxPrice", maxPrice);
        bundle.putIntegerArrayList(Constants.LIST_CULTURE_SELECTED, cultureIdList);
        bundle.putIntegerArrayList(Constants.LIST_AMENITY_SELECTED, amenityIdList);
        bundle.putStringArrayList(Constants.LIST_HOME_TYPE, homeTypeList);
        bundle.putStringArrayList(Constants.LIST_ROOM_TYPE, roomTypeList);
        bundle.putString(Constants.BOOKING_INS, bookingMethod);
        intent.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }

    private void onGuestClick() {
        Intent intentGuest = new Intent(AdvanceSearchActivity.this, ChangeNumberOfGuestActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min", 1);
        bundleGuest.putInt("Max", 15);
        bundleGuest.putInt(Constants.GUEST, guest);
        bundleGuest.putString(Constants.ACTIVITY_NAME, Constants.ADVANCE_SEARCH_ACTIVITY);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }

    private void onBedRoomClick() {
        Intent intentGuest = new Intent(AdvanceSearchActivity.this, ChangeNumberOfRoomActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min", 1);
        bundleGuest.putInt("Max", 15);
        bundleGuest.putInt("Room", room);
        bundleGuest.putString(Constants.ACTIVITY_NAME, Constants.ADVANCE_SEARCH_ACTIVITY);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }


    private void onDateClick() {
        Intent intent = new Intent(AdvanceSearchActivity.this, PickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME, Constants.ADVANCE_SEARCH_ACTIVITY);
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        intent.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADVANCE_SEARCH);
    }

    public void showErrorMessage(int imageView, String title, String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHomeBySearch();
            }
        });

    }

    public void loadHomeBySearch() {
//        fullText = edSearch.getText().toString();
        String dStart = null;
        String dEnd = null;
        if (listDateBooking != null && !listDateBooking.isEmpty()) {
            dStart = listDateBooking.get(0).getTime() + "";
            dEnd = listDateBooking.get(listDateBooking.size() - 1).getTime() + "";
        }
        Log.d("listDateBooking", "loadHomeBySearch: dStart: " + dStart + ", đEnd: " + dEnd);
//        SearchHomePost searchHomePost = new SearchHomePost(INDEX_PAGE, SIZE_PAGE,1,"","");
        SearchHomePost searchHomePost = new SearchHomePost(dStart, dEnd, bookingMethod, homeTypeList, roomTypeList, NUM_BETH_ROOM, amenityIdList, cultureIdList, guest, minPrice, maxPrice, INDEX_PAGE, SIZE_PAGE, room, cityId, districtId, fullText, "ac");
        Log.d("listDateBooking", "SearchHomePost : " + searchHomePost.toString());
        Call<SearchHomeGet> call = Utils.getAPI().getHomeBySearch(searchHomePost, Constants.LANG);
        call.enqueue(new Callback<SearchHomeGet>() {
            @Override
            public void onResponse(Call<SearchHomeGet> call, Response<SearchHomeGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HomeStay> homeStays = response.body().getHomeStayList();
                    if (homeStays.size() == 0) {
                        showErrorLayout();
                        btnRetry.setVisibility(View.GONE);
                        showErrorMessage(R.drawable.empty_cart, "Empty", "Not maching rooms found");
                    } else {
                        showSuccesLayout();
                        Log.d("homeStays", "onResponse: " + homeStays.size());
                        listSearchAdapter = new VerticalListSearchAdapter(homeStays, AdvanceSearchActivity.this);
                        rvListHomeSearch.setLayoutManager(new LinearLayoutManager(AdvanceSearchActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvListHomeSearch.setAdapter(listSearchAdapter);
                        listSearchAdapter.notifyDataSetChanged();
                        onClickHomestay(homeStays);
                    }
                } else {
                    showErrorLayout();
                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n" +
                                    errorCode);

                }
            }

            @Override
            public void onFailure(Call<SearchHomeGet> call, Throwable t) {
                showErrorLayout();
                showErrorMessage(
                        R.drawable.no_result,
                        "No Result",
                        "Please Try Again!\n" +
                                t.getMessage());
            }
        });

    }

    private void showErrorLayout() {
        rvListHomeSearch.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    private void showSuccesLayout() {
        rvListHomeSearch.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
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
