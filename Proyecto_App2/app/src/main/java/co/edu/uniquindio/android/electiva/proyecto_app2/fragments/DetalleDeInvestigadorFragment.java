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

import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeGrupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeLinea;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;


/**
 * Fragmento que se encarga de mostrar los datos detallados del investigador que fue seleccionado de la lista de solicitudes
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class DetalleDeInvestigadorFragment extends Fragment implements AdaptadorDeGrupo.OnClickAdaptadorDeGrupo,
        AdaptadorDeLinea.OnClickAdaptadorDeLinea, View.OnClickListener {

    private RecyclerView grupoR;
    private RecyclerView lineasR;
    private TextView txtNombre;
    private TextView txtGenero;
    private TextView txtNacionalidad;
    private TextView txtEmail;
    private TextView txtCategoria;
    private TextView txtFormacion;
    private TextView txtLink;
    private Investigador investigador;
    private TextView msjNoGrupo;
    private TextView msjNoLineaInv;

    private FloatingActionButton btnFAB;

    ArcMenu arcMenuAndroid;
    android.support.design.widget.FloatingActionButton floatingEliminar,
            floatingAceptar, floatingPosponer;
    Boolean click = false;

    String tipo;

    /**
     * Método constructor del fragmento
     */
    public DetalleDeInvestigadorFragment() {
        // Required empty public constructor
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
        Log.d("tipoIV", tipo);
        investigador = getArguments().getParcelable("investigador");
        View v = inflater.inflate(R.layout.fragment_detalle_investigador, container, false);

        fabButton(v);

        //Oyente de los eventos al realizar click sobre uno de los FAB
        floatingEliminar.setOnClickListener(this);
        floatingAceptar.setOnClickListener(this);
        floatingPosponer.setOnClickListener(this);
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


        txtNombre = (TextView) getView().findViewById(R.id.txtNombre);
        txtNombre.setText(investigador.getNombre() + " " + investigador.getApellido());
        txtGenero = (TextView) getView().findViewById(R.id.txtGenero);
        txtGenero.setText(investigador.getGenero());
        txtCategoria = (TextView) getView().findViewById(R.id.txtCategoria);
        txtCategoria.setText(investigador.getCategoria());
        txtEmail = (TextView) getView().findViewById(R.id.txtEmail);
        txtEmail.setText(investigador.getEmail());
        txtFormacion = (TextView) getView().findViewById(R.id.txtFormacion);
        txtFormacion.setText(investigador.getFormacion());
        txtLink = (TextView) getView().findViewById(R.id.txtLinkcvlac);
        txtLink.setText(investigador.getLink());
        txtNacionalidad = (TextView) getView().findViewById(R.id.txtNacionalidad);
        txtNacionalidad.setText(investigador.getNacionalidad());

        grupoR = (RecyclerView) getView().findViewById(R.id.grupo_inv_info);
        msjNoGrupo = (TextView) getView().findViewById(R.id.mensaje_no_grupos);
        msjNoGrupo.setVisibility(View.INVISIBLE);
        ArrayList<Grupo> grupoInv = new ArrayList<>();
        if (investigador.getGrupo() != null) {
            grupoInv.add(investigador.getGrupo());
            Fragment fragment = this;
            AdaptadorDeGrupo adaptadorDeGrupo = new AdaptadorDeGrupo(grupoInv, this);
            grupoR.setAdapter(adaptadorDeGrupo);
            grupoR.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoGrupo.setVisibility(View.VISIBLE);
        }

        lineasR = (RecyclerView) getView().findViewById(R.id.lista_lineas_investigacion);
        msjNoLineaInv = (TextView) getView().findViewById(R.id.mensaje_no_lineas);
        msjNoLineaInv.setVisibility(View.INVISIBLE);
        if (investigador.getLineasInvestigacion().size() != 0) {
            AdaptadorDeLinea adaptadorDeLinea = new AdaptadorDeLinea(investigador.getLineasInvestigacion(), this);
            lineasR.setAdapter(adaptadorDeLinea);
            lineasR.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoLineaInv.setText(R.string.mensaje_inv_no_linea);
            msjNoLineaInv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Método que se encarga de retornar el investigador
     *
     * @return investigador
     */
    public Investigador getInvestigador() {
        return investigador;
    }

    /**
     * Método que se encarga de cambiar los datos del investigador
     *
     * @param investigador investigador nuevo
     */
    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    /**
     * Método que se ejecuta al presionar un elemento de la lista de la interfaz
     *
     * @param pos posición del elemento de la lista
     */
    @Override
    public void onClickPosition(int pos) {

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
        if (!tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
            floatingPosponer.setVisibility(View.INVISIBLE);
        }
        arcMenuAndroid.setOnClickListener(this);
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
            super.getActivity().onBackPressed();
        }
        if (floatingPosponer.getId() == v.getId()) {
            if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
                String tag = getResources().getString(R.string.tag_fragment_solicitudes);
                ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                        .getSupportFragmentManager().findFragmentByTag(tag);
                listaDeSolicitudesFragment.postergarItem(investigador);
            } else {
                AdminActivity adminActivity = (AdminActivity) getActivity();
                String mensaje = getResources().getString(R.string.mensaje_solicitud_postergada);
                adminActivity.mostrarAlerta(mensaje);
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
        if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
            String tag = getResources().getString(R.string.tag_fragment_solicitudes);
            ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            listaDeSolicitudesFragment.eliminarItem(investigador);

        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            solicitudesPostetgadasFragment.eliminarItem(investigador);
        }
    }

    /**
     * Método que se encarga de aceptar una solicitud de la lista correspondiente
     */
    private void onClickAceptar() {
        if (tipo.equals(AdminActivity.SOLICITUDES_NUEVAS)) {
            String tag = getResources().getString(R.string.tag_fragment_solicitudes);
            ListaDeSolicitudesFragment listaDeSolicitudesFragment = (ListaDeSolicitudesFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            listaDeSolicitudesFragment.aceptarItem(investigador);
        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            solicitudesPostetgadasFragment.aceptarItem(investigador);
        }
    }

    /**
     * Método que se ejecuta al seleccionar un grupo de los que se muestran en la interfaz
     *
     * @param pos posición de la lista sobre la que se ejecuto el evento
     */
    @Override
    public void onClickPositionGrupo(int pos) {
    }

}
