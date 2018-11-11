package dgp.ugr.granaroutes.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.fragmentos.FragmentoMapa;
import dgp.ugr.granaroutes.fragmentos.FragmentoRutas;


/**
 * Clase para manejar la aplicacion
 */

public class ActividadPrincipal extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_principal);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        cargarFragmento(new FragmentoRutas());
    }


    //TODO Cambiar este metodo en el fragmento, ya que no se maneja aqui, esto es un Driver
    /**
     * Metodo para mostrar la actividad de una ruta detallada
     */
    private void irDetallado(){
        Intent intent = new Intent(this, ActividadRutaDetallada.class);
        startActivity(intent);
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
     * Metodo para ir cambiando de fragmento, en funcion de qué botón se pinche de la barra de
     * navegación inferior
     *
     * @param menuItem Elemento que se ha pinchado
     * @return booleano valor de la función cargarFragmento dependiendo de si es valido fragmento
     * o no devolverá "true" o "false"
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_rutas:
                fragment = new FragmentoRutas();
                break;

            case R.id.navigation_rutas_favoritas:
                irDetallado();
                break;

            case R.id.navigation_mapa:
                fragment = new FragmentoMapa();
                break;

        }

        return cargarFragmento(fragment);
    }
}
