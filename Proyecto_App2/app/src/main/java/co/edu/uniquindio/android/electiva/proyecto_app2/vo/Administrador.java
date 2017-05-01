package co.edu.uniquindio.android.electiva.proyecto_app2.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Clase para crear objetos de tipo Administrador con los atributos: nombre, apellido, contraseña, correo.
 * Implementa Parcelable para enviar objetos entre actividades
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class Administrador implements Parcelable {
    String nombre;
    String apellido;
    String contrasenia;
    String correo;

    /**
     * Método constructor de la clase
     */
    public Administrador() {
    }

    /**
     * Método de lectura del parcelable
     *
     * @param in pacelable de entrada
     */
    protected Administrador(Parcel in) {
        nombre = in.readString();
        apellido = in.readString();
        contrasenia = in.readString();
        correo = in.readString();
    }

    /**
     * Creador para un Administrador
     */
    public static final Creator<Administrador> CREATOR = new Creator<Administrador>() {
        @Override
        public Administrador createFromParcel(Parcel in) {
            return new Administrador(in);
        }

        @Override
        public Administrador[] newArray(int size) {
            return new Administrador[size];
        }
    };

    /**
     * Método que permite describir el contenido de un administrador
     *
     * @return entero que representa el contendio de un administrador
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
        dest.writeString(contrasenia);
        dest.writeString(correo);
    }

    /**
     * Método que permite obtener el nombre de un administrador
     *
     * @return nombre del administrador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método que permite obtener el nombre de un administrador
     *
     * @param nombre nombre del administrador
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método que permite obtener el apellido de un administrador
     *
     * @return apellido del administrador
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Método que permite definir el apellido del administrador
     *
     * @param apellido apellido del administrador
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Método que permite obtener la contraseña del administrador
     *
     * @return contraseña del administrador
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Método que permite definir la contraseña del administrador
     *
     * @param contrasenia contrasenia del administrador
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Método que permite obtener el correo del administrador
     *
     * @return correo del administrador
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Método que permite definir el correo del administrador
     *
     * @param correo correo del administrador
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
