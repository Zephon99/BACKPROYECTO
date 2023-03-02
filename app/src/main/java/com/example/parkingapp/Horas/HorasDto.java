package com.example.parkingapp.Horas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HorasDto {


    private int horasId;


    private String horas;

    public HorasDto() {
    }

    public HorasDto(int horasId, String horas) {
        this.horasId = horasId;
        this.horas = horas;
    }

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
