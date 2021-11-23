package com.example.e_curso.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;

public class VerUsuarioConcreto extends AppCompatActivity{
    public static final int CODIGO_OK = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_usuario_concreto);

    //Info del intent + botones + datos a guardar
        final Intent datosUsuario = this.getIntent();
        final Button btModificar = (Button) this.findViewById(R.id.btModificarUsuario);
        final Button btCancelar = (Button) this.findViewById(R.id.btVolverListaUsuarios);
        final EditText nuevoNombre = (EditText) this.findViewById(R.id.etuserName);
        final EditText nuevoApellido = (EditText) this.findViewById(R.id.etNombreCompleto);
        final EditText nuevoEmail = (EditText) this.findViewById(R.id.etEmail);
        CheckBox esDivulgador=(CheckBox) this.findViewById(R.id.cbDivulgador);

        long id=datosUsuario.getExtras().getLong("id");
        Usuario actual=(Usuario) datosUsuario.getSerializableExtra("usuario");
        this.rellenarPagina(actual);
        //nuevoNombre.setText(datosUsuario.getExtras().getString("nombre"));

        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent de datos a enviar
                Intent datosModificados = new Intent();
                //rellenar el intent //HACERLO CON UN OBJETO USUARIO SERIALIZABLE
                datosModificados.putExtra("nombre", nuevoNombre.getText().toString());
                datosModificados.putExtra("apellido",nuevoApellido.getText().toString());
                datosModificados.putExtra("email", nuevoEmail.getText().toString());
                datosModificados.putExtra("divulgador",esDivulgador.isChecked());
                datosModificados.putExtra("pos", VerUsuarioConcreto.this.getIntent().getExtras().getInt("pos"));

                VerUsuarioConcreto.this.setResult(Activity.RESULT_OK, datosModificados);
                VerUsuarioConcreto.this.finish();
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerUsuarioConcreto.this.setResult(Activity.RESULT_CANCELED);
                VerUsuarioConcreto.this.finish();
            }
        });

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
