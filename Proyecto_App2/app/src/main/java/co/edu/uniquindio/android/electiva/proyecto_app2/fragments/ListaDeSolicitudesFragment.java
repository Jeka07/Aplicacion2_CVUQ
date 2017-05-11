package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeGrupoSolicitudes;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeInvestigadorSolicitudes;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.SolicitudesData;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;

/**
 * Fragmento que se encarga de mostrar la lista de solicitudes nuevas pendientes por administrar
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class ListaDeSolicitudesFragment extends Fragment implements AdaptadorDeGrupoSolicitudes.OnClickAdaptadorDeGrupo,
        AdaptadorDeInvestigadorSolicitudes.OnClickAdaptadorDeIntegrante {

    private AdaptadorDeGrupoSolicitudes adaptador;
    @BindView(R.id.lista_grupos_1)
    protected RecyclerView listadoGrupos;
    private ArrayList<Grupo> grupos;
    private OnGrupoSeleccionadoListener listener;

    private AdaptadorDeInvestigadorSolicitudes adaptadorDeInvestigadorSolicitudes;
    @BindView(R.id.lista_investigadores_1)
    protected RecyclerView listadoInvestigadores;
    private ArrayList<Investigador> investigadores;
    private OnIntegranteSeleccionadoListener listenerInvestigador1;
    private AdminActivity adminActivity;

    @BindView(R.id.mensaje_solicitudes_post)
    protected TextView mensaje;

    protected Unbinder unbinder;

    /**
     * Método constructor del fragmento
     */
    public ListaDeSolicitudesFragment() {
        // Required empty public constructor
        llenarListas();
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
        // Inflate the layout for this fragment
        adminActivity = (AdminActivity) getActivity();
        View v = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    /**
     * Método que se encarga de la inicialización de los elementos del fragmento
     *
     * @param savedInstanceState información enviada para mantener
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mensaje.setVisibility(View.INVISIBLE);

        if (grupos.size() != 0 || investigadores.size() != 0) {
            if (grupos.size() != 0) {
                adaptador = new AdaptadorDeGrupoSolicitudes(grupos, this);
                listadoGrupos.setAdapter(adaptador);
                listadoGrupos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {

                    DetalleDeGrupoFragment fragment = new DetalleDeGrupoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("grupo", grupos.get(0));
                    bundle.putString("tipo", adminActivity.SOLICITUDES_NUEVAS);
                    fragment.setArguments(bundle);
                    dibujarDetalle(fragment);
                }
            }
            if (investigadores.size() != 0) {
                adaptadorDeInvestigadorSolicitudes = new AdaptadorDeInvestigadorSolicitudes(investigadores, this);
                listadoInvestigadores.setAdapter(adaptadorDeInvestigadorSolicitudes);
                listadoInvestigadores.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null && grupos.size() == 0) {
                    DetalleDeInvestigadorFragment fragmento = new DetalleDeInvestigadorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("investigador", investigadores.get(0));
                    bundle.putString("tipo", adminActivity.SOLICITUDES_NUEVAS);
                    fragmento.setArguments(bundle);
                    dibujarDetalle(fragmento);
                }
            }

        } else {
            mensaje.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Método que se encarga de mostrar el framento cuando se encuentra en vistas landscape y en equipos grandes.
     *
     * @param fragment fragmento a dibujar
     */
    private void dibujarDetalle(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.vista_detalle_grupo_inv, fragment)
                .commit();
    }

    /**
     * Método que se ejecuta cuando se selecciona un grupo de la lista de solicitudes
     *
     * @param pos Posición del elemento seleccionado
     */
    @Override
    public void onClickPositionGrupo(int pos) {
        if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {
            DetalleDeGrupoFragment fragment = new DetalleDeGrupoFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("grupo", grupos.get(pos));
            bundle.putString("tipo", adminActivity.SOLICITUDES_NUEVAS);
            fragment.setArguments(bundle);
            dibujarDetalle(fragment);
        } else {
            listener.onGrupoSeleccionado(grupos.get(pos));
        }
    }

    /**
     * Método que se ejecuta cuando se selecciona un investigador de la lista de solicitudes
     *
     * @param pos Posición del elemento seleccionado
     */
    @Override
    public void onClickPositionIntegrante(int pos) {
        if (getView().findViewById(R.id.vista_detalle_grupo_inv) == null) {
            listenerInvestigador1.onIntegranteSeleccionado(investigadores.get(pos));
        } else {
            DetalleDeInvestigadorFragment fragmento = new DetalleDeInvestigadorFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("investigador", investigadores.get(pos));
            bundle.putString("tipo", adminActivity.SOLICITUDES_NUEVAS);
            fragmento.setArguments(bundle);
            dibujarDetalle(fragmento);
        }
    }

    /**
     * Interfaz que se encarga de manejar el evento creado al seleccionar un investigador
     */
    public interface OnIntegranteSeleccionadoListener {
        void onIntegranteSeleccionado(Investigador investigador);
    }

    /**
     * Interfaz que se encarga de manejar el evento creado al seleccionar un grupo
     */
    public interface OnGrupoSeleccionadoListener {
        void onGrupoSeleccionado(Grupo grupo);
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
                listener = (OnGrupoSeleccionadoListener) activity;
                listenerInvestigador1 = (OnIntegranteSeleccionadoListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " debe implementar la interfaz OnPersonajeSeleccionadoListener");
            }
        }
    }

    /**
     * Método que se ejecuta al eliminar un elemento de la lista de solicitudes
     *
     * @param object objeto a ser eliminado
     */
    public boolean eliminarItem(Object object) {
        boolean catchR = false;
        SolicitudesData.solicitudesNuevas.remove(object);
        llenarListas();
        try {
            if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {
                getFragmentManager().beginTransaction().detach(this).
                        attach(this).commit();
            }
            return catchR;
        } catch (NullPointerException e) {
            catchR = true;
            return catchR;
        }
    }

    /**
     * Método que se ejecuta al postergar un elemento de la lista de solicitudes
     *
     * @param object objeto a ser postergado
     */
    public boolean postergarItem(Object object) {
        SolicitudesData.solicitudesNuevas.remove(object);
        llenarListas();
        SolicitudesData.solicitudesPostergadas.add(object);
        boolean catchR = false;
        try {
            if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {
                getFragmentManager().beginTransaction().detach(this).
                        attach(this).commit();
            }
            return catchR;
        } catch (NullPointerException e) {
            catchR = true;
            return catchR;
        }
    }

    /**
     * Método que se ejecuta al aceptar un elemento de la lista de solicitudes
     *
     * @param object objeto a ser aceptar
     */
    public boolean aceptarItem(Object object) {
        SolicitudesData.solicitudesNuevas.remove(object);
        llenarListas();
        boolean catchR = false;
        try {
            if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {
                getFragmentManager().beginTransaction().detach(this).
                        attach(this).commit();
            }
            return catchR;
        } catch (NullPointerException e) {
            catchR = true;
            return catchR;
        }
    }

    /**
     * Método que se encarga de llenar nuevamente las listas al momento realizr un cambio
     */
    public void llenarListas() {
        grupos = new ArrayList<>();
        investigadores = new ArrayList<>();
        ArrayList<Object> solicitudesNuevas = SolicitudesData.solicitudesNuevas;
        for (int i = 0; i < solicitudesNuevas.size(); i++) {
            if (solicitudesNuevas.get(i) instanceof Grupo) {
                grupos.add((Grupo) solicitudesNuevas.get(i));
            }
            if (solicitudesNuevas.get(i) instanceof Investigador) {
                investigadores.add((Investigador) solicitudesNuevas.get(i));
            }
        }
    }

}
