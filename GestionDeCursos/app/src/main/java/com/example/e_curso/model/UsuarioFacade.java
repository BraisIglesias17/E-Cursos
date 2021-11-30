package com.example.e_curso.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
            toret.setPass(cursor.getBlob(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_PASSWORD)));
            toret.setRol(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_ROL)));
            toret.setSolicitud(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBManager.USUARIO_COLUMN_SOLICITUD))));

            return toret;
    }

    @SuppressLint("Range")
    public static long getID(Cursor usuario) {
        long toret;

        toret=usuario.getLong(usuario.getColumnIndex(DBManager.USUARIO_COLUMN_ID));
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
                          ","+
                          DBManager.USUARIO_COLUMN_SOLICITUD +
                          ") VALUES (?,?,?,?,?,?)", new Object[]{usuario.getUser(),usuario.getNombreCompleto(),usuario.getPass(),usuario.getEmail(),usuario.getRol().toString(),usuario.getEstadoSolicitud()});


    }
    public boolean deleteUsuario(String nombre){

        return super.deleteElement(DBManager.USUARIO_COLUMN_NAME,nombre);
    }
    public boolean deleteUsuariosByFilter(String atributo, Object valor){

        return super.deleteElement(atributo,valor);
    }

    public boolean actualizarUsuario(Usuario usuario){
        ContentValues valores=new ContentValues();
        valores.put(DBManager.USUARIO_COLUMN_NAME,usuario.getUser());
        valores.put(DBManager.USUARIO_COLUMN_COMPLETE_NAME,usuario.getNombreCompleto());
        valores.put(DBManager.USUARIO_COLUMN_EMAIL,usuario.getEmail());
        valores.put(DBManager.USUARIO_COLUMN_ROL,usuario.getRol().toString());
        valores.put(DBManager.USUARIO_COLUMN_SOLICITUD,usuario.getEstadoSolicitud());

        return super.updateElement(DBManager.USUARIO_COLUMN_NAME+" = ?",valores,new String[]{usuario.getUser()});
    }

    public boolean actualizarSolicitudUsuario(Usuario usuario){
        ContentValues valores=new ContentValues();
        valores.put(DBManager.USUARIO_COLUMN_SOLICITUD,usuario.getEstadoSolicitud());
        valores.put(DBManager.USUARIO_COLUMN_ROL,usuario.getRol().toString());

        return super.updateElement(DBManager.USUARIO_COLUMN_NAME+" = ?",valores,new String[]{usuario.getUser()});
    }
    public Cursor getUsuarioCursorById(long id){
        return super.getById(id);
    }

    public Usuario getUsuarioById(long id){
        Cursor c=super.getById(id);
        c.moveToFirst();
        return UsuarioFacade.readUsuario(c);
    }

    public Cursor buscarUsuario(String user){
        return super.getTablaFiltrada(DBManager.USUARIO_COLUMN_NAME,user);
    }

    //este metodo es para buscar un usuario ya sea por nombre de usuario o de pila
    public Cursor buscarUsuariosPorNombres(String nombre){
        Cursor toret=null;
        nombre='%'+nombre+'%';
        toret=this.dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.USUARIO_TABLE_NAME +" WHERE "+DBManager.USUARIO_COLUMN_NAME+ " LIKE ? OR "+DBManager.USUARIO_COLUMN_COMPLETE_NAME+" LIKE ?",new String[]{nombre,nombre});

        return toret;
    }

    public  Cursor getSolicitudesPendientes(){
        return super.getTablaFiltrada(DBManager.USUARIO_COLUMN_SOLICITUD,Integer.toString(Usuario.PENDIENTE));

    }
    public  Cursor getSolicitudesRechazadas(){
        return super.getTablaFiltrada(DBManager.USUARIO_COLUMN_SOLICITUD,Integer.toString(Usuario.RECHAZADO));

    }
    public  Cursor getSolicitudesAceptadas(){
        return super.getTablaFiltrada(DBManager.USUARIO_COLUMN_SOLICITUD,Integer.toString(Usuario.ACEPTADO));

    }

    public Cursor logIn(String user){

        Cursor usuario=super.getTablaFiltrada(DBManager.USUARIO_COLUMN_NAME,user);
        if(usuario.getCount()==1) {
            return usuario;

        }else{
            return null;
        }

    }
}
