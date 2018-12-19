package dgp.ugr.granaroutes.actividades;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.datos.ProveedorContenidos;
import dgp.ugr.granaroutes.datos.Valoracion;

public class ActividadRegistrarValoracion extends AppCompatActivity {

    RatingBar puntuacionEstrellas;
    EditText puntuacionNumerica;
    FloatingActionButton mandarValoracion;
    EditText descripcion;
    TextView usuarioTitulo;
    final int LIMITE_SUPERIOR = 5;
    final int LIMITE_INFERIOR = 0;
    int elIndice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrar_valoracion);

        usuarioTitulo = findViewById(R.id.contenedor_usuario_valoracion_registro);
        puntuacionEstrellas = findViewById(R.id.contenedor_barra_valoracion_registro);
        mandarValoracion = findViewById(R.id.boton_mandar_valoracion);
        descripcion = findViewById(R.id.contenedor_descripcion_valoracion_registro);


        elIndice = getIntent().getIntExtra("indice",-1);

        aniadirFlechaVolverAnteriorActividad();
        inicializaUsuario();
        inicializaElementosIU();


        if(elIndice >= 0)
            introduceValoracionPrevia();

    }

    private void inicializaElementosIU() {
        puntuacionEstrellas.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                cambiarNumero(v);
            }
        });

        puntuacionNumerica = findViewById(R.id.contenedor_editar_puntuacion_valoracion_registro);
        puntuacionNumerica.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null && charSequence.length() > 0) {
                    corrijeNumeroEntreLimites();
                }
                else{
                    puntuacionEstrellas.setRating(LIMITE_INFERIOR);
                }

            }

            private void corrijeNumeroEntreLimites() {
                float elNumero = Float.parseFloat(puntuacionNumerica.getText().toString());
                if (elNumero > LIMITE_SUPERIOR) {
                    puntuacionEstrellas.setRating(LIMITE_SUPERIOR);
                } else if (elNumero < LIMITE_INFERIOR) {
                    puntuacionEstrellas.setRating(LIMITE_INFERIOR);
                } else {
                    puntuacionEstrellas.setRating(elNumero);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mandarValoracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(elIndice >= 0)
                    modificarValoracion();
                else
                    aniadirValoracion();
                subirDatos();
                mandarValoracion.setClickable(false);
                mandarValoracion.hide();
                volverAnteriorActividad();
            }
        });


    }

    private void modificarValoracion() {
        String texto = descripcion.getText().toString();
        ProveedorContenidos.getInstance().getValoraciones().get(elIndice).setDescripcion(texto);
        float puntuacion = puntuacionEstrellas.getRating();
        ProveedorContenidos.getInstance().getValoraciones().get(elIndice).modificarValoracion(puntuacion);
    }

    private void introduceValoracionPrevia() {
        Valoracion valoracion = ProveedorContenidos.getInstance().getValoraciones().get(elIndice);
        descripcion.setText(valoracion.getDescripcion());
        puntuacionEstrellas.setRating(valoracion.cogerValoracionNumerica());
    }

    private void inicializaUsuario() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if(usuario != null)
            usuarioTitulo.setText(usuario.getDisplayName());
    }

    private void volverAnteriorActividad() {
        onSupportNavigateUp();
    }


    private void cambiarNumero(float nuevoValor) {
        String elValor = Float.toString(nuevoValor);
        puntuacionNumerica.setText(elValor);
    }


    private void aniadirFlechaVolverAnteriorActividad() {
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


    private void subirDatos() {
        ProveedorContenidos.getInstance().subirDatos();
    }

    private void aniadirValoracion() {
        Valoracion valoracion = new Valoracion(descripcion.getText().toString(),
                usuarioTitulo.getText().toString(),
                Float.toString(puntuacionEstrellas.getRating()));
        valoracion.setIdentificador(ProveedorContenidos.getInstance().getValoraciones().size()+1);
        ProveedorContenidos.getInstance().aniadirValoracion(valoracion);
    }
}
