package com.swp.culturehomestay.fragments.main;



import android.app.AlertDialog;
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
import com.swp.culturehomestay.activity.LoginActivity;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.services.WishlistService;
import com.swp.culturehomestay.services.WishlistView;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener, WishlistView {


    private List<HomeStay> homestays = new ArrayList<>();
    private List<Wishlist> wishLists;
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
    @BindView(R.id.topheadelines)
    TextView topHeadline;
    IApi mService;
    boolean isLogin =true;
    WishlistService wishlistService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        ButterKnife.bind(this,view);
        mService = Utils.getAPI();
        wishlistService = new WishlistService(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        if(isLogin){
            onLoadingSwipeRefresh();
        } else {
            showMessageNotLogin(R.drawable.sad,"Please login to see your wishlish","");
        }

        return view;

    }

    private void displayWishList() {

        swipeRefreshLayout.setRefreshing(true);
        Call<List<Wishlist>> call = mService.getWishList(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<Wishlist>>() {
            @Override
            public void onResponse(Call<List<Wishlist>> call, Response<List<Wishlist>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    wishLists = response.body();
//                    wishLists.clear();
                    if ((!wishLists.isEmpty())) {
                        swipeRefreshLayout.setVisibility(View.VISIBLE);

                        adapter = new VerticalListHomeAdapter(wishLists, getContext());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
//                        initListener();
                        topHeadline.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
//                        swipeRefreshLayout.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        showMessageEmpty(R.drawable.empty_cart,"Wishlist is empty", "Plase back home to choose your homestay!");
                    }

                } else {
                    swipeRefreshLayout.setVisibility(View.GONE);
                    topHeadline.setVisibility(View.INVISIBLE);
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
            public void onFailure(Call<List<Wishlist>> call, Throwable t) {
                swipeRefreshLayout.setVisibility(View.GONE);
                topHeadline.setVisibility(View.INVISIBLE);
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
    private void initListener(List<Wishlist> wishLists) {

        adapter.setOnItemClickListener(new VerticalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ViewHomeDetailActivity.class);
                HomeStay homeStay = wishLists.get(position).getHomestay();
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });


    }



    @Override
    public void onRefresh() {
        wishlistService.displayWishList();
    }

    public void onLoadingSwipeRefresh() {

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        wishlistService.displayWishList();
                    }
                }
        );

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
                onLoadingSwipeRefresh();
            }
        });

    }

    public void showMessageEmpty(int imageView, String title, String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setVisibility(View.GONE);

    }

    public void showMessageNotLogin(int imageView, String title, String message) {

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setText("Login/SignUp");

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadWishlistSucces(List<Wishlist> wishLists) {
        if ((!wishLists.isEmpty())) {
//            swipeRefreshLayout.setVisibility(View.VISIBLE);
            showSwipeLayout();
            adapter = new VerticalListHomeAdapter(wishLists, getContext());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            initListener(wishLists);
//            topHeadline.setVisibility(View.VISIBLE);
//            swipeRefreshLayout.setRefreshing(false);
        } else {
//                        swipeRefreshLayout.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
            showErrorLayout();
            showMessageEmpty(R.drawable.empty_cart,"Wishlist is empty", "Plase back home to choose your homestay!");
        }

    }

    @Override
    public void onLoadWishlistError(String mess) {
        hideLoading();
        showErrorLayout();
        showErrorMessage(
                R.drawable.oops,
                "Oops..",
                "Network failure, Please Try Again\n" +
                        mess);
    }

    @Override
    public void showSwipeLayout() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        topHeadline.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);

    }


    @Override
    public void showErrorLayout() {
        swipeRefreshLayout.setVisibility(View.GONE);
        topHeadline.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.errorLayout})
    public void onLoadListAgain(){
        onLoadingSwipeRefresh();
    }

    @Override
    public void refreshWishlist() {
        onRefresh();
    }
}
