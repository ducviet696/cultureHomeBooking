package com.swp.culturehomestay.fragments.main;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.Interface.ILoadMore;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{


    private List<HomeStay> homestays = new ArrayList<>();
    @BindView(R.id.rvWish)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VerticalListHomeAdapter adapter;
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
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        ButterKnife.bind(this,view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        onLoadingSwipeRefresh();
        return view;

    }

    private void loadJson() {
        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<List<HomeStay>> call = iApi.getWishList(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((!homestays.isEmpty())) {
                        homestays.clear();
                    }
                    homestays = response.body();
                    adapter = new VerticalListHomeAdapter(homestays, getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);

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
            public void onFailure(Call<List<HomeStay>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "Oops..",
                        "Network failure, Please Try Again\n" +
                                t.toString());
            }
        });
    }

    //event when click homestay
    private void initListener() {

        adapter.setOnItemClickListener(new VerticalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ViewHomeDetailActivity.class);

                HomeStay homeStay = homestays.get(position);
                intent.putExtra("homestayId", homeStay.getHomestayId());
                startActivity(intent);

            }
        });

    }

    @Override
    public void onRefresh() {
        loadJson();
    }

    private void onLoadingSwipeRefresh() {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        loadJson();
                    }
                }
        );

    }

    private void showErrorMessage(int imageView, String title, String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadingSwipeRefresh();
            }
        });

    }


}
