package com.example.parkingapp.Driver;

import com.example.parkingapp.Booking.BookingDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DriverAPI {

    @GET("/driver/getDriverByAppUserId/{app_user_id}")
    Call<ResponseBody> getDriverByAppUserId(@Path("app_user_id") int appUserId, @Header("Authorization") String token);

    @POST("driver/createDriver")
    Call<ResponseBody> createDriver(@Body DriverDto driverDto, @Header("Authorization")String token);
}
