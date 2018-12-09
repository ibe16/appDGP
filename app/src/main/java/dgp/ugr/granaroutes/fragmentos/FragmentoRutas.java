package dgp.ugr.granaroutes.fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.actividades.ActividadPrincipal;
import dgp.ugr.granaroutes.adaptador.Adaptador;
import dgp.ugr.granaroutes.data.ContentProvider;
import dgp.ugr.granaroutes.data.Ruta;

import static android.support.constraint.Constraints.TAG;

public class FragmentoRutas extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar cargando;
    ActividadPrincipal actividadPrincipal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_actividad_rutas, null);
        //mostrarCargando();
        actividadPrincipal = (ActividadPrincipal) getActivity();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rutasDb = mDatabase.child("rutas");

        rutasDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Ruta> rutas = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    //Firebase añade directamente del JSON los valores a la clase que se especifique
                    Ruta rutita = d.getValue(Ruta.class);
                    rutas.add(rutita);
                }

                ContentProvider.getInstance().fetchRutas(rutas);


                //mostrarDatos();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }


        });



        return view;
    }

    private void mostrarCargando() {
        recyclerView.setVisibility(View.INVISIBLE);
        cargando.setVisibility(View.VISIBLE);
    }

    private void mostrarDatos() {
        recyclerView.setVisibility(View.VISIBLE);
        cargando.setVisibility(View.INVISIBLE);
    }
}
