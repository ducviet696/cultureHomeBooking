package com.swp.culturehomestay.fragments.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swp.culturehomestay.Interface.ILoadMore;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.entity.HomeStay;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {


    VerticalListHomeAdapter adapter;

    ArrayList<HomeStay> homeStays = new ArrayList<>();

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        randomData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvWish);;
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new VerticalListHomeAdapter( recyclerView, getContext(), homeStays);
        recyclerView.setAdapter(adapter);
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(homeStays.size() <= 30) {
                    homeStays.add(null);
                    adapter.notifyItemInserted(homeStays.size() -1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            homeStays.remove(homeStays.size() - 1);
                            adapter.notifyItemRemoved(homeStays.size());
                            // Random more data
                            int index = homeStays.size();
                            int end = index + 5;
                            for (int i = index; i < end; i++)
                            {
                                String name = "home" + i;
                                homeStays.add(new HomeStay(R.drawable.image_not_available, "Villa", 4, name, 500, "Hanoi"));

                            }
                            Toast.makeText(getContext(), "Add 5 homes", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();

                        }
                    }, 2000);
                } else  {
                    Toast.makeText(getContext(), "Load data completed", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;

    }
    private void randomData() {
        for (int i = 0; i < 5; i++)
        {
            String name = "Home " + i;
            homeStays.add(new HomeStay(R.drawable.st2, "Villa", 4, name, 500, "Hanoi"));
        }
    }

}
