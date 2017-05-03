package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeGrupoPostergados;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeInvestigadorPostergados;
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
public class SolicitudesPostetgadasFragment extends Fragment implements AdaptadorDeGrupoPostergados.OnClickAdaptadorDeGrupo,
        AdaptadorDeInvestigadorPostergados.OnClickAdaptadorDeIntegrante {

    AdaptadorDeGrupoPostergados adaptador;
    RecyclerView listadoGrupos;
    ArrayList<Grupo> grupos;
    private OnGrupoSeleccionadoListenerPost listener;

    AdaptadorDeInvestigadorPostergados adaptadorDeInvestigador;
    RecyclerView listadoInvestigadores;
    ArrayList<Investigador> investigadores;
    private OnIntegranteSeleccionadoListenerPost listenerInvestigador1;
    AdminActivity adminActivity;

    /**
     * Método constructor del fragmento
     */
    public SolicitudesPostetgadasFragment() {
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

        return inflater.inflate(R.layout.fragment_solicitudes, container, false);
    }

    /**
     * Método que se encarga de la inicialización de los elementos del fragmento
     *
     * @param savedInstanceState información enviada para mantener
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adminActivity = (AdminActivity) getActivity();
        String mensajeVacio = "";
        TextView mensaje = (TextView) getView().findViewById(R.id.mensaje_solicitudes_post);
        mensaje.setVisibility(View.INVISIBLE);
        if (grupos.size() != 0 || investigadores.size() != 0) {
            if (grupos.size() != 0) {
                listadoGrupos = (RecyclerView) getView().findViewById(R.id.lista_grupos_1);
                adaptador = new AdaptadorDeGrupoPostergados(grupos, this);
                listadoGrupos.setAdapter(adaptador);
                listadoGrupos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null) {

                    DetalleDeGrupoFragment fragment = new DetalleDeGrupoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("grupo", grupos.get(0));
                    bundle.putString("tipo", AdminActivity.SOLICITUDES_POST);
                    fragment.setArguments(bundle);
                    dibujarDetalle(fragment);
                }
            }
            if (investigadores.size() != 0) {
                listadoInvestigadores = (RecyclerView) getView().findViewById(R.id.lista_investigadores_1);
                adaptadorDeInvestigador = new AdaptadorDeInvestigadorPostergados(investigadores, this);
                listadoInvestigadores.setAdapter(adaptadorDeInvestigador);
                listadoInvestigadores.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                if (getView().findViewById(R.id.vista_detalle_grupo_inv) != null && grupos.size() == 0) {
                    DetalleDeInvestigadorFragment fragmento = new DetalleDeInvestigadorFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("investigador", investigadores.get(0));
                    bundle.putString("tipo", AdminActivity.SOLICITUDES_POST);
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
            bundle.putString("tipo", adminActivity.SOLICITUDES_POST);
            fragment.setArguments(bundle);
            dibujarDetalle(fragment);
        } else {
            listener.onGrupoSeleccionadoPost(grupos.get(pos));
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
            listenerInvestigador1.onIntegranteSeleccionadoPost(investigadores.get(pos));
        } else {
            DetalleDeInvestigadorFragment fragmento = new DetalleDeInvestigadorFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("investigador", investigadores.get(pos));
            bundle.putString("tipo", adminActivity.SOLICITUDES_POST);
            fragmento.setArguments(bundle);
            dibujarDetalle(fragmento);
        }
    }

    /**
     * Interfaz que se encarga de manejar el evento creado al seleccionar un investigador
     */
    public interface OnIntegranteSeleccionadoListenerPost {
        void onIntegranteSeleccionadoPost(Investigador investigador);
    }

    /**
     * Interfaz que se encarga de manejar el evento creado al seleccionar un grupo
     */
    public interface OnGrupoSeleccionadoListenerPost {
        void onGrupoSeleccionadoPost(Grupo grupo);
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
                listener = (OnGrupoSeleccionadoListenerPost) activity;
                listenerInvestigador1 = (OnIntegranteSeleccionadoListenerPost) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " debe implementar la interfaz OnPersonajeSeleccionadoListener");
            }
        }
    }

    /**
     * Método que se encarga de llenar nuevamente las listas al momento realizr un cambio
     */
    public void llenarListas() {
        grupos = new ArrayList<>();
        investigadores = new ArrayList<>();
        ArrayList<Object> solicitudesPostergadas = SolicitudesData.solicitudesPostergadas;
        for (int i = 0; i < solicitudesPostergadas.size(); i++) {
            if (solicitudesPostergadas.get(i) instanceof Grupo) {
                grupos.add((Grupo) solicitudesPostergadas.get(i));
            }
            if (solicitudesPostergadas.get(i) instanceof Investigador) {
                investigadores.add((Investigador) solicitudesPostergadas.get(i));
            }
        }
    }

    /**
     * Método que se ejecuta al eliminar un elemento de la lista de solicitudes
     *
     * @param object objeto a ser eliminado
     */
    public boolean eliminarItem(Object object) {
        SolicitudesData.solicitudesPostergadas.remove(object);
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
     * Método que se ejecuta al aceptar un elemento de la lista de solicitudes
     *
     * @param object objeto a ser aceptado
     */
    public boolean aceptarItem(Object object) {
        SolicitudesData.solicitudesPostergadas.remove(object);
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
}
