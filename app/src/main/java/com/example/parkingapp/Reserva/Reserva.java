package com.example.parkingapp.Reserva;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reserva {


    @SerializedName("reservaId")
    @Expose
    private int reservaId;

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    @SerializedName("espacioParqueoid")
    @Expose
    private Long espacioParqueoid;

    @SerializedName("horasId")
    @Expose
    private int horasId;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEspacioParqueoid() {
        return espacioParqueoid;
    }

    public void setEspacioParqueoid(Long espacioParqueoid) {
        this.espacioParqueoid = espacioParqueoid;
    }

    public int getHorasId() {
        return horasId;
    }

    public void setHorasId(int horasId) {
        this.horasId = horasId;
    }
}
