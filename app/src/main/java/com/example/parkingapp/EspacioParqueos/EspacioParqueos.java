package com.example.parkingapp.EspacioParqueos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class EspacioParqueos {

    @SerializedName("espacioParqueoId")
    @Expose
    private Long espacioParqueoId;

    @SerializedName("espacioParqueoFechaDisponible")
    @Expose
    private Date espacioParqueoFechaDisponible;

    @SerializedName("espacioParqueoNombre")
    @Expose
    private String espacioParqueoNombre;

    @SerializedName("espacioParqueoTamaño")
    @Expose
    private Integer espacioParqueoTamaño;

    @SerializedName("espacioParqueoTipo")
    @Expose
    private String espacioParqueoTipo;

    @SerializedName("parqueoId")
    @Expose
    private Long parqueoId;

    public Long getEspacioParqueoId() {
        return espacioParqueoId;
    }

    public void setEspacioParqueoId(Long espacioParqueoId) {
        this.espacioParqueoId = espacioParqueoId;
    }

    public Date getEspacioParqueoFechaDisponible() {
        return espacioParqueoFechaDisponible;
    }

    public void setEspacioParqueoFechaDisponible(Date espacioParqueoFechaDisponible) {
        this.espacioParqueoFechaDisponible = espacioParqueoFechaDisponible;
    }

    public String getEspacioParqueoNombre() {
        return espacioParqueoNombre;
    }

    public void setEspacioParqueoNombre(String espacioParqueoNombre) {
        this.espacioParqueoNombre = espacioParqueoNombre;
    }

    public Integer getEspacioParqueoTamaño() {
        return espacioParqueoTamaño;
    }

    public void setEspacioParqueoTamaño(Integer espacioParqueoTamaño) {
        this.espacioParqueoTamaño = espacioParqueoTamaño;
    }

    public String getEspacioParqueoTipo() {
        return espacioParqueoTipo;
    }

    public void setEspacioParqueoTipo(String espacioParqueoTipo) {
        this.espacioParqueoTipo = espacioParqueoTipo;
    }

    public Long getParqueoId() {
        return parqueoId;
    }

    public void setParqueoId(Long parqueoId) {
        this.parqueoId = parqueoId;
    }
}
