package com.example.parkingapp.AppUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UsuariosDto {


    private Long usuarioId;
    private String usuarioCod;
    private String usuarioEmail;
    private String usuarioPassword;
    private String usuarioTipo;
    private String fecha;
    private int celular;
    private Long personaId;
    private Long institucionId;
    private String nombre;
    private String apellido;
    private String ci;


    public UsuariosDto() {
    }


    public UsuariosDto(Long usuarioId, String usuarioCod, String usuarioEmail, String usuarioPassword, String usuarioTipo, String fecha, int celular, Long personaId, Long institucionId, String nombre, String apellido, String ci) {
        this.usuarioId = usuarioId;
        this.usuarioCod = usuarioCod;
        this.usuarioEmail = usuarioEmail;
        this.usuarioPassword = usuarioPassword;
        this.usuarioTipo = usuarioTipo;
        this.fecha = fecha;
        this.celular = celular;
        this.personaId = personaId;
        this.institucionId = institucionId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;

    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioCod() {
        return usuarioCod;
    }

    public void setUsuarioCod(String usuarioCod) {
        this.usuarioCod = usuarioCod;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioPassword() {
        return usuarioPassword;
    }

    public void setUsuarioPassword(String usuarioPassword) {
        this.usuarioPassword = usuarioPassword;
    }

    public String getUsuarioTipo() {
        return usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public Long getInstitucionId() {
        return institucionId;
    }

    public void setInstitucionId(Long institucionId) {
        this.institucionId = institucionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }


}
