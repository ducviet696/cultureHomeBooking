package com.swp.culturehomestay.services;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistService {

    WishlistView view;

    public WishlistService() {
    }

    public WishlistService(WishlistView view) {
        this.view = view;
    }

    public void addHomeToWishlist(Wishlist wishlist, Context context ){
        Call<Wishlist> call = Utils.getAPI().addToWishlist(wishlist);
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                Toast.makeText(context, "Add Succesfully ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Wishlist> call, Throwable t) {

            }
        });
    }

    public void deleteHomeFromWishlist(Wishlist wishlist, Context context){
        Call<Wishlist> call = Utils.getAPI().deleteWishlist(wishlist);
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                Toast.makeText(context, "Delete Succesfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Wishlist> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public boolean checkIfHomestayInCurrentUserWishList(String homestayId, List<Wishlist> wishlists){
        for (Wishlist wishlist : wishlists){
            if(wishlist.getHomestayId().equals(homestayId)){
                return true;
            }
        }
        return false;

    }

    public void displayWishList() {
//        errorLayout.setVisibility(View.GONE);
        view.showLoading();
        Call<List<Wishlist>> call = Utils.getAPI().getWishList(Constants.USER_ID, "en");
        call.enqueue(new Callback<List<Wishlist>>() {
            @Override
            public void onResponse(Call<List<Wishlist>> call, Response<List<Wishlist>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onLoadWishlistSucces(response.body());

                } else {
                    view.showErrorLayout();
                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }

                    view.showErrorMessage(
                            R.drawable.no_result,
                            "No Result",
                            "Please Try Again!\n" +
                                    errorCode);
                }
            }

            @Override
            public void onFailure(Call<List<Wishlist>> call, Throwable t) {
               view.onLoadWishlistError(t.getMessage());
            }
        });
    }
}
