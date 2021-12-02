package com.example.e_curso.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.model.AsistirFacade;
import com.example.e_curso.model.CursoFacade;

import java.security.MessageDigest;

public class VerCursoDetalle extends AppCompatActivity {


    boolean creador;

    private long idCursoActual;
    private Curso actual;
    private boolean apuntarse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_curso_detalle_activity);
        Curso c=(Curso) this.getIntent().getExtras().get("curso");
        this.idCursoActual=this.getIntent().getExtras().getLong("idCurso");
        this.actual=c;
        this.apuntarse=this.getIntent().getExtras().getBoolean("apuntarse");
        if(c!=null)
            this.rellenarDatos(c);


        this.configuracionBoton();
    }

    private void configuracionBoton() {
        Button apuntarse=this.findViewById(R.id.btApuntarse);
        if(this.actual.getNumAsistentes()==this.actual.getMaxAsistentes() || this.apuntarse==false
            || !this.condicionesApuntarse()){
                apuntarse.setVisibility(View.INVISIBLE);
        }else{
            apuntarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VerCursoDetalle.this.actual.reservarPlaza();
                    CursoFacade cf=new CursoFacade(((MyApplication)(VerCursoDetalle.this.getApplication())).getDBManager());
                    cf.actualizarAsistentes(VerCursoDetalle.this.actual.getNombreCurso(),VerCursoDetalle.this.actual.getNumAsistentes());
                    AsistirFacade af=new AsistirFacade(((MyApplication)(VerCursoDetalle.this.getApplication())).getDBManager());
                    af.insertarParticipacion(((MyApplication)(VerCursoDetalle.this.getApplication())).getId_user_logged(),VerCursoDetalle.this.idCursoActual);
                    VerCursoDetalle.this.finish();
                    Toast.makeText(VerCursoDetalle.this,"Te has apuntado a este curso",Toast.LENGTH_LONG);
                    apuntarse.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    private boolean condicionesApuntarse() {
        boolean toret;
        MyApplication myApp=(MyApplication)this.getApplication();
        AsistirFacade af=new AsistirFacade(myApp.getDBManager());
        long idLoggedUser=myApp.getId_user_logged();

        if(idLoggedUser==this.actual.getCreador() || af.getParticipacion(this.idCursoActual,idLoggedUser)){
            return false;
        }else{
            return true;
        }

    }

    private void rellenarDatos(Curso c){

        TextView nombreCurso=(TextView)this.findViewById(R.id.tvNombreCursoDetalle);
        TextView descripcion=(TextView)this.findViewById(R.id.tvResumenCursoDetalle);
        TextView fecha=(TextView)this.findViewById(R.id.etFechaCurso);
        TextView duracion=(TextView)this.findViewById(R.id.etDuracionCurso);
        TextView numAsistentes=(TextView)this.findViewById(R.id.etMaxAsistentes);
        TextView numActualAsistentes=(TextView) this.findViewById(R.id.etPlazasOcupadas);

        fecha.setEnabled(false);
        duracion.setEnabled(false);
        numAsistentes.setEnabled(false);
        numActualAsistentes.setEnabled(false);

        nombreCurso.setText(c.getNombreCurso());
        descripcion.setText(c.getDescripcion());
        duracion.setText(Double.toString(c.getDuracion()));
        numAsistentes.setText(Integer.toString(c.getMaxAsistentes()));
        numActualAsistentes.setText(Integer.toString(c.getNumAsistentes()));
        fecha.setText(c.getFechaFormato());


    }

}
