package com.example.parkingapp.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.parkingapp.AppUser.AppUser;
import com.example.parkingapp.AppUser.AppUserDto;
import com.example.parkingapp.AppUser.AppUserAPI;
import com.example.parkingapp.AppUser.Usuarios;
import com.example.parkingapp.AppUser.UsuariosAPI;
import com.example.parkingapp.AppUser.UsuariosDto;
import com.example.parkingapp.Autoridades;
import com.example.parkingapp.Booking.Booking;
import com.example.parkingapp.Booking.BookingAPI;
import com.example.parkingapp.Docentes;
import com.example.parkingapp.MainMapActivity;
import com.example.parkingapp.ParkingLot.ParkingLot;
import com.example.parkingapp.R;
import com.example.parkingapp.Registro;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginTabFragment extends Fragment {
    Button loginButton,registroButton;
    Intent intent;
    EditText usernameET,passwordET;
    String un="",pass="";
    AppUserAPI appUserAPI;
    UsuariosAPI usuariosAPI;
    RadioButton docente,autoridad;
    int idUser;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab,container,false);

        intent = new Intent(getActivity(), MainMapActivity.class);
        loginButton = root.findViewById(R.id.loginButton);
        registroButton = root.findViewById(R.id.registroButton);
        usernameET = root.findViewById(R.id.usernameET);
        passwordET = root.findViewById(R.id.passwordET);

        usuariosAPI = retrofit.create(UsuariosAPI.class);
        loginButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                un = usernameET.getText().toString();
                pass = passwordET.getText().toString();

                UsuariosDto usuariosDto = new UsuariosDto();
                usuariosDto.setUsuarioEmail(un);
                usuariosDto.setUsuarioPassword(pass);


                usuariosAPI = retrofit.create(UsuariosAPI.class);

                Call<ResponseBody> call = usuariosAPI.getUsuarioByUsuarioemail(un);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Gson gson = new Gson();
                                Usuarios usuarios = gson.fromJson(jsonObject.getJSONObject("data").toString(), Usuarios.class);


                                Log.e("USUARIO ID",usuarios.getUsuarioId().toString());
                                usuarioLogin(usuarios,un,pass);




                            }catch (JSONException | IOException exception){
                                exception.printStackTrace();
                                Toast.makeText(LoginTabFragment.this.getContext(),"Credenciales incorrectas",Toast.LENGTH_LONG).show();
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


        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Registro.class);// New activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent) ;
                try {
                    LoginTabFragment.this.finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        return root;

    }

    public void usuarioLogin(Usuarios usuarios,String un,String pass){
        if(usuarios.getUsuarioEmail().equals(un) && usuarios.getUsuarioPassword().equals(pass)){
            Intent intent = new Intent(getContext(), Docentes.class);// New activity

            Bundle extras = new Bundle();
            extras.putString("usuarioId", usuarios.getUsuarioId() + "");
            intent.putExtras(extras);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent) ;
            getActivity().finish();

        }else {
            Toast.makeText(LoginTabFragment.this.getContext(),"Credenciales incorrectas",Toast.LENGTH_LONG).show();
        }
    }

}


