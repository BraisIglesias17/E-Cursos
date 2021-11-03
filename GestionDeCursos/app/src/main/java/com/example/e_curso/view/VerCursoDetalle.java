package com.example.e_curso.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

import org.w3c.dom.Text;

public class VerCursoDetalle extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_curso_detalle_activity);
        Curso c=(Curso) this.getIntent().getExtras().get("curso");
        this.rellenarDatos(c);
    }

    private void rellenarDatos(Curso c){

        TextView nombreCurso=(TextView)this.findViewById(R.id.tvNombreCursoDetalle);
        TextView descripcion=(TextView)this.findViewById(R.id.tvResumenCurso);
        EditText fecha=(EditText)this.findViewById(R.id.etFechaCurso);
        EditText duracion=(EditText)this.findViewById(R.id.etDuracionCurso);
        EditText numAsistentes=(EditText)this.findViewById(R.id.etMaxAsistentes);

        fecha.setEnabled(false);
        duracion.setEnabled(false);
        numAsistentes.setEnabled(false);

        nombreCurso.setText(c.getNombreCurso());
        descripcion.setText(c.getDescripcion());
        duracion.setText(c.getDescripcion());
        //numAsistentes.setText(c.getMaxAsistentes());
        fecha.setText(c.getFechaFormato());


    }

}
