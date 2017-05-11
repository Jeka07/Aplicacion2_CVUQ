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
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.sa90.materialarcmenu.ArcMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeInvestigador;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.AdaptadorDeLinea;
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

    @BindView(R.id.txtNombre)
    protected TextView txtNombre;
    @BindView(R.id.txtSigla)
    protected TextView txtSigla;
    @BindView(R.id.txtEmail)
    protected TextView txtEmail;
    @BindView(R.id.txtCategoria)
    protected TextView txtCategoria;
    @BindView(R.id.txtLink)
    protected TextView txtLink;

    @BindView(R.id.lista_investigadores)
    protected RecyclerView listadoIntegrantes;
    @BindView(R.id.lista_lineas_investigacion)
    protected RecyclerView listadoLineas;
    @BindView(R.id.lider_investigador)
    protected RecyclerView liderInvestigadorRecycler;

    @BindView(R.id.mensaje_no_lineas)
    protected TextView msjNoLineaGrup;
    @BindView(R.id.mensaje_no_investigadores)
    protected TextView msjNoInv;
    @BindView(R.id.mensaje_no_lider)
    protected TextView msjNoLider;

    protected Unbinder unbinder;

    private Grupo grupo;

    private AdaptadorDeInvestigador adaptadorInvestigador;
    private Investigador liderInvestigador;
    private ArrayList<Investigador> investigadores;

    private AdaptadorDeLinea adaptador;
    private ArrayList<String> lineas;

    @BindView(R.id.arcmenu_menu)
    protected ArcMenu arcMenuAndroid;
    @BindView(R.id.fab_eliminar)
    protected FloatingActionButton floatingEliminar;
    @BindView(R.id.fab_aceptar)
    protected FloatingActionButton floatingAceptar;
    @BindView(R.id.fab_posponer)
    protected FloatingActionButton floatingPosponer;
    @BindView(R.id.fab)
    protected FloatingActionButton btnFAB;

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

        liderInvestigador = grupo.getLider();
        investigadores = (ArrayList<Investigador>) grupo.getInvestigadores();

        View x = inflater.inflate(R.layout.fragment_detalle_grupo, container, false);
        unbinder = ButterKnife.bind(this, x);
        btnFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#144d0b")));
        arcMenuAndroid.setOnClickListener(this);

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

        txtNombre.setText(grupo.getNombre());
        txtSigla.setText(grupo.getSigla());
        txtEmail.setText(grupo.getEmail());
        txtCategoria.setText(grupo.getCategoria());
        txtLink.setText(grupo.getLink());

        msjNoInv.setVisibility(View.INVISIBLE);
        if (investigadores.size() != 0) {
            adaptadorInvestigador = new AdaptadorDeInvestigador(investigadores, this);
            listadoIntegrantes.setAdapter(adaptadorInvestigador);
            listadoIntegrantes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoInv.setVisibility(View.VISIBLE);
        }

        msjNoLider.setVisibility(View.INVISIBLE);
        ArrayList<Investigador> lider_list = new ArrayList<>();
        if (liderInvestigador != null) {
            lider_list.add(liderInvestigador);
            adaptadorInvestigador = new AdaptadorDeInvestigador(lider_list, this);
            liderInvestigadorRecycler.setAdapter(adaptadorInvestigador);
            liderInvestigadorRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        } else {
            msjNoLider.setVisibility(View.VISIBLE);
        }

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
        arcMenuAndroid.toggleMenu();
    }

    /**
     * Método que se ejecuta al presionar el FloatingActionButton btnFab que contiene la interfaz
     */
    @OnClick(R.id.fab)
    protected void onClickFab() {
        click = !click;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Interpolator interpolador = AnimationUtils.loadInterpolator(btnFAB.getContext(),
                    android.R.interpolator.fast_out_slow_in);
            btnFAB.animate()
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

    /**
     * Método que se encarga de eliminar una solicitud de la lista correspondiente al presionar el fab_eliminar
     */
    @OnClick(R.id.fab_eliminar)
    protected void onClickEliminar() {
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
     * Método que se encarga de aceptar una solicitud de la lista correspondiente al presionar el fab_aceptar
     */
    @OnClick(R.id.fab_aceptar)
    protected void onClickAceptar() {
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
     * Método que se encarga de posponer una solicitud de la lista correspondiente al presionar el fab_posponer
     */
    @OnClick(R.id.fab_posponer)
    protected void onClickPosponer() {
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

    /**
     * Método que se ejecuta al seleccionar un intergrante de los que se muestran en la interfaz
     *
     * @param pos posición de la lista sobre la que se ejecuto el evento
     */
    @Override
    public void onClickPositionIntegrante(int pos) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Bu", "Pase por aquí100");
    }
}
