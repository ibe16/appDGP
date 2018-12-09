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
import dgp.ugr.granaroutes.data.DataListener;
import dgp.ugr.granaroutes.data.Ruta;

import static android.support.constraint.Constraints.TAG;

public class FragmentoRutas extends Fragment implements DataListener{

    private RecyclerView recyclerView;
    private ProgressBar cargando;
    private Adaptador adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_actividad_rutas, null);
        recyclerView = view.findViewById(R.id.rv_rutas);
        cargando = view.findViewById(R.id.pb_loading_indicator);



        if(ContentProvider.getInstance().getRutas() == null || ContentProvider.getInstance().getRutas().isEmpty()) {
            mostrarCargando();
            ContentProvider.getInstance().leerRutas(this);
        }
        else{
            lecturaTerminada();
        }

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


    @Override
    public void lecturaTerminada() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaptador = new Adaptador(getContext(), ContentProvider.getInstance().getRutas(), (ActividadPrincipal) getActivity());
        recyclerView.setAdapter(adaptador);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mostrarDatos();
    }

    public Adaptador getAdaptador() {
        return adaptador;
    }
}
