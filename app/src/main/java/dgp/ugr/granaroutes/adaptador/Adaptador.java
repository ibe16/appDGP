package dgp.ugr.granaroutes.adaptador;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.data.ProveedorContenidos;
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

        void reorganizarDatos();
        void muestraNoHayDatos();
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
        View view = inflater.inflate(R.layout.layout_item_lista, viewGroup,false);
        return new RutaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull Adaptador.RutaViewHolder rutaViewHolder,int position) {
        final Ruta ruta = rutas.get(position);
        rutaViewHolder.titulo.setText(ruta.getNombre());
        rutaViewHolder.descripcion.setText(ruta.getDescripcion().trim());
        final int posicion = rutaViewHolder.getAdapterPosition();

        rutaViewHolder.estrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rutas.get(posicion).clickFavorito();
                if(rutas.get(posicion).isFavorito()){
                    ProveedorContenidos.getInstance().aniadeRutaFavorita(posicion);
                }
                else{
                    ProveedorContenidos.getInstance().quitaRutaFavorita(rutas.get(posicion));
                    notifyDataSetChanged();
                }
                if(getItemCount() > 0)
                    pintarEstrella(rutaViewHolder, posicion);
                else
                    clickHandler.muestraNoHayDatos();


            }
        });

        pintarEstrella(rutaViewHolder, posicion);
    }

    private void pintarEstrella(Adaptador.RutaViewHolder rutaViewHolder, int posicion){
        if(rutas.get(posicion).isFavorito()){
            rutaViewHolder.estrella.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black));
            rutaViewHolder.estrella.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_selected));
        }
        else{
            rutaViewHolder.estrella.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icono_estrella));
            rutaViewHolder.estrella.setColorFilter(ContextCompat.getColor(context, R.color.icon_tint_normal));
        }
    }

    @Override
    public int getItemCount() {
        return rutas.size();
    }

    class RutaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titulo;
        TextView descripcion;
        ImageButton estrella;


        RutaViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.descripcion_elemento);
            descripcion = itemView.findViewById(R.id.subdescripcion_elemento);
            estrella = itemView.findViewById(R.id.icono_estrella);
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
