package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;

/**
 * Fragmento que se encarga de mostrar la interfaz de inicio.
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class InicioFragment extends Fragment {

    TextView txtNombreAdmin;
    String nombreAdmin;

    /**
     * Método constructor de la vista
     */
    public InicioFragment() {
    }

    /**
     * Método que se ejecuta en la creación de la vista
     *
     * @param inflater           inflador de la vista
     * @param container          contenedor de la vista
     * @param savedInstanceState permite recuperar la configuración almacenada
     * @return vista inflada para el fragmento
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        nombreAdmin = getArguments().getString("nombreAdmin");
        View x = inflater.inflate(R.layout.fragment_inicio, container, false);
        return x;
    }

    /**
     * Método que se encarga de la inicialización de los elementos del fragmento
     *
     * @param savedInstanceState información enviada para mantener
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtNombreAdmin = (TextView) getView().findViewById(R.id.nombre_usuario_log);
        txtNombreAdmin.setText(nombreAdmin);
    }
}
