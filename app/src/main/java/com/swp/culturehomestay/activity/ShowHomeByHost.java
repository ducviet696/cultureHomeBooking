package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.HorizontalListHomeAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowHomeByHost extends AppCompatActivity {

    @BindView(R.id.rvHomeByHost)
    RecyclerView rvHomeByHost;
    @BindView(R.id.tvHostName)
    TextView tvHostName;
    HorizontalListHomeAdapter horAdapter;
    List<HomeStay> homeStays = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_home_by_host);
        ButterKnife.bind(this);
        displayListHomeStayByHost();
    }

    @OnClick(R.id.tvBack)
    void clickBack(){
        finish();
    }
    public void displayListHomeStayByHost() {
        Intent intent = getIntent();
        String hostID = intent.getStringExtra(Constants.HOST_ID);
        Call<List<HomeStay>> call = Utils.getAPI().getListHomestayByHostId(hostID, "en");
        call.enqueue(new Callback<List<HomeStay>>() {
            @Override
            public void onResponse(Call<List<HomeStay>> call, Response<List<HomeStay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeStays = response.body();
                    tvHostName.setText(homeStays.get(0).getHostEmail());
                    horAdapter = new HorizontalListHomeAdapter(ShowHomeByHost.this, homeStays);
                    rvHomeByHost.setLayoutManager(new GridLayoutManager(ShowHomeByHost.this, 2));
                    rvHomeByHost.setAdapter(horAdapter);
                    horAdapter.notifyDataSetChanged();
                    onClickHomeStay();
                }
            }

            @Override
            public void onFailure(Call<List<HomeStay>> call, Throwable t) {

            }
        });

    }
    private void onClickHomeStay() {

        horAdapter.setOnItemClickListener(new HorizontalListHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ShowHomeByHost.this, ViewHomeDetailActivity.class);
                HomeStay homeStay = homeStays.get(position);
                intent.putExtra(Constants.HOMESTAY_ID, homeStay.getHomestayId());
                startActivity(intent);
            }
        });

    }


}
