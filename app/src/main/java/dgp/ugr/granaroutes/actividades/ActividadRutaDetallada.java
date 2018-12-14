package dgp.ugr.granaroutes.actividades;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.ProveedorContenidos;

public class ActividadRutaDetallada extends AppCompatActivity {


    private int numero;
    private boolean esRutaFavorita;
    private TextView titulo;
    private TextView descripcion;
    private TextView grupos;
    private TextView lugares;
    private ImageView ruta;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_ruta_detallada);



        aniadirFlechaVolverAnteriorActividad();

        titulo = findViewById(R.id.titulo_ruta);
        descripcion = findViewById(R.id.descripcion_ruta);
        grupos = findViewById(R.id.grupos_ruta);
        lugares = findViewById(R.id.lugares_ruta);
        ruta = findViewById(R.id.imagen_ruta);



        inicializarVariables();

        insertarImagen();

    }

    private void compruebaFavorito(){
        if(esRutaFavorita){
            pintaFavorito();
        }
        else{
            deshacerPintura();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void irAlMapa(){
        Uri uri = ProveedorContenidos.getInstance().getRutas().get(numero).getMapUri();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.maps/com.google.android.maps.MapsActivity"));
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setData(uri);
        startActivity(intent);
    }

    private void clickFavorito(){
        ProveedorContenidos.getInstance().getRutas().get(numero).clickFavorito();
        esRutaFavorita = !esRutaFavorita;
        compruebaFavorito();
        indicarCambiosActividadAnterior();
    }

    private void pintaFavorito(){
        MenuItem item = menu.findItem(R.id.detallado_favorito);
        item.setIcon(getDrawable(R.drawable.estrella_detallada_fav));
    }

    private void deshacerPintura(){
        MenuItem item = menu.findItem(R.id.detallado_favorito);
        item.setIcon(getDrawable(R.drawable.estrella_detallada_no_fav));
    }

    private void insertarImagen(){
        Picasso.with(this)
                .load(ProveedorContenidos.getInstance().getRutas().get(numero).getImagen())
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(ruta);
    }

    private void inicializarVariables(){
        Intent intent = getIntent();
        esRutaFavorita = intent.getBooleanExtra("favorito",false);
        numero = intent.getIntExtra("id",0);
        titulo.setText(intent.getStringExtra("nombre"));
        descripcion.setText(intent.getStringExtra("descripcion"));
        StringBuilder gruposTexto = new StringBuilder();
        for(String cadena: intent.getStringArrayExtra("grupo")) {
            gruposTexto.append(cadena);
            gruposTexto.append("\n");
        }
        grupos.setText(gruposTexto.toString());

        StringBuilder lugaresTexto = new StringBuilder();
        for(String cadena: intent.getStringArrayExtra("lugares")) {
            lugaresTexto.append(cadena);
            lugaresTexto.append("\n");
        }
        lugares.setText(lugaresTexto.toString());
        ProveedorContenidos.getInstance().obtenerValoracionesDeRuta(intent.getStringExtra("nombre"));
    }

    private void indicarCambiosActividadAnterior(){
        Intent data = new Intent();
        data.putExtra("posicion", numero);
        setResult(RESULT_OK, data);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ruta_detallada,menu);
        this.menu = menu;

        compruebaFavorito();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detallado_favorito:
                clickFavorito();
                break;
            case R.id.detallado_mapa:
                irAlMapa();
                break;
        }

        return false;
    }
}
