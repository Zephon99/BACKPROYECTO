package com.example.parkingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.AppUser.UsuariosDto;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.Driver.DriverAPI;
import com.example.parkingapp.Driver.DriverDto;
import com.example.parkingapp.login.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {

    EditText email,nombre,apellido,numero,password,ci,colorV,marcaV,placaV;
    RadioButton docente,autoridad;
    Button registrar,volver;
    Date date1 = new Date();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AppUserAPI appUserAPI;
    DriverAPI driverAPI;
    DriverDto driverDto;

    UsuariosDto usuariosDto;
    UsuariosAPI usuariosAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        email = (EditText) this.findViewById(R.id.email);
        nombre = (EditText) this.findViewById(R.id.nombre);
        apellido = (EditText) this.findViewById(R.id.apellido);
        numero = (EditText) this.findViewById(R.id.numero);
        password = (EditText) this.findViewById(R.id.password);
        ci = (EditText) this.findViewById(R.id.CI);


        registrar = (Button) this.findViewById(R.id.registro);
        volver = (Button) this.findViewById(R.id.volver);
        //docente = (RadioButton) this.findViewById(R.id.radio_docente);
        //autoridad = (RadioButton) this.findViewById(R.id.radio_autoridad);

        registrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                usuariosDto = new UsuariosDto();

                if(nombre.getText().equals(null) || apellido.getText().equals(null) || numero.getText().equals(null) || email.getText().equals(null) || password.getText().equals(null) )
                {
                    Toast.makeText(view.getContext(), "Algunos campos están vacíos", Toast.LENGTH_LONG).show();
                }else{
                    String numero2 = String.valueOf(numero.getText());
                    Integer numero1 = Integer.parseInt(numero2);
                    Log.e("NUMERO",String.valueOf(numero1));
                    usuariosDto.setNombre(String.valueOf(nombre.getText()));
                    usuariosDto.setApellido(String.valueOf(apellido.getText()));
                    usuariosDto.setUsuarioEmail(String.valueOf(email.getText()));
                    usuariosDto.setUsuarioPassword(String.valueOf(password.getText()));
                    usuariosDto.setCelular(numero1);
                    usuariosDto.setCi(String.valueOf(ci.getText()));
                    usuariosDto.setUsuarioCod("1");
                    usuariosDto.setUsuarioTipo("docente");




                    Date date = new Date();
                    SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
                    String stringDate= DateFor.format(date);
                    System.out.println(stringDate);


                    LocalDate date3 = LocalDate.parse(stringDate);









                    usuariosDto.setFecha(stringDate);
                    usuariosDto.setPersonaId(1L);
                    usuariosDto.setInstitucionId(1L);

                    usuariosAPI =  retrofit.create(UsuariosAPI.class);
                    Call<ResponseBody> call2 = usuariosAPI.createUsuarios(usuariosDto);
                    call2.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if(response.isSuccessful()){
                                Log.e("USUARIO", "CREADo");
                                Toast.makeText(view.getContext(), "Usuario Creado", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(view.getContext(), LoginActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }




            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
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