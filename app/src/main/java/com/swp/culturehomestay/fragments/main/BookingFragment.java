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
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.BookingAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.OrderBookingModel;
import com.swp.culturehomestay.models.ReservationModel;
import com.swp.culturehomestay.models.ResultBookingHistoryModel;
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
    private TextView totalCountHistory;
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
        rv = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        rv.setLayoutManager( new LinearLayoutManager(getActivity()));
        totalCountHistory = view.findViewById(R.id.lbl_totalBooking);
        loadData();
        return view;
    }

    private void loadData(){
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<ResultBookingHistoryModel> call = iApi.getReservationsHistory("a0a82435-d293-4703-8a24-494e42609f22");
        call.enqueue(new Callback<ResultBookingHistoryModel>() {

            @Override
            public void onResponse(Call<ResultBookingHistoryModel> call, Response<ResultBookingHistoryModel> response) {
                try{
                    if (response.isSuccessful() && response.body() != null) {

                        if ((!reservationList.isEmpty())) {
                            reservationList.clear();
                        }
                        reservationList = response.body().getContent();
                        adapter = new BookingAdapter(reservationList);
                        rv.setAdapter(adapter);
                        totalCountHistory.setText("Total booking: "+reservationList.size());
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
                        Log.d("Booking", errorCode);
                    }
                }catch (Exception e){
                    Log.d("Booking", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<ResultBookingHistoryModel> call, Throwable t) {
                Log.d("Booking", t.getMessage());
            }
        });
    }
}
