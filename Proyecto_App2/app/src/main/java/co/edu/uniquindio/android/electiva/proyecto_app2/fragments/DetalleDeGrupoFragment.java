package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.sa90.materialarcmenu.ArcMenu;

import java.util.ArrayList;

import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeInvestigador;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeLinea;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.SolicitudesData;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;


/**
 * Fragmento que se encarga de mostrar los datos detallados del grupo que fue seleccionado de la lista de solicitudes
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class DetalleDeGrupoFragment extends Fragment implements AdaptadorDeLinea.OnClickAdaptadorDeLinea,
        AdaptadorDeInvestigador.OnClickAdaptadorDeIntegrante, View.OnClickListener {

    protected TextView txtNombre;
    protected TextView txtSigla;
    protected TextView txtEmail;
    protected TextView txtCategoria;
    protected TextView txtLink;
    protected RecyclerView listadoIntegrantes;
    protected RecyclerView listadoLineas;
    protected TextView msjNoLineaGrup;
    protected TextView msjNoInv;
    protected TextView msjNoLider;
    protected Unbinder unbinder;

    Grupo grupo;

    AdaptadorDeInvestigador adaptadorInvestigador;
    Investigador lider_investigador;
    ArrayList<Investigador> investigadores;

    AdaptadorDeLinea adaptador;
    ArrayList<String> lineas;

    ArcMenu arcMenuAndroid;
    FloatingActionButton floatingEliminar,
            floatingAceptar, floatingPosponer;

    private FloatingActionButton btnFAB;
    private Boolean click = false;

    private String tipo;

    /**
     * Método constructor del fragmento
     */
    public DetalleDeGrupoFragment() {
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
        tipo = getArguments().getString("tipo");

        grupo = getArguments().getParcelable("grupo");
        lineas = (ArrayList<String>) grupo.getLineasInvestigacion();

        lider_investigador = grupo.getLider();
        investigadores = (ArrayList<Investigador>) grupo.getInvestigadores();

        View x = inflater.inflate(R.layout.fragment_detalle_grupo, container, false);

        fabButton(x);

        //Oyente de los eventos al realizar click sobre uno de los FAB
        floatingEliminar.setOnClickListener(this);
        floatingAceptar.setOnClickListener(this);
        floatingPosponer.setOnClickListener(this);

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

        txtNombre = (TextView)
                getView().findViewById(R.id.txtNombre);
        txtNombre.setText(grupo.getNombre());
        txtSigla = (TextView) getView().findViewById(R.id.txtSigla);
        txtSigla.setText(grupo.getSigla());
        txtEmail = (TextView) getView().findViewById(R.id.txtEmail);
        txtEmail.setText(grupo.getEmail());
        txtCategoria = (TextView) getView().findViewById(R.id.txtCategoria);
        txtCategoria.setText(grupo.getCategoria());
        txtLink = (TextView) getView().findViewById(R.id.txtLink);
        txtLink.setText(grupo.getLink());

        listadoIntegrantes = (RecyclerView) getView().findViewById(R.id.lista_investigadores);
        msjNoInv = (TextView) getView().findViewById(R.id.mensaje_no_investigadores);
        msjNoInv.setVisibility(View.INVISIBLE);
        if (investigadores.size() != 0) {
            adaptadorInvestigador = new AdaptadorDeInvestigador(investigadores, this);
            listadoIntegrantes.setAdapter(adaptadorInvestigador);
            listadoIntegrantes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoInv.setVisibility(View.VISIBLE);
        }

        listadoIntegrantes = (RecyclerView) getView().findViewById(R.id.lider_investigador);
        msjNoLider = (TextView) getView().findViewById(R.id.mensaje_no_lider);
        msjNoLider.setVisibility(View.INVISIBLE);
        ArrayList<Investigador> lider_list = new ArrayList<>();
        if (lider_investigador != null) {
            lider_list.add(lider_investigador);
            adaptadorInvestigador = new AdaptadorDeInvestigador(lider_list, this);
            listadoIntegrantes.setAdapter(adaptadorInvestigador);
            listadoIntegrantes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoLider.setVisibility(View.VISIBLE);
        }

        msjNoLineaGrup = (TextView) getView().findViewById(R.id.mensaje_no_lineas);
        msjNoLineaGrup.setVisibility(View.INVISIBLE);
        if (grupo.getLineasInvestigacion().size() != 0) {
            listadoLineas = (RecyclerView) getView().findViewById(R.id.lista_lineas_investigacion);
            adaptador = new AdaptadorDeLinea(lineas, this);
            listadoLineas.setAdapter(adaptador);
            listadoLineas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoLineaGrup.setText(R.string.mensaje_grup_no_linea);
            msjNoLineaGrup.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Método que se encarga de inicializar los botones del menu de tipo FloatingActionButton
     *
     * @param x vista de la cual se obtienen los elementos necesarios para inicializar los botones
     */
    private void fabButton(View x) {
        btnFAB = (FloatingActionButton) x.findViewById(R.id.fab);
        btnFAB.setOnClickListener(this);
        btnFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#144d0b")));

        arcMenuAndroid = (ArcMenu) x.findViewById(R.id.arcmenu_menu);
        floatingEliminar = (FloatingActionButton) x.findViewById(R.id.fab_eliminar);
        floatingAceptar = (FloatingActionButton) x.findViewById(R.id.fab_aceptar);
        floatingPosponer = (FloatingActionButton) x.findViewById(R.id.fab_posponer);
        arcMenuAndroid.setOnClickListener(this);
        //Evento generado al precionar el FAB menu
    }

    /**
     * Método que se ejecuta al dar click sobre alguna de las listas de la interfaz
     *
     * @param pos posición de la ista sobre la que se realizó la acción
     */
    @Override
    public void onClickPosition(int pos) {
    }

    /**
     * Método que se ejecuta al presionar alguno de los botones que contiene la interfaz
     *
     * @param v vista sobre la que se ejecuto el evento
     */
    @Override
    public void onClick(View v) {
        if (floatingEliminar.getId() == v.getId()) {
            onClickEliminar();
        }
        if (floatingAceptar.getId() == v.getId()) {
            onClickAceptar();
        }
        if (floatingPosponer.getId() == v.getId()) {
            if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
                String tag = getResources().getString(R.string.tag_fragment_solicitudes);
                boolean resultado = true;
                AdminActivity adminActivity = (AdminActivity) getActivity();
                ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                        .getSupportFragmentManager().findFragmentByTag(tag);
                String mensaje = getResources().getString(R.string.mensaje_solicitud_postergada);
                adminActivity.mostrarAlerta(mensaje, getContext());
                resultado = listaDeSolicitudesFragment.postergarItem(grupo);
                if (resultado) {
                    adminActivity.onBackPressed();
                }
            } else {
                AdminActivity adminActivity = (AdminActivity) getActivity();
                String mensaje = getResources().getString(R.string.mensaje_solicitud_ya_postergada);
                adminActivity.mostrarAlerta(mensaje, getContext());
            }
        }
        if (btnFAB.getId() == v.getId()) {
            click = !click;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Interpolator interpolador = AnimationUtils.loadInterpolator(btnFAB.getContext(),
                        android.R.interpolator.fast_out_slow_in);
                v.animate()
                        .rotation(click ? 135f : 0)
                        .setInterpolator(interpolador)
                        .start();
            }
            if (click == true) {
                arcMenuAndroid.performClick();
            }
            if (click == false) {
                arcMenuAndroid.performClick();
            }
        }
        if (arcMenuAndroid.getId() == v.getId()) {
            arcMenuAndroid.toggleMenu();
        }
    }

    /**
     * Método que se encarga de eliminar una solicitud de la lista correspondiente
     */
    private void onClickEliminar() {
        boolean resultado = true;
        AdminActivity adminAc = (AdminActivity) getActivity();
        String mensaje = getResources().getString(R.string.mensaje_solicitud_eliminada);

        if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
            String tag = getResources().getString(R.string.tag_fragment_solicitudes);
            ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = listaDeSolicitudesFragment.eliminarItem(grupo);

        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = solicitudesPostetgadasFragment.eliminarItem(grupo);
        }
        if (resultado) {
            adminAc.onBackPressed();
        }

    }

    /**
     * Método que se encarga de aceptar una solicitud de la lista correspondiente
     */
    private void onClickAceptar() {
        boolean resultado = true;
        AdminActivity adminAc = (AdminActivity) getActivity();
        String mensaje = getResources().getString(R.string.mensaje_solicitud_aceptada);

        if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
            String tag = getResources().getString(R.string.tag_fragment_solicitudes);
            ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = listaDeSolicitudesFragment.aceptarItem(grupo);

        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = solicitudesPostetgadasFragment.aceptarItem(grupo);
        }
        if (resultado) {
            adminAc.onBackPressed();
        }
    }

    /**
     * Método que se ejecuta al seleccionar un intergrante de los que se muestran en la interfaz
     *
     * @param pos posición de la lista sobre la que se ejecuto el evento
     */
    @Override
    public void onClickPositionIntegrante(int pos) {
    }


}
