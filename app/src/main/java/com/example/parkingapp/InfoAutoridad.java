package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.Booking.Booking;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotAPI;
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

public class InfoAutoridad extends AppCompatActivity {

    TextView nombre,apellido,numero,tiempo;
    Boolean libre=false;
    Button contactar,volver;
    ImageView lot1,lot2,lot3,lot4,lot5,lot6,lot7,lot8,lot9,lot10,lot11,lot12;
    ArrayList<ParkingLot> parkingLots = new ArrayList<>();
    ArrayList<ParkingLot> parkingLots1 = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();
    BookingAPI bookingAPI;
    String lotID;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    AppUserAPI appUserAPI;
    ParkingLotAPI parkingLotAPI;
    int userID;
    int precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_autoridad);

        nombre = (TextView) this.findViewById(R.id.name);
        apellido = (TextView) this.findViewById(R.id.lastname);
        numero = (TextView) this.findViewById(R.id.numero);
        tiempo = (TextView) this.findViewById(R.id.tiempo);

        contactar = (Button)this.findViewById(R.id.contactarButton);
        volver = (Button) this.findViewById(R.id.volverButton1);


        SharedPreferences sharedPreferences2 = this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        bookingAPI = retrofit.create(BookingAPI.class);
        Call<ResponseBody> call2 = bookingAPI.getBookings(sharedPreferences2.getString("TOKEN",""));
        precio = 0;

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Intent intent = getIntent();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        Booking booking = gson.fromJson(jsonObject1.toString(), Booking.class);
                        bookings.add(booking);

                    }
                    for(int i=0;i<bookings.size();i++){
                        if(bookings.get(i).getParkingLot().getId() == Integer.parseInt(intent.getStringExtra("lotID")) && bookings.get(i).getStatus().equals("activo")){
                            tiempo.setText(bookings.get(i).getInitialTime());
                            nombre.setText(bookings.get(i).getDriver().getUser().getFirstName());
                            apellido.setText(bookings.get(i).getDriver().getUser().getLastName());
                            numero.setText(bookings.get(i).getDriver().getUser().getNumero());
                        }
                    }

                }catch (JSONException | IOException exception){
                    exception.printStackTrace();
                }
                Log.e("SIZE",String.valueOf(bookings.size()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });










        contactar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+"+591"+numero.getText();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(InfoAutoridad.this, Autoridades.class);
                startActivity(i);
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