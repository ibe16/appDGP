package dgp.ugr.granaroutes.data;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.support.constraint.Constraints.TAG;


public class ContentProvider implements Serializable {

    private static ArrayList<Ruta> rutas;
    private static ArrayList<Ruta> rutasFavoritas;
    private static volatile ContentProvider instanciado;

    //private constructor.
    private ContentProvider(){

        //Prevent form the reflection api.
        if (instanciado != null){
            throw new RuntimeException("Utilizar .getInstance() para convocar ContentProvider");
        }
    }

    public static ContentProvider getInstance() {
        if (instanciado == null) {
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
                rutasFavoritas = new ArrayList<>();
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
//TODO leerImagenesDeRutas
    /*
    public void leerImagenesDeRutas() {
        StorageReference mDatabase =  FirebaseStorage.getInstance().getReference();
        mDatabase

        rutasDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rutas = new ArrayList<>();
                rutasFavoritas = new ArrayList<>();
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
    }*/



    public ArrayList<Ruta> getRutasFavoritas() {
        return rutasFavoritas;
    }


    public void aniadeRutaFavorita(int posicion){
        rutasFavoritas.add(rutas.get(posicion));
    }

    public void ordenaPorNumero(){
        Collections.sort(rutasFavoritas, new Comparator<Ruta>(){
            public int compare(Ruta r1, Ruta r2){
                return r1.getNumero() - r2.getNumero();
            }
        });
    }

    public void quitaRutaFavorita(Ruta ruta) {
        if(rutasFavoritas.indexOf(ruta) >= 0)
            rutasFavoritas.remove(ruta);

    }
}

