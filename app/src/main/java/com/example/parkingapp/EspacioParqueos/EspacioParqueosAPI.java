package com.example.parkingapp.EspacioParqueos;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface EspacioParqueosAPI {

    @GET("/espacioParqueo/getEspacioParqueos")
    Call<ResponseBody> getEspacioParqueos();
}
