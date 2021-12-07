package com.example.e_curso.view;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_curso.Adapter.UsuarioCursorAdapter;
import com.example.e_curso.MyApplication;
import com.example.e_curso.R;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.database.DBManager;
import com.example.e_curso.model.UsuarioFacade;


public class VerSolicitudes extends AppCompatActivity {
    private final String MENSAJE_AYUDA_SOLICITUDES = "Mantenga pulsado sobre la solicitud que quiera gestionar";
    private UsuarioFacade uf;
    private DBManager db;
    private UsuarioCursorAdapter cursorAdapter;
    private ListView listViewSolicitudes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_lista_solicitudes);
        listViewSolicitudes = this.findViewById(R.id.lvListaSolicitudes);

        this.gestionAyuda();
        this.setBusqueda();

        this.db=((MyApplication) this.getApplication()).getDBManager();
        this.uf=new UsuarioFacade(this.db);

        Cursor cursorUsuarios=this.uf.getTablaFiltrada(DBManager.USUARIO_COLUMN_SOLICITUD,"1");
        this.cursorAdapter=new UsuarioCursorAdapter(this,cursorUsuarios,uf);
        listViewSolicitudes.setAdapter(this.cursorAdapter);

        this.registerForContextMenu(listViewSolicitudes);
    }

    public void onCreateContextMenu(ContextMenu contxt, View v, ContextMenu.ContextMenuInfo cmi){
        if(v.getId()==R.id.lvListaSolicitudes){
            this.getMenuInflater().inflate( R.menu.opciones_sobre_solicitud_menu, contxt );
            contxt.setHeaderTitle( R.string.app_name );
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        long id=((AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ).id;
        switch (item.getItemId()){
            case R.id.itemMenuAceptar:
                aceptarSolicitud(uf.getUsuarioById(id));
                Toast.makeText(getApplicationContext(),"Usuario ACEPTADO como divulgador",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.itemMenuRechazar:
                denegarSolicitud(uf.getUsuarioById(id));
                Toast.makeText(getApplicationContext(),"Usuario RECHAZADO como divulgador",
                        Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void aceptarSolicitud(Usuario usuario){
        usuario.setRol("DIVUL");
        usuario.setSolicitud(0);
        uf.actualizarSolicitudUsuario(usuario);
        updateListView();
    }

    private void denegarSolicitud(Usuario usuario){
        usuario.setRol("USER");
        usuario.setSolicitud(0);
        uf.actualizarSolicitudUsuario(usuario);
        updateListView();
    }

    private void updateListView(){
        Cursor cursorSolicitudes = this.uf.getTablaFiltrada(DBManager.USUARIO_COLUMN_SOLICITUD,"1");
        this.cursorAdapter.changeCursor(cursorSolicitudes);
    }

    private void setBusqueda(){
        EditText patron=this.findViewById(R.id.etBuscarSolicitudes);
        patron.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filtro=charSequence.toString();
                Cursor c = VerSolicitudes.this.uf.buscarSolicitudesPorNombres(filtro);
                VerSolicitudes.this.cursorAdapter.changeCursor(c);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void gestionAyuda() {
        ImageView img=this.findViewById(R.id.imgHelp_Solicitudes);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(VerSolicitudes.this);
                builder.setTitle("Ayuda");
                builder.setMessage(MENSAJE_AYUDA_SOLICITUDES);
                builder.setNeutralButton("Volver",null);
                builder.create().show();

            }
        });
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

}
