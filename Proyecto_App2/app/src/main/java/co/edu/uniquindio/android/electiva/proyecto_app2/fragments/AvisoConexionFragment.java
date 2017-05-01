package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;

/**
 * Fragmento que se muestra cuando el usuario no cuenta con conexion a internet
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class AvisoConexionFragment extends DialogFragment {

    /**
     * Método constructori del fragmento
     */
    public AvisoConexionFragment() {
    }

    /**
     * Método que se ejecuta en la creación de la vista
     *
     * @param savedInstanceState permite recuperar la configuración almacenada
     * @return el dialogo creado
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.aviso_conexion, null);
        builder.setView(view);
        return builder.create();
    }
}
