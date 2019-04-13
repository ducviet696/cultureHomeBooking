package com.swp.culturehomestay.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;

import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerticalListHomeAdapter  extends RecyclerView.Adapter<VerticalListHomeAdapter.MyViewHolder> {

    private List<HomeStay> homeStays;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public VerticalListHomeAdapter(List<HomeStay> homeStays, Context context) {
        this.homeStays = homeStays;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vertical_list_home_layout,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        HomeStay homeStay = homeStays.get(position);
        String imgeUrl = Constants.BASE_URLIMG +homeStay.getImageProfileUrl();
        Utils.loadImge(context,holder.ivHome,imgeUrl);
        holder.txtName.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
        holder.txtType.setText(homeStay.getType());
        holder.txtBedroomNum.setText(" \u25CF "+String.valueOf(homeStay.getNumberRoom()) + " Bed Room");
        holder.txtPrice.setText(Utils.formatPrice(homeStay.getPriceNightly()));
        holder.txtLocation.setText(homeStay.getAddress().getCityId());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "delete home "+ position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeStays.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivHome)
        ImageView ivHome;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
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
        @BindView(R.id.home_layout)
        CardView home_layout;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;

        }



        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }

    }
}



