package com.swp.culturehomestay.services;


import com.swp.culturehomestay.models.Amenity;
import com.swp.culturehomestay.models.AuthenticatioModel;
import com.swp.culturehomestay.models.CheckWL;
import com.swp.culturehomestay.models.CultureService;
import com.swp.culturehomestay.models.DateBooked;
import com.swp.culturehomestay.models.HomeNameGet;
import com.swp.culturehomestay.models.HomeStay;
import com.swp.culturehomestay.models.LoginCredentials;
import com.swp.culturehomestay.models.PaymentGet;
import com.swp.culturehomestay.models.PaymentPost;
import com.swp.culturehomestay.models.PriceGet;
import com.swp.culturehomestay.models.PricePost;
import com.swp.culturehomestay.models.ReservationContent;
import com.swp.culturehomestay.models.ReservationModel;
import com.swp.culturehomestay.models.ReservationPost;
import com.swp.culturehomestay.models.ResultBookingHistoryModel;
import com.swp.culturehomestay.models.SearchHomeGet;
import com.swp.culturehomestay.models.SearchHomePost;
import com.swp.culturehomestay.models.SignUpCredentials;
import com.swp.culturehomestay.models.SignUpResModel;
import com.swp.culturehomestay.models.UserDetailModel;
import com.swp.culturehomestay.models.UserWant;
import com.swp.culturehomestay.models.Wishlist;
import com.swp.culturehomestay.models.WishlistBean;

import java.util.Date;
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


    @HTTP(method = "POST", path = "user/wishlist", hasBody = true)
    Call<Wishlist> addToWishlist(@Body Wishlist wishlist );

    @HTTP(method = "DELETE", path = "user/wishlist", hasBody = true)
    Call<Wishlist> deleteWishlist(@Body Wishlist wishlist);

    @HTTP(method = "POST", path = "homestay/price", hasBody = true)
    Call<PriceGet> getPrice(@Body PricePost pricePost,
                            @Query("lang") String language);

    //Get listhome by search
    @HTTP(method = "POST", path = "homestay/homestay", hasBody = true)
    Call<SearchHomeGet> getHomeBySearch(@Body SearchHomePost searchHomePost,
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

    //Get booked list date on current home
    @GET ("homestay/notdate")
    Call<DateBooked> getDateBooked(
            @Query("homestayId") String homestayId,
            @Query("numRoom") Integer roomNum
    );

    //Get All Culture Service
     @GET ("homestay/cultureServices")
     Call<List<CultureService>> getAllCultureService();

    //Get All Amenity
    @GET ("homestay/amenities")
    Call<List<Amenity>> getAllAmenity();

    //Get user detail by id
    @GET("user/{id}")
    Call<UserDetailModel> getUserDetailById(@Path("id") String id);

    //Get user by id
 @GET("user/{userid}")
 Call<UserWant> getUserById(@Path("userid") String userId);

 //Reservation
 @HTTP(method = "POST", path = "transaction/reservation", hasBody = true)
 Call<ReservationContent> postResvervation(@Body ReservationPost reservationPost);

 @HTTP(method = "POST", path = "transaction/pay", hasBody = true)
 Call<PaymentGet> getLinkPayment(@Body PaymentPost paymentPost);

 //Get Namestay
 @GET("homestay/queryhomestayname")
 Call<HomeNameGet> getAllHomestayName(
         @Query("lang") String lang,
         @Query("query") String query,
         @Query("page") int page
 );


}
