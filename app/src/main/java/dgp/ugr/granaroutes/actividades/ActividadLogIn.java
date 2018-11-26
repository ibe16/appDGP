package dgp.ugr.granaroutes.actividades;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dgp.ugr.granaroutes.R;

public class ActividadLogIn extends AppCompatActivity {

    private static final String TAG = "LOG_IN";
    private FirebaseAuth mAuth;
    private Button botonRegistroInicio;
    private AutoCompleteTextView email;
    private EditText contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_actividad_log_in);
        mAuth = FirebaseAuth.getInstance();

        botonRegistroInicio = findViewById(R.id.boton_iniciar_sesion_email);
        email = findViewById(R.id.email);
        contraseña = findViewById(R.id.password);




        botonRegistroInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = email.getText().toString();
                String clave = contraseña.getText().toString();
                if(correo.contains("@")){
                    if(clave.length() >= 7 && clave.length() <= 20 && clave.matches("\\p{ASCII}")){
                        //TODO registrarCorreo();
                    }
                }
            }
        });


        /*mAuth.createUserWithEmailAndPassword("guillergood@gmail.com", "guillergood")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //TODO updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ActividadLogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //TODO updateUI(null);
                        }

                        // ...
                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }*/

}


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //TODO updateUI(currentUser);
    }
}
