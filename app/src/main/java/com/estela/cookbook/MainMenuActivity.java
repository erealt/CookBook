package com.estela.cookbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.estela.cookbook.fragments.FragmentCategorias;
import com.estela.cookbook.fragments.FragmentRecetas;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
       toolbar = (Toolbar) findViewById(R.id.toolbar);


        setTitle("CookBook");
        //Pager para las vistas de inicio: recetas recientes y categorias
        viewPager = (ViewPager) findViewById(R.id.pager);       //esta en app_bar_mainmenu.xml
        setupViewPager(viewPager);

        //tabs que mostrarán los títulos de las opciones
        tabLayout = (TabLayout) findViewById(R.id.tabs);        //esta en app_bar_mainme.xml
        tabLayout.setupWithViewPager(viewPager);

        //menu de navegación    esta en menu_main
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    //método que realiza la configuracion del viewPager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentRecetas(), "Recetas");           //Fragment inicio (recetas recientes)
        adapter.addFragment(new FragmentCategorias(), "Categorías");      //Fragment categorias (categorias)
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> FragmentContent = new ArrayList<>();
        private final List<String> FragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentContent.get(position);
        }

        @Override
        public int getCount() {
            return FragmentContent.size();
        }

        //muestra el fragment que se recibe en la configuración del viewPager
        public void addFragment(Fragment fragment, String title) {
            FragmentContent.add(fragment);
            FragmentTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitle.get(position);
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_nuevaReceta){
            DBHelper conn = new DBHelper(getApplicationContext());

            SharedPreferences sharedPreferences = getSharedPreferences("correo_Usuario", Context.MODE_PRIVATE);
            correo = sharedPreferences.getString("correo_Usuario", "");
            int idUsuario = conn.encontrarUsuario(correo);

            SharedPreferences preferences = getSharedPreferences("idUsuario", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorpre = preferences.edit();
            editorpre.putInt("idUsuario", idUsuario);
            editorpre.commit();

            Intent intent = new Intent(getApplicationContext(), nuevaReceta.class);
            startActivity(intent);

        } else if (id == R.id.nav_cerrarSesion) {
            new AlertDialog.Builder(MainMenuActivity.this)
                    .setTitle("Sesión")
                    .setMessage("¿Está seguro que desea cerrar sesión?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //guarda el estado de inicio de sesion automatico a false
                            SharedPreferences pref = getSharedPreferences("flag_sesion", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("flag_sesion", false);
                            editor.commit();

                            //lanza el activity del login  despues de terminar el activity

                            finish();

                        }
                    })
                    //cierra el alertdialog
                    .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    }).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

