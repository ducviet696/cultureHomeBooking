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
        wishlistService = new WishlistService(this,getContext());
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        onLoadingSwipeRefresh();
        return view;

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


        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);
        btnRetry.setVisibility(View.GONE);

    }

    public void showMessageNotLogin(int imageView, String title, String message) {

        showErrorLayout();
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
//        Constants.wishlists.clear();
        if ((!wishLists.isEmpty())) {
            showSwipeLayout();
            adapter = new VerticalListHomeAdapter(wishLists, getContext(),WishlistFragment.this, wishlistService);
            recyclerView.setAdapter(adapter);
            initListener(wishLists);
            adapter.notifyDataSetChanged();

//            Constants.wishlists  = wishLists;
        } else {
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
                "Error\n" +
                        mess);
        Log.d("WiL", "onLoadWishlistError: "+mess);
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

}
