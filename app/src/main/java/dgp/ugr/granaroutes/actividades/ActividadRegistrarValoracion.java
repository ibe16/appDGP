package dgp.ugr.granaroutes.actividades;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.ProveedorContenidos;
import dgp.ugr.granaroutes.data.RegistradorDatos;
import dgp.ugr.granaroutes.data.Valoracion;

public class ActividadRegistrarValoracion extends AppCompatActivity implements RegistradorDatos {

    RatingBar puntuacionEstrellas;
    EditText puntuacionNumerica;
    FloatingActionButton mandarValoracion;
    EditText descripcion;
    TextView usuarioTitulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrar_valoracion);

        aniadirFlechaVolverAnteriorActividad();

        usuarioTitulo = findViewById(R.id.contenedor_usuario_valoracion_registro);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if(usuario != null)
            usuarioTitulo.setText(usuario.getEmail());

        puntuacionEstrellas = findViewById(R.id.contenedor_barra_valoracion_registro);
        mandarValoracion = findViewById(R.id.boton_mandar_valoracion);
        descripcion = findViewById(R.id.contenedor_descripcion_valoracion_registro);

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
            //TODO REFACTORIZAR
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null && charSequence.length() > 0) {
                    if (Float.parseFloat(puntuacionNumerica.getText().toString()) > 5) {
                        puntuacionEstrellas.setRating(5);
                    } else if (Float.parseFloat(puntuacionNumerica.getText().toString()) < 0) {
                        puntuacionEstrellas.setRating(0);
                    } else {
                        puntuacionEstrellas.setRating(Float.parseFloat(puntuacionNumerica.getText().toString()));
                    }
                }
                else{
                    puntuacionEstrellas.setRating(0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mandarValoracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO SI TODO ESTA CORRECTO Y NADA VACIO
                actualizaValoraciones();
            }
        });

    }

    private void cambiarNumero(float nuevoValor) {
        String elValor = Float.toString(nuevoValor);
        puntuacionNumerica.setText(elValor);
    }

    private void actualizaValoraciones() {
        ProveedorContenidos.getInstance().actualizaValoracionesDeRuta(this);
    }

    private void aniadirFlechaVolverAnteriorActividad() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void datosActualizados() {
        aniadirValoracion();
        subirDatos();
        ProveedorContenidos.getInstance().subirDatos();
    }

    private void subirDatos() {
        ProveedorContenidos.getInstance().subirDatos();
    }

    private void aniadirValoracion() {
        Valoracion valoracion = new Valoracion(descripcion.getText().toString(),
                usuarioTitulo.getText().toString(),
                Float.toString(puntuacionEstrellas.getRating()));
        ProveedorContenidos.getInstance().aniadirValoracion(valoracion);
    }
}
