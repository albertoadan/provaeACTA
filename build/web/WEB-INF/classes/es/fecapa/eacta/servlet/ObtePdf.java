/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.fecapa.eacta.servlet;

import java.io.File;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author albert.adan
 */
public class ObtePdf {

    public void obtenirArxiu(long codi) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int codiPartit = (int) codi;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
        } catch (Exception e) {

        }

        ResultSet rset = null;

        File f = new File("/var/www/eACTA/ACTA_" + codiPartit + ".pdf");
        try {
            pstmt = con.prepareStatement("Select pdf from eacta_event where codpartit=? and tipoevent=202 limit 1;");
            pstmt.setInt(1, codiPartit);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                if (f.exists()) {
                    f.delete();
                }
                Blob blob = rset.getBlob(1);
                InputStream is = blob.getBinaryStream();
                FileOutputStream fos = new FileOutputStream("/var/www/eACTA/ACTA_" + codiPartit + ".pdf");
                int b = 0;
                while ((b = is.read()) != -1) {
                    fos.write(b);
                }
            }

            pstmt.close();
            con.close();

        } catch (SQLException e) {
            pstmt.close();
            con.close();
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            pstmt.close();
            con.close();

        } catch (IOException ex) {
            pstmt.close();
            con.close();
        }

    }

    public void eliminarPdf(long codi) {
        int codiPartit = (int) codi;
        File f = new File("/var/www/eACTA/ACTA_" + codiPartit + ".pdf");
        if (f.exists()) {
            f.delete();
        }

    }

    public void deletePdf(long codi) {
        try {
            boolean cond = false;
            Connection con = null;
            PreparedStatement pstmt = null;
            int codiPartit = (int) codi;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://31.47.76.83:3306/eActa", "root", "ccdhp4040..");
            } catch (Exception e) {

            }

            ResultSet rset = null;

            pstmt = con.prepareStatement("Select pdf from eacta_event where codpartit=? and tipoevent=202 limit 1;");
            pstmt.setInt(1, codiPartit);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                cond = true;
            }

            rset.close();
            pstmt.close();
            // con.close();

            if (cond == true) {
                pstmt = con.prepareStatement("delete pdf from eacta_event where codpartit=? and tipoevent=202");
                pstmt.setInt(1, codiPartit);
                rset = pstmt.executeQuery();
            }
            rset.close();
            pstmt.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(ObtePdf.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
