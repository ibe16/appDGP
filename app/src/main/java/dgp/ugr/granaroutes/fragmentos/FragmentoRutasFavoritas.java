package dgp.ugr.granaroutes.fragmentos;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.actividades.ActividadPrincipal;
import dgp.ugr.granaroutes.adaptador.AdaptadorRutas;
import dgp.ugr.granaroutes.data.ProveedorContenidos;
import dgp.ugr.granaroutes.data.Ruta;

public class FragmentoRutasFavoritas extends FragmentoRutas{

    private TextView cartelNoRutasFavoritas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_actividad_rutas_favoritas, container, false);
        recyclerView = view.findViewById(R.id.rv_rutas_favoritas);
        cartelNoRutasFavoritas = view.findViewById(R.id.no_rutas);

        if(rutasVacias()) {
            mostrarNoHayDatos();
        }
        else{
            terminarInicializacion();
        }

        return view;
    }

    @Override
    public void mostrarNoHayDatos() {
        recyclerView.setVisibility(View.INVISIBLE);
        cartelNoRutasFavoritas.setVisibility(View.VISIBLE);
    }


    @Override
    public void mostrarDatos() {
        recyclerView.setVisibility(View.VISIBLE);
        cartelNoRutasFavoritas.setVisibility(View.INVISIBLE);
    }

    @Override
    public void terminarInicializacion() {

        ArrayList<Ruta> rutasFavoritas = ProveedorContenidos.getInstance().getRutasFavoritas();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaptadorRutas = new AdaptadorRutas(getContext(), rutasFavoritas , (ActividadPrincipal) getActivity());
        recyclerView.setAdapter(adaptadorRutas);

        mostrarDatos();
    }

    @Override
    protected boolean rutasVacias() {
        return ProveedorContenidos.getInstance().getRutasFavoritas() == null ||
                ProveedorContenidos.getInstance().getRutasFavoritas().isEmpty();
    }
}
