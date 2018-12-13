package dgp.ugr.granaroutes.actividades;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.adaptador.Adaptador;
import dgp.ugr.granaroutes.data.ProveedorContenidos;
import dgp.ugr.granaroutes.data.Ruta;
import dgp.ugr.granaroutes.fragmentos.FragmentoMapa;
import dgp.ugr.granaroutes.fragmentos.FragmentoRutas;
import dgp.ugr.granaroutes.fragmentos.FragmentoRutasFavoritas;


/**
 * Clase para manejar la aplicacion
 */

public class ActividadPrincipal extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener,
        Adaptador.AdapterOnClickHandler{

    private Fragment fragment;

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

        inicializaValoresMenuHamburguesa();

        inicializaMenuInferior();


        fragment = new FragmentoRutas();
        cargarFragmento(fragment);
    }

    private void inicializaMenuInferior() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }


    /**
     * Devuelve "true" si se ha podido hacer la transsicion de un fragmento a otro.
     * En otro caso "false"
     *
     * @param fragmento Fragmento por el cual se va a cambiar la vista
     * @return booleano valor dependiendo de si es valido fragmento o no devolverá "true" o "false"
     */
    private boolean cargarFragmento (Fragment fragmento){

        boolean devuelve = false;
        if(fragmento != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_fragmento, fragmento)
                    .commit();
            devuelve = true;
        }
        return devuelve;
    }

    /**
     * Metodo para mostrar la actividad de una ruta detallada
     * @param ruta Ruta que se ha pinchado para ser visualizada con mas detalles
     * @param posicion Posicion de la ruta en la colección de elementos
     */
    private void irDetallado(Ruta ruta, int posicion){
        Intent intent = new Intent(this, ActividadRutaDetallada.class);
        intent.putExtra("nombre",ruta.getNombre());
        intent.putExtra("descripcion",ruta.getDescripcion());
        String[] grupo = new String[ruta.getGrupos().size()];
        ArrayList<String> lista = new ArrayList<>(ruta.getGrupos().keySet());
        for(int i = 0; i < lista.size(); i++)
            grupo[i] = lista.get(i);
        intent.putExtra("grupo", grupo);
        String[] lugares = new String[ruta.getLugares().size()];
        lista = new ArrayList<>(ruta.getLugares().keySet());
        for(int i = 0; i < lista.size(); i++)
            lugares[i] = lista.get(i);
        intent.putExtra("lugares", lugares);
        intent.putExtra("favorito", ruta.isFavorito());
        intent.putExtra("id", posicion);

        startActivityForResult(intent,2);
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

        switch (menuItem.getItemId()){
            case R.id.nav_rutas:
            case R.id.navigation_rutas:
                fragment = new FragmentoRutas();
                cargarFragmento = true;
                break;
            case R.id.nav_rutas_fav:
            case R.id.navigation_rutas_favoritas:
                ProveedorContenidos.getInstance().ordenaFavoritasPorNumero();
                fragment = new FragmentoRutasFavoritas();
                cargarFragmento = true;
                break;
            case R.id.nav_map:
            case R.id.navigation_mapa:
                fragment = new FragmentoMapa();
                cargarFragmento = true;
                break;

            case R.id.nav_perfil:
                irPreferenciasUsuario();
                break;
            case R.id.nav_share:
                compartirApp();
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(cargarFragmento) {
            return cargarFragmento(fragment);
        }
        else{
            return true;
        }


    }

    private void compartirApp() {
        String comparte = "Únete a ver tus rutas por Granada con " + getString(R.string.app_name) +" en tu dispositivo favorito\n";
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(comparte + " " + getString(R.string.hastag_code))
                .getIntent();


        shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);


        startActivity(shareIntent);
    }

    private void irPreferenciasUsuario() {
        Intent intent = new Intent(this,PerfilUsuario.class);
        startActivityForResult(intent,1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK)
                volverInicioSesion();
        }
        else if (requestCode == 2){
            if(resultCode == Activity.RESULT_OK && data != null) {
                reorganizarDatos();
            }
        }

    }

    @Override
    public void onClick(Ruta ruta, int posicion) {
        irDetallado(ruta, posicion);
    }

    @Override
    public void reorganizarDatos() {
        FragmentoRutas fragmentoRutas = (FragmentoRutas) fragment;
        fragmentoRutas.getAdaptador().notifyDataSetChanged();
    }

    @Override
    public void muestraNoHayDatos() {
        FragmentoRutas fragmentoRutas = (FragmentoRutas) fragment;
        fragmentoRutas.mostrarNoHayDatos();
    }

    private void volverInicioSesion(){
        Intent intent = new Intent(this, ActividadLogIn.class);
        startActivity(intent);
        finish();
    }

    private void inicializaValoresMenuHamburguesa(){
        NavigationView navigationView = findViewById(R.id.panel_lateral);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView titulo = headerView.findViewById(R.id.titulo_hamburgesa);
        TextView descripcion = headerView.findViewById(R.id.descripcion_hamburgesa);

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if(usuario != null) {
            titulo.setText(usuario.getEmail());
            descripcion.setText(usuario.getDisplayName());
        }
    }

}
