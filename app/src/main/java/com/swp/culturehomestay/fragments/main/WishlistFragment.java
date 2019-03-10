package com.swp.culturehomestay.fragments.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.VerticalListHomeAdapter;
import com.swp.culturehomestay.entity.HomeStay;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {


    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        //view.findViewById(R.id.rvWish);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvWish);;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<HomeStay> homeStays = new ArrayList<>();
        homeStays.add(new HomeStay(R.drawable.homestay, "Villa", 4, "HomeStay one", 500, "Hanoi"));
        homeStays.add(new HomeStay(R.drawable.st2, "Villa", 7, "HomeStay two", 1000, "Thach That, Hoa Lac"));
        homeStays.add(new HomeStay(R.drawable.homestay, "Villa", 5, "Vina Taba Vil", 200, "FPT University"));
        VerticalListHomeAdapter adapter = new VerticalListHomeAdapter( homeStays, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
