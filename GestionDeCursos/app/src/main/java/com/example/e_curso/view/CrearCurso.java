package com.example.e_curso.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

import java.util.Date;

public class CrearCurso extends AppCompatActivity{

        boolean creador;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.crear_modificar_curso);
            this.creador=((MyApplication) this.getApplication()).esCreador();

            Curso c=(Curso) this.getIntent().getExtras().get("curso");

            System.out.println();
            if(c==null){
                Button bt=(Button) this.findViewById(R.id.btGuardarCambiosCurso);
                bt.setText("Crear curso");
            }else{
                this.rellenarDatos(c);
            }
        }

        private void addCurso(){
            EditText txtNombreCurso=(EditText)this.findViewById(R.id.etNombreCurso);
            EditText txtDescripcion=(EditText)this.findViewById(R.id.etResumenCurso);
            EditText txtFecha=(EditText)this.findViewById(R.id.etFechaCurso);
            EditText txtDuracion=(EditText)this.findViewById(R.id.etDuracionCurso);
            EditText txtNumAsist=(EditText)this.findViewById(R.id.etMaxAsistentes);
            //EditText txtTematica=(EditText)this.findViewById(R.id.etTematica);

            String nombre=txtNombreCurso.getText().toString();
            String descripcion=txtDescripcion.getText().toString();
            Date fecha=new Date();
            Double duracion=Double.parseDouble(txtDuracion.getText().toString());
            int numAsist=Integer.parseInt(txtNumAsist.getText().toString());
            long id_Creador=((MyApplication) this.getApplication()).getId_user_logged();
            //Curso toAdd=new Curso(nombre,descripcion,tematica,0,numAsist,fecha,duracion,id_Creador);
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
            txtNumAsist.setText(c.getMaxAsistentes());

        }

}
