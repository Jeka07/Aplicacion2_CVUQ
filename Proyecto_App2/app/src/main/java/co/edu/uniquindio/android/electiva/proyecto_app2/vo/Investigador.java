package co.edu.uniquindio.android.electiva.proyecto_app2.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Clase para crear objetos de tipo Investigador con los atributos: nombre, apellido, link, categoria,
 * líneas de investigación, grupo, email, foto, genero, formación, nacionalidad.
 * Implementa Parcelable para enviar objetos entre actividades
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class Investigador implements Parcelable {

    String nombre;
    String apellido;
    private String link;
    private String categoria;
    private ArrayList lineasInvestigacion;
    private Grupo grupo;
    private String email;
    private int foto;
    private String genero;
    private String Formacion;
    private String nacionalidad;

    /**
     * Método constructor de la clase
     */
    public Investigador() {
        lineasInvestigacion = new ArrayList();
    }

    /**
     * Método de lectura del parcelable
     *
     * @param in pacelable de entrada
     */
    protected Investigador(Parcel in) {
        nombre = in.readString();
        apellido = in.readString();
        link = in.readString();
        categoria = in.readString();
        grupo = in.readParcelable(Grupo.class.getClassLoader());
        email = in.readString();
        foto = in.readInt();
        genero = in.readString();
        Formacion = in.readString();
        nacionalidad = in.readString();
        lineasInvestigacion = in.createStringArrayList();
    }

    /**
     * Creador para un investigador
     */
    public static final Creator<Investigador> CREATOR = new Creator<Investigador>() {
        @Override
        public Investigador createFromParcel(Parcel in) {
            return new Investigador(in);
        }

        @Override
        public Investigador[] newArray(int size) {
            return new Investigador[size];
        }
    };

    /**
     * Método que permite describir el contenido de un investigador
     *
     * @return entero que representa el contendio de un investigador
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
        dest.writeString(apellido);
        dest.writeString(link);
        dest.writeString(categoria);
        dest.writeParcelable(grupo, flags);
        dest.writeString(email);
        dest.writeInt(foto);
        dest.writeString(genero);
        dest.writeString(Formacion);
        dest.writeString(nacionalidad);
        dest.writeStringList(lineasInvestigacion);
    }

    /**
     * Método que permite obtener el nombre de un investigador
     *
     * @return nombre del investigador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que permite obtener el nombre de un investigador
     *
     * @param nombre nombre del investigador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el apellido de un investigador
     *
     * @return apellido del investigador
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Método que permite definir el apellido del investigador
     *
     * @param apellido apellido del investigador
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Método que permite obtener link de un investigador
     *
     * @return link del investigador
     */
    public String getLink() {
        return link;
    }

    /**
     * Método que permite definir link de un investigador
     *
     * @param link link del investigador
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Método que permite obtener la categoria de un investigador
     *
     * @return categoria del investigador
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Método que permite definir la categoria de un investigador
     *
     * @param categoria categoria del investigador
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Método que permite obtener las lineas de investigaión del investigador
     *
     * @return Lineas de investigación del investigador
     */
    public ArrayList getLineasInvestigacion() {
        return lineasInvestigacion;
    }

    /**
     * Método que permite definir las lineas de investigaión del investigador
     *
     * @param lineasInvestigacion Lineas de investigación
     */
    public void setLineasInvestigacion(ArrayList lineasInvestigacion) {
        this.lineasInvestigacion = lineasInvestigacion;
    }

    /**
     * Método que permite obtener el grupo al cual esta asociado el investigador
     *
     * @return grupo de invesitigación
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * Método que permite definir el grupo al cual esta asociado el investigador
     *
     * @param grupo grupo de investigación asociado al investigador
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * Método que permite obtener el email de un investigador
     *
     * @return email del investigador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que permite definir el email de un investigador
     *
     * @param email email del investigador
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que permite obtener la foto de un investigador
     *
     * @return foto del investigador
     */
    public int getFoto() {
        return foto;
    }

    /**
     * Método que permite definir la foto de un investigador
     *
     * @param foto foto del investigador
     */
    public void setFoto(int foto) {
        this.foto = foto;
    }

    /**
     * Método que permite obtener el genero del investigador
     *
     * @return genero del investigador
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Método que permite definir el genero del investigador
     *
     * @param genero genero del investigador
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Método que permite obtener la formación académica del investigador
     *
     * @return formación académica del investigador
     */
    public String getFormacion() {
        return Formacion;
    }

    /**
     * Método que permite definir la formacion academica del investigador
     *
     * @param formacion formacióon academia del investigador
     */
    public void setFormacion(String formacion) {
        Formacion = formacion;
    }

    /**
     * Método que permite obtener la nacionalidad del investigador
     *
     * @return nacionalidad del investigador
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Método que permite definir la nacionalidad del investigador
     *
     * @param nacionalidad nacionalidad del investigador
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
