package dgp.ugr.granaroutes.data;


import android.support.annotation.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

public class Valoracion {
    private int identificador;
    private String descripcion;
    private String usuario;
    private String valoracion;


    public Valoracion() {
    }

    public Valoracion(String descripcion, String usuario, String valoracion) {
        this.descripcion = descripcion;
        this.usuario = usuario;
        setValoracion(valoracion);
    }

    int cogerIdentificador() {
        return identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getValoracion() {
        return valoracion;
    }
    public float cogerValoracionNumerica(){
        String cambiaComasPorPuntos = valoracion.replaceAll(",",".");
        return Float.parseFloat(cambiaComasPorPuntos);
    }

    @NonNull
    @Override
    public String toString() {
        return "Valoracion{" +
                "identificador=" + identificador +
                ", descripcion='" + descripcion + '\'' +
                ", usuario='" + usuario + '\'' +
                ", valoracion='" + valoracion + '\'' +
                '}';
    }

    public void setIdentificador(int i) {
        identificador = i;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private void setValoracion(String valoracion) {
        this.valoracion = valoracion.replaceAll("\\.",",");
    }

    public void modificarValoracion(float valoracion) {
        setValoracion(Float.toString(valoracion));
    }
}
