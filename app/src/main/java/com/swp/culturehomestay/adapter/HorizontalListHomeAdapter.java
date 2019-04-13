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
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalListHomeAdapter extends RecyclerView.Adapter<HorizontalListHomeAdapter.MyViewHolder> {
    Context context;
    List<HomeStay> listHomestay;
    private OnItemClickListener onItemClickListener;

    public HorizontalListHomeAdapter(Context context, List<HomeStay> listHomestay) {
        this.context = context;
        this.listHomestay = listHomestay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_list_home_layout,viewGroup,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final MyViewHolder holder = myViewHolder;
        HomeStay homeStay = listHomestay.get(i);
        String imgeUrl = Constants.BASE_URLIMG +homeStay.getImageProfileUrl();
        Utils.loadImge(context,holder.ivHome,imgeUrl);
        holder.txtName.setText(homeStay.getHomestayMultis().get(0).getHomestayName());
        holder.txtPrice.setText(Utils.formatPrice(homeStay.getPriceNightly()));
        holder.txtLocation.setText(homeStay.getAddress().getCityId());
    }

    @Override
    public int getItemCount() {
        return listHomestay.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.ivHome)
        ImageView ivHome;
        @BindView(R.id.tvNameHome)
        TextView txtName;
        @BindView(R.id.tvLocation)
        TextView txtLocation;
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

    //Interface for click item
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
