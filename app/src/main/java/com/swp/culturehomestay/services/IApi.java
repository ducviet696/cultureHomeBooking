package com.swp.culturehomestay.services;


import com.swp.culturehomestay.models.AuthenticatioModel;
import com.swp.culturehomestay.models.CheckWL;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.LoginCredentials;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
import com.swp.culturehomestay.models.ReservationModel;
import com.swp.culturehomestay.models.ResultBookingHistoryModel;
import com.swp.culturehomestay.models.SignUpCredentials;
import com.swp.culturehomestay.models.SignUpResModel;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.models.WishlistBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {
   @GET ("user/{userid}/wishlists")
        Call<WishlistBean> getWishList(
           @Path("userid") String id,
           @Query("lang") String language
   );
   @GET ("homestay/{id}")
    Call<HomeStay> getHomeById(
           @Path("id") String id,
           @Query("lang") String language
   );
    @GET("transaction/reservations/tenant/{id}")
    Call<ResultBookingHistoryModel> getReservationsHistory(
            @Path("id") String id
    );

    @POST("auth")
    Call<AuthenticatioModel> doLogin(@Body LoginCredentials loginCredentials);

    @POST("user/tenant/")
    Call<SignUpResModel> createAccount(@Body SignUpCredentials signUpCredentials);

//    @POST("user/wishlist")
    @HTTP(method = "POST", path = "user/wishlist", hasBody = true)
    Call<Wishlist> addToWishlist(@Body Wishlist wishlist );

//    @DELETE("user/wishlist")
    @HTTP(method = "DELETE", path = "user/wishlist", hasBody = true)
    Call<Wishlist> deleteWishlist(@Body Wishlist wishlist);

    @HTTP(method = "POST", path = "homestay/price", hasBody = true)
    Call<PriceGet> getPrice(@Body PricePost pricePost,
                            @Query("lang") String language);

    //Check Is homestay exist on current wishlist
    @GET ("/user/tenant/checkwishlist/{tenantId}/{homestayId}")
    Call<CheckWL> checkWishlist(
            @Path("tenantId") String tenantId,
            @Path("homestayId") String homestayId
    );


    @GET ("homestay/host/{hostId}")
    Call<List<HomeStay>> getListHomestayByHostId(
            @Path("hostId") String id,
            @Query("lang") String language
    );



}
