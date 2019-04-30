package com.swp.culturehomestay.fragments.main;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.LoginActivity;
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
import com.swp.culturehomestay.utils.Utils;

import org.w3c.dom.Text;

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
    private RelativeLayout booking_frag;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle;
    private TextView errorMessage;
    private Button btnRetry;
    private Context context;
    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        context = view.getContext();
        booking_frag = (RelativeLayout) view.findViewById(R.id.booking_frag);
        errorLayout = (RelativeLayout) view.findViewById(R.id.errorLayout);
        errorImage = (ImageView) view.findViewById(R.id.errorImage);
        errorTitle = (TextView) view.findViewById(R.id.errorTitle);
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);
        btnRetry = (Button) view.findViewById(R.id.btnRetry);
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
        if (Utils.checkLogin(context)){
            IApi iApi = ApiClient.getApiClient().create(IApi.class);
            Call<ResultBookingHistoryModel> call = iApi.getReservationsHistory(Utils.getUserId(getActivity()));
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
        }else{
            showMessageNotLogin(R.drawable.sad, "Please login to see your booking history", "");
            showErrorLayout();
        }
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

    public void showErrorLayout() {
        booking_frag.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }
}
