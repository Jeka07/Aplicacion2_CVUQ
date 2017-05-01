package co.edu.uniquindio.android.electiva.proyecto_app2.util;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;


/**
 * Clase que representa el adaptador para la lista de grupos mediante el uso del RecyclerView y el patron ViewHolder
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class AdaptadorDeGrupo extends RecyclerView.Adapter<AdaptadorDeGrupo.GrupoViewHolder> {

    private ArrayList<Grupo> grupos;
    private static OnClickAdaptadorDeGrupo listener;

    /**
     * Método constructor que inicializa el valor para los grupos y el capturador de eventos para la seleccion de los mismos
     *
     * @param grupos                Lista de grupos
     * @param listaDeGruposFragment Fragmento que contiene la lista de grupos
     */
    public AdaptadorDeGrupo(ArrayList<Grupo> grupos, Fragment listaDeGruposFragment) {
        this.grupos = grupos;
        listener = (OnClickAdaptadorDeGrupo) listaDeGruposFragment;
    }

    /**
     * Método que se ejecuta al crear el ViewHolder
     *
     * @param parent   vista padre
     * @param viewType número que indica el tipo de vista
     * @return ViewHolder para el grupo
     */
    @Override
    public GrupoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listado_grupos, parent, false);
        GrupoViewHolder grupoVH = new GrupoViewHolder(itemView);
        return grupoVH;
    }

    /**
     * Método que se ejecuta al asociar el ViewHolder con el elemento del arraylist de grupos
     *
     * @param holder   ViewHolder que se asociará
     * @param position posición del arraylist de grupos
     */
    @Override
    public void onBindViewHolder(GrupoViewHolder holder, int position) {
        Grupo grupoC = grupos.get(position);
        holder.binGrupo(grupoC);
    }

    /**
     * Interfaz utilizada para comunicar el evento de selección en el adaptador
     */
    public interface OnClickAdaptadorDeGrupo {
        public void onClickPositionGrupo(int pos);
    }

    /**
     * Método que permite obtener el número de grupos
     *
     * @return número de grupos
     */
    @Override
    public int getItemCount() {
        return grupos.size();
    }

    /**
     * Clase estática que implementa el patron ViewHolder para el adaptador de grupo
     *
     * @version 1.0
     */
    public static class GrupoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombreGrupo;
        private TextView txtSigla;
        private TextView txtLinea1;
        private TextView txtLinea2;

        /**
         * Método constructor que se encarga de inicializar los valores
         *
         * @param itemView vista sobre la que se aplicarán las acciones
         */
        public GrupoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtNombreGrupo = (TextView)
                    itemView.findViewById(R.id.nombre);
            txtSigla = (TextView)
                    itemView.findViewById(R.id.sigla);
            txtLinea1 = (TextView)
                    itemView.findViewById(R.id.linea1);
            txtLinea2 = (TextView)
                    itemView.findViewById(R.id.linea2);
        }

        /**
         * Método utilizado para asociar el grupo al ViewHolder
         *
         * @param grupo grupo al que se desea aplicar el patron
         */
        public void binGrupo(Grupo grupo) {
            txtNombreGrupo.setText(grupo.getNombre());
            txtSigla.setText(grupo.getSigla());
            if (grupo.getLineasInvestigacion().size() != 0) {
                txtLinea1.setText((String) grupo.getLineasInvestigacion().get(0));
                txtLinea2.setText((String) grupo.getLineasInvestigacion().get(1));
            } else {
                txtLinea1.setText(itemView.getContext().getString(R.string.sin_lineas));
            }
        }

        /**
         * Método que captura los eventos de tipo Click sobre los grupos disponibles en el adaptador
         *
         * @param view vista sobre la que se ejecuta el evento
         */
        @Override
        public void onClick(View view) {
            listener.onClickPositionGrupo(getAdapterPosition());
        }
    }


}
