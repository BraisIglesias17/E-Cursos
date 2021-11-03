package com.example.e_curso.core;

import java.io.Serializable;
import java.util.Date;

public class Curso implements Serializable {

    private String descripcion;
    private String nombreCurso;
    private String tematica;
    private int maxAsistentes;
    private Date fecha;
    private double duracion;

    public Curso(String descripcion, String nombreCurso,String tematica, int maxAsistentes, Date fecha, double duracion) {
        this.descripcion = descripcion;
        this.nombreCurso = nombreCurso;
        this.maxAsistentes = maxAsistentes;
        this.fecha = fecha;
        this.duracion = duracion;
        this.tematica = tematica;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getMaxAsistentes() {
        return maxAsistentes;
    }

    public void setMaxAsistentes(int maxAsistentes) {
        this.maxAsistentes = maxAsistentes;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaFormato(){
        int anho=this.fecha.getYear();
        int mes=this.fecha.getMonth();
        int dia=this.fecha.getDay();

        return dia+"/"+mes+"/"+anho;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }
}
