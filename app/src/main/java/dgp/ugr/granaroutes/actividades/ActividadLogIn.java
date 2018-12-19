package dgp.ugr.granaroutes.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;

public class ActividadLogIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText loginEmail, loginContrasenia, nombreUsuario;
    Button loginButtonEmail;
    Button loginButtonGoogle;
    Button registroButtonEmail;
    private static final int RC_SIGN_IN = 9001;
    FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_log_in);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.app_name_login);

        loginEmail =  findViewById(R.id.email_login);
        loginContrasenia = findViewById(R.id.contraseña_login);
        loginButtonEmail = findViewById(R.id.boton_iniciar_sesion_email);
        registroButtonEmail = findViewById(R.id.boton_registro_email);
        loginButtonGoogle = findViewById(R.id.boton_iniciar_sesion_gmail);
        nombreUsuario = findViewById(R.id.nombre_usuario);

        inicializaServiciosGoogle();

        loginButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInCorreo();
            }
        });

        registroButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> valoresIncorrectos = esFormularioCorrecto();
                if(valoresIncorrectos.size() == 0)
                    registrarCorreo();
                else {
                    muestraErrores(valoresIncorrectos);
                }

            }
        });

        loginButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        if(estaAutenticado()){
            irActividadPrincipal();
        }


    }

    private void muestraErrores(ArrayList<String> valoresIncorrectos) {
        StringBuilder builder = new StringBuilder();
        for(String cadena:valoresIncorrectos){
            builder.append(cadena);
        }
        Toast.makeText(getBaseContext(), builder.toString(), Toast.LENGTH_LONG).show();
    }

    private ArrayList<String> esFormularioCorrecto() {
        ArrayList<String> fallos = new ArrayList<>();

        if(!loginEmail.getText().toString().contains("@") ||
                !loginEmail.getText().toString().contains(".")){
            fallos.add(getString(R.string.fallo_correo));
        }
        if(!(loginContrasenia.getText().toString().length() >= 6)){
            fallos.add(getString(R.string.fallo_contraseña));
        }
        if(!(nombreUsuario.getText().toString().length() > 0)){
            fallos.add(getString(R.string.fallo_usuario));
        }

        return fallos;
    }

    private void registrarCorreo(){

        firebaseAuth.createUserWithEmailAndPassword(loginEmail.getText().toString(), loginContrasenia.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),getString(R.string.autentificado)+" " +
                                            nombreUsuario.getText().toString(),
                                    Toast.LENGTH_SHORT).show();
                            asignarNombreAUsuario();
                            irActividadPrincipal();



                        } else {
                            if( task.getException() !=null)
                                Toast.makeText(getApplicationContext(),task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void asignarNombreAUsuario() {
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        if(usuario != null && !nombreUsuario.getText().toString().isEmpty()) {
            UserProfileChangeRequest actualizaciones = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nombreUsuario.getText().toString()).build();
            usuario.updateProfile(actualizaciones);
        }
    }

    private void signInCorreo(){
        firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginContrasenia.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(firebaseAuth.getCurrentUser() != null) {
                        Toast.makeText(getApplicationContext(), getString(R.string.autentificado) +
                               " " + firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                        irActividadPrincipal();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),R.string.error_autentificacion,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInGoogle() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                if(account != null)
                    authWithGoogle(account);
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.error_autentificacion,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void authWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),getString(R.string.autentificado)+ " " +
                            account.getDisplayName(),Toast.LENGTH_SHORT).show();
                    irActividadPrincipal();
                }
                else{
                    Toast.makeText(getApplicationContext(),R.string.error_autentificacion,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void irActividadPrincipal(){
        Intent intent = new Intent(getApplicationContext(),ActividadPrincipal.class);
        intent.putExtra("nombreUsuario", nombreUsuario.getText().toString());
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void inicializaServiciosGoogle(){
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(ActividadLogIn.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private boolean estaAutenticado(){
        return firebaseAuth.getCurrentUser()!=null;
    }


}

