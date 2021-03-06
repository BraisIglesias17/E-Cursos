package com.example.e_curso.model;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.e_curso.core.Curso;
import com.example.e_curso.database.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class CursoFacade extends GeneralFacade{



    public CursoFacade(DBManager dbManager){
        super(dbManager,DBManager.CURSO_TABLE_NAME);
    }



    //preguntar por esto
    @SuppressLint("Range")
    public static Curso readCurso(Cursor cursor){
        Curso toret=new Curso();

        toret.setNombreCurso(cursor.getString(cursor.getColumnIndex(DBManager.CURSO_COLUMN_NAME)));
        toret.setDescripcion(cursor.getString(cursor.getColumnIndex(DBManager.CURSO_COLUMN_DESCRIPCION)));
        toret.setDuracion(cursor.getDouble(cursor.getColumnIndex(DBManager.CURSO_COLUMN_DURACION)));
        toret.setTematica(cursor.getString(cursor.getColumnIndex(DBManager.CURSO_COLUMN_TEMATICA)));
        toret.setMaxAsistentes(cursor.getInt(cursor.getColumnIndex(DBManager.CURSO_COLUMN_MAX_ASIST)));
        toret.setAsistentes(cursor.getInt(cursor.getColumnIndex(DBManager.CURSO_COLUMN_ASIST)));
        toret.setFecha(convertDate(cursor.getString(cursor.getColumnIndex(DBManager.CURSO_COLUMN_FECHA))));
        toret.setCreador(cursor.getLong(cursor.getColumnIndex(DBManager.CURSO_COLUMN_USUARIO_ID)));


        return toret;
    }
    @SuppressLint("Range")
    public static long getID(Cursor curso) {
        long toret;

        toret=curso.getLong(curso.getColumnIndex(DBManager.CURSO_COLUMN_ID));
        return toret;
    }


    public Cursor getCursos(){
            return super.getElements();
    }
    public Cursor getCursosActivos(){

        Cursor toret=null;



        return toret;
    }

    public boolean insertaCurso(Curso curso){

        return super.createObjectInDB("INSERT INTO " +DBManager.CURSO_TABLE_NAME +
                "(" +
                DBManager.CURSO_COLUMN_NAME +
                ","+
                DBManager.CURSO_COLUMN_DESCRIPCION+
                ","+
                DBManager.CURSO_COLUMN_TEMATICA +
                ","+
                DBManager.CURSO_COLUMN_DURACION +
                ","+
                DBManager.CURSO_COLUMN_FECHA +
                ","+
                DBManager.CURSO_COLUMN_MAX_ASIST +
                ","+
                DBManager.CURSO_COLUMN_USUARIO_ID +
                ") VALUES (?,?,?,?,?,?,?)",new Object[]{curso.getNombreCurso(),curso.getDescripcion(),curso.getTematica(),
                Double.toString(curso.getDuracion()),curso.getFechaDB(),Integer.toString(curso.getMaxAsistentes()),curso.getCreador()});

    }


    public boolean deleteCurso(Curso curso){
        return super.deleteElement(DBManager.CURSO_COLUMN_NAME,curso.getNombreCurso());
    }

    public boolean updateCurso(Curso c){
        ContentValues valores=new ContentValues();
        valores.put(DBManager.CURSO_COLUMN_NAME,c.getNombreCurso());
        valores.put(DBManager.CURSO_COLUMN_DESCRIPCION,c.getDescripcion());
        valores.put(DBManager.CURSO_COLUMN_DURACION,c.getDuracion());
        valores.put(DBManager.CURSO_COLUMN_FECHA,c.getFechaDB());
        valores.put(DBManager.CURSO_COLUMN_MAX_ASIST,c.getMaxAsistentes());
        valores.put(DBManager.CURSO_COLUMN_ASIST,c.getNumAsistentes());
        valores.put(DBManager.CURSO_COLUMN_TEMATICA,c.getTematica());

        return super.updateElement(DBManager.CURSO_COLUMN_NAME+" = ?",valores,new String[]{c.getNombreCurso()});
    }

    public boolean updateCurso(Curso c, long id){
        ContentValues valores=new ContentValues();
        valores.put(DBManager.CURSO_COLUMN_NAME,c.getNombreCurso());
        valores.put(DBManager.CURSO_COLUMN_DESCRIPCION,c.getDescripcion());
        valores.put(DBManager.CURSO_COLUMN_DURACION,c.getDuracion());
        valores.put(DBManager.CURSO_COLUMN_FECHA,c.getFechaDB());
        valores.put(DBManager.CURSO_COLUMN_MAX_ASIST,c.getMaxAsistentes());
        valores.put(DBManager.CURSO_COLUMN_ASIST,c.getNumAsistentes());
        valores.put(DBManager.CURSO_COLUMN_TEMATICA,c.getTematica());

        return super.updateElement(DBManager.CURSO_COLUMN_ID+" = ?",valores,new String[]{Long.toString(id)});
    }
    public Cursor getCursosFiltrados(String atributo,String tematica){
        return super.getTablaFiltrada(atributo,tematica);
    }

    public boolean actualizarAsistentes(String name, int numero){
        SQLiteDatabase writableDatabase = super.dbManager.getWritableDatabase();
        boolean toret=false;
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL("UPDATE "+DBManager.CURSO_TABLE_NAME+" SET "+DBManager.CURSO_COLUMN_ASIST +" = ? "+
                    "WHERE "+DBManager.CURSO_COLUMN_NAME +" == ?",new Object[]{numero,name});
            writableDatabase.setTransactionSuccessful();
            toret=true;
        }catch(SQLException exception){
            Log.e(CursoFacade.class.getName(), "createCurso", exception);
        }finally {
            writableDatabase.endTransaction();
            return toret;
        }
    }

    public Cursor getCursosDePublicador(long id){
        return super.getTablaFiltrada(DBManager.CURSO_COLUMN_USUARIO_ID,id+"");
    }
    private static Date convertDate(String cadena){
        SimpleDateFormat isoDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.ROOT );
        isoDateFormat.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
        String strFecha = cadena;
        Date fecha=null;
        try {
            fecha = isoDateFormat.parse(strFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fecha;
    }

    public Cursor getCursosFechasPrueba(){
        Cursor toret=null;

        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.CURSO_TABLE_NAME
                +" WHERE "+DBManager.CURSO_COLUMN_FECHA +">= DATE('now')", null);

        return toret;
    }
    public Cursor getCursosFechasPruebaDivulgador(String id){
        Cursor toret=null;

        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.CURSO_TABLE_NAME
                +" WHERE "+DBManager.CURSO_COLUMN_FECHA +">= DATE('now') AND "+DBManager.CURSO_COLUMN_USUARIO_ID+" == ?", new String[]{id});

        return toret;
    }

    public Cursor getCursosFiltradosConFecha(String atributo,String tematica){
        Cursor toret=null;
        tematica='%'+tematica+'%';
        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.CURSO_TABLE_NAME
                +" WHERE "+DBManager.CURSO_COLUMN_FECHA +">= DATE('now') AND "+atributo+" LIKE ?", new String[]{tematica});

        return toret;
    }

    public Cursor getCursosFiltradosConFechaDivulgador(String atributo,String tematica,long id){
        Cursor toret=null;
        tematica='%'+tematica+'%';
        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.CURSO_TABLE_NAME
                +" WHERE "+DBManager.CURSO_COLUMN_FECHA +">= DATE('now') AND "+atributo+" LIKE ? AND "+DBManager.CURSO_COLUMN_USUARIO_ID+" == ?", new String[]{tematica,Long.toString(id)});

        return toret;
    }

    public Cursor getCursosFiltradosDivulgador(String atributo,String tematica,String id){
        Cursor toret=null;
        tematica='%'+tematica+'%';
        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.CURSO_TABLE_NAME
                +" WHERE "+atributo+" LIKE ? AND "+DBManager.CURSO_COLUMN_USUARIO_ID+" == ?", new String[]{tematica,id});

        return toret;
    }

}
