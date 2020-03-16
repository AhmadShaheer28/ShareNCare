package com.example.sharecare.API;


import com.example.sharecare.ApiResponse.FoodRequestListResponse.RequestListResponse;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodRequest;
import com.example.sharecare.ApiResponse.FoodRequestResponse.FoodResponse;
import com.example.sharecare.ApiResponse.LoginResponse.LoginResponse;
import com.example.sharecare.ApiResponse.ParticipantResponse.participantResponse;
import com.example.sharecare.ApiResponse.RestaurantListResponse.RestaurantResponse;
import com.example.sharecare.ApiResponse.SignupResponse.SignUpRequest;
import com.example.sharecare.ApiResponse.SignupResponse.SignUpResponse;
import com.example.sharecare.ApiResponse.getAllBookResponse.AllBookResponse;
import com.example.sharecare.Model.Book;
import com.example.sharecare.Model.FoodRequestID;
import com.example.sharecare.Model.GetBookModel;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<LoginResponse> login(@Header("email") String email, @Header("password") String password);

    @Headers("Content-Type: application/json")
    @POST("user/create")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @Headers("Content-Type: application/json")
    @POST("request/create")
    Call<FoodResponse> createFoodRequest(@Header("Authorization") String authorization,@Body FoodRequest foodRequest);

    @Headers("Content-Type: application/json")
    @GET("request/restaurantList")
    Call<RestaurantResponse> getRestaurantList(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("request/list")
    Call<RequestListResponse> getFoodRequestList(@Header("Authorization") String authorization,@Query(value="type", encoded=true) String type);

    @Headers("Content-Type: application/json")
    @POST("request/joinRequest")
    Call<FoodResponse> joinFoodRequest(@Header("Authorization") String authorization,@Body FoodRequestID foodRequestid);

    @Headers("Content-Type: application/json")
    @GET("request/getUsersByRequestId")
    Call<participantResponse> getParticipantList(@Header("Authorization") String authorization, @Query(value="request_id", encoded=true) String request_id);


    @Headers("Content-Type: application/json")
    @POST("book/addBook")
    Call<FoodResponse> addBook(@Body Book foodRequest);

    @Headers("Content-Type: application/json")
    @POST("book/getAllBooks")
    Call<AllBookResponse> getAllBooks(@Body GetBookModel ownerId);

}

