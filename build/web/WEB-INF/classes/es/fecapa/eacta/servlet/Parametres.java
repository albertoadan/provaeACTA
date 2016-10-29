/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.fecapa.eacta.servlet;

import es.fecapa.eacta.bean.Acta;

import es.fecapa.eacta.db.EactaEvent;
import es.fecapa.eacta.db.EactaEventHome;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author albert.adan
 */
public class Parametres {

    private List llistaCorreus = new ArrayList();

    public Parametres() {

    }

    /**
     * Metode per extraure tots els comptes de correu de la taula eacta_config
     * als quals s´ha d´enviar mail amb el pdf de l´eacta
     *
     * @param codiPartit
     * @param destino
     * @param mosca
     * @throws SQLException
     */
    public void enviaMail(long codiPartit, String destino, boolean mosca) throws SQLException {
        List llistaCorreus = new ArrayList();
        llistaCorreus.clear();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean cond = false;

        String codiTerri = "";
        String codiTerriLocal = "";

        int codi = (int) codiPartit;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
        } catch (Exception e) {

        }
        try {
            pstmt = con.prepareStatement("SELECT atribut5, atribut7 FROM eActa.eacta_event where (tipoevent=20 || tipoevent=21 || tipoevent=30 || tipoevent=60)"
                    + "  and codpartit=  ? ;");
            pstmt.setInt(1, codi);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString(1) != null) {
                    if (rs.getString(1).contains("@")) {
                        llistaCorreus.add(rs.getString(1));
                    }
                }
                if (rs.getString(2) != null) {
                    if (rs.getString(2).contains("@")) {
                        llistaCorreus.add(rs.getString(2));
                    }
                }
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT atribut2,atribut5 from eacta_event where tipoevent =22 and codpartit =  ?  limit 1 ;");
            pstmt.setInt(1, codi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).length() >= 9) {
                    if (rs.getString(1).charAt(9) == '1') {
                        if (rs.getString(2) != null) {
                            if (rs.getString(2).contains("@")) {
                                llistaCorreus.add(rs.getString(2));
                            }
                        }
                    }
                }
            }
            rs.close();
            pstmt.close();

            pstmt = con.prepareStatement("SELECT atribut2,atribut5 from eacta_event where tipoevent =23 and codpartit =  ? limit 1 ;");
            pstmt.setInt(1, codi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).length() >= 10) {
                    if (rs.getString(1).charAt(10) == '1') {
                        if (rs.getString(2) != null) {
                            if (rs.getString(2).contains("@")) {
                                llistaCorreus.add(rs.getString(2));
                            }
                        }
                    }
                }
            }
            rs.close();
            pstmt.close();

