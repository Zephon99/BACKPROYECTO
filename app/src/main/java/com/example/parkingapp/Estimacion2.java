package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.parkingapp.login.LoginActivity;

import java.util.Calendar;

public class Estimacion2 extends AppCompatActivity {

    private DatePicker datePicker;
    private TextView dateValueTextView;
    private Button updateDateButton,volver;
    String month,day,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimacion2);


        datePicker = (DatePicker) findViewById(R.id.datePicker);
        updateDateButton = (Button) findViewById(R.id.estimarButton1);
        volver = (Button) findViewById(R.id.volverButton1);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Estimacion2.this, Autoridades.class));
                finish();
            }
        });
        // disable dates before today
        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);

        updateDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month = String.valueOf(datePicker.getMonth()+1);
                year = String.valueOf(datePicker.getYear());
                day = String.valueOf(datePicker.getDayOfMonth());
                Log.e("FECHA SELECCIONADA",(datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth() + "-" + datePicker.getYear());


                Intent i = new Intent(Estimacion2.this, ResultadoEstimacion2.class);

                startActivity(i);



            }
        });

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("??Esta seguro que quiere salir de la aplicaci??n?");
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



    }
}