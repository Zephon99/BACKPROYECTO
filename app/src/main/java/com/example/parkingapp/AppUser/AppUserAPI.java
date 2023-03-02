package com.example.parkingapp.AppUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppUserAPI {
    @POST("/login")
    Call<Void> login(@Body AppUserDto appUser);



    @GET("/user/getAppUserByIdAndDocente/{appUser_id}")
    Call<ResponseBody> getAppUserByIdAndDocente(@Path("appUser_id") int appUserId, @Header("Authorization") String token);


    /*@GET("/user/getAppUserByIdAndDocente/{appUser_id}")
    Call<ResponseBody> getAppUserByIdAndDocente(@Path("appUser_id") int appUserId, @Header("Authorization") String token, @Header("Content-Type") String contentType);*/

    @GET("/user/getAppUserByIdAndAutoridad/{appUser_id}")
    Call<ResponseBody> getAppUserByIdAndAutoridad(@Path("appUser_id") int appUserId, @Header("Authorization") String token);


}
