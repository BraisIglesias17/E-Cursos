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
import com.example.e_curso.general.General;
import com.example.e_curso.model.CursoFacade;

import java.util.Date;

public class VerCursos extends AppCompatActivity {


    private static final String MENSAJE_AYUDA_CURSOS = "Manten pulsado sobre un curso para ver las opciones";


    //ControladorCurso
    boolean creador;
    private CursoFacade cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_cursos_activity);

        this.creador=((MyApplication) this.getApplication()).esCreador();
        Curso c=new Curso("Prueba","Creado por USER1","tematica", 0, 30,new Date(123,0,1),0.0, 6);
        Curso c2=new Curso("Prueba2","Descripcion de prueba2","tematica2", 0, 30,new Date(120,11,1),0.0, 0);
        DBManager db=((MyApplication) this.getApplication()).getDBManager();
        this.cursos=new CursoFacade(db);
        this.drawInterface();
        this.gestionAyuda();
        this.triggerCreateCurso();

        Intent intent=this.getIntent();
        String caso=this.getIntent().getStringExtra(MenuPrincipal.CURSOS_ACCESO);

        Cursor cursos=this.cursos.getCursosFechasPrueba();
        switch (caso){
            case "GENERALES": cursos=this.cursos.getCursosFechasPrueba();
                break;
            case "APUNTADOS": //NECESARIO IMPLEMENTAR INSERCCION DE TABLAS
                break;
            case "OFERTADOS": cursos=this.cursos.getCursosFiltrados(DBManager.CURSO_COLUMN_USUARIO_ID,Long.toString(((MyApplication)this.getApplication()).getId_user_logged()));
                break;
        }



        CursoAdapterCursor adapter=new CursoAdapterCursor(this,cursos,this.cursos);



        ListView listViewCursos= (ListView) this.findViewById(R.id.listViewCursosGenerales);

        listViewCursos.setAdapter(adapter);

        listViewCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                VerCursos.this.goToVerDetalle(i);
                return true;
            }
        });


    }

    private void triggerCreateCurso() {

        Button crearCurso=(Button) this.findViewById(R.id.btAddNuevoCurso);

        crearCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerCursos.this,CrearCurso.class);
                Curso c=null;

                intent.putExtra("curso",c);
                VerCursos.this.startActivity(intent);

            }
        });
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
