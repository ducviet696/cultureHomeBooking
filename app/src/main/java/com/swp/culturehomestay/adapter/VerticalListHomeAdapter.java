package com.swp.culturehomestay.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.Interface.ILoadMore;
import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.entity.HomeStay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View view) {
        super(view);
        progressBar = view.findViewById(R.id.proBar);
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {
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
    @BindView(R.id.home_layout)
    CardView home_layout;

    public ItemViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

public class VerticalListHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadMore loadMore;
    boolean isLoading;
    //    int visibleThreshold = 1;
    int lastVisibleItem, totalItemCount;
    ArrayList<HomeStay> homeStays;
    Context context;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public VerticalListHomeAdapter(RecyclerView recyclerView, Context context, ArrayList<HomeStay> homeStays) {
        this.context = context;
        this.homeStays = homeStays;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= lastVisibleItem + 1) {
                    if (loadMore != null) {
                        loadMore.onLoadMore();
                        isLoading = true;
                    }

                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return homeStays.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }


    public VerticalListHomeAdapter(ArrayList<HomeStay> homeStays, Context context) {
        this.homeStays = homeStays;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        if (i == VIEW_TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.vertical_list_home_layout, viewGroup, false);
            final ItemViewHolder holder = new ItemViewHolder(itemView);
            holder.home_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    HomeStay home = homeStays.get(position);
                    Intent sender = new Intent(context, ViewHomeDetailActivity.class);
                    sender.putExtra("Name",home.getNameHome());
                    context.startActivity(sender);
                    Toast.makeText(context, "String: " + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            });
            return holder;
        } else if (i == VIEW_TYPE_LOADING) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.item_loading, viewGroup, false);
            return new LoadingViewHolder(itemView);
        }
        return null;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            HomeStay homeStay = homeStays.get(i);
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            holder.ivHome.setImageResource(homeStays.get(i).getImgUrl());
            holder.txtName.setText(homeStays.get(i).getNameHome());
            holder.txtType.setText(homeStays.get(i).getType() + " - ");
            holder.txtBedroomNum.setText(String.valueOf(homeStays.get(i).getBedRoomNumber()) + " Bed Room");
            holder.txtPrice.setText(String.valueOf(homeStays.get(i).getPrice()));
            holder.txtLocation.setText(homeStays.get(i).getLocation());
        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return homeStays.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}


