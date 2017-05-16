package co.edu.uniquindio.android.electiva.proyecto_app2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.edu.uniquindio.android.electiva.proyecto_app2.R;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.AvisoConexionFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.RegistroFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.IngresoFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.interfaz.IManagerFirebase;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.ManagerFireBaseAdmin;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.SolicitudesData;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;

/**
 * Actividad principal de la aplicación que se encarga de llamar los fragmentos ingresarFragment y registroFragment
 * y a la actividad AdminActivity según la opción seleccionada por el usuario.
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements IngresoFragment.OnButtonListener,
        RegistroFragment.OnButtonRegistrarListener, IManagerFirebase {

    SolicitudesData sd = new SolicitudesData();
    public static final String ADMINISTRADOR = "administrador";
    public static final String LISTA_ADMINISTRADORES = "administradores";

    @Nullable
    @BindView(R.id.titulo_activity_main)
    protected TextView tituloActivity;

    private Unbinder unbinder;

    private Fragment fragmentoActual;
    public Boolean fragmentoNuevo;
    private String etiqueta;
    private int titulo_fragmento;
    private ArrayList<Administrador> administradores;
    private ManagerFireBaseAdmin managerFireBase;
    final AvisoConexionFragment aviso = new AvisoConexionFragment();
    private boolean avisoAbierto = false;

    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    /**
     * Método que se ejecuta en la creación de la actividad
     *
     * @param savedInstanceState permite recuperar la configuración almacenada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerFireBase = ManagerFireBaseAdmin.instancia(this);
        unbinder = ButterKnife.bind(this);

        aviso.setCancelable(false);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        fragmentoNuevo = false;
        if (savedInstanceState == null) {
            fragmentoNuevo = true;
            IngresoFragment ingresoFragment = new IngresoFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(LISTA_ADMINISTRADORES, administradores);
            ingresoFragment.setArguments(bundle);
            etiqueta = getResources().getString(R.string.tag_fragment_ingreso);
            dibujarFragmento(ingresoFragment, R.string.titulo_frag_ingreso, etiqueta);
        } else if (savedInstanceState.get("llave").equals("true")) {
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
            finish();
        } else {
            Fragment fp2 = getSupportFragmentManager().getFragment(savedInstanceState, "fragmentGuardado");
            dibujarFragmento(fp2, savedInstanceState.getInt("tituloFragmento"), fp2.getTag());
        }
    }

    /**
     * Método que se encarga de almacenar el fragmento actual de la actividad
     *
     * @param outState Contiene información a almacenar.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        etiqueta = fragmentoActual.getTag();
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                    Configuration.SCREENLAYOUT_SIZE_LARGE) {
                Toast toast = Toast.makeText(getApplicationContext(), "Soy grande", Toast.LENGTH_LONG);
                toast.show();
                outState.putString("llave", "true");
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Soy pequeño", Toast.LENGTH_LONG);
                toast.show();
                outState.putString("llave", "false");
                outState.putInt("tituloFragmento", titulo_fragmento);
                getSupportFragmentManager().putFragment(outState, "fragmentGuardado", fragmentoActual);
            }
        } else {
            outState.putString("llave", "false");
            outState.putInt("tituloFragmento", titulo_fragmento);
            getSupportFragmentManager().putFragment(outState, "fragmentGuardado", fragmentoActual);
        }
    }

    /**
     * Método que se encarga de mostrar el framento en el layout de la actividad.
     *
     * @param fragment    Fragmento a mostrar
     * @param contextName Titulo a mostrar con el fragmento
     * @param tag         Etiquta del fragmento
     */
    public void dibujarFragmento(Fragment fragment, int contextName, String tag) {
        if (!fragmentoNuevo) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, fragment, tag)
                    .commit();
        } else {
            if (navigateToSame(fragment)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView, fragment, tag)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView, fragment, tag).addToBackStack(tag)
                        .commit();
            }
        }
        fragmentoActual = fragment;
        titulo_fragmento = contextName;
        if (tituloActivity != null)
            tituloActivity.setText(titulo_fragmento);
    }

    /**
     * Método que se encarga de volver al fragmento anterior al precionar el boton atras.
     */
    @Override
    public void onBackPressed() {
        fragmentoNuevo = false;
        super.onBackPressed();
        Fragment fragmentAfterBackPress = getCurrentFragment();
        String tag;
        if (fragmentAfterBackPress instanceof IngresoFragment) {
            tag = getResources().getString(R.string.tag_fragment_ingreso);
            dibujarFragmento(new IngresoFragment(), R.string.titulo_frag_ingreso, tag);
        } else if (fragmentAfterBackPress instanceof RegistroFragment) {
            tag = getResources().getString(R.string.tag_fragment_registro);
            dibujarFragmento(new RegistroFragment(), R.string.titulo_frag_registro, tag);
        }
    }

    /**
     * Método que se encarga de buscar el fragmento anterior al actual para usarlo en el onBackPressed
     *
     * @return Fragmento anterior
     */
    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentoActual instanceof IngresoFragment) {
            try {
                String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
                return currentFragment;
            } catch (ArrayIndexOutOfBoundsException e) {
                finish();
                return null;
            }
        } else {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
            return currentFragment;
        }
    }

    /**
     * Método que se ejecuta al precionar el boton Registrarse de ingresarFragment
     */
    @Override
    public void onRegistroSelected() {
        RegistroFragment registroFragment = new RegistroFragment();
        String tag = getResources().getString(R.string.tag_fragment_registro);
        fragmentoNuevo = true;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LISTA_ADMINISTRADORES, administradores);
        registroFragment.setArguments(bundle);
        dibujarFragmento(registroFragment, R.string.titulo_frag_registro, tag);

    }

    /**
     * Método que se ejecuta al precionar el boton ingresar de ingresarFragment
     */
    @Override
    public void onIngresoSelected(boolean existe, Administrador administrador) {
        if (existe) {
            Intent intent = new Intent(this, AdminActivity.class);
            intent.putExtra(ADMINISTRADOR, administrador);
            startActivity(intent);
            finish();
        } else {
            String mensaje = (String) getResources().getText(R.string.mensaje_alerta_sesion);
            mostrarAlerta(mensaje);
        }
    }

    /**
     * Método que se ejecuta al registrar un nuevo administrador.
     */
    @Override
    public void onRegistradoListener(String mensaje, boolean registroCorrecto, Administrador admin) {
        if (registroCorrecto) {
            mostrarAlerta(mensaje);
            String tag = getResources().getString(R.string.tag_fragment_ingreso);
            managerFireBase.insertarAdministrador(admin);
            dibujarFragmento(new IngresoFragment(), R.string.titulo_frag_inicio, tag);

        } else {
            mostrarAlerta(mensaje);
        }
    }

    /**
     * Método que se encarga de mostrar los mensajes de alerta por pantalla
     *
     * @param textoMensaje mensaje de alerta a mostrar
     */
    public void mostrarAlerta(String textoMensaje) {
        Toast toast = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.mensaje,
                (ViewGroup) findViewById(R.id.lytLayout));
        TextView mensaje = (TextView) layout.findViewById(R.id.txtMensaje);
        mensaje.setText(textoMensaje);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    /**
     * Metodo que se encarga de verificar que el fragmento anterior no sea igual al fragmento actual
     *
     * @param fragment fragmento a verificar
     * @return variable booleana con el resultado de la verificación
     */
    private boolean navigateToSame(Fragment fragment) {
        boolean flag = false;
        if (fragmentoActual instanceof IngresoFragment && fragment instanceof IngresoFragment) {
            flag = true;
        } else if (fragmentoActual instanceof RegistroFragment && fragment instanceof RegistroFragment) {
            flag = true;
        }
        return flag;
    }

    /**
     * Método que se ejecuta cuando se destruye la actividad
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /**
     * Método que permite obtener la lista de administradores registrados
     *
     * @return lista de administradores
     */
    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    /**
     * Método que permite modificar la lista de administradores registrados
     *
     * @param administradores lista de administradores modificada
     */
    public void setAdministradores(ArrayList<Administrador> administradores) {
        this.administradores = administradores;
    }

    /**
     * Método que se ejecuta al agregar un objeto a la base de datos
     *
     * @param o Objeto que se va a agregar a la base de datos
     */
    @Override
    public void add(Object o) {
    }

    /**
     * Método que se ejecuta al remover un objeto de la base de datos
     *
     * @param o Objeto que se va a eliminar de la base de datos
     */
    @Override
    public void remove(Object o) {
    }

    /**
     * Clase que se encarga de verificar si existe conexión a internet
     */
    class NetworkChangeReceiver extends BroadcastReceiver {

        /**
         * Método que recibe la notificacion sobre el estado de la red
         *
         * @param context Contexto sobre el que se hace la verificación
         * @param intent  No se utiliza en este caso
         */
        @Override
        public void onReceive(final Context context, final Intent intent) {
            isNetworkAvailable(context);
        }

        /**
         * Método que se encarga de verificar si la conexión es valida
         *
         * @param context contesto sobre el que se realiza la verificación
         * @return true si la conexión es valida, false en caso contrario
         */
        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {

                                if (avisoAbierto) {
                                    new Handler().postDelayed(new Runnable() {
                                        public void run() {
                                            aviso.dismiss();
                                        }
                                    }, 2000);
                                    avisoAbierto = false;
                                }
                                isConnected = true;
                            }
                            return true;
                        }
                    }
                }
            }
            aviso.setStyle(aviso.STYLE_NO_TITLE, R.style.CardView);
            aviso.show(getSupportFragmentManager(), MainActivity.class.getName());
            avisoAbierto = true;
            isConnected = false;
            return false;
        }
    }
}






