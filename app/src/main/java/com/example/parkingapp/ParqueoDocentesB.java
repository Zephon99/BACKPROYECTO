package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.AppUser.AppUser;
import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.Booking.Booking;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.EspacioParqueos.EspacioParqueos;
import com.example.parkingapp.EspacioParqueos.EspacioParqueosAPI;
import com.example.parkingapp.Horas.Horas;
import com.example.parkingapp.Horas.HorasAPI;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotAPI;
import com.example.parkingapp.Reserva.Reserva;
import com.example.parkingapp.Reserva.ReservaAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParqueoDocentesB extends AppCompatActivity {
    Long userId;
    int reservaId;
    String horaInicio,horaFinal;
    Horas horas = new Horas();
    ArrayList<EspacioParqueos> espacioParqueos = new ArrayList<>();
    ArrayList<Reserva> reservaArrayList = new ArrayList<>();
    ArrayList<Reserva> reservaArrayList2 = new ArrayList<>();
    Button volver,pronostico;
    TextView nombre,apellido,numero,tipo,idusuariotv,precio;
    String lotID;
    EspacioParqueos espacioParqueos1;
    Reserva reserva;
    UsuariosAPI usuariosAPI;
    ReservaAPI reservaAPI;
    HorasAPI horasAPI;
    int horaId;
    EspacioParqueosAPI espacioParqueosAPI;





    ImageView lot1,lot2,lot3,lot4,lot5,lot6,lot7,lot8,lot9,lot10,lot11,lot12,lot13,lot14,lot15,lot16,lot17,lot18,lot19,lot20;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueos_docentes);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String userIdStr = extras.getString("usuarioId");
        horaInicio = extras.getString("horaInicio");
        horaFinal = extras.getString("horaFinal");
        horaId = Integer.parseInt(extras.getString("horaId"));

        volver=(Button)findViewById(R.id.volver);
        pronostico = (Button)findViewById(R.id.estimar);

        Log.e("DATA:",userIdStr+"Hora Final:"+horaFinal+"Hora inicio:"+horaInicio+"Hora ID:"+horaId);

        nombre = (TextView) findViewById(R.id.NombreDocente);
        apellido = (TextView) findViewById(R.id.ApellidoDocente);
        numero = (TextView) findViewById(R.id.CelularDocente);
        tipo = (TextView) findViewById(R.id.TipoDocente);
        idusuariotv = (TextView) findViewById(R.id.idusuario);
        precio = (TextView) findViewById(R.id.preciotv);
        usuariosAPI = retrofit.create(UsuariosAPI.class);

        userId = Long.parseLong(userIdStr);

        lot1 = (ImageView) findViewById(R.id.lot1);
        lot2 = (ImageView) findViewById(R.id.lot2);
        lot3 = (ImageView) findViewById(R.id.lot3);
        lot4 = (ImageView) findViewById(R.id.lot4);
        lot5 = (ImageView) findViewById(R.id.lot5);
        lot6 = (ImageView) findViewById(R.id.lot6);
        lot7 = (ImageView) findViewById(R.id.lot7);
        lot8 = (ImageView) findViewById(R.id.lot8);
        lot9 = (ImageView) findViewById(R.id.lot9);
        lot10 = (ImageView) findViewById(R.id.lot10);
        lot11 = (ImageView) findViewById(R.id.lot11);
        lot12 = (ImageView) findViewById(R.id.lot12);
        lot13 = (ImageView) findViewById(R.id.lot13);
        lot14 = (ImageView) findViewById(R.id.lot14);
        lot15 = (ImageView) findViewById(R.id.lot15);
        lot16 = (ImageView) findViewById(R.id.lot16);
        lot17= (ImageView) findViewById(R.id.lot17);
        lot18 = (ImageView) findViewById(R.id.lot18);
        lot19 = (ImageView) findViewById(R.id.lot19);
        lot20 = (ImageView) findViewById(R.id.lot20);


        Call<ResponseBody> call = usuariosAPI.getUsuarioByUsuarioId(userId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Gson gson = new Gson();
                        Usuarios usuarios = gson.fromJson(jsonObject.getJSONObject("data").toString(), Usuarios.class);
                        nombre.setText(usuarios.getNombre());
                        apellido.setText(usuarios.getApellido());
                        numero.setText(String.valueOf(usuarios.getCelular()));
                        tipo.setText("Docente");
                        idusuariotv.setText(String.valueOf(usuarios.getUsuarioId()));

                    }catch (JSONException | IOException exception){
                        exception.printStackTrace();
                    }
                }else {
                    Log.e("RESPONSE",String.valueOf(response.code()));
                }
            }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });

        findParqueos();


        reservaAPI = retrofit.create(ReservaAPI.class);
        Call<ResponseBody> call2 = reservaAPI.getReservasByTimeAndStatus(horaId,"1");

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                            reserva = gson.fromJson(jsonObject1.toString(), Reserva.class);
                            reservaArrayList2.add(reserva);
                        }
                    }catch (JSONException | IOException exception){
                        exception.printStackTrace();


                    }
                }else {
                    Log.e("RESPONSE",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });


        findReservas();
        findReservas();
        findReservas();
        findReservas();




        lot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(0).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(0).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(0).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(0).getEspacioParqueoId()+"");

                }
            }

        });

        lot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(1).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(1).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(1).getEspacioParqueoId()+"");

                }


            }
        });

        lot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(2).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(2).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(2).getEspacioParqueoId()+"");

                }
            }
        });

        lot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(3).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(3).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(3).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(3).getEspacioParqueoId()+"");

                }
            }

        });

        lot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(4).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(4).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(4).getEspacioParqueoId()+"");

                }


            }
        });

        lot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(5).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(5).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(5).getEspacioParqueoId()+"");

                }
            }
        });

        lot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(6).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(6).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(6).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(6).getEspacioParqueoId()+"");

                }
            }

        });

        lot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(7).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(7).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(7).getEspacioParqueoId()+"");

                }


            }
        });

        lot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(8).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(8).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(8).getEspacioParqueoId()+"");

                }
            }
        });

        lot10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(9).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(9).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(9).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(9).getEspacioParqueoId()+"");

                }
            }

        });

        lot11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(10).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(10).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(10).getEspacioParqueoId()+"");

                }


            }
        });

        lot12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(11).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(11).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(11).getEspacioParqueoId()+"");

                }
            }
        });

        lot13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(12).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(12).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(12).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(12).getEspacioParqueoId()+"");

                }
            }

        });

        lot14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(13).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(13).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(13).getEspacioParqueoId()+"");

                }


            }
        });

        lot15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(14).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(14).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(14).getEspacioParqueoId()+"");

                }
            }
        });

        lot16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(15).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(15).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(15).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(15).getEspacioParqueoId()+"");

                }
            }

        });

        lot17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(16).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(16).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(16).getEspacioParqueoId()+"");

                }


            }
        });

        lot18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Long lotid = espacioParqueos.get(17).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userIdStr+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(17).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(17).getEspacioParqueoId()+"");

                }
            }
        });

        lot19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Long lotid = espacioParqueos.get(18).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);

                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA",userId+""+horaInicio+""+horaFinal+horaId+espacioParqueos.get(18).getEspacioParqueoId());

                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(18).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(18).getEspacioParqueoId()+"");

                }
            }

        });

        lot20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lotid = espacioParqueos.get(19).getEspacioParqueoId();
                Log.e("LOT NUMERO",""+lotid);


                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO RESERVA","NICE");
                    mandarinfoReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(19).getEspacioParqueoId()+"",reservaId);


                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(espacioParqueos,lotid,reservaArrayList)){
                    Log.e("INFO CONDUCTOR","NICE");
                    String usuarioOcupado = "";
                    for(int i=0;i<reservaArrayList.size();i++){
                        if(reservaArrayList.get(i).getEspacioParqueoid().equals(lotid) && reservaArrayList.get(i).getHorasId() == horaId){
                            usuarioOcupado = reservaArrayList.get(i).getUsuarioId()+"";
                            mandarinfoConductor(usuarioOcupado,horaInicio,horaFinal,horaId+"",lotid+"",userIdStr);
                        }
                    }

                }
                //Si no esta ocupado mandar a reserva
                if(!infoConductor(espacioParqueos,lotid,reservaArrayList) && !infoReserva(espacioParqueos,lotid,reservaArrayList) ) {
                    Log.e("LOT LIBRE", "NICE");
                    Log.e("USER ID:", idusuariotv.getText().toString());
                    confirmacionReserva(userId+"",horaInicio,horaFinal,horaId+"",espacioParqueos.get(19).getEspacioParqueoId()+"");

                }


            }
        });

        pronostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParqueoDocentesB.this.getApplicationContext(), Estimacion.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userId + "");

                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                finish();
            }
        });


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ParqueoDocentesB.this.getApplicationContext(), Docentes.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userId + "");
                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                finish();
            }
        });
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("¿Esta seguro que quiere salir de la aplicación?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }


    public void changeImageColor(ArrayList<EspacioParqueos> espacioParqueos1,ArrayList<Reserva> reservasHora,ArrayList<Reserva> reserva2){
        int numero=0;
        if(reservasHora.size()==0){
            Log.e("TAMAño lista","VACIA");
        }else{
            precio.setText(calcularPrecio1(reserva2)+"");
            for(int i=0;i<reservasHora.size();i++){
                if(espacioParqueos1.get(0).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot1.setBackgroundResource(R.drawable.parqueoocupado2);

                }
                if(espacioParqueos1.get(1).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot2.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(2).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot3.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(3).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot4.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(4).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot5.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(5).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot6.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(6).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot7.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(7).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot8.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(8).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot9.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(9).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot10.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(10).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot11.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(11).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot12.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(12).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot13.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(13).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot14.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(14).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot15.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(15).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot16.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(16).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot17.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(17).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot18.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(18).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot19.setBackgroundResource(R.drawable.parqueoocupado2);
                }
                if(espacioParqueos1.get(19).getEspacioParqueoId() == (reservasHora.get(i).getEspacioParqueoid())){
                    lot20.setBackgroundResource(R.drawable.parqueoocupado2);
                }

            }
        }

    }

    public void findParqueos(){
        espacioParqueosAPI = retrofit.create(EspacioParqueosAPI.class);
        Call<ResponseBody> call2 = espacioParqueosAPI.getEspacioParqueos();

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                            espacioParqueos1 = gson.fromJson(jsonObject1.toString(), EspacioParqueos.class);
                            espacioParqueos.add(espacioParqueos1);

                        }


                    }catch (JSONException | IOException exception){
                        exception.printStackTrace();


                    }
                }else {
                    Log.e("RESPONSE",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });
    }

    public void findReservas(){
        reservaAPI = retrofit.create(ReservaAPI.class);
        Call<ResponseBody> call2 = reservaAPI.getReservasByTimeAndStatus(horaId,"1");

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

                            reserva = gson.fromJson(jsonObject1.toString(), Reserva.class);
                            reservaArrayList.add(reserva);
                        }
                        if(reservaArrayList.size()<=0 || espacioParqueos.size()<=0){
                            Log.e("TAMAÑO RESERVA",reservaArrayList.size()+""+espacioParqueos.size());
                            Log.e("AVISO:","No hay reservas");
                        }else{
                            Log.e("TAMAÑO RESERVA",reservaArrayList.size()+""+espacioParqueos.size());
                            changeImageColor(espacioParqueos,reservaArrayList,reservaArrayList2);


                        }


                    }catch (JSONException | IOException exception){
                        exception.printStackTrace();


                    }
                }else {
                    Log.e("RESPONSE",String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });


    }

    public boolean infoConductor(ArrayList<EspacioParqueos> espacioParqueos,Long lotID, ArrayList<Reserva> reservas){
        for(int i=0;i<reservas.size();i++){
            if(reservas.get(i).getEspacioParqueoid() == lotID &&  reservas.get(i).getUsuarioId()!=(userId) ){
                return true;
            }
        }
        return false;
    }

    public boolean infoReserva(ArrayList<EspacioParqueos> espacioParqueos,Long lotID, ArrayList<Reserva> reservas){
        for(int i=0;i<reservas.size();i++){
            if(reservas.get(i).getEspacioParqueoid() == lotID && reservas.get(i).getUsuarioId() == userId){
                reservaId = reservas.get(i).getReservaId();
                return true;
            }
        }
        return false;

    }


    public void confirmacionReserva(String userId,String horaInicio,String horaFinal,String horaid,String parqueoId){
        Intent intent = new Intent(ParqueoDocentesB.this.getApplicationContext(), ConfirmacionReserva.class);// New activity
        Bundle extras = new Bundle();
        extras.putString("usuarioId", userId + "");
        extras.putString("horaInicio", horaInicio + "");
        extras.putString("horaFinal", horaFinal + "");
        extras.putString("horaId", horaid + "");
        extras.putString("parqueoId", parqueoId + "");
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent) ;
        finish();
    }

    public void mandarinfoReserva(String userId,String horaInicio,String horaFinal,String horaid,String parqueoId,int reservaId){
        Intent intent = new Intent(ParqueoDocentesB.this.getApplicationContext(), InfoReserva.class);// New activity
        Bundle extras = new Bundle();
        extras.putString("usuarioId", userId + "");
        extras.putString("horaInicio", horaInicio + "");
        extras.putString("horaFinal", horaFinal + "");
        extras.putString("horaId", horaid + "");
        extras.putString("parqueoId", parqueoId + "");
        extras.putString("reservaId",reservaId+"");
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent) ;
        finish();
    }


    public void mandarinfoConductor(String userId,String horaInicio,String horaFinal,String horaid,String parqueoId,String userId1){
        Intent intent = new Intent(ParqueoDocentesB.this.getApplicationContext(), InfoConductor.class);// New activity
        Bundle extras = new Bundle();
        extras.putString("usuarioId", userId + "");
        extras.putString("usuarioId1", userId1 + "");
        extras.putString("horaInicio", horaInicio + "");
        extras.putString("horaFinal", horaFinal + "");
        extras.putString("horaId", horaid + "");
        extras.putString("parqueoId", parqueoId + "");
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent) ;
        finish();
    }

    private int calcularPrecio1(ArrayList<Reserva> reservas){

        int precio = 0;

        if (reservas.size() <= espacioParqueos.size()*0.25){
            precio=2;
        }else{
            if (reservas.size() > espacioParqueos.size()*0.25 && reservas.size() <= espacioParqueos.size()*0.5){
                precio=3;
            }else {
                if (reservas.size() > espacioParqueos.size()*0.5 && reservas.size() <= espacioParqueos.size()*0.75){
                    precio=4;
                }else{
                    if (reservas.size() > espacioParqueos.size()*0.75 ){
                        precio=5;
                    }
                }

            }

        }

        return  precio;
    }
}
