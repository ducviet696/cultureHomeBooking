package com.swp.culturehomestay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.adapter.CultureAdapter;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomestayCulture;
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

public class ShowHomeCultureActivity extends AppCompatActivity {


    @BindView(R.id.rvCulture)
    RecyclerView rvCul;
    @BindView(R.id.tvTotalCul)
    TextView tvTotalCul;
    @BindView(R.id.tvTittle)
    TextView tvTittle;
    @BindView(R.id.layoutNotEmpty)
    LinearLayout layoutNotEmpty;
    @BindView(R.id.layoutEmpty)
    LinearLayout layoutEmpty;
    ArrayList<Integer> cultureIdList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_home_culture);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick(R.id.tvBack)
    void onClose(){
        finish();
    }

    @OnClick(R.id.btnSave)
    void onSave(){
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(Constants.CULTURE_ID_LIST,cultureIdList);
        setResult(RESULT_OK,intent);
        finish();
        Log.d("cultureIdList", "cultureIdList finish: " + cultureIdList);
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
                    List<HomestayCulture> homestayCultureList =  homeStay.getHomestayCultures();
                    if(homestayCultureList.isEmpty()){
                        layoutNotEmpty.setVisibility(View.GONE);
                        layoutEmpty.setVisibility(View.VISIBLE);
                    } else {

                        layoutNotEmpty.setVisibility(View.VISIBLE);
                        layoutEmpty.setVisibility(View.GONE);
                        tvTotalCul.setText("("+ homestayCultureList.size()+")");
                        CultureAdapter adapter = new CultureAdapter(homestayCultureList,ShowHomeCultureActivity.this,R.layout.item_culture);
                        rvCul.setLayoutManager(new LinearLayoutManager(ShowHomeCultureActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvCul.setAdapter(adapter);
                        adapter.setCheckboxCheckedListener(new CultureAdapter.CheckboxCheckedListener() {
                            @Override
                            public void onCheckboxClick(int position) {
                                String cultureServiceId = homestayCultureList.get(position).getCultureServiceId();
                                if (cultureIdList.contains(cultureServiceId)) {
                                    cultureIdList.remove(cultureServiceId);
                                } else {
                                    cultureIdList.add(Integer.valueOf(cultureServiceId));
                                }
                                Log.d("cultureIdList", "onClick: " + cultureIdList);
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
                Toast.makeText(ShowHomeCultureActivity.this, "Fail", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
