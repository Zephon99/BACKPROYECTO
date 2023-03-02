package com.example.parkingapp.ReservasDia;

import com.example.parkingapp.Reserva.ReservaAPI;
import com.example.parkingapp.Reserva.ReservaDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservaDiaAPI {

    @GET("getReservaByHoraInicioAndEstado/{reserva_dia_hora_inicio}")
    Call<ResponseBody> findReservaDiaByReservaDiaHoraInicioAndReservaDiaEstado(@Path("reserva_dia_hora_inicio") String reservaHoraInicio);

    @POST("/reservadia/createReservaDia")
    Call<ResponseBody> createReservaDia(@Body ReservasDiaDto reservasDiaDto);


}
