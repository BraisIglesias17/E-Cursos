package com.example.e_curso;

import android.app.Application;

import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;

public class MyApplication extends Application {

    private Usuario logeado;
    private int id_user_logged;
    private DBManager db;
    @Override
    public void onCreate() {
        super.onCreate();
        this.db=new DBManager(this.getApplicationContext());
    }

    public int getId_user_logged(){
        return this.id_user_logged;
    }
    public Usuario getUserLogged(){
        return this.logeado;
    }

    public void setLogeado(Usuario logeado) {
        this.logeado = logeado;
    }

    public boolean esAdmin(){
        boolean toret=false;

        switch (logeado.getRol()){
            case ADMIN: toret=true;
                break;
            default: toret=false;
        }

        return toret;
    }
    public void setId_user_logged(int id_user_logged) {
        this.id_user_logged = id_user_logged;
    }

    public DBManager getDBManager(){
        return this.db;
    }
}
