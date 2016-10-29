package es.fecapa.eacta.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Watermark extends PdfPageEventHelper {

	private Image image;
	
	public Watermark(Image image) {
		super();
		this.image = image;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		try {
			//watermark
			writer.getDirectContentUnder().addImage(image);
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
