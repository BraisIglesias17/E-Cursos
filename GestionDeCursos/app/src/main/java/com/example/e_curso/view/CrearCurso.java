package com.example.e_curso.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

public class CrearCurso extends AppCompatActivity{

        boolean creador;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.crear_modificar_curso);
            Curso c=(Curso) this.getIntent().getExtras().get("curso");

            this.rellenarDatos(c);
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
            txtDuracion.setText((int) c.getDuracion());
            txtNumAsist.setText(c.getMaxAsistentes());

        }

}
