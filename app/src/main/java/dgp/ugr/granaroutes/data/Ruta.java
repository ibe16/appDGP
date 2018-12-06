package dgp.ugr.granaroutes.data;



import android.net.Uri;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ruta {
    private static final AtomicInteger contador = new AtomicInteger(0);
    int numero;
    String nombre;
    String descripcion;
    String map;
    Map<String, Boolean> grupos;
    Map<String, Boolean> lugares;

    public Ruta() {
        numero = contador.incrementAndGet();
    }

    public Ruta(String nombre, String descripcion, String map, Map<String, Boolean> grupos, Map<String, Boolean> lugares) {
        numero = contador.incrementAndGet();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.map = map;
        this.grupos = grupos;
        this.lugares = lugares;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMap() {
        return map;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setGrupos(Map<String, Boolean> grupos) {
        this.grupos = grupos;
    }

    public void setLugares(Map<String, Boolean> lugares) {
        this.lugares = lugares;
    }

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
}
