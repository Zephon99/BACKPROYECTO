package com.example.parkingapp.RegistroIncidentes;

import java.util.Date;

public class RegistroIncidentesDto {
    private Long idControlIncidente;


    private Long idUsuarioOriginante;


    private Long idUsuarioDestinante;


    private String fecha;


    private String hora;

    private boolean estado;

    public RegistroIncidentesDto() {
    }

    public RegistroIncidentesDto(Long idControlIncidente, Long idUsuarioOriginante, Long idUsuarioDestinante, String fecha, String hora, boolean estado) {
        this.idControlIncidente = idControlIncidente;
        this.idUsuarioOriginante = idUsuarioOriginante;
        this.idUsuarioDestinante = idUsuarioDestinante;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

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
