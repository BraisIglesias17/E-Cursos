package com.example.e_curso.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.e_curso.R;
import com.example.e_curso.core.Curso;
import com.example.e_curso.model.CursoFacade;

import java.util.Date;


public class CursoAdapterCursor extends CursorAdapter {


    CursoFacade cursos;
    public CursoAdapterCursor(Context context, Cursor c,CursoFacade cf){
        super(context,c);
        this.cursos=cf;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.listview_curso,
                viewGroup,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nombre= view.findViewById(R.id.tvNombreCurso);
        TextView tematica=view.findViewById(R.id.tvTematicaCurso);
        TextView fecha=view.findViewById(R.id.tvFechaCurso);

        final Curso curso = CursoFacade.readCurso(cursor);
        nombre.setText(curso.getNombreCurso());
        Date fechaActual = new Date(System.currentTimeMillis());
        fecha.setText(curso.getFechaDB());
        tematica.setText(curso.getTematica());

        String fechaUtil="";
        int i=0;
        char next='x';
        while (next!=' '){
            next=curso.getFechaDB().charAt(i);
            fechaUtil+=next;
            i++;
        }
        fecha.setText(fechaUtil);

        if (!fechaActual.before(curso.getFecha()) && !fechaActual.equals(curso.getFecha())){
            fecha.setTextColor(Color.RED);
        }else{
            fecha.setTextColor(Color.BLACK);
        }
    }
}
