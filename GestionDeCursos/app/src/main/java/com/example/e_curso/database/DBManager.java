package com.example.e_curso.database;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper{


    public static final int DB_VERSION =2;
    public static final String DB_NAME ="ECURSOS" ;
    public static final String _id="_id";
    //CONSTANTES PARA USUARIO

    public static final String USUARIO_TABLE_NAME="USUARIO";
    public static final String USUARIO_COLUMN_ID=_id;
    public static final String USUARIO_COLUMN_NAME="USER_NAME";
    public static final String USUARIO_COLUMN_COMPLETE_NAME ="SURNAME";
    public static final String USUARIO_COLUMN_EMAIL="EMAIL";
    public static final String USUARIO_COLUMN_PUBLICADOR="ES_PUBLICADOR";
    public static final String USUARIO_COLUMN_ADMIN="ES_ADMIN";
    public static final String USUARIO_COLUMN_PASSWORD="PASSWORD";
    public static final String USUARIO_COLUMN_ROL="ROL";
    public static final String USUARIO_COLUMN_SOLICITUD="SOLICITUD";

    //CONSTANTES PARA CURSO

    public static final String CURSO_TABLE_NAME="CURSO";
    public static final String CURSO_COLUMN_ID=_id;
    public static final String CURSO_COLUMN_NAME="CURSO_NAME";
    public static final String CURSO_COLUMN_TEMATICA="TEMATICA";
    public static final String CURSO_COLUMN_DESCRIPCION="DESCRIPCION";
    public static final String CURSO_COLUMN_MAX_ASIST="MAX_ASIST";
    public static final String CURSO_COLUMN_ASIST="ASIST";
    public static final String CURSO_COLUMN_FECHA="FECHA_CURSO";
    public static final String CURSO_COLUMN_DURACION="DURACION_CURSO";
    public static final String CURSO_COLUMN_USUARIO_ID=USUARIO_COLUMN_ID;



    //CONSTANTES PARA CURSOS_LISTADOS

    public static final String USUARIO_ASISTE_CURSO_TABLE_NAME="USUARIO_ASISTE_CURSO";
    public static final String USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO="_id_USUARIO";
    public static final String USUARIO_ASISTE_CURSO_COLUMN_ID_CURSO="_id_CURSO";

    public DBManager(@Nullable Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(DB_NAME,"Creando la base de datos");
        try {

            sqLiteDatabase.beginTransaction();
            //CREACION DE TABLA DE USUARIO
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USUARIO_TABLE_NAME +"(" +
                    _id +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USUARIO_COLUMN_NAME + " TEXT NOT NULL UNIQUE," +
                    USUARIO_COLUMN_PASSWORD + " BLOB," +
                    USUARIO_COLUMN_COMPLETE_NAME + " TEXT NOT NULL," +
                    USUARIO_COLUMN_EMAIL + " TEXT NOT NULL," +
                    USUARIO_COLUMN_ROL + " TEXT NOT NULL," +
                    USUARIO_COLUMN_SOLICITUD + " INTEGER" +
                    ")");




            //CREACION DE TABLA DE CURSO
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CURSO_TABLE_NAME +"(" +
                    _id +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CURSO_COLUMN_NAME + " TEXT NOT NULL," +
                    CURSO_COLUMN_DESCRIPCION + " TEXT NOT NULL," +
                    CURSO_COLUMN_TEMATICA + " TEXT NOT NULL," +
                    CURSO_COLUMN_FECHA + " TEXT NOT NULL," +
                    CURSO_COLUMN_DURACION + " REAL NOT NULL," +
                    CURSO_COLUMN_ASIST + " INTEGER DEFAULT 0," +
                    CURSO_COLUMN_MAX_ASIST + " INTEGER NOT NULL," +
                    CURSO_COLUMN_USUARIO_ID +" INTEGER NOT NULL," +
                    "foreign key ("+CURSO_COLUMN_USUARIO_ID+" ) references "+USUARIO_TABLE_NAME+"("+USUARIO_COLUMN_ID+")"+
                    ")");



            //MODIFICAR
            //CREACION DE TABLA DE CURSOS APUNTADOS
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USUARIO_ASISTE_CURSO_TABLE_NAME +"(" +
                    USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+" INTEGER," +
                    USUARIO_ASISTE_CURSO_COLUMN_ID_CURSO + " INTEGER,"+
                    "foreign key ("+USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+" ) references "+USUARIO_TABLE_NAME+"("+USUARIO_COLUMN_ID+"),"+
                    "foreign key ("+USUARIO_ASISTE_CURSO_COLUMN_ID_CURSO+" ) references "+CURSO_TABLE_NAME+"("+CURSO_COLUMN_ID+"),"+
                    "PRIMARY KEY("+USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+","+USUARIO_ASISTE_CURSO_COLUMN_ID_USUARIO+")"+
                    ")");

            sqLiteDatabase.setTransactionSuccessful();

        }catch (SQLException exception){
            Log.e(DBManager.class.getName(), "onCreate", exception);

        }finally {
            sqLiteDatabase.endTransaction();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        try {

            sqLiteDatabase.beginTransaction();
            //ELIMINACION DE TABLA DE USUARIO
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USUARIO_TABLE_NAME);


            //ELIMINACION DE TABLA DE CURSO
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CURSO_TABLE_NAME);


            //ELIMINACION DE TABLA DE CURSOS APUNTADOS
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USUARIO_ASISTE_CURSO_TABLE_NAME);



            sqLiteDatabase.setTransactionSuccessful();

        }catch (SQLException exception){
            Log.e(DBManager.class.getName(), "onCreate", exception);
        }finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
