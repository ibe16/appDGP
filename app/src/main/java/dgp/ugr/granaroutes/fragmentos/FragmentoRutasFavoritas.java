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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import dgp.ugr.granaroutes.data.DataListener;
import dgp.ugr.granaroutes.data.Ruta;

import static android.support.constraint.Constraints.TAG;

public class FragmentoRutasFavoritas extends Fragment implements DataListener{

    private RecyclerView recyclerView;
    private TextView cartelNoRutas;
    private Adaptador adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_actividad_rutas_favoritas, null);
        recyclerView = view.findViewById(R.id.rv_rutas_favoritas);
        cartelNoRutas = view.findViewById(R.id.no_rutas);

        if(ContentProvider.getInstance().getRutasFavoritas() == null ||
                ContentProvider.getInstance().getRutasFavoritas().isEmpty()) {
            mostrarNoHayFavoritas();
        }
        else{
            lecturaTerminada();
        }

        return view;
    }

    private void mostrarNoHayFavoritas() {
        recyclerView.setVisibility(View.INVISIBLE);
        cartelNoRutas.setVisibility(View.VISIBLE);
    }

    private void mostrarDatos() {
        recyclerView.setVisibility(View.VISIBLE);
        cartelNoRutas.setVisibility(View.INVISIBLE);
    }


    @Override
    public void lecturaTerminada() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaptador = new Adaptador(getContext(), ContentProvider.getInstance().getRutasFavoritas(), (ActividadPrincipal) getActivity());
        recyclerView.setAdapter(adaptador);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mostrarDatos();
    }

    @Override
    public void reorganizarDatos() {

    }

    public Adaptador getAdaptador() {
        return adaptador;
    }

}

