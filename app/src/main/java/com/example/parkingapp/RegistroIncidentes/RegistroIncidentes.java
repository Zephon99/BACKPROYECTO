package com.example.parkingapp.RegistroIncidentes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RegistroIncidentes {


    @SerializedName("idControlIncidente")
    @Expose
    private Long idControlIncidente;

    @SerializedName("idUsuarioOriginante")
    @Expose
    private Long idUsuarioOriginante;

    @SerializedName("idUsuarioDestinante")
    @Expose
    private Long idUsuarioDestinante;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("hora")
    @Expose
    private String hora;

    @SerializedName("idControlIncidente")
    @Expose
    private boolean estado;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Long getIdControlIncidente() {
        return idControlIncidente;
    }

    public void setIdControlIncidente(Long idControlIncidente) {
        this.idControlIncidente = idControlIncidente;
    }

    public Long getIdUsuarioOriginante() {
        return idUsuarioOriginante;
    }

    public void setIdUsuarioOriginante(Long idUsuarioOriginante) {
        this.idUsuarioOriginante = idUsuarioOriginante;
    }

    public Long getIdUsuarioDestinante() {
        return idUsuarioDestinante;
    }

    public void setIdUsuarioDestinante(Long idUsuarioDestinante) {
        this.idUsuarioDestinante = idUsuarioDestinante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}
