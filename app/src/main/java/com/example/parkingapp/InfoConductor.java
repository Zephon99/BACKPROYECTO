package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.AppUser.AppUser;
import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.Booking.Booking;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotAPI;
import com.example.parkingapp.RegistroIncidentes.RegistroIncidentesAPI;
import com.example.parkingapp.RegistroIncidentes.RegistroIncidentesDto;
import com.example.parkingapp.Reserva.ReservaAPI;
import com.example.parkingapp.Reserva.ReservaDto;
import com.example.parkingapp.login.LoginActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoConductor extends AppCompatActivity {

    TextView nombre,apellido,numero,horaInicioTv,horaFinalTv;
    Boolean libre=false;
    Button contactar,volver;
    RegistroIncidentesAPI registroIncidentesAPI;




    String lotID;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AppUserAPI appUserAPI;
    ParkingLotAPI parkingLotAPI;
    int userID;
    int precio;

    String horaInicio,horaFinal,userIdStr,espacioParqueostr;
    int horaId;
    UsuariosAPI usuariosAPI;
    Long usuarioId,espacioParqueoId;
    ReservaAPI reservaAPI;
    String usuarioId1;
    Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_conductor);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userIdStr = extras.getString("usuarioId");
        usuarioId1 = extras.getString("usuarioId1");
        horaInicio = extras.getString("horaInicio");
        horaFinal = extras.getString("horaFinal");
        espacioParqueostr = extras.getString("parqueoId");
        horaId = Integer.parseInt(extras.getString("horaId"));
        espacioParqueoId = Long.parseLong(espacioParqueostr);




        nombre = (TextView) this.findViewById(R.id.name);
        apellido = (TextView) this.findViewById(R.id.lastname);
        numero = (TextView) this.findViewById(R.id.numero);
        horaInicioTv = (TextView) this.findViewById(R.id.horaIniciotv);
        horaFinalTv = (TextView) this.findViewById(R.id.horaFinaltv);
        contactar = (Button)this.findViewById(R.id.contactarButton);
        volver = (Button) this.findViewById(R.id.volverButton1);


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InfoConductor.this, Docentes.class));
                finish();
            }
        });

        usuarioId = Long.parseLong(userIdStr);

        usuariosAPI = retrofit.create(UsuariosAPI.class);

        Call<ResponseBody> call = usuariosAPI.getUsuarioByUsuarioId(usuarioId);

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
                        horaInicioTv.setText(horaInicio);
                        horaFinalTv.setText(horaFinal);


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










        contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistroIncidentesDto registroIncidentesDto = new RegistroIncidentesDto();
                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
                String stringDate= DateFor.format(date);
                System.out.println(stringDate);

                Calendar calendar = Calendar.getInstance();
                int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
                int hour12hrs = calendar.get(Calendar.HOUR);
                int minutes = calendar.get(Calendar.MINUTE);
                int seconds = calendar.get(Calendar.SECOND);

                registroIncidentesDto.setIdUsuarioOriginante(Long.parseLong(usuarioId1));
                registroIncidentesDto.setIdUsuarioDestinante(usuarioId);
                registroIncidentesDto.setFecha(stringDate);
                registroIncidentesDto.setHora(hour24hrs+":"+minutes);
                registroIncidentesDto.setEstado(true);


                registroIncidentesAPI =  retrofit.create(RegistroIncidentesAPI.class);
                Call<ResponseBody> call2 = registroIncidentesAPI.createIncidente(registroIncidentesDto);
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Log.e("REGISTRO INCIDENTE:", "CREADO");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                //Recibir nombre, numero de parqueo, mensaje automatico
                String url = "https://api.whatsapp.com/send?phone="+"+591"+numero.getText();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InfoConductor.this.getApplicationContext(), ParqueosDocentesA.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", usuarioId1 + "");
                extras.putString("horaInicio", horaInicio + "");
                extras.putString("horaFinal", horaFinal + "");
                extras.putString("horaId", horaId + "");
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

}