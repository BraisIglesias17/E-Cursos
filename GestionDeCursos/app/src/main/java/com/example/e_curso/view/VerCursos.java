package com.example.e_curso.view;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.CursosListAdapter;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

import java.util.List;

public class VerCursos extends AppCompatActivity {


    CursosListAdapter adapter;
    List<Curso> cursos;
    boolean creador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_usuario_activity);


        ListView listViewCursos=this.findViewById(R.id.listViewCursosGenerales);

        //ESTA CLASE DEBERA DISPARAR UNA BUSQUEDA SOBRE LA BASE DE DATOS

        if(creador){
            //SE BUSCA LOS CREADOS POR EL USUARIO
        }else{
            //SE BUSCAN TODOS
        }

        listViewCursos.setAdapter(adapter);
    }
}
