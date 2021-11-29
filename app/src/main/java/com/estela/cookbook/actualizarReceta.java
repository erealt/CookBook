package com.estela.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.estela.cookbook.clases.Receta;

import java.util.ArrayList;

public class actualizarReceta extends AppCompatActivity {
    DBHelper conn;
    EditText txtNombreReceta;
    EditText txtPorciones, txttiempoPreparacion;
    EditText txtIngredientes, txtProcedimiento;
    Spinner spCategorias, spDificultad;
    int idReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevareceta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        conn = new DBHelper(getApplicationContext());

        Button btnActualizarReceta = (Button) findViewById(R.id.btnGuardarReceta);
        txtNombreReceta = (EditText) findViewById(R.id.txtNombreReceta);
        txtPorciones = (EditText) findViewById(R.id.txtPorciones);
        txtIngredientes = (EditText) findViewById(R.id.txtIngredientes);
        txtProcedimiento = (EditText) findViewById(R.id.txtPreparacion);
        txttiempoPreparacion = (EditText) findViewById(R.id.txtTiempo);

        this.setTitle("Actualizar receta");

        spCategorias = (Spinner) findViewById(R.id.spCategorias);
        ArrayAdapter spAdapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategorias.setAdapter(spAdapter);

        spDificultad = (Spinner) findViewById(R.id.spDificultad);
        ArrayAdapter spAdapterD = ArrayAdapter.createFromResource(this, R.array.dificultades, android.R.layout.simple_spinner_item);
        spAdapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDificultad.setAdapter(spAdapterD);

        Intent i = getIntent();
        String id_Receta = i.getStringExtra("id_Receta");
        idReceta = Integer.parseInt(id_Receta);

        conn = new DBHelper(this);
        ArrayList<Receta> listReceta = conn.detalleReceta(idReceta);

        if(!listReceta.isEmpty()){
            for (Receta receta : listReceta) {
                String dificultad = receta.getDificultad().toString();
                String categoria = String.valueOf(receta.getCategoria());

                switch (dificultad){
                    case "Baja":
                        spDificultad.setSelection(0);
                        break;

                    case "Media":
                        spDificultad.setSelection(1);
                        break;

                    case "Alta":
                        spDificultad.setSelection(2);
                        break;
                }

                switch (categoria){
                    case "1":
                        spCategorias.setSelection(0);
                        break;

                    case "2":
                        spCategorias.setSelection(1);
                        break;

                    case "3":
                        spCategorias.setSelection(2);
                        break;

                    case "4":
                        spCategorias.setSelection(3);
                        break;

                    case "5":
                        spCategorias.setSelection(4);
                        break;

                    case "6":
                        spCategorias.setSelection(5);
                        break;

                    case "7":
                        spCategorias.setSelection(6);
                        break;
                }

                txtNombreReceta.setText(receta.getNombre_receta());
                txtPorciones.setText(String.valueOf(receta.getPorciones()));
                txttiempoPreparacion.setText(String.valueOf(receta.getTiempo()));
                txtIngredientes.setText(receta.getIngredientes());
                txtProcedimiento.setText(receta.getPreparacion());
            }
        }

        btnActualizarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPorciones.getText().toString().equals("") || txttiempoPreparacion.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Rellena las personas y el tiempo", Toast.LENGTH_SHORT).show();

                } else {
                    String receta = txtNombreReceta.getText().toString();
                    int porcion = Integer.parseInt(String.valueOf(txtPorciones.getText()));
                    int tiempoP = Integer.parseInt(String.valueOf(txttiempoPreparacion.getText()));
                    String dificultad = spDificultad.getSelectedItem().toString();
                    String ingredientes = txtIngredientes.getText().toString();
                    String preparacion = txtProcedimiento.getText().toString();
                    String categoria = spCategorias.getSelectedItem().toString();
                    int idCategoria = (int) spCategorias.getSelectedItemId(), fkCategoria = idCategoria + 1;
                    ;
                    int foto = 0;

                    switch (categoria) {
                        case "Bebidas":
                            foto = R.drawable.bebidas;
                            break;

                        case "Ensaladas":
                            foto = R.drawable.ensaladas;
                            break;

                        case "Pastas":
                            foto = R.drawable.pastas;
                            break;

                        case "Platos fuertes":
                            foto = R.drawable.platosfuertes;
                            break;

                        case "Sopa":
                            foto = R.drawable.sopas;
                            break;

                        case "Guarniciones":
                            foto = R.drawable.guarnicion;
                            break;

                        case "Postres":
                            foto = R.drawable.postres;
                            break;
                    }

                    conn.actualizarReceta(String.valueOf(idReceta), receta, fkCategoria, dificultad, tiempoP, porcion, ingredientes, preparacion, foto);
                    Toast.makeText(getApplicationContext(), "La receta se ha actualizado correctamente ", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
