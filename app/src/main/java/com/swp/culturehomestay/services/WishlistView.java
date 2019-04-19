package com.swp.culturehomestay.services;

import com.swp.culturehomestay.models.Wishlist;

import java.util.List;

public interface WishlistView {
    void showLoading();
    void hideLoading();
    void onLoadWishlistSucces(List<Wishlist> wishLists);
    void onLoadWishlistError(String mess);
    void showSwipeLayout();
    void showErrorLayout();
    void showErrorMessage(int imageView, String title, String message);
    void showMessageEmpty(int imageView, String title, String message);
    void showMessageNotLogin(int imageView, String title, String message);
}
