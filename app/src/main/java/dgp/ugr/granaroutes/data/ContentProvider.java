package dgp.ugr.granaroutes.data;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.support.constraint.Constraints.TAG;


public class ContentProvider implements Serializable {

    private static ArrayList<Ruta> rutas;
    private static volatile ContentProvider instanciado;

    //private constructor.
    private ContentProvider(){

        //Prevent form the reflection api.
        if (instanciado != null){
            throw new RuntimeException("Utilizar .getInstance() para convocar ContentProvider");
        }
    }

    public static ContentProvider getInstance() {
        if (instanciado == null) { //if there is no instance available... create new one
            synchronized (ContentProvider.class) {
                if (instanciado == null) instanciado = new ContentProvider();
            }
        }

        return instanciado;
    }

    //Make singleton from serialize and deserialize operation.
    protected ContentProvider readResolve() {
        return getInstance();
    }


    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public void leerRutas(final DataListener escuchador) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rutasDb = mDatabase.child("rutas");

        rutasDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rutas = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    //Firebase añade directamente del JSON los valores a la clase que se especifique
                    Ruta rutita = d.getValue(Ruta.class);
                    rutas.add(rutita);
                }

                escuchador.lecturaTerminada();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }


        });
    }

    public void ordenaPorNumero(){
        Collections.sort(rutas, new Comparator<Ruta>(){
            public int compare(Ruta r1, Ruta r2){
                return r1.getNumero() - r2.getNumero();
            }
        });
    }

    public void ordenaPorFavorito(){
        Collections.sort(rutas, new Comparator<Ruta>(){
            public int compare(Ruta r1, Ruta r2){
                int esMenor = -1;
                int esMayor = 1;
                if(r1.isFavorito() && r2.isFavorito())
                    return r1.getNumero() - r2.getNumero();
                else if(r1.isFavorito() && !r2.isFavorito())
                    return esMenor;
                else
                    return esMayor;
            }
        });
    }

}

