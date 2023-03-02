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

public class Autoridades extends AppCompatActivity {

    TextView nombre,apellido,numero,tipo,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,precio1;
    Button estimar,volver;
    Boolean libre=false;
    ImageView lot1,lot2,lot3,lot4,lot5,lot6,lot7,lot8,lot9,lot10,lot11,lot12;
    ArrayList<ParkingLot> parkingLots = new ArrayList<>();
    ArrayList<ParkingLot> parkingLots1 = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();
    ArrayList<Booking> bookingsActivos = new ArrayList<>();
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
        setContentView(R.layout.activity_parqueos_docentes);


        estimar = (Button)this.findViewById(R.id.estimar);
        volver = (Button)this.findViewById(R.id.volver) ;



        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Autoridades.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });






        nombre = (TextView) this.findViewById(R.id.NombreDocente);
        apellido = (TextView) this.findViewById(R.id.ApellidoDocente);
        numero = (TextView) this.findViewById(R.id.CelularDocente);
        tipo = (TextView) this.findViewById(R.id.TipoDocente);
        precio1 = (TextView)this.findViewById(R.id.preciotv);
        lot1 = (ImageView) this.findViewById(R.id.lot1);
        lot2 = (ImageView) this.findViewById(R.id.lot2);
        lot3 = (ImageView) this.findViewById(R.id.lot3);
        lot4 = (ImageView) this.findViewById(R.id.lot4);
        lot5 = (ImageView) this.findViewById(R.id.lot5);
        lot6 = (ImageView) this.findViewById(R.id.lot6);
        lot7 = (ImageView) this.findViewById(R.id.lot7);
        lot8 = (ImageView) this.findViewById(R.id.lot8);
        lot9 = (ImageView) this.findViewById(R.id.lot9);
        lot10 = (ImageView) this.findViewById(R.id.lot10);
        lot11 = (ImageView) this.findViewById(R.id.lot11);
        lot12= (ImageView) this.findViewById(R.id.lot12);

        t1 = (TextView)this.findViewById(R.id.tiempo1);
        t2 = (TextView)this.findViewById(R.id.tiempo2);
        t3 = (TextView)this.findViewById(R.id.tiempo3);
        t4 = (TextView)this.findViewById(R.id.tiempo4);
        t5 = (TextView)this.findViewById(R.id.tiempo5);
        t6 = (TextView)this.findViewById(R.id.tiempo6);
        t7 = (TextView)this.findViewById(R.id.tiempo7);
        t8 = (TextView)this.findViewById(R.id.tiempo8);
        t9 = (TextView)this.findViewById(R.id.tiempo9);
        t10 = (TextView)this.findViewById(R.id.tiempo10);
        t11 = (TextView)this.findViewById(R.id.tiempo11);
        t12 = (TextView)this.findViewById(R.id.tiempo12);


        SharedPreferences sharedPreferences = this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("AppUserID",0);
        appUserAPI = retrofit.create(AppUserAPI.class);

        Call<ResponseBody> call = appUserAPI.getAppUserByIdAndAutoridad(userID,sharedPreferences.getString("TOKEN",""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Gson gson = new Gson();
                        AppUser appUser = gson.fromJson(jsonObject.getJSONObject("data").toString(), AppUser.class);
                        nombre.setText(appUser.getFirstName());
                        apellido.setText(appUser.getLastName());
                        numero.setText(appUser.getNumero());
                        tipo.setText("Autoridad");

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


        SharedPreferences sharedPreferences1 = this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        parkingLotAPI = retrofit.create(ParkingLotAPI.class);



        userID = sharedPreferences1.getInt("AppUserID",0);

        //Log.e("APPUSERID", String.valueOf(sharedPreferences.getInt("appUserId", 0)));


        Call<ResponseBody> call1 = parkingLotAPI.getParkingLots(sharedPreferences.getString("TOKEN",""));

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        ParkingLot parkingLot = gson.fromJson(jsonObject1.toString(), ParkingLot.class);
                        Log.e("PARKING LOT:",String.valueOf(parkingLot.getId()));
                        parkingLots.add(parkingLot);
                    }

                }catch (JSONException | IOException exception){
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });

        SharedPreferences sharedPreferences2 = Autoridades.this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        bookingAPI = retrofit.create(BookingAPI.class);
        Call<ResponseBody> call2 = bookingAPI.getBookings(sharedPreferences2.getString("TOKEN",""));
        precio = 0;

        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response2) {
                try {
                    JSONObject jsonObject = new JSONObject(response2.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        Booking booking = gson.fromJson(jsonObject1.toString(), Booking.class);
                        bookings.add(booking);

                    }
                    precio1.setText(String.valueOf(calcularPrecio1(bookings)));
                    if(bookings!=null){changeImage(bookings,parkingLots);}
                }catch (JSONException | IOException exception){
                    exception.printStackTrace();
                }
                Log.e("SIZE",String.valueOf(bookings.size()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call2, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });




        lot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(24).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }

            }
        });

        lot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(25).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(26).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(27).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(28).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(29).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lotID = String.valueOf(parkingLots.get(30).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }


                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });

        lot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lotID = String.valueOf(parkingLots.get(31).getId());



                //Si esta ocupado y es del usuario mandar a info reserva
                if(infoReserva(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO RESERVA","NICE");
                    Intent i = new Intent(Autoridades.this, InfoReserva2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
                //Si esta ocupado y no es del usuario mandar a info del conductor
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)){
                    Log.e("INFO CONDUCTOR","NICE");
                    Intent i = new Intent(Autoridades.this, InfoAutoridad.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }




                //Si no esta ocupado mandar a reserva
                if(infoConductor(parkingLots,Integer.parseInt(lotID),bookings)==false && infoReserva(parkingLots,Integer.parseInt(lotID),bookings)==false ){
                    Log.e("LOT LIBRE","NICE");
                    Intent i = new Intent(Autoridades.this, Lot2.class);
                    i.putExtra("lotID",lotID);
                    startActivity(i);
                    finish();
                }
            }
        });




        estimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Autoridades.this, Estimacion2.class);
                i.putExtra("lotID",lotID);
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


    public boolean infoReserva(ArrayList<ParkingLot> parkingLots,int lotID, ArrayList<Booking> bookings){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getParkingLot().getId() == lotID && bookings.get(i).getStatus().equals("activo") && bookings.get(i).getDriver().getId() == userID){
                return true;
            }
        }
        return false;
    }

    public boolean infoConductor(ArrayList<ParkingLot> parkingLots,int lotID, ArrayList<Booking> bookings){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getParkingLot().getId() == lotID && bookings.get(i).getStatus().equals("activo") && bookings.get(i).getDriver().getId() != userID){
                return true;
            }
        }
        return false;
    }

    public boolean tieneReservaActiva(ArrayList<ParkingLot> parkingLots,int lotID, ArrayList<Booking> bookings){
        for(int i=0;i<bookings.size();i++){
            if(bookings.get(i).getDriver().getId() == userID && bookings.get(i).getStatus().equals("activo")){
                return true;
            }
        }
        return false;
    }


    private int calcularPrecio1(ArrayList<Booking> bookings){
        int contador = 0;
        int precio = 0;
        ArrayList<Booking> bookings1 = new ArrayList<>();

        for(int i=0;i<bookings.size();i++){

            if(bookings.get(i).getStatus().equals("activo") && Integer.parseInt(bookings.get(i).getParkingLot().getLot()) <= 12 ){

                contador++;
                Log.e("CONTADOR",String.valueOf(contador));

            }
        }
        if(contador==0){
            precio = 2;
        }else{
            if(contador <= (12*0.25)){
                precio = 3;
            }else {
                if(contador <= (12*0.5) && contador > (12*0.25)){
                    precio = 4;
                }else{
                    if(contador <= (12*0.75) && contador > (12*0.5)){
                        precio = 5;
                    }else{
                        if(contador <= 12 && contador > (12*0.75)){
                            precio = 5;
                        }
                    }
                }
            }
        }

        return precio;
    }

    public void changeImage(ArrayList<Booking> bookings,ArrayList<ParkingLot> parkingLots){

        for(int i=0;i<bookings.size();i++){
            Log.e("SIZE BOOKINGS",String.valueOf(bookings.size()));
            if(bookings.size()==0){
                Log.e("SIZE BOOKINGS",String.valueOf(bookings.size()));
            }else{
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(24).getId() && bookings.get(i).getStatus().equals("activo") ) {
                    Log.e("SIRVEEEEEE","NO SRIVER");
                    lot1.setImageResource(R.drawable.parqueoocupado2);
                    t1.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(25).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot2.setImageResource(R.drawable.parqueoocupado2);
                    t2.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(26).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot3.setImageResource(R.drawable.parqueoocupado2);
                    t3.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(27).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot4.setImageResource(R.drawable.parqueoocupado2);
                    t4.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(28).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot5.setImageResource(R.drawable.parqueoocupado2);
                    t5.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(29).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot6.setImageResource(R.drawable.parqueoocupado2);
                    t6.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(30).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot7.setImageResource(R.drawable.parqueoocupado2);
                    t7.setText(bookings.get(i).getInitialTime());
                }
                if(bookings.get(i).getParkingLot().getId() == parkingLots.get(31).getId() && bookings.get(i).getStatus().equals("activo")) {
                    lot8.setImageResource(R.drawable.parqueoocupado2);
                    t8.setText(bookings.get(i).getInitialTime());
                }

            }




        }
    }
}