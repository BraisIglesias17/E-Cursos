package com.example.e_curso.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Curso implements Serializable {

    private String descripcion;
    private String nombreCurso;
    private String tematica;
    private int maxAsistentes;
    private Date fecha;
    private double duracion;
    private int numAsistentes;
    private long idCreador;

    public Curso(String nombreCurso, String descripcion, String tematica, int numAsistentes, int maxAsistentes, Date fecha, double duracion, long idCreador) {
        this.descripcion = descripcion;
        this.nombreCurso = nombreCurso;
        this.maxAsistentes = maxAsistentes;
        this.fecha = fecha;
        this.duracion = duracion;
        this.tematica = tematica;
        this.numAsistentes=numAsistentes;
        this.idCreador=idCreador;
    }

    public Curso(){

    }

    public long getCreador(){
        return this.idCreador;
    }
    public void setCreador(long idCreador){
        this.idCreador=idCreador;
    }
    public void reservarPlaza(){
        this.numAsistentes++;
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
        int anho=1900+this.fecha.getYear();
        int mes=1+this.fecha.getMonth();
        int dia=this.fecha.getDate();

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

    public int getNumAsistentes() {
        return numAsistentes;
    }

    public void setAsistentes(int numAsistentes) {
        this.numAsistentes=numAsistentes;
    }

    public String getFechaDB(){
        SimpleDateFormat isoDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.ROOT );
        isoDateFormat.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
        String strFecha = isoDateFormat.format( this.fecha );
        String toret=strFecha;

        return toret;

    }

    public static int getPosTematica(String tematica){
        switch (tematica){
            case "SALUD": return 0;

            case "TECNOLOGIA": return 1;

            case "DEPORTE": return 2;

            case "MEDITACION": return 3;

            case "ECONOMIA": return 4;

            default:  return 5;
        }
    }

    @Override
    public String toString() {
        return "Curso{" +
                "descripcion='" + descripcion + '\'' +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", tematica='" + tematica + '\'' +
                ", maxAsistentes=" + maxAsistentes +
                ", fecha=" + fecha +
                ", duracion=" + duracion +
                ", numAsistentes=" + numAsistentes +
                '}';
    }
}
