package co.edu.uniquindio.android.electiva.proyecto_app2.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para crear objetos de tipo Administrador con los atributos: nombre, sigla, link, categoria
 * lider, líneas de investigación, investigadores, email y foto.
 * Implementa Parcelable para enviar objetos entre actividades
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class Grupo implements Parcelable {

    private String nombre;
    private String sigla;
    private String link;
    private String categoria;
    private Investigador lider;
    private List<String> lineasInvestigacion;
    private List<Investigador> investigadores;
    private String email;
    private int foto;

    /**
     * Método constructor de la clase
     */
    public Grupo() {
        investigadores = new ArrayList<Investigador>();
        lineasInvestigacion = new ArrayList<String>();
    }

    /**
     * Método de lectura del parcelable
     *
     * @param in pacelable de entrada
     */
    protected Grupo(Parcel in) {
        nombre = in.readString();
        sigla = in.readString();
        link = in.readString();
        categoria = in.readString();
        lider = in.readParcelable(Investigador.class.getClassLoader());
        lineasInvestigacion = in.createStringArrayList();
        investigadores = in.createTypedArrayList(Investigador.CREATOR);
        email = in.readString();
        foto = in.readInt();
    }

    /**
     * Creador para un grupo
     */
    public static final Creator<Grupo> CREATOR = new Creator<Grupo>() {
        @Override
        public Grupo createFromParcel(Parcel in) {
            return new Grupo(in);
        }

        @Override
        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };

    /**
     * Método que permite obtener el nombre de un administrador
     *
     * @return nombre del grupo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que permite definir el nombre de un grupo
     *
     * @param nombre nombre del grupo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el id de un personaje
     *
     * @return sigla del grupo
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Método que permite definir el id de un personaje
     *
     * @param sigla sigla del grupo
     */
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Método que permite obtener link de un grupo
     *
     * @return link del grupo
     */
    public String getLink() {
        return link;
    }

    /**
     * Método que permite definir link de un grupo
     *
     * @param link link del grupo
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Método que permite obtener la categoria de un grupo
     *
     * @return categoria del grupo
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Método que permite definir la categoria de un grupo
     *
     * @param categoria categoria del grupo
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Método que permite obtener el lider de un grupo
     *
     * @return lider del grupo
     */
    public Investigador getLider() {
        return lider;
    }

    /**
     * Método que permite definir el lider de un grupo
     *
     * @param lider lider del grupo
     */
    public void setLider(Investigador lider) {
        this.lider = lider;
    }

    /**
     * Método que permite obtener el email de un grupo
     *
     * @return email del grupo
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que permite definir el email de un grupo
     *
     * @param email email del grupo
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que permite obtener la foto de un grupo
     *
     * @return foto del grupo
     */
    public int getFoto() {
        return foto;
    }

    /**
     * Método que permite definir la foto de un grupo
     *
     * @param foto foto del grupo
     */
    public void setFoto(int foto) {
        this.foto = foto;
    }

    /**
     * Método que permite obtener la lista de investigadores asociados al grupo
     *
     * @return investigadores asociados al grupo
     */
    public List getInvestigadores() {
        return investigadores;
    }

    /**
     * Método que permite definir la lista de investigadores asociados al grupo
     *
     * @param investigadores investigadores asociados al grupo
     */
    public void setInvestigadores(ArrayList investigadores) {
        this.investigadores = investigadores;
    }

    /**
     * Método que permite obtener las lineas de investigaión del grupo
     *
     * @return Lineas de investigación del grupo
     */
    public List getLineasInvestigacion() {
        return lineasInvestigacion;
    }

    /**
     * Método que permite definir las lineas de investigaión del grupo
     *
     * @param lineasInvestigacion Lineas de investigación
     */
    public void setLineasInvestigacion(List lineasInvestigacion) {
        this.lineasInvestigacion = lineasInvestigacion;
    }

    /**
     * Método que permite describir el contenido de un grupo
     *
     * @return entero que representa el contendio de un grupo
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Método de escritura del parcelable
     *
     * @param dest  parcelable de destino
     * @param flags banderas para condicionar la escritura
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(sigla);
        dest.writeString(link);
        dest.writeString(categoria);
        dest.writeParcelable(lider, flags);
        dest.writeStringList(lineasInvestigacion);
        dest.writeTypedList(investigadores);
        dest.writeString(email);
        dest.writeInt(foto);
    }
}
