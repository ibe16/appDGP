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


public class ProveedorContenidos implements Serializable {

    private static volatile ArrayList<Ruta> rutas;
    private static volatile ArrayList<Ruta> rutasFavoritas;
    private static volatile ProveedorContenidos instanciado;

    private ProveedorContenidos(){

        if (instanciado != null){
            throw new RuntimeException("Utilizar .getInstance() para invocar ProveedorContenidos");
        }
    }

    public static ProveedorContenidos getInstance() {
        if (instanciado == null) {
            synchronized (ProveedorContenidos.class) {
                if (instanciado == null) instanciado = new ProveedorContenidos();
            }
        }

        return instanciado;
    }


    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public void obtenerRutas(final RegistradorDatos escuchador) {
        DatabaseReference baseDatos = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rutasBd = baseDatos.child("rutas");

        rutasBd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rutas = new ArrayList<>();
                rutasFavoritas = new ArrayList<>();
                for (DataSnapshot unidad : dataSnapshot.getChildren()) {
                    Ruta rutita = unidad.getValue(Ruta.class);
                    rutas.add(rutita);
                }

                escuchador.terminarInicializacion();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error al obtener Rutas", error.toException());
            }


        });
    }




    public ArrayList<Ruta> getRutasFavoritas() {
        return rutasFavoritas;
    }


    public void aniadeRutaFavorita(int posicion){
        rutasFavoritas.add(rutas.get(posicion));
    }

    public void ordenaFavoritasPorNumero(){
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

