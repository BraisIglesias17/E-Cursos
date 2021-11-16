package com.example.e_curso.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.core.Usuario;
import com.example.e_curso.model.CursoFacade;
import com.example.e_curso.model.UsuarioFacade;

import org.w3c.dom.Text;

import java.util.Date;

public class UsuarioCursorAdapter extends CursorAdapter {

    UsuarioFacade cursos;
    public UsuarioCursorAdapter(Context context, Cursor c, UsuarioFacade cf){
        super(context,c);
        this.cursos=cf;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.listview_usuario,
                viewGroup,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView nombreUsuario=view.findViewById(R.id.tvNombreCompletoUsuario);
        TextView user=view.findViewById(R.id.tvUser);
        TextView email=view.findViewById(R.id.tvEmail1);

        Usuario u=UsuarioFacade.readUsuario(cursor);


        nombreUsuario.setText(u.getNombreCompleto());
        user.setText(u.getUser());
        email.setText(u.getEmail());

        if(u.getRol().toString()== Usuario.Rol.DIVUL.toString()){
            ImageView icono=view.findViewById(R.id.ivIconoDivulgador);
            icono.setVisibility(View.VISIBLE);
        }

    }
}
