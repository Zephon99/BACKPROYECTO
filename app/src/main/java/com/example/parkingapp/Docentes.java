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
import android.widget.TextView;

import com.example.parkingapp.AppUser.AppUser;
import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.login.LoginActivity;
import com.example.parkingapp.login.LoginAdapter;
import com.example.parkingapp.login.LoginTabFragment;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Docentes extends AppCompatActivity {

    Button parqueoA,parqueoB,admVehiculos,volver;
    TextView nombre,apellido,numero,tipo;
    AppUserAPI appUserAPI;
    UsuariosAPI usuariosAPI;
    Long userID;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docentes);
        nombre = (TextView) this.findViewById(R.id.NombreDocente);
        apellido = (TextView) this.findViewById(R.id.ApellidoDocente);
        numero = (TextView) this.findViewById(R.id.CelularDocente);
        tipo = (TextView) this.findViewById(R.id.TipoDocente);



        parqueoA = (Button) this.findViewById(R.id.ParqueoA);
        parqueoB = (Button) this.findViewById(R.id.ParqueoB);
        admVehiculos = (Button) this.findViewById(R.id.admVehiculos);
        volver = (Button) this.findViewById(R.id.volver);




        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("usuarioId");
        userID = Long.parseLong(stringVariableName);
        Log.e("ID USUARIO:",userID.toString());
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


        parqueoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Docentes.this.getApplicationContext(), Lot.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userID + "");
                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                finish();
            }
        });
        parqueoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Docentes.this.getApplicationContext(), Lot2.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userID + "");
                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                finish();
            }
        });

        admVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Docentes.this.getApplicationContext(), AdmVehiculos.class);// New activity
                Bundle extras = new Bundle();
                extras.putString("usuarioId", userID + "");
                intent.putExtras(extras);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                finish();
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