package com.estela.cookbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estela.cookbook.R;
import com.estela.cookbook.clases.Receta;

import java.util.ArrayList;

public class RecetasAdapter extends BaseAdapter  {
    private Context context;
    private ArrayList<Receta> listRecetas;
    public String auxidReceta;

    public RecetasAdapter(Context context, ArrayList<Receta> listRecetas){
        this.context = context;
        this.listRecetas = listRecetas;
    }

    @Override
    public int getCount() {
        return listRecetas.size();
    }//devuelve el numero de elementos que se introducen en el adapter

    @Override
    public Object getItem(int posicion) {
        return listRecetas.get(posicion);
    }//este metodo deberia devolver el id de fila del item que esta en esa posicion del adapter

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
   public View getView(int posicion, View v, ViewGroup parent) {
        //Este metodo crea una nueva View para cada elemento añadido
        //Se le pasa el View en el que se ha pulsado
        //si v es null, se instancia y configura con las propiedades deseadas para la presentación
        //si v no es null, se inicializa con este objeto view
        Receta item = (Receta)getItem(posicion);
        v = LayoutInflater.from(context).inflate(R.layout.recetas_items, null);

        TextView nombreReceta = (TextView)v.findViewById(R.id.txtNombreRecetaItem);
        TextView idReceta = (TextView) v.findViewById(R.id.txtIdRecetaItem);

        idReceta.setText(String.valueOf(item.getIdReceta()));

        nombreReceta.setText(item.getNombre_receta());

        auxidReceta = String.valueOf(item.getIdReceta());
        return v;

    }
}
