package com.example.e_curso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_curso.view.MenuAdministrador;
import com.example.e_curso.view.MenuPrincipal;
import com.example.e_curso.view.Registro;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView bt= (TextView) this.findViewById(R.id.etSignIn);
        Button botonLogIn=(Button) this.findViewById(R.id.btLogIn);


        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean acceder=MainActivity.this.checkLogIn();
                boolean admin=true;
                if(acceder && !admin){
                    Intent menu_principal=new Intent(MainActivity.this, MenuPrincipal.class);
                    MainActivity.this.goTo(menu_principal,"nombeusuario");




                }else if(acceder && admin){
                    Intent menu_principal_admin=new Intent(MainActivity.this, MenuAdministrador.class);
                    MainActivity.this.goTo(menu_principal_admin,"nombeusuario");

                }
                else{
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subActividad = new Intent( MainActivity.this, Registro.class );
                MainActivity.this.startActivity(subActividad);
                //MainActivity.this.startActivityForResult( subActividad,0);
            }
        });
    }
    private void goTo(Intent intent, String username){
        intent.putExtra("username",username);
        this.startActivity(intent);
    }

    private boolean checkLogIn() {
        return true;
    }
}