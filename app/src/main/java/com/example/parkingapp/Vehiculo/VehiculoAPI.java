package com.example.parkingapp.Vehiculo;

import com.example.parkingapp.AppUser.UsuariosDto;
import com.example.parkingapp.Driver.DriverDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehiculoAPI {


    @POST("vehiculo/createVehiculo")
    Call<ResponseBody> createVehiculo(@Body VehiculoDto vehiculoDto);

    @GET("/vehiculo/getVehiculoByPlaca/{vehiculo_placa}")
    Call<ResponseBody> findVehiculoByVehiculoPlaca(@Path("vehiculo_placa") String vehiculoPlaca);

    @GET("/vehiculo/getVehiculosByUsuarioIdAndStatus/{usuario_id}")
    Call<ResponseBody> findVehiculoByUsuarioIdAndStatus(@Path("usuario_id") Long id);

    @GET("/vehiculo/getVehiculosByUsuarioId/{usuario_id}")
    Call<ResponseBody> findVehiculosByUsuarioId(@Path("usuario_id") Long id);

    @PUT("vehiculo/updateVehiculoStatusToDesactivado/{usuario_id}")
    Call<ResponseBody> updateVehiculoStatusToDesactivado(@Path("usuario_id") Long id,@Header("Content-Type") String contentType);

    @PUT("vehiculo/updateVehiculoStatusToActivado/{vehiculo_placa}")
    Call<ResponseBody> updateVehiculoStatusToActivado(@Path("vehiculo_placa") String placa,@Header("Content-Type") String contentType);

    @PUT("vehiculo/updateVehiculoStatusToEliminado/{vehiculo_placa}")
    Call<ResponseBody> updateVehiculoStatusToEliminado(@Path("vehiculo_placa") String placa,@Header("Content-Type") String contentType);

}
