package com.example.e_curso.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;

import java.util.ArrayList;

public class CursosListAdapter extends ArrayAdapter {


    public CursosListAdapter(Context context, ArrayList<Curso> cursos){
        super(context,0,cursos);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=LayoutInflater.from(this.getContext());
        Curso curso=(Curso) this.getItem(position);

        if(view==null){
            view= inflater.inflate(R.layout.listview_curso,null);

        }

        //rellenar con datos de Curso

        TextView nombre=(TextView) view.findViewById(R.id.tvNombreCurso);
        TextView tematica=(TextView) view.findViewById(R.id.tvTematicaCurso);
        TextView fecha=(TextView) view.findViewById(R.id.tvFechaCurso);

        nombre.setText(curso.getNombreCurso());
        tematica.setText(curso.getTematica());
        fecha.setText(curso.getFechaFormato());

        return view;


    }
}
