package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.general.General;
import com.example.e_curso.model.AsistirFacade;

public class MenuPrincipal extends AppCompatActivity {

    public static String CURSOS_ACCESO="cursosAccedidos";


    boolean creador=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_usuario_activity);

        this.creador=((MyApplication)this.getApplication()).esCreador();
        //UPDATE
        Button btVerCursos=this.findViewById(R.id.btVerCursosGenerales);
        this.gestionVerCursosGenerales();
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosApuntados);
        this.gestionVerCursosApuntados();


        LinearLayout cursosPublicados=this.findViewById(R.id.linearLayoutCursosPublicados);
        if(this.creador){
            Button btVerCursosPublicados=this.findViewById(R.id.btVerCursosPublicados);
            this.gestionVerCursosOfertados();
        }else{
            cursosPublicados.setVisibility(View.GONE);
        }

        AsistirFacade af=new AsistirFacade(((MyApplication)this.getApplication()).getDBManager());
        af.eliminarParticipacion(6,22);

    }


    private void gestionVerCursosApuntados() {
        Button btVerCursos=this.findViewById(R.id.btVerCursosApuntados);
        btVerCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_ACCESO,General.Acceso.APUNTADOS.toString());

                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });

    }

    private void gestionVerCursosGenerales() {
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosGenerales);
        btVerMisCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_ACCESO,General.Acceso.GENERALES.toString());

                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });
    }

    private void gestionVerCursosOfertados() {
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosPublicados);
        btVerMisCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMisCursos=new Intent(MenuPrincipal.this,VerCursos.class);
                verMisCursos.putExtra(MenuPrincipal.CURSOS_ACCESO,General.Acceso.OFERTADOS.toString());
                MenuPrincipal.this.startActivity(verMisCursos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_general,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.miVerPerfil:
                Intent intent=new Intent(this,VerUsuarioConcreto.class);
                intent.putExtra("usuario",((MyApplication)this.getApplication()).getUserLogged());

                this.startActivity(intent);
                break;
            case R.id.miLogOut:
                MyApplication myapp=(MyApplication) this.getApplication();
                myapp.setLogeado(null);
                myapp.setId_user_logged(0);


                this.startActivity(new Intent(this.getBaseContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                this.finish();

                break;
        }
        return true;
    }
}

