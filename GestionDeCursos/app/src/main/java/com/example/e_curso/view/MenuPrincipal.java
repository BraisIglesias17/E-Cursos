package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.R;

public class MenuPrincipal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_usuario_activity);






        this.gestionVerCursos();
        this.gestionVerCursosApuntados();

    }

    private void gestionVerCursosApuntados() {
        Button btVerCursos=this.findViewById(R.id.btVerCursosGenerales);
        btVerCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    private void gestionVerCursos() {
        Button btVerMisCursos=this.findViewById(R.id.btVerCursosApuntados);
        btVerMisCursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
