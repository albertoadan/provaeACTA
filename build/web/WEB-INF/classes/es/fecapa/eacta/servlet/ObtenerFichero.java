package es.fecapa.eacta.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ObtenerFichero extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public ObtenerFichero() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String destino = request.getParameter("destino");
		File fichero=new File(destino);
		OutputStream out=response.getOutputStream();		
		if (fichero.exists()){
			String nameFinal = destino.substring(destino.lastIndexOf(File.separator)+1);
			response.setHeader("Content-Disposition", "attachment; filename="+nameFinal);
			FileInputStream reader=new FileInputStream(destino);		
			BufferedInputStream inFile = new BufferedInputStream(reader);			
			int availableLength = inFile.available();
			//Leemos el fichero al completo en memoria.
            byte[] totalBytes = new byte[availableLength];
            inFile.read(totalBytes);
            out.write(totalBytes);
			inFile.close();
			reader.close();
			fichero.delete();
		}else{			
			out.write("Archivo no encontrado.".getBytes());
		}
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
