package com.example.e_curso.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.CursoAdapterCursor;
import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.CursoFacade;

import java.util.Date;

public class VerCursos extends AppCompatActivity {


    private static final String MENSAJE_AYUDA_CURSOS = "Manten pulsado sobre un curso para ver las opciones";


    //ControladorCurso
    boolean creador;
    private CursoFacade cursos;

    //no es necesario ARRAYLIST
    //TODO CON CURSORES SOBRE BUSQUEDAS EN LA BASE DE DATOS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_cursos_activity);

        this.creador=((MyApplication) this.getApplication()).esCreador();
        Curso c=new Curso("Prueba","Descripcion de prueba","tematica", 0, 30,new Date(2023,12,1),0.0, 0);
        DBManager db=((MyApplication) this.getApplication()).getDBManager();
        this.cursos=new CursoFacade(db);

        this.cursos.insertaCurso(c);

        Cursor cursos=this.cursos.getCursos();
        CursoAdapterCursor adapter=new CursoAdapterCursor(this,cursos,this.cursos);
        this.drawInterface();

        this.gestionAyuda();


        ListView listViewCursos= (ListView) this.findViewById(R.id.listViewCursosGenerales);

        listViewCursos.setAdapter(adapter);

        listViewCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                VerCursos.this.goToVerDetalle(i);
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


    }

    private void drawInterface(){
        Button btAdd=(Button) this.findViewById(R.id.btAddNuevoCurso);
        if(!this.creador){
            btAdd.setVisibility(Button.GONE);
        }
    }
    private void goToVerDetalle(int i) {
        Intent intent=new Intent(VerCursos.this,VerCursoDetalle.class);
        //Curso seleccionado=this.cursos.get(i);
        //intent.putExtra("curso",seleccionado);
        //this.startActivity(intent);
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
}
