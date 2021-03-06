package com.example.e_curso.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.model.UsuarioFacade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VerUsuarioConcreto extends AppCompatActivity{

    private UsuarioFacade usuarioFacade;
    private Usuario usuarioActual;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_usuario_concreto);

    //Info del intent + botones + datos a guardar
        final Intent datosUsuario = this.getIntent();
        final Button btModificar = (Button) this.findViewById(R.id.btModificarUsuario);

        final EditText nuevoNombre = (EditText) this.findViewById(R.id.etuserName);
        final EditText nuevoApellido = (EditText) this.findViewById(R.id.etNombreCompleto);
        final EditText nuevoEmail = (EditText) this.findViewById(R.id.etEmail);
        CheckBox esDivulgador=(CheckBox) this.findViewById(R.id.cbDivulgador);

        long id=datosUsuario.getExtras().getLong("id");
        this.usuarioActual=(Usuario) datosUsuario.getSerializableExtra("usuario");
        this.usuarioFacade=new UsuarioFacade(((MyApplication) this.getApplication()).getDBManager());

        this.rellenarPagina(this.usuarioActual);
        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dlg=new AlertDialog.Builder(VerUsuarioConcreto.this);
                dlg.setMessage("¿Estas seguro de modificar esta información?");
                dlg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        VerUsuarioConcreto.this.usuarioActual.setUser(nuevoNombre.getText().toString());
                        VerUsuarioConcreto.this.usuarioActual.setNombreCompleto(nuevoApellido.getText().toString());
                        VerUsuarioConcreto.this.usuarioActual.setEmail(nuevoEmail.getText().toString());
                        if(VerUsuarioConcreto.this.usuarioActual.getRol()==Usuario.Rol.USER && esDivulgador.isChecked()){
                            //En caso de que un usuario se quiera hacer divulgador
                            VerUsuarioConcreto.this.usuarioActual.setSolicitud(1);
                        }else if(VerUsuarioConcreto.this.usuarioActual.getRol()==Usuario.Rol.DIVUL && !esDivulgador.isChecked()){
                            VerUsuarioConcreto.this.usuarioActual.setRol(Usuario.Rol.USER);
                        }

                        VerUsuarioConcreto.this.usuarioFacade.actualizarUsuario(VerUsuarioConcreto.this.usuarioActual);
                        if(((MyApplication) VerUsuarioConcreto.this.getApplication()).getUserLogged().getRol()!=Usuario.Rol.ADMIN){
                            ((MyApplication) VerUsuarioConcreto.this.getApplication()).updateUserLogged();

                        }
                        VerUsuarioConcreto.this.finish();
                        Toast.makeText(VerUsuarioConcreto.this,"Usuario modificado correctamente",Toast.LENGTH_LONG).show();
                    }
                });
                dlg.setNegativeButton("No", null);
                dlg.create().show();

            }
        });



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


    private void rellenarPagina(Usuario user){

        TextView nombreUsuario=(TextView)this.findViewById(R.id.etuserName);
        TextView nombreCompletoUsuario=(TextView)this.findViewById(R.id.etNombreCompleto);
        TextView emailUsuario=(TextView)this.findViewById(R.id.etEmail);
        CheckBox esDivulgador=(CheckBox) this.findViewById(R.id.cbDivulgador);

        nombreUsuario.setText(user.getUser());
        nombreCompletoUsuario.setText(user.getNombreCompleto());
        emailUsuario.setText(user.getEmail());
        if(user.getRol() == Usuario.Rol.DIVUL)
            esDivulgador.setChecked(true);
        else
            esDivulgador.setChecked(false);
    }

}
