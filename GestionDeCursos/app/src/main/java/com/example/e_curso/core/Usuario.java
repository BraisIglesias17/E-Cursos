package com.example.e_curso.core;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String user, pass, nombreCompleto,  email;
    private int solicitud; //PUEDE TOMAR VALORES 0 (NO HAY SOLICITUD),1(HAY SOLICITUD PENDIENTE)

    public void setRol(String string) {

        switch (string){
            case "ADMIN": this.rol= Rol.ADMIN;
                break;
            case "DIVUL": this.rol= Rol.DIVUL;
                break;
            case "USER": this.rol= Rol.USER;
                break;
        }
    }

    public int getEstadoSolicitud() {
        return this.solicitud;
    }

    public static enum Rol{
        ADMIN,
        DIVUL,
        USER
    }
    private  Rol rol;

    public Usuario(){

    }
    public Usuario(String user, String apellido, String pass, String email, Rol rol, int solicitud){

        this.nombreCompleto = apellido;
        this.user = user;
        this.pass = pass;
        this.rol = rol;
        this.email = email;
        this.solicitud=solicitud;
    }

    public int getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(int solicitud) {
        this.solicitud = solicitud;
    }

    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user=user;
    }

    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass=pass;
    }

    /*
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }*/

    public String getNombreCompleto(){
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail(){return email; }
    public void setEmail(String email){this.email = email; }

    public String toString(){
        return this.getUser() + this.getRol()
                + this.getNombreCompleto() + this.getEmail();
    }
}
