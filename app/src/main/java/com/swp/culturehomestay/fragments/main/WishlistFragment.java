package com.swp.culturehomestay.fragments.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swp.culturehomestay.Interface.ILoadMore;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {


    public static final String USER_ID ="624daf76-c874-4ff3-a6ad-caf1bacce6e6";
    private List<HomeStay> articles = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VerticalListHomeAdapter adapter;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView = view.findViewById(R.id.rvWish);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setNestedScrollingEnabled(false);
        loadJson();

        return view;

    }

    private void loadJson() {
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
//        String q = "us";
//        Date todayDate = Calendar.getInstance().getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateFrom = formatter.format(todayDate);
//        Call<HomeStay> call = iApi.getHomeStaySearch(q,dateFrom,"publishedAt",API_KEY);
//        Call<String> homeid = iApi.getHomeStay("");
        Call<List<HomeStay>> call = iApi.getWishList(USER_ID);
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    if((!articles.isEmpty())){
                        articles.clear();
                    }
                    articles = response.body();
                    adapter = new VerticalListHomeAdapter(articles, getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeStay>> call, Throwable t) {
                Toast.makeText(getContext(), "wwtf", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "onFailure: "+t.getMessage());
            }
        });
    }

}
