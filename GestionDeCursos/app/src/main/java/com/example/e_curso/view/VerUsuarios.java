package com.example.e_curso.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.UsuarioCursorAdapter;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.UsuarioFacade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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


        //Inicializacion del entorno
        ListView listViewUsuarios = this.findViewById(R.id.lvListaUsuarios);
        this.db=((MyApplication) this.getApplication()).getDBManager();
        this.uf=new UsuarioFacade(this.db);

        this.uf.insertUsuario(this.mockUpMethod());
        Cursor cursorUsuarios=this.uf.getUsuarios();
        this.cursorAdapter=new UsuarioCursorAdapter(this,cursorUsuarios,uf);
        listViewUsuarios.setAdapter(this.cursorAdapter);


        //gestion de busquedas
        this.setBusqueda();



        this.registerForContextMenu(listViewUsuarios);


        /* QUEDA IMPLEMENTAR NAVEGACION A MODIFICAR Y ELIMINAR
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
        });*/
    }
    public void onResume(){
        super.onResume();

    }


     public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi){
            if(v.getId()==R.id.lvListaUsuarios){
                this.getMenuInflater().inflate( R.menu.opciones_sobre_usuario_menu, contxt );
                contxt.setHeaderTitle( R.string.app_name );
            }
     }

     @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
         long id=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).id;
         switch (item.getItemId()){
             case R.id.itemMenuEliminar:
                                            this.triggerEliminarUsuario(id);
                 break;
             case R.id.itemMenuModificar: this.triggerModificarUsuario(id);
                 break;
         }
         return true;
    }

    private void triggerEliminarUsuario(long id) {
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        build.setTitle("Eliminar usuario");
        build.setMessage("¿Estas seguro de eliminar este usuario?");
        build.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        build.setNegativeButton("No",null);
        build.create().show();

    }
    private void triggerModificarUsuario(long id){
        Intent modificarUsuario = new Intent(VerUsuarios.this, VerUsuarioConcreto.class);
        modificarUsuario.putExtra("id",id);
        Cursor c=this.uf.getById(id);
        c.moveToFirst();
        Usuario toSend=UsuarioFacade.readUsuario(c);
        modificarUsuario.putExtra("usuario",toSend);
        VerUsuarios.this.startActivity(modificarUsuario);
    }

    private void setBusqueda() {

        EditText patron=this.findViewById(R.id.etBuscarUsuario);

        patron.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filtro=charSequence.toString();
                Cursor c=VerUsuarios.this.uf.buscarUsuariosPorNombres(filtro);
                VerUsuarios.this.cursorAdapter.changeCursor(c);

            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    private Usuario mockUpMethod(){
       String pass="user3";
       byte [] digest;
        MessageDigest md = null; //genero un resumen de  la contraseña en claro
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest = md.digest(pass.getBytes());
        Usuario prueba=new Usuario("user3", "usuario no divulgador",digest,"user@mail.com", Usuario.Rol.USER,0);
        return prueba;
    }

}
