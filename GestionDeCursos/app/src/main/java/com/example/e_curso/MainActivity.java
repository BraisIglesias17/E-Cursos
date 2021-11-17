package com.example.e_curso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_curso.core.Usuario;
import com.example.e_curso.model.UsuarioFacade;
import com.example.e_curso.view.MenuAdministrador;
import com.example.e_curso.view.MenuPrincipal;
import com.example.e_curso.view.Registro;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Currency;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    private UsuarioFacade usuarioDB;
    private boolean admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.usuarioDB=new UsuarioFacade(((MyApplication) this.getApplication()).getDBManager());

        TextView bt= (TextView) this.findViewById(R.id.etSignIn);
        Button botonLogIn=(Button) this.findViewById(R.id.btLogIn);


        botonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean acceder=MainActivity.this.checkLogIn();
                if(acceder){
                    MainActivity.this.admin=((MyApplication)MainActivity.this.getApplication()).esAdmin();
                }




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
        boolean toret=false;

        EditText user=this.findViewById(R.id.etUser);
        EditText password=this.findViewById(R.id.etPassword);

        byte[] passwordEncriptada=this.encriptarPassword(password.getText().toString());

        Cursor usuario=this.usuarioDB.logIn(user.getText().toString());

        if(usuario!=null){
            usuario.moveToFirst();
            Usuario actual=UsuarioFacade.readUsuario(usuario);
            int id=UsuarioFacade.getID(usuario);
            if(Arrays.equals(actual.getPass(),passwordEncriptada)){
                toret=true;
                MyApplication app=(MyApplication) this.getApplication();
                app.setLogeado(actual);
                app.setId_user_logged(id);
            }

        }
        return toret;
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
}