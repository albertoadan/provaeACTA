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
</head>
<body> 
	<center> 
		<table>
		  <tr>
<!--		    <td><img src="<%= request.getContextPath() %>/LogoFecapa.jpg"></td>
	    	<td><img src="<%= request.getContextPath() %>/LogoCCdHP.jpg"></td>
	    	<td><img src="<%= request.getContextPath() %>/LogoArbitres.jpg"></td>-->
                       <td align="center"><img src="<%= request.getContextPath()%>/fecapa-logo.png"></td>
		  </tr>
		</table>
		<h1>Pàgina de Connexió</h1> 
		<h2>Introdueixi el seu usuari i contrasenya</h2>  
		<form action="login.html" method="POST"> 
			<table>
		 		<tr>
		    		<td>Usuari</td>
                                <td><input type="text" name="username" style="background-color:#FFFFFF;width: 100%;"   > </td>
		 		</tr>
		 		<tr>
		    		<td>Contrasenya</td>
		    		<td><input type="password" name="password" style="background-color:#FFFFFF;width: 100%;"> </td>
		  		</tr>
			</table>
			<br>
			<input type="submit" value="Acceptar" class="btn">
		</form> 
		<h3><font color=red>${message}</font></h3>
		<font style="font-family: verdana;font-size: 10pt;font-style: italic;color: gray;">Versió 20161028</font>
	</center>                
                
</body>
</html>