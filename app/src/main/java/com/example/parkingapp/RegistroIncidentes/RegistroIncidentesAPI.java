package com.example.parkingapp.RegistroIncidentes;

import com.example.parkingapp.Reserva.ReservaDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistroIncidentesAPI {
    @POST("/registroIncidentes/createIncidente")
    Call<ResponseBody> createIncidente(@Body RegistroIncidentesDto registroIncidentesDto);


}
