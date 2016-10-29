package es.fecapa.eacta.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.fecapa.eacta.bean.Acta;
import es.fecapa.eacta.controller.EActa_controller;

import es.fecapa.eacta.db.EactaEventHome;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.rmi.runtime.Log;

public class EActaPDFBuilder {

    private Font bigFont;
    private Font titleFont;
    private Font headerFont;
    private Font normalFont;
    private Font littleFont;
    private Font littleFontRed;
    private Font littleFont2;
    private Font littleFontGray;
    private Font littleFontBlue;
    private Font aux;

    private BaseColor markBackGround;
    private BaseColor fonsVermelles;
    private BaseColor fonsBlaves;

    private Document document;
    private PdfWriter writer;
    private Acta data;
    private Locale locale;
    private String fechaActualFormat;
    private int numPage;
//	private DecimalFormat dfnumber = new DecimalFormat("#,##0");

    private float alturacelda = 13;

    public static boolean generarInforme(String destino, Acta acta, Locale locale) {
        return new EActaPDFBuilder().generarPDF(destino, acta, locale);
    }

    public boolean comprobaNumero(String num) {
        try {
            int n = Integer.parseInt(num);
            if (n > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean generarPDF(String destino, Acta acta, Locale plocale) {
        //TODO Poner valores reales del acta
//        markBackGround = new BaseColor(132, 134, 132);
        fonsVermelles = new BaseColor(204, 006, 005);
        fonsBlaves = new BaseColor(59, 131, 189);
        markBackGround = fonsBlaves;
//                
//                
//                  URL url=null;
//            try {
//                url = new URL("http://31.47.76.83/var/www/eACTA/ACTA_"+acta.getCodpartit() + ".pdf" );
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(EActaPDFBuilder.class.getName()).log(Level.SEVERE, null, ex);
//            }
//          URLConnection con=null;
//            try {
//                con = url.openConnection();
//            } catch (IOException ex) {
//                Logger.getLogger(EActaPDFBuilder.class.getName()).log(Level.SEVERE, null, ex);
//            }
//          con.setDoOutput(true);
        document = new Document();
        numPage = 1;
        data = acta;
        locale = plocale;
        SimpleDateFormat sdf = new SimpleDateFormat("d/MM/yyyy");
        fechaActualFormat = sdf.format(new Date());

        try {
            // writer = PdfWriter.getInstance(document, con.getOutputStream());
            writer = PdfWriter.getInstance(document, new FileOutputStream(destino));
            document.setMargins(10, 10, 0, 0);
            document.open();
            writer.setCompressionLevel(0);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);

            prepareFonts();
            prepareLogo();
            addPage(acta);
            document.close();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(EActaPDFBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private void addPage(Acta acta) throws DocumentException {
        document.newPage();
        addHeaderSpace(acta);
        //	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        //Lista eventos del partido
        EactaEventHome eactaeventDAO = new EactaEventHome();
        List<Object[]> listaeventos = eactaeventDAO.eventosPartidoPorPartido(acta.getCodpartit());

        //Cabecera
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{35, 15, 35, 15});
        PdfPCell cell = null;
        cell = new PdfPCell(new Phrase("Temporada" + "   " + acta.getTemporada(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Jornada" + "   " + acta.getJornada(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Núm d'Acta" + "    " + acta.getCodpartit(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ACTA del partit celebrat a" + "   " + acta.getPoblaciojoc(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("el " + acta.getDatajoc(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("a les" + "   " + acta.getHorajoc() + " hores", headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PISTA DE JOC" + "   " + acta.getAdressajoc(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("COMPETICIÓ" + "   " + acta.getCompeticio() + " - " + acta.getNomcompeticio(), headerFont));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("ÀRBITRE 1" + "   " + acta.getNom_cognoms_ar1() + " (" + acta.getTerritorial_ar1() + ")", headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Cronometrador" + "   " + acta.getNom_cognoms_cr(), headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);

        if (!acta.getNom_cognoms_ar2().equals("")) {
            cell = new PdfPCell(new Phrase("ÀRBITRE 2" + "   " + acta.getNom_cognoms_ar2() + " (" + acta.getTerritorial_ar2() + ")", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("", headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            table.addCell(cell);
        }

        table.setSpacingAfter(5);
        document.add(table);

        int tarjetasA = 0;
        //EQUIP 1
        table = new PdfPTable(12);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2, 8, 3, 25, 2, 3, 3, 2, 2, 2, 2, 38});

        cell = new PdfPCell(new Phrase("1", titleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("LOCAL", titleFont));
        cell.setBorder(Rectangle.BOX);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getNomequiplocal() + " " + acta.getClasselocal(), normalFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("TEMPS MORT\n" + tiempoMuerto(listaeventos, "L"), littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("TARGETES", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("LLISTA D'EVENTS", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("LLICÈNCIA NÚM.", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setMinimumHeight(alturacelda);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("CAT.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("COGNOMS I NOMS DEL JUGADORS", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("NÚM.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("GOLS", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("BL.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("V.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(listaEventos(listaeventos), littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        cell.setRowspan(calcularFilasEventos(acta));
        table.addCell(cell);

        String nombre = "";

        if (!acta.getCodlic_l1().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l1(), littleFontGray));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l1(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l1().equals("1")) {
                nombre = acta.getNom_cognoms_l1() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l1();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l1()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l1(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l1(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(fonsBlaves);
            }
//			if (tarjetasA > 0) cell.setBackgroundColor(markBackGround);
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(fonsBlaves);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(fonsBlaves);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l1(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l1(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
        }
        if (!acta.getCodlic_l2().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l2(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l2(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l2().equals("1")) {
                nombre = acta.getNom_cognoms_l2() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l2();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l2()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l2(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l2(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(fonsBlaves);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(fonsBlaves);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(fonsBlaves);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l2(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l2(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l3().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l3(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l3(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l3().equals("1")) {
                nombre = acta.getNom_cognoms_l3() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l3();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l3()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l3(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l3(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l3(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l3(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l4().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l4(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l4(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l4().equals("1")) {
                nombre = acta.getNom_cognoms_l4() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l4();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l4()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l4(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l4(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l4(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l4(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l5().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l5(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l5(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l5().equals("1")) {
                nombre = acta.getNom_cognoms_l5() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l5();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l5()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l5(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l5(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l5(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l5(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l6().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l6(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l6(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l6().equals("1")) {
                nombre = acta.getNom_cognoms_l6() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l6();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l6()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l6(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l6(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l6(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l6(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l7().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l7(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l7(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l7().equals("1")) {
                nombre = acta.getNom_cognoms_l7() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l7();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l7()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l7(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l7(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l7(), "L"))) {
                aux = littleFontRed;
            } else {
                aux = littleFontGray;
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l7(), "L"), aux));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
        }
        if (!acta.getCodlic_l8().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l8(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l8(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l8().equals("1")) {
                nombre = acta.getNom_cognoms_l8() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l8();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l8()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l8(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l8(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l8(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l8(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l9().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l9(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l9(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l9().equals("1")) {
                nombre = acta.getNom_cognoms_l9() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l9();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l9()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l9(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l9(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l9(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l9(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l10().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l10(), littleFontGray));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l10(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_l10().equals("1")) {
                nombre = acta.getNom_cognoms_l10() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_l10();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_l10()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_l10(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l10(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l10(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l10(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l11().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l11(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l11(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TÈCNIC", littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l11(), "L");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l11(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l11(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l12().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l12(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l12(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("DELEGAT/DA", littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l12(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l12(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l13().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l13(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l13(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("AUXILIAR", littleFont));
            cell.setBorder(Rectangle.BOX);
            //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
            if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                cell.setColspan(2);
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
            if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l13(), "L");
                if (tarjetasA > 0) {
                    cell = new PdfPCell(new Phrase("1", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("1", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 0) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
                if (tarjetasA > 1) {
                    cell = new PdfPCell(new Phrase("2", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("2", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 1) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
                if (tarjetasA > 2) {
                    cell = new PdfPCell(new Phrase("3", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("3", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 2) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l13(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l13(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l14().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(5);
            if (acta.getCat_l14().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_l14().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l14(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l14(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l14(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l15().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_l15().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_l15().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l15(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l15(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l15(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l16().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_l16().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_l16().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l16(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l16(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l16(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_l17().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_l17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_l17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_l17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_l17().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_l17().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_l17(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_l17(), "L"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_l17(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }

        cell = new PdfPCell(new Phrase("TOTAL FALTES D'EQUIP", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setMinimumHeight(alturacelda);
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(calcularTotalFaltas(listaeventos, "L"), littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(6);
        table.addCell(cell);

        //EQUIP 2
        cell = new PdfPCell(new Phrase("2", titleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("VISITANT", titleFont));
        cell.setBorder(Rectangle.BOX);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getNomequipvisit() + " " + acta.getClassevisit(), normalFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("TEMPS MORT\n" + tiempoMuerto(listaeventos, "V"), littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("TARGETES", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("LLICÈNCIA NÚM.", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setMinimumHeight(alturacelda);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("CAT.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("COGNOMS I NOMS DEL JUGADORS", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("NÚM.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("GOLS", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("BL.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("V.", littleFont2));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);

        if (!acta.getCodlic_v1().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v1(), littleFontGray));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v1(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v1().equals("1")) {
                nombre = acta.getNom_cognoms_v1() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v1();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v1()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v1(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v1(), "V");
            if (tarjetasA > 0) {
                if (tarjetasA > 0) {
                    cell = new PdfPCell(new Phrase("1", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("1", littleFontGray));
                }
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                if (tarjetasA > 1) {
                    cell = new PdfPCell(new Phrase("2", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("2", littleFontGray));
                }
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                if (tarjetasA > 2) {
                    cell = new PdfPCell(new Phrase("3", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("3", littleFontGray));
                }
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v1(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v1(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v2().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v2(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v2(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v2().equals("1")) {
                nombre = acta.getNom_cognoms_v2() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v2();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v2()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v2(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v2(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v2(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v2(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v3().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v3(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v3(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v3().equals("1")) {
                nombre = acta.getNom_cognoms_v3() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v3();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v3()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v3(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v3(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v3(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v3(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v4().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v4(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v4(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v4().equals("1")) {
                nombre = acta.getNom_cognoms_v4() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v4();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v4()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v4(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v4(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v4(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v4(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v5().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v5(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v5(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v5().equals("1")) {
                nombre = acta.getNom_cognoms_v5() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v5();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v5()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v5(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v5(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v5(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v5(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v6().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v6(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v6(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v6().equals("1")) {
                nombre = acta.getNom_cognoms_v6() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v6();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v6()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v6(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v6(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v6(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v6(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v7().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v7(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v7(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v7().equals("1")) {
                nombre = acta.getNom_cognoms_v7() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v7();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v7()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v7(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v7(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v7(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v7(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v8().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v8(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v8(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v8().equals("1")) {
                nombre = acta.getNom_cognoms_v8() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v8();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v8()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v8(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v8(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v8(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v8(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v9().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v9(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v9(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v9().equals("1")) {
                nombre = acta.getNom_cognoms_v9() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v9();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v9()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v9(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v9(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v9(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v9(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v10().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v10(), littleFontGray));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v10(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            if (acta.getCapi_v10().equals("1")) {
                nombre = acta.getNom_cognoms_v10() + " (C)";
            } else {
                nombre = acta.getNom_cognoms_v10();
            }
            cell = new PdfPCell(new Phrase(nombre, littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(acta.getNum_v10()), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(calcularGoles(listaeventos, acta.getNum_v10(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v10(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v10(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v10(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v11().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v11(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v11(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TÈCNIC", littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v11(), "V");
            if (tarjetasA > 0) {
                cell = new PdfPCell(new Phrase("1", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("1", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 0) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 1) {
                cell = new PdfPCell(new Phrase("2", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("2", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 1) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);
            if (tarjetasA > 2) {
                cell = new PdfPCell(new Phrase("3", littleFont));
            } else {
                cell = new PdfPCell(new Phrase("3", littleFontGray));
            }
            cell.setBorder(Rectangle.BOX);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            if (tarjetasA > 2) {
                cell.setBackgroundColor(markBackGround);
            }
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v11(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v11(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v12().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v12(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v12(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("DELEGAT/DA", littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(5);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v12(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v12(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v13().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v13(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v13(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("AUXILIAR", littleFont));
            cell.setBorder(Rectangle.BOX);
            //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
            if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                cell.setColspan(2);
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
            if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v13(), "V");
                if (tarjetasA > 0) {
                    cell = new PdfPCell(new Phrase("1", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("1", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 0) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
                if (tarjetasA > 1) {
                    cell = new PdfPCell(new Phrase("2", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("2", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 1) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
                if (tarjetasA > 2) {
                    cell = new PdfPCell(new Phrase("3", littleFont));
                } else {
                    cell = new PdfPCell(new Phrase("3", littleFontGray));
                }
                cell.setBorder(Rectangle.BOX);
                cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                if (tarjetasA > 2) {
                    cell.setBackgroundColor(markBackGround);
                }
                table.addCell(cell);
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v13(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v13(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v14().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v14(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_v14().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_v14().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v14(), "V");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v14(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v14(), "L"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v15().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v15(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_v15().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_v15().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v15(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v15(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v15(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v16().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v16(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_v16().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_v16().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v16(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v16(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v16(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }
        if (!acta.getCodlic_v17().equals("")) {
            cell = new PdfPCell(new Phrase(acta.getCodlic_v17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setMinimumHeight(alturacelda);
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getNom_cognoms_v17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getCat_v17(), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (acta.getCat_v17().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    cell.setColspan(2);
                } else {
                    cell.setColspan(5);
                }
            } else {
                cell.setColspan(5);
            }
            table.addCell(cell);
            if (acta.getCat_v17().equalsIgnoreCase("AUXILIAR")) {
                //FLAG 13 Activar tarjetas azules para auxiliares en acta pdf
                if (EActa_controller.estaFlagActivo(acta.getConfig(), 13)) {
                    tarjetasA = calcularTarjetasAzules(listaeventos, acta.getNum_v17(), "L");
                    if (tarjetasA > 0) {
                        cell = new PdfPCell(new Phrase("1", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("1", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 0) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 1) {
                        cell = new PdfPCell(new Phrase("2", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("2", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 1) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                    if (tarjetasA > 2) {
                        cell = new PdfPCell(new Phrase("3", littleFont));
                    } else {
                        cell = new PdfPCell(new Phrase("3", littleFontGray));
                    }
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                    if (tarjetasA > 2) {
                        cell.setBackgroundColor(markBackGround);
                    }
                    table.addCell(cell);
                }
            }
            cell = new PdfPCell(new Phrase(calcularTarjetasRojas(listaeventos, acta.getNum_v17(), "V"), littleFont));
            cell.setBorder(Rectangle.BOX);
            if (comprobaNumero(calcularTarjetasRojas(listaeventos, acta.getNum_v17(), "V"))) {
                cell.setBackgroundColor(fonsVermelles);
            }
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);

        }

        cell = new PdfPCell(new Phrase("TOTAL FALTES D'EQUIP", littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setMinimumHeight(alturacelda);
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(calcularTotalFaltas(listaeventos, "V"), littleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setColspan(6);
        table.addCell(cell);

        table.setSpacingAfter(5);
        document.add(table);

        //FIRMAS RESULTADOS
        table = new PdfPTable(15);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1, 6, 6, 6, 1, 8, 6, 6, 3, 3, 1, 6, 6, 6, 1});

        cell = new PdfPCell(new Phrase("1", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Signatures", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("RESULTAT FINAL DEL PARTIT", titleFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.RIGHT);
        cell.setVerticalAlignment(Rectangle.ALIGN_MIDDLE);
        cell.setRowspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP);
        cell.setColspan(4);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Signatures", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.TOP);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("2", normalFont));
        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
        table.addCell(cell);

        String goles = calcularTotalGoles(listaeventos, "L");
        String golestxt = "(" + pasarTexto(Integer.parseInt(goles)) + ")";
        String golesdef = " gols";
        if (goles == "1") {
            golesdef = " gol";
        }

        cell = new PdfPCell(new Phrase(acta.getFirma1_cl(), normalFont));
        if (acta.getFirma1_cl().contains("PROTESTO")) {
            cell.setBackgroundColor(fonsVermelles);
        }
        cell.setBorder(Rectangle.LEFT);
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("LOCAL", headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(goles + " " + golestxt + golesdef, bigFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setColspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(acta.getFirma1_cv(), normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setColspan(5);
        if (acta.getFirma1_cv().contains("PROTESTO")) {
            cell.setBackgroundColor(fonsVermelles);
        }
        table.addCell(cell);

//        cell = new PdfPCell(new Phrase(acta.getFirma1_cv(), normalFont));
//        cell.setBorder(Rectangle.RIGHT);
//        cell.setColspan(5);
//        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Capità", normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Capità", normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setMinimumHeight(20);
        table.addCell(cell);

        goles = calcularTotalGoles(listaeventos, "V");
        golestxt = "(" + pasarTexto(Integer.parseInt(goles)) + ")";
        if (goles == "1") {
            golesdef = " gol";
        } else {
            golesdef = " gols";
        }

        cell = new PdfPCell(new Phrase(acta.getFirma1_el(), normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setColspan(5);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("VISITANT", headerFont));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(goles + " " + golestxt + golesdef, bigFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getFirma1_ev(), normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setColspan(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Tècnic", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setColspan(4);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Tècnic", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setColspan(2);
        table.addCell(cell);

        table.setSpacingAfter(5);
        document.add(table);

        //OBSERVACIONES
        table = new PdfPTable(1);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{100});

        cell = new PdfPCell(new Phrase("OBSERVACIONS FORMULADES PER L'ÁRBITRE", normalFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getObservacions(), normalFont));
        cell.setBorder(Rectangle.BOX);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        cell.setMinimumHeight(30);
        table.addCell(cell);
        table.setSpacingAfter(5);
        document.add(table);

        //FIRMAS
        table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3, 30, 33, 30, 3});

        cell = new PdfPCell(new Phrase("1", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.TOP);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Assabentat:", normalFont));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Signatures", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Assabentat:", normalFont));
        cell.setBorder(Rectangle.TOP);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("2", normalFont));
        cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(acta.getFirma2_cl(), normalFont));
        if (acta.getFirma2_cl().contains("PROTESTO")) {
            cell.setBackgroundColor(fonsVermelles);
        }
//        cell = new PdfPCell(new Phrase(acta.getFirma2_cl(), normalFont));        
        cell.setBorder(Rectangle.RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getFirma2_ar(), normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getFirma2_cv(), normalFont));
        if (acta.getFirma2_cv().contains("PROTESTO")) {
            cell.setBackgroundColor(fonsVermelles);
        }
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setBorder(Rectangle.LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("El Capità de l'Equip", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Àrbitre 1", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("El Capità de l'Equip", normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.LEFT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        cell.setBorder(Rectangle.LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(acta.getFirma2_ar2(), normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        cell.setBorder(Rectangle.LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Àrbitre 2", normalFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("", normalFont));
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        table.addCell(cell);

        table.setSpacingAfter(5);
        document.add(table);
    }

    private void addHeaderSpace(Acta acta) throws DocumentException {
        //FIRMA DELEGADO DE PISTA		
        try {
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(30);
            table.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            table.setWidths(new float[]{100});
            PdfPCell cell = new PdfPCell(new Phrase("", normalFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setMinimumHeight(10);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Delegat de pista" + "   " + acta.getNom_cognoms_dp(), normalFont));
            cell.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.RIGHT);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(acta.getFirma1_dp(), normalFont));
            cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            cell.setVerticalAlignment(Rectangle.ALIGN_MIDDLE);
            cell.setMinimumHeight(40);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Núm. Llic." + "   " + acta.getCodlic_dp(), normalFont));
            cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.RIGHT);
            cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            table.addCell(cell);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    //prepararFuentes
    private void prepareFonts() {
        bigFont = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
        titleFont = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
        headerFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);  //Cabecera
        normalFont = new Font(FontFamily.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
        littleFont = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLACK);
        littleFont2 = new Font(FontFamily.HELVETICA, 5, Font.NORMAL, BaseColor.BLACK);
        littleFontGray = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.GRAY);
        littleFontRed = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.RED);
        littleFontBlue = new Font(FontFamily.HELVETICA, 6, Font.NORMAL, BaseColor.BLUE);
    }

    private void prepareLogo() {
        Image logo_image = null;
        try {
            logo_image = Image.getInstance(this.getClass().getClassLoader().getResource("fecapa-logo.png"));
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logo_image.setAbsolutePosition(10, 770);
        logo_image.setCompressionLevel(0);
        logo_image.setInterpolation(false);
        Watermark wm = new Watermark(logo_image);
        writer.setPageEvent(wm);

    }

    public int calcularFilasEventos(Acta acta) {
        int resultado = 6;
        if (!acta.getCodlic_l1().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l2().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l3().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l4().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l5().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l6().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l7().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l8().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l9().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l10().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l11().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l12().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l13().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l14().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l15().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l16().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_l17().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v1().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v2().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v3().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v4().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v5().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v6().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v7().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v8().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v9().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v10().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v11().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v12().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v13().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v14().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v15().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v16().equals("")) {
            resultado = resultado + 1;
        }
        if (!acta.getCodlic_v17().equals("")) {
            resultado = resultado + 1;
        }

        return resultado;
    }

    public String listaEventos(List<Object[]> listaeventos) {

        ArrayList<String> evento = new ArrayList<String>();

        String dorsal = "";

        for (Object[] object : listaeventos) {
            if (object[7].toString().equals("0")) {
                dorsal = "";
            } else {
                dorsal = "Dorsal " + object[7].toString();
            }
            evento.add("Part " + object[4].toString() + " Crono " + object[5].toString()
                    + " " + object[6].toString() + " " + object[3].toString()
                    + " " + dorsal + " " + object[8].toString());
        }
        String resultado = "";
        if (evento.size() > 0) {
            resultado = evento.get(0);
        }
        for (int i = 1; i < evento.size(); i++) {
            resultado = resultado + "\n\n" + evento.get(i);
        }
        return resultado;
    }

    public String tiempoMuerto(List<Object[]> listaeventos, String equipo) {
        String p1 = "-";
        String p2 = "-";
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Temps Mort") && object[6].toString().equals(equipo)) {
                if (!object[5].toString().equals("00:00")) { //Si crono <> 00:00
                    if (object[4].toString().equals("1")) {
                        p1 = "X";
                    }
                    if (object[4].toString().equals("2")) {
                        p2 = "X";
                    }
                }
            }
        }
        return p1 + "     " + p2;
    }

    public String calcularGoles(List<Object[]> listaeventos, Long dorsal, String equipo) {
        Integer goles = 0;
        String golestxt = "";
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Gol")
                    && object[7].toString().equals(dorsal.toString())
                    && object[6].toString().equals(equipo)) {
                goles = goles + 1;
            }
        }
        if (goles == 0) {
            golestxt = "";
        } else {
            golestxt = goles.toString();
        }
        return golestxt;
    }

    public int calcularTarjetasAzules(List<Object[]> listaeventos, Long dorsal, String equipo) {
        int goles = 0;
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Tarja Blava")
                    && object[7].toString().equals(dorsal.toString())
                    && object[6].toString().equals(equipo)) {
                goles = goles + 1;
            }
        }
        return goles;
    }

    public String calcularTarjetasRojas(List<Object[]> listaeventos, Long dorsal, String equipo) {
        Integer goles = 0;
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Tarja Vermella")
                    && object[7].toString().equals(dorsal.toString())
                    && object[6].toString().equals(equipo)) {
                goles = goles + 1;
            }
        }
        return goles.toString();
    }

    public String calcularTotalFaltas(List<Object[]> listaeventos, String equipo) {
        String total = "0";
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Total Faltes")
                    && object[6].toString().equals(equipo)) {
                total = object[8].toString();
                break;
            }
        }
        return total;
    }

    public static String calcularTotalGoles(List<Object[]> listaeventos, String equipo) {
        Integer total = 0;
        for (Object[] object : listaeventos) {
            if (object[3].toString().equals("Gol")
                    && object[6].toString().equals(equipo)) {
                total = total + 1;
            }
        }
        return total.toString();
    }

    public String pasarTexto(Integer numero) {
        String texto = "zero";
        switch (numero) {
            case 1:
                texto = "un";
                break;
            case 2:
                texto = "dos";
                break;
            case 3:
                texto = "tres";
                break;
            case 4:
                texto = "quatre";
                break;
            case 5:
                texto = "cinc";
                break;
            case 6:
                texto = "sis";
                break;
            case 7:
                texto = "set";
                break;
            case 8:
                texto = "vuit";
                break;
            case 9:
                texto = "nou";
                break;
            case 10:
                texto = "deu";
                break;
            case 11:
                texto = "onze";
                break;
            case 12:
                texto = "dotze";
                break;
            case 13:
                texto = "tretze";
                break;
            case 14:
                texto = "catorze";
                break;
            case 15:
                texto = "quinze";
                break;
            case 16:
                texto = "setze";
                break;
            case 17:
                texto = "diset";
                break;
            case 18:
                texto = "divuit";
                break;
            case 19:
                texto = "dinou";
                break;
            case 20:
                texto = "vint";
                break;
            default:
                break;
        }

        return texto;
    }

    public static void main(String[] args) {
        System.out.println("Generando...");

        Acta data = new Acta();
        data = EActa_controller.recuperarActa(161620, 0, 0);
        data.setCodpartit(161620);

        new EActaPDFBuilder().generarPDF("C:\\ACTA.pdf", data, new Locale("es"));
        System.out.println("EActa generada....");
    }

}
