package com.swp.culturehomestay.services;


import com.swp.culturehomestay.models.AuthenticatioModel;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.LoginCredentials;
import com.swp.culturehomestay.models.ReservationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {
   @GET ("homestay/host/{id}")
    Call<List<HomeStay>> getWishList(
           @Path("id") String id,
           @Query("lang") String language
   );
   @GET ("homestay/{id}")
    Call<HomeStay> getHomeById(
           @Path("id") String id,
           @Query("lang") String language
   );
    @GET("transaction/reservations/user/{id}")
    Call<List<ReservationModel>> getReservationsHistory(
            @Path("id") String id
    );

    @POST("auth")
    Call<AuthenticatioModel> doLogin(@Body LoginCredentials loginCredentials);




}
