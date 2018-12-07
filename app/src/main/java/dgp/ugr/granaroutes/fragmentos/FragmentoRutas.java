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
import dgp.ugr.granaroutes.data.Ruta;

import static android.support.constraint.Constraints.TAG;

public class FragmentoRutas extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar cargando;
    private DatabaseReference mDatabase;
    private ArrayList<Ruta> rutas;
    ActividadPrincipal actividadPrincipal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_actividad_rutas, null);
        actividadPrincipal = (ActividadPrincipal) getActivity();
        rutas = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rutasDb = mDatabase.child("rutas");
        rutasDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    //Firebase añade directamente del JSON los valores a la clase que se especifique
                    Ruta rutita = d.getValue(Ruta.class);
                    rutas.add(rutita);


                }

                recyclerView = view.findViewById(R.id.rv_rutas);
                cargando = view.findViewById(R.id.pb_loading_indicator);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                Adaptador adaptador = new Adaptador(getContext(),rutas, actividadPrincipal);
                recyclerView.setAdapter(adaptador);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                        recyclerView.getContext(), LinearLayoutManager.VERTICAL);

                recyclerView.addItemDecoration(dividerItemDecoration);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }


        });



        return view;
    }
}
