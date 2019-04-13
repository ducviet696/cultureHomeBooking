package com.swp.culturehomestay.fragments.main;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.BookingAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.OrderBookingModel;
import com.swp.culturehomestay.models.ReservationModel;
import com.swp.culturehomestay.models.SearchBody;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.HomeStayService;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {
    private List<ReservationModel> reservationList = new ArrayList<>();
    private RecyclerView rv;
    private BookingAdapter adapter;

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
        reservationList = new ArrayList<>();
        rv = view.findViewById(R.id.my_recycler_view);
        rv.setLayoutManager( new LinearLayoutManager(getActivity()));
        loadData();
        return view;
    }

    private void loadData(){
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<List<ReservationModel>> call = iApi.getReservationsHistory("8da46239-3d7c-4c40-a59c-5e7dbe22ee68");
        call.enqueue(new Callback<List<ReservationModel>>(){

            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                try{
                    if (response.isSuccessful() && response.body() != null) {

                        if ((!reservationList.isEmpty())) {
                            reservationList.clear();
                        }
                        reservationList = response.body();
                        adapter = new BookingAdapter(reservationList);
                        rv.setAdapter(adapter);
                    } else {
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
                        Log.d("DATA", errorCode);
                    }
                }catch (Exception e){
                    Log.d("DATA", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {
                Log.d("DATA", t.getMessage());
            }
        });
    }
}
