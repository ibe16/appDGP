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
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.Auth;

import dgp.ugr.granaroutes.R;

public class ActividadLogIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText loginEmail,loginPassword;
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

        loginEmail =  findViewById(R.id.email_login);
        loginPassword = findViewById(R.id.contraseña_login);
        loginButtonEmail = findViewById(R.id.boton_iniciar_sesion_email);
        registroButtonEmail = findViewById(R.id.boton_registro_email);
        loginButtonGoogle = findViewById(R.id.boton_iniciar_sesion_gmail);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(ActividadLogIn.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        loginButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInCorreo();
            }
        });

        registroButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCorreo();
            }
        });


        loginButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            Toast.makeText(getApplicationContext(),"Autenticado con "+ firebaseAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),ActividadPrincipal.class));
        }

    }

    private void registrarCorreo(){
        firebaseAuth.createUserWithEmailAndPassword(loginEmail.getText().toString(),loginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Registrado con "+ loginEmail.toString(),Toast.LENGTH_SHORT).show();
                            irActividadPrincipal();

                        } else {
                            Toast.makeText(getApplicationContext(),"Error de registro",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void signInCorreo(){
        firebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString(),loginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Iniciado sesión con "+ loginEmail.toString(),Toast.LENGTH_SHORT).show();
                    irActividadPrincipal();

                } else {
                    Toast.makeText(getApplicationContext(),"Error de inicio de sesión",Toast.LENGTH_SHORT).show();
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
                authWithGoogle(account);
            }
            else{
                Toast.makeText(getApplicationContext(),"Error de autentificación",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void authWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Autenticado con "+ account.getDisplayName(),Toast.LENGTH_SHORT).show();
                    irActividadPrincipal();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error de autentificación",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void irActividadPrincipal(){
        startActivity(new Intent(getApplicationContext(),ActividadPrincipal.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}

