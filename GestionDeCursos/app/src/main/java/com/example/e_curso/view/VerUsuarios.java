package com.example.e_curso.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.UsuarioCursorAdapter;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.UsuarioFacade;

import java.util.ArrayList;

public class VerUsuarios extends AppCompatActivity {

    private final int MODIFY_CODE = 1;
    private final int DELETE_CODE = 2;

    private UsuarioFacade uf;
    private DBManager db;
    private UsuarioCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //No perder la actividad que pasa a segundo plano
        setContentView(R.layout.ver_lista_usuarios);
        mockUpMethod();

        Usuario nuevo=new Usuario("br17", "Brais Iglesias Otero","pass","braiotero17@gmail.com", Usuario.Rol.DIVUL);
        ListView listViewUsuarios = this.findViewById(R.id.lvListaUsuarios);
        this.db=((MyApplication) this.getApplication()).getDBManager();
        this.uf=new UsuarioFacade(this.db);
        this.uf.insertUsuario(nuevo);
        Cursor cursorUsuarios=this.uf.getUsuarios();
        this.cursorAdapter=new UsuarioCursorAdapter(this,cursorUsuarios,uf);
        listViewUsuarios.setAdapter(this.cursorAdapter);

        listViewUsuarios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent modificarUsuario = new Intent(VerUsuarios.this, VerUsuarioConcreto.class);
                Usuario user = null;

                modificarUsuario.putExtra("nombre",user.getUser());
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
            user.setUser(data.getExtras().getString("nombre"));
            user.setNombreCompleto(data.getExtras().getString("apellido"));
            user.setEmail(data.getExtras().getString("email"));
            if(data.getExtras().getBoolean("divulgador"))
                user.setRol(Usuario.Rol.DIVUL);
            else
                user.setRol(Usuario.Rol.USER);

        }
    }

    private void mockUpMethod(){
        //this.usuarios=new ArrayList<>();
        //this.usuarios.add(new Usuario("PAPA","PEPE","papepe","qwer123", Usuario.Rol.DIVUL,"email@email.es"));

        //this.adaptadorUsuario=new UsuariosListAdapter(this,this.usuarios);
    }

}
