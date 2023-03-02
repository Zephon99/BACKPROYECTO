package com.example.parkingapp.EspacioParqueos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class EspacioParqueosDto {


    private Long espacioParqueoId;


    private Date espacioParqueoFechaDisponible;


    private String espacioParqueoNombre;


    private Integer espacioParqueoTamaño;


    private String espacioParqueoTipo;


    private Long parqueoId;

    public EspacioParqueosDto() {
    }

    public EspacioParqueosDto(Long espacioParqueoId, Date espacioParqueoFechaDisponible, String espacioParqueoNombre, Integer espacioParqueoTamaño, String espacioParqueoTipo, Long parqueoId) {
        this.espacioParqueoId = espacioParqueoId;
        this.espacioParqueoFechaDisponible = espacioParqueoFechaDisponible;
        this.espacioParqueoNombre = espacioParqueoNombre;
        this.espacioParqueoTamaño = espacioParqueoTamaño;
        this.espacioParqueoTipo = espacioParqueoTipo;
        this.parqueoId = parqueoId;
    }

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
