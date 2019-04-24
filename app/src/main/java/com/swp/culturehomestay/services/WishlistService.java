package com.swp.culturehomestay.services;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.swp.culturehomestay.R;
import com.swp.culturehomestay.models.CheckWL;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.utils.Constants;
import com.swp.culturehomestay.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistService {

    WishlistView view;
    List<Wishlist> wishlists = new ArrayList<>();
    Context context;

    public WishlistService() {
    }

    public WishlistService(WishlistView view, Context context) {
        this.context = context;
        this.view = view;
    }

    public void addHomeToWishlist(Wishlist wishlist, Context context) {
        Call<Wishlist> call = Utils.getAPI().addToWishlist(wishlist);
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                Constants.wishlists.add(wishlist);
                Toast.makeText(context, "Add Succesfully ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Wishlist> call, Throwable t) {

            }
        });
    }

    public void deleteHomeFromWishlist(Wishlist wishlist, Context context) {

        Call<Wishlist> call = Utils.getAPI().deleteWishlist(wishlist);
        call.enqueue(new Callback<Wishlist>() {
            @Override
            public void onResponse(Call<Wishlist> call, Response<Wishlist> response) {
                Constants.wishlists.remove(wishlist);
                Toast.makeText(context, "Delete Succesfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Wishlist> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Using for Horizontal Adapter
    public void addOrDelWishlist(String tenantId, String homestayId, Context context, ImageView imageView, int add, int del) {
        Call<CheckWL> call = Utils.getAPI().checkWishlist(tenantId, homestayId);
        call.enqueue(new Callback<CheckWL>() {
            @Override
            public void onResponse(Call<CheckWL> call, Response<CheckWL> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getContent() == 0) {
                        addHomeToWishlist(new Wishlist(tenantId, homestayId), context);
                        imageView.setImageResource(add);
                        Log.d("addOrDelWishlist", "onResponse: " + response.body().getContent());
                    } else {
                        deleteHomeFromWishlist(new Wishlist(tenantId, homestayId), context);
                        imageView.setImageResource(del);
                        Log.d("addOrDelWishlist", "onResponse: " + response.body().getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckWL> call, Throwable t) {

            }
        });
    }

    //Using for For Floating Action button on  ViewHomeDetailActivity
    public void addOrDelWishlist(String tenantId, String homestayId, Context context, FloatingActionButton fab) {
        Call<CheckWL> call = Utils.getAPI().checkWishlist(tenantId, homestayId);
        call.enqueue(new Callback<CheckWL>() {
            @Override
            public void onResponse(Call<CheckWL> call, Response<CheckWL> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getContent() == 0) {
                        addHomeToWishlist(new Wishlist(tenantId, homestayId), context);
                        fab.setImageResource(R.drawable.ic_fav_act_35dp);
                        Log.d("addOrDelWishlist", "onResponse: " + response.body().getContent());
                    } else {
                        deleteHomeFromWishlist(new Wishlist(tenantId, homestayId), context);
                        fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        Log.d("addOrDelWishlist", "onResponse: " + response.body().getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckWL> call, Throwable t) {

            }
        });
    }

    //Using to show icon wishlish
    public void checkIfHomestayInCurrentUserWishList(String tenantId, String homestayId, Context context, ImageView imageView, int add, int del) {
        Call<CheckWL> call = Utils.getAPI().checkWishlist(tenantId, homestayId);
        call.enqueue(new Callback<CheckWL>() {
            @Override
            public void onResponse(Call<CheckWL> call, Response<CheckWL> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getContent() == 0) {
                        imageView.setImageResource(del);
                    } else {
                        imageView.setImageResource(add);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckWL> call, Throwable t) {

            }
        });
    }

    public void checkIfHomestayInCurrentUserWishList(String tenantId, String homestayId, Context context, FloatingActionButton fab, int add, int del) {
        Call<CheckWL> call = Utils.getAPI().checkWishlist(tenantId, homestayId);
        call.enqueue(new Callback<CheckWL>() {
            @Override
            public void onResponse(Call<CheckWL> call, Response<CheckWL> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getContent() == 0) {
                        fab.setImageResource(del);
                    } else {
                        fab.setImageResource(add);
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckWL> call, Throwable t) {

            }
        });
    }


    public boolean checkIfHomestayInCurrentUserWishList(String homestayId) {

        for (Wishlist wishlist : Constants.wishlists) {
            if (wishlist.getHomestayId().equals(homestayId)) {
                return true;
            }
        }
        return false;

    }

    public void displayWishList() {
//        errorLayout.setVisibility(View.GONE);
        view.showLoading();
        if (Utils.checkLogin(context)) {
            Call<List<Wishlist>> call = Utils.getAPI().getWishList(Utils.getUserId(context), "en");
            call.enqueue(new Callback<List<Wishlist>>() {
                @Override
                public void onResponse(Call<List<Wishlist>> call, Response<List<Wishlist>> response) {
                    view.hideLoading();
                    if (response.isSuccessful() && response.body() != null) {
                        if (!wishlists.isEmpty()) {
                            wishlists.clear();
                        }
                        wishlists = response.body();
                        Constants.wishlists = wishlists;
                        view.onLoadWishlistSucces(wishlists);

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
        } else {
            view.hideLoading();
            view.showMessageNotLogin(R.drawable.sad, "Please login to see your wishlish", "");
        }

    }
}
