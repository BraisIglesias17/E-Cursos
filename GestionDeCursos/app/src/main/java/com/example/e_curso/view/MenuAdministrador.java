package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MainActivity;
import com.example.e_curso.MyApplication;
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


                this.startActivity(new Intent(this.getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                this.finish();

                break;
        }
        return true;
    }
}