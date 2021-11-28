package com.estela.cookbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estela.cookbook.R;
import com.estela.cookbook.clases.Categoria;

import java.util.ArrayList;

public class CategoriasAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Categoria> listcategorias;

    public CategoriasAdapter(Context context, ArrayList<Categoria> listcategorias) {
        this.context = context;
        this.listcategorias = listcategorias;
    }

    @Override
    public int getCount() {
        return listcategorias.size();
    }

    @Override
    public Object getItem(int posicion) {
        return listcategorias.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    public View getView(int posicion, View view, ViewGroup viewGroup) {
        Categoria item = (Categoria) getItem(posicion);

        view = LayoutInflater.from(context).inflate(R.layout.categoria_items, null);
        ImageView catfoto = (ImageView) view.findViewById(R.id.imgvCategorias);
        TextView txtcategoria = (TextView) view.findViewById(R.id.txtcategoria);

        catfoto.setImageResource(item.getCatgFoto());
        txtcategoria.setText(item.getNombre_categoria());

        return view;
    }
}
