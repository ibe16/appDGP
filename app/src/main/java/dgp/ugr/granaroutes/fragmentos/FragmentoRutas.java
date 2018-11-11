package dgp.ugr.granaroutes.fragmentos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import dgp.ugr.granaroutes.R;

public class FragmentoRutas extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar cargando;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_actividad_rutas, null);

        recyclerView = view.findViewById(R.id.rv_rutas);
        cargando = view.findViewById(R.id.pb_loading_indicator);

        return view;
    }
}
