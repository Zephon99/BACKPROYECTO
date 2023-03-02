package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.parkingapp.Booking.BookingDto;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotAPI;
import com.example.parkingapp.Reserva.ReservaAPI;
import com.example.parkingapp.Vehiculo.VehiculoAPI;
import com.example.parkingapp.login.LoginActivity;
import com.google.gson.Gson;

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

public class InfoReserva extends AppCompatActivity {

    Booking booking = new Booking();

    TextView nombre,apellido,numero,horaInicioTv,horaFinalTv;
    Boolean libre=false;
    Button volver,desocupar;
    UsuariosAPI usuariosAPI;
    int reservaId;

    String lotID;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AppUserAPI appUserAPI;
    ParkingLotAPI parkingLotAPI;
    Long userID;
    int precio;


    int horaId;
    Long usuarioId,espacioParqueoId;
    ReservaAPI reservaAPI;
    String usuarioId1;
    String horaInicio,horaFinal,userIdStr,espacioParqueostr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_reserva);

        nombre = (TextView) this.findViewById(R.id.name);
        apellido = (TextView) this.findViewById(R.id.lastname);
        numero = (TextView) this.findViewById(R.id.numero);
        horaInicioTv = (TextView) this.findViewById(R.id.horaIniciotv);
        horaFinalTv = (TextView) this.findViewById(R.id.horaSalidatv);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userID = Long.parseLong(extras.getString("usuarioId"));
        horaInicio = extras.getString("horaInicio");
        horaFinal = extras.getString("horaFinal");
        espacioParqueostr = extras.getString("parqueoId");
        horaId = Integer.parseInt(extras.getString("horaId"));
        reservaId = Integer.parseInt(extras.getString("reservaId"));

        Log.e("Data",userID+horaInicio+horaFinal+espacioParqueostr+horaId);
        volver = (Button) this.findViewById(R.id.volverButton1);
        desocupar = (Button) this.findViewById(R.id.desocuparButton1);

        usuariosAPI = retrofit.create(UsuariosAPI.class);

        Call<ResponseBody> call = usuariosAPI.getUsuarioByUsuarioId(userID);

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


                volver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(InfoReserva.this.getApplicationContext(), Docentes.class);// New activity
                        Bundle extras = new Bundle();
                        extras.putString("usuarioId", usuarioId + "");
                        extras.putString("horaInicio", horaInicio + "");
                        extras.putString("horaFinal", horaFinal + "");
                        extras.putString("horaId", horaId + "");
                        intent.putExtras(extras);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                });

                desocupar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reservaAPI = retrofit.create(ReservaAPI.class);
                        Call<ResponseBody> call2 = reservaAPI.updateReservaToDisable(reservaId, "application/json");
                        call2.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {

                                if (response.isSuccessful()) {
                                    Log.e("STATUS:", "DESOCUPADO");
                                    Toast.makeText(view.getContext(), "DESOCUPADO", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(InfoReserva.this.getApplicationContext(), ParqueosDocentesA.class);// New activity
                                    Bundle extras = new Bundle();
                                    extras.putString("usuarioId", userID + "");
                                    extras.putString("horaInicio", horaInicio + "");
                                    extras.putString("horaFinal", horaFinal + "");
                                    extras.putString("horaId", horaId + "");
                                    intent.putExtras(extras);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
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
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }

}