//          
            pstmt = con.prepareStatement("SELECT atribut4,atribut5 from eacta_event where tipoevent =1 and codpartit =  ? limit 1 ;");
            pstmt.setInt(1, codi);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                codiTerri = rs.getString(1);
                codiTerriLocal = rs.getString(2);
            }
            rs.close();
            pstmt.close();

            if (codiTerri != null) {
                pstmt = con.prepareStatement("select atribut1 from eacta_config where codparametre between concat('10',?,'00') "
                        + "and concat('10',?,'99')    ;");
                pstmt.setString(1, codiTerri);
                pstmt.setString(2, codiTerri);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (rs.getString(1) != null) {
                        if (rs.getString(1).contains("@")) {
                            llistaCorreus.add(rs.getString(1));
                        }
                    }
                }
                rs.close();
                pstmt.close();
            }

            if (codiTerri != null && codiTerriLocal != null) {
                if (!codiTerri.equals(codiTerriLocal)) {
                    pstmt = con.prepareStatement("select atribut1 from eacta_config where codparametre between "
                            + "concat('11',?,'00') and concat('11',?,'99')  ;");
                    pstmt.setString(1, codiTerriLocal);
                    pstmt.setString(2, codiTerriLocal);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        if (rs.getString(1) != null) {
                            if (rs.getString(1).contains("@")) {
                                llistaCorreus.add(rs.getString(1));
                            }
                        }
                    }
                    rs.close();
                    pstmt.close();
                }
            }

            if (mosca && codiTerri != null) {
                pstmt = con.prepareStatement("select atribut1 from eacta_config where codparametre between concat('9',?,'00') "
                        + "and concat('9',?,'99') ;");
                pstmt.setString(1, codiTerri);
                pstmt.setString(2, codiTerri);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (rs.getString(1) != null) {
                        if (rs.getString(1).contains("@")) {
                            llistaCorreus.add(rs.getString(1));
                        }
                    }
                }
                rs.close();
                pstmt.close();
            }

            con.close();

            LogSupport.info(" es genera la llista d´emails a enviar");
            sendMail(codi, destino, llistaCorreus);

        } catch (SQLException e) {
            pstmt.close();
            con.close();
            // TODO Auto-generated catch block
            LogSupport.info(Parametres.class.toString() + " metode enviaMail :");
            LogSupport.grava(e);
        }

    }

    public void finalSignatura(Acta acta) {
        String protesto = "";
        String assabentat = "";
        Calendar c1 = Calendar.getInstance();
        String fecha = c1.get(Calendar.DATE) + "/" + c1.get(Calendar.MONTH) + "/" + c1.get(Calendar.YEAR);
        String hora = c1.getTime().getHours() + ":" + c1.getTime().getMinutes();
        String licencia = "";
        EactaEventHome eactaeventDAO = new EactaEventHome();
        EactaEvent evento;
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
    }

    public void preSignatura(Acta acta) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean cond = false;

        long start = System.currentTimeMillis();
        EactaEventHome eactaeventDAO = new EactaEventHome();
        EactaEvent evento;

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
        long end = System.currentTimeMillis();
        long res = end - start;
        System.out.println("Guardar presignaturesDelegat - Segundos: " + res / 1000);

    }

    public void guardaDorsalLocal(Acta acta) throws SQLException {
       // LogSupport.info("usuari :" + acta.getUsername() + " guardant els dorsals locals a la base de dades");
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement st = null;
        boolean cond = false;
        Map llista = new HashMap();

        long start = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
        } catch (Exception e) {
            LogSupport.grava(e);
        }

        try {
            pstmt = con.prepareStatement("SELECT atribut1, dorsal from eacta_event where codpartit=? and tipoevent between 35 and 44 limit 10;");
            pstmt.setLong(1, acta.getCodpartit());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer x = (Integer) rs.getObject("dorsal");
                if ((rs.getString(1)!=null) && (x!=null)) {
                    llista.put(rs.getString(1), rs.getInt(2));
                }                
            }
            rs.close();
            pstmt.close();

            Iterator<Map.Entry<Integer, Integer>> it = llista.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> pair = it.next();
                try {
                    pstmt = con.prepareStatement("update eacta_llicencia set dorsal= ? , "
                            + " where codlic= ? and codmoda=1  ;");
                    pstmt.setInt(1, pair.getValue());
                    pstmt.setString(2, pair.getKey().toString());
                    pstmt.executeQuery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                long res = end - start;
                pstmt.close();
                LogSupport.info("usuari : " + acta.getUsername() + " partit: " + acta.getCodpartit() + " Dorsals locals guardats en " + res / 1000);
            }
        } catch (Exception e) {
            LogSupport.info("Error gravant els dorsals locals");
            LogSupport.grava(e);
        }

        con.close();

    }

    public void guardaDorsalvisitant(Acta acta) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement st = null;
        boolean cond = false;
        Map llista = new HashMap();

        long start = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
        } catch (Exception e) {
            LogSupport.grava(e);
        }

        try {
            pstmt = con.prepareStatement("SELECT atribut1, dorsal from eacta_event where codpartit=? and tipoevent between 65 and 74 limit 10;");
            pstmt.setLong(1, acta.getCodpartit());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                 Integer x = (Integer) rs.getObject("dorsal");
                if ((rs.getString(1)!=null) && (x!=null)) {
                llista.put(rs.getString(1), rs.getInt(2));
                }
            }
            rs.close();
            pstmt.close();

            Iterator<Map.Entry<Integer, Integer>> it = llista.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> pair = it.next();
                try {
                    pstmt = con.prepareStatement("update eacta_llicencia set dorsal= ? , "
                            + " where codlic= ? and codmoda=1  ;");
                    pstmt.setInt(1, pair.getValue());
                    pstmt.setString(2, pair.getKey().toString());
                    pstmt.executeQuery();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                long res = end - start;
                pstmt.close();
                LogSupport.info("usuari : " + acta.getUsername() + " partit: " + acta.getCodpartit() + " Dorsals visitants guardats en " + res / 1000);
            }
        } catch (Exception e) {
            LogSupport.info("Error gravant els dorsals visitants");
            LogSupport.grava(e);
        }
        con.close();
    }

    public String obtenerLicenciaCapitan(String equipo, Acta acta) {
        String resultado = "";

        if (equipo.equals("local")) {
            if (acta.getCapi_l1() != null) {
                if (acta.getCapi_l1().equals("1")) {
                    resultado = acta.getCodlic_l1();
                }
            } else if (acta.getCapi_l2() != null) {
                if (acta.getCapi_l2().equals("1")) {
                    resultado = acta.getCodlic_l2();
                }
            } else if (acta.getCapi_l3() != null) {
                if (acta.getCapi_l3().equals("1")) {
                    resultado = acta.getCodlic_l3();
                }
            } else if (acta.getCapi_l4() != null) {
                if (acta.getCapi_l4().equals("1")) {
                    resultado = acta.getCodlic_l4();
                }
            } else if (acta.getCapi_l5() != null) {
                if (acta.getCapi_l5().equals("1")) {
                    resultado = acta.getCodlic_l5();
                }
            } else if (acta.getCapi_l6() != null) {
                if (acta.getCapi_l6().equals("1")) {
                    resultado = acta.getCodlic_l6();
                }
            } else if (acta.getCapi_l7() != null) {
                if (acta.getCapi_l7().equals("1")) {
                    resultado = acta.getCodlic_l7();
                }
            } else if (acta.getCapi_l8() != null) {
                if (acta.getCapi_l8().equals("1")) {
                    resultado = acta.getCodlic_l8();
                }
            } else if (acta.getCapi_l9() != null) {
                if (acta.getCapi_l9().equals("1")) {
                    resultado = acta.getCodlic_l9();
                }
            } else if (acta.getCapi_l10() != null) {
                if (acta.getCapi_l10().equals("1")) {
                    resultado = acta.getCodlic_l10();
                }
            }
        } else if (acta.getCapi_v1() != null) {
            if (acta.getCapi_v1().equals("1")) {
                resultado = acta.getCodlic_l1();
            }
        } else if (acta.getCapi_v2() != null) {
            if (acta.getCapi_v2().equals("1")) {
                resultado = acta.getCodlic_l2();
            }
        } else if (acta.getCapi_v3() != null) {
            if (acta.getCapi_v3().equals("1")) {
                resultado = acta.getCodlic_l3();
            }
        } else if (acta.getCapi_v4() != null) {
            if (acta.getCapi_v4().equals("1")) {
                resultado = acta.getCodlic_l4();
            }
        } else if (acta.getCapi_v5() != null) {
            if (acta.getCapi_v5().equals("1")) {
                resultado = acta.getCodlic_l5();
            }
        } else if (acta.getCapi_v6() != null) {
            if (acta.getCapi_v6().equals("1")) {
                resultado = acta.getCodlic_l6();
            }
        } else if (acta.getCapi_v7() != null) {
            if (acta.getCapi_v7().equals("1")) {
                resultado = acta.getCodlic_l7();
            }
        } else if (acta.getCapi_v8() != null) {
            if (acta.getCapi_v8().equals("1")) {
                resultado = acta.getCodlic_l8();
            }
        } else if (acta.getCapi_v9() != null) {
            if (acta.getCapi_v9().equals("1")) {
                resultado = acta.getCodlic_l9();
            }
        } else if (acta.getCapi_v10() != null) {
            if (acta.getCapi_v10().equals("1")) {
                resultado = acta.getCodlic_l10();
            }
        }
        return resultado;
    }

    public void sendMail(long codi, String destino, List<String> llista) throws SQLException {
        //Enviamos a mail arbitro y clubs
        System.out.println("eACTA - ENVIANDO MAIL A :");

//        ObtePdf ob = new ObtePdf();
//        ob.obtenirArxiu(codi);
        String Username = "eacta@fecapa.cat";
        String Subject = "FECAPA: eActa del partit " + codi;
        String To = "";

        for (String obj : llista) {
            To = obj.toString() + "," + To;
        }

        //  To = To + "aadan@fecapa.cat";
        String Mensaje = "FECAPA: eActa del partit " + codi;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.fecapa.cat");
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
            LogSupport.info(" mail enviat a la llista:  " + To);

        } catch (MessagingException e) {
            LogSupport.info(Parametres.class.toString() + " metode sendMail :");
            LogSupport.grava(e);
        }
    }

}
