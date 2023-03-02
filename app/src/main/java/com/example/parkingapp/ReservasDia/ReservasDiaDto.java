package com.example.parkingapp.ReservasDia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReservasDiaDto {

    private Long reservaDiaId;


    private Long reservaDiaCosto;


    private String reservaDiaEstado;


    private String reservaDiaFecha;


    private String reservaDiaHoraFin;


    private String reservaDiaHoraInicio;


    private Long usuarioId;

    private Long espacioParqueoId;


    public ReservasDiaDto() {
    }

    public ReservasDiaDto(Long reservaDiaId, Long reservaDiaCosto, String reservaDiaEstado, String reservaDiaFecha, String reservaDiaHoraFin, String reservaDiaHoraInicio, Long usuarioId, Long espacioParqueoId) {
        this.reservaDiaId = reservaDiaId;
        this.reservaDiaCosto = reservaDiaCosto;
        this.reservaDiaEstado = reservaDiaEstado;
        this.reservaDiaFecha = reservaDiaFecha;
        this.reservaDiaHoraFin = reservaDiaHoraFin;
        this.reservaDiaHoraInicio = reservaDiaHoraInicio;
        this.usuarioId = usuarioId;
        this.espacioParqueoId = espacioParqueoId;
    }

    public Long getEspacioParqueoId() {
        return espacioParqueoId;
    }

    public void setEspacioParqueoId(Long espacioParqueoId) {
        this.espacioParqueoId = espacioParqueoId;
    }

    public Long getReservaDiaId() {
        return reservaDiaId;
    }

    public void setReservaDiaId(Long reservaDiaId) {
        this.reservaDiaId = reservaDiaId;
    }

    public Long getReservaDiaCosto() {
        return reservaDiaCosto;
    }

    public void setReservaDiaCosto(Long reservaDiaCosto) {
        this.reservaDiaCosto = reservaDiaCosto;
    }

    public String getReservaDiaEstado() {
        return reservaDiaEstado;
    }

    public void setReservaDiaEstado(String reservaDiaEstado) {
        this.reservaDiaEstado = reservaDiaEstado;
    }

    public String getReservaDiaFecha() {
        return reservaDiaFecha;
    }

    public void setReservaDiaFecha(String reservaDiaFecha) {
        this.reservaDiaFecha = reservaDiaFecha;
    }

    public String getReservaDiaHoraFin() {
        return reservaDiaHoraFin;
    }

    public void setReservaDiaHoraFin(String reservaDiaHoraFin) {
        this.reservaDiaHoraFin = reservaDiaHoraFin;
    }

    public String getReservaDiaHoraInicio() {
        return reservaDiaHoraInicio;
    }

    public void setReservaDiaHoraInicio(String reservaDiaHoraInicio) {
        this.reservaDiaHoraInicio = reservaDiaHoraInicio;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

