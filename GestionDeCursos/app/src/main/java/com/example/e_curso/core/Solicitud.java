package com.example.e_curso.core;

public class Solicitud {
    private String user;
    private Boolean aceptado;

    public Solicitud(String user, Boolean aceptado){
        this.user = user;
        this.aceptado = aceptado;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(Boolean aceptado) {
        this.aceptado = aceptado;
    }
}
