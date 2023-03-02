package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.login.LoginActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class ResultadoEstimacion2 extends AppCompatActivity {
    private BookingAPI bookingAPI;
    ArrayList data;
    Button volverButton;
    BarChart barChart;
    TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_estimacion2);
        getData();
        volverButton = (Button) findViewById(R.id.volverButton1);
        barChart = (BarChart) findViewById(R.id.barchart);
        resultado = (TextView) findViewById(R.id.resultado);
        BarDataSet barDataSet = new BarDataSet(data,"RESERVAS");
        BarData barData  = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Autoridades.class));
                finish();
            }
        });

        final int min = 1;
        final int max = 5;
        final int random = new Random().nextInt((max - min) + 1) + min;

        if(random==1){
            resultado.setText("7 Reservas");
        }
        if(random==2){
            resultado.setText("9 Reservas");
        }
        if(random==3){
            resultado.setText("6 Reservas");
        }
        if(random==4){
            resultado.setText("15 Reservas");
        }
        if(random==5){
            resultado.setText("9 Reservas");
        }



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

    private void getData(){
        data = new ArrayList();
        data.add(new BarEntry(1f,4));
        data.add(new BarEntry(2f,3));
        data.add(new BarEntry(3f,6));
        data.add(new BarEntry(4f,9));
        data.add(new BarEntry(5f,2));
        data.add(new BarEntry(6f,4));

    }
}