package dgp.ugr.granaroutes.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.Ruta;


/**
 * Clase interna para manejar un sólo objeto en el recyclerView
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.RutaViewHolder> {
    private Context context;
    private ArrayList<Ruta> rutas;
    private AdapterOnClickHandler clickHandler;


    public interface AdapterOnClickHandler{
        void onClick(Ruta ruta);
    }


    public Adaptador(Context context, ArrayList<Ruta> rutasRemotas, AdapterOnClickHandler handler) {
        this.context = context;
        rutas = rutasRemotas;
        clickHandler = handler;
    }

    @NonNull
    @Override
    public Adaptador.RutaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_lista, null);
        return new RutaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.RutaViewHolder rutaViewHolder, int position) {
        Ruta ruta = rutas.get(position);
        rutaViewHolder.titulo.setText(ruta.getNombre());
        rutaViewHolder.descripcion.setText(ruta.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return rutas.size();
    }

    class RutaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titulo;
        TextView descripcion;


        RutaViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.descripcion_elemento);
            descripcion = itemView.findViewById(R.id.subdescripcion_elemento);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Ruta ruta = rutas.get(adapterPosition);
            clickHandler.onClick(ruta);
        }
    }
}
