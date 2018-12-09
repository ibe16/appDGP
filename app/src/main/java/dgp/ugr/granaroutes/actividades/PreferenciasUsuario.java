package dgp.ugr.granaroutes.actividades;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dgp.ugr.granaroutes.R;

public class PreferenciasUsuario extends AppCompatActivity {

    Button logOut;
    Button deleteAccount;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_preferencias_usuario);
        logOut = findViewById(R.id.log_out_button);
        deleteAccount = findViewById(R.id.delete_button);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                Toast.makeText(getApplicationContext(),R.string.sesion_cerrada,Toast.LENGTH_SHORT).show();
                aniadirResultadoActividadPrincipal();
            }
        });



        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser() != null) {
                    auth.getCurrentUser().delete();
                    Toast.makeText(getApplicationContext(),R.string.cuenta_borrada,Toast.LENGTH_SHORT).show();
                }

                aniadirResultadoActividadPrincipal();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void aniadirResultadoActividadPrincipal(){

        Intent data = new Intent();
        String text = "Inicio_Sesion";
        data.setData(Uri.parse(text));
        setResult(RESULT_OK, data);
        finish();

    }
}
