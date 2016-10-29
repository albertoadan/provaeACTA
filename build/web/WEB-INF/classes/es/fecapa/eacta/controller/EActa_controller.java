package es.fecapa.eacta.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.fecapa.eacta.bean.Acta;
import es.fecapa.eacta.bean.DatosActa;
import es.fecapa.eacta.bean.Login;
import es.fecapa.eacta.bean.Preacta;
import es.fecapa.eacta.db.EactaEvent;
import es.fecapa.eacta.db.EactaEventHome;
import es.fecapa.eacta.db.Eacta_vw_partit;
import es.fecapa.eacta.db.Eacta_vw_partitHome;
import es.fecapa.eacta.db.Eacta_vw_usuari;
import es.fecapa.eacta.db.Eacta_vw_usuariHome;
import es.fecapa.eacta.pdf.EActaPDFBuilder;
import es.fecapa.eacta.servlet.LogSupport;
import es.fecapa.eacta.servlet.ObtePdf;
import es.fecapa.eacta.servlet.Parametres;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Version;

@Controller
public class EActa_controller {

    public static String ACTION_EXPORTAR_PDF = "exportar";
    public static String ACTION_GUARDAR = "guardar";
    public static String ACTION_GUARDAR_SALIR = "guardarsalir";
    public static String ACTION_CERRAR = "cerrar";
    public static String ACTION_CERRARSESION = "cerrarsesion";
    public static String ACTION_SALIR = "salir";
    public static String ACTION_FIRMA1_DP = "firma1_delegadopista";
    public static String ACTION_FIRMA1_CL = "firma1_capitanlocal";
    public static String ACTION_FIRMA1_EL = "firma1_entrenadorlocal";
    public static String ACTION_FIRMA1_CV = "firma1_capitanvisit";
    public static String ACTION_FIRMA1_EV = "firma1_entrenadorvisit";
    public static String ACTION_FIRMA2_CL = "firma2_capitanlocal";
    public static String ACTION_FIRMA2_AR = "firma2_arbitro";
    public static String ACTION_FIRMA2_AR2 = "firma2_arbitro2";
    public static String ACTION_FIRMA2_CV = "firma2_capitanvisit";
    public static String ACTION_NUEVO_EVENTO = "nuevoevento";
    public static String ACTION_ELIMINAR_EVENTO = "eliminarevento";
    public static String ACTION_REINICIAR = "reiniciar";
    public static String ACTION_CREARMODIFICAR = "crearmodif";

    public static String ACTION_FIRMAS1 = "firmas1";
    public static String ACTION_FIRMAS2 = "firmas2";
       public static String ACTION_MOSTRAR_EVENTS ="mostrarEvents";

    public static String ACTION_NUEVO_EVENTO_TAZUL = "nuevoeventotazul";
    public static String ACTION_NUEVO_EVENTO_TROJA = "nuevoeventotroja";

    //  private static final Log log = LogFactory.getLog(EActa_controller.class);
    public static String ACTION_TORNAR_LOCAL = "tornarLocal";
    public static String ACTION_TORNAR_VISITANT = "tornarVisitant";
    public static String ACTION_TORNAR_OBSERVACIONS = "tornarObservacions";
    public static String ACTION_TORNAR_LLISTA_EVENTS = "tornarLlistaEvents";
    public static String ACTION_TORNAR_CAPCALERA = "tornarCapcalera";
//    public static String ACTION_MOSTRAR_EVENTS ="mostrarEvents";
//    public final static String CURRENT_VERSION =  Application.class.getPackage().getImplementationVersion();

    @SuppressWarnings("rawtypes")
    @RequestMapping("/login")
    public ModelAndView login(@ModelAttribute Login login, Model m) throws Exception {
        //PROCESO DE LOGIN
        String message = "";
        ModelAndView retorno = null;
        //BUSCAMOS EL USUARIO LOGADO EN LA TABLA DE USUARIOS
        Eacta_vw_usuariHome usuarioDAO = new Eacta_vw_usuariHome();
        Eacta_vw_usuari usuario = null;
        List usuario2 = usuarioDAO.findByProperty("user", login.getUsername());
        if (usuario2.size() > 0) {
            usuario = (Eacta_vw_usuari) usuario2.get(0);
        }

        if (usuario != null) {
            //EL USUARIO EXISTE
            if (login.getPassword().equals(usuario.getPassword())) {
                //USUARIO CORRECTO

                message = "Benvingut usuari " + login.getUsername();
                login.setMessage(message);
                 LogSupport.info("Login usuari : " + login.getUsername());
                retorno = new ModelAndView("menu", "login", login);

            } else {
                //PASSWORD INCORRECTO
                message = "Password incorrecte";
                retorno = new ModelAndView("login", "message", message);
            }
        } else {
            //EL USUARIO NO EXISTE
            message = "L' usuari no es troba a la base de dades";
            retorno = new ModelAndView("login", "message", message);
        }
        return retorno;
    }
    
        @RequestMapping("/preacta")
    public ModelAndView preacta(@ModelAttribute Preacta preacta, Model m) throws Exception {
        //PREVIO A LA CREACION DEL ACTA
        String message = "";
        ModelAndView retorno = null;
        //   log.debug("debug  preacta ");
        //Cerrar sesion
        if (preacta.getAccion().equalsIgnoreCase(ACTION_CERRARSESION)) {
            preacta.setCodpartit(0);
            message = "";
            retorno = new ModelAndView("login", "message", message);
        } else {
            //BUSCAMOS EL PARTIDO INTRODUCIDO POR EL USUARIO
            Eacta_vw_partitHome partitDAO = new Eacta_vw_partitHome();
            Eacta_vw_partit partit = partitDAO.findById(preacta.getCodpartit());

            if (partit != null) {
                int x = comprovaTemporada(preacta.getCodpartit());
                if (x == 0) {
                    LogSupport.info(" Usuari :"+preacta.getUsername() + " introdueix un codi de partit dÂ´altre temporada" );
                    message = "Aquest partit no Ã©s dÂ´aquesta temporada";
                    retorno = new ModelAndView("menu", "message", message);
                } else {
                    //BUSCAMOS LOS DATOS Y JUGADORES ASOCIADOS AL PARTIDO PARA CREAR EL ACTA
                    ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
                    listadatosacta = recuperarDatosActa(preacta.getCodpartit());
                    Acta acta = recuperarActa(preacta.getCodpartit(), partit.getCodtecnic(), partit.getCodtecnic2());
                    acta.setSeccion("");

                    if (acta != null) {
                        //Verificar si el usuario puede crear o modificar el acta
                        if (usuarioTienePermisos(acta.getConfig(), partit.getCodtecnic(), preacta.getUsername(), partit.getCodtecnic2())) {

                            if (preacta.getAccion().equalsIgnoreCase(ACTION_REINICIAR)) {  	//REINICIAR ACTA solo si abierta
                                if (acta.getEstado().equals("A")) {
                                    //Eliminamos todos los eventos del acta
                                    eliminarActa(preacta.getCodpartit());

                                    ObtePdf ob = new ObtePdf();
                                    ob.eliminarPdf(preacta.getCodpartit());
                                    ob.deletePdf(preacta.getCodpartit());

                                    //Volvemos a generarla
                                    listadatosacta = recuperarDatosActa(preacta.getCodpartit());
                                    acta = recuperarActa(preacta.getCodpartit(), partit.getCodtecnic(), partit.getCodtecnic2());
                                    acta.setSeccion("");
                                } else {
                                    message = "L'acta del partit " + preacta.getCodpartit() + " no es pot reiniciar perque ha sigut tancada.";
                                    LogSupport.info("usuari : " + preacta.getUsername() + " intenta reiniciar acta tancada " +preacta.getCodpartit() );
                                    Login login = new Login();
                                    login.setUsername(preacta.getUsername());
                                    login.setMessage(message);
                                    retorno = new ModelAndView("menu", "login", login);
                                    return retorno;
                                }
                            }

                            //CREA O MODIFICAR ACTA
                            //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
                            ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
                            ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
                            listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
                            listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
                            //codcate=33 -> Ã rbitres
                            //codcate=37 -> delegat
                            //codcate=56 -> auxiliar
                            //codcate=39 -> tÃ¨cnic ... la resta sÃ³n jugadors i porters 
                            listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
                            listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
                            listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
                            listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
                            listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
                            listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
                            listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
                            listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
                            listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);

                            acta.setDatosactalocal(listajugadoresseleclocal);
                            acta.setDatosactavisit(listajugadoresselecvisit);
                            acta.setDatosactaTlocal(listatecnicoslocal);
                            acta.setDatosactaTvisit(listatecnicosvisit);
                            acta.setDatosactaTfede(listatecnicosfede);
                            acta.setDatosactaDlocal(listadelegadoslocal);
                            acta.setDatosactaDvisit(listadelegadosvisit);
                            acta.setDatosactaDfede(listadelegadosfede);
                            acta.setDatosactaAlocal(listaauxiliareslocal);
                            acta.setDatosactaAvisit(listaauxiliaresvisit);
                            acta.setDatosactaArbitro(listaarbitros);

                            acta.setCodpartit(preacta.getCodpartit());
                            acta.setUsername(preacta.getUsername());

//                        MostraPdf mostra = new MostraPdf();
//                        mostra.obtenirArxiu(175620);
                            ObtePdf ob = new ObtePdf();
                            ob.obtenirArxiu(preacta.getCodpartit());

                            retorno = new ModelAndView("acta", "acta", acta);

                        } else {
                            message = "No tÃ© permisos per accedir a l'acta del partit " + preacta.getCodpartit() + ".";
                            Login login = new Login();
                            login.setUsername(preacta.getUsername());
                            login.setMessage(message);
                            LogSupport.info("usuari : " + preacta.getUsername() + " intenta accedir a acta sense permisos");
                            retorno = new ModelAndView("menu", "login", login);
                        }
                    } else {
                        //System.out.println("ACTA NO GENERADA");
                        message = "No es troben les dades de l'acta del partit " + preacta.getCodpartit() + ". Consulteu amb l'administrador (eacta@fecapa.cat).";
                        Login login = new Login();
                        login.setUsername(preacta.getUsername());
                        login.setMessage(message);
                        LogSupport.info("no es troben les dades de lÂ´acta del partit " + preacta.getCodpartit());
                        retorno = new ModelAndView("menu", "login", login);
                    }
                }

            } else {
                message = "El partit " + preacta.getCodpartit() + " no s'ha trobat a la base de dades";
                Login login = new Login();
                login.setUsername(preacta.getUsername());
                login.setMessage(message);
                LogSupport.info(" partit" + preacta.getCodpartit() + " No es troba a la base de dades");
                retorno = new ModelAndView("menu", "login", login);
            }
        }
        return retorno;
    }
    
    
        private int comprovaTemporada(long codi) {
        int cod = 0;
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from fecapa.partit p,fecapa.competicio c\n"
                    + "where p.codpartit=" + codi + " and\n"
                    + "c.codcomp=p.codcomp and\n"
                    + "c.codtemp=(select codtemp from fecapa.comite where codcomite=1) limit 1;");
            if (rs.next()) {
                cod = rs.getInt(1);
            }
        } catch (Exception e) {            
            LogSupport.grava(e);
        }
        return cod;
    }
    


    private ByteBuffer getAsByteArray(final URLConnection urlConnection) throws IOException {
        final ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
        final InputStream inputStream = urlConnection.getInputStream();
        final byte[] buf = new byte[1024];
        int len;
        while (true) {
            len = inputStream.read(buf);
            if (len == -1) {
                break;
            }
            tmpOut.write(buf, 0, len);
        }
        tmpOut.close();
        return ByteBuffer.wrap(tmpOut.toByteArray(), 0, tmpOut.size());
    }

    protected void gravaPdf(byte[] pdf, Acta acta) throws ServletException, IOException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pdf FROM eacta_event where codpartit=" + acta.getCodpartit() + " and tipoevent=202 ;");
            if (!rs.isBeforeFirst()) {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO eacta_event "
                        + "(codpartit, pdf, tipoevent, event) VALUES(" + acta.getCodpartit() + ", ? , 202, 'grava pdf' ) ;");
                pstmt.setBytes(1, pdf);
                pstmt.executeUpdate();
                con.close();
            } else {
                PreparedStatement pstmt = con.prepareStatement("UPDATE eacta_event SET pdf=? "
                        + " where codpartit=" + acta.getCodpartit() + " and tipoevent=202  ");
                pstmt.setBytes(1, pdf);
                pstmt.executeUpdate();
                con.close();
            }
        } catch (Exception e) {
            LogSupport.grava(e);
        }
    }

    @RequestMapping("/acta")
    public ModelAndView acta(@ModelAttribute Acta acta, Model m) throws Exception {
        //FUNCIONALIDADES DENTRO DEL EACTA
        String destino = "";
        ModelAndView retorno = null;
        //  log.debug("debug  Acta");

        if (acta.getAccion().equalsIgnoreCase(ACTION_EXPORTAR_PDF)) {  	//EXPORTAR PDF 
            acta = GuardarActa(acta, "A");
            acta.setSeccion("");
 
            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";

            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));

            //   log.debug(" es previsualitza acta " );
            retorno = new ModelAndView("acta", "destino", destino);
        } 
                  else if (acta.getAccion().equalsIgnoreCase(ACTION_CERRAR)) {	//CERRAR
            LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode cerrar" );
            //Cambiamos estado y guardamos acta
            acta = GuardarActa(acta, "C");

            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";

            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));

            try {

                URLConnection ur = new URL("http://localhost:8080/pdf/ACTA_" + acta.getCodpartit() + ".pdf").openConnection();

                byte[] b = getAsByteArray(ur).array();

                gravaPdf(b, acta);

                ObtePdf ob = new ObtePdf();
                ob.eliminarPdf(acta.getCodpartit());
                ob.obtenirArxiu(acta.getCodpartit());
            } catch (Exception e) {
                LogSupport.grava(e);
            }

            try {
                // Enviamos resultados
                EnviarResultado(acta);
            } catch (Exception e) {
                LogSupport.grava(e);
            }

            try {
                // Enviamos mails
                SendMail(acta, destino);
            } catch (Exception e) {
                LogSupport.grava(e);
            }

            try {
                Parametres p = new Parametres();
                p.enviaMail(acta.getCodpartit(), destino, true);
            } catch (Exception e) {
                LogSupport.grava(e);
            }

            //    log.debug("es tanca lÂ´acta " + acta.getCodpartit());
            retorno = new ModelAndView("menu", "login", acta);

        }  
          
                      else if (acta.getAccion().equalsIgnoreCase(ACTION_GUARDAR_SALIR)) {	//GUARDAR Y SALIR
                  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode guardar_salir");
            ObtePdf ob = new ObtePdf();
            ob.eliminarPdf(acta.getCodpartit());

            acta = GuardarActa(acta, "A");
            retorno = new ModelAndView("menu", "login", acta);

        } else if (acta.getAccion().equalsIgnoreCase(ACTION_GUARDAR)) {	//GUARDAR  --> Desde eventos del partido
             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode guardar");
            acta = GuardarActa(acta, "A");
            acta.setSeccion("GENEVENTOS");
            retorno = new ModelAndView("acta", "message", "Acta guardada");

        } else if (acta.getAccion().equalsIgnoreCase(ACTION_SALIR)) {	//SALIR
             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode salir");
            ObtePdf ob = new ObtePdf();
            ob.eliminarPdf(acta.getCodpartit());

            retorno = new ModelAndView("menu", "login", acta);
        } 
        
        else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMAS1)) {	//FIRMA ALINEACION DELEGADO
            LogSupport.info("usuari: " + acta.getUsername() + " partit: " + acta.getCodpartit() + " Eacta controller dintre de metode firmas1");

            Parametres p = new Parametres();
            p.preSignatura(acta);

              acta = RecargarEventos(acta);
            acta = obtenirActa(acta, "A");
           // acta = RecargarEventos(acta);
            acta.setSeccion("FIRMA1");
            acta.setMessage("Acta guardada tras firma pre-partido");
            retorno = new ModelAndView("acta", "acta", acta);

        }    else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMAS2)) {	//FIRMA RESULTADO PARTIDO
             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode firmas2");
            Parametres p = new Parametres();
            p.finalSignatura(acta);
            acta = RecargarEventos(acta);
             acta = obtenirActa(acta, "A");
           // acta = RecargarEventos(acta);
            acta.setSeccion("FIRMA2");
            acta.setMessage("Acta guardada tras firma partido");
            
            retorno = new ModelAndView("acta", "acta", acta);

        }
         else if (acta.getAccion().contains("cerrar_") && (acta.getAccion().length() > 5)) {
             LogSupport.info("usuari: "+acta.getUsername()+ " Eacta controller dintre de metode cerrar_mosca");
            String tasques = acta.getAccion();

            boolean comite = false;

            acta = GuardarActa(acta, "C");

            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";

            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));
             LogSupport.info("usuari: "+acta.getUsername()+ " es genera el pdf del partit "+ acta.getCodpartit());
            try {

                URLConnection ur = new URL("http://localhost:8080/pdf/ACTA_" + acta.getCodpartit() + ".pdf").openConnection();

                byte[] b = getAsByteArray(ur).array();

                gravaPdf(b, acta);

                ObtePdf ob = new ObtePdf();
                ob.eliminarPdf(acta.getCodpartit());
                ob.obtenirArxiu(acta.getCodpartit());

            } catch (Exception e) {
                LogSupport.grava(e);
            }

            if (tasques.contains("comite")) {
                comite = true;
                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller mosca indicant enviar comite ");
            }
            if (tasques.contains("locals")) {                 
                Parametres p = new Parametres();
                p.guardaDorsalLocal(acta);
              //  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es guarden els dorsals locals ");
            }
            if (tasques.contains("visitants")) {
                Parametres p = new Parametres();
                p.guardaDorsalvisitant(acta);
               //  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es guarden els dorsals visitants ");
            }
            if (!tasques.contains("finalitzat")) {
                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller mosca indicant partit finalitzat, s´envien resultats");
                try {
                    // Enviamos resultados
                    EnviarResultado(acta);
                } catch (Exception e) {
                       LogSupport.grava(e);
                }
            }
            
            try {
                //Enviamos mails
                SendMail(acta, destino);
            } catch (Exception e) {
               LogSupport.grava(e);
            }
            try {
                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es repasa la llista de correus a enviar l´acta "+ acta.getCodpartit());
                Parametres p = new Parametres();
                p.enviaMail(acta.getCodpartit(), destino, comite);
            } catch (Exception e) {
                    LogSupport.grava(e);
            }
         
            retorno = new ModelAndView("menu", "login", acta);

        } 
        

//        else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_DP) || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_CL)
        //                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_EL) || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_CV)
        //                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_EV)) {	//FIRMA ALINEACION DELEGADO
        //            acta = GuardarActa(acta, "A");
        //            acta.setSeccion("FIRMA1");
        //            acta.setMessage("Acta guardada tras firma pre-partido");
        //            //    log.debug("acta "+ acta.getCodpartit() + "  signada la part de firmes 1");
        //            retorno = new ModelAndView("acta", "acta", acta);
        //
        //        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_CL)
