<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>CCd&#39;Hoquei Patins - eACTA</title>
<!--	<link rel="stylesheet" type="text/css" href="/eACTA/Fecapa.css"/>-->
        <link rel="stylesheet" type="text/css" href="Fecapa.css"/>
	<script>
	function validar(form, accion){
		if (accion == 'cerrarsesion') {
			document.getElementById("codpartit").value = 0;
			document.getElementById("accion").value = accion;
			document.getElementById(form).submit();
		} else {
			if (document.getElementById("codpartit").value == "") {
				alert("Ha d'indicar el número de partit.");
			} else {
				if (isNaN(document.getElementById("codpartit").value)) {
					alert("Ha d'indicar el número de partit.");
				} else {
					if (accion == 'reiniciar') {
						if (confirm("Si reinicia l'acta es perdran les dades actuals. Voleu continuar?")) {
							document.getElementById("accion").value = accion;
							document.getElementById(form).submit();
						}
					} else {
						document.getElementById("accion").value = accion;
						document.getElementById(form).submit();
					}
				}
			}
		}
		return true;
	}
	</script>
</head>
<body>
	<center> 
		<table >
		  <tr>
<!--		    <td><img src="<%= request.getContextPath() %>/LogoFecapa.jpg"></td>
	    	<td><img src="<%= request.getContextPath() %>/LogoCCdHP.jpg"></td>
	    	<td><img src="<%= request.getContextPath() %>/LogoArbitres.jpg"></td>-->
                        <td align="center"><img src="<%= request.getContextPath()%>/fecapa-logo.png"></td>
		  </tr>
		</table>
		<h2>Introdueixi el codi del partit preacta</h2> 
		<form id="form_preacta" action="preacta.html" method="POST"> 
			<table> 
		 		<tr>
		    		<td>Partit</td>
		    		<td><input type="text" name="codpartit" id="codpartit" value="" style="background-color:#FFFFFF;"></td>
		 		</tr>
		 	</table>
		 	<table>
		  		<tr>
                                    <td style="text-align: center;"><input type="button" value="Crear o modificar Acta" class="btn" onClick="validar('form_preacta','crearmodif');"></td>
		    		<td style="text-align: center;"><input type="button" value="Reiniciar Acta" class="btn3" onClick="validar('form_preacta','reiniciar');"></td>
		    		<td style="text-align: center;"><input type="button" value="Sortir" class="btn4" onClick="validar('form_preacta','cerrarsesion');"></td>
		  		</tr>
			</table>
                    
                    
			<div style="display:none;">	
				<font color=red size=1> ************* DEBUG *************<br>
					ACCION:<input type="text" name="accion" id="accion"><BR>
					USUARIO:<input type="text" name="username" value="${preacta.username}">
				</font>
			</div>
		</form> 
		<h3><font color=gray>${preacta.message}</font></h3> 
		<font style="font-family: verdana;font-size: 10pt;font-style: italic;color: gray;">Versió 20161028</font>
	</center> 
</body>
</html>