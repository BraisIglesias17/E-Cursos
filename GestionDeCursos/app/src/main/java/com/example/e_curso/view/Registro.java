package com.example.e_curso.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.UsuarioFacade;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicialización del entorno
        setContentView(R.layout.sign_in_activity);
        DBManager db = ((MyApplication) this.getApplication()).getDBManager();
        UsuarioFacade uf = new UsuarioFacade(db);
        Button registrar = this.findViewById(R.id.btSignIn);

        final EditText usuario = this.findViewById(R.id.etuserName);
        final EditText nombreCompleto = this.findViewById(R.id.etNombreCompleto);
        final EditText email = this.findViewById(R.id.etMailUsuario);
        EditText pass = this.findViewById(R.id.etPasswd);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int solicitud;

                byte[] passBytes = Registro.this.encriptarPassword(pass.getText().toString());
                solicitud = comprobarSolicitud();

                Usuario nuevoUsuario = new Usuario(usuario.getText().toString(),nombreCompleto.getText().toString(),passBytes,
                        email.getText().toString(), Usuario.Rol.USER, solicitud);

                uf.insertUsuario(nuevoUsuario);

                Intent intent = new Intent(Registro.this,MenuPrincipal.class);

                Registro.this.startActivity(intent);

            }
        });


    }

    public int comprobarSolicitud(){
        CheckBox checked = this.findViewById(R.id.cbSolicitudDivulgador);

        if(checked.isChecked())
            return 1;
        else
            return 0;
    }

    private byte[] encriptarPassword(String password) {

        byte [] digest=null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //genero un resumen de  la contraseña en claro
            digest = md.digest(password.getBytes());
        }catch (NoSuchAlgorithmException exception){
            Log.e("ENCRYPT",exception.getMessage());
        }

        return digest;
    }

    private byte[] passIntoBytes(EditText pass){
        return pass.getText().toString().getBytes(StandardCharsets.UTF_8);
    }
}
