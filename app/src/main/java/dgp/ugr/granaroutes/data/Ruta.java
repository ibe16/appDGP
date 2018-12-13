package dgp.ugr.granaroutes.data;



import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Se quita la inspeccion, puesto que dice que los constructores no son utilizados
 * y son necesarios para incrustar los datos desde Firebase.
 */

@SuppressWarnings("unused")
public class Ruta {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int numero;
    private String nombre;
    private String descripcion;
    private String map;
    private Map<String, Boolean> grupos;
    private Map<String, Boolean> lugares;
    private boolean favorito;
    private String imagen;

    public Ruta() {
        numero = contador.incrementAndGet();
        favorito = false;
    }

    public Ruta(String nombre, String descripcion, String map, Map<String, Boolean> grupos,
                Map<String, Boolean> lugares, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.map = map;
        this.grupos = grupos;
        this.lugares = lugares;
        this.imagen = imagen;
        numero = contador.incrementAndGet();
        favorito = false;
    }



    int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Uri getMapUri() {
        return Uri.parse(map);
    }

    public Map<String, Boolean>getGrupos() {
        return grupos;
    }

    public Map<String, Boolean> getLugares() {
        return lugares;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public Uri getImagen() {
        return Uri.parse(imagen);
    }



    @NonNull
    @Override
    public String toString() {
        return "Ruta{" +
                "numero=" + numero +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", map=" + map +
                ", grupos=" + grupos +
                ", lugares=" + lugares +
                '}';
    }

    public void clickFavorito() {
        favorito = !favorito;
    }

}
