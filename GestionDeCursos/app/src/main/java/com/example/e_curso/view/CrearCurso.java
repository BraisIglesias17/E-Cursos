package com.example.e_curso.view;

import android.app.DatePickerDialog;
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
            if(c==null){

                this.anho= 1900+(new Date().getYear());
                this.mes=1;
                this.dia=1;
                bt.setText("Crear curso");
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
          this.finish();
        }

    private void modifyCurso(){
        EditText txtNombreCurso=(EditText)this.findViewById(R.id.etNombreCurso);
        EditText txtDescripcion=(EditText)this.findViewById(R.id.etResumenCurso);
        EditText txtFecha=(EditText)this.findViewById(R.id.etFechaCurso);
        EditText txtDuracion=(EditText)this.findViewById(R.id.etDuracionCurso);
        EditText txtNumAsist=(EditText)this.findViewById(R.id.etMaxAsistentes);
        Spinner txtTematica= (Spinner) this.findViewById(R.id.spinnerTematica);

        String nombre=txtNombreCurso.getText().toString();
        String descripcion=txtDescripcion.getText().toString();
        String tematica=(String) txtTematica.getSelectedItem();
        Date fecha=this.fecha;
        Double duracion=Double.parseDouble(txtDuracion.getText().toString());
        int numAsist=Integer.parseInt(txtNumAsist.getText().toString());
        long id_Creador=((MyApplication) this.getApplication()).getId_user_logged();
        Curso toModify=new Curso(nombre,descripcion,tematica,0,numAsist,fecha,duracion,id_Creador);

        CursoFacade cf=new CursoFacade(((MyApplication) this.getApplication()).getDBManager());
        cf.updateCurso(toModify,this.idCurso);
        Toast.makeText(this,"El curso ha sido modificado",Toast.LENGTH_SHORT);
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
        TextView fecha=(TextView) this.findViewById(R.id.tvFechaCursoDetalle);
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
