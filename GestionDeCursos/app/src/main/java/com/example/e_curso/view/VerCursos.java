package com.example.e_curso.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.CursosListAdapter;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.CursoFacade;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

        DBManager db=((MyApplication) this.getApplication()).getDBManager();
        this.cursos=new CursoFacade(db);


        SQLiteDatabase jk=db.getReadableDatabase();
        this.creador=Boolean.parseBoolean(this.getIntent().getExtras().get(MenuPrincipal.ES_CREADOR).toString());
        this.drawInterface();

        this.gestionAyuda();


        ListView listViewCursos= (ListView) this.findViewById(R.id.listViewCursosGenerales);

        String[] cursos = {"Curso 1", "Curso 2", "Curso 3", "Curso 4", "Curso 5", "Curso 6", "Curso 7", "Curso 8"};
        ArrayAdapter<String> adp=new ArrayAdapter<String>(VerCursos.this, android.R.layout.simple_list_item_1, cursos);
        listViewCursos.setAdapter(adp);


        listViewCursos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                registerForContextMenu(listViewCursos);
                //VerCursos.this.goToVerDetalle(i);
                return false;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int id=v.getId();
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.menu_contextual_lvcursos, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
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
