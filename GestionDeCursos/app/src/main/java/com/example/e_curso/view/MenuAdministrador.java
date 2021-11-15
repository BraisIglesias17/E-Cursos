package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.R;

public class MenuAdministrador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal_admin_activity);
        this.gestionVerUsuarios();
        this.gestionVerSolicitudes();
    }

    private void gestionVerUsuarios() {
        Button btVerUsuarios=this.findViewById(R.id.btUsuarios);
        btVerUsuarios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent verUsuarios=new Intent(MenuAdministrador.this, VerUsuarios.class);
                MenuAdministrador.this.startActivity(verUsuarios);
            }
        });
    }

    private void gestionVerSolicitudes() {
        Button btVerSolicitudes=this.findViewById(R.id.btSolicitudes);
        btVerSolicitudes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent verSolicitudes=new Intent(MenuAdministrador.this, VerSolicitudes.class);
                MenuAdministrador.this.startActivity(verSolicitudes);
            }
        });
    }
}