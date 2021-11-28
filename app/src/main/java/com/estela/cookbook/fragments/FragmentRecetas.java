package com.estela.cookbook.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.estela.cookbook.DBHelper;
import com.estela.cookbook.R;
import com.estela.cookbook.adapters.RecetasAdapter;
import com.estela.cookbook.clases.Receta;
import com.estela.cookbook.detalleReceta;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FragmentRecetas  extends Fragment {

    public FragmentRecetas() {}
    public String rece;
    GridView gridviewRecetas;
    ArrayList<Receta> listRecetas;
    RecetasAdapter adapter = null;
    int idUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recetas, container, false);

        DBHelper conn = new DBHelper(getActivity());

        gridviewRecetas = (GridView) view.findViewById(R.id.gridviewRecetas);
        listRecetas = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = conn.cargarRecetas(idUsuario);

        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);
        // se llena el gridview con la lista de recetas que tiene el usuario

        gridviewRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //View-> es la vista dentro del adaptador donde se hizo click
                //position-> la posicion de la vista en el adaptador
                //id -> id de la dila del elemento que se hizo clic

                TextView idreceta=(TextView) v.findViewById(R.id.txtIdRecetaItem);
                String textId  = idreceta.getText().toString();
                //se guarda el id de la receta que se ha seleccionado. Este id esta en la view oculto
                //y se manda a la siguiente activity con el intent.putExtra
                Intent intentMasInfoReceta = new Intent(getActivity(), detalleReceta.class);

               intentMasInfoReceta.putExtra("id_Receta",textId);

                startActivity(intentMasInfoReceta);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        //para recargar la lista de recetas una vez que el foco haya regresado al fragment
        DBHelper conn = new DBHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = new ArrayList<>();
        listRecetas = conn.cargarRecetas(idUsuario);
        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);
        super.onResume();
    }

    public void onDestroyView(){
        //para recargar la lista de recetas una vez que el foco haya regresado al fragment
        DBHelper conn = new DBHelper(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
        idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = new ArrayList<>();
        listRecetas = conn.cargarRecetas(idUsuario);
        adapter = new RecetasAdapter(getActivity(), listRecetas);
        gridviewRecetas.setAdapter(adapter);

        super.onDestroyView();
    }
}
