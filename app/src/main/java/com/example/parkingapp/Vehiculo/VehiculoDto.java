package com.example.parkingapp.Vehiculo;

public class VehiculoDto {

    private int vehiculoId;
    private String vehiculoPlaca;
    private String vehiculoMarca;
    private String vehiculoColor;
    private Long usuarioId;
    private String status;

    public VehiculoDto() {
    }

    public VehiculoDto(int vehiculoId, String vehiculoPlaca, String vehiculoMarca, String vehiculoColor, Long usuarioId,String status) {
        this.vehiculoId = vehiculoId;
        this.vehiculoPlaca = vehiculoPlaca;
        this.vehiculoMarca = vehiculoMarca;
        this.vehiculoColor = vehiculoColor;
        this.usuarioId = usuarioId;
        this.status = status;
    }

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

    public String getVehiculoMarca() {
        return vehiculoMarca;
    }

    public void setVehiculoMarca(String vehiculoMarca) {
        this.vehiculoMarca = vehiculoMarca;
    }

    public String getVehiculoColor() {
        return vehiculoColor;
    }

    public void setVehiculoColor(String vehiculoColor) {
        this.vehiculoColor = vehiculoColor;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
