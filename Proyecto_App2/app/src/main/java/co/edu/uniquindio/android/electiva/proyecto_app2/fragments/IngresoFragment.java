package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.MainActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.interfaz.IManagerFirebase;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.ManagerFireBaseAdmin;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;

/**
 * Fragmento que se encarga del ingreso de los administradores.
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class IngresoFragment extends Fragment implements View.OnClickListener, IManagerFirebase,
        ManagerFireBaseAdmin.OnCarga{

    @Nullable
    @BindView(R.id.registrarse)
    protected Button btnRegistro;
    @BindView(R.id.ingresar)
    protected Button btnIngresar;

    private OnButtonListener listener;

    @BindView(R.id.ingresar_correo)
    protected EditText txtCorreo;
    @BindView(R.id.ingresar_contrasenia)
    protected EditText txtContrasenia;

    protected Unbinder unbinder;

    private AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);

    private ArrayList<Administrador> administradores = new ArrayList<>();
    private ManagerFireBaseAdmin managerFireBase;

    private ProgressDialog progress;

    /**
     * Constructor vacio para instanciar el fragmento.
     */
    public IngresoFragment() {
    }

    /**
     * Método que se ejecuta en la creación de la vista
     *
     * @param inflater           inflador de la vista
     * @param container          contenedor de la vista
     * @param savedInstanceState permite recuperar la configuración almacenada
     * @return vista inflada para el fragmento
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.fragment_ingreso, container, false);
        unbinder = ButterKnife.bind(this,x);
        managerFireBase = ManagerFireBaseAdmin.instancia(this);

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

        progress = ProgressDialog.show(getContext(),"Cargando","Espere", true);
        managerFireBase.cargarLista();

        Log.v("AdminTam","Tam = "+administradores.size());

        animacionButton();

        if (getView().findViewById(R.id.vista_registro) != null) {
            RegistroFragment registroFragment = new RegistroFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MainActivity.LISTA_ADMINISTRADORES, administradores);
            registroFragment.setArguments(bundle);
            dibujarFragmentoRegistro(registroFragment);
        } else {
            btnRegistro.setOnClickListener(this);
        }
    }

    /**
     * Método que se encarga de mostrar el framento cuando se encuentra en vistas landscape y en equipos grandes.
     *
     * @param fragment fragmento a dibujar
     */
    private void dibujarFragmentoRegistro(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.vista_registro, fragment)
                .commit();
    }

    /**
     * Método que gestiona los eventos de tipo Click sobre las vistas
     *
     * @param v vista sobre la que se genera el evento
     */
    @Override
    public void onClick(View v) {
        if (getView().findViewById(R.id.vista_registro) != null) {
            RegistroFragment registroFragment = new RegistroFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MainActivity.LISTA_ADMINISTRADORES, administradores);
            registroFragment.setArguments(bundle);
            dibujarFragmentoRegistro(registroFragment);
        } else {
            if (v.getId() == btnRegistro.getId()) {
                listener.onRegistroSelected();
            }
        }
    }

    /**
     * Método que se ejecuta al presionar el botón ingresar de la interfaz
     */
    @OnClick(R.id.ingresar)
    protected void onClickIngresar() {
        btnIngresar.startAnimation(animation1);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txtContrasenia.getWindowToken(), 0);

        Administrador admin = new Administrador();
        boolean existe = false;
        try {
            if (!txtContrasenia.getText().toString().equals("") && !txtCorreo.getText().toString().equals("")) {
                for (int i = 0; i < administradores.size(); i++) {
                    if (txtContrasenia.getText().toString().equals(administradores.get(i).getContrasenia())
                            && txtCorreo.getText().toString().equals(administradores.get(i).getCorreo())) {
                        existe = true;
                        admin = administradores.get(i);
                    }
                }
            }
            listener.onIngresoSelected(existe, admin);
        } catch (NullPointerException e) {
            listener.onIngresoSelected(existe, null);
        }
    }

    @Override
    public void add(Object o) {
        administradores.add((Administrador) o);
    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public void onFinalCarga(ArrayList<Administrador> administradores) {
        Log.d("hola","Prueba");
        this.administradores = administradores;
        progress.dismiss();
    }

    /**
     * Interface utilizada para comunicar el evento de generado al pulsar el boton registrar
     */
    public interface OnButtonListener {
        public void onRegistroSelected();

        public void onIngresoSelected(boolean existe, Administrador administrador);
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
                listener = (OnButtonListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " debe implementar la interfaz OnButtonListener");
            }
        }
    }

    /**
     * Método que crea una animación para mostrar al presionar los botones
     */
    private void animacionButton() {
        animation1.setDuration(2);
        animation1.setStartOffset(5);
        animation1.setFillAfter(true);
    }


}
