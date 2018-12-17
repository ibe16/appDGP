package dgp.ugr.granaroutes.data;


import android.support.annotation.NonNull;

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
        this.valoracion = valoracion.replaceAll("\\.",",");
    }

    public int getIdentificador() {
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

    void setIdentificador(int i) {
        identificador = i;
    }

    public void setUsuario(String cambiado) {
        usuario = cambiado;
    }
}
