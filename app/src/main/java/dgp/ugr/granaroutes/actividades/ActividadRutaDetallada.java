package dgp.ugr.granaroutes.actividades;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.ContentProvider;
import dgp.ugr.granaroutes.data.Ruta;

public class ActividadRutaDetallada extends AppCompatActivity {


    private int numero;
    private ImageView favorito;
    private boolean rutaFavorita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_ruta_detallada);
        Intent intent = getIntent();

        TextView titulo;
        TextView descripcion;
        TextView grupos;
        TextView lugares;
        ImageView mapa;
        ImageView ruta;


        titulo = findViewById(R.id.titulo_ruta);
        descripcion = findViewById(R.id.descripcion_ruta);
        grupos = findViewById(R.id.grupos_ruta);
        lugares = findViewById(R.id.lugares_ruta);
        mapa = findViewById(R.id.imagen_mostrar_ruta_en_mapa);
        favorito = findViewById(R.id.imagen_favorito_ruta);
        ruta = findViewById(R.id.imagen_ruta);




        rutaFavorita = intent.getBooleanExtra("favorito",false);

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

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = ContentProvider.getInstance().getRutas().get(numero).getMapUri();

                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.maps/com.google.android.maps.MapsActivity"));
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentProvider.getInstance().getRutas().get(numero).clickFavorito();
                rutaFavorita = !rutaFavorita;
                esFavorito();
                Intent data = new Intent();
                data.putExtra("posicion", numero);
                setResult(RESULT_OK, data);
            }



        });

        esFavorito();


        Picasso.with(this)
                .load(ContentProvider.getInstance().getRutas().get(numero).getImagen())
                .error(R.drawable.common_google_signin_btn_icon_dark)
                .into(ruta);

    }

    private void esFavorito(){
        if(rutaFavorita){
            favorito.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_black));
            favorito.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_tint_selected));
        }
        else{
            favorito.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.icono_estrella));
            favorito.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.icon_tint_normal));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
