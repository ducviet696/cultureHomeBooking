package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.CultureAdapter;
import com.swp.culturehomestay.adapter.ReviewAdapter;
import com.swp.culturehomestay.models.FeedBack;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowReviewActivity extends AppCompatActivity {

    @BindView(R.id.rvReview)
    RecyclerView rvReview;
    @BindView(R.id.ratingBar2)
    RatingBar ratingBar;
    List<FeedBack> feedBackList;
    int totalRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick(R.id.tvBack)
    void onClose(){
        finish();
    }
    public void getData() {
        Intent intent = getIntent();
        String homestayId = intent.getStringExtra(Constants.HOMESTAY_ID);
        Call<HomeStay> call = Utils.getAPI().getHomeById(homestayId,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null){
                    HomeStay homeStay = response.body();
                    feedBackList = homeStay.getFeedBack();
                    for(FeedBack feedBack : feedBackList){
                        totalRate += feedBack.getStart();
                    }
                    ratingBar.setRating(Float.valueOf(totalRate/feedBackList.size()));
                    ReviewAdapter adapter = new ReviewAdapter(feedBackList,ShowReviewActivity.this);
                    rvReview.setLayoutManager(new LinearLayoutManager(ShowReviewActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvReview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {

            }
        });
    }

}
