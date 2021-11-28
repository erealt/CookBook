package com.estela.cookbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;

import java.util.regex.Pattern;

public class registroUsuarios extends AppCompatActivity {
    DBHelper conn;
    EditText txtUser, txtEmail, txtPass1, txtPass2;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevousuario);
        this.setTitle("Nuevo Usuario");

        txtUser = (EditText) findViewById(R.id.txtUsuario);
        txtEmail = (EditText) findViewById(R.id.txtemail);
        txtPass1 = (EditText) findViewById(R.id.txtpassw1);
        txtPass2 = (EditText) findViewById(R.id.txtpassw2);
        btnRegistrar = (Button)findViewById(R.id.btnGuardarUsuario);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //comprueba que no existan campos vacios
                if(txtUser.getText().toString().equals("") | txtEmail.getText().toString().equals("") |
                        txtPass1.getText().toString().equals("") | txtPass2.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Existen campos vacíos", Toast.LENGTH_SHORT).show();

                }
                else if (!PatternsCompat.EMAIL_ADDRESS.matcher(txtEmail.getText()).matches()){
                    Toast.makeText(getApplicationContext(), "El correo no es valido", Toast.LENGTH_SHORT).show();

                }
                //comprueba que las contraseñas coincidan
                else if(!(txtPass1.getText().toString().equals(txtPass2.getText().toString()))){
                    Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT).show();
                    txtPass1.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                    txtPass2.setTextColor(getApplicationContext().getResources().getColor(R.color.red));

                } else{
                    //realiza el registro en la BD
                    conn = new DBHelper(getApplicationContext());
                    String nombre = txtUser.getText().toString(),
                            correo = txtEmail.getText().toString(),
                            pass = txtPass1.getText().toString();

                    conn.registrarUsuario(nombre, correo, pass,1);



                    Toast.makeText(registroUsuarios.this, "¡El registro ha finalizado correctamente!", Toast.LENGTH_SHORT).show();

                    //lanza el activity donde se encuentra el menu NavigationDrawer
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}

