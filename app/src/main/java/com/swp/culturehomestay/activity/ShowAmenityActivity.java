package com.swp.culturehomestay.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.Amenity;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.services.ApiClient;
import com.swp.culturehomestay.services.IApi;
import com.swp.culturehomestay.utils.AmenityCollection;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.EnumAmenity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowAmenityActivity extends AppCompatActivity {

    @BindView(R.id.lvAmen)
    ListView lvAmen;
    String homestaysID;
    public List<Amenity> amenityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_amenity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        homestaysID  = intent.getStringExtra(Constants.HOMESTAY_ID);
        Log.d("id", "onCreate: "+homestaysID);
        loadJson(homestaysID);
        Log.d("size", "size: "+ amenityList.size());
//        amenityList = intent.getStringArrayListExtra("listAmen");
//        Toast.makeText(this, String.valueOf(listAmen.size() ), Toast.LENGTH_SHORT).show();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listAmen);

    }
    @OnClick(R.id.tvBack)
    public void onClickBack()
    {
        finish();
    }

    //get homestay
    public void loadJson(String homestaysID) {
        IApi iApi = ApiClient.getApiClient().create(IApi.class);
        Call<HomeStay> call = iApi.getHomeById(homestaysID,"en");
        call.enqueue(new Callback<HomeStay>() {
            @Override
            public void onResponse(Call<HomeStay> call, Response<HomeStay> response) {
                if(response.isSuccessful() && response.body()!= null) {
                    amenityList = response.body().getAmenities();
                    Log.d("Fail", "onFailure: " + amenityList.size());
                    AmenityAdapter adapter = new AmenityAdapter(ShowAmenityActivity.this,R.layout.amenity_cus, amenityList);
                    lvAmen.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<HomeStay> call, Throwable t) {
                Log.d("Fail", "onFailure: "+t.getMessage());

            }
        });
    }


    //custom adapter
    public class AmenityAdapter extends ArrayAdapter<Amenity> {
        private Context context;
        private int resource;
        private List<Amenity> amenityList1;

        public AmenityAdapter(Context context, int resource, List<Amenity> amenityList) {
            super(context, resource, amenityList);
            this.context = context;
            this.resource = resource;
            this.amenityList1 = amenityList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.amenity_cus,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.txtAmenName = convertView.findViewById(R.id.tvAmenName);
                viewHolder.txtTypeAmen = convertView.findViewById(R.id.tvtypeAmenity);
                viewHolder.ivIcon = convertView.findViewById(R.id.ivIcon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Amenity amenity = amenityList1.get(position);
//            viewHolder.txtTypeAmen.setText(amenity.getType());
            viewHolder.txtTypeAmen.setVisibility(View.GONE);
            viewHolder.txtAmenName.setText(amenity.getEnglishName());
            viewHolder.ivIcon.setImageResource(AmenityCollection.put2Map().get(amenity.getEnglishName()));
//            viewHolder.ivIcon.setLayoutParams(new LinearLayout.LayoutParams(20,20));

            return convertView;
        }
        public class ViewHolder {
            TextView txtAmenName, txtTypeAmen;
            ImageView ivIcon;

        }


    }

}

