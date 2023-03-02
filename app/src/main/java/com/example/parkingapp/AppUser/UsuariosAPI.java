package com.example.parkingapp.AppUser;

import com.example.parkingapp.Driver.DriverDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuariosAPI {

    @POST("/login")
    Call<Void> login(@Body UsuariosDto usuariosDto);

    @GET("/usuarios/getUsuarioByEmail/{usuario_email}")
    Call<ResponseBody> getUsuarioByUsuarioemail(@Path("usuario_email") String usuarioEmail);

    @GET("/usuarios/getUsuarioById/{usuario_id}")
    Call<ResponseBody> getUsuarioByUsuarioId(@Path("usuario_id") Long id);

    @POST("usuarios/createUsuarios")
    Call<ResponseBody> createUsuarios(@Body UsuariosDto usuariosDto);

    @GET("/user/getAppUserByIdAndDocente/{appUser_id}")
    Call<ResponseBody> getAppUserByIdAndDocente(@Path("appUser_id") int appUserId, @Header("Authorization") String token);

    @GET("/user/getAppUserByIdAndAutoridad/{appUser_id}")
    Call<ResponseBody> getAppUserByIdAndAutoridad(@Path("appUser_id") int appUserId, @Header("Authorization") String token);
}
