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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

    @BindView(R.id.grupo_inv_info)
    protected RecyclerView grupoR;
    @BindView(R.id.lista_lineas_investigacion)
    protected RecyclerView lineasR;
    @BindView(R.id.txtNombre)
    protected TextView txtNombre;
    @BindView(R.id.txtGenero)
    protected TextView txtGenero;
    @BindView(R.id.txtNacionalidad)
    protected TextView txtNacionalidad;
    @BindView(R.id.txtCategoria)
    protected TextView txtCategoria;
    @BindView(R.id.txtEmail)
    protected TextView txtEmail;
    @BindView(R.id.txtFormacion)
    protected TextView txtFormacion;
    @BindView(R.id.txtLinkcvlac)
    protected TextView txtLink;
    @BindView(R.id.mensaje_no_grupos)
    protected TextView msjNoGrupo;
    @BindView(R.id.mensaje_no_lineas)
    protected TextView msjNoLineaInv;

    @BindView(R.id.fab)
    protected FloatingActionButton btnFAB;

    @BindView(R.id.arcmenu_menu)
    protected ArcMenu arcMenuAndroid;
    @BindView(R.id.fab_eliminar)
    protected FloatingActionButton floatingEliminar;
    @BindView(R.id.fab_aceptar)
    protected FloatingActionButton floatingAceptar;
    @BindView(R.id.fab_posponer)
    protected FloatingActionButton floatingPosponer;

    protected Unbinder unbinder;

    private Boolean click = false;
    private String tipo;
    private Investigador investigador;

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
        investigador = getArguments().getParcelable("investigador");
        View v = inflater.inflate(R.layout.fragment_detalle_investigador, container, false);
        unbinder = ButterKnife.bind(this, v);
        btnFAB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#144d0b")));
        arcMenuAndroid.setOnClickListener(this);
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

        txtNombre.setText(investigador.getNombre() + " " + investigador.getApellido());
        txtGenero.setText(investigador.getGenero());
        txtCategoria.setText(investigador.getCategoria());
        txtEmail.setText(investigador.getEmail());
        txtFormacion.setText(investigador.getFormacion());
        txtLink.setText(investigador.getLink());
        txtNacionalidad.setText(investigador.getNacionalidad());

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
    protected void onFabClick() {
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
     * Método que se encarga de eliminar una solicitud de la lista correspondiente al presionar el botón fab_eliminar
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
            resultado = listaDeSolicitudesFragment.eliminarItem(investigador);

        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = solicitudesPostetgadasFragment.eliminarItem(investigador);
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
            resultado = listaDeSolicitudesFragment.aceptarItem(investigador);

        } else {
            String tag = getResources().getString(R.string.tag_fragment_postergadas);
            SolicitudesPostetgadasFragment solicitudesPostetgadasFragment = (SolicitudesPostetgadasFragment) getActivity()
                    .getSupportFragmentManager().findFragmentByTag(tag);
            adminAc.mostrarAlerta(mensaje, getContext());
            resultado = solicitudesPostetgadasFragment.aceptarItem(investigador);
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
            resultado = listaDeSolicitudesFragment.postergarItem(investigador);
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
     * Método que se ejecuta al seleccionar un grupo de los que se muestran en la interfaz
     *
     * @param pos posición de la lista sobre la que se ejecuto el evento
     */
    @Override
    public void onClickPositionGrupo(int pos) {
    }

}
