package com.estela.cookbook;

import android.content.Context;
import android.content.SharedPreferences;
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

public class nuevaReceta extends AppCompatActivity {
    DBHelper conn;
    EditText txtNombreReceta;
    EditText txtPorciones, txttiempoPreparacion;
    EditText txtIngredientes, txtProcedimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevareceta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        conn = new DBHelper(getApplicationContext());

        Button btnGuardarReceta = (Button) findViewById(R.id.btnGuardarReceta);
        txtNombreReceta = (EditText) findViewById(R.id.txtNombreReceta);
        txtPorciones = (EditText) findViewById(R.id.txtPorciones);
        txtIngredientes = (EditText) findViewById(R.id.txtIngredientes);
        txtProcedimiento = (EditText) findViewById(R.id.txtPreparacion);
        txttiempoPreparacion = (EditText) findViewById(R.id.txtTiempo);

        this.setTitle("Nueva Receta");

        final Spinner spCategorias = (Spinner) findViewById(R.id.spCategorias);
        ArrayAdapter spAdapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategorias.setAdapter(spAdapter);

        final Spinner spDificultad = (Spinner) findViewById(R.id.spDificultad);
        ArrayAdapter spAdapterD = ArrayAdapter.createFromResource(this, R.array.dificultades, android.R.layout.simple_spinner_item);
        spAdapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDificultad.setAdapter(spAdapterD);

        btnGuardarReceta.setOnClickListener(new View.OnClickListener() {
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

                        case "Estofados":
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


                    SharedPreferences sharedPreferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
                    int idUsuario = sharedPreferences.getInt("idUsuario", 0);
                    conn.nuevaReceta(receta, fkCategoria, dificultad, tiempoP, porcion, ingredientes, preparacion, foto, idUsuario);

                    Toast.makeText(getApplicationContext(), "Receta guardada exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
           // }
        });




}
}
