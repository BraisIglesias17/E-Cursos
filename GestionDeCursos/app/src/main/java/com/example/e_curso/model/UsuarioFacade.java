package com.example.e_curso.model;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;


public class UsuarioFacade extends GeneralFacade{




    public UsuarioFacade(DBManager db){

        super(db,DBManager.USUARIO_TABLE_NAME);
    }


    @SuppressLint("Range")
    public static Usuario readUsuario(Cursor cursor){
            Usuario toret=new Usuario();

            toret.setUser(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_NAME)));
            toret.setNombreCompleto(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_COMPLETE_NAME)));
            toret.setEmail(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_EMAIL)));
            toret.setPass(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_PASSWORD)));
            toret.setRol(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_ROL)));

            return toret;
    }


    public Cursor getUsuarios(){
        return super.getElements();
    }

    public boolean insertUsuario(Usuario usuario){

                  return super.createObjectInDB("INSERT INTO " +DBManager.USUARIO_TABLE_NAME + "(" +
                          DBManager.USUARIO_COLUMN_NAME +
                          ","+
                          DBManager.USUARIO_COLUMN_COMPLETE_NAME +
                          ","+
                          DBManager.USUARIO_COLUMN_PASSWORD +
                          ","+
                          DBManager.USUARIO_COLUMN_EMAIL +
                          ","+
                          DBManager.USUARIO_COLUMN_ROL +
                          ") VALUES (?,?,?,?,?)", new Object[]{usuario.getUser(),usuario.getNombreCompleto(),usuario.getPass(),usuario.getEmail(),usuario.getRol().toString()});


    }
    public boolean deleteUsuario(String nombre){

        return super.deleteElement(DBManager.USUARIO_COLUMN_NAME,nombre);
    }
    public boolean deleteUsuariosByFilter(String atributo, Object valor){

        return super.deleteElement(atributo,valor);
    }


    public Cursor getUsuarioCursorById(int id){
        return super.getById(id);
    }

    public Usuario getUsuarioById(int id){
        return UsuarioFacade.readUsuario(super.getById(id));
    }
}
