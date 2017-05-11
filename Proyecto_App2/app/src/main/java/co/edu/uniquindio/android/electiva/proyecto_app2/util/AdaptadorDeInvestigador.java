package co.edu.uniquindio.android.electiva.proyecto_app2.util;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;

/**
 * Clase que representa el adaptador para la lista de investigadores mediante el uso del RecyclerView y el patron ViewHolder
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class AdaptadorDeInvestigador extends RecyclerView.Adapter<AdaptadorDeInvestigador.IntegranteViewHolder> {

    private ArrayList<Investigador> investigadors;
    private static OnClickAdaptadorDeIntegrante listener;

    /**
     * Método constructor que inicializa el valor para los investigadores y el capturador de eventos para la seleccion de los mismos
     *
     * @param investigadors          Lista de investigadores
     * @param investigadoresFragment Fragmento que contiene la lista de investigadores
     */
    public AdaptadorDeInvestigador(ArrayList<Investigador> investigadors, Fragment investigadoresFragment) {
        this.investigadors = investigadors;
        listener = (OnClickAdaptadorDeIntegrante) investigadoresFragment;
    }

    /**
     * Método que se ejecuta al crear el ViewHolder
     *
     * @param parent   vista padre
     * @param viewType número que indica el tipo de vista
     * @return ViewHolder para el investigador
     */
    @Override
    public IntegranteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_integrantes, parent, false);
        AdaptadorDeInvestigador.IntegranteViewHolder integranteVH = new AdaptadorDeInvestigador.IntegranteViewHolder(itemView);
        return integranteVH;
    }

    /**
     * Método que se ejecuta al asociar el ViewHolder con el elemento del arraylist de investigadores
     *
     * @param holder   ViewHolder que se asociará
     * @param position posición del arraylist de investigador
     */
    @Override
    public void onBindViewHolder(IntegranteViewHolder holder, int position) {
        Investigador investigador = investigadors.get(position);
        holder.binIntegrante(investigador);
    }

    /**
     * Método que permite obtener el número de investigadores
     *
     * @return número de investigadores
     */
    @Override
    public int getItemCount() {
        return investigadors.size();
    }

    /**
     * Interfaz utilizada para comunicar el evento de selección en el adaptador
     */
    public interface OnClickAdaptadorDeIntegrante {
        public void onClickPositionIntegrante(int pos);
    }

    /**
     * Clase estática que implementa el patron ViewHolder para el adaptador de investigador
     *
     * @version 1.0
     */
    public static class IntegranteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.nombreInvestigador)
        protected TextView txtNombre;
        @BindView(R.id.apellido)
        protected TextView txtApellido;
        @BindView(R.id.linea1_investigador)
        protected TextView txtLinea1;
        @BindView(R.id.linea2_investigador)
        protected TextView txtLinea2;

        protected Unbinder unbinder;

        /**
         * Método constructor que se encarga de inicializar los valores
         *
         * @param itemView vista sobre la que se aplicarán las acciones
         */
        public IntegranteViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            unbinder = ButterKnife.bind(this,itemView);
        }

        /**
         * Método utilizado para asociar el investigador al ViewHolder
         *
         * @param investigador investigador al que se desea aplicar el patron
         */
        public void binIntegrante(Investigador investigador) {
            txtNombre.setText(investigador.getNombre());
            txtApellido.setText(investigador.getApellido());
            if (investigador.getLineasInvestigacion().size() != 0) {
                txtLinea1.setText((String) investigador.getLineasInvestigacion().get(0));
                txtLinea2.setText((String) investigador.getLineasInvestigacion().get(1));
            } else {
                txtLinea1.setText(itemView.getContext().getString(R.string.sin_lineas));
            }
        }

        /**
         * Método que captura los eventos de tipo Click sobre los investigadores disponibles en el adaptador
         *
         * @param view vista sobre la que se ejecuta el evento
         */
        @Override
        public void onClick(View view) {
            listener.onClickPositionIntegrante(getAdapterPosition());
        }
    }
}
