package dgp.ugr.granaroutes.data;

import android.net.Uri;

public class Ruta {
    int numero;
    String nombre;
    String descripcion;
    Uri referenciaMapa;

    public Ruta(int numero, String nombre, String descripcion, Uri referenciaMapa) {
        this.numero = numero;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.referenciaMapa = referenciaMapa;
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

    public Uri getReferenciaMapa() {
        return referenciaMapa;
    }
}
