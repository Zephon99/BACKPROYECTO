package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.Vehiculo.Vehiculo;
import com.example.parkingapp.Vehiculo.VehiculoAPI;
import com.example.parkingapp.Vehiculo.VehiculoDto;
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

public class AdmVehiculos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    TextView nombre,apellido,numero,tipo;
    EditText placa,color,marca;
    Button registrar,activar,eliminar,volver;
    Spinner spinnerActivar,spinnerEliminar;
    Long userId;
    VehiculoAPI vehiculoAPI;
    VehiculoDto vehiculoDto;
    UsuariosAPI usuariosAPI;
    String[] strVehiculos;
    Vehiculo vehiculochange = new Vehiculo();

    ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_vehiculos);

        vehiculoDto = new VehiculoDto();

        nombre = (TextView) this.findViewById(R.id.NombreDocente);
        apellido = (TextView) this.findViewById(R.id.ApellidoDocente);
        numero = (TextView) this.findViewById(R.id.CelularDocente);
        tipo = (TextView) this.findViewById(R.id.TipoDocente);

        placa = (EditText) findViewById(R.id.placaV);
        color = (EditText) findViewById(R.id.colorV);
        marca = (EditText) findViewById(R.id.marcaV);


        registrar = (Button) findViewById(R.id.registrarV);
        activar = (Button) findViewById(R.id.activarV);
        eliminar = (Button) findViewById(R.id.eliminarV);
        volver = (Button) findViewById(R.id.volverButton);

        Bundle extras = getIntent().getExtras();
        String stringVariableName = extras.getString("usuarioId");
        userId = Long.parseLong(stringVariableName);

        spinnerEliminar = (Spinner)findViewById(R.id.spinner2);
        spinnerEliminar.setOnItemSelectedListener(this);

        spinnerActivar = (Spinner)findViewById(R.id.spinner);
        spinnerActivar.setOnItemSelectedListener(this);


         usuariosAPI= retrofit.create(UsuariosAPI.class);

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


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehiculoDto.setVehiculoMarca(String.valueOf(marca.getText()));
                vehiculoDto.setVehiculoPlaca(String.valueOf(placa.getText()));
                vehiculoDto.setVehiculoColor(String.valueOf(color.getText()));
                vehiculoDto.setUsuarioId(userId);
                vehiculoDto.setStatus("desactivado");

                vehiculoAPI =  retrofit.create(VehiculoAPI.class);
                Call<ResponseBody> call2 = vehiculoAPI.createVehiculo(vehiculoDto);
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Log.e("Vehiculo", "CREADO");
                            Toast.makeText(view.getContext(), "Vehiculo Agregado", Toast.LENGTH_LONG).show();
                            marca.getText().clear();
                            placa.getText().clear();
                            color.getText().clear();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });



            }

        });

        vehiculoAPI= retrofit.create(VehiculoAPI.class);

        Call<ResponseBody> call2 = vehiculoAPI.findVehiculosByUsuarioId(userId);

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        strVehiculos = new String[jsonArray.length()];
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            Vehiculo vehiculo = gson.fromJson(jsonObject1.toString(), Vehiculo.class);
                            Log.e("Vehiculos:",String.valueOf(vehiculo.getVehiculoId()));
                            vehiculos.add(vehiculo);
                            strVehiculos[i]=vehiculo.getVehiculoPlaca();

                        }
                        ArrayAdapter adapter = new ArrayAdapter(AdmVehiculos.this,android.R.layout.simple_spinner_item,strVehiculos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerActivar.setAdapter(adapter);

                        ArrayAdapter adapter2 = new ArrayAdapter(AdmVehiculos.this,android.R.layout.simple_spinner_item,strVehiculos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEliminar.setAdapter(adapter2);


                    }catch (JSONException | IOException exception){
                        exception.printStackTrace();
                        Toast.makeText(AdmVehiculos.this.getApplicationContext(), "Agregue vehiculos para usar la aplicación", Toast.LENGTH_LONG).show();

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


        activar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedV = spinnerActivar.getSelectedItem().toString();


                vehiculoAPI =  retrofit.create(VehiculoAPI.class);
                Call<ResponseBody> call2 = vehiculoAPI.updateVehiculoStatusToDesactivado(userId,"application/json");
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {

                        if(response.isSuccessful()){
                            Log.e("STATUS:", "CAMBIADO A DESACTIVADO");


                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                vehiculoAPI =  retrofit.create(VehiculoAPI.class);
                Call<ResponseBody> call3 = vehiculoAPI.updateVehiculoStatusToActivado(selectedV,"application/json");

                call3.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(view.getContext(), "Vehiculo Activado", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });





            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedV1 = spinnerEliminar.getSelectedItem().toString();
                System.out.println(selectedV1);
                vehiculoAPI =  retrofit.create(VehiculoAPI.class);
                Call<ResponseBody> call4 = vehiculoAPI.updateVehiculoStatusToEliminado(selectedV1,"application/json");

                call4.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(view.getContext(), "Vehiculo eliminado", Toast.LENGTH_LONG).show();

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

                Intent intent = new Intent(AdmVehiculos.this.getApplicationContext(), Docentes.class);// New activity
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {



    }
}