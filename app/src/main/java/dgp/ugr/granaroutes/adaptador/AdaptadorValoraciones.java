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
import dgp.ugr.granaroutes.data.Valoracion;


//TODO TERMINAR VISTA DE VALORACIONES

public class AdaptadorValoraciones extends RecyclerView.Adapter<AdaptadorValoraciones.ValoracionViewHolder> {
    private Context context;
    private ArrayList<Valoracion> valoraciones;
    private AdministradorClickValoraciones clickHandler;


    public interface AdministradorClickValoraciones {
        void onClick(Valoracion Valoracion);

        void muestraNoHayDatos();
    }


    public AdaptadorValoraciones(Context context, ArrayList<Valoracion> valoracionesRemotas,
                                 AdministradorClickValoraciones handler) {
        this.context = context;
        valoraciones = valoracionesRemotas;
        clickHandler = handler;
    }

    @NonNull
    @Override
    public AdaptadorValoraciones.ValoracionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_lista, viewGroup, false);
        return new ValoracionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull AdaptadorValoraciones.ValoracionViewHolder ValoracionViewHolder, int position) {


    }


    @Override
    public int getItemCount() {
        return valoraciones.size();
    }

    class ValoracionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;
        TextView descripcion;


        ValoracionViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.descripcion_elemento);
            descripcion = itemView.findViewById(R.id.subdescripcion_elemento);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Valoracion valoracion = valoraciones.get(adapterPosition);
            clickHandler.onClick(valoracion);
        }
    }
}
