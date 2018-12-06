package dgp.ugr.granaroutes.actividades;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.adaptador_recyclerview.Adaptador;
import dgp.ugr.granaroutes.data.Ruta;
import dgp.ugr.granaroutes.fragmentos.FragmentoMapa;
import dgp.ugr.granaroutes.fragmentos.FragmentoRutas;


/**
 * Clase para manejar la aplicacion
 */

public class ActividadPrincipal extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,
        Adaptador.AdapterOnClickHandler{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.panel_lateral);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        cargarFragmento(new FragmentoRutas());
    }


    /**
     * Devuelve "true" si se ha podido hacer la transsicion de un fragmento a otro.
     * En otro caso "false"
     *
     * @param fragmento Fragmento por el cual se va a cambiar la vista
     * @return booleano valor dependiendo de si es valido fragmento o no devolverá "true" o "false"
     */
    private boolean cargarFragmento (Fragment fragmento){

        boolean valor = false;
        if(fragmento != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_fragmento, fragmento)
                    .commit();
            valor = true;
        }
        return valor;
    }

    /**
     * Metodo para mostrar la actividad de una ruta detallada
     */
    private void irDetallado(Ruta ruta){
        Intent intent = new Intent(this, ActividadRutaDetallada.class);
        intent.putExtra("nombre",ruta.getNombre());
        intent.putExtra("descripcion",ruta.getDescripcion());
        String[] grupo = new String[ruta.getGrupos().size()];
        ArrayList<String> lista = new ArrayList<String>(ruta.getGrupos().keySet());
        for(int i = 0; i < lista.size(); i++)
            grupo[i] = lista.get(i);
        intent.putExtra("grupo", grupo);
        String[] lugares = new String[ruta.getLugares().size()];
        lista = new ArrayList<String>(ruta.getLugares().keySet());
        for(int i = 0; i < lista.size(); i++)
            lugares[i] = lista.get(i);
        intent.putExtra("lugares", lugares);

        startActivity(intent);
    }

    /**
     * Manejando para ir cambiando de fragmento, en funcion de qué botón se pinche de la barra de
     * navegación inferior.
     *
     *
     * @param menuItem Elemento que se ha pinchado
     * @return booleano valor de la función cargarFragmento dependiendo de si es valido fragmento
     * o no devolverá "true" o "false"
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        boolean cargarFragmento = false;
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_rutas:
                fragment = new FragmentoRutas();
                cargarFragmento = true;
                break;

            case R.id.navigation_rutas_favoritas:
                cargarFragmento = true;
                break;

            case R.id.navigation_mapa:
                fragment = new FragmentoMapa();
                cargarFragmento = true;
                break;

        }

        if(cargarFragmento) {
            return cargarFragmento(fragment);
        }
        else{
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(Ruta ruta) {
        irDetallado(ruta);
    }
}
