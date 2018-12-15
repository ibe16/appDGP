package dgp.ugr.granaroutes.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.Valoracion;



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
        View view = inflater.inflate(R.layout.layout_valoracion_item, viewGroup, false);
        return new ValoracionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull AdaptadorValoraciones.ValoracionViewHolder ValoracionViewHolder, int position) {
        final Valoracion valoracion = valoraciones.get(position);
        ValoracionViewHolder.usuario.setText(valoracion.getUsuario());
        ValoracionViewHolder.descripcion.setText(valoracion.getDescripcion());
        String cambiaComasPorPuntos = valoracion.getValoracion().replaceAll(",",".");
        ValoracionViewHolder.puntuacion.setRating(Float.parseFloat(cambiaComasPorPuntos));

    }


    @Override
    public int getItemCount() {
        return valoraciones.size();
    }

    class ValoracionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView usuario;
        TextView descripcion;
        RatingBar puntuacion;


        ValoracionViewHolder(@NonNull View itemView) {
            super(itemView);

            usuario = itemView.findViewById(R.id.usuario_valoracion);
            descripcion = itemView.findViewById(R.id.descripcion_valoracion);
            puntuacion = itemView.findViewById(R.id.valoracion_numerica);
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
