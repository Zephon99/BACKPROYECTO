package com.example.parkingapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.Booking.Booking;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.Booking.BookingAdapter;
import com.example.parkingapp.Booking.BookingDto;
import com.example.parkingapp.Booking.CountBooking;
import com.example.parkingapp.Driver.Driver;
import com.example.parkingapp.Driver.DriverAPI;
import com.example.parkingapp.ParkingLot.ParkingLotAPI;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.ParkingLot.ParkingLotDto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.parkingapp.databinding.ActivityMainMapBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainMapActivity extends FragmentActivity implements OnMapReadyCallback {
    Intent intent;

    private AlertDialog.Builder dialogBuilder,dialogBuilder2;
    private AlertDialog dialog,dialog2;
    private DatePicker datePicker;
    private Button reservaButton, volverButton, estimarButton,verReservas,estimarButton2,volverButton2,volverButton3;
    private TextView parkingLotName, parkingLotPrice, parkingLot, parkingLotStatus,driverName,driverLastName,resultado;
    private GoogleMap mMap;
    private AppUserAPI appUserAPI;
    private DriverAPI driverAPI;
    private TimePicker timePicker;
    private ActivityMainMapBinding binding;
    private int parkingid;
    private BookingAPI bookingAPI,bookingAPI1;
    private int userID;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private BookingDto bookingDto;
    private ArrayList<ParkingLot> parkingLots = new ArrayList<>();
    private String initialTime;
    private int precio=0;
    ParkingLot parkingLot1;
    RecyclerView recyclerView;
    Booking booking;
    BookingAdapter bookingAdapter;

    private ParkingLotAPI parkingLotAPI;
    Intent i;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = MainMapActivity.this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);

        driverAPI = retrofit.create(DriverAPI.class);

        verReservas = (Button)findViewById(R.id.verReservas);
        driverName = (TextView)findViewById(R.id.driverName);
        driverLastName = (TextView)findViewById(R.id.driverLastName);




        userID = sharedPreferences.getInt("AppUserID",0);

        Call<ResponseBody> call = driverAPI.getDriverByAppUserId(userID,sharedPreferences.getString("TOKEN",""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Gson gson = new Gson();
                    Driver driver = gson.fromJson(jsonObject.getJSONObject("data").toString(), Driver.class);
                    Log.e("DRIVER ID", String.valueOf(driver.getId()));
                    driverName.setText(driver.getUser().getFirstName());
                    driverLastName.setText(driver.getUser().getLastName());

                }catch (JSONException | IOException exception){
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SharedPreferences sharedPreferences = MainMapActivity.this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        parkingLotAPI = retrofit.create(ParkingLotAPI.class);



        userID = sharedPreferences.getInt("AppUserID",0);

        //Log.e("APPUSERID", String.valueOf(sharedPreferences.getInt("appUserId", 0)));


        Call<ResponseBody> call = parkingLotAPI.getParkingLots(sharedPreferences.getString("TOKEN",""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        ParkingLot parkingLot = gson.fromJson(jsonObject1.toString(), ParkingLot.class);

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


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markerTitle = marker.getTitle();
                Log.e("MARKER TITLE:",markerTitle);

                String[] parts = markerTitle.split(":");
                parkingid = Integer.parseInt(parts[1]);
                Log.e("MARKER ID:",""+parkingid);
                Call<ResponseBody> call = parkingLotAPI.getParkingLot(parkingid,sharedPreferences.getString("TOKEN",""));

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Gson gson = new Gson();
                                ParkingLot parkingLot = gson.fromJson(jsonObject.getJSONObject("data").toString(), ParkingLot.class);
                                Log.e("ID PARKING LOT", parkingLot.getId().toString());

                                createNewParkingLotDialog("Estacionamiento "+ parkingLot.getId(), parkingLot.getPrice().toString(), parkingLot.getOccupationStatus(),parkingLot);

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
                return false;
            }
        });

        /*LatLng sydney = new LatLng(-16.513007936127465, -68.12310055830051);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Estacionamiento 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        i = new Intent(this, ParkingSpaceDetails.class);*/

    }


    public void createMarker(String longitud,String latitud,int i){

        LatLng marker = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
        mMap.addMarker(new MarkerOptions().position(marker).title("Estacionamiento:"+i).icon(BitmapDescriptorFactory.fromResource(R.drawable.parkinglotchiquito)));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));


    }

    public void createNewParkingLotDialog(String name,String price, String status,ParkingLot parkingLot){
    dialogBuilder = new AlertDialog.Builder(this);

    final View parkingLotView = getLayoutInflater().inflate(R.layout.parkinglotpopup,null);
    parkingLotPrice = (TextView) parkingLotView.findViewById(R.id.parkinglotPrice);
    recyclerView = parkingLotView.findViewById(R.id.recycler_view);
    timePicker = (TimePicker)parkingLotView.findViewById(R.id.timepicker);
    timePicker.setIs24HourView(true);
    estimarButton = (Button) parkingLotView.findViewById(R.id.estimarButton);
    ArrayList<Booking> bookings1 = new ArrayList<>();
    SharedPreferences sharedPreferences = parkingLotView.getContext().getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        bookingAPI = retrofit.create(BookingAPI.class);
        Call<ResponseBody> call = bookingAPI.getBookingsByParkingLotId(parkingLot.getId(),sharedPreferences.getString("TOKEN",""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        Booking booking = gson.fromJson(jsonObject1.toString(), Booking.class);
                        bookings1.add(booking);

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
        setRecyclerView(parkingLot,bookings1);


    parkingLotPrice.setText(String.valueOf(parkingLot.getPrice()));




    reservaButton = (Button) parkingLotView.findViewById(R.id.reservaButton);
    volverButton = (Button) parkingLotView.findViewById(R.id.volverButton);





    dialogBuilder.setView(parkingLotView);
    dialog = dialogBuilder.create();
    dialog.show();



    reservaButton.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            String endTime;
            String hourI,minuteI,hourE,minuteE;



            SharedPreferences sharedPreferences = parkingLotView.getContext().getSharedPreferences("Libreria", Context.MODE_PRIVATE);

            hourI = String.valueOf(timePicker.getHour());
            minuteI = String.valueOf(timePicker.getMinute());
            hourE = String.valueOf(timePicker.getHour()+1);
            minuteE = String.valueOf(timePicker.getMinute());
            initialTime = hourI + ":" + minuteI+"0";
            endTime = hourE + ":" + minuteE+"0";



            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            System.out.println(date);





            String comptime,booktime;




            bookingDto = new BookingDto();
            bookingDto.setDate(date);
            bookingDto.setInitialTime(initialTime);
            bookingDto.setEndTime(endTime);
            bookingDto.setParkingLotId(parkingid);
            bookingDto.setDriverId(userID);
            bookingDto.setParkingAttendantId(1);
            bookingDto.setStatus("reservado");


            bookingAPI =  retrofit.create(BookingAPI.class);

            calcularPrecio(initialTime,parkingLots,parkingLot);
            Call<ResponseBody> call = bookingAPI.createBooking(bookingDto,sharedPreferences.getString("TOKEN",""));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if(response.isSuccessful()){
                        Log.e("BOOKING", "Created");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }
    });

    volverButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });

    estimarButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialogBuilder2 = new AlertDialog.Builder(parkingLotView.getContext());
            final View parkingLotView1 = getLayoutInflater().inflate(R.layout.estimaciondate,null);



            volverButton2 = (Button) parkingLotView1.findViewById(R.id.volverButton1);
            estimarButton2 = (Button) parkingLotView1.findViewById(R.id.estimarButton1);
            datePicker = (DatePicker)parkingLotView1.findViewById(R.id.datePicker);








            dialogBuilder2.setView(parkingLotView1);
            dialog2 = dialogBuilder2.create();
            dialog2.show();

            estimarButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();



                    SharedPreferences sharedPreferences = parkingLotView1.getContext().getSharedPreferences("Libreria", Context.MODE_PRIVATE);
                    bookingAPI = retrofit.create(BookingAPI.class);
                    Call<ResponseBody> call = bookingAPI.countBookings(sharedPreferences.getString("TOKEN",""));
                    ArrayList<CountBooking> countBookings = new ArrayList<>();
                    ArrayList<Integer> countedBookings = new ArrayList<>();


                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                int []count=new int[jsonArray.length()];
                                for(int i = 0; i<jsonArray.length(); i++){
                                    String resultString = jsonArray.getString(i);
                                    count[i] = Integer.parseInt(jsonArray.getString(i));
                                    Log.e("COUNTED",String.valueOf(count[i]));
                                }
                                calcular(count);
                            }catch (JSONException | IOException exception){
                                exception.printStackTrace();
                            }

                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("ERROR:",t.getMessage());
                        }


                    });

                }
            });


        }
    });


    }

    private void calcular(int[] count){


        //SUMATORIA DE Y
        int sumy = 0;

        for (int i = 0;i<count.length;i++){
            sumy+=count[i];
        }

        //LLENANDO EL VECTOR X
        int [] x = new int[count.length];

        if(count.length%2==0){

            for(int i=0;i<count.length;i++){
                if(i==0){
                    x[i]=-5;
                }
                x[i]=x[i]+2;
            }
        }else{
            for(int i=0;i<count.length;i++){
                if(i==0){
                    x[i]=-2;
                }
                x[i]=x[i-1]+1;
            }
        }

        //LLENANDO EL VECTOR X2
        int [] x2 = new int[ count.length];
        int sumx2=0;
        for(int i=0;i<count.length;i++){
            x2[i]=x[i]*x[i];
            sumx2+=x2[i];
        }

        //LLENANDO EL VECTOR XY
        int [] xy = new int[ count.length];
        int sumxy=0;
        for(int i=0;i<count.length;i++){
            xy[i]=count[i]*x[i];
            sumxy+=xy[i];
        }


        //RESULTADOS FINALES
        Double a,b,y1;
        a=Double.valueOf(sumy)/Double.valueOf(count.length);

        b=Double.valueOf(sumxy)/Double.valueOf(sumx2);

        y1 = a + b * (count.length);


        Log.e("VALOR DE ESTIMACION",String.valueOf(y1));


        intent = new Intent(getApplicationContext(), EstimationResult.class);
        intent.putExtra("RESULTADO",String.valueOf(y1));
        startActivity(intent);
    }

    private void setRecyclerView(ParkingLot parkingLot,List<Booking> bookings){
        bookingAdapter = new BookingAdapter(bookings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookingAdapter);
    }

    private void calcularPrecio(String initialTime,ArrayList<ParkingLot> parkingLotArrayList, ParkingLot parkingLot){


        ArrayList<Booking> bookings = new ArrayList<>();

        SharedPreferences sharedPreferences2 = MainMapActivity.this.getSharedPreferences("Libreria", Context.MODE_PRIVATE);
        bookingAPI1 = retrofit.create(BookingAPI.class);
        Call<ResponseBody> call = bookingAPI1.getBookings(sharedPreferences2.getString("TOKEN",""));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        Booking booking = gson.fromJson(jsonObject1.toString(), Booking.class);
                        bookings.add(booking);

                    }

                }catch (JSONException | IOException exception){
                    exception.printStackTrace();
                }
                precio = calcularPrecio1(bookings,initialTime);
                Log.e("SIZE",String.valueOf(bookings.size()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR:",t.getMessage());
            }
        });





    }

    private int calcularPrecio1(ArrayList<Booking> bookings,String initialTime){
        int contador = 0;
        int precio = 0;
        for(int i=0;i<bookings.size();i++){
            Log.e("TIEMPOS BDD:",bookings.get(i).getInitialTime());
            Log.e("TIEMPOS USUARIO:",initialTime);
            if(bookings.get(i).getInitialTime().equals(initialTime)){

                contador++;
                Log.e("CONTADOR",String.valueOf(contador));

            }
        }
        if(contador==0){
            precio = 2;
        }else{
            if(contador <= (bookings.size()*0.25)){
                precio = 3;
            }else {
                if(contador <= (bookings.size()*0.5) && contador > (bookings.size()*0.25)){
                    precio = 4;
                }else{
                    if(contador <= (bookings.size()*0.75) && contador > (bookings.size()*0.5)){
                        precio = 5;
                    }else{
                        if(contador <= bookings.size() && contador > (bookings.size()*0.75)){
                            precio = 5;
                        }
                    }
                }
            }
        }

        Toast.makeText(MainMapActivity.this, "Reserva creada Con Precio: "+precio, Toast.LENGTH_SHORT).show();

        return precio;
    }


}