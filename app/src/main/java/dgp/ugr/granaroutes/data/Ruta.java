package dgp.ugr.granaroutes.data;



import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Ruta {
    private static final AtomicInteger contador = new AtomicInteger(0);
    private int numero;
    private String nombre;
    private String descripcion;
    private String map;
    private Map<String, Boolean> grupos;
    private Map<String, Boolean> lugares;
    private boolean favorito;

    public Ruta() {
        numero = contador.incrementAndGet();
        favorito = false;
    }

    public Ruta(String nombre, String descripcion, String map, Map<String, Boolean> grupos, Map<String, Boolean> lugares) {
        numero = contador.incrementAndGet();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.map = map;
        this.grupos = grupos;
        this.lugares = lugares;
        favorito = false;
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

    public boolean isFavorito() {
        return favorito;
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
