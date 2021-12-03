package com.example.e_curso;

import android.app.Application;

import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.UsuarioFacade;

public class MyApplication extends Application {

    private Usuario logeado;
    private long id_user_logged;
    private DBManager db;

    @Override
    public void onCreate() {
        super.onCreate();
        this.db=new DBManager(this.getApplicationContext());
    }

    public long getId_user_logged(){
        return this.id_user_logged;
    }
    public Usuario getUserLogged(){
        return this.logeado;
    }

    public void updateUserLogged(){
        UsuarioFacade uf=new UsuarioFacade(this.db);
        this.logeado=uf.getUsuarioById(id_user_logged);
    }
    public void setLogeado(Usuario logeado) {
        this.logeado = logeado;
    }

    public boolean esAdmin(){
        boolean toret;

        switch (logeado.getRol()){
            case ADMIN: toret=true;
                break;
            default: toret=false;
        }

        return toret;
    }

    public boolean esCreador(){
        boolean toret;

        switch (logeado.getRol()){
            case DIVUL: toret=true;
                break;
            default: toret=false;
        }

        return toret;
    }
    public void setId_user_logged(long id_user_logged) {
        this.id_user_logged = id_user_logged;
    }

    public DBManager getDBManager(){
        return this.db;
    }
}
