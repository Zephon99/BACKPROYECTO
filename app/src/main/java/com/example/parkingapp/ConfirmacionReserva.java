package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.Reserva.ReservaAPI;
import com.example.parkingapp.Reserva.ReservaDto;
import com.example.parkingapp.ReservasDia.ReservaDiaAPI;
import com.example.parkingapp.ReservasDia.ReservasDiaDto;
import com.example.parkingapp.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmacionReserva extends AppCompatActivity {
    TextView nombre,apellido,numero,tipo,horaIniciotv,horaFinaltv;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    String horaInicio,horaFinal,userIdStr,espacioParqueostr;
    int horaId;
    UsuariosAPI usuariosAPI;
    Long usuarioId,espacioParqueoId;
    ReservaAPI reservaAPI;
    ReservaDiaAPI reservaDiaAPI;

    Button confirmar,volver;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_reserva);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userIdStr = extras.getString("usuarioId");
        horaInicio = extras.getString("horaInicio");
        horaFinal = extras.getString("horaFinal");
        espacioParqueostr = extras.getString("parqueoId");
        horaId = Integer.parseInt(extras.getString("horaId"));
        espacioParqueoId = Long.parseLong(espacioParqueostr);

        nombre = (TextView) findViewById(R.id.NombreDocente1);
        apellido = (TextView) findViewById(R.id.ApellidoDocente1);
        numero = (TextView) findViewById(R.id.CelularDocente1);
        tipo = (TextView) findViewById(R.id.TipoDocente1);
        horaIniciotv = (TextView) findViewById(R.id.horaIngreso);
        horaFinaltv = (TextView) findViewById(R.id.horaSalida);

        confirmar = (Button) this.findViewById(R.id.confirmar);
        volver = (Button) this.findViewById(R.id.volverButton1);

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
                        tipo.setText("Docente");

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


        horaIniciotv.setText(horaInicio);
        horaFinaltv.setText(horaFinal);



        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmacionReserva.this.getApplicationContext(), ParqueosDocentesA.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userIdStr + "");
                extras.putString("horaInicio",horaInicio);
                extras.putString("horaFinal",horaFinal);
                extras.putString("horaId",horaId+"");

                ReservaDto reservaDto = new ReservaDto();


                reservaDto.setHorasId(horaId);
                reservaDto.setEspacioParqueoid(espacioParqueoId);
                reservaDto.setUsuarioId(Long.parseLong(userIdStr));
                reservaDto.setStatus("1");


                reservaAPI =  retrofit.create(ReservaAPI.class);
                Call<ResponseBody> call2 = reservaAPI.createReserva(reservaDto);
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Log.e("RESERVA", "CREADA");
                            Toast.makeText(view.getContext(), "RESERVA CREADA", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                Date date = new Date();
                SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
                String stringDate= DateFor.format(date);

                ReservasDiaDto reservasDiaDto = new ReservasDiaDto();
                reservasDiaDto.setReservaDiaHoraInicio(horaInicio);
                reservasDiaDto.setReservaDiaHoraFin(horaFinal);
                reservasDiaDto.setEspacioParqueoId(espacioParqueoId);
                reservasDiaDto.setReservaDiaCosto(5L);
                reservasDiaDto.setReservaDiaFecha(stringDate);
                reservasDiaDto.setUsuarioId(usuarioId);
                reservasDiaDto.setReservaDiaEstado("RESERVADO");


                reservaDiaAPI =  retrofit.create(ReservaDiaAPI.class);
                Call<ResponseBody> call3 = reservaDiaAPI.createReservaDia(reservasDiaDto);
                call3.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Log.e("RESERVA", "CREADA");
                            Toast.makeText(view.getContext(), "RESERVA CREADA", Toast.LENGTH_LONG).show();
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(ConfirmacionReserva.this,"Notification");
                            builder.setContentTitle("Parking App Notification");
                            builder.setContentText(nombre.getText()+" Su reserva fue creada con exito!\n" +
                                    "La hora de entrada es: "+horaInicio+"\n" +
                                    "La hora de salida es: "+horaFinal);
                            builder.setSmallIcon(R.drawable.iconoparqueo4);
                            builder.setAutoCancel(true);
                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(ConfirmacionReserva.this);
                            managerCompat.notify(1,builder.build());
                            intent.putExtras(extras);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent) ;
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


            }
        });


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConfirmacionReserva.this.getApplicationContext(), Docentes.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", usuarioId + "");
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


}