package com.swp.culturehomestay.services;

import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.SearchBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface HomeStayService {
    @GET("homestay/homestay?lang=vi")
    Call<List<HomeStay>> getHomeStayById(@Body SearchBody searchBody);

}

