package dgp.ugr.granaroutes.fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.actividades.ActividadPrincipal;
import dgp.ugr.granaroutes.adaptador.AdaptadorRutas;
import dgp.ugr.granaroutes.datos.ProveedorContenidos;
import dgp.ugr.granaroutes.datos.RegistradorDatos;

public class FragmentoRutas extends Fragment implements RegistradorDatos {

    protected RecyclerView recyclerView;
    private ProgressBar cargando;
    protected AdaptadorRutas adaptadorRutas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_actividad_rutas,container, false);
        recyclerView = view.findViewById(R.id.rv_rutas);
        cargando = view.findViewById(R.id.pb_loading_indicator);

        if(rutasVacias()) {
            mostrarNoHayDatos();
            ProveedorContenidos.getInstance().obtenerRutas(this);
        }
        else{
            datosActualizados();
        }

        return view;
    }

    public void mostrarNoHayDatos() {
        recyclerView.setVisibility(View.INVISIBLE);
        cargando.setVisibility(View.VISIBLE);
    }

    public void mostrarDatos() {
        recyclerView.setVisibility(View.VISIBLE);
        cargando.setVisibility(View.INVISIBLE);
    }


    @Override
    public void datosActualizados() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaptadorRutas = new AdaptadorRutas(getContext(), ProveedorContenidos.getInstance().getRutas(), (ActividadPrincipal) getActivity());
        recyclerView.setAdapter(adaptadorRutas);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecoration);

        mostrarDatos();
    }


    public AdaptadorRutas getAdaptadorRutas() {
        return adaptadorRutas;
    }

    protected boolean rutasVacias(){
        return ProveedorContenidos.getInstance().getRutas() == null
                || ProveedorContenidos.getInstance().getRutas().isEmpty();
    }
}
