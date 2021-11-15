package com.example.e_curso.core;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String user, pass, nombre, apellido,  email;
    public static enum Rol{
        ADMIN,
        DIVUL,
        USER
    }
    private  Rol rol;

    public Usuario(){

    }
    public Usuario (String nombre, String apellido, String user, String pass, Rol rol, String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.user = user;
        this.pass = pass;
        this.rol = rol;
        this.email = email;
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

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getApellido(){
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getEmail(){return email; }
    public void setEmail(String email){this.email = email; }

    public String ToString(){
        return this.getUser() + this.getRol() + this.getNombre()
                + this.getApellido() + this.getEmail();
    }
}
