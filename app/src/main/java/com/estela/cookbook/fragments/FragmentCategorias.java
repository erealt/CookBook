package com.estela.cookbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.estela.cookbook.DBHelper;
import com.estela.cookbook.R;
import com.estela.cookbook.adapters.CategoriasAdapter;
import com.estela.cookbook.clases.Categoria;
import com.estela.cookbook.recetasCategorias;

import java.util.ArrayList;

public class FragmentCategorias extends Fragment {
    public FragmentCategorias(){}

    ListView listviewCategorias;
    ArrayList<Categoria> listCategorias;
    CategoriasAdapter adapter = null;
    DBHelper conn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        conn = new DBHelper(getActivity());

        listviewCategorias = (ListView) view.findViewById(R.id.listViewCategorias);
        listCategorias = new ArrayList<>();

        listCategorias = conn.CargarCategorias();


        adapter = new CategoriasAdapter(getActivity(), listCategorias);
        listviewCategorias.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listviewCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int pos = position+1;
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
                int idUsuario = sharedPreferences.getInt("idUsuario", 0);
                boolean hayRecetas = conn.hayRecetasRegistradas(pos, idUsuario);

                if(hayRecetas == true){
                    //se guarda la posicion de la categoria que se obtiene desde el adaptador
                    // y se guarda en un Extra para usarlo en la consulta para mostrar las recetas por categoria
                    Intent intent = new Intent(getActivity(), recetasCategorias.class);
                    intent.putExtra("id_Categoria", String.valueOf(pos));
                    startActivity(intent);
                } else{
                    Toast.makeText(getActivity(), "Aún no hay recetas registradas con esta categoria",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
