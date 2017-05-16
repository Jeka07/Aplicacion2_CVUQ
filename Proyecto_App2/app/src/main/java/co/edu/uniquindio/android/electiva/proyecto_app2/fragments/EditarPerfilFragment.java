package co.edu.uniquindio.android.electiva.proyecto_app2.fragments;


import android.app.Activity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.activity.AdminActivity;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;

/**
 * Fragmento que se encarga de realizar las acciones necesarias para el la edición de los datos del administrador
 * que se encuentra con la sesion abierta.
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class EditarPerfilFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.actualizar_datos)
    protected Button btnActualizar;

    private OnButtonEditListener listenerEdit;
    private AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
    private Administrador administrador;

    @BindView(R.id.edit_nombre)
    EditText txtNombre;
    @BindView(R.id.edit_apellido)
    EditText txtApellido;
    @BindView(R.id.edit_correo)
    EditText txtCorreo;
    @BindView(R.id.contrasenia_nueva)
    EditText txtContraseniaNueva;
    @BindView(R.id.contrasenia_actual)
    EditText txtContraseiaActual;
    @BindView(R.id.edit_confirmar_contrasenia)
    EditText txtConfirmarContrasenia;

    protected Unbinder unbinder;

    private boolean edicionCorrecta;

    /**
     * Método constructor del fragmento
     */
    public EditarPerfilFragment() {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        administrador = getArguments().getParcelable("admin");
        Log.d("ID1"," "+administrador.getId());
        View x = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        unbinder = ButterKnife.bind(this, x);
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
        txtNombre.setText(administrador.getNombre());
        txtApellido.setText(administrador.getApellido());
        txtCorreo.setText(administrador.getCorreo());

        animacionButton();
    }

    /**
     * Método que gestiona los eventos de tipo Click sobre las vistas
     *
     * @param v vista sobre la que se genera el evento
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * Método que gestiona los eventos de tipo Click sobre el botón btnActualizar
     */
    @OnClick(R.id.actualizar_datos)
    protected void onClickActualizar() {
        btnActualizar.startAnimation(animation1);
        Log.d("ID2"," "+administrador.getId());
        String mensaje = (String) getResources().getText(R.string.mensaje_alerta_edicion_correcta);
        String contraseniaNueva = txtContraseniaNueva.getText().toString();
        if (!txtNombre.getText().toString().equals("")) {
            administrador.setNombre(txtNombre.getText().toString());
            edicionCorrecta = true;
        }
        if (!txtApellido.getText().toString().equals("")) {
            administrador.setApellido(txtApellido.getText().toString());
            edicionCorrecta = true;
        }
        if (!txtCorreo.getText().toString().equals("")) {
            administrador.setCorreo(txtCorreo.getText().toString());
            edicionCorrecta = true;
        }
        if (!contraseniaNueva.equals("")) {
            String contraseniaActual = txtContraseiaActual.getText().toString();
            String confirmarCont = txtConfirmarContrasenia.getText().toString();
            if (contraseniaActual.equals(administrador.getContrasenia())
                    && contraseniaNueva.equals(confirmarCont)) {
                administrador.setContrasenia(contraseniaNueva);
                edicionCorrecta = true;
            } else {
                mensaje = (String) getResources().getText(R.string.mensaje_alerta_contrasenias_erroneas);
                edicionCorrecta = false;
            }
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(txtConfirmarContrasenia.getWindowToken(), 0);
        listenerEdit.onActualizarSelected(administrador, edicionCorrecta, mensaje);
    }

    /**
     * Interfaz utilizada para controlar la acción generada al pulsar el botón actualizar
     */
    public interface OnButtonEditListener {
        public void onActualizarSelected(Administrador administrador, boolean edicionCorrecta, String mensaje);
    }

    /**
     * Método que crea una animación para mostrar al presionar los botones
     */
    private void animacionButton() {
        animation1.setDuration(20);
        animation1.setStartOffset(5);
        animation1.setFillAfter(true);
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
                listenerEdit = (OnButtonEditListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " debe implementar la interfaz OnButtonEditListener");
            }
        }
    }
}