//                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_AR)
//                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_AR2)
//                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_CV)) {	//FIRMA RESULTADO PARTIDO
//            acta = GuardarActa(acta, "A");
//            acta.setSeccion("FIRMA2");
//            acta.setMessage("Acta guardada tras firma partido");
//            //    log.debug("acta "+ acta.getCodpartit() + "  signada la part de firmes 2");
//            retorno = new ModelAndView("acta", "acta", acta);

        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO)) {
             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode nuevo_evento");
            GuardarEvento(acta);

            acta = RecargarEventos(acta);

            retorno = new ModelAndView("acta", "acta", acta);

        } 
        else if (acta.getAccion().equalsIgnoreCase(ACTION_ELIMINAR_EVENTO)) {
             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode eliminar evento");
            EliminarEvento(acta);

            acta = RecargarEventos(acta);
            retorno = new ModelAndView("acta", "acta", acta);
//		} else if(acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_GOL)) {
//			GuardarEventoGol(acta);
//			
//			acta = RecargarEventos(acta);
//			acta.setSeccion("GENEVENTOS");
//			retorno = new ModelAndView("acta", "acta", acta);
        } 
        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_TAZUL)) {
             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode tarja blava");
            GuardarEventoTAzul(acta);

            acta = RecargarEventos(acta);
            acta.setSeccion("GENEVENTOS");
            retorno = new ModelAndView("acta", "acta", acta);
        } 
        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_TROJA)) {
             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode tarja vermella");
            GuardarEventoTRoja(acta);

            acta = RecargarEventos(acta);
            acta.setSeccion("GENEVENTOS");
            retorno = new ModelAndView("acta", "acta", acta);
        }

        else if (acta.getAccion().equalsIgnoreCase(ACTION_MOSTRAR_EVENTS)) {
                    
                    acta = RecargarEventos (acta);
                    retorno = new ModelAndView("acta", "acta", acta);
                }
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_LOCAL)) {
//            if (acta.getCapi_l1() == null) {
//                acta.setCapi_l1("");
//            }
//            if (acta.getCapi_l2() == null) {
//                acta.setCapi_l2("");
//            }
//            if (acta.getCapi_l3() == null) {
//                acta.setCapi_l3("");
//            }
//            if (acta.getCapi_l4() == null) {
//                acta.setCapi_l4("");
//            }
//            if (acta.getCapi_l5() == null) {
//                acta.setCapi_l5("");
//            }
//            if (acta.getCapi_l6() == null) {
//                acta.setCapi_l6("");
//            }
//            if (acta.getCapi_l7() == null) {
//                acta.setCapi_l7("");
//            }
//            if (acta.getCapi_l8() == null) {
//                acta.setCapi_l8("");
//            }
//            if (acta.getCapi_l9() == null) {
//                acta.setCapi_l9("");
//            }
//            if (acta.getCapi_l10() == null) {
//                acta.setCapi_l10("");
//            }
//            EactaEventHome eactaeventDAO = new EactaEventHome();
//            EactaEvent evento;
//            //TODO CARLES Sancionado en atributo7 de los jugadores
//            evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Local", "0", "00:00", "L", acta.getNum_l1(), acta.getCodlic_l1(), acta.getNom_cognoms_l1(),
//                    acta.getCat_l1(), acta.getPin_l1(), acta.getCapi_l1(), acta.getNif_l1(), acta.getSancion_l1(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Local", "0", "00:00", "L", acta.getNum_l2(), acta.getCodlic_l2(), acta.getNom_cognoms_l2(),
//                    acta.getCat_l2(), acta.getPin_l2(), acta.getCapi_l2(), acta.getNif_l2(), acta.getSancion_l2(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Local", "0", "00:00", "L", acta.getNum_l3(), acta.getCodlic_l3(), acta.getNom_cognoms_l3(),
//                    acta.getCat_l3(), acta.getPin_l3(), acta.getCapi_l3(), acta.getNif_l3(), acta.getSancion_l3(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Local", "0", "00:00", "L", acta.getNum_l4(), acta.getCodlic_l4(), acta.getNom_cognoms_l4(),
//                    acta.getCat_l4(), acta.getPin_l4(), acta.getCapi_l4(), acta.getNif_l4(), acta.getSancion_l4(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Local", "0", "00:00", "L", acta.getNum_l5(), acta.getCodlic_l5(), acta.getNom_cognoms_l5(),
//                    acta.getCat_l5(), acta.getPin_l5(), acta.getCapi_l5(), acta.getNif_l5(), acta.getSancion_l5(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Local", "0", "00:00", "L", acta.getNum_l6(), acta.getCodlic_l6(), acta.getNom_cognoms_l6(),
//                    acta.getCat_l6(), acta.getPin_l6(), acta.getCapi_l6(), acta.getNif_l6(), acta.getSancion_l6(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Local", "0", "00:00", "L", acta.getNum_l7(), acta.getCodlic_l7(), acta.getNom_cognoms_l7(),
//                    acta.getCat_l7(), acta.getPin_l7(), acta.getCapi_l7(), acta.getNif_l7(), acta.getSancion_l7(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Local", "0", "00:00", "L", acta.getNum_l8(), acta.getCodlic_l8(), acta.getNom_cognoms_l8(),
//                    acta.getCat_l8(), acta.getPin_l8(), acta.getCapi_l8(), acta.getNif_l8(), acta.getSancion_l8(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Local", "0", "00:00", "L", acta.getNum_l9(), acta.getCodlic_l9(), acta.getNom_cognoms_l9(),
//                    acta.getCat_l9(), acta.getPin_l9(), acta.getCapi_l9(), acta.getNif_l9(), acta.getSancion_l9(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Local", "0", "00:00", "L", acta.getNum_l10(), acta.getCodlic_l10(), acta.getNom_cognoms_l10(),
//                    acta.getCat_l10(), acta.getPin_l10(), acta.getCapi_l10(), acta.getNif_l10(), acta.getSancion_l10(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Local", "0", "00:00", "L", acta.getNum_l11(), acta.getCodlic_l11(), acta.getNom_cognoms_l11(),
//                    acta.getCat_l11(), acta.getPin_l11(), "", acta.getNif_l11(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Local", "0", "00:00", "L", acta.getNum_l12(), acta.getCodlic_l12(), acta.getNom_cognoms_l12(),
//                    acta.getCat_l12(), "", "", acta.getNif_l12(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Local", "0", "00:00", "L", acta.getNum_l13(), acta.getCodlic_l13(), acta.getNom_cognoms_l13(),
//                    acta.getCat_l13(), "", "", acta.getNif_l13(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Local", "0", "00:00", "L", acta.getNum_l14(), acta.getCodlic_l14(), acta.getNom_cognoms_l14(),
//                    acta.getCat_l14(), "", "", acta.getNif_l14(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Local", "0", "00:00", "L", acta.getNum_l15(), acta.getCodlic_l15(), acta.getNom_cognoms_l15(),
//                    acta.getCat_l15(), "", "", acta.getNif_l15(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Local", "0", "00:00", "L", acta.getNum_l16(), acta.getCodlic_l16(), acta.getNom_cognoms_l16(),
//                    acta.getCat_l16(), "", "", acta.getNif_l16(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Local", "0", "00:00", "L", acta.getNum_l17(), acta.getCodlic_l17(), acta.getNom_cognoms_l17(),
//                    acta.getCat_l17(), "", "", acta.getNif_l17(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//
//            acta = obtenirActa(acta, "A");
//            acta = RecargarEventos(acta);
//            acta.setSeccion("");
//            //  log.debug("es guarden els jugadors de l´equip local a la base de ");
//
//            retorno = new ModelAndView("acta", "acta", acta);

        //       } 
//else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_VISITANT)) {
//            if (acta.getCapi_v1() == null) {
//                acta.setCapi_v1("");
//            }
//            if (acta.getCapi_v2() == null) {
//                acta.setCapi_v2("");
//            }
//            if (acta.getCapi_v3() == null) {
//                acta.setCapi_v3("");
//            }
//            if (acta.getCapi_v4() == null) {
//                acta.setCapi_v4("");
//            }
//            if (acta.getCapi_v5() == null) {
//                acta.setCapi_v5("");
//            }
//            if (acta.getCapi_v6() == null) {
//                acta.setCapi_v6("");
//            }
//            if (acta.getCapi_v7() == null) {
//                acta.setCapi_v7("");
//            }
//            if (acta.getCapi_v8() == null) {
//                acta.setCapi_v8("");
//            }
//            if (acta.getCapi_v9() == null) {
//                acta.setCapi_v9("");
//            }
//            if (acta.getCapi_v10() == null) {
//                acta.setCapi_v10("");
//            }
//            EactaEventHome eactaeventDAO = new EactaEventHome();
//            EactaEvent evento;
//            evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Visitant", "0", "00:00", "V", acta.getNum_v1(), acta.getCodlic_v1(), acta.getNom_cognoms_v1(),
//                    acta.getCat_v1(), acta.getPin_v1(), acta.getCapi_v1(), acta.getNif_v1(), acta.getSancion_v1(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Visitant", "0", "00:00", "V", acta.getNum_v2(), acta.getCodlic_v2(), acta.getNom_cognoms_v2(),
//                    acta.getCat_v2(), acta.getPin_v2(), acta.getCapi_v2(), acta.getNif_v2(), acta.getSancion_v2(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Visitant", "0", "00:00", "V", acta.getNum_v3(), acta.getCodlic_v3(), acta.getNom_cognoms_v3(),
//                    acta.getCat_v3(), acta.getPin_v3(), acta.getCapi_v3(), acta.getNif_v3(), acta.getSancion_v3(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Visitant", "0", "00:00", "V", acta.getNum_v4(), acta.getCodlic_v4(), acta.getNom_cognoms_v4(),
//                    acta.getCat_v4(), acta.getPin_v4(), acta.getCapi_v4(), acta.getNif_v4(), acta.getSancion_v4(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Visitant", "0", "00:00", "V", acta.getNum_v5(), acta.getCodlic_v5(), acta.getNom_cognoms_v5(),
//                    acta.getCat_v5(), acta.getPin_v5(), acta.getCapi_v5(), acta.getNif_v5(), acta.getSancion_v5(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Visitant", "0", "00:00", "V", acta.getNum_v6(), acta.getCodlic_v6(), acta.getNom_cognoms_v6(),
//                    acta.getCat_v6(), acta.getPin_v6(), acta.getCapi_v6(), acta.getNif_v6(), acta.getSancion_v6(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Visitant", "0", "00:00", "V", acta.getNum_v7(), acta.getCodlic_v7(), acta.getNom_cognoms_v7(),
//                    acta.getCat_v7(), acta.getPin_v7(), acta.getCapi_v7(), acta.getNif_v7(), acta.getSancion_v7(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Visitant", "0", "00:00", "V", acta.getNum_v8(), acta.getCodlic_v8(), acta.getNom_cognoms_v8(),
//                    acta.getCat_v8(), acta.getPin_v8(), acta.getCapi_v8(), acta.getNif_v8(), acta.getSancion_v8(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Visitant", "0", "00:00", "V", acta.getNum_v9(), acta.getCodlic_v9(), acta.getNom_cognoms_v9(),
//                    acta.getCat_v9(), acta.getPin_v9(), acta.getCapi_v9(), acta.getNif_v9(), acta.getSancion_v9(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Visitant", "0", "00:00", "V", acta.getNum_v10(), acta.getCodlic_v10(), acta.getNom_cognoms_v10(),
//                    acta.getCat_v10(), acta.getPin_v10(), acta.getCapi_v10(), acta.getNif_v10(), acta.getSancion_v10(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Visitant", "0", "00:00", "V", acta.getNum_v11(), acta.getCodlic_v11(), acta.getNom_cognoms_v11(),
//                    acta.getCat_v11(), acta.getPin_v11(), "", acta.getNif_v11(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Visitant", "0", "00:00", "V", acta.getNum_v12(), acta.getCodlic_v12(), acta.getNom_cognoms_v12(),
//                    acta.getCat_v12(), "", "", acta.getNif_v12(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Visitant", "0", "00:00", "V", acta.getNum_v13(), acta.getCodlic_v13(), acta.getNom_cognoms_v13(),
//                    acta.getCat_v13(), "", "", acta.getNif_v13(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Visitant", "0", "00:00", "V", acta.getNum_v14(), acta.getCodlic_v14(), acta.getNom_cognoms_v14(),
//                    acta.getCat_v14(), "", "", acta.getNif_v14(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Visitant", "0", "00:00", "V", acta.getNum_v15(), acta.getCodlic_v15(), acta.getNom_cognoms_v15(),
//                    acta.getCat_v15(), "", "", acta.getNif_v15(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Visitant", "0", "00:00", "V", acta.getNum_v16(), acta.getCodlic_v16(), acta.getNom_cognoms_v16(),
//                    acta.getCat_v16(), "", "", acta.getNif_v16(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Visitant", "0", "00:00", "V", acta.getNum_v17(), acta.getCodlic_v17(), acta.getNom_cognoms_v17(),
//                    acta.getCat_v17(), "", "", acta.getNif_v17(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//
//            acta = obtenirActa(acta, "A");
//            acta = RecargarEventos(acta);
//            acta.setSeccion("");
//
//            //    log.debug("es guarden els jugadors de l´equip visitant a la base de dades");
//            retorno = new ModelAndView("acta", "acta", acta);
//        } 
//else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_OBSERVACIONS)) {
//            EactaEventHome eactaeventDAO = new EactaEventHome();
//            EactaEvent evento;
//            //Observaciones
//            String observaciones = acta.getObservacions();
//            observaciones = observaciones.replace("'", "");
//            observaciones = observaciones.replace("\"", "");
//            evento = new EactaEvent(acta.getCodpartit(), 150, "Observacions Àrbitre", "0", "00:00", "L", 0, observaciones, "", "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//
//            acta = obtenirActa(acta, "A");
//
//            acta.setSeccion("");
//
//            retorno = new ModelAndView("acta", "acta", acta);
//
//        } //        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_LLISTA_EVENTS)) {
        //            EactaEventHome eactaeventDAO = new EactaEventHome();
        //            EactaEvent evento;
        //
        //            //Eventos del partido
        //            //Globales
        //            evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "L", 0, acta.getTotalfaltasl(), "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "V", 0, acta.getTotalfaltasv(), "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        //
        //            //Obtenemos todos los eventos de tipo gol y los eliminamos
        //            evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "", "", "", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.eliminarEactaeventGol(evento);
        //
        //            evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "L", 0, acta.getTotalgolesl(), "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "V", 0, acta.getTotalgolesv(), "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        //
        //            //Generamos los nuevos goles en funcion de lo que hay en el grid
        //            String aux = acta.getCgolespl();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            String[] parte = aux.split(",");
        //            aux = acta.getCgolesml();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            String[] crono = aux.split(",");
        //            aux = acta.getCgolesdl();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            String[] dorsal = aux.split(",");
        //            Long xdorsal;
        //            String xparte;
        //            String xcrono;
        //            if (isNumeric(acta.getTotalgolesl())) {
        //                //Goles existentes	
        //                if (!parte[0].equals("")) {
        //                    for (int i = 0; i < parte.length; i++) {
        //                        if (isNumeric(dorsal[i].trim())) {
        //                            xdorsal = Long.parseLong(dorsal[i].trim());
        //                        } else {
        //                            xdorsal = Long.parseLong("0");
        //                        }
        //                        if (parte[i].equals("")) {
        //                            xparte = "1";
        //                        } else {
        //                            xparte = parte[i].trim();
        //                        }
        //                        if (crono[i].equals("")) {
        //                            xcrono = "00:00";
        //                        } else {
        //                            xcrono = crono[i].trim();
        //                        }
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "L", xdorsal, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                    //Goles añadidos
        //                    for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                } else { //Goles nuevos, no habia
        //                    for (int i = 0; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                }
        //            }
        //            aux = acta.getCgolespv();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            parte = aux.split(",");
        //            aux = acta.getCgolesmv();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            crono = aux.split(",");
        //            aux = acta.getCgolesdv();
        //            aux = aux.replaceAll("\\[", "");
        //            aux = aux.replaceAll("\\]", "");
        //            dorsal = aux.split(",");
        //            if (isNumeric(acta.getTotalgolesv())) {
        //                //Goles existentes	
        //                if (!parte[0].equals("")) {
        //                    for (int i = 0; i < parte.length; i++) {
        //                        if (isNumeric(dorsal[i].trim())) {
        //                            xdorsal = Long.parseLong(dorsal[i].trim());
        //                        } else {
        //                            xdorsal = Long.parseLong("0");
        //                        }
        //                        if (parte[i].equals("")) {
        //                            xparte = "1";
        //                        } else {
        //                            xparte = parte[i].trim();
        //                        }
        //                        if (crono[i].equals("")) {
        //                            xcrono = "00:00";
        //                        } else {
        //                            xcrono = crono[i].trim();
        //                        }
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "V", xdorsal, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                    //Goles añadidos
        //                    for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                } else { //Goles nuevos
        //                    for (int i = 0; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
        //                        eactaeventDAO.crearEactaEvent(evento);
        //                    }
        //                }
        //            }
        //
        //            evento = new EactaEvent(acta.getCodpartit(), 101, "Servei Inicial", "1", "00:00", acta.getSaqueinicial(), 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaevent(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 100, "Hora Inici Real", "1", acta.getHorainicio(), "L", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertol1(), "L", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertol2(), "L", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertov1(), "V", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertov2(), "V", 0, "", "", "", "", "", "", "", "", "");
        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        //            
        //            acta = RecargarEventos(acta);
        //            
        //            acta = obtenirActa(acta, "A");         
        //            
        //            
        //            retorno = new ModelAndView("acta", "acta", acta);
        //
        //        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_CAPCALERA)) {
//            //GUARDAMOS ACTA QUE YA EXISTE A BASE DE ACTUALIZAR LOS EVENTOS
//            //Cabecera
//            EactaEventHome eactaeventDAO = new EactaEventHome();
//            EactaEvent evento = new EactaEvent(acta.getCodpartit(), 23, "Delegat de pista", "0", "00:00", "L", 0, acta.getCodlic_dp(), acta.getNom_cognoms_dp(), "", acta.getPin_dp(), "", acta.getNif_dp(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 22, "Cronometrador", "0", "00:00", "L", 0, acta.getCodlic_cr(), acta.getNom_cognoms_cr(), "", "", "", acta.getNif_cr(), "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 20, "Àrbitre Principal", "0", "00:00", "L", 0, acta.getCodlic_ar1(), acta.getNom_cognoms_ar1(), acta.getCat_ar1(), acta.getPin_ar1(), acta.getEmail_ar1(), acta.getNif_ar1(), acta.getTerritorial_ar1(), acta.getTelefono_ar1(), "");
//            eactaeventDAO.actualizarEactaevent(evento);
//            evento = new EactaEvent(acta.getCodpartit(), 21, "Àrbitre Auxiliar", "0", "00:00", "L", 0, acta.getCodlic_ar2(), acta.getNom_cognoms_ar2(), "", acta.getPin_ar2(), "", acta.getNif_ar2(), acta.getTerritorial_ar2(), "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//
//            acta = RecargarEventos(acta);
//
//            acta = obtenirActa(acta, "A");
//            acta.setSeccion("");
//
//            retorno = new ModelAndView("acta", "acta", acta);
//
//        }
        return retorno;

    }

    public Acta obtenirActa(Acta acta, String estat) {

        //Recuperamos los datos del acta para las selecciones
        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
        listadatosacta = recuperarDatosActa(acta.getCodpartit());

        //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
        ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
        listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
        listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
        //codcate=33 -> àrbitres
        //codcate=37 -> delegat
        //codcate=56 -> auxiliar
        //codcate=39 -> tècnic ... la resta són jugadors i porters 
        listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
        listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
        listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
        listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
        listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
        listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
        listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
        listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
        listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);

        acta.setDatosactalocal(listajugadoresseleclocal);
        acta.setDatosactavisit(listajugadoresselecvisit);
        acta.setDatosactaTlocal(listatecnicoslocal);
        acta.setDatosactaTvisit(listatecnicosvisit);
        acta.setDatosactaTfede(listatecnicosfede);
        acta.setDatosactaDlocal(listadelegadoslocal);
        acta.setDatosactaDvisit(listadelegadosvisit);
        acta.setDatosactaDfede(listadelegadosfede);
        acta.setDatosactaAlocal(listaauxiliareslocal);
        acta.setDatosactaAvisit(listaauxiliaresvisit);
        acta.setDatosactaArbitro(listaarbitros);

        return acta;
    }

    @SuppressWarnings("unchecked")
    public static Acta recuperarActa(long codpartit, long codtecnic, long codtecnic2) {
        //Nos traemos los datos del acta
        EactaEventHome eactaeventDAO = new EactaEventHome();
        List<Object[]> resultados_consulta = eactaeventDAO.eventosPorPartido(codpartit);

        String evento = "";
        Acta acta = new Acta();
        acta.setCodpartit(codpartit);
        long dorsal = (long) 0;
        String atributo1 = "";
        String atributo2 = "";
        String atributo3 = "";
        String atributo4 = "";
        String atributo5 = "";
        String atributo6 = "";
        String atributo7 = "";
        String atributo8 = "";
        String atributo9 = "";

        if (resultados_consulta.size() > 0) {

            for (Object[] object : resultados_consulta) {
                evento = object[3].toString();
                dorsal = 0;
                atributo1 = "";
                atributo2 = "";
                atributo3 = "";
                atributo4 = "";
                atributo5 = "";
                atributo6 = "";
                atributo7 = "";
                atributo8 = "";
                atributo9 = "";
                if (object[7] != null) {
                    dorsal = Long.parseLong(object[7].toString());
                }
                if (object[8] != null) {
                    atributo1 = object[8].toString();
                }
                if (object[9] != null) {
                    atributo2 = object[9].toString();
                }
                if (object[10] != null) {
                    atributo3 = object[10].toString();
                }
                if (object[11] != null) {
                    atributo4 = object[11].toString();
                }
                if (object[12] != null) {
                    atributo5 = object[12].toString();
                }
                if (object[13] != null) {
                    atributo6 = object[13].toString();
                }
                if (object[14] != null) {
                    atributo7 = object[14].toString();
                }
                if (object[15] != null) {
                    atributo8 = object[15].toString();
                }
                if (object[16] != null) {
                    atributo9 = object[16].toString();
                }

                if (evento.equals("Codi de Partit")) {
                    acta.setConfig(atributo2);				//atribut2
                }
                if (evento.equals("Codi Temporada")) {
                    acta.setCodtemp(atributo1); 			//atribut1
                    acta.setTemporada(atributo2); 			//atribut2
                }
                if (evento.equals("Modalitat")) {
                    acta.setModalitat(atributo1); 				//atribut1
                }
                if (evento.equals("Jornada")) {
                    acta.setJornada(Long.parseLong(atributo1)); 	//atribut1
                }
                if (evento.equals("Poblacio de Pista")) {
                    acta.setCodipostaljoc(atributo1); 		//atribut1
                    acta.setPoblaciojoc(atributo2); 		//atribut2
                }
                if (evento.equals("Adressa de Pista")) {
                    acta.setAdressajoc(atributo1); 		//atribut1
                }
                if (evento.equals("Moment de Joc")) {
                    acta.setDatajoc(atributo1); 			//atribut1
                    acta.setHorajoc(atributo2); 			//atribut2
                }
                if (evento.equals("Competició")) {
                    acta.setCompeticio(atributo1); 		//atribut1	
                    acta.setNomcompeticio(atributo2); 	//atribut2
                }
                if (evento.equals("Finalitzacio")) {
                    acta.setFinalitzat(atributo1);	//atribut1
                }
                if (evento.equals("Àrbitre Principal")) {
                    acta.setCodlic_ar1(atributo1);
                    acta.setNom_cognoms_ar1(atributo2);
                    acta.setCat_ar1(atributo3);
                    acta.setPin_ar1(atributo4);
                    acta.setEmail_ar1(atributo5);
                    acta.setNif_ar1(atributo6);
                    acta.setTerritorial_ar1(atributo7);
                    acta.setTelefono_ar1(atributo8);
                    acta.setCodtecnic(codtecnic);			//codtecnic del partit

                }
                if (evento.equals("Àrbitre Auxiliar")) {
                    acta.setCodlic_ar2(atributo1);
                    acta.setNom_cognoms_ar2(atributo2);
                    acta.setPin_ar2(atributo4);
                    acta.setNif_ar2(atributo6);
                    acta.setTerritorial_ar2(atributo7);
                    acta.setCodtecnic2(codtecnic2);
                }
                if (evento.equals("Cronometrador")) {
                    acta.setCodlic_cr(atributo1);
                    acta.setNom_cognoms_cr(atributo2);
                    acta.setNif_cr(atributo6);
                }
                if (evento.equals("Delegat de pista")) {
                    acta.setCodlic_dp(atributo1);
                    acta.setNom_cognoms_dp(atributo2);
                    acta.setPin_dp(atributo4);
                    acta.setNif_dp(atributo6);
                }
                if (evento.equals("Equip Local")) {
                    acta.setCodequiplocal(Long.parseLong(atributo1));				//atribut1
                    acta.setNomequiplocal(atributo2); 								//atribut2
                    acta.setCodentitatlocal(Long.parseLong(atributo3)); 			//atribut3
                    acta.setCodcategorialocal(atributo4); 							//atribut4
                    acta.setCodclasselocal(Long.parseLong(atributo5)); 				//atribut5  
                    String clase = obtenerClase(acta.getCodclasselocal());
                    acta.setClasselocal(clase);
                    acta.setPin_clubl(atributo6);									//atribut6
                    acta.setMailequiplocal(atributo7);
                }
                //Cargamos jugadores de los eventos
                if (evento.equals("Porter1 Local")) {
                    acta.setCodlic_l1(atributo1);
                    acta.setNom_cognoms_l1(atributo2);
                    acta.setCat_l1(atributo3);
                    acta.setPin_l1(atributo4);
                    acta.setCapi_l1(atributo5);
                    acta.setNum_l1(dorsal);
                    acta.setNif_l1(atributo6);
                    acta.setSancion_l1(atributo7);
                }
                if (evento.equals("Jugador1 Local")) {
                    acta.setCodlic_l2(atributo1);
                    acta.setNom_cognoms_l2(atributo2);
                    acta.setCat_l2(atributo3);
                    acta.setPin_l2(atributo4);
                    acta.setCapi_l2(atributo5);
                    acta.setNum_l2(dorsal);
                    acta.setNif_l2(atributo6);
                    acta.setSancion_l2(atributo7);
                }
                if (evento.equals("Jugador2 Local")) {
                    acta.setCodlic_l3(atributo1);
                    acta.setNom_cognoms_l3(atributo2);
                    acta.setCat_l3(atributo3);
                    acta.setPin_l3(atributo4);
                    acta.setCapi_l3(atributo5);
                    acta.setNum_l3(dorsal);
                    acta.setNif_l3(atributo6);
                    acta.setSancion_l3(atributo7);
                }
                if (evento.equals("Jugador3 Local")) {
                    acta.setCodlic_l4(atributo1);
                    acta.setNom_cognoms_l4(atributo2);
                    acta.setCat_l4(atributo3);
                    acta.setPin_l4(atributo4);
                    acta.setCapi_l4(atributo5);
                    acta.setNum_l4(dorsal);
                    acta.setNif_l4(atributo6);
                    acta.setSancion_l4(atributo7);
                }
                if (evento.equals("Jugador4 Local")) {
                    acta.setCodlic_l5(atributo1);
                    acta.setNom_cognoms_l5(atributo2);
                    acta.setCat_l5(atributo3);
                    acta.setPin_l5(atributo4);
                    acta.setCapi_l5(atributo5);
                    acta.setNum_l5(dorsal);
                    acta.setNif_l5(atributo6);
                    acta.setSancion_l5(atributo7);
                }
                if (evento.equals("Jugador5 Local")) {
                    acta.setCodlic_l6(atributo1);
                    acta.setNom_cognoms_l6(atributo2);
                    acta.setCat_l6(atributo3);
                    acta.setPin_l6(atributo4);
                    acta.setCapi_l6(atributo5);
                    acta.setNum_l6(dorsal);
                    acta.setNif_l6(atributo6);
                    acta.setSancion_l6(atributo7);
                }
                if (evento.equals("Jugador6 Local")) {
                    acta.setCodlic_l7(atributo1);
                    acta.setNom_cognoms_l7(atributo2);
                    acta.setCat_l7(atributo3);
                    acta.setPin_l7(atributo4);
                    acta.setCapi_l7(atributo5);
                    acta.setNum_l7(dorsal);
                    acta.setNif_l7(atributo6);
                    acta.setSancion_l7(atributo7);
                }
                if (evento.equals("Jugador7 Local")) {
                    acta.setCodlic_l8(atributo1);
                    acta.setNom_cognoms_l8(atributo2);
                    acta.setCat_l8(atributo3);
                    acta.setPin_l8(atributo4);
                    acta.setCapi_l8(atributo5);
                    acta.setNum_l8(dorsal);
                    acta.setNif_l8(atributo6);
                    acta.setSancion_l8(atributo7);
                }
                if (evento.equals("Jugador8 Local")) {
                    acta.setCodlic_l9(atributo1);
                    acta.setNom_cognoms_l9(atributo2);
                    acta.setCat_l9(atributo3);
                    acta.setPin_l9(atributo4);
                    acta.setCapi_l9(atributo5);
                    acta.setNum_l9(dorsal);
                    acta.setNif_l9(atributo6);
                    acta.setSancion_l9(atributo7);
                }
                if (evento.equals("Porter2 Local")) {
                    acta.setCodlic_l10(atributo1);
                    acta.setNom_cognoms_l10(atributo2);
                    acta.setCat_l10(atributo3);
                    acta.setPin_l10(atributo4);
                    acta.setCapi_l10(atributo5);
                    acta.setNum_l10(dorsal);
                    acta.setNif_l10(atributo6);
                    acta.setSancion_l10(atributo7);
                }
                if (evento.equals("Tecnic Local")) {
                    acta.setCodlic_l11(atributo1);
                    acta.setNom_cognoms_l11(atributo2);
                    acta.setCat_l11(atributo3);
                    acta.setPin_l11(atributo4);
                    acta.setPin_el(atributo4);
                    acta.setNif_l11(atributo6);
                    acta.setNum_l11(dorsal);
                }
                if (evento.equals("Delegat Local")) {
                    acta.setCodlic_l12(atributo1);
                    acta.setNom_cognoms_l12(atributo2);
                    acta.setCat_l12(atributo3);
                    acta.setNif_l12(atributo6);
                    acta.setNum_l12(dorsal);
                }
                if (evento.equals("Auxiliar Local")) {
                    acta.setCodlic_l13(atributo1);
                    acta.setNom_cognoms_l13(atributo2);
                    acta.setCat_l13(atributo3);
                    acta.setNif_l13(atributo6);
                    acta.setNum_l13(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar2 Local")) {
                    acta.setCodlic_l14(atributo1);
                    acta.setNom_cognoms_l14(atributo2);
                    acta.setCat_l14(atributo3);
                    acta.setNif_l14(atributo6);
                    acta.setNum_l14(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar3 Local")) {
                    acta.setCodlic_l15(atributo1);
                    acta.setNom_cognoms_l15(atributo2);
                    acta.setCat_l15(atributo3);
                    acta.setNif_l15(atributo6);
                    acta.setNum_l15(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar4 Local")) {
                    acta.setCodlic_l16(atributo1);
                    acta.setNom_cognoms_l16(atributo2);
                    acta.setCat_l16(atributo3);
                    acta.setNif_l16(atributo6);
                    acta.setNum_l16(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar5 Local")) {
                    acta.setCodlic_l17(atributo1);
                    acta.setNom_cognoms_l17(atributo2);
                    acta.setCat_l17(atributo3);
                    acta.setNif_l17(atributo6);
                    acta.setNum_l17(dorsal);
                }
                if (evento.equals("Equip Visitant")) {
                    acta.setCodequipvisit(Long.parseLong(atributo1));				//atribut1
                    acta.setNomequipvisit(atributo2); 								//atribut2
                    acta.setCodentitatvisit(Long.parseLong(atributo3)); 			//atribut3
                    acta.setCodcategoriavisit(atributo4); 							//atribut4
                    acta.setCodclassevisit(Long.parseLong(atributo5));				//atribut5
                    String clase = obtenerClase(acta.getCodclassevisit());
                    acta.setClassevisit(clase);
                    acta.setPin_clubv(atributo6);									//atribut6
                    acta.setMailequipvisit(atributo7);
                }
                if (evento.equals("Porter1 Visitant")) {
                    acta.setCodlic_v1(atributo1);
                    acta.setNom_cognoms_v1(atributo2);
                    acta.setCat_v1(atributo3);
                    acta.setPin_v1(atributo4);
                    acta.setCapi_v1(atributo5);
                    acta.setNum_v1(dorsal);
                    acta.setNif_v1(atributo6);
                    acta.setSancion_v1(atributo7);
                }
                if (evento.equals("Jugador1 Visitant")) {
                    acta.setCodlic_v2(atributo1);
                    acta.setNom_cognoms_v2(atributo2);
                    acta.setCat_v2(atributo3);
                    acta.setPin_v2(atributo4);
                    acta.setCapi_v2(atributo5);
                    acta.setNum_v2(dorsal);
                    acta.setNif_v2(atributo6);
                    acta.setSancion_v2(atributo7);
                }
                if (evento.equals("Jugador2 Visitant")) {
                    acta.setCodlic_v3(atributo1);
                    acta.setNom_cognoms_v3(atributo2);
                    acta.setCat_v3(atributo3);
                    acta.setPin_v3(atributo4);
                    acta.setCapi_v3(atributo5);
                    acta.setNum_v3(dorsal);
                    acta.setNif_v3(atributo6);
                    acta.setSancion_v3(atributo7);
                }
                if (evento.equals("Jugador3 Visitant")) {
                    acta.setCodlic_v4(atributo1);
                    acta.setNom_cognoms_v4(atributo2);
                    acta.setCat_v4(atributo3);
                    acta.setPin_v4(atributo4);
                    acta.setCapi_v4(atributo5);
                    acta.setNum_v4(dorsal);
                    acta.setNif_v4(atributo6);
                    acta.setSancion_v4(atributo7);
                }
                if (evento.equals("Jugador4 Visitant")) {
                    acta.setCodlic_v5(atributo1);
                    acta.setNom_cognoms_v5(atributo2);
                    acta.setCat_v5(atributo3);
                    acta.setPin_v5(atributo4);
                    acta.setCapi_v5(atributo5);
                    acta.setNum_v5(dorsal);
                    acta.setNif_v5(atributo6);
                    acta.setSancion_v5(atributo7);
                }
                if (evento.equals("Jugador5 Visitant")) {
                    acta.setCodlic_v6(atributo1);
                    acta.setNom_cognoms_v6(atributo2);
                    acta.setCat_v6(atributo3);
                    acta.setPin_v6(atributo4);
                    acta.setCapi_v6(atributo5);
                    acta.setNum_v6(dorsal);
                    acta.setNif_v6(atributo6);
                    acta.setSancion_v6(atributo7);
                }
                if (evento.equals("Jugador6 Visitant")) {
                    acta.setCodlic_v7(atributo1);
                    acta.setNom_cognoms_v7(atributo2);
                    acta.setCat_v7(atributo3);
                    acta.setPin_v7(atributo4);
                    acta.setCapi_v7(atributo5);
                    acta.setNum_v7(dorsal);
                    acta.setNif_v7(atributo6);
                    acta.setSancion_v7(atributo7);
                }
                if (evento.equals("Jugador7 Visitant")) {
                    acta.setCodlic_v8(atributo1);
                    acta.setNom_cognoms_v8(atributo2);
                    acta.setCat_v8(atributo3);
                    acta.setPin_v8(atributo4);
                    acta.setCapi_v8(atributo5);
                    acta.setNum_v8(dorsal);
                    acta.setNif_v8(atributo6);
                    acta.setSancion_v8(atributo7);
                }
                if (evento.equals("Jugador8 Visitant")) {
                    acta.setCodlic_v9(atributo1);
                    acta.setNom_cognoms_v9(atributo2);
                    acta.setCat_v9(atributo3);
                    acta.setPin_v9(atributo4);
                    acta.setCapi_v9(atributo5);
                    acta.setNum_v9(dorsal);
                    acta.setNif_v9(atributo6);
                    acta.setSancion_v9(atributo7);
                }
                if (evento.equals("Porter2 Visitant")) {
                    acta.setCodlic_v10(atributo1);
                    acta.setNom_cognoms_v10(atributo2);
                    acta.setCat_v10(atributo3);
                    acta.setPin_v10(atributo4);
                    acta.setCapi_v10(atributo5);
                    acta.setNum_v10(dorsal);
                    acta.setNif_v10(atributo6);
                    acta.setSancion_v10(atributo7);
                }
                if (evento.equals("Tecnic Visitant")) {
                    acta.setCodlic_v11(atributo1);
                    acta.setNom_cognoms_v11(atributo2);
                    acta.setCat_v11(atributo3);
                    acta.setPin_v11(atributo4);
                    acta.setPin_ev(atributo4);
                    acta.setNif_v11(atributo6);
                    acta.setNum_v11(dorsal);
                }
                if (evento.equals("Delegat Visitant")) {
                    acta.setCodlic_v12(atributo1);
                    acta.setNom_cognoms_v12(atributo2);
                    acta.setCat_v12(atributo3);
                    acta.setNif_v12(atributo6);
                    acta.setNum_v12(dorsal);
                }
                if (evento.equals("Auxiliar Visitant")) {
                    acta.setCodlic_v13(atributo1);
                    acta.setNom_cognoms_v13(atributo2);
                    acta.setCat_v13(atributo3);
                    acta.setNif_v13(atributo6);
                    acta.setNum_v13(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar2 Visitant")) {
                    acta.setCodlic_v14(atributo1);
                    acta.setNom_cognoms_v14(atributo2);
                    acta.setCat_v14(atributo3);
                    acta.setNif_v14(atributo6);
                    acta.setNum_v14(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar3 Visitant")) {
                    acta.setCodlic_v15(atributo1);
                    acta.setNom_cognoms_v15(atributo2);
                    acta.setCat_v15(atributo3);
                    acta.setNif_v15(atributo6);
                    acta.setNum_v15(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar4 Visitant")) {
                    acta.setCodlic_v16(atributo1);
                    acta.setNom_cognoms_v16(atributo2);
                    acta.setCat_v16(atributo3);
                    acta.setNif_v16(atributo6);
                    acta.setNum_v16(dorsal);
                }
                if (evento.equals("Delegat-Auxiliar5 Visitant")) {
                    acta.setCodlic_v17(atributo1);
                    acta.setNom_cognoms_v17(atributo2);
                    acta.setCat_v17(atributo3);
                    acta.setNif_v17(atributo6);
                    acta.setNum_v17(dorsal);
                }
                //TODO CARLES En jugadores atributo4 deberia ir el pin
                if (evento.equals("PreSignatura Delegat Pista")) {
                    acta.setFirma1_dp(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma1_dp_actualizar("1");
                    } else {
                        acta.setFirma1_dp_actualizar("0");
                    }
                }
                if (evento.equals("PreSignatura Capita Local")) {
                    acta.setFirma1_cl(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma1_cl_actualizar("1");
                    } else {
                        acta.setFirma1_cl_actualizar("0");
                    }
                }
                if (evento.equals("PreSignatura Tècnic Local")) {
                    acta.setFirma1_el(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma1_el_actualizar("1");
                    } else {
                        acta.setFirma1_el_actualizar("0");
                    }
                }
                if (evento.equals("PreSignatura Capita Visitant")) {
                    acta.setFirma1_cv(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma1_cv_actualizar("1");
                    } else {
                        acta.setFirma1_cv_actualizar("0");
                    }
                }
                if (evento.equals("PreSignatura Tècnic Visitant")) {
                    acta.setFirma1_ev(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma1_ev_actualizar("1");
                    } else {
                        acta.setFirma1_ev_actualizar("0");
                    }
                }

                if (evento.equals("Observacions Àrbitre")) {
                    acta.setObservacions(atributo1);
                }

                if (evento.equals("Signatura Capita Local")) {
                    acta.setFirma2_cl(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma2_cl_actualizar("1");
                    } else {
                        acta.setFirma2_cl_actualizar("0");
                    }
                }
                if (evento.equals("Signatura Àrbitre1")) {
                    acta.setFirma2_ar(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma2_ar_actualizar("1");
                    } else {
                        acta.setFirma2_ar_actualizar("0");
                    }
                }
                if (evento.equals("Signatura Àrbitre2")) {
                    acta.setFirma2_ar2(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma2_ar2_actualizar("1");
                    } else {
                        acta.setFirma2_ar2_actualizar("0");
                    }
                }
                if (evento.equals("Signatura Capità Visitant")) {
                    acta.setFirma2_cv(atributo4);
                    if (atributo4.equals("")) {
                        acta.setFirma2_cv_actualizar("1");
                    } else {
                        acta.setFirma2_cv_actualizar("0");
                    }
                }
                if (evento.equals("Estat")) {
                    acta.setEstado(atributo1);
                }
            }

            acta = RecargarEventos(acta);
        }
        return acta;
    }

    public void eliminarActa(long codpartit) {
        //Nos traemos los datos del acta
        EactaEventHome eactaeventDAO = new EactaEventHome();
        eactaeventDAO.eliminarEacta(codpartit);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<DatosActa> recuperarDatosActa(long codpartit) {
        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
        DatosActa datosacta = null;
        datosacta = new DatosActa();

        //Traemos los datos de los jugadores/tecnicos/arbitros
        Eacta_vw_partitHome partitDAO = new Eacta_vw_partitHome();
        List<Object[]> resultados_consulta = partitDAO.jugadoresPorPartido(codpartit);
        if (resultados_consulta.size() > 0) {
            for (Object[] object : resultados_consulta) {
                datosacta = new DatosActa();
                //Datos jugadores
                if (object[0] != null) {
                    datosacta.setCodlic(object[0].toString());						//codlic
                }
                if (object[1] != null) {
                    datosacta.setNom(object[1].toString());							//nom
                }
                if (object[2] != null) {
                    datosacta.setSubmodalitat(object[3].toString());					//submodalitat
                }
                if (object[4] != null) {
                    datosacta.setCodenti(Long.parseLong(object[4].toString()));		//codenti
                }
                if (object[5] != null) {
                    datosacta.setCodcate(Long.parseLong(object[5].toString()));		//codcate
                }
                if (object[6] != null) {
                    datosacta.setClasse(Long.parseLong(object[6].toString()));		//classe
                }
                if (object[7] != null) {
                    datosacta.setCodequip(Long.parseLong(object[7].toString()));		//codeequip
                }
                if (object[8] != null) {
                    datosacta.setCodequip2(Long.parseLong(object[8].toString()));	//codequip2
                }
                if (object[9] != null) {
                    datosacta.setCodequip3(Long.parseLong(object[9].toString()));	//codequip3
                }
                if (object[10] != null) {
                    datosacta.setDorsal(object[10].toString());						//dorsal
                }
                if (object[11] != null) {
                    datosacta.setCategoria(object[11].toString());					//eactanomcate					
                }
                if (object[12] != null) {
                    datosacta.setPin(object[12].toString());						//pin
                }
                if (object[13] != null) {
                    datosacta.setNif(object[13].toString());						//nif
                }
                if (object[14] != null) {
                    datosacta.setSancionat(Long.parseLong(object[14].toString()));	//sancionat
                }
                listadatosacta.add(datosacta);
            }
        }

        return listadatosacta;
    }

//	public String abreviarCategoria(long codcate, String tipo) {
//		String resultado = ""; 
//		if (codcate == 1) resultado = "PI"; 	//PB-Iniciació
//		if (codcate == 2) resultado = "PB"; 	//Prebenjamí
//		if (codcate == 3) resultado = "BJ"; 		//Benjamí
//		if (codcate == 4) resultado = "AL"; 		//Aleví
//		if (codcate == 5) resultado = "IN"; 		//Infantil
//		if (codcate == 6) resultado = "JV"; 	//Juvenil
//		if (codcate == 7) resultado = "JN"; 	//Júnior
//		if (codcate == 9) resultado = "2C"; 	//2CAT
//		if (codcate == 10) resultado = "2F"; 	//2CAT FEM
//		if (codcate == 11) resultado = "1C"; 	//1CAT
//		if (codcate == 12) resultado = "1F"; 	//1CAT FEM
//		if (codcate == 13) resultado = "NC"; 	//NCAT
//		if (codcate == 14) resultado = "NF"; 	//NCAT FEM
//		if (codcate == 15) resultado = "VE"; 	//Veterans
//		if (codcate == 18) resultado = "PB"; 	//Prebenjamí
//		if (codcate == 19) resultado = "BJ"; 	//Benjamí
//		if (codcate == 20) resultado = "AL"; 	//Aleví
//		if (codcate == 21) resultado = "IN"; 	//Infantil
//		if (codcate == 22) resultado = "JV"; 	//Juvenil
//		if (codcate == 23) resultado = "JN"; 	//Júnior
//		if (codcate == 27) resultado = "SM"; 	//Sènior Masculí
//		if (codcate == 31) resultado = "VE"; 	//Veterans
//		if (codcate == 34) resultado = "SF"; 	//Senior Femení
//		if (codcate == 35) resultado = "OL"; 	//OK LIGA 
//		if (codcate == 36) resultado = "1D"; 	//1ª DIVISIÓN
//		if (codcate == 38) resultado = "OF"; 	//OK LIGA FEM
//		if (codcate == 40) resultado = "16"; 	//Fem16
//		if (codcate == 0) {
//			if (tipo.equalsIgnoreCase("Arbitre")) resultado = "AR";
//			if (tipo.equalsIgnoreCase("Delegat/da")) resultado = "DL";
//			if (tipo.equalsIgnoreCase("Tècnic")) resultado = "TN";
//			if (tipo.equalsIgnoreCase("Auxiliar")) resultado = "AX";
//		}
//		return resultado;
//	}
    public void GuardarEvento(Acta acta) {
        EactaEventHome eventoDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent();

        long tipoevent = 0;
        if (acta.getNeventotipo().equals("Gol")) {
            tipoevent = 110;
        } else if (acta.getNeventotipo().equals("Servei Inicial")) {
            tipoevent = 101;
        } else if (acta.getNeventotipo().equals("Total Faltes")) {
            tipoevent = 130;
        } else if (acta.getNeventotipo().equals("Directe")) {
            tipoevent = 112;
        } else if (acta.getNeventotipo().equals("Penal")) {
            tipoevent = 111;
        } else if (acta.getNeventotipo().equals("Tarja Blava")) {
            tipoevent = 120;
        } else if (acta.getNeventotipo().equals("Tarja Vermella")) {
            tipoevent = 121;
        } else if (acta.getNeventotipo().equals("Temps Mort")) {
            tipoevent = 102;
        } else if (acta.getNeventotipo().equals("Hora Inici Real")) {
            tipoevent = 100;
        }

        evento.setCodpartit(acta.getCodpartit());
        evento.setTipoevent(tipoevent);
        evento.setEvent(acta.getNeventotipo());
        evento.setPart(acta.getNeventoparte());
        if (!acta.getNeventocrono().equals("")) {
            evento.setCrono(acta.getNeventocrono());
        } else {
            evento.setCrono("00:00");
        }
        evento.setLocalvisitant(acta.getNeventoequipo());
        if (!acta.getNeventodorsal().equals("")) {
            evento.setDorsal(Long.parseLong(acta.getNeventodorsal()));
        }
        evento.setAtribut1(acta.getNeventoatribut());

        eventoDAO.crearEactaEvent(evento);
    }

    public void EliminarEvento(Acta acta) {
        EactaEventHome eventoDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent();

        evento.setCodpartit(acta.getCodpartit());
        evento.setId(Long.parseLong(acta.getDelevento()));
        eventoDAO.eliminarEactaevent(evento);
    }

    public void EliminarGoles(Acta acta, String local, String idevento) {
        EactaEventHome eventoDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent();

        evento.setCodpartit(acta.getCodpartit());
        evento.setId(Long.parseLong(idevento));
        eventoDAO.eliminarEactaevent(evento);
    }

//	public void GuardarEventoGol(Acta acta) { 
//		EactaEventHome eventoDAO = new EactaEventHome();
//		EactaEvent evento = new EactaEvent();
//	
//		long tipoevent = 110;
//	
//		evento.setCodpartit(acta.getCodpartit());
//		evento.setTipoevent(tipoevent);
//		evento.setEvent("Gol");
//		if (acta.getGolesequipo().equals("L")) {
//			evento.setPart(acta.getCgolespl());
//			if(!acta.getCgolesml().equals("")) evento.setCrono(acta.getCgolesml());
//			else evento.setCrono("00:00");
//			evento.setLocalvisitant(acta.getGolesequipo());
//			if (!acta.getCgolesdl().equals("")) evento.setDorsal(Long.parseLong(acta.getCgolesdl()));
//			
//		} else {
//			evento.setPart(acta.getCgolespv());
//			if(!acta.getCgolesmv().equals("")) evento.setCrono(acta.getCgolesmv());
//			else evento.setCrono("00:00");
//			evento.setLocalvisitant(acta.getGolesequipo());
//			if (!acta.getCgolesdv().equals("")) evento.setDorsal(Long.parseLong(acta.getCgolesdv()));
//		}
//		
//		evento.setAtribut1("");
//
//		eventoDAO.crearEactaEvent(evento);
//	}
    public void GuardarEventoTAzul(Acta acta) {
        EactaEventHome eventoDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent();

        long tipoevent = 120;

        evento.setCodpartit(acta.getCodpartit());
        evento.setTipoevent(tipoevent);
        evento.setEvent("Tarja Blava");
        evento.setPart(acta.getCfaltasap());
        if (!acta.getCfaltasam().equals("")) {
            evento.setCrono(acta.getCfaltasam());
        } else {
            evento.setCrono("00:00");
        }
        evento.setLocalvisitant(acta.getFaltasaequipo());
        if (!acta.getCfaltasad().equals("")) {
            evento.setDorsal(Long.parseLong(acta.getCfaltasad()));
        }
        evento.setAtribut1(acta.getCfaltasar());

        eventoDAO.crearEactaEvent(evento);
    }

    public void GuardarEventoTRoja(Acta acta) {
        EactaEventHome eventoDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent();

        long tipoevent = 121;

        evento.setCodpartit(acta.getCodpartit());
        evento.setTipoevent(tipoevent);
        evento.setEvent("Tarja Vermella");
        evento.setPart(acta.getCfaltasrp());
        if (!acta.getCfaltasrm().equals("")) {
            evento.setCrono(acta.getCfaltasrm());
        } else {
            evento.setCrono("00:00");
        }
        evento.setLocalvisitant(acta.getFaltasrequipo());
        if (!acta.getCfaltasrd().equals("")) {
            evento.setDorsal(Long.parseLong(acta.getCfaltasrd()));
        }
        evento.setAtribut1(acta.getCfaltasrr());

        eventoDAO.crearEactaEvent(evento);
    }

    @SuppressWarnings("deprecation")
    public Acta GuardarActa(Acta acta, String estado) {
        // guardar timestamp inicio
        long start = System.currentTimeMillis();

//        EactaEventHome eactaeventDAO = new EactaEventHome();
//        EactaEvent evento;
        //GUARDAMOS ACTA QUE YA EXISTE A BASE DE ACTUALIZAR LOS EVENTOS
        //Cabecera
        EactaEventHome eactaeventDAO = new EactaEventHome();
        EactaEvent evento = new EactaEvent(acta.getCodpartit(), 23, "Delegat de pista", "0", "00:00", "L", 0, acta.getCodlic_dp(), acta.getNom_cognoms_dp(), "", acta.getPin_dp(), "", acta.getNif_dp(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 22, "Cronometrador", "0", "00:00", "L", 0, acta.getCodlic_cr(), acta.getNom_cognoms_cr(), "", "", "", acta.getNif_cr(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 20, "Àrbitre Principal", "0", "00:00", "L", 0, acta.getCodlic_ar1(), acta.getNom_cognoms_ar1(), acta.getCat_ar1(), acta.getPin_ar1(), acta.getEmail_ar1(), acta.getNif_ar1(), acta.getTerritorial_ar1(), acta.getTelefono_ar1(), "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 21, "Àrbitre Auxiliar", "0", "00:00", "L", 0, acta.getCodlic_ar2(), acta.getNom_cognoms_ar2(), "", acta.getPin_ar2(), "", acta.getNif_ar2(), acta.getTerritorial_ar2(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        //Estado
        evento = new EactaEvent(acta.getCodpartit(), 61, "Estat", "0", "00:00", "L", 0, estado, "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);

        //  Observaciones
        String observaciones = acta.getObservacions();
        observaciones = observaciones.replace("'", "");
        observaciones = observaciones.replace("\"", "");
        evento = new EactaEvent(acta.getCodpartit(), 150, "Observacions Àrbitre", "0", "00:00", "L", 0, observaciones, "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
//        ACTUALIZAMOS LOS JUGADORES
//        Locales
        if (acta.getCapi_l1() == null) {
            acta.setCapi_l1("");
        }
        if (acta.getCapi_l2() == null) {
            acta.setCapi_l2("");
        }
        if (acta.getCapi_l3() == null) {
            acta.setCapi_l3("");
        }
        if (acta.getCapi_l4() == null) {
            acta.setCapi_l4("");
        }
        if (acta.getCapi_l5() == null) {
            acta.setCapi_l5("");
        }
        if (acta.getCapi_l6() == null) {
            acta.setCapi_l6("");
        }
        if (acta.getCapi_l7() == null) {
            acta.setCapi_l7("");
        }
        if (acta.getCapi_l8() == null) {
            acta.setCapi_l8("");
        }
        if (acta.getCapi_l9() == null) {
            acta.setCapi_l9("");
        }
        if (acta.getCapi_l10() == null) {
            acta.setCapi_l10("");
        }
        //TODO CARLES Sancionado en atributo7 de los jugadores
        evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Local", "0", "00:00", "L", acta.getNum_l1(), acta.getCodlic_l1(), acta.getNom_cognoms_l1(),
                acta.getCat_l1(), acta.getPin_l1(), acta.getCapi_l1(), acta.getNif_l1(), acta.getSancion_l1(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Local", "0", "00:00", "L", acta.getNum_l2(), acta.getCodlic_l2(), acta.getNom_cognoms_l2(),
                acta.getCat_l2(), acta.getPin_l2(), acta.getCapi_l2(), acta.getNif_l2(), acta.getSancion_l2(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Local", "0", "00:00", "L", acta.getNum_l3(), acta.getCodlic_l3(), acta.getNom_cognoms_l3(),
                acta.getCat_l3(), acta.getPin_l3(), acta.getCapi_l3(), acta.getNif_l3(), acta.getSancion_l3(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Local", "0", "00:00", "L", acta.getNum_l4(), acta.getCodlic_l4(), acta.getNom_cognoms_l4(),
                acta.getCat_l4(), acta.getPin_l4(), acta.getCapi_l4(), acta.getNif_l4(), acta.getSancion_l4(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Local", "0", "00:00", "L", acta.getNum_l5(), acta.getCodlic_l5(), acta.getNom_cognoms_l5(),
                acta.getCat_l5(), acta.getPin_l5(), acta.getCapi_l5(), acta.getNif_l5(), acta.getSancion_l5(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Local", "0", "00:00", "L", acta.getNum_l6(), acta.getCodlic_l6(), acta.getNom_cognoms_l6(),
                acta.getCat_l6(), acta.getPin_l6(), acta.getCapi_l6(), acta.getNif_l6(), acta.getSancion_l6(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Local", "0", "00:00", "L", acta.getNum_l7(), acta.getCodlic_l7(), acta.getNom_cognoms_l7(),
                acta.getCat_l7(), acta.getPin_l7(), acta.getCapi_l7(), acta.getNif_l7(), acta.getSancion_l7(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Local", "0", "00:00", "L", acta.getNum_l8(), acta.getCodlic_l8(), acta.getNom_cognoms_l8(),
                acta.getCat_l8(), acta.getPin_l8(), acta.getCapi_l8(), acta.getNif_l8(), acta.getSancion_l8(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Local", "0", "00:00", "L", acta.getNum_l9(), acta.getCodlic_l9(), acta.getNom_cognoms_l9(),
                acta.getCat_l9(), acta.getPin_l9(), acta.getCapi_l9(), acta.getNif_l9(), acta.getSancion_l9(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Local", "0", "00:00", "L", acta.getNum_l10(), acta.getCodlic_l10(), acta.getNom_cognoms_l10(),
                acta.getCat_l10(), acta.getPin_l10(), acta.getCapi_l10(), acta.getNif_l10(), acta.getSancion_l10(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Local", "0", "00:00", "L", acta.getNum_l11(), acta.getCodlic_l11(), acta.getNom_cognoms_l11(),
                acta.getCat_l11(), acta.getPin_l11(), "", acta.getNif_l11(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Local", "0", "00:00", "L", acta.getNum_l12(), acta.getCodlic_l12(), acta.getNom_cognoms_l12(),
                acta.getCat_l12(), "", "", acta.getNif_l12(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Local", "0", "00:00", "L", acta.getNum_l13(), acta.getCodlic_l13(), acta.getNom_cognoms_l13(),
                acta.getCat_l13(), "", "", acta.getNif_l13(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Local", "0", "00:00", "L", acta.getNum_l14(), acta.getCodlic_l14(), acta.getNom_cognoms_l14(),
                acta.getCat_l14(), "", "", acta.getNif_l14(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Local", "0", "00:00", "L", acta.getNum_l15(), acta.getCodlic_l15(), acta.getNom_cognoms_l15(),
                acta.getCat_l15(), "", "", acta.getNif_l15(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Local", "0", "00:00", "L", acta.getNum_l16(), acta.getCodlic_l16(), acta.getNom_cognoms_l16(),
                acta.getCat_l16(), "", "", acta.getNif_l16(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Local", "0", "00:00", "L", acta.getNum_l17(), acta.getCodlic_l17(), acta.getNom_cognoms_l17(),
                acta.getCat_l17(), "", "", acta.getNif_l17(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        //Visitantes
        if (acta.getCapi_v1() == null) {
            acta.setCapi_v1("");
        }
        if (acta.getCapi_v2() == null) {
            acta.setCapi_v2("");
        }
        if (acta.getCapi_v3() == null) {
            acta.setCapi_v3("");
        }
        if (acta.getCapi_v4() == null) {
            acta.setCapi_v4("");
        }
        if (acta.getCapi_v5() == null) {
            acta.setCapi_v5("");
        }
        if (acta.getCapi_v6() == null) {
            acta.setCapi_v6("");
        }
        if (acta.getCapi_v7() == null) {
            acta.setCapi_v7("");
        }
        if (acta.getCapi_v8() == null) {
            acta.setCapi_v8("");
        }
        if (acta.getCapi_v9() == null) {
            acta.setCapi_v9("");
        }
        if (acta.getCapi_v10() == null) {
            acta.setCapi_v10("");
        }
        evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Visitant", "0", "00:00", "V", acta.getNum_v1(), acta.getCodlic_v1(), acta.getNom_cognoms_v1(),
                acta.getCat_v1(), acta.getPin_v1(), acta.getCapi_v1(), acta.getNif_v1(), acta.getSancion_v1(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Visitant", "0", "00:00", "V", acta.getNum_v2(), acta.getCodlic_v2(), acta.getNom_cognoms_v2(),
                acta.getCat_v2(), acta.getPin_v2(), acta.getCapi_v2(), acta.getNif_v2(), acta.getSancion_v2(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Visitant", "0", "00:00", "V", acta.getNum_v3(), acta.getCodlic_v3(), acta.getNom_cognoms_v3(),
                acta.getCat_v3(), acta.getPin_v3(), acta.getCapi_v3(), acta.getNif_v3(), acta.getSancion_v3(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Visitant", "0", "00:00", "V", acta.getNum_v4(), acta.getCodlic_v4(), acta.getNom_cognoms_v4(),
                acta.getCat_v4(), acta.getPin_v4(), acta.getCapi_v4(), acta.getNif_v4(), acta.getSancion_v4(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Visitant", "0", "00:00", "V", acta.getNum_v5(), acta.getCodlic_v5(), acta.getNom_cognoms_v5(),
                acta.getCat_v5(), acta.getPin_v5(), acta.getCapi_v5(), acta.getNif_v5(), acta.getSancion_v5(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Visitant", "0", "00:00", "V", acta.getNum_v6(), acta.getCodlic_v6(), acta.getNom_cognoms_v6(),
                acta.getCat_v6(), acta.getPin_v6(), acta.getCapi_v6(), acta.getNif_v6(), acta.getSancion_v6(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Visitant", "0", "00:00", "V", acta.getNum_v7(), acta.getCodlic_v7(), acta.getNom_cognoms_v7(),
                acta.getCat_v7(), acta.getPin_v7(), acta.getCapi_v7(), acta.getNif_v7(), acta.getSancion_v7(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Visitant", "0", "00:00", "V", acta.getNum_v8(), acta.getCodlic_v8(), acta.getNom_cognoms_v8(),
                acta.getCat_v8(), acta.getPin_v8(), acta.getCapi_v8(), acta.getNif_v8(), acta.getSancion_v8(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Visitant", "0", "00:00", "V", acta.getNum_v9(), acta.getCodlic_v9(), acta.getNom_cognoms_v9(),
                acta.getCat_v9(), acta.getPin_v9(), acta.getCapi_v9(), acta.getNif_v9(), acta.getSancion_v9(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Visitant", "0", "00:00", "V", acta.getNum_v10(), acta.getCodlic_v10(), acta.getNom_cognoms_v10(),
                acta.getCat_v10(), acta.getPin_v10(), acta.getCapi_v10(), acta.getNif_v10(), acta.getSancion_v10(), "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Visitant", "0", "00:00", "V", acta.getNum_v11(), acta.getCodlic_v11(), acta.getNom_cognoms_v11(),
                acta.getCat_v11(), acta.getPin_v11(), "", acta.getNif_v11(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Visitant", "0", "00:00", "V", acta.getNum_v12(), acta.getCodlic_v12(), acta.getNom_cognoms_v12(),
                acta.getCat_v12(), "", "", acta.getNif_v12(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Visitant", "0", "00:00", "V", acta.getNum_v13(), acta.getCodlic_v13(), acta.getNom_cognoms_v13(),
                acta.getCat_v13(), "", "", acta.getNif_v13(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Visitant", "0", "00:00", "V", acta.getNum_v14(), acta.getCodlic_v14(), acta.getNom_cognoms_v14(),
                acta.getCat_v14(), "", "", acta.getNif_v14(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Visitant", "0", "00:00", "V", acta.getNum_v15(), acta.getCodlic_v15(), acta.getNom_cognoms_v15(),
                acta.getCat_v15(), "", "", acta.getNif_v15(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Visitant", "0", "00:00", "V", acta.getNum_v16(), acta.getCodlic_v16(), acta.getNom_cognoms_v16(),
                acta.getCat_v16(), "", "", acta.getNif_v16(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Visitant", "0", "00:00", "V", acta.getNum_v17(), acta.getCodlic_v17(), acta.getNom_cognoms_v17(),
                acta.getCat_v17(), "", "", acta.getNif_v17(), "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        //Eventos del partido
        //Globales
        evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "L", 0, acta.getTotalfaltasl(), "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "V", 0, acta.getTotalfaltasv(), "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);

        //Obtenemos todos los eventos de tipo gol y los eliminamos
        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "", "", "", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.eliminarEactaeventGol(evento);

        evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "L", 0, acta.getTotalgolesl(), "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "V", 0, acta.getTotalgolesv(), "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);

        //Generamos los nuevos goles en funcion de lo que hay en el grid
        String aux = acta.getCgolespl();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        String[] parte = aux.split(",");
        aux = acta.getCgolesml();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        String[] crono = aux.split(",");
        aux = acta.getCgolesdl();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        String[] dorsal = aux.split(",");
        Long xdorsal;
        String xparte;
        String xcrono;
        if (isNumeric(acta.getTotalgolesl())) {
            //Goles existentes	
            if (!parte[0].equals("")) {
                for (int i = 0; i < parte.length; i++) {
                    if (isNumeric(dorsal[i].trim())) {
                        xdorsal = Long.parseLong(dorsal[i].trim());
                    } else {
                        xdorsal = Long.parseLong("0");
                    }
                    if (parte[i].equals("")) {
                        xparte = "1";
                    } else {
                        xparte = parte[i].trim();
                    }
                    if (crono[i].equals("")) {
                        xcrono = "00:00";
                    } else {
                        xcrono = crono[i].trim();
                    }
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "L", xdorsal, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
                //Goles añadidos
                for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
            } else { //Goles nuevos, no habia
                for (int i = 0; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
            }
        }
        aux = acta.getCgolespv();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        parte = aux.split(",");
        aux = acta.getCgolesmv();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        crono = aux.split(",");
        aux = acta.getCgolesdv();
        aux = aux.replaceAll("\\[", "");
        aux = aux.replaceAll("\\]", "");
        dorsal = aux.split(",");
        if (isNumeric(acta.getTotalgolesv())) {
            //Goles existentes	
            if (!parte[0].equals("")) {
                for (int i = 0; i < parte.length; i++) {
                    if (isNumeric(dorsal[i].trim())) {
                        xdorsal = Long.parseLong(dorsal[i].trim());
                    } else {
                        xdorsal = Long.parseLong("0");
                    }
                    if (parte[i].equals("")) {
                        xparte = "1";
                    } else {
                        xparte = parte[i].trim();
                    }
                    if (crono[i].equals("")) {
                        xcrono = "00:00";
                    } else {
                        xcrono = crono[i].trim();
                    }
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "V", xdorsal, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
                //Goles añadidos
                for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
            } else { //Goles nuevos
                for (int i = 0; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
                    eactaeventDAO.crearEactaEvent(evento);
                }
            }
        }

        evento = new EactaEvent(acta.getCodpartit(), 101, "Servei Inicial", "1", "00:00", acta.getSaqueinicial(), 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaevent(evento);
        evento = new EactaEvent(acta.getCodpartit(), 100, "Hora Inici Real", "1", acta.getHorainicio(), "L", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertol1(), "L", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertol2(), "L", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertov1(), "V", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertov2(), "V", 0, "", "", "", "", "", "", "", "", "");
        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
        acta = RecargarEventos(acta);

        //Firmas
        String protesto = "";
        String assabentat = "";
        Calendar c1 = Calendar.getInstance();
        String fecha = c1.get(Calendar.DATE) + "/" + c1.get(Calendar.MONTH) + "/" + c1.get(Calendar.YEAR);
        String hora = c1.getTime().getHours() + ":" + c1.getTime().getMinutes();
        String licencia = "";
        //Delegado de pista
        if (!acta.getFirma1_dp().equals("") && acta.getFirma1_dp_actualizar().equals("1")) {
            licencia = acta.getCodlic_dp();
            evento = new EactaEvent(acta.getCodpartit(), 90, "PreSignatura Delegat Pista", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma1_dp(), "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma1_dp().equals("") && acta.getFirma1_dp_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 90, "PreSignatura Delegat Pista", "0", "00:00", "L", 0, "", "",
                    "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Capitan local
        if (!acta.getFirma1_cl().equals("") && acta.getFirma1_cl_actualizar().equals("1")) {
            if (acta.getFirma1_cl().contains("PROTESTO")) {
                protesto = "1";
            } else {
                protesto = "";
            }
            if (acta.getFirma1_cl().contains("ASSABENTAT")) {
                assabentat = "1";
            } else {
                assabentat = "";
            }
            licencia = obtenerLicenciaCapitan("local", acta);
            evento = new EactaEvent(acta.getCodpartit(), 91, "PreSignatura Capita Local", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma1_cl(), protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma1_cl().equals("") && acta.getFirma1_cl_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 91, "PreSignatura Capita Local", "0", "00:00", "L", 0, "", "",
                    "", "", protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Entrenador local
        if (!acta.getFirma1_el().equals("") && acta.getFirma1_el_actualizar().equals("1")) {
            licencia = acta.getCodlic_l11();
            evento = new EactaEvent(acta.getCodpartit(), 92, "PreSignatura Tècnic Local", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma1_el(), "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma1_el().equals("") && acta.getFirma1_el_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 92, "PreSignatura Tècnic Local", "0", "00:00", "L", 0, "", "",
                    "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Capitan visitante
        if (!acta.getFirma1_cv().equals("") && acta.getFirma1_cv_actualizar().equals("1")) {
            if (acta.getFirma1_cv().contains("PROTESTO")) {
                protesto = "1";
            } else {
                protesto = "";
            }
            if (acta.getFirma1_cv().contains("ASSABENTAT")) {
                assabentat = "1";
            } else {
                assabentat = "";
            }
            licencia = obtenerLicenciaCapitan("visitante", acta);
            evento = new EactaEvent(acta.getCodpartit(), 93, "PreSignatura Capita Visitant", "0", "00:00", "V", 0, licencia, fecha,
                    hora, acta.getFirma1_cv(), protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma1_cv().equals("") && acta.getFirma1_cv_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 93, "PreSignatura Capita Visitant", "0", "00:00", "V", 0, "", "",
                    "", "", protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Entrenador visitante
        if (!acta.getFirma1_ev().equals("") && acta.getFirma1_ev_actualizar().equals("1")) {
            licencia = acta.getCodlic_v11();
            evento = new EactaEvent(acta.getCodpartit(), 94, "PreSignatura Tècnic Visitant", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma1_ev(), "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma1_ev().equals("") && acta.getFirma1_ev_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 94, "PreSignatura Tècnic Visitant", "0", "00:00", "L", 0, "", "",
                    "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Capitan local 2
        if (!acta.getFirma2_cl().equals("") && acta.getFirma2_cl_actualizar().equals("1")) {
            if (acta.getFirma2_cl().contains("PROTESTO")) {
                protesto = "1";
            } else {
                protesto = "";
            }
            if (acta.getFirma2_cl().contains("ASSABENTAT")) {
                assabentat = "1";
            } else {
                assabentat = "";
            }
            licencia = obtenerLicenciaCapitan("local", acta);
            evento = new EactaEvent(acta.getCodpartit(), 160, "Signatura Capita Local", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma2_cl(), protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma2_cl().equals("") && acta.getFirma2_cl_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 160, "Signatura Capita Local", "0", "00:00", "L", 0, "", "",
                    "", "", protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Arbitro 2
        if (!acta.getFirma2_ar().equals("") && acta.getFirma2_ar_actualizar().equals("1")) {
            licencia = acta.getCodlic_ar1();
            evento = new EactaEvent(acta.getCodpartit(), 162, "Signatura Àrbitre1", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma2_ar(), "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma2_ar().equals("") && acta.getFirma2_ar_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 162, "Signatura Àrbitre1", "0", "00:00", "L", 0, "", "",
                    "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Arbitro auxiliar 2
        if (!acta.getFirma2_ar2().equals("") && acta.getFirma2_ar2_actualizar().equals("1")) {
            licencia = acta.getCodlic_ar2();
            evento = new EactaEvent(acta.getCodpartit(), 163, "Signatura Àrbitre2", "0", "00:00", "L", 0, licencia, fecha,
                    hora, acta.getFirma2_ar2(), "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma2_ar2().equals("") && acta.getFirma2_ar2_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 163, "Signatura Àrbitre2", "0", "00:00", "L", 0, "", "",
                    "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        //Capitan visitante 2
        if (!acta.getFirma2_cv().equals("") && acta.getFirma2_cv_actualizar().equals("1")) {
            if (acta.getFirma2_cv().contains("PROTESTO")) {
                protesto = "1";
            } else {
                protesto = "";
            }
            if (acta.getFirma2_cv().contains("ASSABENTAT")) {
                assabentat = "1";
            } else {
                assabentat = "";
            }
            licencia = obtenerLicenciaCapitan("visitante", acta);
            evento = new EactaEvent(acta.getCodpartit(), 161, "Signatura Capità Visitant", "0", "00:00", "V", 0, licencia, fecha,
                    hora, acta.getFirma2_cv(), protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }
        if (acta.getFirma2_cv().equals("") && acta.getFirma2_cv_actualizar().equals("1")) {
            evento = new EactaEvent(acta.getCodpartit(), 161, "Signatura Capità Visitant", "0", "00:00", "V", 0, "", "",
                    "", "", protesto, assabentat, "", "", "");
            eactaeventDAO.actualizarEactaevent(evento);
        }

        //Recuperamos los datos del acta para las selecciones
        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
        listadatosacta = recuperarDatosActa(acta.getCodpartit());

        //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
        ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
        ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
        listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
        listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
        //codcate=33 -> àrbitres
        //codcate=37 -> delegat
        //codcate=56 -> auxiliar
        //codcate=39 -> tècnic ... la resta són jugadors i porters 
        listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
        listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
        listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
        listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
        listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
        listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
        listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
        listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
        listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);

        acta.setDatosactalocal(listajugadoresseleclocal);
        acta.setDatosactavisit(listajugadoresselecvisit);
        acta.setDatosactaTlocal(listatecnicoslocal);
        acta.setDatosactaTvisit(listatecnicosvisit);
        acta.setDatosactaTfede(listatecnicosfede);
        acta.setDatosactaDlocal(listadelegadoslocal);
        acta.setDatosactaDvisit(listadelegadosvisit);
        acta.setDatosactaDfede(listadelegadosfede);
        acta.setDatosactaAlocal(listaauxiliareslocal);
        acta.setDatosactaAvisit(listaauxiliaresvisit);
        acta.setDatosactaArbitro(listaarbitros);

        // calcular tiempo transcurrido
        long end = System.currentTimeMillis();
        long res = end - start;
        System.out.println("Guardar eActa - Segundos: " + res / 1000);

        return acta;
    }

    public String obtenerLicenciaCapitan(String equipo, Acta acta) {
        String resultado = "";

        if (equipo.equals("local")) {
            if (acta.getCapi_l1().equals("1")) {
                resultado = acta.getCodlic_l1();
            } else if (acta.getCapi_l2().equals("1")) {
                resultado = acta.getCodlic_l2();
            } else if (acta.getCapi_l3().equals("1")) {
                resultado = acta.getCodlic_l3();
            } else if (acta.getCapi_l4().equals("1")) {
                resultado = acta.getCodlic_l4();
            } else if (acta.getCapi_l5().equals("1")) {
                resultado = acta.getCodlic_l5();
            } else if (acta.getCapi_l6().equals("1")) {
                resultado = acta.getCodlic_l6();
            } else if (acta.getCapi_l7().equals("1")) {
                resultado = acta.getCodlic_l7();
            } else if (acta.getCapi_l8().equals("1")) {
                resultado = acta.getCodlic_l8();
            } else if (acta.getCapi_l9().equals("1")) {
                resultado = acta.getCodlic_l9();
            } else if (acta.getCapi_l10().equals("1")) {
                resultado = acta.getCodlic_l10();
            }
        } else if (acta.getCapi_v1().equals("1")) {
            resultado = acta.getCodlic_v1();
        } else if (acta.getCapi_v2().equals("1")) {
            resultado = acta.getCodlic_v2();
        } else if (acta.getCapi_v3().equals("1")) {
            resultado = acta.getCodlic_v3();
        } else if (acta.getCapi_v4().equals("1")) {
            resultado = acta.getCodlic_v4();
        } else if (acta.getCapi_v5().equals("1")) {
            resultado = acta.getCodlic_v5();
        } else if (acta.getCapi_v6().equals("1")) {
            resultado = acta.getCodlic_v6();
        } else if (acta.getCapi_v7().equals("1")) {
            resultado = acta.getCodlic_v7();
        } else if (acta.getCapi_v8().equals("1")) {
            resultado = acta.getCodlic_v8();
        } else if (acta.getCapi_v9().equals("1")) {
            resultado = acta.getCodlic_v9();
        } else if (acta.getCapi_v10().equals("1")) {
            resultado = acta.getCodlic_v10();
        }
        return resultado;
    }

    public ArrayList<DatosActa> obtenerJugadoresInicialesporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat, long codequip) {
        ArrayList<DatosActa> listajugadores = new ArrayList<DatosActa>();
        String categoria1 = "Jugador/a pista";
        String categoria2 = "Porter/a";
        for (DatosActa object : listadatosacta) {
            if (object.getCodenti() == codentitat
                    && (object.getCodequip() == codequip || object.getCodequip2() == codequip || object.getCodequip3() == codequip)
                    && (object.getSubmodalitat().equals(categoria1) || object.getSubmodalitat().equals(categoria2))) {
                listajugadores.add(object);
            }
        }
        return listajugadores;
    }

    public ArrayList<DatosActa> obtenerJugadoresporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat) {
        ArrayList<DatosActa> listajugadores = new ArrayList<DatosActa>();
        String categoria1 = "Jugador/a pista";
        String categoria2 = "Porter/a";
        for (DatosActa object : listadatosacta) {
            if (object.getCodenti() == codentitat
                    && (object.getSubmodalitat().equals(categoria1) || object.getSubmodalitat().equals(categoria2))) {
                listajugadores.add(object);
            }
        }
        return listajugadores;
    }

    public ArrayList<DatosActa> obtenerTecnicosporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat, long codcate) {
        ArrayList<DatosActa> listatecnicos = new ArrayList<DatosActa>();

        for (DatosActa object : listadatosacta) {
            if (object.getCodenti() == codentitat
                    && object.getCodcate() == codcate) {
                listatecnicos.add(object);
            }
        }
        return listatecnicos;
    }

    @SuppressWarnings("rawtypes")
    public boolean usuarioTienePermisos(String eactaconf, Long codtecnic, String user, Long codtecnic2) {
        //Buscamos usuario actual en la tabla de usuario por user
        //Si el id coincide con datosacta.getcodtecnic(0) es el arbitro --> true
        //Si el usuario tiene codperfil = 1, 2 o 3 es admin --> true
        //si no false
        boolean resultado = false;
        Eacta_vw_usuariHome usuarioDAO = new Eacta_vw_usuariHome();
        Eacta_vw_usuari usuario = null;
        List usuario2 = usuarioDAO.findByProperty("user", user);
        if (usuario2.size() > 0) {
            usuario = (Eacta_vw_usuari) usuario2.get(0);
        }

        if (!estaFlagActivo(eactaconf, 6)) {
            resultado = true;	//Validación desactivada. No controlamos los permisos.
        } else //FLAG 6 Validacion activa. Controlamos los permisos. Solo Admin y arbitro del partido.
        //System.out.println("VALIDACION 6 - USUARIO LOGADO PERMISOS ACTIVA");
        {
            if (usuario != null) {
                if (usuario.getCodtecnic().compareTo(codtecnic) == 0) {
                    resultado = true;
                }
                if (usuario.getCodtecnic().compareTo(codtecnic2) == 0) {
                    resultado = true;
                } else if (usuario.getCodperfil() == 1 || usuario.getCodperfil() == 2 || usuario.getCodperfil() == 3) {
                    resultado = true;
                }
            } else {
                System.out.println("No se encuentra el usuario");
                resultado = false;
            }
        }
        return resultado;

    }

    public static boolean estaFlagActivo(String config, int flag) {
        //16 Flags del campo eactaconf. Si el flag n esta activo...
        //1 - No se puede añadir un jugador sancionado ni local ni visitante
        //2 - No se pueden añadir mas de 4 jugadores de categoria inferior a la del partido (excepto junior codecat=7)
        //3 - Deben haber tres jugadores y un portero mínimo para guardar el acta
        //4 - Delegado de pista obligatorio
        //5 - Cronometrador obligatorio
        //6 - Se validan los permisos del usuario. Debe ser el arbitro del partido o admin
        //7 - Avisa que no hay tecnico
        //8 - Pone a observaciones (L'equip xxx no presenta tècnic).
        //9 - 16 - Libres
        if (config.charAt(flag - 1) == '1') {
            return true;
        } else {
            return false;
        }
    }

    public void SendMail(Acta acta, String destino) {
        //Enviamos a mail arbitro y clubs
        System.out.println("eACTA - ENVIANDO MAIL A :");

        String Username = "eacta@fecapa.cat";
        String Subject = "FECAPA: eActa del partit " + acta.getCodpartit();
        String To = "";
        //FLAG 7 Envio del correo al cerrar el acta
        if (estaFlagActivo(acta.getConfig(), 7)) {
            //Enviamos mail al arbitro y los dos clubs
            To = acta.getEmail_ar1() + "," + acta.getMailequiplocal() + "," + acta.getMailequipvisit();
            System.out.println("Arbitro: " + acta.getEmail_ar1());
            System.out.println("Club local: " + acta.getMailequiplocal());
            System.out.println("Club visitante: " + acta.getMailequipvisit());
        } else {
            //Enviamos mail a usuarios de test
            To = "eacta@fecapa.cat" + "," + "caltimiras@fecapa.cat" + "," + "mmolto@fecapa.cat";
            System.out.println("Usuarios test: " + To);
        }
        //FLAG 9 Envio del correo a secretaria tecnica
        if (estaFlagActivo(acta.getConfig(), 9)) {
            To = To + "," + "eacta@fecapa.cat";
            System.out.println("Secretaria técnica: eacta@fecapa.cat");
        }

        String Mensaje = "FECAPA: eActa del partit " + acta.getCodpartit();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.fecapa.cat");
        props.put("mail.smtp.ssl.trust", "smtp.fecapa.cat");
//          props.put("mail.smtp.port", "5022");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.user", "eacta@fecapa.cat");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("eacta@fecapa.cat", "Cepeda2015");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensaje);

            BodyPart texto = new MimeBodyPart();
            texto.setText(Mensaje);

            MimeBodyPart mbp2 = new MimeBodyPart();
            FileDataSource source = new FileDataSource(destino);
            mbp2.setDataHandler(new DataHandler(source));
            mbp2.setFileName(source.getName());

            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(mbp2);

            message.setContent(multiParte);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static Acta RecargarEventos(Acta acta) {
        acta.setTotalfaltasl("");
        acta.setTotalfaltasv("");
        acta.setTotalgolesl("");
        acta.setTotalgolesv("");
        acta.setSaqueinicial("");
        acta.setHorainicio("");
        acta.setTiempomuertol1("");
        acta.setTiempomuertol2("");
        acta.setTiempomuertov1("");
        acta.setTiempomuertov2("");

        //Recargamos eventos de las tablas
        EactaEventHome eactaeventDAO = new EactaEventHome();
        List<Object[]> listaeventos = eactaeventDAO.eventosPartidoPorPartido(acta.getCodpartit());
        ArrayList<String> levento_id = new ArrayList<String>();
        ArrayList<String> levento_tipo = new ArrayList<String>();
        ArrayList<String> levento_parte = new ArrayList<String>();
        ArrayList<String> levento_crono = new ArrayList<String>();
        ArrayList<String> levento_equipo = new ArrayList<String>();
        ArrayList<String> levento_dorsal = new ArrayList<String>();
        ArrayList<String> levento_atributo = new ArrayList<String>();
        ArrayList<String> levento_atributop = new ArrayList<String>();

        ArrayList<String> golespl = new ArrayList<String>();
        ArrayList<String> golesml = new ArrayList<String>();
        ArrayList<String> golesdl = new ArrayList<String>();
        ArrayList<String> golesel = new ArrayList<String>();

        ArrayList<String> golespv = new ArrayList<String>();
        ArrayList<String> golesmv = new ArrayList<String>();
        ArrayList<String> golesdv = new ArrayList<String>();
        ArrayList<String> golesev = new ArrayList<String>();

        ArrayList<String> faltasap = new ArrayList<String>();
        ArrayList<String> faltasam = new ArrayList<String>();
        ArrayList<String> faltasad = new ArrayList<String>();
        ArrayList<String> faltasar = new ArrayList<String>();
        ArrayList<String> faltasae = new ArrayList<String>();

        ArrayList<String> faltasrp = new ArrayList<String>();
        ArrayList<String> faltasrm = new ArrayList<String>();
        ArrayList<String> faltasrd = new ArrayList<String>();
        ArrayList<String> faltasrr = new ArrayList<String>();
        ArrayList<String> faltasre = new ArrayList<String>();

        for (Object[] object : listaeventos) {
            levento_id.add(object[1].toString());
            levento_tipo.add(object[3].toString());
            levento_parte.add(object[4].toString());
            levento_crono.add(object[5].toString());
            levento_equipo.add(object[6].toString());
            levento_dorsal.add((object[7] == null) ? "" : object[7].toString());
            if (object[8] == null) {
                levento_atributo.add("");
                levento_atributop.add("");
            } else {
                levento_atributo.add(object[8].toString());
                if (object[8].toString().length() > 10) {
                    levento_atributop.add(object[8].toString().substring(0, 10) + "...");
                } else {
                    levento_atributop.add(object[8].toString());
                }
            }
            if (object[3].toString().equals("Total Faltes")) {
                if (object[6].toString().equals("L")) {
                    acta.setTotalfaltasl((object[8] == null) ? "" : object[8].toString());
                }
                if (object[6].toString().equals("V")) {
                    acta.setTotalfaltasv((object[8] == null) ? "" : object[8].toString());
                }
            }

            if (object[3].toString().equals("Total Gols")) {
                if (object[6].toString().equals("L")) {
                    acta.setTotalgolesl((object[8] == null) ? "" : object[8].toString());
                }
                if (object[6].toString().equals("V")) {
                    acta.setTotalgolesv((object[8] == null) ? "" : object[8].toString());
                }
            }

            if (object[3].toString().equals("Servei Inicial")) {
                acta.setSaqueinicial(object[6].toString());
            }

            if (object[3].toString().equals("Hora Inici Real")) {
                acta.setHorainicio(object[5].toString());
            }

            if (object[3].toString().equals("Temps Mort")) {
                if (object[6].toString().equals("L") && object[4].toString().equals("1")) {
                    acta.setTiempomuertol1(object[5].toString());
                }
                if (object[6].toString().equals("L") && object[4].toString().equals("2")) {
                    acta.setTiempomuertol2(object[5].toString());
                }
                if (object[6].toString().equals("V") && object[4].toString().equals("1")) {
                    acta.setTiempomuertov1(object[5].toString());
                }
                if (object[6].toString().equals("V") && object[4].toString().equals("2")) {
                    acta.setTiempomuertov2(object[5].toString());
                }
            }

            if (object[3].toString().equals("Gol")) {
                if (object[6].toString().equals("L")) {
                    golespl.add(object[4].toString());
                    golesml.add(object[5].toString());
                    golesdl.add((object[7] == null) ? "" : object[7].toString());
                    golesel.add(object[6].toString());
                } else {
                    golespv.add(object[4].toString());
                    golesmv.add(object[5].toString());
                    golesdv.add((object[7] == null) ? "" : object[7].toString());
                    golesev.add(object[6].toString());
                }
            }

            if (object[3].toString().equals("Tarja Blava")) {
                faltasap.add(object[4].toString());
                faltasam.add(object[5].toString());
                faltasad.add((object[7] == null) ? "" : object[7].toString());
                faltasar.add((object[8] == null) ? "" : object[8].toString());
                faltasae.add(object[6].toString());
            }

            if (object[3].toString().equals("Tarja Vermella")) {
                faltasrp.add(object[4].toString());
                faltasrm.add(object[5].toString());
                faltasrd.add((object[7] == null) ? "" : object[7].toString());
                faltasrr.add((object[8] == null) ? "" : object[8].toString());
                faltasre.add(object[6].toString());
            }
        }

        acta.setEvento_id(levento_id);
        acta.setEvento_tipo(levento_tipo);
        acta.setEvento_parte(levento_parte);
        acta.setEvento_crono(levento_crono);
        acta.setEvento_equipo(levento_equipo);
        acta.setEvento_dorsal(levento_dorsal);
        acta.setEvento_atributo(levento_atributo);
        acta.setEvento_atributop(levento_atributop);

        acta.setGolesdl(golesdl);
        acta.setGolesml(golesml);
        acta.setGolespl(golespl);
        acta.setGolesel(golesel);

        acta.setGolesdv(golesdv);
        acta.setGolesmv(golesmv);
        acta.setGolespv(golespv);
        acta.setGolesev(golesev);

        acta.setFaltasad(faltasad);
        acta.setFaltasam(faltasam);
        acta.setFaltasap(faltasap);
        acta.setFaltasar(faltasar);
        acta.setFaltasae(faltasae);

        acta.setFaltasrd(faltasrd);
        acta.setFaltasrm(faltasrm);
        acta.setFaltasrp(faltasrp);
        acta.setFaltasrr(faltasrr);
        acta.setFaltasre(faltasre);

        acta.setSeccion("EVENTOS");
        acta.setMessage("Evento creado");

        return acta;
    }

    @SuppressWarnings("unchecked")
    public void EnviarResultado(Acta acta) throws IOException {
        //FLAG 12 Enviar resultado automático al cerrar el acta
        if (estaFlagActivo(acta.getConfig(), 12)) {
            //Recuperar goles local y visitante
            EactaEventHome eactaeventoDAO = new EactaEventHome();
            List<Object[]> listaeventos = eactaeventoDAO.eventosPartidoPorPartido(acta.getCodpartit());
            String golesL = EActaPDFBuilder.calcularTotalGoles(listaeventos, "L");
            String golesV = EActaPDFBuilder.calcularTotalGoles(listaeventos, "V");

            //Preparamos la llamada 
            String host = "";
            //FLAG 14 Enviar resultado automatico al servidor de produccion
            if (estaFlagActivo(acta.getConfig(), 14)) {
                host = "www.fecapa.com";
            } else {
                host = "www.aplicbox.com";
            }
            String url = "http://" + host + ":9080/eCompeticio/PartitAction.do?action=smsresultats&code=00000000&date=11111111&time=222222&fullnumber=" + acta.getTelefono_ar1()
                    + "&word1=FECAPA&word2=" + acta.getCodpartit() + "&word3=" + golesL + "&word4=" + golesV
                    + "&word5=&aliases=FECAPA&text=FECAPA%20" + acta.getCodpartit() + "%20" + golesL + "%20" + golesV + "%20";
            //TEST url = "http://aplicbox.com:9080/eCompeticio/PartitAction.do?action=smsresultats&code=00000000&date=11111111&time=222222&fullnumber=649482995&word1=FECAPA&word2=165902&word3=8&word4=7&word5=&aliases=$word1&text=$word1%20$word2%20$word3%20$word4%20$word5";
            System.out.println("eACTA - Enviando resultado automático del acta " + acta.getCodpartit() + " a " + url);

            URL URL = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(URL.openStream()));
            String inputLine;
            String resultado = "";
            while ((inputLine = in.readLine()) != null) {
                resultado = resultado + inputLine;
            }
            in.close();
            if (resultado.equals("")) {
                resultado = "NO RESPONDE";
            }

            System.out.println("Resultado --> " + resultado);

            //Creamos un evento con el resultado
            EactaEventHome eactaeventDAO = new EactaEventHome();
            EactaEvent evento = new EactaEvent(acta.getCodpartit(), 61, "", "", "", "", 0, "", "", "", "", "", "", "", "", "");
            eactaeventDAO.actualizarEactaResultado(evento, url, resultado);
        }
    }

    public boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static String obtenerClase(Long clase) {
        int claseaux;
        String resultado = "";
        if (clase == null) {
            return resultado;
        } else {
            claseaux = clase.intValue();
            switch (claseaux) {
                case 1:
                    resultado = "";
                    break;
                case 2:
                    resultado = "A";
                    break;
                case 3:
                    resultado = "B";
                    break;
                case 4:
                    resultado = "C";
                    break;
                case 5:
                    resultado = "D";
                    break;
                case 6:
                    resultado = "E";
                    break;
                case 7:
                    resultado = "F";
                    break;
                case 8:
                    resultado = "G";
                    break;
                case 9:
                    resultado = "H";
                    break;
                case 10:
                    resultado = "I";
                    break;
                case 11:
                    resultado = "J";
                    break;
                case 12:
                    resultado = "K";
                    break;
                case 13:
                    resultado = "L";
                    break;
                case 14:
                    resultado = "M";
                    break;
                case 15:
                    resultado = "N";
                    break;
                case 16:
                    resultado = "O";
                    break;
                case 17:
                    resultado = "P";
                    break;
                case 18:
                    resultado = "Q";
                    break;
                case 19:
                    resultado = "R";
                    break;
                case 20:
                    resultado = "S";
                    break;
                case 21:
                    resultado = "T";
                    break;
                case 22:
                    resultado = "U";
                    break;
                case 23:
                    resultado = "V";
                    break;
                case 24:
                    resultado = "W";
                    break;
                case 25:
                    resultado = "X";
                    break;
                case 26:
                    resultado = "Y";
                    break;
                case 27:
                    resultado = "Z";
                    break;
                default:
                    break;
            }
            return resultado;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(estaFlagActivo("00000010111111101101", 12));
        System.out.println(estaFlagActivo("00000010111111101101", 14));

    }

}









































//package es.fecapa.eacta.controller;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//import java.util.Properties;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.BodyPart;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import es.fecapa.eacta.bean.Acta;
//import es.fecapa.eacta.bean.DatosActa;
//import es.fecapa.eacta.bean.Login;
//import es.fecapa.eacta.bean.Preacta;
//import es.fecapa.eacta.db.EactaEvent;
//import es.fecapa.eacta.db.EactaEventHome;
//import es.fecapa.eacta.db.Eacta_vw_partit;
//import es.fecapa.eacta.db.Eacta_vw_partitHome;
//import es.fecapa.eacta.db.Eacta_vw_usuari;
//import es.fecapa.eacta.db.Eacta_vw_usuariHome;
//import es.fecapa.eacta.pdf.EActaPDFBuilder;
//import es.fecapa.eacta.servlet.LogSupport;
//
//import es.fecapa.eacta.servlet.ObtePdf;
//import es.fecapa.eacta.servlet.Parametres;
//import java.io.BufferedInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.net.URLConnection;
//import java.nio.ByteBuffer;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.jar.Attributes;
//import java.util.jar.Manifest;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.mail.Session;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.hibernate.Version;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Controller
//public class EActa_controller {
//
//    public static String ACTION_EXPORTAR_PDF = "exportar";
//    public static String ACTION_GUARDAR = "guardar";
//    public static String ACTION_GUARDAR_SALIR = "guardarsalir";
//    public static String ACTION_CERRAR = "cerrar";
//    public static String ACTION_CERRARSESION = "cerrarsesion";
//    public static String ACTION_SALIR = "salir";
//    public static String ACTION_FIRMA1_DP = "firma1_delegadopista";
//    public static String ACTION_FIRMA1_CL = "firma1_capitanlocal";
//    public static String ACTION_FIRMA1_EL = "firma1_entrenadorlocal";
//    public static String ACTION_FIRMA1_CV = "firma1_capitanvisit";
//    public static String ACTION_FIRMA1_EV = "firma1_entrenadorvisit";
//
//    public static String ACTION_FIRMAS1 = "firmas1";
//    public static String ACTION_FIRMAS2 = "firmas2";
//
//    public static String ACTION_FIRMA2_CL = "firma2_capitanlocal";
//    public static String ACTION_FIRMA2_AR = "firma2_arbitro";
//    public static String ACTION_FIRMA2_AR2 = "firma2_arbitro2";
//    public static String ACTION_FIRMA2_CV = "firma2_capitanvisit";
//    public static String ACTION_NUEVO_EVENTO = "nuevoevento";
//    public static String ACTION_ELIMINAR_EVENTO = "eliminarevento";
//    public static String ACTION_REINICIAR = "reiniciar";
//    public static String ACTION_CREARMODIFICAR = "crearmodif";
//
//    public static String ACTION_NUEVO_EVENTO_TAZUL = "nuevoeventotazul";
//    public static String ACTION_NUEVO_EVENTO_TROJA = "nuevoeventotroja";
////    public static String ACTION_DESCARREGAR_ACTA = "descarregar";
//
//    //   private static final Log log = LogFactory.getLog(EActa_controller_old.class);
//    public static String ACTION_TORNAR_LOCAL = "tornarLocal";
//    public static String ACTION_TORNAR_VISITANT = "tornarVisitant";
//    public static String ACTION_TORNAR_OBSERVACIONS = "tornarObservacions";
//    public static String ACTION_TORNAR_LLISTA_EVENTS = "tornarLlistaEvents";
//    public static String ACTION_TORNAR_CAPCALERA = "tornarCapcalera";
////    public static String ACTION_MOSTRAR_EVENTS ="mostrarEvents";
////    public final static String CURRENT_VERSION =  Application.class.getPackage().getImplementationVersion();
//    
//    
//    
//
//    @SuppressWarnings("rawtypes")
//    @RequestMapping("/login")
//    public ModelAndView login(@ModelAttribute Login login, Model m) throws Exception {
//        //PROCESO DE LOGIN
//        String message = "";
//        ModelAndView retorno = null;
//        //BUSCAMOS EL USUARIO LOGADO EN LA TABLA DE USUARIOS
//        Eacta_vw_usuariHome usuarioDAO = new Eacta_vw_usuariHome();
//        Eacta_vw_usuari usuario = null;
//        List usuario2 = usuarioDAO.findByProperty("user", login.getUsername());
//        if (usuario2.size() > 0) {
//            usuario = (Eacta_vw_usuari) usuario2.get(0);
//        }
//
//        if (usuario != null) {
//            //EL USUARIO EXISTE
//            if (login.getPassword().equals(usuario.getPassword())) {
//                //USUARIO CORRECTO
//
//                message = "Benvingut usuari " + login.getUsername();
//                login.setMessage(message);
//                LogSupport.info("Login usuari : " + login.getUsername());
//                retorno = new ModelAndView("menu", "login", login);
//
//            } else {
//                //PASSWORD INCORRECTO
//                message = "Password incorrecte";
//                retorno = new ModelAndView("login", "message", message);
//            }
//        } else {
//            //EL USUARIO NO EXISTE
//            message = "L' usuari no es troba a la base de dades";
//            retorno = new ModelAndView("login", "message", message);
//        }
//        return retorno;
//    }
//
//    @RequestMapping("/preacta")
//    public ModelAndView preacta(@ModelAttribute Preacta preacta, Model m) throws Exception {
//        //PREVIO A LA CREACION DEL ACTA
//        String message = "";
//        ModelAndView retorno = null;
//        //   log.debug("debug  preacta ");
//        //Cerrar sesion
//        if (preacta.getAccion().equalsIgnoreCase(ACTION_CERRARSESION)) {
//            preacta.setCodpartit(0);
//            message = "";
//            retorno = new ModelAndView("login", "message", message);
//        } else {
//            //BUSCAMOS EL PARTIDO INTRODUCIDO POR EL USUARIO
//            Eacta_vw_partitHome partitDAO = new Eacta_vw_partitHome();
//            Eacta_vw_partit partit = partitDAO.findById(preacta.getCodpartit());
//
//            if (partit != null) {
//                int x = comprovaTemporada(preacta.getCodpartit());
//                if (x == 0) {
//                    LogSupport.info(" Usuari :"+preacta.getUsername() + " introdueix un codi de partit dÂ´altre temporada" );
//                    message = "Aquest partit no Ã©s dÂ´aquesta temporada";
//                    retorno = new ModelAndView("menu", "message", message);
//                } else {
//                    //BUSCAMOS LOS DATOS Y JUGADORES ASOCIADOS AL PARTIDO PARA CREAR EL ACTA
//                    ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
//                    listadatosacta = recuperarDatosActa(preacta.getCodpartit());
//                    Acta acta = recuperarActa(preacta.getCodpartit(), partit.getCodtecnic(), partit.getCodtecnic2());
//                    acta.setSeccion("");
//
//                    if (acta != null) {
//                        //Verificar si el usuario puede crear o modificar el acta
//                        if (usuarioTienePermisos(acta.getConfig(), partit.getCodtecnic(), preacta.getUsername(), partit.getCodtecnic2())) {
//
//                            if (preacta.getAccion().equalsIgnoreCase(ACTION_REINICIAR)) {  	//REINICIAR ACTA solo si abierta
//                                if (acta.getEstado().equals("A")) {
//                                    //Eliminamos todos los eventos del acta
//                                    eliminarActa(preacta.getCodpartit());
//
//                                    ObtePdf ob = new ObtePdf();
//                                    ob.eliminarPdf(preacta.getCodpartit());
//                                    ob.deletePdf(preacta.getCodpartit());
//
//                                    //Volvemos a generarla
//                                    listadatosacta = recuperarDatosActa(preacta.getCodpartit());
//                                    acta = recuperarActa(preacta.getCodpartit(), partit.getCodtecnic(), partit.getCodtecnic2());
//                                    acta.setSeccion("");
//                                } else {
//                                    message = "L'acta del partit " + preacta.getCodpartit() + " no es pot reiniciar perque ha sigut tancada.";
//                                    LogSupport.info("usuari : " + preacta.getUsername() + " intenta reiniciar acta tancada " +preacta.getCodpartit() );
//                                    Login login = new Login();
//                                    login.setUsername(preacta.getUsername());
//                                    login.setMessage(message);
//                                    retorno = new ModelAndView("menu", "login", login);
//                                    return retorno;
//                                }
//                            }
//
//                            //CREA O MODIFICAR ACTA
//                            //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
//                            ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
//                            ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
//                            listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
//                            listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
//                            //codcate=33 -> Ã rbitres
//                            //codcate=37 -> delegat
//                            //codcate=56 -> auxiliar
//                            //codcate=39 -> tÃ¨cnic ... la resta sÃ³n jugadors i porters 
//                            listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
//                            listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
//                            listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
//                            listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
//                            listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
//                            listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
//                            listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
//                            listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
//                            listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);
//
//                            acta.setDatosactalocal(listajugadoresseleclocal);
//                            acta.setDatosactavisit(listajugadoresselecvisit);
//                            acta.setDatosactaTlocal(listatecnicoslocal);
//                            acta.setDatosactaTvisit(listatecnicosvisit);
//                            acta.setDatosactaTfede(listatecnicosfede);
//                            acta.setDatosactaDlocal(listadelegadoslocal);
//                            acta.setDatosactaDvisit(listadelegadosvisit);
//                            acta.setDatosactaDfede(listadelegadosfede);
//                            acta.setDatosactaAlocal(listaauxiliareslocal);
//                            acta.setDatosactaAvisit(listaauxiliaresvisit);
//                            acta.setDatosactaArbitro(listaarbitros);
//
//                            acta.setCodpartit(preacta.getCodpartit());
//                            acta.setUsername(preacta.getUsername());
//
////                        MostraPdf mostra = new MostraPdf();
////                        mostra.obtenirArxiu(175620);
//                            ObtePdf ob = new ObtePdf();
//                            ob.obtenirArxiu(preacta.getCodpartit());
//
//                            retorno = new ModelAndView("acta", "acta", acta);
//
//                        } else {
//                            message = "No tÃ© permisos per accedir a l'acta del partit " + preacta.getCodpartit() + ".";
//                            Login login = new Login();
//                            login.setUsername(preacta.getUsername());
//                            login.setMessage(message);
//                            LogSupport.info("usuari : " + preacta.getUsername() + " intenta accedir a acta sense permisos");
//                            retorno = new ModelAndView("menu", "login", login);
//                        }
//                    } else {
//                        //System.out.println("ACTA NO GENERADA");
//                        message = "No es troben les dades de l'acta del partit " + preacta.getCodpartit() + ". Consulteu amb l'administrador (eacta@fecapa.cat).";
//                        Login login = new Login();
//                        login.setUsername(preacta.getUsername());
//                        login.setMessage(message);
//                        LogSupport.info("no es troben les dades de lÂ´acta del partit " + preacta.getCodpartit());
//                        retorno = new ModelAndView("menu", "login", login);
//                    }
//                }
//
//            } else {
//                message = "El partit " + preacta.getCodpartit() + " no s'ha trobat a la base de dades";
//                Login login = new Login();
//                login.setUsername(preacta.getUsername());
//                login.setMessage(message);
//                LogSupport.info(" partit" + preacta.getCodpartit() + " No es troba a la base de dades");
//                retorno = new ModelAndView("menu", "login", login);
//            }
//        }
//        return retorno;
//    }
//
//    private ByteBuffer getAsByteArray(final URLConnection urlConnection) throws IOException {
//        final ByteArrayOutputStream tmpOut = new ByteArrayOutputStream();
//        final InputStream inputStream = urlConnection.getInputStream();
//        final byte[] buf = new byte[1024];
//        int len;
//        while (true) {
//            len = inputStream.read(buf);
//            if (len == -1) {
//                break;
//            }
//            tmpOut.write(buf, 0, len);
//        }
//        tmpOut.close();
//        return ByteBuffer.wrap(tmpOut.toByteArray(), 0, tmpOut.size());
//    }
//
//    private int comprovaTemporada(long codi) {
//        int cod = 0;
//        Connection con = null;
//        Statement stmt = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
//
//            stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("select count(*) from fecapa.partit p,fecapa.competicio c\n"
//                    + "where p.codpartit=" + codi + " and\n"
//                    + "c.codcomp=p.codcomp and\n"
//                    + "c.codtemp=(select codtemp from fecapa.comite where codcomite=1) limit 1;");
//            if (rs.next()) {
//                cod = rs.getInt(1);
//            }
//        } catch (Exception e) {            
//            LogSupport.grava(e);
//        }
//        return cod;
//    }
//
//    protected void gravaPdf(byte[] pdf, Acta acta) throws ServletException, IOException, SQLException {
//        Connection con = null;
//        Statement stmt = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
//
//            stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT pdf FROM eacta_event where codpartit=" + acta.getCodpartit() + " and tipoevent=202 limit 1; ");
//            if (!rs.next()) {
//                PreparedStatement pstmt = con.prepareStatement("INSERT INTO eacta_event "
//                        + "(codpartit, pdf, tipoevent, event) VALUES(" + acta.getCodpartit() + ", ? , 202, 'grava pdf' ) ;");
//                pstmt.setBytes(1, pdf);
//                pstmt.executeUpdate();
//                con.close();
//            } else {
//                PreparedStatement pstmt = con.prepareStatement("UPDATE eacta_event SET pdf=? "
//                        + " where codpartit=" + acta.getCodpartit() + " and tipoevent=202 ; ");
//                pstmt.setBytes(1, pdf);
//                pstmt.executeUpdate();
//                con.close();
//            }
//        } catch (Exception e) {
//            LogSupport.grava(e);
//        }
//    }
//
//    @RequestMapping("/acta")
//    public ModelAndView acta(@ModelAttribute Acta acta, Model m) throws Exception {
//        //FUNCIONALIDADES DENTRO DEL EACTA
//        String destino = "";
//        ModelAndView retorno = null;
//        //  log.debug("debug  Acta");
//
//        if (acta.getAccion().equalsIgnoreCase(ACTION_EXPORTAR_PDF)) {  	//EXPORTAR PDF 
//            LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode exportar_pdf");
//            acta = GuardarActa(acta, "A");
//            acta.setSeccion("");
//
//            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";
//
//            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));
//
//            //   log.debug(" es previsualitza acta " );
//            retorno = new ModelAndView("acta", "destino", destino);
//        } 
//              else if (acta.getAccion().equalsIgnoreCase(ACTION_CERRAR)) {	//CERRAR
//            LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode cerrar" );
//            //Cambiamos estado y guardamos acta
//            acta = GuardarActa(acta, "C");
//
//            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";
//
//            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));
//
//            try {
//
//                URLConnection ur = new URL("http://localhost:8080/pdf/ACTA_" + acta.getCodpartit() + ".pdf").openConnection();
//
//                byte[] b = getAsByteArray(ur).array();
//
//                gravaPdf(b, acta);
//
//                ObtePdf ob = new ObtePdf();
//                ob.eliminarPdf(acta.getCodpartit());
//                ob.obtenirArxiu(acta.getCodpartit());
//            } catch (Exception e) {
//                LogSupport.grava(e);
//            }
//
//            try {
//                // Enviamos resultados
//                EnviarResultado(acta);
//            } catch (Exception e) {
//                LogSupport.grava(e);
//            }
//
//            try {
//                // Enviamos mails
//                SendMail(acta, destino);
//            } catch (Exception e) {
//                LogSupport.grava(e);
//            }
//
//            try {
//                Parametres p = new Parametres();
//                p.enviaMail(acta.getCodpartit(), destino, true);
//            } catch (Exception e) {
//                LogSupport.grava(e);
//            }
//
//            //    log.debug("es tanca lÂ´acta " + acta.getCodpartit());
//            retorno = new ModelAndView("menu", "login", acta);
//
//        } 
//              else if (acta.getAccion().equalsIgnoreCase(ACTION_GUARDAR_SALIR)) {	//GUARDAR Y SALIR
//                  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode guardar_salir");
//            ObtePdf ob = new ObtePdf();
//            ob.eliminarPdf(acta.getCodpartit());
//
//            acta = GuardarActa(acta, "A");
//            retorno = new ModelAndView("menu", "login", acta);
//
//        } else if (acta.getAccion().equalsIgnoreCase(ACTION_GUARDAR)) {	//GUARDAR  --> Desde eventos del partido
//             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode guardar");
//            acta = GuardarActa(acta, "A");
//            acta.setSeccion("GENEVENTOS");
//            retorno = new ModelAndView("acta", "message", "Acta guardada");
//
//        } else if (acta.getAccion().equalsIgnoreCase(ACTION_SALIR)) {	//SALIR
//             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode salir");
//            ObtePdf ob = new ObtePdf();
//            ob.eliminarPdf(acta.getCodpartit());
//
//            retorno = new ModelAndView("menu", "login", acta);
//        } 
//              else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMAS1)) {	//FIRMA ALINEACION DELEGADO
//             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode firmas1");
////            acta = GuardarActa(acta, "A");
////            acta.setSeccion("FIRMA1");
////            acta.setMessage("Acta guardada tras firma pre-partido");
////            //    log.debug("acta "+ acta.getCodpartit() + "  signada la part de firmes 1");
////            retorno = new ModelAndView("acta", "acta", acta);
//
//            Parametres p = new Parametres();
//            p.preSignatura(acta);
//            
//          //  acta = RecargarEventos(acta);
//          acta = obtenirActa(acta, "A");
//          acta = RecargarEventos(acta);
//            acta.setSeccion("FIRMA1");
//            acta.setMessage("Acta guardada tras firma pre-partido");
//            retorno = new ModelAndView("acta", "acta", acta);
//
////        } 
//              else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_DP) || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_CL)
////                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_EL) || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_CV)
////                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_EV)) {	//FIRMA ALINEACION DELEGADO
////            acta = GuardarActa(acta, "A");
////            acta.setSeccion("FIRMA1");
////            acta.setMessage("Acta guardada tras firma pre-partido");
////            //    log.debug("acta "+ acta.getCodpartit() + "  signada la part de firmes 1");
////            retorno = new ModelAndView("acta", "acta", acta);
////        } 
////           else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMA1_DP)) {
////            Parametres p = new Parametres();
////            p.updateSignatures(acta);            
////            acta.setSeccion("FIRMA1");
////            acta.setMessage("Acta guardada tras firma pre-partido");
////            acta =  this.obtenirActa(acta, "A");
////            retorno = new ModelAndView("acta", "acta", acta);
////        } 
////        else if (acta.getAccion().contains("Presignatures")) {
////            Parametres p = new Parametres();
//        }
//          else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMAS2)) {	//FIRMA RESULTADO PARTIDO
//             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode firmas2");
//            Parametres p = new Parametres();
//            p.finalSignatura(acta);
//            acta = RecargarEventos(acta);
//             acta = obtenirActa(acta, "A");
//           // acta = RecargarEventos(acta);
//            acta.setSeccion("FIRMA2");
//            acta.setMessage("Acta guardada tras firma partido");
//            
//            retorno = new ModelAndView("acta", "acta", acta);
//
////        }
////        else if (acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_CL)
////                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_AR)
////                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_AR2)
////                || acta.getAccion().equalsIgnoreCase(ACTION_FIRMA2_CV)) {	//FIRMA RESULTADO PARTIDO
////            acta = GuardarActa(acta, "A");
////            acta.setSeccion("FIRMA2");
////            acta.setMessage("Acta guardada tras firma partido");
////            //    log.debug("acta "+ acta.getCodpartit() + "  signada la part de firmes 2");
////            retorno = new ModelAndView("acta", "acta", acta);
//        } 
//        else if (acta.getAccion().contains("cerrar_") && (acta.getAccion().length() > 5)) {
//             LogSupport.info("usuari: "+acta.getUsername()+ " Eacta controller dintre de metode cerrar_mosca");
//            String tasques = acta.getAccion();
//
//            boolean comite = false;
//
//            acta = GuardarActa(acta, "C");
//
//            destino = "/var/www/eACTA/ACTA_" + acta.getCodpartit() + ".pdf";
//
//            new EActaPDFBuilder().generarPDF(destino, acta, new Locale("es"));
//             LogSupport.info("usuari: "+acta.getUsername()+ " es genera el pdf del partit "+ acta.getCodpartit());
//            try {
//
//                URLConnection ur = new URL("http://localhost:8090/pdf/ACTA_" + acta.getCodpartit() + ".pdf").openConnection();
//
//                byte[] b = getAsByteArray(ur).array();
//
//                gravaPdf(b, acta);
//
//                ObtePdf ob = new ObtePdf();
//                ob.eliminarPdf(acta.getCodpartit());
//                ob.obtenirArxiu(acta.getCodpartit());
//
//            } catch (Exception e) {
//
//            }
//
//            if (tasques.contains("comite")) {
//                comite = true;
//                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller mosca indicant enviar comite ");
//            }
//            if (tasques.contains("locals")) {
//                 
//                Parametres p = new Parametres();
//                p.guardaDorsalLocal(acta);
//              //  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es guarden els dorsals locals ");
//            }
//            if (tasques.contains("visitants")) {
//                Parametres p = new Parametres();
//                p.guardaDorsalvisitant(acta);
//               //  LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es guarden els dorsals visitants ");
//            }
//            if (!tasques.contains("finalitzat")) {
//                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller mosca indicant partit finalitzat, s´envien resultats");
//                try {
//                    // Enviamos resultados
//                    EnviarResultado(acta);
//                } catch (Exception e) {
//
//                }
//            }
//            
//            try {
//                //Enviamos mails
//                SendMail(acta, destino);
//            } catch (Exception e) {
//               LogSupport.grava(e);
//            }
//            try {
//                 LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller es repasa la llista de correus a enviar l´acta "+ acta.getCodpartit());
//                Parametres p = new Parametres();
//                p.enviaMail(acta.getCodpartit(), destino, comite);
//            } catch (Exception e) {
//                    LogSupport.grava(e);
//            }
//            // acta.setMessage(tasques);
//
//            // retorno = new ModelAndView("login", "message", tasques);
//            retorno = new ModelAndView("menu", "login", acta);
//
//        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO)) {
//             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode nuevo_evento");
//            GuardarEvento(acta);
//
//            acta = RecargarEventos(acta);
//
//            retorno = new ModelAndView("acta", "acta", acta);
//
//        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_ELIMINAR_EVENTO)) {
//             LogSupport.info("usuari: "+acta.getUsername()+" partit: "+acta.getCodpartit()+ " Eacta controller dintre de metode eliminar evento");
//            EliminarEvento(acta);
//
//            acta = RecargarEventos(acta);
//            retorno = new ModelAndView("acta", "acta", acta);
////		} else if(acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_GOL)) {
////			GuardarEventoGol(acta);
////			
////			acta = RecargarEventos(acta);
////			acta.setSeccion("GENEVENTOS");
////			retorno = new ModelAndView("acta", "acta", acta);
//        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_TAZUL)) {
//             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode tarja blava");
//            GuardarEventoTAzul(acta);
//
//            acta = RecargarEventos(acta);
//            acta.setSeccion("GENEVENTOS");
//            retorno = new ModelAndView("acta", "acta", acta);
//        } 
//        else if (acta.getAccion().equalsIgnoreCase(ACTION_NUEVO_EVENTO_TROJA)) {
//             LogSupport.info("usuari: "+acta.getUsername()+ " partit: "+acta.getCodpartit()+" Eacta controller dintre de metode tarja vermella");
//            GuardarEventoTRoja(acta);
//
//            acta = RecargarEventos(acta);
//            acta.setSeccion("GENEVENTOS");
//            retorno = new ModelAndView("acta", "acta", acta);
//        }
//
////        else if (acta.getAccion().equalsIgnoreCase(ACTION_MOSTRAR_EVENTS)) {
//        //            
//        //            acta = RecargarEventos (acta);
//        //            retorno = new ModelAndView("acta", "acta", acta);
//        //        }
////        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_LOCAL)) {
////            if (acta.getCapi_l1() == null) {
////                acta.setCapi_l1("");
////            }
////            if (acta.getCapi_l2() == null) {
////                acta.setCapi_l2("");
////            }
////            if (acta.getCapi_l3() == null) {
////                acta.setCapi_l3("");
////            }
////            if (acta.getCapi_l4() == null) {
////                acta.setCapi_l4("");
////            }
////            if (acta.getCapi_l5() == null) {
////                acta.setCapi_l5("");
////            }
////            if (acta.getCapi_l6() == null) {
////                acta.setCapi_l6("");
////            }
////            if (acta.getCapi_l7() == null) {
////                acta.setCapi_l7("");
////            }
////            if (acta.getCapi_l8() == null) {
////                acta.setCapi_l8("");
////            }
////            if (acta.getCapi_l9() == null) {
////                acta.setCapi_l9("");
////            }
////            if (acta.getCapi_l10() == null) {
////                acta.setCapi_l10("");
////            }
////            EactaEventHome eactaeventDAO = new EactaEventHome();
////            EactaEvent evento;
////            //TODO CARLES Sancionado en atributo7 de los jugadores
////            evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Local", "0", "00:00", "L", acta.getNum_l1(), acta.getCodlic_l1(), acta.getNom_cognoms_l1(),
////                    acta.getCat_l1(), acta.getPin_l1(), acta.getCapi_l1(), acta.getNif_l1(), acta.getSancion_l1(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Local", "0", "00:00", "L", acta.getNum_l2(), acta.getCodlic_l2(), acta.getNom_cognoms_l2(),
////                    acta.getCat_l2(), acta.getPin_l2(), acta.getCapi_l2(), acta.getNif_l2(), acta.getSancion_l2(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Local", "0", "00:00", "L", acta.getNum_l3(), acta.getCodlic_l3(), acta.getNom_cognoms_l3(),
////                    acta.getCat_l3(), acta.getPin_l3(), acta.getCapi_l3(), acta.getNif_l3(), acta.getSancion_l3(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Local", "0", "00:00", "L", acta.getNum_l4(), acta.getCodlic_l4(), acta.getNom_cognoms_l4(),
////                    acta.getCat_l4(), acta.getPin_l4(), acta.getCapi_l4(), acta.getNif_l4(), acta.getSancion_l4(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Local", "0", "00:00", "L", acta.getNum_l5(), acta.getCodlic_l5(), acta.getNom_cognoms_l5(),
////                    acta.getCat_l5(), acta.getPin_l5(), acta.getCapi_l5(), acta.getNif_l5(), acta.getSancion_l5(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Local", "0", "00:00", "L", acta.getNum_l6(), acta.getCodlic_l6(), acta.getNom_cognoms_l6(),
////                    acta.getCat_l6(), acta.getPin_l6(), acta.getCapi_l6(), acta.getNif_l6(), acta.getSancion_l6(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Local", "0", "00:00", "L", acta.getNum_l7(), acta.getCodlic_l7(), acta.getNom_cognoms_l7(),
////                    acta.getCat_l7(), acta.getPin_l7(), acta.getCapi_l7(), acta.getNif_l7(), acta.getSancion_l7(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Local", "0", "00:00", "L", acta.getNum_l8(), acta.getCodlic_l8(), acta.getNom_cognoms_l8(),
////                    acta.getCat_l8(), acta.getPin_l8(), acta.getCapi_l8(), acta.getNif_l8(), acta.getSancion_l8(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Local", "0", "00:00", "L", acta.getNum_l9(), acta.getCodlic_l9(), acta.getNom_cognoms_l9(),
////                    acta.getCat_l9(), acta.getPin_l9(), acta.getCapi_l9(), acta.getNif_l9(), acta.getSancion_l9(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Local", "0", "00:00", "L", acta.getNum_l10(), acta.getCodlic_l10(), acta.getNom_cognoms_l10(),
////                    acta.getCat_l10(), acta.getPin_l10(), acta.getCapi_l10(), acta.getNif_l10(), acta.getSancion_l10(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Local", "0", "00:00", "L", acta.getNum_l11(), acta.getCodlic_l11(), acta.getNom_cognoms_l11(),
////                    acta.getCat_l11(), acta.getPin_l11(), "", acta.getNif_l11(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Local", "0", "00:00", "L", acta.getNum_l12(), acta.getCodlic_l12(), acta.getNom_cognoms_l12(),
////                    acta.getCat_l12(), "", "", acta.getNif_l12(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Local", "0", "00:00", "L", acta.getNum_l13(), acta.getCodlic_l13(), acta.getNom_cognoms_l13(),
////                    acta.getCat_l13(), "", "", acta.getNif_l13(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Local", "0", "00:00", "L", acta.getNum_l14(), acta.getCodlic_l14(), acta.getNom_cognoms_l14(),
////                    acta.getCat_l14(), "", "", acta.getNif_l14(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Local", "0", "00:00", "L", acta.getNum_l15(), acta.getCodlic_l15(), acta.getNom_cognoms_l15(),
////                    acta.getCat_l15(), "", "", acta.getNif_l15(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Local", "0", "00:00", "L", acta.getNum_l16(), acta.getCodlic_l16(), acta.getNom_cognoms_l16(),
////                    acta.getCat_l16(), "", "", acta.getNif_l16(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Local", "0", "00:00", "L", acta.getNum_l17(), acta.getCodlic_l17(), acta.getNom_cognoms_l17(),
////                    acta.getCat_l17(), "", "", acta.getNif_l17(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////
////            acta = obtenirActa(acta, "A");
////            acta = RecargarEventos(acta);
////            acta.setSeccion("");
////            //  log.debug("es guarden els jugadors de lÂ´equip local a la base de ");
////
////            retorno = new ModelAndView("acta", "acta", acta);
//        //       } 
////else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_VISITANT)) {
////            if (acta.getCapi_v1() == null) {
////                acta.setCapi_v1("");
////            }
////            if (acta.getCapi_v2() == null) {
////                acta.setCapi_v2("");
////            }
////            if (acta.getCapi_v3() == null) {
////                acta.setCapi_v3("");
////            }
////            if (acta.getCapi_v4() == null) {
////                acta.setCapi_v4("");
////            }
////            if (acta.getCapi_v5() == null) {
////                acta.setCapi_v5("");
////            }
////            if (acta.getCapi_v6() == null) {
////                acta.setCapi_v6("");
////            }
////            if (acta.getCapi_v7() == null) {
////                acta.setCapi_v7("");
////            }
////            if (acta.getCapi_v8() == null) {
////                acta.setCapi_v8("");
////            }
////            if (acta.getCapi_v9() == null) {
////                acta.setCapi_v9("");
////            }
////            if (acta.getCapi_v10() == null) {
////                acta.setCapi_v10("");
////            }
////            EactaEventHome eactaeventDAO = new EactaEventHome();
////            EactaEvent evento;
////            evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Visitant", "0", "00:00", "V", acta.getNum_v1(), acta.getCodlic_v1(), acta.getNom_cognoms_v1(),
////                    acta.getCat_v1(), acta.getPin_v1(), acta.getCapi_v1(), acta.getNif_v1(), acta.getSancion_v1(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Visitant", "0", "00:00", "V", acta.getNum_v2(), acta.getCodlic_v2(), acta.getNom_cognoms_v2(),
////                    acta.getCat_v2(), acta.getPin_v2(), acta.getCapi_v2(), acta.getNif_v2(), acta.getSancion_v2(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Visitant", "0", "00:00", "V", acta.getNum_v3(), acta.getCodlic_v3(), acta.getNom_cognoms_v3(),
////                    acta.getCat_v3(), acta.getPin_v3(), acta.getCapi_v3(), acta.getNif_v3(), acta.getSancion_v3(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Visitant", "0", "00:00", "V", acta.getNum_v4(), acta.getCodlic_v4(), acta.getNom_cognoms_v4(),
////                    acta.getCat_v4(), acta.getPin_v4(), acta.getCapi_v4(), acta.getNif_v4(), acta.getSancion_v4(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Visitant", "0", "00:00", "V", acta.getNum_v5(), acta.getCodlic_v5(), acta.getNom_cognoms_v5(),
////                    acta.getCat_v5(), acta.getPin_v5(), acta.getCapi_v5(), acta.getNif_v5(), acta.getSancion_v5(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Visitant", "0", "00:00", "V", acta.getNum_v6(), acta.getCodlic_v6(), acta.getNom_cognoms_v6(),
////                    acta.getCat_v6(), acta.getPin_v6(), acta.getCapi_v6(), acta.getNif_v6(), acta.getSancion_v6(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Visitant", "0", "00:00", "V", acta.getNum_v7(), acta.getCodlic_v7(), acta.getNom_cognoms_v7(),
////                    acta.getCat_v7(), acta.getPin_v7(), acta.getCapi_v7(), acta.getNif_v7(), acta.getSancion_v7(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Visitant", "0", "00:00", "V", acta.getNum_v8(), acta.getCodlic_v8(), acta.getNom_cognoms_v8(),
////                    acta.getCat_v8(), acta.getPin_v8(), acta.getCapi_v8(), acta.getNif_v8(), acta.getSancion_v8(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Visitant", "0", "00:00", "V", acta.getNum_v9(), acta.getCodlic_v9(), acta.getNom_cognoms_v9(),
////                    acta.getCat_v9(), acta.getPin_v9(), acta.getCapi_v9(), acta.getNif_v9(), acta.getSancion_v9(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Visitant", "0", "00:00", "V", acta.getNum_v10(), acta.getCodlic_v10(), acta.getNom_cognoms_v10(),
////                    acta.getCat_v10(), acta.getPin_v10(), acta.getCapi_v10(), acta.getNif_v10(), acta.getSancion_v10(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Visitant", "0", "00:00", "V", acta.getNum_v11(), acta.getCodlic_v11(), acta.getNom_cognoms_v11(),
////                    acta.getCat_v11(), acta.getPin_v11(), "", acta.getNif_v11(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Visitant", "0", "00:00", "V", acta.getNum_v12(), acta.getCodlic_v12(), acta.getNom_cognoms_v12(),
////                    acta.getCat_v12(), "", "", acta.getNif_v12(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Visitant", "0", "00:00", "V", acta.getNum_v13(), acta.getCodlic_v13(), acta.getNom_cognoms_v13(),
////                    acta.getCat_v13(), "", "", acta.getNif_v13(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Visitant", "0", "00:00", "V", acta.getNum_v14(), acta.getCodlic_v14(), acta.getNom_cognoms_v14(),
////                    acta.getCat_v14(), "", "", acta.getNif_v14(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Visitant", "0", "00:00", "V", acta.getNum_v15(), acta.getCodlic_v15(), acta.getNom_cognoms_v15(),
////                    acta.getCat_v15(), "", "", acta.getNif_v15(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Visitant", "0", "00:00", "V", acta.getNum_v16(), acta.getCodlic_v16(), acta.getNom_cognoms_v16(),
////                    acta.getCat_v16(), "", "", acta.getNif_v16(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Visitant", "0", "00:00", "V", acta.getNum_v17(), acta.getCodlic_v17(), acta.getNom_cognoms_v17(),
////                    acta.getCat_v17(), "", "", acta.getNif_v17(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////
////            acta = obtenirActa(acta, "A");
////            acta = RecargarEventos(acta);
////            acta.setSeccion("");
////
////            //    log.debug("es guarden els jugadors de lÂ´equip visitant a la base de dades");
////            retorno = new ModelAndView("acta", "acta", acta);
////        } 
////else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_OBSERVACIONS)) {
////            EactaEventHome eactaeventDAO = new EactaEventHome();
////            EactaEvent evento;
////            //Observaciones
////            String observaciones = acta.getObservacions();
////            observaciones = observaciones.replace("'", "");
////            observaciones = observaciones.replace("\"", "");
////            evento = new EactaEvent(acta.getCodpartit(), 150, "Observacions Ã€rbitre", "0", "00:00", "L", 0, observaciones, "", "", "", "", "", "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////
////            acta = obtenirActa(acta, "A");
////
////            acta.setSeccion("");
////
////            retorno = new ModelAndView("acta", "acta", acta);
////
////        } //        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_LLISTA_EVENTS)) {
//        //            EactaEventHome eactaeventDAO = new EactaEventHome();
//        //            EactaEvent evento;
//        //
//        //            //Eventos del partido
//        //            //Globales
//        //            evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "L", 0, acta.getTotalfaltasl(), "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "V", 0, acta.getTotalfaltasv(), "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        //
//        //            //Obtenemos todos los eventos de tipo gol y los eliminamos
//        //            evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "", "", "", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.eliminarEactaeventGol(evento);
//        //
//        //            evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "L", 0, acta.getTotalgolesl(), "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "V", 0, acta.getTotalgolesv(), "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        //
//        //            //Generamos los nuevos goles en funcion de lo que hay en el grid
//        //            String aux = acta.getCgolespl();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            String[] parte = aux.split(",");
//        //            aux = acta.getCgolesml();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            String[] crono = aux.split(",");
//        //            aux = acta.getCgolesdl();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            String[] dorsal = aux.split(",");
//        //            Long xdorsal;
//        //            String xparte;
//        //            String xcrono;
//        //            if (isNumeric(acta.getTotalgolesl())) {
//        //                //Goles existentes	
//        //                if (!parte[0].equals("")) {
//        //                    for (int i = 0; i < parte.length; i++) {
//        //                        if (isNumeric(dorsal[i].trim())) {
//        //                            xdorsal = Long.parseLong(dorsal[i].trim());
//        //                        } else {
//        //                            xdorsal = Long.parseLong("0");
//        //                        }
//        //                        if (parte[i].equals("")) {
//        //                            xparte = "1";
//        //                        } else {
//        //                            xparte = parte[i].trim();
//        //                        }
//        //                        if (crono[i].equals("")) {
//        //                            xcrono = "00:00";
//        //                        } else {
//        //                            xcrono = crono[i].trim();
//        //                        }
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "L", xdorsal, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                    //Goles aÃ±adidos
//        //                    for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                } else { //Goles nuevos, no habia
//        //                    for (int i = 0; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                }
//        //            }
//        //            aux = acta.getCgolespv();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            parte = aux.split(",");
//        //            aux = acta.getCgolesmv();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            crono = aux.split(",");
//        //            aux = acta.getCgolesdv();
//        //            aux = aux.replaceAll("\\[", "");
//        //            aux = aux.replaceAll("\\]", "");
//        //            dorsal = aux.split(",");
//        //            if (isNumeric(acta.getTotalgolesv())) {
//        //                //Goles existentes	
//        //                if (!parte[0].equals("")) {
//        //                    for (int i = 0; i < parte.length; i++) {
//        //                        if (isNumeric(dorsal[i].trim())) {
//        //                            xdorsal = Long.parseLong(dorsal[i].trim());
//        //                        } else {
//        //                            xdorsal = Long.parseLong("0");
//        //                        }
//        //                        if (parte[i].equals("")) {
//        //                            xparte = "1";
//        //                        } else {
//        //                            xparte = parte[i].trim();
//        //                        }
//        //                        if (crono[i].equals("")) {
//        //                            xcrono = "00:00";
//        //                        } else {
//        //                            xcrono = crono[i].trim();
//        //                        }
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "V", xdorsal, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                    //Goles aÃ±adidos
//        //                    for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                } else { //Goles nuevos
//        //                    for (int i = 0; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
//        //                        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
//        //                        eactaeventDAO.crearEactaEvent(evento);
//        //                    }
//        //                }
//        //            }
//        //
//        //            evento = new EactaEvent(acta.getCodpartit(), 101, "Servei Inicial", "1", "00:00", acta.getSaqueinicial(), 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaevent(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 100, "Hora Inici Real", "1", acta.getHorainicio(), "L", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertol1(), "L", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertol2(), "L", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertov1(), "V", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        //            evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertov2(), "V", 0, "", "", "", "", "", "", "", "", "");
//        //            eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        //            
//        //            acta = RecargarEventos(acta);
//        //            
//        //            acta = obtenirActa(acta, "A");         
//        //            
//        //            
//        //            retorno = new ModelAndView("acta", "acta", acta);
//        //
//        //        } 
////        else if (acta.getAccion().equalsIgnoreCase(ACTION_TORNAR_CAPCALERA)) {
////            //GUARDAMOS ACTA QUE YA EXISTE A BASE DE ACTUALIZAR LOS EVENTOS
////            //Cabecera
////            EactaEventHome eactaeventDAO = new EactaEventHome();
////            EactaEvent evento = new EactaEvent(acta.getCodpartit(), 23, "Delegat de pista", "0", "00:00", "L", 0, acta.getCodlic_dp(), acta.getNom_cognoms_dp(), "", acta.getPin_dp(), "", acta.getNif_dp(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 22, "Cronometrador", "0", "00:00", "L", 0, acta.getCodlic_cr(), acta.getNom_cognoms_cr(), "", "", "", acta.getNif_cr(), "", "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 20, "Ã€rbitre Principal", "0", "00:00", "L", 0, acta.getCodlic_ar1(), acta.getNom_cognoms_ar1(), acta.getCat_ar1(), acta.getPin_ar1(), acta.getEmail_ar1(), acta.getNif_ar1(), acta.getTerritorial_ar1(), acta.getTelefono_ar1(), "");
////            eactaeventDAO.actualizarEactaevent(evento);
////            evento = new EactaEvent(acta.getCodpartit(), 21, "Ã€rbitre Auxiliar", "0", "00:00", "L", 0, acta.getCodlic_ar2(), acta.getNom_cognoms_ar2(), "", acta.getPin_ar2(), "", acta.getNif_ar2(), acta.getTerritorial_ar2(), "", "");
////            eactaeventDAO.actualizarEactaevent(evento);
////
////            acta = RecargarEventos(acta);
////
////            acta = obtenirActa(acta, "A");
////            acta.setSeccion("");
////
////            retorno = new ModelAndView("acta", "acta", acta);
////
////        }
//        return retorno;
//
//    }
//
//    public Acta obtenirActa(Acta acta, String estat) {
//
//        //Recuperamos los datos del acta para las selecciones
//        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
//        listadatosacta = recuperarDatosActa(acta.getCodpartit());
//
//        //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
//        ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
//        listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
//        listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
//        //codcate=33 -> Ã rbitres
//        //codcate=37 -> delegat
//        //codcate=56 -> auxiliar
//        //codcate=39 -> tÃ¨cnic ... la resta sÃ³n jugadors i porters 
//        listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
//        listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
//        listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
//        listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
//        listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
//        listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
//        listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
//        listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
//        listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);
//
//        acta.setDatosactalocal(listajugadoresseleclocal);
//        acta.setDatosactavisit(listajugadoresselecvisit);
//        acta.setDatosactaTlocal(listatecnicoslocal);
//        acta.setDatosactaTvisit(listatecnicosvisit);
//        acta.setDatosactaTfede(listatecnicosfede);
//        acta.setDatosactaDlocal(listadelegadoslocal);
//        acta.setDatosactaDvisit(listadelegadosvisit);
//        acta.setDatosactaDfede(listadelegadosfede);
//        acta.setDatosactaAlocal(listaauxiliareslocal);
//        acta.setDatosactaAvisit(listaauxiliaresvisit);
//        acta.setDatosactaArbitro(listaarbitros);
//
//        return acta;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static Acta recuperarActa(long codpartit, long codtecnic, long codtecnic2) {
//        //Nos traemos los datos del acta
//        EactaEventHome eactaeventDAO = new EactaEventHome();
//        List<Object[]> resultados_consulta = eactaeventDAO.eventosPorPartido(codpartit);
//
//        String evento = "";
//        Acta acta = new Acta();
//        acta.setCodpartit(codpartit);
//        long dorsal = (long) 0;
//        String atributo1 = "";
//        String atributo2 = "";
//        String atributo3 = "";
//        String atributo4 = "";
//        String atributo5 = "";
//        String atributo6 = "";
//        String atributo7 = "";
//        String atributo8 = "";
//        String atributo9 = "";
//
//        if (resultados_consulta.size() > 0) {
//
//            for (Object[] object : resultados_consulta) {
//                evento = object[3].toString();
//                dorsal = 0;
//                atributo1 = "";
//                atributo2 = "";
//                atributo3 = "";
//                atributo4 = "";
//                atributo5 = "";
//                atributo6 = "";
//                atributo7 = "";
//                atributo8 = "";
//                atributo9 = "";
//                if (object[7] != null) {
//                    dorsal = Long.parseLong(object[7].toString());
//                }
//                if (object[8] != null) {
//                    atributo1 = object[8].toString();
//                }
//                if (object[9] != null) {
//                    atributo2 = object[9].toString();
//                }
//                if (object[10] != null) {
//                    atributo3 = object[10].toString();
//                }
//                if (object[11] != null) {
//                    atributo4 = object[11].toString();
//                }
//                if (object[12] != null) {
//                    atributo5 = object[12].toString();
//                }
//                if (object[13] != null) {
//                    atributo6 = object[13].toString();
//                }
//                if (object[14] != null) {
//                    atributo7 = object[14].toString();
//                }
//                if (object[15] != null) {
//                    atributo8 = object[15].toString();
//                }
//                if (object[16] != null) {
//                    atributo9 = object[16].toString();
//                }
//
//                if (evento.equals("Codi de Partit")) {
//                    acta.setConfig(atributo2);				//atribut2
//                }
//                if (evento.equals("Codi Temporada")) {
//                    acta.setCodtemp(atributo1); 			//atribut1
//                    acta.setTemporada(atributo2); 			//atribut2
//                }
//                if (evento.equals("Modalitat")) {
//                    acta.setModalitat(atributo1); 				//atribut1
//                }
//                if (evento.equals("Jornada")) {
//                    acta.setJornada(Long.parseLong(atributo1)); 	//atribut1
//                }
//                if (evento.equals("Poblacio de Pista")) {
//                    acta.setCodipostaljoc(atributo1); 		//atribut1
//                    acta.setPoblaciojoc(atributo2); 		//atribut2
//                }
//                if (evento.equals("Adressa de Pista")) {
//                    acta.setAdressajoc(atributo1); 		//atribut1
//                }
//                if (evento.equals("Moment de Joc")) {
//                    acta.setDatajoc(atributo1); 			//atribut1
//                    acta.setHorajoc(atributo2); 			//atribut2
//                }
//                if (evento.equals("CompeticiÃ³")) {
//                    acta.setCompeticio(atributo1); 		//atribut1	
//                    acta.setNomcompeticio(atributo2); 	//atribut2
//                }
//                if (evento.equals("Finalitzacio")) {
//                    acta.setFinalitzat(atributo1);	//atribut1
//                }
//                if (evento.equals("Ã€rbitre Principal")) {
//                    acta.setCodlic_ar1(atributo1);
//                    acta.setNom_cognoms_ar1(atributo2);
//                    acta.setCat_ar1(atributo3);
//                    acta.setPin_ar1(atributo4);
//                    acta.setEmail_ar1(atributo5);
//                    acta.setNif_ar1(atributo6);
//                    acta.setTerritorial_ar1(atributo7);
//                    acta.setTelefono_ar1(atributo8);
//                    acta.setCodtecnic(codtecnic);			//codtecnic del partit
//
//                }
//                if (evento.equals("Ã€rbitre Auxiliar")) {
//                    acta.setCodlic_ar2(atributo1);
//                    acta.setNom_cognoms_ar2(atributo2);
//                    acta.setPin_ar2(atributo4);
//                    acta.setNif_ar2(atributo6);
//                    acta.setTerritorial_ar2(atributo7);
//                    acta.setCodtecnic2(codtecnic2);
//                }
//                if (evento.equals("Cronometrador")) {
//                    acta.setCodlic_cr(atributo1);
//                    acta.setNom_cognoms_cr(atributo2);
//                    acta.setNif_cr(atributo6);
//                }
//                if (evento.equals("Delegat de pista")) {
//                    acta.setCodlic_dp(atributo1);
//                    acta.setNom_cognoms_dp(atributo2);
//                    acta.setPin_dp(atributo4);
//                    acta.setNif_dp(atributo6);
//                }
//                if (evento.equals("Equip Local")) {
//                    acta.setCodequiplocal(Long.parseLong(atributo1));				//atribut1
//                    acta.setNomequiplocal(atributo2); 								//atribut2
//                    acta.setCodentitatlocal(Long.parseLong(atributo3)); 			//atribut3
//                    acta.setCodcategorialocal(atributo4); 							//atribut4
//                    acta.setCodclasselocal(Long.parseLong(atributo5)); 				//atribut5  
//                    String clase = obtenerClase(acta.getCodclasselocal());
//                    acta.setClasselocal(clase);
//                    acta.setPin_clubl(atributo6);									//atribut6
//                    acta.setMailequiplocal(atributo7);
//                }
//                //Cargamos jugadores de los eventos
//                if (evento.equals("Porter1 Local")) {
//                    acta.setCodlic_l1(atributo1);
//                    acta.setNom_cognoms_l1(atributo2);
//                    acta.setCat_l1(atributo3);
//                    acta.setPin_l1(atributo4);
//                    acta.setCapi_l1(atributo5);
//                    acta.setNum_l1(dorsal);
//                    acta.setNif_l1(atributo6);
//                    acta.setSancion_l1(atributo7);
//                }
//                if (evento.equals("Jugador1 Local")) {
//                    acta.setCodlic_l2(atributo1);
//                    acta.setNom_cognoms_l2(atributo2);
//                    acta.setCat_l2(atributo3);
//                    acta.setPin_l2(atributo4);
//                    acta.setCapi_l2(atributo5);
//                    acta.setNum_l2(dorsal);
//                    acta.setNif_l2(atributo6);
//                    acta.setSancion_l2(atributo7);
//                }
//                if (evento.equals("Jugador2 Local")) {
//                    acta.setCodlic_l3(atributo1);
//                    acta.setNom_cognoms_l3(atributo2);
//                    acta.setCat_l3(atributo3);
//                    acta.setPin_l3(atributo4);
//                    acta.setCapi_l3(atributo5);
//                    acta.setNum_l3(dorsal);
//                    acta.setNif_l3(atributo6);
//                    acta.setSancion_l3(atributo7);
//                }
//                if (evento.equals("Jugador3 Local")) {
//                    acta.setCodlic_l4(atributo1);
//                    acta.setNom_cognoms_l4(atributo2);
//                    acta.setCat_l4(atributo3);
//                    acta.setPin_l4(atributo4);
//                    acta.setCapi_l4(atributo5);
//                    acta.setNum_l4(dorsal);
//                    acta.setNif_l4(atributo6);
//                    acta.setSancion_l4(atributo7);
//                }
//                if (evento.equals("Jugador4 Local")) {
//                    acta.setCodlic_l5(atributo1);
//                    acta.setNom_cognoms_l5(atributo2);
//                    acta.setCat_l5(atributo3);
//                    acta.setPin_l5(atributo4);
//                    acta.setCapi_l5(atributo5);
//                    acta.setNum_l5(dorsal);
//                    acta.setNif_l5(atributo6);
//                    acta.setSancion_l5(atributo7);
//                }
//                if (evento.equals("Jugador5 Local")) {
//                    acta.setCodlic_l6(atributo1);
//                    acta.setNom_cognoms_l6(atributo2);
//                    acta.setCat_l6(atributo3);
//                    acta.setPin_l6(atributo4);
//                    acta.setCapi_l6(atributo5);
//                    acta.setNum_l6(dorsal);
//                    acta.setNif_l6(atributo6);
//                    acta.setSancion_l6(atributo7);
//                }
//                if (evento.equals("Jugador6 Local")) {
//                    acta.setCodlic_l7(atributo1);
//                    acta.setNom_cognoms_l7(atributo2);
//                    acta.setCat_l7(atributo3);
//                    acta.setPin_l7(atributo4);
//                    acta.setCapi_l7(atributo5);
//                    acta.setNum_l7(dorsal);
//                    acta.setNif_l7(atributo6);
//                    acta.setSancion_l7(atributo7);
//                }
//                if (evento.equals("Jugador7 Local")) {
//                    acta.setCodlic_l8(atributo1);
//                    acta.setNom_cognoms_l8(atributo2);
//                    acta.setCat_l8(atributo3);
//                    acta.setPin_l8(atributo4);
//                    acta.setCapi_l8(atributo5);
//                    acta.setNum_l8(dorsal);
//                    acta.setNif_l8(atributo6);
//                    acta.setSancion_l8(atributo7);
//                }
//                if (evento.equals("Jugador8 Local")) {
//                    acta.setCodlic_l9(atributo1);
//                    acta.setNom_cognoms_l9(atributo2);
//                    acta.setCat_l9(atributo3);
//                    acta.setPin_l9(atributo4);
//                    acta.setCapi_l9(atributo5);
//                    acta.setNum_l9(dorsal);
//                    acta.setNif_l9(atributo6);
//                    acta.setSancion_l9(atributo7);
//                }
//                if (evento.equals("Porter2 Local")) {
//                    acta.setCodlic_l10(atributo1);
//                    acta.setNom_cognoms_l10(atributo2);
//                    acta.setCat_l10(atributo3);
//                    acta.setPin_l10(atributo4);
//                    acta.setCapi_l10(atributo5);
//                    acta.setNum_l10(dorsal);
//                    acta.setNif_l10(atributo6);
//                    acta.setSancion_l10(atributo7);
//                }
//                if (evento.equals("Tecnic Local")) {
//                    acta.setCodlic_l11(atributo1);
//                    acta.setNom_cognoms_l11(atributo2);
//                    acta.setCat_l11(atributo3);
//                    acta.setPin_l11(atributo4);
//                    acta.setPin_el(atributo4);
//                    acta.setNif_l11(atributo6);
//                    acta.setNum_l11(dorsal);
//                }
//                if (evento.equals("Delegat Local")) {
//                    acta.setCodlic_l12(atributo1);
//                    acta.setNom_cognoms_l12(atributo2);
//                    acta.setCat_l12(atributo3);
//                    acta.setNif_l12(atributo6);
//                    acta.setNum_l12(dorsal);
//                }
//                if (evento.equals("Auxiliar Local")) {
//                    acta.setCodlic_l13(atributo1);
//                    acta.setNom_cognoms_l13(atributo2);
//                    acta.setCat_l13(atributo3);
//                    acta.setNif_l13(atributo6);
//                    acta.setNum_l13(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar2 Local")) {
//                    acta.setCodlic_l14(atributo1);
//                    acta.setNom_cognoms_l14(atributo2);
//                    acta.setCat_l14(atributo3);
//                    acta.setNif_l14(atributo6);
//                    acta.setNum_l14(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar3 Local")) {
//                    acta.setCodlic_l15(atributo1);
//                    acta.setNom_cognoms_l15(atributo2);
//                    acta.setCat_l15(atributo3);
//                    acta.setNif_l15(atributo6);
//                    acta.setNum_l15(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar4 Local")) {
//                    acta.setCodlic_l16(atributo1);
//                    acta.setNom_cognoms_l16(atributo2);
//                    acta.setCat_l16(atributo3);
//                    acta.setNif_l16(atributo6);
//                    acta.setNum_l16(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar5 Local")) {
//                    acta.setCodlic_l17(atributo1);
//                    acta.setNom_cognoms_l17(atributo2);
//                    acta.setCat_l17(atributo3);
//                    acta.setNif_l17(atributo6);
//                    acta.setNum_l17(dorsal);
//                }
//                if (evento.equals("Equip Visitant")) {
//                    acta.setCodequipvisit(Long.parseLong(atributo1));				//atribut1
//                    acta.setNomequipvisit(atributo2); 								//atribut2
//                    acta.setCodentitatvisit(Long.parseLong(atributo3)); 			//atribut3
//                    acta.setCodcategoriavisit(atributo4); 							//atribut4
//                    acta.setCodclassevisit(Long.parseLong(atributo5));				//atribut5
//                    String clase = obtenerClase(acta.getCodclassevisit());
//                    acta.setClassevisit(clase);
//                    acta.setPin_clubv(atributo6);									//atribut6
//                    acta.setMailequipvisit(atributo7);
//                }
//                if (evento.equals("Porter1 Visitant")) {
//                    acta.setCodlic_v1(atributo1);
//                    acta.setNom_cognoms_v1(atributo2);
//                    acta.setCat_v1(atributo3);
//                    acta.setPin_v1(atributo4);
//                    acta.setCapi_v1(atributo5);
//                    acta.setNum_v1(dorsal);
//                    acta.setNif_v1(atributo6);
//                    acta.setSancion_v1(atributo7);
//                }
//                if (evento.equals("Jugador1 Visitant")) {
//                    acta.setCodlic_v2(atributo1);
//                    acta.setNom_cognoms_v2(atributo2);
//                    acta.setCat_v2(atributo3);
//                    acta.setPin_v2(atributo4);
//                    acta.setCapi_v2(atributo5);
//                    acta.setNum_v2(dorsal);
//                    acta.setNif_v2(atributo6);
//                    acta.setSancion_v2(atributo7);
//                }
//                if (evento.equals("Jugador2 Visitant")) {
//                    acta.setCodlic_v3(atributo1);
//                    acta.setNom_cognoms_v3(atributo2);
//                    acta.setCat_v3(atributo3);
//                    acta.setPin_v3(atributo4);
//                    acta.setCapi_v3(atributo5);
//                    acta.setNum_v3(dorsal);
//                    acta.setNif_v3(atributo6);
//                    acta.setSancion_v3(atributo7);
//                }
//                if (evento.equals("Jugador3 Visitant")) {
//                    acta.setCodlic_v4(atributo1);
//                    acta.setNom_cognoms_v4(atributo2);
//                    acta.setCat_v4(atributo3);
//                    acta.setPin_v4(atributo4);
//                    acta.setCapi_v4(atributo5);
//                    acta.setNum_v4(dorsal);
//                    acta.setNif_v4(atributo6);
//                    acta.setSancion_v4(atributo7);
//                }
//                if (evento.equals("Jugador4 Visitant")) {
//                    acta.setCodlic_v5(atributo1);
//                    acta.setNom_cognoms_v5(atributo2);
//                    acta.setCat_v5(atributo3);
//                    acta.setPin_v5(atributo4);
//                    acta.setCapi_v5(atributo5);
//                    acta.setNum_v5(dorsal);
//                    acta.setNif_v5(atributo6);
//                    acta.setSancion_v5(atributo7);
//                }
//                if (evento.equals("Jugador5 Visitant")) {
//                    acta.setCodlic_v6(atributo1);
//                    acta.setNom_cognoms_v6(atributo2);
//                    acta.setCat_v6(atributo3);
//                    acta.setPin_v6(atributo4);
//                    acta.setCapi_v6(atributo5);
//                    acta.setNum_v6(dorsal);
//                    acta.setNif_v6(atributo6);
//                    acta.setSancion_v6(atributo7);
//                }
//                if (evento.equals("Jugador6 Visitant")) {
//                    acta.setCodlic_v7(atributo1);
//                    acta.setNom_cognoms_v7(atributo2);
//                    acta.setCat_v7(atributo3);
//                    acta.setPin_v7(atributo4);
//                    acta.setCapi_v7(atributo5);
//                    acta.setNum_v7(dorsal);
//                    acta.setNif_v7(atributo6);
//                    acta.setSancion_v7(atributo7);
//                }
//                if (evento.equals("Jugador7 Visitant")) {
//                    acta.setCodlic_v8(atributo1);
//                    acta.setNom_cognoms_v8(atributo2);
//                    acta.setCat_v8(atributo3);
//                    acta.setPin_v8(atributo4);
//                    acta.setCapi_v8(atributo5);
//                    acta.setNum_v8(dorsal);
//                    acta.setNif_v8(atributo6);
//                    acta.setSancion_v8(atributo7);
//                }
//                if (evento.equals("Jugador8 Visitant")) {
//                    acta.setCodlic_v9(atributo1);
//                    acta.setNom_cognoms_v9(atributo2);
//                    acta.setCat_v9(atributo3);
//                    acta.setPin_v9(atributo4);
//                    acta.setCapi_v9(atributo5);
//                    acta.setNum_v9(dorsal);
//                    acta.setNif_v9(atributo6);
//                    acta.setSancion_v9(atributo7);
//                }
//                if (evento.equals("Porter2 Visitant")) {
//                    acta.setCodlic_v10(atributo1);
//                    acta.setNom_cognoms_v10(atributo2);
//                    acta.setCat_v10(atributo3);
//                    acta.setPin_v10(atributo4);
//                    acta.setCapi_v10(atributo5);
//                    acta.setNum_v10(dorsal);
//                    acta.setNif_v10(atributo6);
//                    acta.setSancion_v10(atributo7);
//                }
//                if (evento.equals("Tecnic Visitant")) {
//                    acta.setCodlic_v11(atributo1);
//                    acta.setNom_cognoms_v11(atributo2);
//                    acta.setCat_v11(atributo3);
//                    acta.setPin_v11(atributo4);
//                    acta.setPin_ev(atributo4);
//                    acta.setNif_v11(atributo6);
//                    acta.setNum_v11(dorsal);
//                }
//                if (evento.equals("Delegat Visitant")) {
//                    acta.setCodlic_v12(atributo1);
//                    acta.setNom_cognoms_v12(atributo2);
//                    acta.setCat_v12(atributo3);
//                    acta.setNif_v12(atributo6);
//                    acta.setNum_v12(dorsal);
//                }
//                if (evento.equals("Auxiliar Visitant")) {
//                    acta.setCodlic_v13(atributo1);
//                    acta.setNom_cognoms_v13(atributo2);
//                    acta.setCat_v13(atributo3);
//                    acta.setNif_v13(atributo6);
//                    acta.setNum_v13(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar2 Visitant")) {
//                    acta.setCodlic_v14(atributo1);
//                    acta.setNom_cognoms_v14(atributo2);
//                    acta.setCat_v14(atributo3);
//                    acta.setNif_v14(atributo6);
//                    acta.setNum_v14(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar3 Visitant")) {
//                    acta.setCodlic_v15(atributo1);
//                    acta.setNom_cognoms_v15(atributo2);
//                    acta.setCat_v15(atributo3);
//                    acta.setNif_v15(atributo6);
//                    acta.setNum_v15(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar4 Visitant")) {
//                    acta.setCodlic_v16(atributo1);
//                    acta.setNom_cognoms_v16(atributo2);
//                    acta.setCat_v16(atributo3);
//                    acta.setNif_v16(atributo6);
//                    acta.setNum_v16(dorsal);
//                }
//                if (evento.equals("Delegat-Auxiliar5 Visitant")) {
//                    acta.setCodlic_v17(atributo1);
//                    acta.setNom_cognoms_v17(atributo2);
//                    acta.setCat_v17(atributo3);
//                    acta.setNif_v17(atributo6);
//                    acta.setNum_v17(dorsal);
//                }
//                //TODO CARLES En jugadores atributo4 deberia ir el pin
//                if (evento.equals("PreSignatura Delegat Pista")) {
//                    acta.setFirma1_dp(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma1_dp_actualizar("1");
//                    } else {
//                        acta.setFirma1_dp_actualizar("0");
//                    }
//                }
//                if (evento.equals("PreSignatura Capita Local")) {
//                    acta.setFirma1_cl(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma1_cl_actualizar("1");
//                    } else {
//                        acta.setFirma1_cl_actualizar("0");
//                    }
//                }
//                if (evento.equals("PreSignatura TÃ¨cnic Local")) {
//                    acta.setFirma1_el(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma1_el_actualizar("1");
//                    } else {
//                        acta.setFirma1_el_actualizar("0");
//                    }
//                }
//                if (evento.equals("PreSignatura Capita Visitant")) {
//                    acta.setFirma1_cv(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma1_cv_actualizar("1");
//                    } else {
//                        acta.setFirma1_cv_actualizar("0");
//                    }
//                }
//                if (evento.equals("PreSignatura TÃ¨cnic Visitant")) {
//                    acta.setFirma1_ev(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma1_ev_actualizar("1");
//                    } else {
//                        acta.setFirma1_ev_actualizar("0");
//                    }
//                }
//
//                if (evento.equals("Observacions Ã€rbitre")) {
//                    acta.setObservacions(atributo1);
//                }
//
//                if (evento.equals("Signatura Capita Local")) {
//                    acta.setFirma2_cl(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma2_cl_actualizar("1");
//                    } else {
//                        acta.setFirma2_cl_actualizar("0");
//                    }
//                }
//                if (evento.equals("Signatura Ã€rbitre1")) {
//                    acta.setFirma2_ar(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma2_ar_actualizar("1");
//                    } else {
//                        acta.setFirma2_ar_actualizar("0");
//                    }
//                }
//                if (evento.equals("Signatura Ã€rbitre2")) {
//                    acta.setFirma2_ar2(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma2_ar2_actualizar("1");
//                    } else {
//                        acta.setFirma2_ar2_actualizar("0");
//                    }
//                }
//                if (evento.equals("Signatura CapitÃ  Visitant")) {
//                    acta.setFirma2_cv(atributo4);
//                    if (atributo4.equals("")) {
//                        acta.setFirma2_cv_actualizar("1");
//                    } else {
//                        acta.setFirma2_cv_actualizar("0");
//                    }
//                }
//                if (evento.equals("Estat")) {
//                    acta.setEstado(atributo1);
//                }
//            }
//
//            acta = RecargarEventos(acta);
//        }
//        return acta;
//    }
//
//    public void eliminarActa(long codpartit) {
//        //Nos traemos los datos del acta
//        EactaEventHome eactaeventDAO = new EactaEventHome();
//        eactaeventDAO.eliminarEacta(codpartit);
//    }
//
//    @SuppressWarnings("unchecked")
//    public ArrayList<DatosActa> recuperarDatosActa(long codpartit) {
//        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
//        DatosActa datosacta = null;
//        datosacta = new DatosActa();
//
//        //Traemos los datos de los jugadores/tecnicos/arbitros
//        Eacta_vw_partitHome partitDAO = new Eacta_vw_partitHome();
//        List<Object[]> resultados_consulta = partitDAO.jugadoresPorPartido(codpartit);
//        if (resultados_consulta.size() > 0) {
//            for (Object[] object : resultados_consulta) {
//                datosacta = new DatosActa();
//                //Datos jugadores
//                if (object[0] != null) {
//                    datosacta.setCodlic(object[0].toString());						//codlic
//                }
//                if (object[1] != null) {
//                    datosacta.setNom(object[1].toString());							//nom
//                }
//                if (object[2] != null) {
//                    datosacta.setSubmodalitat(object[3].toString());					//submodalitat
//                }
//                if (object[4] != null) {
//                    datosacta.setCodenti(Long.parseLong(object[4].toString()));		//codenti
//                }
//                if (object[5] != null) {
//                    datosacta.setCodcate(Long.parseLong(object[5].toString()));		//codcate
//                }
//                if (object[6] != null) {
//                    datosacta.setClasse(Long.parseLong(object[6].toString()));		//classe
//                }
//                if (object[7] != null) {
//                    datosacta.setCodequip(Long.parseLong(object[7].toString()));		//codeequip
//                }
//                if (object[8] != null) {
//                    datosacta.setCodequip2(Long.parseLong(object[8].toString()));	//codequip2
//                }
//                if (object[9] != null) {
//                    datosacta.setCodequip3(Long.parseLong(object[9].toString()));	//codequip3
//                }
//                if (object[10] != null) {
//                    datosacta.setDorsal(object[10].toString());						//dorsal
//                }
//                if (object[11] != null) {
//                    datosacta.setCategoria(object[11].toString());					//eactanomcate					
//                }
//                if (object[12] != null) {
//                    datosacta.setPin(object[12].toString());						//pin
//                }
//                if (object[13] != null) {
//                    datosacta.setNif(object[13].toString());						//nif
//                }
//                if (object[14] != null) {
//                    datosacta.setSancionat(Long.parseLong(object[14].toString()));	//sancionat
//                }
//                listadatosacta.add(datosacta);
//            }
//        }
//
//        return listadatosacta;
//    }
//
////	public String abreviarCategoria(long codcate, String tipo) {
////		String resultado = ""; 
////		if (codcate == 1) resultado = "PI"; 	//PB-IniciaciÃ³
////		if (codcate == 2) resultado = "PB"; 	//PrebenjamÃ­
////		if (codcate == 3) resultado = "BJ"; 		//BenjamÃ­
////		if (codcate == 4) resultado = "AL"; 		//AlevÃ­
////		if (codcate == 5) resultado = "IN"; 		//Infantil
////		if (codcate == 6) resultado = "JV"; 	//Juvenil
////		if (codcate == 7) resultado = "JN"; 	//JÃºnior
////		if (codcate == 9) resultado = "2C"; 	//2CAT
////		if (codcate == 10) resultado = "2F"; 	//2CAT FEM
////		if (codcate == 11) resultado = "1C"; 	//1CAT
////		if (codcate == 12) resultado = "1F"; 	//1CAT FEM
////		if (codcate == 13) resultado = "NC"; 	//NCAT
////		if (codcate == 14) resultado = "NF"; 	//NCAT FEM
////		if (codcate == 15) resultado = "VE"; 	//Veterans
////		if (codcate == 18) resultado = "PB"; 	//PrebenjamÃ­
////		if (codcate == 19) resultado = "BJ"; 	//BenjamÃ­
////		if (codcate == 20) resultado = "AL"; 	//AlevÃ­
////		if (codcate == 21) resultado = "IN"; 	//Infantil
////		if (codcate == 22) resultado = "JV"; 	//Juvenil
////		if (codcate == 23) resultado = "JN"; 	//JÃºnior
////		if (codcate == 27) resultado = "SM"; 	//SÃ¨nior MasculÃ­
////		if (codcate == 31) resultado = "VE"; 	//Veterans
////		if (codcate == 34) resultado = "SF"; 	//Senior FemenÃ­
////		if (codcate == 35) resultado = "OL"; 	//OK LIGA 
////		if (codcate == 36) resultado = "1D"; 	//1Âª DIVISIÃ“N
////		if (codcate == 38) resultado = "OF"; 	//OK LIGA FEM
////		if (codcate == 40) resultado = "16"; 	//Fem16
////		if (codcate == 0) {
////			if (tipo.equalsIgnoreCase("Arbitre")) resultado = "AR";
////			if (tipo.equalsIgnoreCase("Delegat/da")) resultado = "DL";
////			if (tipo.equalsIgnoreCase("TÃ¨cnic")) resultado = "TN";
////			if (tipo.equalsIgnoreCase("Auxiliar")) resultado = "AX";
////		}
////		return resultado;
////	}
//    public void GuardarEvento(Acta acta) {
//        EactaEventHome eventoDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent();
//
//        long tipoevent = 0;
//        if (acta.getNeventotipo().equals("Gol")) {
//            tipoevent = 110;
//        } else if (acta.getNeventotipo().equals("Servei Inicial")) {
//            tipoevent = 101;
//        } else if (acta.getNeventotipo().equals("Total Faltes")) {
//            tipoevent = 130;
//        } else if (acta.getNeventotipo().equals("Directe")) {
//            tipoevent = 112;
//        } else if (acta.getNeventotipo().equals("Penal")) {
//            tipoevent = 111;
//        } else if (acta.getNeventotipo().equals("Tarja Blava")) {
//            tipoevent = 120;
//        } else if (acta.getNeventotipo().equals("Tarja Vermella")) {
//            tipoevent = 121;
//        } else if (acta.getNeventotipo().equals("Temps Mort")) {
//            tipoevent = 102;
//        } else if (acta.getNeventotipo().equals("Hora Inici Real")) {
//            tipoevent = 100;
//        }
//
//        evento.setCodpartit(acta.getCodpartit());
//        evento.setTipoevent(tipoevent);
//        evento.setEvent(acta.getNeventotipo());
//        evento.setPart(acta.getNeventoparte());
//        if (!acta.getNeventocrono().equals("")) {
//            evento.setCrono(acta.getNeventocrono());
//        } else {
//            evento.setCrono("00:00");
//        }
//        evento.setLocalvisitant(acta.getNeventoequipo());
//        if (!acta.getNeventodorsal().equals("")) {
//            evento.setDorsal(Long.parseLong(acta.getNeventodorsal()));
//        }
//        evento.setAtribut1(acta.getNeventoatribut());
//
//        eventoDAO.crearEactaEvent(evento);
//    }
//
//    public void EliminarEvento(Acta acta) {
//        EactaEventHome eventoDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent();
//
//        evento.setCodpartit(acta.getCodpartit());
//        evento.setId(Long.parseLong(acta.getDelevento()));
//        eventoDAO.eliminarEactaevent(evento);
//    }
//
//    public void EliminarGoles(Acta acta, String local, String idevento) {
//        EactaEventHome eventoDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent();
//
//        evento.setCodpartit(acta.getCodpartit());
//        evento.setId(Long.parseLong(idevento));
//        eventoDAO.eliminarEactaevent(evento);
//    }
//
////	public void GuardarEventoGol(Acta acta) { 
////		EactaEventHome eventoDAO = new EactaEventHome();
////		EactaEvent evento = new EactaEvent();
////	
////		long tipoevent = 110;
////	
////		evento.setCodpartit(acta.getCodpartit());
////		evento.setTipoevent(tipoevent);
////		evento.setEvent("Gol");
////		if (acta.getGolesequipo().equals("L")) {
////			evento.setPart(acta.getCgolespl());
////			if(!acta.getCgolesml().equals("")) evento.setCrono(acta.getCgolesml());
////			else evento.setCrono("00:00");
////			evento.setLocalvisitant(acta.getGolesequipo());
////			if (!acta.getCgolesdl().equals("")) evento.setDorsal(Long.parseLong(acta.getCgolesdl()));
////			
////		} else {
////			evento.setPart(acta.getCgolespv());
////			if(!acta.getCgolesmv().equals("")) evento.setCrono(acta.getCgolesmv());
////			else evento.setCrono("00:00");
////			evento.setLocalvisitant(acta.getGolesequipo());
////			if (!acta.getCgolesdv().equals("")) evento.setDorsal(Long.parseLong(acta.getCgolesdv()));
////		}
////		
////		evento.setAtribut1("");
////
////		eventoDAO.crearEactaEvent(evento);
////	}
//    public void GuardarEventoTAzul(Acta acta) {
//        EactaEventHome eventoDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent();
//
//        long tipoevent = 120;
//
//        evento.setCodpartit(acta.getCodpartit());
//        evento.setTipoevent(tipoevent);
//        evento.setEvent("Tarja Blava");
//        evento.setPart(acta.getCfaltasap());
//        if (!acta.getCfaltasam().equals("")) {
//            evento.setCrono(acta.getCfaltasam());
//        } else {
//            evento.setCrono("00:00");
//        }
//        evento.setLocalvisitant(acta.getFaltasaequipo());
//        if (!acta.getCfaltasad().equals("")) {
//            evento.setDorsal(Long.parseLong(acta.getCfaltasad()));
//        }
//        evento.setAtribut1(acta.getCfaltasar());
//
//        eventoDAO.crearEactaEvent(evento);
//    }
//
//    public void GuardarEventoTRoja(Acta acta) {
//        EactaEventHome eventoDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent();
//
//        long tipoevent = 121;
//
//        evento.setCodpartit(acta.getCodpartit());
//        evento.setTipoevent(tipoevent);
//        evento.setEvent("Tarja Vermella");
//        evento.setPart(acta.getCfaltasrp());
//        if (!acta.getCfaltasrm().equals("")) {
//            evento.setCrono(acta.getCfaltasrm());
//        } else {
//            evento.setCrono("00:00");
//        }
//        evento.setLocalvisitant(acta.getFaltasrequipo());
//        if (!acta.getCfaltasrd().equals("")) {
//            evento.setDorsal(Long.parseLong(acta.getCfaltasrd()));
//        }
//        evento.setAtribut1(acta.getCfaltasrr());
//
//        eventoDAO.crearEactaEvent(evento);
//    }
//
//    @SuppressWarnings("deprecation")
//    public Acta GuardarActa(Acta acta, String estado) {
//        // guardar timestamp inicio
//        long start = System.currentTimeMillis();
//        LogSupport.info("usuari: "+acta.getUsername()+ " Eacta controller usuari grava lÂ´acta "+ acta.getCodpartit());
//
////        EactaEventHome eactaeventDAO = new EactaEventHome();
////        EactaEvent evento;
//        //GUARDAMOS ACTA QUE YA EXISTE A BASE DE ACTUALIZAR LOS EVENTOS
//        //Cabecera
//        EactaEventHome eactaeventDAO = new EactaEventHome();
//        EactaEvent evento = new EactaEvent(acta.getCodpartit(), 23, "Delegat de pista", "0", "00:00", "L", 0, acta.getCodlic_dp(), acta.getNom_cognoms_dp(), "", acta.getPin_dp(), "", acta.getNif_dp(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 22, "Cronometrador", "0", "00:00", "L", 0, acta.getCodlic_cr(), acta.getNom_cognoms_cr(), "", "", "", acta.getNif_cr(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 20, "Ã€rbitre Principal", "0", "00:00", "L", 0, acta.getCodlic_ar1(), acta.getNom_cognoms_ar1(), acta.getCat_ar1(), acta.getPin_ar1(), acta.getEmail_ar1(), acta.getNif_ar1(), acta.getTerritorial_ar1(), acta.getTelefono_ar1(), "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 21, "Ã€rbitre Auxiliar", "0", "00:00", "L", 0, acta.getCodlic_ar2(), acta.getNom_cognoms_ar2(), "", acta.getPin_ar2(), "", acta.getNif_ar2(), acta.getTerritorial_ar2(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        //Estado
//        evento = new EactaEvent(acta.getCodpartit(), 61, "Estat", "0", "00:00", "L", 0, estado, "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//
//        //Observaciones
//        String observaciones = acta.getObservacions();
//        observaciones = observaciones.replace("'", "");
//        observaciones = observaciones.replace("\"", "");
//        evento = new EactaEvent(acta.getCodpartit(), 150, "Observacions Ã€rbitre", "0", "00:00", "L", 0, observaciones, "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
////        ACTUALIZAMOS LOS JUGADORES
////        Locales
//        if (acta.getCapi_l1() == null) {
//            acta.setCapi_l1("");
//        }
//        if (acta.getCapi_l2() == null) {
//            acta.setCapi_l2("");
//        }
//        if (acta.getCapi_l3() == null) {
//            acta.setCapi_l3("");
//        }
//        if (acta.getCapi_l4() == null) {
//            acta.setCapi_l4("");
//        }
//        if (acta.getCapi_l5() == null) {
//            acta.setCapi_l5("");
//        }
//        if (acta.getCapi_l6() == null) {
//            acta.setCapi_l6("");
//        }
//        if (acta.getCapi_l7() == null) {
//            acta.setCapi_l7("");
//        }
//        if (acta.getCapi_l8() == null) {
//            acta.setCapi_l8("");
//        }
//        if (acta.getCapi_l9() == null) {
//            acta.setCapi_l9("");
//        }
//        if (acta.getCapi_l10() == null) {
//            acta.setCapi_l10("");
//        }
//        //TODO CARLES Sancionado en atributo7 de los jugadores
//        evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Local", "0", "00:00", "L", acta.getNum_l1(), acta.getCodlic_l1(), acta.getNom_cognoms_l1(),
//                acta.getCat_l1(), acta.getPin_l1(), acta.getCapi_l1(), acta.getNif_l1(), acta.getSancion_l1(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Local", "0", "00:00", "L", acta.getNum_l2(), acta.getCodlic_l2(), acta.getNom_cognoms_l2(),
//                acta.getCat_l2(), acta.getPin_l2(), acta.getCapi_l2(), acta.getNif_l2(), acta.getSancion_l2(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Local", "0", "00:00", "L", acta.getNum_l3(), acta.getCodlic_l3(), acta.getNom_cognoms_l3(),
//                acta.getCat_l3(), acta.getPin_l3(), acta.getCapi_l3(), acta.getNif_l3(), acta.getSancion_l3(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Local", "0", "00:00", "L", acta.getNum_l4(), acta.getCodlic_l4(), acta.getNom_cognoms_l4(),
//                acta.getCat_l4(), acta.getPin_l4(), acta.getCapi_l4(), acta.getNif_l4(), acta.getSancion_l4(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Local", "0", "00:00", "L", acta.getNum_l5(), acta.getCodlic_l5(), acta.getNom_cognoms_l5(),
//                acta.getCat_l5(), acta.getPin_l5(), acta.getCapi_l5(), acta.getNif_l5(), acta.getSancion_l5(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Local", "0", "00:00", "L", acta.getNum_l6(), acta.getCodlic_l6(), acta.getNom_cognoms_l6(),
//                acta.getCat_l6(), acta.getPin_l6(), acta.getCapi_l6(), acta.getNif_l6(), acta.getSancion_l6(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Local", "0", "00:00", "L", acta.getNum_l7(), acta.getCodlic_l7(), acta.getNom_cognoms_l7(),
//                acta.getCat_l7(), acta.getPin_l7(), acta.getCapi_l7(), acta.getNif_l7(), acta.getSancion_l7(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Local", "0", "00:00", "L", acta.getNum_l8(), acta.getCodlic_l8(), acta.getNom_cognoms_l8(),
//                acta.getCat_l8(), acta.getPin_l8(), acta.getCapi_l8(), acta.getNif_l8(), acta.getSancion_l8(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Local", "0", "00:00", "L", acta.getNum_l9(), acta.getCodlic_l9(), acta.getNom_cognoms_l9(),
//                acta.getCat_l9(), acta.getPin_l9(), acta.getCapi_l9(), acta.getNif_l9(), acta.getSancion_l9(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Local", "0", "00:00", "L", acta.getNum_l10(), acta.getCodlic_l10(), acta.getNom_cognoms_l10(),
//                acta.getCat_l10(), acta.getPin_l10(), acta.getCapi_l10(), acta.getNif_l10(), acta.getSancion_l10(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Local", "0", "00:00", "L", acta.getNum_l11(), acta.getCodlic_l11(), acta.getNom_cognoms_l11(),
//                acta.getCat_l11(), acta.getPin_l11(), "", acta.getNif_l11(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Local", "0", "00:00", "L", acta.getNum_l12(), acta.getCodlic_l12(), acta.getNom_cognoms_l12(),
//                acta.getCat_l12(), "", "", acta.getNif_l12(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Local", "0", "00:00", "L", acta.getNum_l13(), acta.getCodlic_l13(), acta.getNom_cognoms_l13(),
//                acta.getCat_l13(), "", "", acta.getNif_l13(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Local", "0", "00:00", "L", acta.getNum_l14(), acta.getCodlic_l14(), acta.getNom_cognoms_l14(),
//                acta.getCat_l14(), "", "", acta.getNif_l14(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Local", "0", "00:00", "L", acta.getNum_l15(), acta.getCodlic_l15(), acta.getNom_cognoms_l15(),
//                acta.getCat_l15(), "", "", acta.getNif_l15(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Local", "0", "00:00", "L", acta.getNum_l16(), acta.getCodlic_l16(), acta.getNom_cognoms_l16(),
//                acta.getCat_l16(), "", "", acta.getNif_l16(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Local", "0", "00:00", "L", acta.getNum_l17(), acta.getCodlic_l17(), acta.getNom_cognoms_l17(),
//                acta.getCat_l17(), "", "", acta.getNif_l17(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        //Visitantes
//        if (acta.getCapi_v1() == null) {
//            acta.setCapi_v1("");
//        }
//        if (acta.getCapi_v2() == null) {
//            acta.setCapi_v2("");
//        }
//        if (acta.getCapi_v3() == null) {
//            acta.setCapi_v3("");
//        }
//        if (acta.getCapi_v4() == null) {
//            acta.setCapi_v4("");
//        }
//        if (acta.getCapi_v5() == null) {
//            acta.setCapi_v5("");
//        }
//        if (acta.getCapi_v6() == null) {
//            acta.setCapi_v6("");
//        }
//        if (acta.getCapi_v7() == null) {
//            acta.setCapi_v7("");
//        }
//        if (acta.getCapi_v8() == null) {
//            acta.setCapi_v8("");
//        }
//        if (acta.getCapi_v9() == null) {
//            acta.setCapi_v9("");
//        }
//        if (acta.getCapi_v10() == null) {
//            acta.setCapi_v10("");
//        }
//        evento = new EactaEvent(acta.getCodpartit(), 35, "Porter1 Visitant", "0", "00:00", "V", acta.getNum_v1(), acta.getCodlic_v1(), acta.getNom_cognoms_v1(),
//                acta.getCat_v1(), acta.getPin_v1(), acta.getCapi_v1(), acta.getNif_v1(), acta.getSancion_v1(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 36, "Jugador1 Visitant", "0", "00:00", "V", acta.getNum_v2(), acta.getCodlic_v2(), acta.getNom_cognoms_v2(),
//                acta.getCat_v2(), acta.getPin_v2(), acta.getCapi_v2(), acta.getNif_v2(), acta.getSancion_v2(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 37, "Jugador2 Visitant", "0", "00:00", "V", acta.getNum_v3(), acta.getCodlic_v3(), acta.getNom_cognoms_v3(),
//                acta.getCat_v3(), acta.getPin_v3(), acta.getCapi_v3(), acta.getNif_v3(), acta.getSancion_v3(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 38, "Jugador3 Visitant", "0", "00:00", "V", acta.getNum_v4(), acta.getCodlic_v4(), acta.getNom_cognoms_v4(),
//                acta.getCat_v4(), acta.getPin_v4(), acta.getCapi_v4(), acta.getNif_v4(), acta.getSancion_v4(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 39, "Jugador4 Visitant", "0", "00:00", "V", acta.getNum_v5(), acta.getCodlic_v5(), acta.getNom_cognoms_v5(),
//                acta.getCat_v5(), acta.getPin_v5(), acta.getCapi_v5(), acta.getNif_v5(), acta.getSancion_v5(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 40, "Jugador5 Visitant", "0", "00:00", "V", acta.getNum_v6(), acta.getCodlic_v6(), acta.getNom_cognoms_v6(),
//                acta.getCat_v6(), acta.getPin_v6(), acta.getCapi_v6(), acta.getNif_v6(), acta.getSancion_v6(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 41, "Jugador6 Visitant", "0", "00:00", "V", acta.getNum_v7(), acta.getCodlic_v7(), acta.getNom_cognoms_v7(),
//                acta.getCat_v7(), acta.getPin_v7(), acta.getCapi_v7(), acta.getNif_v7(), acta.getSancion_v7(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 42, "Jugador7 Visitant", "0", "00:00", "V", acta.getNum_v8(), acta.getCodlic_v8(), acta.getNom_cognoms_v8(),
//                acta.getCat_v8(), acta.getPin_v8(), acta.getCapi_v8(), acta.getNif_v8(), acta.getSancion_v8(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 43, "Jugador8 Visitant", "0", "00:00", "V", acta.getNum_v9(), acta.getCodlic_v9(), acta.getNom_cognoms_v9(),
//                acta.getCat_v9(), acta.getPin_v9(), acta.getCapi_v9(), acta.getNif_v9(), acta.getSancion_v9(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 44, "Porter2 Visitant", "0", "00:00", "V", acta.getNum_v10(), acta.getCodlic_v10(), acta.getNom_cognoms_v10(),
//                acta.getCat_v10(), acta.getPin_v10(), acta.getCapi_v10(), acta.getNif_v10(), acta.getSancion_v10(), "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 45, "Tecnic Visitant", "0", "00:00", "V", acta.getNum_v11(), acta.getCodlic_v11(), acta.getNom_cognoms_v11(),
//                acta.getCat_v11(), acta.getPin_v11(), "", acta.getNif_v11(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 46, "Delegat Visitant", "0", "00:00", "V", acta.getNum_v12(), acta.getCodlic_v12(), acta.getNom_cognoms_v12(),
//                acta.getCat_v12(), "", "", acta.getNif_v12(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 47, "Auxiliar Visitant", "0", "00:00", "V", acta.getNum_v13(), acta.getCodlic_v13(), acta.getNom_cognoms_v13(),
//                acta.getCat_v13(), "", "", acta.getNif_v13(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 48, "Delegat-Auxiliar2 Visitant", "0", "00:00", "V", acta.getNum_v14(), acta.getCodlic_v14(), acta.getNom_cognoms_v14(),
//                acta.getCat_v14(), "", "", acta.getNif_v14(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 49, "Delegat-Auxiliar3 Visitant", "0", "00:00", "V", acta.getNum_v15(), acta.getCodlic_v15(), acta.getNom_cognoms_v15(),
//                acta.getCat_v15(), "", "", acta.getNif_v15(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 50, "Delegat-Auxiliar4 Visitant", "0", "00:00", "V", acta.getNum_v16(), acta.getCodlic_v16(), acta.getNom_cognoms_v16(),
//                acta.getCat_v16(), "", "", acta.getNif_v16(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 51, "Delegat-Auxiliar5 Visitant", "0", "00:00", "V", acta.getNum_v17(), acta.getCodlic_v17(), acta.getNom_cognoms_v17(),
//                acta.getCat_v17(), "", "", acta.getNif_v17(), "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        //Eventos del partido
//        //Globales
//        evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "L", 0, acta.getTotalfaltasl(), "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 130, "Total Faltes", "2", "00:00", "V", 0, acta.getTotalfaltasv(), "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//
//        //Obtenemos todos los eventos de tipo gol y los eliminamos
//        evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "", "", "", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.eliminarEactaeventGol(evento);
//
//        evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "L", 0, acta.getTotalgolesl(), "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 131, "Total Gols", "2", "00:00", "V", 0, acta.getTotalgolesv(), "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//
//        //Generamos los nuevos goles en funcion de lo que hay en el grid
//        String aux = acta.getCgolespl();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        String[] parte = aux.split(",");
//        aux = acta.getCgolesml();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        String[] crono = aux.split(",");
//        aux = acta.getCgolesdl();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        String[] dorsal = aux.split(",");
//        Long xdorsal;
//        String xparte;
//        String xcrono;
//        if (isNumeric(acta.getTotalgolesl())) {
//            //Goles existentes	
//            if (!parte[0].equals("")) {
//                for (int i = 0; i < parte.length; i++) {
//                    if (isNumeric(dorsal[i].trim())) {
//                        xdorsal = Long.parseLong(dorsal[i].trim());
//                    } else {
//                        xdorsal = Long.parseLong("0");
//                    }
//                    if (parte[i].equals("")) {
//                        xparte = "1";
//                    } else {
//                        xparte = parte[i].trim();
//                    }
//                    if (crono[i].equals("")) {
//                        xcrono = "00:00";
//                    } else {
//                        xcrono = crono[i].trim();
//                    }
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "L", xdorsal, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//                //Goles aÃ±adidos
//                for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//            } else { //Goles nuevos, no habia
//                for (int i = 0; i < Integer.parseInt(acta.getTotalgolesl()); i++) {
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "L", 0, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//            }
//        }
//        aux = acta.getCgolespv();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        parte = aux.split(",");
//        aux = acta.getCgolesmv();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        crono = aux.split(",");
//        aux = acta.getCgolesdv();
//        aux = aux.replaceAll("\\[", "");
//        aux = aux.replaceAll("\\]", "");
//        dorsal = aux.split(",");
//        if (isNumeric(acta.getTotalgolesv())) {
//            //Goles existentes	
//            if (!parte[0].equals("")) {
//                for (int i = 0; i < parte.length; i++) {
//                    if (isNumeric(dorsal[i].trim())) {
//                        xdorsal = Long.parseLong(dorsal[i].trim());
//                    } else {
//                        xdorsal = Long.parseLong("0");
//                    }
//                    if (parte[i].equals("")) {
//                        xparte = "1";
//                    } else {
//                        xparte = parte[i].trim();
//                    }
//                    if (crono[i].equals("")) {
//                        xcrono = "00:00";
//                    } else {
//                        xcrono = crono[i].trim();
//                    }
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", xparte, xcrono, "V", xdorsal, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//                //Goles aÃ±adidos
//                for (int i = parte.length; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//            } else { //Goles nuevos
//                for (int i = 0; i < Integer.parseInt(acta.getTotalgolesv()); i++) {
//                    evento = new EactaEvent(acta.getCodpartit(), 110, "Gol", "1", "00:00", "V", 0, "", "", "", "", "", "", "", "", "");
//                    eactaeventDAO.crearEactaEvent(evento);
//                }
//            }
//        }
//
//        evento = new EactaEvent(acta.getCodpartit(), 101, "Servei Inicial", "1", "00:00", acta.getSaqueinicial(), 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaevent(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 100, "Hora Inici Real", "1", acta.getHorainicio(), "L", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLV(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertol1(), "L", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertol2(), "L", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "1", acta.getTiempomuertov1(), "V", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        evento = new EactaEvent(acta.getCodpartit(), 102, "Temps Mort", "2", acta.getTiempomuertov2(), "V", 0, "", "", "", "", "", "", "", "", "");
//        eactaeventDAO.actualizarEactaeventEventoPartidoLVParte(evento);
//        acta = RecargarEventos(acta);
//
//        //Firmas
//        String protesto = "";
//        String assabentat = "";
//        Calendar c1 = Calendar.getInstance();
//        String fecha = c1.get(Calendar.DATE) + "/" + c1.get(Calendar.MONTH) + "/" + c1.get(Calendar.YEAR);
//        String hora = c1.getTime().getHours() + ":" + c1.getTime().getMinutes();
//        String licencia = "";
//        //Delegado de pista
//        if (!acta.getFirma1_dp().equals("") && acta.getFirma1_dp_actualizar().equals("1")) {
//            licencia = acta.getCodlic_dp();
//            evento = new EactaEvent(acta.getCodpartit(), 90, "PreSignatura Delegat Pista", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma1_dp(), "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma1_dp().equals("") && acta.getFirma1_dp_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 90, "PreSignatura Delegat Pista", "0", "00:00", "L", 0, "", "",
//                    "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Capitan local
//        if (!acta.getFirma1_cl().equals("") && acta.getFirma1_cl_actualizar().equals("1")) {
//            if (acta.getFirma1_cl().contains("PROTESTO")) {
//                protesto = "1";
//            } else {
//                protesto = "";
//            }
//            if (acta.getFirma1_cl().contains("ASSABENTAT")) {
//                assabentat = "1";
//            } else {
//                assabentat = "";
//            }
//            licencia = obtenerLicenciaCapitan("local", acta);
//            evento = new EactaEvent(acta.getCodpartit(), 91, "PreSignatura Capita Local", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma1_cl(), protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma1_cl().equals("") && acta.getFirma1_cl_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 91, "PreSignatura Capita Local", "0", "00:00", "L", 0, "", "",
//                    "", "", protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Entrenador local
//        if (!acta.getFirma1_el().equals("") && acta.getFirma1_el_actualizar().equals("1")) {
//            licencia = acta.getCodlic_l11();
//            evento = new EactaEvent(acta.getCodpartit(), 92, "PreSignatura TÃ¨cnic Local", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma1_el(), "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma1_el().equals("") && acta.getFirma1_el_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 92, "PreSignatura TÃ¨cnic Local", "0", "00:00", "L", 0, "", "",
//                    "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Capitan visitante
//        if (!acta.getFirma1_cv().equals("") && acta.getFirma1_cv_actualizar().equals("1")) {
//            if (acta.getFirma1_cv().contains("PROTESTO")) {
//                protesto = "1";
//            } else {
//                protesto = "";
//            }
//            if (acta.getFirma1_cv().contains("ASSABENTAT")) {
//                assabentat = "1";
//            } else {
//                assabentat = "";
//            }
//            licencia = obtenerLicenciaCapitan("visitante", acta);
//            evento = new EactaEvent(acta.getCodpartit(), 93, "PreSignatura Capita Visitant", "0", "00:00", "V", 0, licencia, fecha,
//                    hora, acta.getFirma1_cv(), protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma1_cv().equals("") && acta.getFirma1_cv_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 93, "PreSignatura Capita Visitant", "0", "00:00", "V", 0, "", "",
//                    "", "", protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Entrenador visitante
//        if (!acta.getFirma1_ev().equals("") && acta.getFirma1_ev_actualizar().equals("1")) {
//            licencia = acta.getCodlic_v11();
//            evento = new EactaEvent(acta.getCodpartit(), 94, "PreSignatura TÃ¨cnic Visitant", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma1_ev(), "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma1_ev().equals("") && acta.getFirma1_ev_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 94, "PreSignatura TÃ¨cnic Visitant", "0", "00:00", "L", 0, "", "",
//                    "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
////
////        //Firmas 2
////        //Capitan local 2
//        if (!acta.getFirma2_cl().equals("") && acta.getFirma2_cl_actualizar().equals("1")) {
//            if (acta.getFirma2_cl().contains("PROTESTO")) {
//                protesto = "1";
//            } else {
//                protesto = "";
//            }
//            if (acta.getFirma2_cl().contains("ASSABENTAT")) {
//                assabentat = "1";
//            } else {
//                assabentat = "";
//            }
//            licencia = obtenerLicenciaCapitan("local", acta);
//            evento = new EactaEvent(acta.getCodpartit(), 160, "Signatura Capita Local", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma2_cl(), protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma2_cl().equals("") && acta.getFirma2_cl_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 160, "Signatura Capita Local", "0", "00:00", "L", 0, "", "",
//                    "", "", protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Arbitro 2
//        if (!acta.getFirma2_ar().equals("") && acta.getFirma2_ar_actualizar().equals("1")) {
//            licencia = acta.getCodlic_ar1();
//            evento = new EactaEvent(acta.getCodpartit(), 162, "Signatura Ã€rbitre1", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma2_ar(), "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma2_ar().equals("") && acta.getFirma2_ar_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 162, "Signatura Ã€rbitre1", "0", "00:00", "L", 0, "", "",
//                    "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Arbitro auxiliar 2
//        if (!acta.getFirma2_ar2().equals("") && acta.getFirma2_ar2_actualizar().equals("1")) {
//            licencia = acta.getCodlic_ar2();
//            evento = new EactaEvent(acta.getCodpartit(), 163, "Signatura Ã€rbitre2", "0", "00:00", "L", 0, licencia, fecha,
//                    hora, acta.getFirma2_ar2(), "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma2_ar2().equals("") && acta.getFirma2_ar2_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 163, "Signatura Ã€rbitre2", "0", "00:00", "L", 0, "", "",
//                    "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Capitan visitante 2
//        if (!acta.getFirma2_cv().equals("") && acta.getFirma2_cv_actualizar().equals("1")) {
//            if (acta.getFirma2_cv().contains("PROTESTO")) {
//                protesto = "1";
//            } else {
//                protesto = "";
//            }
//            if (acta.getFirma2_cv().contains("ASSABENTAT")) {
//                assabentat = "1";
//            } else {
//                assabentat = "";
//            }
//            licencia = obtenerLicenciaCapitan("visitante", acta);
//            evento = new EactaEvent(acta.getCodpartit(), 161, "Signatura CapitÃ  Visitant", "0", "00:00", "V", 0, licencia, fecha,
//                    hora, acta.getFirma2_cv(), protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        if (acta.getFirma2_cv().equals("") && acta.getFirma2_cv_actualizar().equals("1")) {
//            evento = new EactaEvent(acta.getCodpartit(), 161, "Signatura CapitÃ  Visitant", "0", "00:00", "V", 0, "", "",
//                    "", "", protesto, assabentat, "", "", "");
//            eactaeventDAO.actualizarEactaevent(evento);
//        }
//        //Recuperamos los datos del acta para las selecciones
//        ArrayList<DatosActa> listadatosacta = new ArrayList<DatosActa>();
//        listadatosacta = recuperarDatosActa(acta.getCodpartit());
//
//        //OBTENEMOS LOS JUGADORES SELECCIONABLES DE CADA EQUIPO Y CATEGORIA Y TECNICOS
//        ArrayList<DatosActa> listajugadoresseleclocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listajugadoresselecvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicoslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicosvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listatecnicosfede = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadoslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadosvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listadelegadosfede = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaauxiliareslocal = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaauxiliaresvisit = new ArrayList<DatosActa>();
//        ArrayList<DatosActa> listaarbitros = new ArrayList<DatosActa>();
//        listajugadoresseleclocal = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatlocal());
//        listajugadoresselecvisit = obtenerJugadoresporEquipo(listadatosacta, acta.getCodentitatvisit());
//        //codcate=33 -> Ã rbitres
//        //codcate=37 -> delegat
//        //codcate=56 -> auxiliar
//        //codcate=39 -> tÃ¨cnic ... la resta sÃ³n jugadors i porters 
//        listatecnicoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 39);
//        listatecnicosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 39);
//        listatecnicosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 39);
//        listadelegadoslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 37);
//        listadelegadosvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 37);
//        listadelegadosfede = obtenerTecnicosporEquipo(listadatosacta, 15, 37);
//        listaauxiliareslocal = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatlocal(), 56);
//        listaauxiliaresvisit = obtenerTecnicosporEquipo(listadatosacta, acta.getCodentitatvisit(), 56);
//        listaarbitros = obtenerTecnicosporEquipo(listadatosacta, 15, 33);
//
//        acta.setDatosactalocal(listajugadoresseleclocal);
//        acta.setDatosactavisit(listajugadoresselecvisit);
//        acta.setDatosactaTlocal(listatecnicoslocal);
//        acta.setDatosactaTvisit(listatecnicosvisit);
//        acta.setDatosactaTfede(listatecnicosfede);
//        acta.setDatosactaDlocal(listadelegadoslocal);
//        acta.setDatosactaDvisit(listadelegadosvisit);
//        acta.setDatosactaDfede(listadelegadosfede);
//        acta.setDatosactaAlocal(listaauxiliareslocal);
//        acta.setDatosactaAvisit(listaauxiliaresvisit);
//        acta.setDatosactaArbitro(listaarbitros);
//
//        // calcular tiempo transcurrido
//        long end = System.currentTimeMillis();
//        long res = end - start;
//        System.out.println("Guardar eActa - Segundos: " + res / 1000);
//        LogSupport.info(" usuari: "+ acta.getUsername() + " es guarda lÂ´acta del partit "+ acta.getCodpartit() +" en "+ res/1000 +"s");
//        return acta;
//    }
//
//    public String obtenerLicenciaCapitan(String equipo, Acta acta) {
//        String resultado = "";
//
//        if (equipo.equals("local")) {
//            if (acta.getCapi_l1().equals("1")) {
//                resultado = acta.getCodlic_l1();
//            } else if (acta.getCapi_l2().equals("1")) {
//                resultado = acta.getCodlic_l2();
//            } else if (acta.getCapi_l3().equals("1")) {
//                resultado = acta.getCodlic_l3();
//            } else if (acta.getCapi_l4().equals("1")) {
//                resultado = acta.getCodlic_l4();
//            } else if (acta.getCapi_l5().equals("1")) {
//                resultado = acta.getCodlic_l5();
//            } else if (acta.getCapi_l6().equals("1")) {
//                resultado = acta.getCodlic_l6();
//            } else if (acta.getCapi_l7().equals("1")) {
//                resultado = acta.getCodlic_l7();
//            } else if (acta.getCapi_l8().equals("1")) {
//                resultado = acta.getCodlic_l8();
//            } else if (acta.getCapi_l9().equals("1")) {
//                resultado = acta.getCodlic_l9();
//            } else if (acta.getCapi_l10().equals("1")) {
//                resultado = acta.getCodlic_l10();
//            }
//        } else if (acta.getCapi_v1().equals("1")) {
//            resultado = acta.getCodlic_v1();
//        } else if (acta.getCapi_v2().equals("1")) {
//            resultado = acta.getCodlic_v2();
//        } else if (acta.getCapi_v3().equals("1")) {
//            resultado = acta.getCodlic_v3();
//        } else if (acta.getCapi_v4().equals("1")) {
//            resultado = acta.getCodlic_v4();
//        } else if (acta.getCapi_v5().equals("1")) {
//            resultado = acta.getCodlic_v5();
//        } else if (acta.getCapi_v6().equals("1")) {
//            resultado = acta.getCodlic_v6();
//        } else if (acta.getCapi_v7().equals("1")) {
//            resultado = acta.getCodlic_v7();
//        } else if (acta.getCapi_v8().equals("1")) {
//            resultado = acta.getCodlic_v8();
//        } else if (acta.getCapi_v9().equals("1")) {
//            resultado = acta.getCodlic_v9();
//        } else if (acta.getCapi_v10().equals("1")) {
//            resultado = acta.getCodlic_v10();
//        }
//        return resultado;
//    }
//
//    public ArrayList<DatosActa> obtenerJugadoresInicialesporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat, long codequip) {
//        ArrayList<DatosActa> listajugadores = new ArrayList<DatosActa>();
//        String categoria1 = "Jugador/a pista";
//        String categoria2 = "Porter/a";
//        for (DatosActa object : listadatosacta) {
//            if (object.getCodenti() == codentitat
//                    && (object.getCodequip() == codequip || object.getCodequip2() == codequip || object.getCodequip3() == codequip)
//                    && (object.getSubmodalitat().equals(categoria1) || object.getSubmodalitat().equals(categoria2))) {
//                listajugadores.add(object);
//            }
//        }
//        return listajugadores;
//    }
//
//    public ArrayList<DatosActa> obtenerJugadoresporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat) {
//        ArrayList<DatosActa> listajugadores = new ArrayList<DatosActa>();
//        String categoria1 = "Jugador/a pista";
//        String categoria2 = "Porter/a";
//        for (DatosActa object : listadatosacta) {
//            if (object.getCodenti() == codentitat
//                    && (object.getSubmodalitat().equals(categoria1) || object.getSubmodalitat().equals(categoria2))) {
//                listajugadores.add(object);
//            }
//        }
//        return listajugadores;
//    }
//
//    public ArrayList<DatosActa> obtenerTecnicosporEquipo(ArrayList<DatosActa> listadatosacta, long codentitat, long codcate) {
//        ArrayList<DatosActa> listatecnicos = new ArrayList<DatosActa>();
//
//        for (DatosActa object : listadatosacta) {
//            if (object.getCodenti() == codentitat
//                    && object.getCodcate() == codcate) {
//                listatecnicos.add(object);
//            }
//        }
//        return listatecnicos;
//    }
//
//    @SuppressWarnings("rawtypes")
//    public boolean usuarioTienePermisos(String eactaconf, Long codtecnic, String user, Long codtecnic2) {
//        //Buscamos usuario actual en la tabla de usuario por user
//        //Si el id coincide con datosacta.getcodtecnic(0) es el arbitro --> true
//        //Si el usuario tiene codperfil = 1, 2 o 3 es admin --> true
//        //si no false
//        boolean resultado = false;
//        Eacta_vw_usuariHome usuarioDAO = new Eacta_vw_usuariHome();
//        Eacta_vw_usuari usuario = null;
//        List usuario2 = usuarioDAO.findByProperty("user", user);
//        if (usuario2.size() > 0) {
//            usuario = (Eacta_vw_usuari) usuario2.get(0);
//        }
//
//        if (!estaFlagActivo(eactaconf, 6)) {
//            resultado = true;	//ValidaciÃ³n desactivada. No controlamos los permisos.
//        } else //FLAG 6 Validacion activa. Controlamos los permisos. Solo Admin y arbitro del partido.
//        //System.out.println("VALIDACION 6 - USUARIO LOGADO PERMISOS ACTIVA");
//        {
//            if (usuario != null) {
//                if (usuario.getCodtecnic().compareTo(codtecnic) == 0) {
//                    resultado = true;
//                }
//                if (usuario.getCodtecnic().compareTo(codtecnic2) == 0) {
//                    resultado = true;
//                } else if (usuario.getCodperfil() == 1 || usuario.getCodperfil() == 2 || usuario.getCodperfil() == 3) {
//                    resultado = true;
//                }
//            } else {
//                System.out.println("No se encuentra el usuario");
//                resultado = false;
//            }
//        }
//        return resultado;
//
//    }
//
//    public static boolean estaFlagActivo(String config, int flag) {
//        //16 Flags del campo eactaconf. Si el flag n esta activo...
//        //1 - No se puede aÃ±adir un jugador sancionado ni local ni visitante
//        //2 - No se pueden aÃ±adir mas de 4 jugadores de categoria inferior a la del partido (excepto junior codecat=7)
//        //3 - Deben haber tres jugadores y un portero mÃ­nimo para guardar el acta
//        //4 - Delegado de pista obligatorio
//        //5 - Cronometrador obligatorio
//        //6 - Se validan los permisos del usuario. Debe ser el arbitro del partido o admin
//        //7 - Avisa que no hay tecnico
//        //8 - Pone a observaciones (L'equip xxx no presenta tÃ¨cnic).
//        //9 - 16 - Libres
//        if (config.charAt(flag - 1) == '1') {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void SendMail(Acta acta, String destino) {
//        //Enviamos a mail arbitro y clubs
//        System.out.println("eACTA - ENVIANDO MAIL A :");
//
//        ObtePdf ob = new ObtePdf();
//        try {
//            ob.obtenirArxiu(acta.getCodpartit());
//        } catch (SQLException ex) {
//            LogSupport.grava(ex);
//        }
//
//        String Username = "eacta@fecapa.cat";
//        String Subject = "FECAPA: eActa del partit " + acta.getCodpartit();
//        String To = "";
//        //FLAG 7 Envio del correo al cerrar el acta
//        if (estaFlagActivo(acta.getConfig(), 7)) {
//            //  To ="aadan@fecapa.cat";
//            //Enviamos mail al arbitro y los dos clubs
//            To = acta.getEmail_ar1() + "," + acta.getMailequiplocal() + "," + acta.getMailequipvisit();
//            System.out.println("Arbitro: " + acta.getEmail_ar1());
//            System.out.println("Club local: " + acta.getMailequiplocal());
//            System.out.println("Club visitante: " + acta.getMailequipvisit());
//        } else {
//            //  Enviamos mail a usuarios de test
//            To = "eacta@fecapa.cat" + "," + "caltimiras@fecapa.cat" + "," + "mmolto@fecapa.cat";
//            System.out.println("Usuarios test: " + To);
//        }
//        //FLAG 9 Envio del correo a secretaria tecnica
//        if (estaFlagActivo(acta.getConfig(), 9)) {
//            To = To + "," + "eacta@fecapa.cat";
//            System.out.println("Secretaria tÃ©cnica: eacta@fecapa.cat");
//        }
//
//        String Mensaje = "FECAPA: eActa del partit " + acta.getCodpartit();
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.fecapa.cat");
//        props.put("mail.smtp.port", "25");
//        props.put("mail.smtp.user", "eacta@fecapa.cat");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("eacta@fecapa.cat", "Cepeda2015");
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(Username));
//            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(To));
//            message.setSubject(Subject);
//            message.setText(Mensaje);
//
//            BodyPart texto = new MimeBodyPart();
//            texto.setText(Mensaje);
//
//            MimeBodyPart mbp2 = new MimeBodyPart();
//            FileDataSource source = new FileDataSource(destino);
//            mbp2.setDataHandler(new DataHandler(source));
//            mbp2.setFileName(source.getName());
//
//            MimeMultipart multiParte = new MimeMultipart();
//            multiParte.addBodyPart(texto);
//            multiParte.addBodyPart(mbp2);
//
//            message.setContent(multiParte);
//
//            Transport.send(message);
//            LogSupport.info(" usuari : " + acta.getUsername()+ ".  SÂ´envia e-mail de lÂ´acta "+ acta.getCodpartit()+ " a "+ To);
//        } catch (MessagingException e) {
//            LogSupport.grava(e);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public static Acta RecargarEventos(Acta acta) {
//        acta.setTotalfaltasl("");
//        acta.setTotalfaltasv("");
//        acta.setTotalgolesl("");
//        acta.setTotalgolesv("");
//        acta.setSaqueinicial("");
//        acta.setHorainicio("");
//        acta.setTiempomuertol1("");
//        acta.setTiempomuertol2("");
//        acta.setTiempomuertov1("");
//        acta.setTiempomuertov2("");
//
//        //Recargamos eventos de las tablas
//        EactaEventHome eactaeventDAO = new EactaEventHome();
//        List<Object[]> listaeventos = eactaeventDAO.eventosPartidoPorPartido(acta.getCodpartit());
//        ArrayList<String> levento_id = new ArrayList<String>();
//        ArrayList<String> levento_tipo = new ArrayList<String>();
//        ArrayList<String> levento_parte = new ArrayList<String>();
//        ArrayList<String> levento_crono = new ArrayList<String>();
//        ArrayList<String> levento_equipo = new ArrayList<String>();
//        ArrayList<String> levento_dorsal = new ArrayList<String>();
//        ArrayList<String> levento_atributo = new ArrayList<String>();
//        ArrayList<String> levento_atributop = new ArrayList<String>();
//
//        ArrayList<String> golespl = new ArrayList<String>();
//        ArrayList<String> golesml = new ArrayList<String>();
//        ArrayList<String> golesdl = new ArrayList<String>();
//        ArrayList<String> golesel = new ArrayList<String>();
//
//        ArrayList<String> golespv = new ArrayList<String>();
//        ArrayList<String> golesmv = new ArrayList<String>();
//        ArrayList<String> golesdv = new ArrayList<String>();
//        ArrayList<String> golesev = new ArrayList<String>();
//
//        ArrayList<String> faltasap = new ArrayList<String>();
//        ArrayList<String> faltasam = new ArrayList<String>();
//        ArrayList<String> faltasad = new ArrayList<String>();
//        ArrayList<String> faltasar = new ArrayList<String>();
//        ArrayList<String> faltasae = new ArrayList<String>();
//
//        ArrayList<String> faltasrp = new ArrayList<String>();
//        ArrayList<String> faltasrm = new ArrayList<String>();
//        ArrayList<String> faltasrd = new ArrayList<String>();
//        ArrayList<String> faltasrr = new ArrayList<String>();
//        ArrayList<String> faltasre = new ArrayList<String>();
//
//        for (Object[] object : listaeventos) {
//            levento_id.add(object[1].toString());
//            levento_tipo.add(object[3].toString());
//            levento_parte.add(object[4].toString());
//            levento_crono.add(object[5].toString());
//            levento_equipo.add(object[6].toString());
//            levento_dorsal.add((object[7] == null) ? "" : object[7].toString());
//            if (object[8] == null) {
//                levento_atributo.add("");
//                levento_atributop.add("");
//            } else {
//                levento_atributo.add(object[8].toString());
//                if (object[8].toString().length() > 10) {
//                    levento_atributop.add(object[8].toString().substring(0, 10) + "...");
//                } else {
//                    levento_atributop.add(object[8].toString());
//                }
//            }
//            if (object[3].toString().equals("Total Faltes")) {
//                if (object[6].toString().equals("L")) {
//                    acta.setTotalfaltasl((object[8] == null) ? "" : object[8].toString());
//                }
//                if (object[6].toString().equals("V")) {
//                    acta.setTotalfaltasv((object[8] == null) ? "" : object[8].toString());
//                }
//            }
//
//            if (object[3].toString().equals("Total Gols")) {
//                if (object[6].toString().equals("L")) {
//                    acta.setTotalgolesl((object[8] == null) ? "" : object[8].toString());
//                }
//                if (object[6].toString().equals("V")) {
//                    acta.setTotalgolesv((object[8] == null) ? "" : object[8].toString());
//                }
//            }
//
//            if (object[3].toString().equals("Servei Inicial")) {
//                acta.setSaqueinicial(object[6].toString());
//            }
//
//            if (object[3].toString().equals("Hora Inici Real")) {
//                acta.setHorainicio(object[5].toString());
//            }
//
//            if (object[3].toString().equals("Temps Mort")) {
//                if (object[6].toString().equals("L") && object[4].toString().equals("1")) {
//                    acta.setTiempomuertol1(object[5].toString());
//                }
//                if (object[6].toString().equals("L") && object[4].toString().equals("2")) {
//                    acta.setTiempomuertol2(object[5].toString());
//                }
//                if (object[6].toString().equals("V") && object[4].toString().equals("1")) {
//                    acta.setTiempomuertov1(object[5].toString());
//                }
//                if (object[6].toString().equals("V") && object[4].toString().equals("2")) {
//                    acta.setTiempomuertov2(object[5].toString());
//                }
//            }
//
//            if (object[3].toString().equals("Gol")) {
//                if (object[6].toString().equals("L")) {
//                    golespl.add(object[4].toString());
//                    golesml.add(object[5].toString());
//                    golesdl.add((object[7] == null) ? "" : object[7].toString());
//                    golesel.add(object[6].toString());
//                } else {
//                    golespv.add(object[4].toString());
//                    golesmv.add(object[5].toString());
//                    golesdv.add((object[7] == null) ? "" : object[7].toString());
//                    golesev.add(object[6].toString());
//                }
//            }
//
//            if (object[3].toString().equals("Tarja Blava")) {
//                faltasap.add(object[4].toString());
//                faltasam.add(object[5].toString());
//                faltasad.add((object[7] == null) ? "" : object[7].toString());
//                faltasar.add((object[8] == null) ? "" : object[8].toString());
//                faltasae.add(object[6].toString());
//            }
//
//            if (object[3].toString().equals("Tarja Vermella")) {
//                faltasrp.add(object[4].toString());
//                faltasrm.add(object[5].toString());
//                faltasrd.add((object[7] == null) ? "" : object[7].toString());
//                faltasrr.add((object[8] == null) ? "" : object[8].toString());
//                faltasre.add(object[6].toString());
//            }
//        }
//
//        acta.setEvento_id(levento_id);
//        acta.setEvento_tipo(levento_tipo);
//        acta.setEvento_parte(levento_parte);
//        acta.setEvento_crono(levento_crono);
//        acta.setEvento_equipo(levento_equipo);
//        acta.setEvento_dorsal(levento_dorsal);
//        acta.setEvento_atributo(levento_atributo);
//        acta.setEvento_atributop(levento_atributop);
//
//        acta.setGolesdl(golesdl);
//        acta.setGolesml(golesml);
//        acta.setGolespl(golespl);
//        acta.setGolesel(golesel);
//
//        acta.setGolesdv(golesdv);
//        acta.setGolesmv(golesmv);
//        acta.setGolespv(golespv);
//        acta.setGolesev(golesev);
//
//        acta.setFaltasad(faltasad);
//        acta.setFaltasam(faltasam);
//        acta.setFaltasap(faltasap);
//        acta.setFaltasar(faltasar);
//        acta.setFaltasae(faltasae);
//
//        acta.setFaltasrd(faltasrd);
//        acta.setFaltasrm(faltasrm);
//        acta.setFaltasrp(faltasrp);
//        acta.setFaltasrr(faltasrr);
//        acta.setFaltasre(faltasre);
//
//        acta.setSeccion("EVENTOS");
//        acta.setMessage("Evento creado");
//
//        return acta;
//    }
//
//    @SuppressWarnings("unchecked")
//    public void EnviarResultado(Acta acta) throws IOException {
//        //FLAG 12 Enviar resultado automÃ¡tico al cerrar el acta
//        if (estaFlagActivo(acta.getConfig(), 12)) {
//            //Recuperar goles local y visitante
//            EactaEventHome eactaeventoDAO = new EactaEventHome();
//            List<Object[]> listaeventos = eactaeventoDAO.eventosPartidoPorPartido(acta.getCodpartit());
//            String golesL = EActaPDFBuilder.calcularTotalGoles(listaeventos, "L");
//            String golesV = EActaPDFBuilder.calcularTotalGoles(listaeventos, "V");
//
//            //Preparamos la llamada 
//            String host = "";
//            //FLAG 14 Enviar resultado automatico al servidor de produccion
//            if (estaFlagActivo(acta.getConfig(), 14)) {
//                host = "www.fecapa.com";
//            } else {
//                host = "www.aplicbox.com";
//            }
//            String url = "http://" + host + ":9080/eCompeticio/PartitAction.do?action=smsresultats&code=00000000&date=11111111&time=222222&fullnumber=" + acta.getTelefono_ar1()
//                    + "&word1=FECAPA&word2=" + acta.getCodpartit() + "&word3=" + golesL + "&word4=" + golesV
//                    + "&word5=&aliases=FECAPA&text=FECAPA%20" + acta.getCodpartit() + "%20" + golesL + "%20" + golesV + "%20";
//            //TEST url = "http://aplicbox.com:9080/eCompeticio/PartitAction.do?action=smsresultats&code=00000000&date=11111111&time=222222&fullnumber=649482995&word1=FECAPA&word2=165902&word3=8&word4=7&word5=&aliases=$word1&text=$word1%20$word2%20$word3%20$word4%20$word5";
//            System.out.println("eACTA - Enviando resultado automÃ¡tico del acta " + acta.getCodpartit() + " a " + url);
//            
//            URL URL = new URL(url);
//            BufferedReader in = new BufferedReader(new InputStreamReader(URL.openStream()));
//            String inputLine;
//            String resultado = "";
//            while ((inputLine = in.readLine()) != null) {
//                resultado = resultado + inputLine;
//                LogSupport.info("usuari : "+ acta.getUsername() + " sÂ´envia resultat correcte ecompeticio del partit "+ acta.getCodpartit());
//            }
//            in.close();
//            if (resultado.equals("")) {
//                resultado = "NO RESPONDE";
//                LogSupport.info("usuari : "+ acta.getUsername() + " lÂ´enviament del resultat del partit "+ acta.getCodpartit() + " dona problemes per alguna dada");
//            }
//
//            System.out.println("Resultado --> " + resultado);
//
//            //Creamos un evento con el resultado
//            EactaEventHome eactaeventDAO = new EactaEventHome();
//            EactaEvent evento = new EactaEvent(acta.getCodpartit(), 61, "", "", "", "", 0, "", "", "", "", "", "", "", "", "");
//            eactaeventDAO.actualizarEactaResultado(evento, url, resultado);
//        }
//    }
//
//    public boolean isNumeric(String s) {
//        return s.matches("[-+]?\\d*\\.?\\d+");
//    }
//
//    public static String obtenerClase(Long clase) {
//        int claseaux;
//        String resultado = "";
//        if (clase == null) {
//            return resultado;
//        } else {
//            claseaux = clase.intValue();
//            switch (claseaux) {
//                case 1:
//                    resultado = "";
//                    break;
//                case 2:
//                    resultado = "A";
//                    break;
//                case 3:
//                    resultado = "B";
//                    break;
//                case 4:
//                    resultado = "C";
//                    break;
//                case 5:
//                    resultado = "D";
//                    break;
//                case 6:
//                    resultado = "E";
//                    break;
//                case 7:
//                    resultado = "F";
//                    break;
//                case 8:
//                    resultado = "G";
//                    break;
//                case 9:
//                    resultado = "H";
//                    break;
//                case 10:
//                    resultado = "I";
//                    break;
//                case 11:
//                    resultado = "J";
//                    break;
//                case 12:
//                    resultado = "K";
//                    break;
//                case 13:
//                    resultado = "L";
//                    break;
//                case 14:
//                    resultado = "M";
//                    break;
//                case 15:
//                    resultado = "N";
//                    break;
//                case 16:
//                    resultado = "O";
//                    break;
//                case 17:
//                    resultado = "P";
//                    break;
//                case 18:
//                    resultado = "Q";
//                    break;
//                case 19:
//                    resultado = "R";
//                    break;
//                case 20:
//                    resultado = "S";
//                    break;
//                case 21:
//                    resultado = "T";
//                    break;
//                case 22:
//                    resultado = "U";
//                    break;
//                case 23:
//                    resultado = "V";
//                    break;
//                case 24:
//                    resultado = "W";
//                    break;
//                case 25:
//                    resultado = "X";
//                    break;
//                case 26:
//                    resultado = "Y";
//                    break;
//                case 27:
//                    resultado = "Z";
//                    break;
//                default:
//                    break;
//            }
//            return resultado;
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        System.out.println(estaFlagActivo("00000010111111101101", 12));
//        System.out.println(estaFlagActivo("00000010111111101101", 14));
//
//    }
//
//}