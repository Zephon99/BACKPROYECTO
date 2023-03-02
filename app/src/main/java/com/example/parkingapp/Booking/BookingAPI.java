package com.example.parkingapp.Booking;

import com.example.parkingapp.AppUser.AppUserDto;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotDto;
import com.example.parkingapp.Response;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingAPI {
    @POST("/booking/createBooking")
    Call<ResponseBody> createBooking(@Body BookingDto bookingDto, @Header("Authorization")String token);

    @GET("/booking/getBookingByParkingLotByIdAndStatus/{parkingLot_id}")
    Call<ResponseBody> getBookingByParkingLotId(@Path("parkingLot_id") int parkingLotId, @Header("Authorization") String token);


    @GET("/booking/getBookingsByParkingLotByIdAndStatus/{parkingLot_id}")
    Call<ResponseBody> getBookingsByParkingLotId(@Path("parkingLot_id") int parkingLotId, @Header("Authorization") String token);

    @GET("/booking/countBookings")
    Call<ResponseBody>countBookings(@Header("Authorization") String token);


    @GET("/booking/getBookings")
    Call<ResponseBody>getBookings(@Header("Authorization") String token);

    @POST("/booking/updateBooking/{booking_id}")
    Call<ResponseBody> updateBookingStatus(@Path("booking_id") int bookingId,@Body Booking booking,@Header("Authorization")String token);



}
