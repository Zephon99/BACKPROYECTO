package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.AppUser.AppUser;
import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.Booking.BookingDto;
import com.example.parkingapp.Horas.Horas;
import com.example.parkingapp.Horas.HorasAPI;
import com.example.parkingapp.Reserva.Reserva;
import com.example.parkingapp.Reserva.ReservaAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
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

public class Lot extends AppCompatActivity {
    TextView nombre,apellido,numero,tipo;
    Button reserva,volver;
    EditText hora;
    BookingDto bookingDto;
    Spinner spinnerInicio,spinnerFinal;
    HorasAPI horasAPI;
    private static final String[] horas = {"8:00", "9:00", "10:00","11:00","12:00","13:00","14:00","15:00"
    ,"16:00","17:00","18:00","19:00","20:00","21:00"};

    private static final String[] minutos = {"0","15", "30", "45"};
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UsuariosAPI usuariosAPI;
    ReservaAPI reservaAPI;
    Long userID;

    //implements AdapterView.OnItemSelectedListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot);
        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("usuarioId");
        userID = Long.parseLong(stringVariableName);
        Log.e("ID USUARIO:",userID.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        nombre = (TextView) this.findViewById(R.id.NombreDocente);
        apellido = (TextView) this.findViewById(R.id.ApellidoDocente);
        numero = (TextView) this.findViewById(R.id.CelularDocente);
        tipo = (TextView) this.findViewById(R.id.TipoDocente);

        reserva = (Button) this.findViewById(R.id.reserva);
        volver = (Button) this.findViewById(R.id.volver);


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


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        spinnerInicio = (Spinner)findViewById(R.id.horaInicio);
        spinnerFinal = (Spinner)findViewById(R.id.horaFinal);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Lot.this,
                android.R.layout.simple_spinner_item,horas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInicio.setAdapter(adapter);


        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(Lot.this,
                android.R.layout.simple_spinner_item,horas);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinal.setAdapter(adapter1);


        volver = (Button)this.findViewById(R.id.volver) ;

        reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lot.this.getApplicationContext(), ParqueosDocentesA.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userID + "");
                extras.putString("horaInicio",spinnerInicio.getSelectedItem().toString());
                extras.putString("horaFinal",spinnerFinal.getSelectedItem().toString());





                horasAPI = retrofit.create(HorasAPI.class);

                Call<ResponseBody> call3 = horasAPI.getHorasByHora(spinnerInicio.getSelectedItem().toString());


                call3.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Gson gson = new Gson();
                                Horas horas = gson.fromJson(jsonObject.getJSONObject("data").toString(), Horas.class);
                                int idhora = horas.getHorasId();
                                extras.putString("horaId",""+idhora);
                                intent.putExtras(extras);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent) ;
                                finish();

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
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lot.this.getApplicationContext(), Docentes.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userID + "");
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