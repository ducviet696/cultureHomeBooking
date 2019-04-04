package com.swp.culturehomestay.fragments.main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.BookingAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.OrderBookingModel;
import com.swp.culturehomestay.models.SearchBody;
import com.swp.culturehomestay.services.HomeStayService;
import com.swp.culturehomestay.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        OrderBookingModel obm = new OrderBookingModel();
        List<OrderBookingModel> orders = obm.createOrderRandom(5);
        RecyclerView rv = view.findViewById(R.id.my_recycler_view);
        BookingAdapter adapter = new BookingAdapter(orders);
        rv.setAdapter(adapter);
        rv.setLayoutManager( new LinearLayoutManager(getActivity()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HomeStayService hsService = retrofit.create(HomeStayService.class);
        SearchBody sb = new SearchBody(0,6,2,"","18814f86-4865-4571-b016-312fc00cecbe",1556582400000l,1556582400000l);

        Call<List<HomeStay>> res = hsService.getHomeStayById(sb);
        return view;
    }

    private class SearchHomeStay extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
        }
    }

}
