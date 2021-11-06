package com.example.e_curso.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.CursosListAdapter;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class VerCursos extends AppCompatActivity {


    private static final String MENSAJE_AYUDA_CURSOS = "Manten pulsado sobre un curso para ver las opciones";
    CursosListAdapter adapter;
    ArrayList<Curso> cursos;
    //ControladorCurso
    boolean creador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_cursos_activity);

        this.creador=Boolean.parseBoolean(this.getIntent().getExtras().get(MenuPrincipal.ES_CREADOR).toString());
        this.drawInterface();


        this.gestionAyuda();

        this.mockUpMethod();
        ListView listViewCursos= (ListView) this.findViewById(R.id.listViewCursosGenerales);
        listViewCursos.setAdapter(this.adapter);


        listViewCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });
        //ESTA CLASE DEBERA DISPARAR UNA BUSQUEDA SOBRE LA BASE DE DATOS
        /*
        Button btGeneral= (Button) this.findViewById(R.id.btGuardarCambios);
        if(creador){
            //SE BUSCA LOS CREADOS POR EL USUARIO
            btGeneral.setText("Guardar cambios");
        }else{
            //SE BUSCAN TODOS
            btGeneral.setText("Apuntarse");
        }*/

        listViewCursos.setAdapter(adapter);
    }

    private void drawInterface(){
        Button btAdd=(Button) this.findViewById(R.id.btAddNuevoCurso);
        if(!this.creador){
            btAdd.setVisibility(Button.GONE);
        }
    }
    private void goToVerDetalle(int i) {
        Intent intent=new Intent(VerCursos.this,VerCursoDetalle.class);
        Curso seleccionado=this.cursos.get(i);
        intent.putExtra("curso",seleccionado);
        this.startActivity(intent);
    }

    private void gestionAyuda() {
        ImageView img=this.findViewById(R.id.imgHelp);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(VerCursos.this);
                builder.setTitle("Ayuda");
                builder.setMessage(MENSAJE_AYUDA_CURSOS);
                builder.setNeutralButton("Volver",null);
                builder.create().show();

            }
        });
    }

    private void mockUpMethod(){
        this.cursos=new ArrayList<>();
        this.cursos.add(new Curso("It will be fun","Forgery","Security",30,new Date(),2));

        this.adapter=new CursosListAdapter(this,this.cursos);


    }
}
