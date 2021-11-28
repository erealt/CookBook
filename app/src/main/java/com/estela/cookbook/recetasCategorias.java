package com.estela.cookbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.estela.cookbook.adapters.RecetasAdapter;
import com.estela.cookbook.clases.Receta;

import java.util.ArrayList;

public class recetasCategorias extends AppCompatActivity {

    DBHelper conn;
    GridView gridViewRecetasCategoria;
    ArrayList<Receta> listRecetas;
    RecetasAdapter adapter = null;
    int idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetascategorias);



        this.setTitle("Recetas por categoría");

        conn = new DBHelper(getApplicationContext());
        gridViewRecetasCategoria = (GridView) findViewById(R.id.gridviewRecetasCategoria);
        listRecetas = new ArrayList<>();

        Intent i = getIntent();
        String id_Categoria = i.getStringExtra("id_Categoria");
        idCategoria = Integer.parseInt(id_Categoria);

        SharedPreferences sharedPreferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
        int idUsuario = sharedPreferences.getInt("idUsuario", 0);

        listRecetas = conn.cargarRecetasporCategoria(idCategoria, idUsuario);
        adapter = new RecetasAdapter(getApplicationContext(), listRecetas);
        gridViewRecetasCategoria.setAdapter(adapter);

        gridViewRecetasCategoria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //se guarda un auxiliar del id de la receta que se obtiene desde el adaptador
                // y se guarda en un Extra para usarlo en la consulta de la info de la receta

                TextView idreceta=(TextView) v.findViewById(R.id.txtIdRecetaItem);
                String textId  = idreceta.getText().toString();

               Intent intent = new Intent(getApplicationContext(), detalleReceta.class);
               intent.putExtra("id_Receta", textId);

                startActivity(intent);
            }
        });
    }
}
