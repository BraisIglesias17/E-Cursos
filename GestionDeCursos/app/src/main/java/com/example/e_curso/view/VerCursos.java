package com.example.e_curso.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    private boolean creador;
    private CursoFacade cursos;
    private CursoAdapterCursor adapterCursos;

    private String caso;

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
        this.caso=this.getIntent().getStringExtra(MenuPrincipal.CURSOS_ACCESO);

        Cursor cursos=this.cursos.getCursosFechasPrueba();
        TextView titulo=this.findViewById(R.id.tvVerCursos);
        switch (this.caso){
            case "GENERALES": titulo.setText("Cursos"); cursos=this.cursos.getCursosFechasPrueba();
                break;
            case "APUNTADOS": titulo.setText("Mis Cursos"); //NECESARIO IMPLEMENTAR INSERCCION DE TABLAS
                break;
            case "OFERTADOS": titulo.setText("Mis cursos publicados");  cursos=this.cursos.getCursosFiltrados(DBManager.CURSO_COLUMN_USUARIO_ID,Long.toString(((MyApplication)this.getApplication()).getId_user_logged()));
                break;
        }



        this.adapterCursos=new CursoAdapterCursor(this,cursos,this.cursos);


        //gestion de busquedas
        this.setBusqueda();

        //filtro
        Button btFiltrar=this.findViewById(R.id.btFiltrarCursos);
        btFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerCursos.this.setFiltro();
            }
        });


        ListView listViewCursos= (ListView) this.findViewById(R.id.listViewCursosGenerales);

        listViewCursos.setAdapter(adapterCursos);

        listViewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VerCursos.this.goToVerDetalle(i);
            }
        });



    }

    private Spinner crearSpinner() {
        Spinner spinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tematicas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spinner.setGravity(Gravity.CENTER);
        spinner.setBackgroundColor(0xBA68C8);*/
        spinner.setAdapter(adapter);
        return spinner;

    }
    private void rellenarSpinner(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tematicas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*spinner.setGravity(Gravity.CENTER);
        spinner.setBackgroundColor(0xBA68C8);*/
        spinner.setAdapter(adapter);
    }

    private void setFiltro() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Filtro");
        builder.setMessage("Selecciona una tematica");

        View vista=View.inflate(this,R.layout.filtro_cursos_layour,null);
        Spinner spinner1=vista.findViewById(R.id.spinnerFiltroCursos);
        this.rellenarSpinner(spinner1);
        CheckBox cbFiltroFecha=vista.findViewById(R.id.cbFiltroCursos);


        builder.setView(vista);
        builder.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String filtro=(String) spinner1.getSelectedItem();
                if(filtro.equals("OTRO")){
                    filtro="%";
                }
                Cursor c=null;
                if(cbFiltroFecha.isChecked()){
                    c=VerCursos.this.cursos.getCursosFiltrados(DBManager.CURSO_COLUMN_TEMATICA,filtro);
                }else{
                    c=VerCursos.this.cursos.getCursosFiltradosConFecha(DBManager.CURSO_COLUMN_TEMATICA,filtro);
                }

                VerCursos.this.adapterCursos.changeCursor(c);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void setBusqueda() {
        EditText patron=this.findViewById(R.id.etBuscarCurso);

        patron.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filtro=charSequence.toString();
                switch (VerCursos.this.caso){
                    case "GENERALES": if(filtro==""){
                                            Cursor c=VerCursos.this.cursos.getCursosFechasPrueba();
                                            VerCursos.this.adapterCursos.changeCursor(c);
                                    }else{
                                             Cursor c=VerCursos.this.cursos.getCursosFiltrados(DBManager.CURSO_COLUMN_NAME,filtro);
                                                VerCursos.this.adapterCursos.changeCursor(c);
                                    }
                        break;
                    case "APUNTADOS": //NECESARIO IMPLEMENTAR INSERCCION DE TABLAS
                        break;
                    case "OFERTADOS":
                                        Long id=((MyApplication)VerCursos.this.getApplication()).getId_user_logged();
                                        if(filtro==""){
                                            Cursor c=VerCursos.this.cursos.getCursosFiltrados(DBManager.CURSO_COLUMN_USUARIO_ID,Long.toString(id));
                                            VerCursos.this.adapterCursos.changeCursor(c);
                                        }else{
                                            Cursor c=VerCursos.this.cursos.getCursosFiltradosDivulgador(DBManager.CURSO_COLUMN_NAME,filtro,Long.toString(id));
                                            VerCursos.this.adapterCursos.changeCursor(c);
                                        }
                        break;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

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
