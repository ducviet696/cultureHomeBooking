package com.swp.culturehomestay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.activity.ViewHomeDetailActivity;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.services.WishlistService;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorizontalListHomeAdapter extends RecyclerView.Adapter<HorizontalListHomeAdapter.MyViewHolder> {
    Context context;
    List<HomeStay> listHomestay;
    List<Wishlist> listWishlist = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    WishlistService wishlistService = new WishlistService();

    public HorizontalListHomeAdapter(Context context, List<HomeStay> listHomestay) {
        this.context = context;
        this.listHomestay = listHomestay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.horizontal_list_home_layout,viewGroup,false);
        loadWishlist(context);
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
//        Log.d("Wishlist", "Wishlist: "+String.valueOf(listWishlist.size()));
//        loadWishlist(context);

        //EDM
        if(wishlistService.checkIfHomestayInCurrentUserWishList(homeStay.getHomestayId())){
            holder.btnDelete.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.btnDelete.setImageResource(R.drawable.ic_favorite_border_black_24dp);}
        Log.d("WL", "Wiistlist: "+Constants.wishlists.size());
//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(wishlistService.checkIfHomestayInCurrentUserWishList(homeStay.getHomestayId())){
//                    wishlistService.deleteHomeFromWishlist(new Wishlist(Constants.USER_ID, homeStay.getHomestayId()),context);
//                    holder.btnDelete.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                    Log.d("WL", "Wiistlistdel: "+Constants.wishlists.size());
//                } else {
//                    wishlistService.addHomeToWishlist(new Wishlist(Constants.USER_ID, homeStay.getHomestayId()),context);
//                    holder.btnDelete.setImageResource(R.drawable.ic_favorite_black_24dp);
//                    Log.d("WL", "Wiistlistadd: "+Constants.wishlists.size());
//                }
//
//            }
//        });

        // End EDM

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistService.addOrDelWishlist(Constants.USER_ID,homeStay.getHomestayId(),context,holder.btnDelete,R.drawable.ic_favorite_black_24dp,R.drawable.ic_favorite_border_black_24dp);

            }
        });

    }

    //load Wishlist(){
    public void loadWishlist(Context context){
        Call<List<Wishlist>> call = Utils.getAPI().getWishList(Constants.USER_ID,"en");
        call.enqueue(new Callback<List<Wishlist>>() {
            @Override
            public void onResponse(Call<List<Wishlist>> call, Response<List<Wishlist>> response) {
                Constants.wishlists = response.body();
            }

            @Override
            public void onFailure(Call<List<Wishlist>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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
        @BindView(R.id.btnDelete)
        ImageView btnDelete;
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
