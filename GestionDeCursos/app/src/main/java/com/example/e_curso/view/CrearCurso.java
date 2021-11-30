package com.example.e_curso.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.model.CursoFacade;

import java.time.LocalDateTime;
import java.util.Date;

public class CrearCurso extends AppCompatActivity{

    private boolean creador;
    private int anho;
    private int mes;
    private int dia;
    private Date fecha;
    private long idCurso;
    private EditText etFecha;


    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.crear_modificar_curso);
            this.creador=((MyApplication) this.getApplication()).esCreador();

            Curso c=(Curso) this.getIntent().getExtras().get("curso");
            this.idCurso=this.getIntent().getExtras().getLong("idCurso");

            Button bt=(Button) this.findViewById(R.id.btGuardarCambiosCurso);
            Button btDel=(Button)  this.findViewById(R.id.btEliminarCurso);

            if(c==null){

                this.anho= 1900+(new Date().getYear());
                this.mes=1;
                this.dia=1;
                bt.setText("Crear curso");
                btDel.setVisibility(View.GONE);
                this.crearSpinner("OTRO");
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CrearCurso.this.addCurso();
                    }
                });
            }else{
                this.anho= (1900+c.getFecha().getYear());
                this.mes=(1+c.getFecha().getMonth());
                this.dia=c.getFecha().getDate();
                this.fecha=new Date(c.getFecha().getYear(),c.getFecha().getMonth(),c.getFecha().getDate());
                this.crearSpinner(c.getTematica());
                this.rellenarDatos(c);

                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CrearCurso.this.modifyCurso();
                    }
                });

                btDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CrearCurso.this.eliminarCurso(c);
                        //Toast.makeText(CrearCurso.this,"El curso ha sido eliminado correctamente",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            this.escogerFecha();

        }

    private void crearSpinner(String porDefecto) {

        Spinner spinner = (Spinner) findViewById(R.id.spinnerTematica);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tematicas_array, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(Curso.getPosTematica(porDefecto));
        int pos=adapter.getPosition(porDefecto);
        spinner.setAdapter(adapter);
        spinner.setSelection(pos);

    }

    private void addCurso(){

        EditText txtNombreCurso=(EditText)this.findViewById(R.id.etNombreCurso);
        EditText txtDescripcion=(EditText)this.findViewById(R.id.etResumenCurso);
        EditText txtFecha=(EditText)this.findViewById(R.id.etFechaCurso);
        EditText txtDuracion=(EditText)this.findViewById(R.id.etDuracionCurso);
        EditText txtNumAsist=(EditText)this.findViewById(R.id.etMaxAsistentes);
        Spinner txtTematica= (Spinner) this.findViewById(R.id.spinnerTematica);

        String nombre=txtNombreCurso.getText().toString();
        String descripcion=txtDescripcion.getText().toString();
        String tematica=(String) txtTematica.getSelectedItem();
        //Date fecha=this.escogerFecha();
        Double duracion=Double.parseDouble(txtDuracion.getText().toString());
        int numAsist=Integer.parseInt(txtNumAsist.getText().toString());
        long id_Creador=((MyApplication) this.getApplication()).getId_user_logged();
        Curso toAdd=new Curso(nombre,descripcion,tematica,0,numAsist,this.fecha,duracion,id_Creador);

        CursoFacade cf=new CursoFacade(((MyApplication) this.getApplication()).getDBManager());
        cf.insertaCurso(toAdd);
        CrearCurso.this.finish();
        Toast.makeText(CrearCurso.this,"El curso ha sido creado correctamente",Toast.LENGTH_SHORT).show();
    }

    private void modifyCurso(){

        EditText txtNombreCurso=(EditText)this.findViewById(R.id.etNombreCurso);
        EditText txtDescripcion=(EditText)this.findViewById(R.id.etResumenCurso);
        EditText txtFecha=(EditText)this.findViewById(R.id.etFechaCurso);
        EditText txtDuracion=(EditText)this.findViewById(R.id.etDuracionCurso);
        EditText txtNumAsist=(EditText)this.findViewById(R.id.etMaxAsistentes);
        Spinner txtTematica= (Spinner) this.findViewById(R.id.spinnerTematica);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Quiere guardar los cambios?")
                .setTitle("Guardar Cambios");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String nombre=txtNombreCurso.getText().toString();
                String descripcion=txtDescripcion.getText().toString();
                String tematica=(String) txtTematica.getSelectedItem();
                Date fecha=CrearCurso.this.fecha;
                Double duracion=Double.parseDouble(txtDuracion.getText().toString());
                int numAsist=Integer.parseInt(txtNumAsist.getText().toString());
                long id_Creador=((MyApplication) CrearCurso.this.getApplication()).getId_user_logged();
                Curso toModify=new Curso(nombre,descripcion,tematica,0,numAsist,fecha,duracion,id_Creador);

                CursoFacade cf=new CursoFacade(((MyApplication) CrearCurso.this.getApplication()).getDBManager());
                cf.updateCurso(toModify,CrearCurso.this.idCurso);
                CrearCurso.this.finish();
                Toast.makeText(CrearCurso.this,"El curso ha sido modificado correctamente",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //CrearCurso.this.finish();
            }
        });

        builder.create().show();
    }

    private void eliminarCurso(Curso c){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está usted segur@ de que desea eliminar este curso?")
                .setTitle("Eliminar Curso");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                CursoFacade cf=new CursoFacade(((MyApplication) CrearCurso.this.getApplication()).getDBManager());
                cf.deleteCurso(c);
                CrearCurso.this.finish();
                Toast.makeText(CrearCurso.this,"El curso ha sido eliminado correctamente",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.create().show();

    }

    private void rellenarDatos(Curso c){

            TextView nombreCurso=(TextView)this.findViewById(R.id.tvNombreCursoDetalle);
            TextView descripcion=(TextView)this.findViewById(R.id.tvResumenCursoDetalle);
            TextView fecha=(TextView)this.findViewById(R.id.tvFechaCursoDetalle);
            TextView duracion=(TextView)this.findViewById(R.id.tvDuracionCursoDetalle);
            TextView numAsist=(TextView)this.findViewById(R.id.tvNumMaximoAsistentesDetalle);

            EditText txtNombreCurso=(EditText)this.findViewById(R.id.etNombreCurso);
            EditText txtDescripcion=(EditText)this.findViewById(R.id.etResumenCurso);
            EditText txtFecha=(EditText)this.findViewById(R.id.etFechaCurso);
            EditText txtDuracion=(EditText)this.findViewById(R.id.etDuracionCurso);
            EditText txtNumAsist=(EditText)this.findViewById(R.id.etMaxAsistentes);


            txtNombreCurso.setText(c.getNombreCurso());
            txtDescripcion.setText(c.getDescripcion());
            txtFecha.setText(c.getFechaFormato());
            txtDuracion.setText(Double.toString(c.getDuracion()));
            txtNumAsist.setText(Integer.toString(c.getMaxAsistentes()));
    }

    private void escogerFecha() {

        this.etFecha=(EditText)this.findViewById(R.id.etFechaCurso);
        TextView fecha=(TextView) this.findViewById(R.id.etFechaCurso);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dlg=new DatePickerDialog(CrearCurso.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                //año la resta de 1900, al mes hay que restarle uno
                                CrearCurso.this.anho=(i-1900);
                                CrearCurso.this.mes=(i1);
                                CrearCurso.this.dia=i2;
                                CrearCurso.this.etFecha.setText(i2+"/"+(i1+1)+"/"+(i));
                                CrearCurso.this.fecha=new Date(CrearCurso.this.anho,CrearCurso.this.mes,CrearCurso.this.dia);
                            }
                        },CrearCurso.this.anho,CrearCurso.this.mes,CrearCurso.this.dia);
                dlg.show();
            }
        });

    }

}
