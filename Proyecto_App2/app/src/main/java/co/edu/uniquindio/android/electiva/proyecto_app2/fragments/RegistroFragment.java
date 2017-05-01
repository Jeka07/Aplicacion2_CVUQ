package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.MainActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;

/**
 * Fragmento que se encarga de realizar las acciones necesarias para el registro de los nuevos administradores
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class RegistroFragment extends Fragment implements View.OnClickListener {


    Button btnRegistro;
    AlphaAnimation animation1;
    ArrayList<Administrador> administradores;

    EditText txtNombre;
    EditText txtApellido;
    EditText txtCorreo;
    EditText txtContrasenia;
    EditText txtContraseniaConf;

    boolean registroCorrecto;

    OnButtonRegistrarListener listener;

    /**
     * Método constructor del fragmento
     */
    public RegistroFragment() {
    }

    /**
     * Método que se ejecuta en la creación de la vista
     *
     * @param inflater           inflador de la vista
     * @param container          contenedor de la vista
     * @param savedInstanceState permite recuperar la configuración almacenada
     * @return vista inflada para el fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_registro, container, false);
        btnRegistro = (Button) x.findViewById(R.id.agregar_administrador);
        txtNombre = (EditText) x.findViewById(R.id.agregar_nombre);
        txtApellido = (EditText) x.findViewById(R.id.agregar_apellido);
        txtCorreo = (EditText) x.findViewById(R.id.agregar_correo);
        txtContrasenia = (EditText) x.findViewById(R.id.agregar_contrasenia);
        txtContraseniaConf = (EditText) x.findViewById(R.id.registr_confirmar_contrasenia);

        if (getArguments() != null) {
            Log.d("PAse","PAse por el argumento de admin");
            administradores = getArguments().getParcelableArrayList(MainActivity.LISTA_ADMINISTRADORES);
        } else {
            administradores = llenarAdministradores();
        }

        btnRegistro.setOnClickListener(this);
        animacionButton();
        return x;
    }

    /**
     * Método que gestiona los eventos de tipo Click sobre las vistas
     *
     * @param v vista sobre la que se genera el evento
     */
    @Override
    public void onClick(View v) {
        v.startAnimation(animation1);
        if (v.getId() == btnRegistro.getId()) {
            String mensaje = "";
            if (!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("")
                    && !txtCorreo.getText().toString().equals("") && !txtContrasenia.getText().toString().equals("")
                    && !txtContraseniaConf.getText().toString().equals("")) {
                if (txtContraseniaConf.getText().toString().equals(txtContrasenia.getText().toString())) {
                    Administrador admin = new Administrador();
                    admin.setNombre(txtNombre.getText().toString());
                    admin.setApellido(txtApellido.getText().toString());
                    admin.setCorreo(txtCorreo.getText().toString());
                    admin.setContrasenia(txtContrasenia.getText().toString());
                    administradores.add(admin);
                    registroCorrecto = true;
                    mensaje = getResources().getString(R.string.mensaje_registro_correcto);
                } else {
                    mensaje = (String) getResources().getText(R.string.mensaje_alerta_contrasenias_erroneas);
                    registroCorrecto = false;
                }
            } else {
                mensaje = (String) getResources().getText(R.string.mensaje_campos_vacios);
                registroCorrecto = false;
            }
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(txtContrasenia.getWindowToken(), 0);
            listener.onRegistradoListener(mensaje,registroCorrecto);
        }
    }

    /**
     * Método que crea una animación para mostrar al presionar los botones
     */
    private void animacionButton() {
        animation1 = new AlphaAnimation(0.2f, 1.0f);
        animation1.setDuration(2);
        animation1.setStartOffset(5);
        animation1.setFillAfter(true);
    }

    /**
     * Interfaz utilizada para comunicar el evento de generado al registrar un administrador
     */
    public interface OnButtonRegistrarListener {
        void onRegistradoListener(String mensaje,boolean registroCorrecto);

    }

    /**
     * Método que se ejecuta cuando se asocia el fragmento con la actividad
     *
     * @param context contexto que representa la actividad que maneja el fragmento
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
            try {
                listener = (OnButtonRegistrarListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " debe implementar la interfaz OnButtonRegistrarListener");
            }
        }
    }

    /**
     * Método que permite obtener la lista de administradores registrados
     *
     * @return lista de administradores
     */
    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    /**
     * Método que permite modificar la lista de administradores registrados
     *
     * @param administradores lista de administradores modificada
     */
    public void setAdministradores(ArrayList<Administrador> administradores) {
        this.administradores = administradores;
    }

    /**
     * Método que se encarga de agregar los datos iniciales a la lista de administradores
     *
     * @return Lista con los datos de los administradores
     */
    public ArrayList<Administrador> llenarAdministradores() {
        Administrador admin1 = new Administrador();
        admin1.setNombre("Lili");
        admin1.setApellido("Rojas");
        admin1.setCorreo("lili@micorreo.com");
        admin1.setContrasenia("123");

        Administrador admin2 = new Administrador();
        admin2.setNombre("Alberto");
        admin2.setApellido("Montolla");
        admin2.setCorreo("alberto@micorreo.com");
        admin2.setContrasenia("123");

        Administrador admin3 = new Administrador();
        admin3.setNombre("p");
        admin3.setApellido("p");
        admin3.setCorreo("p");
        admin3.setContrasenia("p");

        ArrayList<Administrador> admin = new ArrayList<>();
        admin.add(admin1);
        admin.add(admin2);
        admin.add(admin3);

        return admin;
    }
}
