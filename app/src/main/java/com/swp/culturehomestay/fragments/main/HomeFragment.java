package com.swp.culturehomestay.fragments.main;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.BookingHomeDetailActivity;
import com.swp.culturehomestay.activity.ChangeNumberOfGuestActivity;
import com.swp.culturehomestay.activity.PickDateActivity;
import com.swp.culturehomestay.activity.SearchViaMapActivity;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.HorizontalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

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
public class HomeFragment extends Fragment {

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
    public List<Wishlist> wishlists = new ArrayList<>();
    public List<HomeStay> homeStays = new ArrayList<>();
    private HorizontalListHomeAdapter horAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mService = Utils.getAPI();
        displayMostCultureHomeStayList();
        displayHomestayForRickList();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.REQUEST_CODE_HOME_FRAGMENT){
            if(resultCode==RESULT_OK) {
                List<Date> dateList = (List<Date>)data.getSerializableExtra("dateList");
                tvCheckin.setText(Utils.formatDayOfWeekFull(dateList.get(0)));
                tvCheckout.setText(Utils.formatDayOfWeekFull(dateList.get(dateList.size()-1)));
                tvCheckin.setTextColor(getResources().getColor(R.color.colorBlack));
                tvCheckout.setTextColor(getResources().getColor(R.color.colorBlack));
            } else if(resultCode==Constants.RESULT_CODE_CHANGE_GUEST) {
                guest = data.getIntExtra("totalGuest",1);
                tvTotalGuest.setText(guest+" Guest(s)");
            }
        }
    }

    @OnClick({R.id.tvCheckin,R.id.tvCheckout,R.id.tvTotalGuest,R.id.tvSearch})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tvCheckin:
                Intent intentDate = new Intent(getContext(), PickDateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ACTIVITY_NAME,Constants.HOME_FRAGMENT);
                intentDate.putExtra(Constants.BUNDLE, bundle);
                startActivityForResult(intentDate, Constants.REQUEST_CODE_HOME_FRAGMENT);
                break;
            case R.id.tvCheckout:
                Intent intentDate2 = new Intent(getContext(), PickDateActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString(Constants.ACTIVITY_NAME,Constants.HOME_FRAGMENT);
                intentDate2.putExtra(Constants.BUNDLE, bundle2);
                startActivityForResult(intentDate2, Constants.REQUEST_CODE_HOME_FRAGMENT);
                break;
            case R.id.tvTotalGuest:
                Intent intentGuest = new Intent(getContext(), ChangeNumberOfGuestActivity.class);
                Bundle bundleGuest = new Bundle();
                bundleGuest.putInt("Min",1);
                bundleGuest.putInt("Max",30);
                bundleGuest.putInt("Guest",guest);
                bundleGuest.putString(Constants.ACTIVITY_NAME,Constants.HOME_FRAGMENT);
                intentGuest.putExtra(Constants.BUNDLE, bundleGuest);
                startActivityForResult(intentGuest,Constants.REQUEST_CODE_HOME_FRAGMENT);
                break;
            case R.id.tvSearch:
                Intent intentSearch = new Intent(getContext(), SearchViaMapActivity.class);
                startActivity(intentSearch);
        }

    }
    public void displayMostCultureHomeStayList() {

        Call<List<HomeStay>> call = mService.getListHomestayByHostId(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeStays = response.body();
                    Log.d("Wishlist2", "Wishlist: " + String.valueOf(wishlists.size()));
                    horAdapter = new HorizontalListHomeAdapter(getContext(), homeStays, wishlists);
                    rvrvMostCulture.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    rvrvMostCulture.setAdapter(horAdapter);
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

    public void displayHomestayForRickList() {

        Call<List<HomeStay>> call = mService.getListHomestayByHostId(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeStays = response.body();
                    Log.d("Wishlist2", "Wishlist: " + String.valueOf(wishlists.size()));
                    horAdapter = new HorizontalListHomeAdapter(getContext(), homeStays, wishlists);
                    rvForRick.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    rvForRick.setAdapter(horAdapter);
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

    //event when click homestay
    private void initListener() {

        horAdapter.setOnItemClickListener(new HorizontalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ViewHomeDetailActivity.class);
                HomeStay homeStay = homeStays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });
    }
}
