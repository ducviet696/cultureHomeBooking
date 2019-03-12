package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomeStay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerticalListHomeAdapter extends RecyclerView.Adapter<VerticalListHomeAdapter.ViewHolder> {
    ArrayList<HomeStay> homeStays;
    Context context;

    public VerticalListHomeAdapter(ArrayList<HomeStay> homeStays, Context context) {
        this.homeStays = homeStays;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.vertical_list_home_layout, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        viewHolder.ivHome.setImageResource(homeStays.get(i).getImgUrl());
        viewHolder.txtName.setText(homeStays.get(i).getNameHome());
        viewHolder.txtType.setText(homeStays.get(i).getType() + " - ");
        viewHolder.txtBedroomNum.setText(String.valueOf(homeStays.get(i).getBedRoomNumber()) + " Bed Room");
        viewHolder.txtPrice.setText(String.valueOf(homeStays.get(i).getPrice()));
        viewHolder.txtLocation.setText(homeStays.get(i).getLocation());

//        viewHolder.rvWish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HomeStay home = homeStays.get(i);
//                Intent  intent = new Intent(context, HomeDetailActivity.class);
//                intent.putExtra("homestay", (Serializable) home);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return homeStays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivHome)
        ImageView ivHome;
        @BindView(R.id.tvTypeHome)
        TextView txtType;
        @BindView(R.id.tvNameHome)
        TextView txtName;
        @BindView(R.id.tvLocation)
        TextView txtLocation;
        @BindView(R.id.BedRoomNum)
        TextView txtBedroomNum;
        @BindView(R.id.tvPricePerNight)
        TextView txtPrice;
//        RecyclerView rvWish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            rvWish = (RecyclerView) itemView.findViewById(R.id.rvWish);
        }
    }
}
