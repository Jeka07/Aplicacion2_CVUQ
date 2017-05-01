package co.edu.uniquindio.android.electiva.proyecto_app2.util;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;


/**
 * Clase que representa el adaptador para la lista de líneas de investigación mediante el uso del RecyclerView
 * y el patron ViewHolder
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class AdaptadorDeLinea extends RecyclerView.Adapter<AdaptadorDeLinea.LineaViewHolder> {

    private ArrayList<String> lineas;
    private static OnClickAdaptadorDeLinea listener;

    /**
     * Método constructor que inicializa el valor para los investigadores y el capturador de eventos para la seleccion de los mismos
     *
     * @param lineas                      Lista de líneas de investigación
     * @param lineasInvestigacionFragment Fragmento que contiene la lista de líneas de investigación
     */
    public AdaptadorDeLinea(ArrayList<String> lineas, Fragment lineasInvestigacionFragment) {
        this.lineas = lineas;
        listener = (OnClickAdaptadorDeLinea) lineasInvestigacionFragment;
    }

    /**
     * Método que se ejecuta al crear el ViewHolder
     *
     * @param parent   vista padre
     * @param viewType número que indica el tipo de vista
     * @return ViewHolder para las líneas de investigación
     */
    @Override
    public AdaptadorDeLinea.LineaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_lineas, parent, false);
        AdaptadorDeLinea.LineaViewHolder lineaVH = new AdaptadorDeLinea.LineaViewHolder(itemView);
        return lineaVH;
    }

    /**
     * Método que se ejecuta al asociar el ViewHolder con el elemento del arraylist de investigadores
     *
     * @param holder   ViewHolder que se asociará
     * @param position posición del arraylist de investigador
     */
    @Override
    public void onBindViewHolder(AdaptadorDeLinea.LineaViewHolder holder, int position) {
        String linea = lineas.get(position);
        holder.binLinea(linea);
    }

    /**
     * Interfaz utilizada para comunicar el evento de selección en el adaptador
     */
    public interface OnClickAdaptadorDeLinea {
        public void onClickPosition(int pos);
    }

    /**
     * Método que permite obtener el número de líneas de investigación
     *
     * @return número de investigación
     */
    @Override
    public int getItemCount() {
        return lineas.size();
    }

    /**
     * Clase estática que implementa el patron ViewHolder para el adaptador de líneas de investigación
     *
     * @version 1.0
     */
    public static class LineaViewHolder extends RecyclerView.ViewHolder {

        private TextView txtLinea;

        /**
         * Método constructor que se encarga de inicializar los valores
         *
         * @param itemView vista sobre la que se aplicarán las acciones
         */
        public LineaViewHolder(View itemView) {
            super(itemView);
            txtLinea = (TextView)
                    itemView.findViewById(R.id.linea_investigacion);
        }

        /**
         * Método utilizado para asociar la línea de investigación al ViewHolder
         *
         * @param linea Línea de investigación al que se desea aplicar el patron
         */
        public void binLinea(String linea) {
            txtLinea.setText(linea);
        }
    }
}
