package dgp.ugr.granaroutes.actividades;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.adaptador.AdaptadorValoraciones;
import dgp.ugr.granaroutes.data.ProveedorContenidos;

public class ActividadValoracionesDetalladas extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_valoraciones_detalladas);


        aniadirFlechaVolverAnteriorActividad();

        RecyclerView listaValoraciones = findViewById(R.id.rv_valoraciones_detalladas);
        listaValoraciones.setHasFixedSize(true);
        listaValoraciones.setLayoutManager(new LinearLayoutManager(this));
        AdaptadorValoraciones adaptadorValoraciones =
                new AdaptadorValoraciones(this, ProveedorContenidos.getInstance().getValoraciones(), null);
        listaValoraciones.setAdapter(adaptadorValoraciones);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                listaValoraciones.getContext(), LinearLayoutManager.VERTICAL);

        listaValoraciones.addItemDecoration(dividerItemDecoration);

    }

    private void aniadirFlechaVolverAnteriorActividad(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    /**
     * Se sobrecarga onSupportNavigateUp() para habilitar a la flecha superior a volver
     * a la actividad anterior
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    

}
