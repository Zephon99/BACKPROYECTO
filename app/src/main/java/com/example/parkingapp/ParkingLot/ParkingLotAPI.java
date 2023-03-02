package com.example.parkingapp.ParkingLot;



import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParkingLotAPI {
    @GET("/parkinglot/getParkingLots")
    Call<ResponseBody>getParkingLots(@Header("Authorization") String token);

    @GET("/parkinglot/parkinglotbyid/{parkingLot_id}")
    Call<ResponseBody> getParkingLot(@Path("parkingLot_id") int parkingLotId, @Header("Authorization") String token);


}
