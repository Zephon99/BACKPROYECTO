package com.example.parkingapp.Reserva;

import com.example.parkingapp.AppUser.UsuariosDto;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservaAPI {

    @GET("/reservas/getReservaByUserAndTimeAndLot")
    Call<ResponseBody> getReservaByUserAndTimeAndLot(@Query("usuario_id") Long id,@Query("espacio_parqueoid") Long espacioId,@Query("horas_id")int horasId,@Query("status")String status );

    @GET("/reservas/getReservasByHorasId/{horas_id}")
    Call<ResponseBody> getReservasByHorasId(@Path("horas_id") int horasId);

    @GET("/reservas/getReservaByTimeAndStatus")
    Call<ResponseBody> getReservasByTimeAndStatus(@Query("horas_id") int horasId,@Query("status") String status);

    @POST("/reservas/createReserva")
    Call<ResponseBody> createReserva(@Body ReservaDto reservaDto);

    @PUT("reservas/updateReservaToDisable/{reserva_id}")
    Call<ResponseBody> updateReservaToDisable(@Path("reserva_id") int id,@Header("Content-Type") String contentType);


}
