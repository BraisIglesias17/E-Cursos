package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.R;

public class MenuPrincipal extends AppCompatActivity {

    public static String CURSOS_APUNTADOS="misCursos";
    public static String CURSOS_OFERTADOS="misCursosOfertados";
    public static String ES_CREADOR="esCreador";

    boolean creador=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_usuario_activity);

        //UPDATE
        Button btVerCursos=this.findViewById(R.id.btVerCursosGenerales);
        this.gestionVerCursos(btVerCursos,false,false);
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosApuntados);
        this.gestionVerCursos(btVerMisCursos,true,false);
        Button btVerCursosPublicados=this.findViewById(R.id.btVerCursosPublicados);
        this.gestionVerCursos(btVerCursosPublicados,false,true);



        //ESTABLECER SI ES UN CREADOR CON UNA SHAREDPREFERENCE

    }

    /*
    private void gestionVerCursosApuntados() {
        Button btVerCursos=this.findViewById(R.id.btVerCursosGenerales);
        btVerCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,true);
                verMisCursos.putExtra(MenuPrincipal.ES_CREADOR, MenuPrincipal.this.creador);
                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });

    }

    private void gestionVerCursosGenerales() {
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosApuntados);
        btVerMisCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,false);
                verMisCursos.putExtra(MenuPrincipal.ES_CREADOR,MenuPrincipal.this.creador);
                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });
    }

    private void gestionVerCursosOfertados() {
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosPublicados);
        btVerMisCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,false);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,false);
                verMisCursos.putExtra(MenuPrincipal.ES_CREADOR,MenuPrincipal.this.creador);
                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });
    }*/
    private void gestionVerCursos(Button b, boolean misCursos, boolean ofertados){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,misCursos);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_APUNTADOS,ofertados);
                verMisCursos.putExtra(MenuPrincipal.ES_CREADOR,MenuPrincipal.this.creador);
                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });
    }
}
