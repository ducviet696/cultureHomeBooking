package com.swp.culturehomestay.services;


import com.swp.culturehomestay.models.HomeStay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {
   @GET ("homestay/host/{id}")
    Call<List<HomeStay>> getWishList(
           @Path("id") String id
   );
}
