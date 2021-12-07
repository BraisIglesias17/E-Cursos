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
import android.widget.ImageView;
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

public class VerUsuarios extends AppCompatActivity {

    private final int MODIFY_CODE = 1;
    public static String MENSAJE_AYUDA_USUARIOS="Manten pulsado sobre un usuario para ver las acciones disponibles";

    private UsuarioFacade uf;
    private DBManager db;
    private UsuarioCursorAdapter cursorAdapter;
    private ListView listViewUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_lista_usuarios);
        listViewUsuarios = this.findViewById(R.id.lvListaUsuarios);

        //Inicializacion del entorno
        this.db=((MyApplication) this.getApplication()).getDBManager();
        this.uf=new UsuarioFacade(this.db);

        Cursor cursorUsuarios=this.uf.getUsuarios();
        this.cursorAdapter=new UsuarioCursorAdapter(this,cursorUsuarios,uf);
        listViewUsuarios.setAdapter(this.cursorAdapter);

        //gestion de busquedas
        this.setBusqueda();
        this.gestionAyuda();
        this.registerForContextMenu(listViewUsuarios);
    }

    @Override
    public void onResume(){
        super.onResume();

        updateListView();

    }

    @Override
    public void onStop(){
        super.onStop();
        if(this.cursorAdapter.getCursor()!=null){
            this.cursorAdapter.getCursor().close();
        }
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
        build.setMessage("¿Estas seguro de querer eliminar este usuario?");
        build.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               UsuarioFacade usuarioBorrar  =  new UsuarioFacade(VerUsuarios.this.db);
               usuarioBorrar.deleteUsuariosByFilter(DBManager.USUARIO_COLUMN_ID, id);

               Toast.makeText(getApplicationContext(),"Usuario borrado correctamente",
                        Toast.LENGTH_LONG).show();
               updateListView();
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

    private void updateListView(){
        Cursor c = this.uf.getUsuarios();
        this.cursorAdapter.changeCursor(c);
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

    private void gestionAyuda() {
        ImageView img=this.findViewById(R.id.imgHelp_Usuarios);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(VerUsuarios.this);
                builder.setTitle("Ayuda");
                builder.setMessage(MENSAJE_AYUDA_USUARIOS);
                builder.setNeutralButton("Volver",null);
                builder.create().show();

            }
        });
    }
}
