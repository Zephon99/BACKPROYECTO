package com.example.parkingapp.AppUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Usuarios {

    @SerializedName("usuarioId")
    @Expose
    private Long usuarioId;

    @SerializedName("usuarioCod")
    @Expose
    private String usuarioCod;

    @SerializedName("usuarioEmail")
    @Expose
    private String usuarioEmail;

    @SerializedName("usuarioPassword")
    @Expose
    private String usuarioPassword;

    @SerializedName("usuarioTipo")
    @Expose
    private String usuarioTipo;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("celular")
    @Expose
    private int celular;

    @SerializedName("personaId")
    @Expose
    private Long personaId;

    @SerializedName("institucionId")
    @Expose
    private Long institucionId;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("ci")
    @Expose
    private String ci;



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
