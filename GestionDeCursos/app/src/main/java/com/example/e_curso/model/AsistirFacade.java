package com.example.e_curso.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.lang.UScript;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

import com.example.e_curso.database.DBManager;

public class AsistirFacade extends  GeneralFacade{



    public AsistirFacade(DBManager dbManager){
        super(dbManager,DBManager.USUARIO_ASISTE_CURSO_TABLE_NAME);

    }



    public boolean insertarParticipacion(long userID,long cursoID){
        return super.createObjectInDB("INSERT INTO " +DBManager.USUARIO_ASISTE_CURSO_TABLE_NAME + "(" +
                DBManager.USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO +
                ","+
                DBManager.USUARIO_ASISTE_CURSO_COLUMN_ID_CURSO +
               ") VALUES (?,?)", new Object[]{Long.toString(userID),Long.toString(cursoID)});


    }


    public Cursor getCursosApuntados(long userID){
        Cursor toret=null;

        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.USUARIO_ASISTE_CURSO_TABLE_NAME +" NATURAL JOIN "+DBManager.CURSO_TABLE_NAME+" WHERE "+DBManager.USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+" == ? ",new String[]{Long.toString(userID)});

        return toret;
    }
    public boolean eliminarParticipacion(long userID,long cursoID){
        SQLiteDatabase db=this.dbManager.getWritableDatabase();

        boolean toret=false;

        try{
            db.execSQL("DELETE FROM "+DBManager.USUARIO_ASISTE_CURSO_TABLE_NAME+" WHERE "+ DBManager.USUARIO_ASISTE_CURSO_COLUMN_ID_CURSO+" == ? AND "+DBManager.USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+" == ?"
                    ,new Object[]{Long.toString(cursoID),Long.toString(userID)});
            toret=true;
        }catch (Exception exc){
            Log.e("DELETE ACTION",exc.getMessage());
        }

        return toret;
    }
}
