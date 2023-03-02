package com.example.parkingapp.Horas;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HorasAPI {

    @GET("/horas/getHorasByHora/{horas}")
    Call<ResponseBody> getHorasByHora(@Path("horas") String hora);


}
