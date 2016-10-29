/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.fecapa.eacta.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author aadan
 */
public class LogSupport {

 private static final Logger log = Logger.getLogger("eACTA");
    private static FileHandler fileTxt = null;
    private static SimpleFormatter formatterTxt;
    public static String dia = "";
    private static String ruta = System.getProperty("file.separator") + "var" + System.getProperty("file.separator") + "log" + System.getProperty("file.separator")
            + "eACTA";
    private static PrintWriter printWriter;
    private static File arxiu = null;
    private static BufferedWriter bw;

     //  private static String ruta = "C:\\Users\\albert.adan\\Desktop";
    /**
     * Graba una excepcion en el log.
     *
     * @param e excepcion para grabar.
     */
    public static void grava(Exception e)  {
       File f = new File(ruta);
        if (!f.exists()) {
            f.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
String hora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        if (arxiu != null) {
            if (!dia.equals(timeStamp)) {
                arxiu = null;
            }
            else {
                try {
                bw = new BufferedWriter(new FileWriter(new File(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt"), true));
                bw.write(timeStamp + "/"+hora   + ".ERROR: " + e.toString() );
                bw.newLine();
                bw.close();
//                   printWriter= new PrintWriter(new FileOutputStream(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt", true));
//                    printWriter.write(text);
                dia = ((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LogSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            }
        }
        if (arxiu == null) {
            arxiu = new File(timeStamp);
            try {
                bw = new BufferedWriter(new FileWriter(new File(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt"), true));
                bw.write(timeStamp+ "/"+hora + ".ERROR: " + e.toString());
                bw.newLine();
                bw.close();
//                   printWriter= new PrintWriter(new FileOutputStream(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt", true));
//                    printWriter.write(text);
                dia = ((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LogSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public static void info(String text)  {
        File f = new File(ruta);
        if (!f.exists()) {
            f.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String hora = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
        if (arxiu != null) {
            if (!dia.equals(timeStamp)) {
                arxiu = null;
            }
            else {
                try {
                bw = new BufferedWriter(new FileWriter(new File(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt"), true));
                bw.write(timeStamp + "/"+hora+ ".INFO: " + text );
                bw.newLine();
                bw.close();
//                   printWriter= new PrintWriter(new FileOutputStream(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt", true));
//                    printWriter.write(text);
                dia = ((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LogSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            }
        }
        if (arxiu == null) {
            arxiu = new File(timeStamp);
            try {
                bw = new BufferedWriter(new FileWriter(new File(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt"), true));
                bw.write(timeStamp+ "/"+hora + ".INFO: " + text);
                bw.newLine();
                bw.close();
//                   printWriter= new PrintWriter(new FileOutputStream(ruta + System.getProperty("file.separator") + (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()) + ".txt", true));
//                    printWriter.write(text);
                dia = ((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(LogSupport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        //Grabamos la informacion de la excepcion producida.
        //log.log(Level.INFO, text);
    }
}
