package com.example.e_curso.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.core.Usuario;

import java.util.ArrayList;

public class UsuariosListAdapter extends ArrayAdapter {


    public UsuariosListAdapter(Context context, ArrayList<Usuario> usuarios){
        super(context,0,usuarios);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(this.getContext());
        Usuario user=(Usuario) this.getItem(position);

        if(view==null){
            view= inflater.inflate(R.layout.listview_usuario,null);

        }

        //rellenar con datos de Usuario

        TextView nombre=(TextView) view.findViewById(R.id.tvNombreUsuario1);
        TextView apellido=(TextView) view.findViewById(R.id.tvApellidosUsuario1);
        TextView email=(TextView) view.findViewById(R.id.tvEmail1);

        nombre.setText(user.getNombre());
        apellido.setText(user.getApellido());
        email.setText(user.getEmail());

        return view;


    }
}
