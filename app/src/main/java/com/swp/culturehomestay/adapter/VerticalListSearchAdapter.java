package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.services.WishlistService;
import com.swp.culturehomestay.utils.AmenityCollection;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerticalListSearchAdapter extends RecyclerView.Adapter<VerticalListSearchAdapter.MyViewHolder> {

    private List<HomeStay> homeStays;
    private Context context;
    private OnItemClickListener onItemClickListener;
    WishlistService wishlistService = new WishlistService();

    public VerticalListSearchAdapter(List<HomeStay> homeStays, Context context) {
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
        if(homeStay.getBookingMethod().equals(Constants.BOOKING_RES)){
            holder.iconBooking.setImageResource(R.drawable.ic_confirm);
            holder.tvTypeBooking.setText("CONFIRMATION");
        } else {
            holder. iconBooking.setImageResource(R.drawable.ic_booknow);
            holder. tvTypeBooking.setText("INSTANT");
        }
//        holder.txtName.setText(homeStay.getHomestayMultis().get(0).getHomestayName().isEmpty()?homeStay.getHomestayMultis().get(1).getHomestayName():homeStay.getHomestayMultis().get(0).getHomestayName());
        holder.txtName.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
        holder.txtType.setText(AmenityCollection.homeType().get(homeStay.getType()).toUpperCase());
        holder.txtBedroomNum.setText(" \u25CF "+String.valueOf(homeStay.getNumberRoom()) + " BED ROOM");
        holder.txtPrice.setText(Utils.formatPrice(homeStay.getPriceNightly()));
        holder.txtLocation.setText(homeStay.getAddress().getCityId());

        if(!Utils.checkLogin(context)){
            holder.btnDeleteWl.setVisibility(View.GONE);
        } else {
            holder.btnDeleteWl.setVisibility(View.VISIBLE);
            wishlistService.checkIfHomestayInCurrentUserWishList(Utils.getUserId(context),homeStay.getHomestayId(),context,holder.btnDeleteWl,R.drawable.ic_favorite_black_24dp,R.drawable.ic_favorite_border_black_24dp);
            holder.btnDeleteWl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wishlistService.addOrDelWishlist(Utils.getUserId(context),homeStay.getHomestayId(),context,holder.btnDeleteWl,R.drawable.ic_favorite_black_24dp,R.drawable.ic_favorite_border_black_24dp);
                }
            });
        }


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
        @BindView(R.id.btnDeleteWl)
        ImageView btnDeleteWl;
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
        @BindView(R.id.iconBooking)
        ImageView iconBooking;
        @BindView(R.id.typeBooking)
        TextView tvTypeBooking;
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



