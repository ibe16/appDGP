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
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


public class ProveedorContenidos implements Serializable {

    private static volatile ArrayList<Ruta> rutas;
    private static volatile ArrayList<Valoracion> valoraciones;
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

    public void obtenerValoracionesDeRuta(final String nombreRuta){
        DatabaseReference baseDatos = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference valoracionesBd = baseDatos.child("valoraciones");

        valoracionesBd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                valoraciones = new ArrayList<>();
                for (DataSnapshot unidad : dataSnapshot.getChildren()) {
                    if(esLaRuta(unidad.getKey(), nombreRuta)){
                        extraeValoraciones(unidad);
                    }
                }

                for(Valoracion v:valoraciones){
                    Log.d("VALORACION: ", v.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error al obtener Valoraciones", error.toException());
            }


        });
    }

    private void extraeValoraciones(DataSnapshot unidad) {
        for (DataSnapshot valoracion : unidad.getChildren()) {
            Valoracion v = valoracion.getValue(Valoracion.class);
            if(valoracion.getKey() != null && v != null) {
                v.setIdentificador(Integer.parseInt(valoracion.getKey()));
                valoraciones.add(v);
            }
        }
    }

    private boolean esLaRuta(String posibleNombre, String elNombre) {
        return Objects.equals(posibleNombre, elNombre);
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

    //TODO METER VALORACIONES Y CAMBIAR CONSTRAINTS
    /**
     * public static class User {
     *
     *   public String date_of_birth;
     *   public String full_name;
     *   public String nickname;
     *
     *   public User(String dateOfBirth, String fullName) {
     *     // ...
     *   }
     *
     *   public User(String dateOfBirth, String fullName, String nickname) {
     *     // ...
     *   }
     *
     * }
     *
     * DatabaseReference usersRef = ref.child("users");
     *
     * Map<String, User> users = new HashMap<>();
     * users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
     * users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));
     *
     * usersRef.setValueAsync(users);
     *
     *
     *
     *
     *
     * CAMBIO CONSTRAINT
     *
     * https://stackoverflow.com/questions/45263159/constraintlayout-change-constraints-programmatically
     *
     *
     */
}

