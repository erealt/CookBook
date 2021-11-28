package com.estela.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String email, contrasenia;

    DBHelper conn;

    EditText correo, contraseña;
    TextView registrar_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conn = new DBHelper(this);



        //Verificar el estado del flag de sesion para decidir la acción que el
        //activity realizará (no guardar la sesion o guardarla)
        SharedPreferences sharedPreferences = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
        boolean sesionIniciada = sharedPreferences.getBoolean("flag_sesion", false);

        //Si al abrir la aplicación no hemos cerrado la sesión anteriormente el flag estara en true
        //entonces se abrira directamente la pantalla principal sin pasar por el login
        if(sesionIniciada == true){
            Intent i = new Intent(this, MainMenuActivity.class);
            startActivity(i);
            finish();
        }

        Button btnLogin = (Button) findViewById(R.id.btnlogin);
        TextView registrar_usuario=(TextView)findViewById(R.id.clic);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = (EditText) findViewById(R.id.txtCorreo);
                contraseña = (EditText) findViewById(R.id.txtPass);

                email = correo.getText().toString();
                contrasenia = contraseña.getText().toString();

                if(email.equals("") || contrasenia.equals("")){
                    Toast.makeText(getApplicationContext(), "Aún no hay datos para iniciar sesión", Toast.LENGTH_SHORT).show();
                    correo.setFocusable(true);
                    contraseña.setFocusable(true);
                } else{
                    boolean loginn = false;

                    try{
                        loginn =conn.login(email,contrasenia);

                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                    //si el loginn es true sera que el correo y contraseña estan registrados en la bd
                    // y son correctos
                    if(loginn == true){
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);


                        SharedPreferences pref = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("flag_sesion", true);
                        editor.commit();

                        //Guardamos en el xml del sharedPreferences el email y el id del usuario para tenerlos a mejor acceso
                        //para hacer consultas a la bd cuando lo necesitemos
                        SharedPreferences preferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorpre = preferences.edit();
                        editorpre.putString("correo_Usuario", email);
                        editorpre.commit();

                        int idUsu=conn.encontrarUsuario(email);
                        SharedPreferences prefusu = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorusu = prefusu.edit();
                        editorpre.putInt("idUsuario", idUsu);
                         editorpre.commit();

                        Toast.makeText(getApplicationContext(), "¡Bienvenid@ a CookBook!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();

                    } else if(loginn == false){
                        Toast.makeText(getApplicationContext(), "El usuario o contraseña están incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent (getApplicationContext(), registroUsuarios.class);

                startActivity(intent1);
            }
        });
    }

}