package com.example.parkingapp.ReservasDia;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReservasDia {

    @SerializedName("reserva_dia_id")
    @Expose
    private Long reservaDiaId;

    @SerializedName("reserva_dia_costo")
    @Expose
    private Long reservaDiaCosto;

    @SerializedName("reserva_dia_estado")
    @Expose
    private String reservaDiaEstado;

    @SerializedName("reserva_dia_fecha")
    @Expose
    private String reservaDiaFecha;

    @SerializedName("reserva_dia_hora_fin")
    @Expose
    private String reservaDiaHoraFin;

    @SerializedName("reserva_dia_hora_inicio")
    @Expose
    private String reservaDiaHoraInicio;

    @SerializedName("usuario_id")
    @Expose
    private Long usuarioId;

    @SerializedName("espacio_parqueo_id")
    @Expose
    private Long espacioParqueoId;

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

