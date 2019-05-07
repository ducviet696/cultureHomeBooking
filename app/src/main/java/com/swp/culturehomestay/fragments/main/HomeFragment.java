package com.swp.culturehomestay.fragments.main;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.AdvanceSearchActivity;
import com.swp.culturehomestay.activity.BookingHomeDetailActivity;
import com.swp.culturehomestay.activity.ChangeNumberOfGuestActivity;
import com.swp.culturehomestay.activity.PickDateActivity;
import com.swp.culturehomestay.activity.SearchViaMapActivity;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.HorizontalListHomeAdapter;
import com.swp.culturehomestay.adapter.VerticalListSearchAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.SearchHomeGet;
import com.swp.culturehomestay.models.SearchHomePost;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
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

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.tvCheckin)
    TextView tvCheckin;
    @BindView(R.id.tvCheckout)
    TextView tvCheckout;
    @BindView(R.id.tvTotalGuest)
    TextView tvTotalGuest;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.rvMostCulture)
    RecyclerView rvrvMostCulture;
    @BindView(R.id.rvForRick)
    RecyclerView rvForRick;
    int guest =1;
    IApi mService;
    public List<HomeStay> homeStays = new ArrayList<>();
    public List<HomeStay> homeStays2 = new ArrayList<>();
    private HorizontalListHomeAdapter horAdapter;
    private HorizontalListHomeAdapter searchAdapter;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Date> listDateBooking = new ArrayList<>();
    public static final int INDEX_PAGE = 0;
    public static final int SIZE_PAGE = 10;
    public static final String ORDER_BY_RATE = "rate";
    public static final String ORDER_BY_PRICE = "price";
    public static final String ASC_DESC = "desc";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mService = Utils.getAPI();
        swipeRefreshLayout.setOnRefreshListener(this);
        displayMostPopularity();
        displayHomestayForRickList();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.REQUEST_CODE_HOME_FRAGMENT){
            if(resultCode==RESULT_OK) {
                listDateBooking = (List<Date>)data.getSerializableExtra(Constants.LIST_DATE_BOOKING);
                tvCheckin.setText(Utils.formatDayOfWeekFull(listDateBooking.get(0)));
                tvCheckout.setText(Utils.formatDayOfWeekFull(listDateBooking.get(listDateBooking.size()-1)));
                tvCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                tvCheckout.setTextColor(getResources().getColor(R.color.colorBlack));
            } else if(resultCode==Constants.RESULT_CODE_CHANGE_GUEST) {
                guest = data.getIntExtra(Constants.GUEST,1);
                tvTotalGuest.setText(guest+" Guest(s)");
            }
        }
    }

    @OnClick({R.id.tvCheckin,R.id.tvCheckout,R.id.tvTotalGuest, R.id.btnSearch})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tvCheckin:
                onCheckInOutClick();
                break;
            case R.id.tvCheckout:
                onCheckInOutClick();
                break;
            case R.id.tvTotalGuest:
                onChangeGuestClick();
                break;
            case R.id.btnSearch:
                onSearchButtonClick();
                break;
        }

    }

    private void onSearchButtonClick() {
        Intent intentSearchAdv = new Intent(getContext(), AdvanceSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.LIST_DATE_BOOKING,(Serializable) listDateBooking);
        bundle.putInt(Constants.GUEST,guest);
        bundle.putString(Constants.ACTIVITY_NAME,Constants.HOME_FRAGMENT);
        intentSearchAdv.putExtra(Constants.BUNDLE,bundle);
        startActivity(intentSearchAdv);
    }

    private void onChangeGuestClick() {
        Intent intentGuest = new Intent(getContext(), ChangeNumberOfGuestActivity.class);
        Bundle bundleGuest = new Bundle();
        bundleGuest.putInt("Min",1);
        bundleGuest.putInt("Max",20);
        bundleGuest.putInt(Constants.GUEST,guest);
        bundleGuest.putString(Constants.ACTIVITY_NAME,Constants.HOME_FRAGMENT);
        intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
        startActivityForResult(intentGuest,Constants.REQUEST_CODE_HOME_FRAGMENT);
    }

    private void onCheckInOutClick() {
        Intent intentDate = new Intent(getContext(), PickDateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ACTIVITY_NAME, Constants.HOME_FRAGMENT);
        bundle.putSerializable(Constants.LIST_DATE_BOOKING, (Serializable) listDateBooking);
        intentDate.putExtra(Constants.BUNDLE, bundle);
        startActivityForResult(intentDate, Constants.REQUEST_CODE_HOME_FRAGMENT);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    public void displayMostPopularity() {
        SearchHomePost searchHomePost = new SearchHomePost(INDEX_PAGE,SIZE_PAGE,ORDER_BY_RATE,ASC_DESC, "ac");
        Log.d("listDateBooking", "SearchHomePost : " + searchHomePost.toString());
        Call<SearchHomeGet> call = Utils.getAPI().getHomeBySearch(searchHomePost, Constants.LANG);
        call.enqueue(new Callback<SearchHomeGet>() {
            @Override
            public void onResponse(Call<SearchHomeGet> call, Response<SearchHomeGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                        List<HomeStay> homeStays = response.body().getHomeStayList();
                        Log.d("homeStays", "onResponse: " + response.body());
                        searchAdapter = new HorizontalListHomeAdapter(getContext(), homeStays);
                        rvrvMostCulture.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        rvrvMostCulture.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                        onClickHomestay(homeStays);
                }
            }

            @Override
            public void onFailure(Call<SearchHomeGet> call, Throwable t) {
                Log.d("homeStays", "onFailure: "+t.getMessage());
            }
        });

    }


    public void displayHomestayForRickList() {

        SearchHomePost searchHomePost = new SearchHomePost(INDEX_PAGE,SIZE_PAGE,ORDER_BY_PRICE,ASC_DESC, "ac");
        Log.d("listDateBooking", "SearchHomePost : " + searchHomePost.toString());
        Call<SearchHomeGet> call = Utils.getAPI().getHomeBySearch(searchHomePost, Constants.LANG);
        call.enqueue(new Callback<SearchHomeGet>() {
            @Override
            public void onResponse(Call<SearchHomeGet> call, Response<SearchHomeGet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HomeStay> homeStays = response.body().getHomeStayList();
                    searchAdapter = new HorizontalListHomeAdapter(getContext(), homeStays);
                    rvForRick.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    searchAdapter.notifyDataSetChanged();
                    rvForRick.setAdapter(searchAdapter);
                    onClickHomestay(homeStays);
                }
            }

            @Override
            public void onFailure(Call<SearchHomeGet> call, Throwable t) {

            }
        });

    }

    //event when click homestay
    private void onClickHomestay(List<HomeStay> homeStays) {

        searchAdapter.setOnItemClickListener(new HorizontalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ViewHomeDetailActivity.class);
                HomeStay homeStay = homeStays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        displayMostPopularity();
        displayHomestayForRickList();
    }

}
