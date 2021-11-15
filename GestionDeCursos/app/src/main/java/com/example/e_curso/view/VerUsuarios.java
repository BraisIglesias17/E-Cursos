package com.example.e_curso.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;

import java.util.ArrayList;

public class VerUsuarios extends AppCompatActivity {

    private final int MODIFY_CODE = 1;
    private final int DELETE_CODE = 2;


    private ArrayAdapter<Usuario> adaptadorUsuario;
    private ArrayList<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //No perder la actividad que pasa a segundo plano
        setContentView(R.layout.ver_lista_usuarios);
        ListView listViewUsuarios = this.findViewById(R.id.lvListaUsuarios);


        listViewUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent modificarUsuario = new Intent(VerUsuarios.this, VerUsuarioConcreto.class);
                Usuario user = (Usuario) VerUsuarios.this.adaptadorUsuario.getItem(i);

                modificarUsuario.putExtra("nombre",user.getNombre());
                modificarUsuario.putExtra("pos", i);

                VerUsuarios.this.startActivityForResult(modificarUsuario, MODIFY_CODE);
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_CODE
                && resultCode == Activity.RESULT_OK) {

            int pos = data.getExtras().getInt("pos");
            Usuario user = new Usuario();
            user.setNombre(data.getExtras().getString("nombre"));
            user.setApellido(data.getExtras().getString("apellido"));
            user.setEmail(data.getExtras().getString("email"));
            if(data.getExtras().getBoolean("divulgador"))
                user.setRol(Usuario.Rol.DIVUL);
            else
                user.setRol(Usuario.Rol.USER);
            this.usuarios.set(pos, user);
            this.adaptadorUsuario.notifyDataSetChanged();
        }
    }
}
