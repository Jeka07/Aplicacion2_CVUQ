package co.edu.uniquindio.android.electiva.proyecto_app2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.DetalleDeGrupoFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.DetalleDeInvestigadorFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.EditarPerfilFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.InicioFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.ListaDeSolicitudesFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.RegistroFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.SolicitudesPostetgadasFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.interfaz.IManagerFirebase;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.ManagerFireBaseAdmin;
import co.edu.uniquindio.android.electiva.proyecto_app2.util.SolicitudesData;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;

import static co.edu.uniquindio.android.electiva.proyecto_app2.R.id.drawerLayout;

/**
 * Actividad que se encarga de manipular la información a la cual puede acceder un investigador despues de iniciar sesión
 *
 * @author Ricardo Ayala Martínez
 * @author Roy López Cardona
 * @author Jesica Tapasco Velez
 * @version 1.0
 */
public class AdminActivity extends AppCompatActivity implements
        View.OnClickListener,
        ListaDeSolicitudesFragment.OnIntegranteSeleccionadoListener,
        ListaDeSolicitudesFragment.OnGrupoSeleccionadoListener,
        NavigationView.OnNavigationItemSelectedListener,
        EditarPerfilFragment.OnButtonEditListener,
        SolicitudesPostetgadasFragment.OnIntegranteSeleccionadoListenerPost,
        SolicitudesPostetgadasFragment.OnGrupoSeleccionadoListenerPost,
        IManagerFirebase, ManagerFireBaseAdmin.OnCarga {

    @BindView(R.id.drawerLayout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    protected NavigationView mNavigationView;
    @BindView(R.id.toolbar_admin)
    protected Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    protected TextView tituloFragmento;
    private Unbinder unbinder;

    private FragmentManager mFragmentManager;
    private Fragment fragmentoActual;
    private Boolean fragmentRestaurado;
    private String etiqueta;
    private int titulo_fragmento;

    private Administrador administrador;
    private ManagerFireBaseAdmin managerFireBase;

    public static final String SOLICITUDES_NUEVAS = "nuevas";
    public static final String SOLICITUDES_POST = "postergadas";

    final AvisoConexionFragment aviso = new AvisoConexionFragment();
    private boolean avisoAbierto = false;

    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;

    /**
     * Método que se ejecuta en la creación de la actividad actual
     *
     * @param savedInstanceState permite recuperar la configuración almacenada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        administrador = extras.getParcelable(MainActivity.ADMINISTRADOR);

        aviso.setCancelable(false);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        managerFireBase = ManagerFireBaseAdmin.instancia(this);

        unbinder = ButterKnife.bind(this);

        mNavigationView.setNavigationItemSelectedListener(this);

        View view = mNavigationView.getHeaderView(0);
        view.setOnClickListener(this);

        fragmentRestaurado = false;
        mFragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {
            etiqueta = getResources().getString(R.string.tag_fragment_inicio);
            InicioFragment inicioFragment = new InicioFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nombreAdmin", administrador.getNombre());
            inicioFragment.setArguments(bundle);
            dibujarFragmento(inicioFragment, R.string.titulo_frag_inicio, etiqueta);
        } else {
            fragmentRestaurado = true;
            fragmentoActual = getSupportFragmentManager().getFragment(savedInstanceState, "fragmentGuardado");
            dibujarFragmento(fragmentoActual, savedInstanceState.getInt("tituloFragmento"), fragmentoActual.getTag());
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    /**
     * Método que captura los eventos de selección sobre el menú de opciones
     *
     * @param menuItem Elemento que contiene el menu con el item seleccionado
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fragmentRestaurado = false;
        Menu m = mNavigationView.getMenu();

        if (menuItem.getItemId() == R.id.cambiar_idioma) {
            boolean b = !m.findItem(R.id.espanol).isVisible();
            m.findItem(R.id.espanol).setVisible(b);
            m.findItem(R.id.ingles).setVisible(b);
        }
        if (menuItem.getItemId() == R.id.nuevas_solicitudes) {
            etiqueta = getResources().getString(R.string.tag_fragment_solicitudes);
            ListaDeSolicitudesFragment listaDeSolicitudesFragment = new ListaDeSolicitudesFragment();
            dibujarFragmento(listaDeSolicitudesFragment, R.string.titulo_frag_solicutudes, etiqueta);
            mDrawerLayout.closeDrawers();
        }
        if (menuItem.getItemId() == R.id.solicitudes_postergadas) {
            etiqueta = getResources().getString(R.string.tag_fragment_postergadas);
            dibujarFragmento(new SolicitudesPostetgadasFragment(), R.string.titulo_frag_solicitudes_pos, etiqueta);
            mDrawerLayout.closeDrawers();
        }

        if (menuItem.getItemId() == R.id.item_cuenta) {
            boolean b = !m.findItem(R.id.configurar_cuenta).isVisible();
            m.findItem(R.id.configurar_cuenta).setVisible(b);
            m.findItem(R.id.cerrar_sesion).setVisible(b);
        }
        if (menuItem.getItemId() == R.id.configurar_cuenta) {
            etiqueta = getResources().getString(R.string.tag_fragment_editar);
            EditarPerfilFragment editarPerfilFragment = new EditarPerfilFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("admin", administrador);
            editarPerfilFragment.setArguments(bundle);
            Log.d("ID3", " " + administrador.getId());
            dibujarFragmento(editarPerfilFragment, R.string.titulo_frag_editar, etiqueta);
            mDrawerLayout.closeDrawers();
        }
        if (menuItem.getItemId() == R.id.espanol) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        }

        if (menuItem.getItemId() == R.id.ingles) {
            FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
            mDrawerLayout.closeDrawers();
        }
        if (menuItem.getItemId() == R.id.cerrar_sesion) {
            Log.d("Cerrar", "Me cerraron");
            Intent resultado = new Intent(this, MainActivity.class);

            startActivity(resultado);
            mDrawerLayout.closeDrawers();
            finish();
            Fragment f = null;
        }
        return true;
    }


    /**
     * Método que se encarga de almacenar la información de la actividad para recuperarla prosteriormente.
     *
     * @param outState Variable que almacenara informacion a persistir.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        etiqueta = fragmentoActual.getTag();
        outState.putInt("tituloFragmento", titulo_fragmento);
        getSupportFragmentManager().putFragment(outState, "fragmentGuardado", fragmentoActual);

    }


    /**
     * Método que contiene los eventos generados al presionar ciertos elementos
     *
     * @param v Vista sobre la que se realizo la acción
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.navigation_header) {
            String tag = getResources().getString(R.string.tag_fragment_inicio);
            InicioFragment inicioFragment = new InicioFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nombreAdmin", administrador.getNombre());
            inicioFragment.setArguments(bundle);
            dibujarFragmento(inicioFragment, R.string.titulo_frag_inicio, etiqueta);
            mDrawerLayout.closeDrawers();
        }
    }

    /**
     * Método que se ejecuta al presionar un investigador de la lista
     *
     * @param investigador investigador seleccionado
     */
    @Override
    public void onIntegranteSeleccionado(Investigador investigador) {
        llamarDetalleIntegrante(investigador, SOLICITUDES_NUEVAS);
    }

    /**
     * Método que se encarga de crear una nueva instancia del fragmento DetalleDeIntegranteFragment
     * y pasarle los atributos necesarior antes de dibujarlo en el layout de la actividad
     *
     * @param investigador investigador sobre el cual se ejecuto el evento OnClick
     * @param nombreList   nombre de la lista de solicitudes donde se encontraba el investigador
     */
    private void llamarDetalleIntegrante(Investigador investigador, String nombreList) {
        DetalleDeInvestigadorFragment fragmento = new DetalleDeInvestigadorFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("investigador", investigador);
        bundle.putString("tipo", nombreList);
        fragmento.setArguments(bundle);
        fragmentRestaurado = false;
        etiqueta = getResources().getString(R.string.tag_fragment_detalle_inv);
        dibujarFragmento(fragmento, R.string.titulo_frag_perfil_investigador, etiqueta);
    }

    /**
     * Método que se ejecuta al presionar un grupo de la lista
     *
     * @param grupo grupo seleccionado
     */
    @Override
    public void onGrupoSeleccionado(Grupo grupo) {
        llamarDetalleGrupo(grupo, SOLICITUDES_NUEVAS);
    }

    /**
     * Método que se encarga de crear una nueva instancia del fragmento DetalleDeGrupoFragment
     * y pasarle los atributos necesarior antes de dibujarlo en el layout de la actividad
     *
     * @param grupo      grupo sobre el cual se ejecuto el evento OnClick
     * @param nombreList nombre de la lista de solicitudes donde se encontraba el grupo
     */
    private void llamarDetalleGrupo(Grupo grupo, String nombreList) {
        DetalleDeGrupoFragment fragment = new DetalleDeGrupoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("grupo", grupo);
        bundle.putString("tipo", nombreList);
        fragment.setArguments(bundle);
        fragmentRestaurado = false;
        etiqueta = getResources().getString(R.string.tag_fragment_detalle_grupo);
        dibujarFragmento(fragment, R.string.titulo_frag_perfil_grupo, etiqueta);
    }

    /**
     * Método que se encarga de mostrar el framento en el layout de la actividad.
     *
     * @param fragment    Fragmento a mostrar
     * @param contextName Titulo a mostrar con el fragmento
     * @param tag         Etiquta del fragmento
     */
    public void dibujarFragmento(Fragment fragment, int contextName, String tag) {
        if (fragmentRestaurado) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView_prueba, fragment, tag)
                    .commit();
        } else {
            if (navigateToSame(fragment)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView_prueba, fragment, tag)
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerView_prueba, fragment, tag).addToBackStack(tag)
                        .commit();
            }
        }
        fragmentoActual = fragment;
        titulo_fragmento = contextName;
        tituloFragmento.setText(titulo_fragmento);
        etiqueta = tag;
    }

    /**
     * Método que se encarga de volver al fragmento anterior al precionar el boton atras.
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            fragmentRestaurado = true;
            super.onBackPressed();

            Fragment fragmentAfterBackPress = getCurrentFragment();
            String tag;
            if (fragmentAfterBackPress instanceof InicioFragment) {
                tag = getResources().getString(R.string.tag_fragment_inicio);
                dibujarFragmento(fragmentAfterBackPress, R.string.titulo_frag_inicio, tag);
            } else if (fragmentAfterBackPress instanceof ListaDeSolicitudesFragment) {
                tag = getResources().getString(R.string.tag_fragment_solicitudes);
                dibujarFragmento(fragmentAfterBackPress, R.string.titulo_frag_solicutudes, tag);
            } else if (fragmentAfterBackPress instanceof EditarPerfilFragment) {
                tag = getResources().getString(R.string.tag_fragment_editar);
                dibujarFragmento(fragmentAfterBackPress, R.string.titulo_frag_editar, tag);
            } else if (fragmentAfterBackPress instanceof DetalleDeGrupoFragment) {
                tag = getResources().getString(R.string.tag_fragment_detalle_grupo);
                dibujarFragmento(fragmentAfterBackPress, R.string.titulo_frag_perfil_grupo, tag);
            } else if (fragmentAfterBackPress instanceof DetalleDeInvestigadorFragment) {
                tag = getResources().getString(R.string.tag_fragment_detalle_inv);
                dibujarFragmento(fragmentAfterBackPress, R.string.titulo_frag_perfil_investigador, tag);
            } else if (fragmentAfterBackPress instanceof SolicitudesPostetgadasFragment) {
                tag = getResources().getString(R.string.tag_fragment_postergadas);
                dibujarFragmento(new SolicitudesPostetgadasFragment(), R.string.titulo_frag_solicitudes_pos, tag);
            }
        }
    }

    /**
     * Método que se encarga de buscar el fragmento anterior al actual para usarlo en el onBackPressed
     *
     * @return Fragmento anterior
     */
    private Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentoActual instanceof InicioFragment) {
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
     * Metodo que se encarga de verificar que el fragmento anterior no sea igual al fragmento actual
     *
     * @param fragment fragmento a verificar
     * @return variable booleana con el resultado de la verificación
     */
    private boolean navigateToSame(Fragment fragment) {
        boolean flag = false;
        if (fragmentoActual instanceof InicioFragment && fragment instanceof InicioFragment) {
            flag = true;
        } else if (fragmentoActual instanceof EditarPerfilFragment && fragment instanceof EditarPerfilFragment) {
            flag = true;
        } else if (fragmentoActual instanceof ListaDeSolicitudesFragment && fragment instanceof ListaDeSolicitudesFragment) {
            flag = true;
        } else if (fragmentoActual instanceof DetalleDeInvestigadorFragment && fragment instanceof DetalleDeInvestigadorFragment) {
            flag = true;
        } else if (fragmentoActual instanceof DetalleDeGrupoFragment && fragment instanceof DetalleDeGrupoFragment) {
            flag = true;
        } else if (fragmentoActual instanceof SolicitudesPostetgadasFragment && fragment instanceof SolicitudesPostetgadasFragment) {
            flag = true;
        }
        return flag;
    }

    /**
     * Método que se ejecuta al actualizar los datos de un administrador.
     *
     * @param admin           administrador sobre el que se hizo la modificación
     * @param edicionCorrecta variable booleana para verificar si el cambio fue correcto
     */
    @Override
    public void onActualizarSelected(Administrador admin, boolean edicionCorrecta, String mensaje) {
        String tag = getResources().getString(R.string.tag_fragment_inicio);
        if (edicionCorrecta) {
            administrador.setNombre(admin.getNombre());
            administrador.setApellido(admin.getApellido());
            administrador.setContrasenia(admin.getContrasenia());

            managerFireBase.writeNewAdmin(admin);

            InicioFragment inicioFragment = new InicioFragment();
            Bundle bundle = new Bundle();
            bundle.putString("nombreAdmin", administrador.getNombre());
            inicioFragment.setArguments(bundle);
            dibujarFragmento(inicioFragment, R.string.titulo_frag_inicio, tag);
        } else {
            mostrarAlerta(mensaje, getApplicationContext());
            EditarPerfilFragment editarPerfilFragment = new EditarPerfilFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("admin", admin);
            editarPerfilFragment.setArguments(bundle);
            dibujarFragmento(editarPerfilFragment, R.string.titulo_frag_editar, etiqueta);
        }
    }

    /**
     * Método que se ejecuta al dar click sobre un investigador
     *
     * @param investigador investigador sobre el cual se ejecuto el evento
     */
    @Override
    public void onIntegranteSeleccionadoPost(Investigador investigador) {
        llamarDetalleIntegrante(investigador, SOLICITUDES_POST);
    }

    /**
     * Método que se ejecuta al dar click sobre un grupo
     *
     * @param grupo Grupo sobre el que se ejecuta el evento
     */
    @Override
    public void onGrupoSeleccionadoPost(Grupo grupo) {
        llamarDetalleGrupo(grupo, SOLICITUDES_POST);
    }

    /**
     * Método que se encarga de mostrar los mensajes de alerta por pantalla
     *
     * @param textoMensaje mensaje de alerta a mostrar
     */
    public void mostrarAlerta(String textoMensaje, Context context) {
        Toast toast = new Toast(context);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.mensaje,
                (ViewGroup) findViewById(R.id.lytLayout));
        TextView mensaje = (TextView) layout.findViewById(R.id.txtMensaje);
        mensaje.setText(textoMensaje);
        toast.setDuration(Toast.LENGTH_SHORT);
        unbinder = ButterKnife.bind(layout);
        toast.setView(layout);
        toast.show();
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
     * Método que se ejecuta al terminar de extraer la información de la base de datos.
     *
     * @param administradores información que se obtiene de la base de datos
     */
    @Override
    public void onFinalCarga(ArrayList<Administrador> administradores) {
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

