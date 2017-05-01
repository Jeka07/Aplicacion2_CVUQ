package co.edu.uniquindio.android.electiva.proyecto_app2.util;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.android.electiva.proyecto_app2.fragments.RegistroFragment;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Administrador;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Grupo;
import co.edu.uniquindio.android.electiva.proyecto_app2.vo.Investigador;

/**
 * Clase estatica que se encarga de inicializar los arraylist con los datos de los administradores, las nuevas solicitudes y
 * las solicitudes postergadas
 *
 * @author Ricardo Ayala Martinez
 * @author Roy López Cardona
 * @author Jesica Tapasco Vélez
 * @version 1.0
 */
public class SolicitudesData {


    //Variables estaticas que almacenan los datos de las solicitudes
    public static ArrayList<Object> solicitudesNuevas = llenarObjetos();
    public static ArrayList<Object> solicitudesPostergadas = new ArrayList<>();

    //Variable estatica que almacena los datos de los administradores
    public static ArrayList<Administrador> administradores = llenarAdmin();

    /**
     * Método contructor de la clase
     */
    public SolicitudesData() {
    }


    /**
     * Método que se encarga de contiene los datos para el arraylist de solicitudes nuevas
     *
     * @return ArrayList de solicicitudes nuevas
     */
    private static ArrayList<Object> llenarObjetos() {
        ArrayList<Object> solicitudes = new ArrayList();

        List<String> lineasG1 = new ArrayList<>(), lineasG2 = new ArrayList<>(), lineasG3 = new ArrayList<>(), lineasG4 = new ArrayList<String>();
        lineasG1.add("Bases de datos");
        lineasG1.add("Redes de computadoras");
        lineasG1.add("Mineria");

        List<Investigador> invest = new ArrayList<Investigador>();
        Investigador i1 = new Investigador();
        Investigador i2 = new Investigador();
        Investigador i3 = new Investigador();
        Investigador i4 = new Investigador();
        Investigador i5 = new Investigador();

        i1.setNombre("Pepito");
        i1.setApellido("Perez");
        i1.setLink("www.cvlac.com/pepito");
        i1.setCategoria("Categoria 2");
        i1.setLineasInvestigacion((ArrayList) lineasG1);

        i2.setNombre("Pepita");
        i2.setApellido("Lopez");
        i2.setLineasInvestigacion((ArrayList) lineasG1);

        i3.setNombre("Lupita");
        i3.setApellido("Luna");
        i3.setLineasInvestigacion((ArrayList) lineasG1);

        i4.setNombre("Marcus");
        i4.setApellido("Cornelious");
        i4.setLineasInvestigacion((ArrayList) lineasG1);

        i5.setNombre("Serena");
        i5.setApellido("Williams");
        i5.setLineasInvestigacion((ArrayList) lineasG1);

        invest.add(i1);
        invest.add(i2);
        invest.add(i3);
        invest.add(i4);
        invest.add(i5);

        solicitudes.add(i1);
        solicitudes.add(i2);


        lineasG2.add("Bases de datos");
        lineasG2.add("Mineria");
        lineasG2.add("Gestion del conocimiento");

        lineasG4.add("Redes de computadoras");
        lineasG4.add("Ingenieria de software");
        lineasG4.add("Mineria");


        Investigador lider = new Investigador();
        lider.setNombre("Manu");
        lider.setApellido("Lalu");
        lider.setLineasInvestigacion((ArrayList) lineasG2);


        Grupo g1 = new Grupo();
        g1.setCategoria("Categoria C");
        g1.setNombre("Grupo de Investigación en Redes, Información y Distribuciones GRID");
        //g1.setNombre("GEOIDE-G62");
        g1.setEmail("grid@micorreo.com");
        g1.setSigla("GRID");
        g1.setFoto(12300);
        g1.setLider(lider);
        g1.setInvestigadores((ArrayList) invest);
        g1.setLink("www.cvlac.com/grid");
        g1.setLineasInvestigacion(lineasG4);


        Grupo g2 = new Grupo();
        g2.setNombre("GEOIDE-G62");
        g2.setSigla("GEOIDE-G62");
        g2.setCategoria("Categoria D");
        g2.setLink("www.cvlac.com/geoide");
        //g2.setLineasInvestigacion(lineasG2);
        //g2.setLider(lider);
        //g2.setInvestigadores((ArrayList) invest);

        Grupo g3 = new Grupo();
        g3.setNombre("SINFOCI");
        g3.setSigla("SINFOCI");
        g3.setCategoria("Categoria D");
        g3.setLink("www.cvlac.com/SINFOCI");
        g3.setLineasInvestigacion(lineasG2);
        g3.setLider(lider);
        g3.setInvestigadores((ArrayList) invest);

        Grupo g4 = new Grupo();
        g4.setNombre("CIDERA");
        g4.setSigla("CIDERA");
        g4.setCategoria("Categoria D");
        g4.setLink("www.cvlac.com/CIDERA");
        g4.setLineasInvestigacion(lineasG2);
        g4.setLider(lider);
        g4.setInvestigadores((ArrayList) invest);

        solicitudes.add(g1);
        solicitudes.add(g2);
        solicitudes.add(g3);
        solicitudes.add(g4);

        return solicitudes;
    }

    /**
     * Método que se encarga de contiene los datos para el arraylist de administradores
     *
     * @return ArrayList con los datos de los administradores
     */
    private static ArrayList<Administrador> llenarAdmin() {
        RegistroFragment registroFragment = new RegistroFragment();
        ArrayList<Administrador> admins = registroFragment.llenarAdministradores();
        return admins;
    }

}
