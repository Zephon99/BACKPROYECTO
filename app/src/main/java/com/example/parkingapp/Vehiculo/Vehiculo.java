package com.example.parkingapp.Vehiculo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehiculo {

    @SerializedName("vehiculoId")
    @Expose
    private int vehiculoId;

    @SerializedName("vehiculoPlaca")
    @Expose
    private String vehiculoPlaca;

    @SerializedName("vehiculoColor")
    @Expose
    private String vehiculoColor;

    @SerializedName("vehiculoMarca")
    @Expose
    private String vehiculoMarca;

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public String getVehiculoPlaca() {
        return vehiculoPlaca;
    }

    public void setVehiculoPlaca(String vehiculoPlaca) {
        this.vehiculoPlaca = vehiculoPlaca;
    }

    public String getVehiculoColor() {
        return vehiculoColor;
    }

    public void setVehiculoColor(String vehiculoColor) {
        this.vehiculoColor = vehiculoColor;
    }

    public String getVehiculoMarca() {
        return vehiculoMarca;
    }

    public void setVehiculoMarca(String vehiculoMarca) {
        this.vehiculoMarca = vehiculoMarca;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
