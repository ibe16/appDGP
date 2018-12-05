package dgp.ugr.granaroutes.adaptador_recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


//TODO rellenar el adaptador para el RecyclerView del fragmento, los datos de la base de datos externa

/**
 * Clase interna para manejar un sólo objeto en el recyclerView
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.RutaViewHolder> {
    private Context context;
    private ArrayList rutas;


    public Adaptador(Context context, ArrayList rutasRemotas) {
        this.context = context;
        rutas = rutasRemotas;
    }

    @NonNull
    @Override
    public Adaptador.RutaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.RutaViewHolder rutaViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class RutaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public RutaViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
