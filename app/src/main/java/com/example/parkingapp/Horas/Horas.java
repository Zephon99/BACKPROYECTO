package com.example.parkingapp.Horas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Horas {

    @SerializedName("horasId")
    @Expose
    private int horasId;

    @SerializedName("horas")
    @Expose
    private String horas;

    public int getHorasId() {
        return horasId;
    }

    public void setHorasId(int horasId) {
        this.horasId = horasId;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
}
