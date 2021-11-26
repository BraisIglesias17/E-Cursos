package com.example.e_curso.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.model.CursoFacade;

import java.util.Date;

public class CrearCurso extends AppCompatActivity{

        private boolean creador;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.crear_modificar_curso);
            this.creador=((MyApplication) this.getApplication()).esCreador();

            Curso c=(Curso) this.getIntent().getExtras().get("curso");
            long id=this.getIntent().getExtras().getLong("idCurso");

            Button bt=(Button) this.findViewById(R.id.btGuardarCambiosCurso);
            if(c==null){
                bt.setText("Crear curso");
                this.crearSpinner();
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CrearCurso.this.addCurso();
                    }
                });
            }else{
                this.rellenarDatos(c);
            }




        }

    private void crearSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTematica);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tematicas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

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
            Date fecha=new Date();
            Double duracion=Double.parseDouble(txtDuracion.getText().toString());
            int numAsist=Integer.parseInt(txtNumAsist.getText().toString());
            long id_Creador=((MyApplication) this.getApplication()).getId_user_logged();
            Curso toAdd=new Curso(nombre,descripcion,tematica,0,numAsist,fecha,duracion,id_Creador);

          CursoFacade cf=new CursoFacade(((MyApplication) this.getApplication()).getDBManager());
          cf.insertaCurso(toAdd);
          this.finish();
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

}
