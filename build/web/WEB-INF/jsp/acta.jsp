<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="es.fecapa.eacta.bean.Acta"%>
<%@page import="es.fecapa.eacta.bean.DatosActa"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--        <meta http-equiv="pragma" content="no-cache" />-->
        <title>CCd&#39;Hoquei Patins - eACTA</title>
        <!--        <link rel="stylesheet" type="text/css" href="/eACTA/Fecapa.css"/>-->
        <link rel="stylesheet" type="text/css" href="Fecapa.css"/>
        <script src="funcionesacta.js" type="text/javascript"></script>
        <script   src="https://code.jquery.com/jquery-3.1.1.js"    ></script>
    </head>
    <body onload="cargaInicial();"> 
        <center> 
            <table align="center" border="0">
                <tr>
      <!--	    <td><img src="<%= request.getContextPath()%>/LogoFecapa.jpg"></td>
                  <td><img src="<%= request.getContextPath()%>/LogoCCdHP.jpg"></td>
                  <td><img src="<%= request.getContextPath()%>/LogoArbitres.jpg"></td>-->
                    <td align="center"><img src="<%= request.getContextPath()%>/fecapa-logo.png"></td>
                </tr>
            </table>
            <h2>eACTA número ${acta.codpartit}</h2>
            <form id="form_acta" action="acta.html" method="POST">
           
                <div id="boton_cabecera" >
                    <input class="btn2" type="button" value="Capçalera" onClick="mostrar('cabecera'); mostrar('tornar_cabecera');" /> 
                </div>
                <div id="cabecera" style="display:none;">
                    <table>
                        <tr>
                            <td style="width: 250px;">Partit</td>
                            <td><input type="text" name="codpartit" id="codpartit" value="${acta.codpartit}" READONLY style="width:155px;"></td>

                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Temporada  </td>
                            <td><input type="text" name="temporada" id="temporada" value="${acta.temporada}" READONLY style="width:155px;"></td>
                            <td>Jornada</td>
                            <td colspan=2><input type="text" name="jornada" id="jornada" value="${acta.jornada}" READONLY style="width:100px;"></td>
                        </tr>
                        <tr>
                            <td>Població pista</td>
                            <td colspan=4><input type="text" name="poblaciojoc" id="poblaciojoc" value="${acta.poblaciojoc}" READONLY style="width:528px;"></td>
                        </tr>
                        <tr>
                            <td>Adreça pista</td>
                            <td colspan=4><input type="text" name="adressajoc" id="adressajoc" value="${acta.adressajoc}" READONLY style="width:528px;"></td>
                        </tr>
                        <tr>
                            <td>Data</td>
                            <td><input type="text" name="datajoc" id="datajoc" value="${acta.datajoc}" READONLY style="width:160px;"></td>
                            <td>Hora</td>
                            <td colspan=2><input type="text" name="horajoc" id="horajoc" value="${acta.horajoc}" READONLY style="width:100px;"></td>
                        </tr>
                        <tr>
                            <td>Competició</td>
                            <td colspan=4><input type="text" name="nomcompeticio" id="nomcompeticio" value="${acta.nomcompeticio}" READONLY style="width: 528px;"></td>
                        </tr>
                        <tr>
                            <td>Arbitre 1</td>
                            <td><input type="text" name="codlic_ar1" id="codlic_ar1" value="${acta.codlic_ar1}" READONLY style="width:155px;"></td>
                            <td colspan=2><input type="text" name="nom_cognoms_ar1" id="nom_cognoms_ar1" value="${acta.nom_cognoms_ar1}" READONLY style="width: 270px;"></td>
                            <td style="width: 92px;"><img src="<%= request.getContextPath()%>/add.png" onClick="addArbitro1();" id="add_ar1" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" onClick="deleteArbitro1();" id="del_ar1" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/help.png" onClick="mostrarArbitro();" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>Arbitre 2</td>
                            <td><input type="text" name="codlic_ar2" id="codlic_ar2" value="${acta.codlic_ar2}" READONLY style="width:155px;"></td>
                            <td colspan=2><input type="text" name="nom_cognoms_ar2" id="nom_cognoms_ar2" value="${acta.nom_cognoms_ar2}" READONLY style="width: 270px;"></td>
                            <td><img src="<%= request.getContextPath()%>/add.png" onClick="addArbitro2();"  id="add_ar2" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" onClick="deleteArbitro2();" id="del_ar2" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/help.png" onClick="mostrarArbitro();" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>Cronometrador</td>
                            <td><input type="text" name="codlic_cr" id="codlic_cr" value="${acta.codlic_cr}" READONLY style="width:155px;"></td>
                            <td colspan=2><input type="text" name="nom_cognoms_cr" id="nom_cognoms_cr" value="${acta.nom_cognoms_cr}" READONLY style="width: 270px;"></td>
                            <td><img src="<%= request.getContextPath()%>/add.png" onClick="addCronometrador();" id="add_cr" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" onClick="deleteCronometrador();" id="del_cr" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/help.png" onClick="mostrarCronometrador();" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>Delegat pista</td>
                            <td><input type="text" name="codlic_dp" id="codlic_dp" value="${acta.codlic_dp}" READONLY style="width:155px;"></td>
                            <td colspan=2><input type="text" name="nom_cognoms_dp" id="nom_cognoms_dp" value="${acta.nom_cognoms_dp}" READONLY style="width: 270px;"></td>
                            <td><img src="<%= request.getContextPath()%>/add.png" onClick="addDelegatpista();" id="add_dp" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" onClick="deleteDelegatpista();" id="del_dp" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegatpista();" style="cursor:pointer;"></td>
                        </tr>
                    </table>
                    <br>
                    <!--                        <input id="tornar_cabecera" class="btn" type="button" value="Tornar" onClick="guardaTornar('form_acta','tornarCapcalera');" /> -->
                    <input id="tornar_cabecera" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                </div>
                <div id="boton_equipolocal">
                    <br>
                    <input class="btn2" type="button" value="Equip local" onClick="mostrar('equipolocal'); mostrar('tornar_equipolocal')" /> 
                </div>
                <div id="equipolocal" style="display:none;">
                    <table>
                        <tr>
                            <td colspan=6>EQUIP LOCAL 
                                &nbsp;<input type="text" name="codequiplocal" id="codequiplocal" value="${acta.codequiplocal}" READONLY  style="width:85px;">
                                &nbsp;&nbsp;<input type="text" name="nomequiplocal" id="nomequiplocal" value="${acta.nomequiplocal}" READONLY style="width:335px;"> 
                                &nbsp;<input type="text" name="classelocal" id="classelocal" value='${acta.classelocal}' READONLY style="width:38px;">
                            </td>
                            <td style="text-align: right;"><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarJugadorlocal();" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td style="width: 40px;">P</td>
                            <td style="width: 10px;">C</td>
                            <td style="width: 70px;">Llicència</td>
                            <td style="width: 18px;">Cat.</td>
                            <td style="width: 30px;">Cognoms i Nom</td>
                            <td colspan=2 style="width: 30px;">Dorsal</td>
                        </tr>
                        <tr>
                            <td>P1<input type="text" name="posicion_l1" id="posicion_l1" value="1" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l1" id="capi_l1" value="1" ${acta.capi_l1=='1'? "checked" : ""} onClick="marcaCapitanLocal(1);"></td>
                            <td><input type="text" name="codlic_l1" id="codlic_l1" value="${acta.codlic_l1}" READONLY style="width: 160px;"></td>
                            <td><input type="text" name="cat_l1" id="cat_l1" value="${acta.cat_l1}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l1" id="nom_cognoms_l1" value="${acta.nom_cognoms_l1}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l1" id="num_l1" value="${acta.num_l1}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l1" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(1);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l1" onClick="deleteJugadorlocal(1, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J1<input type="text" name="posicion_l2" id="posicion_l2" value="2" READONLY style="width:18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l2" id="capi_l2" value="1" ${acta.capi_l2=='1'? "checked" : ""} onClick="marcaCapitanLocal(2);"></td>
                            <td><input type="text" name="codlic_l2" id="codlic_l2" value="${acta.codlic_l2}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l2" id="cat_l2" value="${acta.cat_l2}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l2" id="nom_cognoms_l2" value="${acta.nom_cognoms_l2}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l2" id="num_l2" value="${acta.num_l2}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l2" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(2);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l2" onClick="deleteJugadorlocal(2, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J2<input type="text" name="posicion_l3" id="posicion_l3" value="3" READONLY style="width:18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l3" id="capi_l3" value="1" ${acta.capi_l3=='1'? "checked" : ""} onClick="marcaCapitanLocal(3);"></td>
                            <td><input type="text" name="codlic_l3" id="codlic_l3" value="${acta.codlic_l3}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l3" id="cat_l3" value="${acta.cat_l3}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l3" id="nom_cognoms_l3" value="${acta.nom_cognoms_l3}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l3" id="num_l3" value="${acta.num_l3}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l3" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(3);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l3" onClick="deleteJugadorlocal(3, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J3<input type="text" name="posicion_l4" id="posicion_l4" value="4" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l4" id="capi_l4" value="1" ${acta.capi_l4=='1'? "checked" : ""} onClick="marcaCapitanLocal(4);"></td>
                            <td><input type="text" name="codlic_l4" id="codlic_l4" value="${acta.codlic_l4}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l4" id="cat_l4" value="${acta.cat_l4}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l4" id="nom_cognoms_l4" value="${acta.nom_cognoms_l4}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l4" id="num_l4" value="${acta.num_l4}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l4" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(4);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l4" onClick="deleteJugadorlocal(4, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J4<input type="text" name="posicion_l5" id="posicion_l5" value="5" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l5" id="capi_l5" value="1" ${acta.capi_l5=='1'? "checked" : ""} onClick="marcaCapitanLocal(5);"></td>
                            <td><input type="text" name="codlic_l5" id="codlic_l5" value="${acta.codlic_l5}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l5" id="cat_l5" value="${acta.cat_l5}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l5" id="nom_cognoms_l5" value="${acta.nom_cognoms_l5}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l5" id="num_l5" value="${acta.num_l5}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l5" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(5);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l5" onClick="deleteJugadorlocal(5, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J5<input type="text" name="posicion_l6" id="posicion_l6" value="6" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l6" id="capi_l6" value="1" ${acta.capi_l6=='1'? "checked" : ""} onClick="marcaCapitanLocal(6);"></td>
                            <td><input type="text" name="codlic_l6" id="codlic_l6" value="${acta.codlic_l6}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l6" id="cat_l6" value="${acta.cat_l6}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l6" id="nom_cognoms_l6" value="${acta.nom_cognoms_l6}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l6" id="num_l6" value="${acta.num_l6}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l6" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(6);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l6" onClick="deleteJugadorlocal(6, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J6<input type="text" name="posicion_l7" id="posicion_l7" value="7" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l7" id="capi_l7" value="1" ${acta.capi_l7=='1'? "checked" : ""} onClick="marcaCapitanLocal(7);"></td>
                            <td><input type="text" name="codlic_l7" id="codlic_l7" value="${acta.codlic_l7}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l7" id="cat_l7" value="${acta.cat_l7}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l7" id="nom_cognoms_l7" value="${acta.nom_cognoms_l7}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l7" id="num_l7" value="${acta.num_l7}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l7" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(7);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l7" onClick="deleteJugadorlocal(7, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J7<input type="text" name="posicion_l8" id="posicion_l8" value="8" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l8" id="capi_l8" value="1" ${acta.capi_l8=='1'? "checked" : ""} onClick="marcaCapitanLocal(8);"></td>
                            <td><input type="text" name="codlic_l8" id="codlic_l8" value="${acta.codlic_l8}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l8" id="cat_l8" value="${acta.cat_l8}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l8" id="nom_cognoms_l8" value="${acta.nom_cognoms_l8}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l8" id="num_l8" value="${acta.num_l8}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l8" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(8);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l8" onClick="deleteJugadorlocal(8, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J8<input type="text" name="posicion_l9" id="posicion_l9" value="9" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l9" id="capi_l9" value="1" ${acta.capi_l9=='1'? "checked" : ""} onClick="marcaCapitanLocal(9);"></td>
                            <td><input type="text" name="codlic_l9" id="codlic_l9" value="${acta.codlic_l9}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l9" id="cat_l9" value="${acta.cat_l9}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l9" id="nom_cognoms_l9" value="${acta.nom_cognoms_l9}" READONLY style="width:346px;"></td>
                            <td><input type="text" name="num_l9" id="num_l9" value="${acta.num_l9}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l9" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(9);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l9" onClick="deleteJugadorlocal(9, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>P2<input type="text" name="posicion_l10" id="posicion_l10" value="10" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_l10" id="capi_l10" value="1" ${acta.capi_l10=='1'? "checked" : ""} onClick="marcaCapitanLocal(10);"></td>
                            <td><input type="text" name="codlic_l10" id="codlic_l10" value="${acta.codlic_l10}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_l10" id="cat_l10" value="${acta.cat_l10}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_l10" id="nom_cognoms_l10" value="${acta.nom_cognoms_l10}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l10" id="num_l10" value="${acta.num_l10}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l10" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorlocal(10);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l10" onClick="deleteJugadorlocal(10, '${acta.nomequiplocal}');" style="cursor:pointer;"></td>
                        </tr>	
                        <tr style="border-top: 3px solid #bbb;">
                            <td colspan=2>T<input type="text" name="posicion_l11" id="posicion_l11" value="11" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l11" id="codlic_l11" value="${acta.codlic_l11}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarTecnicolocal();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l11" id="nom_cognoms_l11" value="${acta.nom_cognoms_l11}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l11" id="num_l11" value="${acta.num_l11}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l11" src="<%= request.getContextPath()%>/add.png" onClick="addTecnicolocal(11);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l11" onClick="deleteTecnicolocal(11, '${acta.nomequiplocal}', 'entrenador');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>D<input type="text" name="posicion_l12" id="posicion_l12" value="12" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l12" id="codlic_l12" value="${acta.codlic_l12}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadolocal(12);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l12" id="nom_cognoms_l12" value="${acta.nom_cognoms_l12}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l12" id="num_l12" value="${acta.num_l12}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l12" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadolocal(12);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l12" onClick="deleteTecnicolocal(12, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>A<input type="text" name="posicion_l13" id="posicion_l13" value="13" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l13" id="codlic_l13" value="${acta.codlic_l13}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarAuxiliarlocal(13);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l13" id="nom_cognoms_l13" value="${acta.nom_cognoms_l13}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l13" id="num_l13" value="${acta.num_l13}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l13" src="<%= request.getContextPath()%>/add.png" onClick="addAuxiliarlocal(13);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l13" onClick="deleteTecnicolocal(13, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT2<input type="text" name="posicion_l16" id="posicion_l16" value="16" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l16" id="codlic_l16" value="${acta.codlic_l16}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicolocal(16);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l16" id="nom_cognoms_l16" value="${acta.nom_cognoms_l16}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l16" id="num_l16" value="${acta.num_l16}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l16" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicolocal(16);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l16" onClick="deleteTecnicolocal(16, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT3<input type="text" name="posicion_l14" id="posicion_l14" value="14" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l14" id="codlic_l14" value="${acta.codlic_l14}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicolocal();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l14" id="nom_cognoms_l14" value="${acta.nom_cognoms_l14}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l14" id="num_l14" value="${acta.num_l14}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l14" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicolocal(14);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l14" onClick="deleteTecnicolocal(14, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT4<input type="text" name="posicion_l15" id="posicion_l15" value="16" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l15" id="codlic_l15" value="${acta.codlic_l15}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicolocal();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l15" id="nom_cognoms_l15" value="${acta.nom_cognoms_l15}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l15" id="num_l15" value="${acta.num_l15}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l15" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicolocal(15);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l15" onClick="deleteTecnicolocal(15, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT5<input type="text" name="posicion_l17" id="posicion_l17" value="17" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_l17" id="codlic_l17" value="${acta.codlic_l17}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicolocal();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_l17" id="nom_cognoms_l17" value="${acta.nom_cognoms_l17}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_l17" id="num_l17" value="${acta.num_l17}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_l17" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicolocal(17);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_l17" onClick="deleteTecnicolocal(17, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                    </table>
                    <br> 
                    <input id="tornar_equipolocal" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                    <!-- <input id="tornar_equipolocal" class="btn" type="button" value="Tornar" onClick="guardaEventsLocal('form_acta');" />  -->
                </div>
                <div id="boton_equipovisit">
                    <br>
                    <input class="btn2" type="button" value="Equip visitant" onClick="mostrar('equipovisit'); mostrar('tornar_equipovisit');" /> 
                </div>
                <div id="equipovisit" style="display:none;">
                    <table>
                        <tr>
                            <td colspan=6>EQUIP VISIT. 
                                &nbsp;&nbsp;<input type="text" name="codequipvisit" id="codequipvisit" value="${acta.codequipvisit}" READONLY style="width:85px;">
                                &nbsp;&nbsp;<input type="text" name="nomequipvisit" id="nomequipvisit" value="${acta.nomequipvisit}" READONLY style="width:335px;">
                                &nbsp;<input type="text" name="classevisit" id="classevisit" value='${acta.classevisit}' READONLY style="width:38px;"></td>
                            <td style="text-align: right;"><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarJugadorvisit();" style="cursor:pointer;"></td>

                        </tr>
                        <tr>
                            <td style="width: 40px;">P</td>
                            <td style="width: 10px;">C</td>
                            <td style="width: 70px;">Llicència</td>
                            <td style="width: 18px;">Cat.</td>
                            <td style="width: 30px;">Cognoms i Nom</td>
                            <td colspan=2 style="width: 30px;">Dorsal</td>
                        </tr>
                        <tr>
                            <td>P1<input type="text" name="posicion_v1" id="posicion_v1" value="1" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v1" id="capi_v1" value="1" ${acta.capi_v1=='1'? "checked" : ""} onClick="marcaCapitanVisit(1);"></td>
                            <td><input type="text" name="codlic_v1" id="codlic_v1" value="${acta.codlic_v1}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v1" id="cat_v1" value="${acta.cat_v1}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v1" id="nom_cognoms_v1" value="${acta.nom_cognoms_v1}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v1" id="num_v1" value="${acta.num_v1}"  style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v1" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(1);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v1" onClick="deleteJugadorvisit(1, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J1<input type="text" name="posicion_v2" id="posicion_v2" value="2" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v2" id="capi_v2" value="1" ${acta.capi_v2=='1'? "checked" : ""} onClick="marcaCapitanVisit(2);"></td>
                            <td><input type="text" name="codlic_v2" id="codlic_v2" value="${acta.codlic_v2}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v2" id="cat_v2" value="${acta.cat_v2}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v2" id="nom_cognoms_v2" value="${acta.nom_cognoms_v2}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v2" id="num_v2" value="${acta.num_v2}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v2" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(2);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v2" onClick="deleteJugadorvisit(2, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J2<input type="text" name="posicion_v3" id="posicion_v3" value="3" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v3" id="capi_v3" value="1" ${acta.capi_v3=='1'? "checked" : ""} onClick="marcaCapitanVisit(3);"></td>
                            <td><input type="text" name="codlic_v3" id="codlic_v3" value="${acta.codlic_v3}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v3" id="cat_v3" value="${acta.cat_v3}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v3" id="nom_cognoms_v3" value="${acta.nom_cognoms_v3}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v3" id="num_v3" value="${acta.num_v3}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v3" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(3);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v3" onClick="deleteJugadorvisit(3, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J3<input type="text" name="posicion_v4" id="posicion_v4" value="4" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v4" id="capi_v4" value="1" ${acta.capi_v4=='1'? "checked" : ""} onClick="marcaCapitanVisit(4);"></td>
                            <td><input type="text" name="codlic_v4" id="codlic_v4" value="${acta.codlic_v4}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v4" id="cat_v4" value="${acta.cat_v4}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v4" id="nom_cognoms_v4" value="${acta.nom_cognoms_v4}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v4" id="num_v4" value="${acta.num_v4}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v4" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(4);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v4" onClick="deleteJugadorvisit(4, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J4<input type="text" name="posicion_v5" id="posicion_v5" value="5" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v5" id="capi_v5" value="1" ${acta.capi_v5=='1'? "checked" : ""} onClick="marcaCapitanVisit(5);"></td>
                            <td><input type="text" name="codlic_v5" id="codlic_v5" value="${acta.codlic_v5}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v5" id="cat_v5" value="${acta.cat_v5}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v5" id="nom_cognoms_v5" value="${acta.nom_cognoms_v5}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v5" id="num_v5" value="${acta.num_v5}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v5" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(5);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v5" onClick="deleteJugadorvisit(5, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J5<input type="text" name="posicion_v6" id="posicion_v6" value="6" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v6" id="capi_v6" value="1" ${acta.capi_v6=='1'? "checked" : ""} onClick="marcaCapitanVisit(6);"></td>
                            <td><input type="text" name="codlic_v6" id="codlic_v6" value="${acta.codlic_v6}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v6" id="cat_v6" value="${acta.cat_v6}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v6" id="nom_cognoms_v6" value="${acta.nom_cognoms_v6}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v6" id="num_v6" value="${acta.num_v6}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v6" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(6);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v6" onClick="deleteJugadorvisit(6, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J6<input type="text" name="posicion_v7" id="posicion_v7" value="7" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v7" id="capi_v7" value="1" ${acta.capi_v7=='1'? "checked" : ""} onClick="marcaCapitanVisit(7);"></td>
                            <td><input type="text" name="codlic_v7" id="codlic_v7" value="${acta.codlic_v7}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v7" id="cat_v7" value="${acta.cat_v7}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v7" id="nom_cognoms_v7" value="${acta.nom_cognoms_v7}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v7" id="num_v7" value="${acta.num_v7}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v7" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(7);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v7" onClick="deleteJugadorvisit(7, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J7<input type="text" name="posicion_v8" id="posicion_v8" value="8" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v8" id="capi_v8" value="1" ${acta.capi_v8=='1'? "checked" : ""} onClick="marcaCapitanVisit(8);"></td>
                            <td><input type="text" name="codlic_v8" id="codlic_v8" value="${acta.codlic_v8}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v8" id="cat_v8" value="${acta.cat_v8}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v8" id="nom_cognoms_v8" value="${acta.nom_cognoms_v8}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v8" id="num_v8" value="${acta.num_v8}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v8" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(8);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v8" onClick="deleteJugadorvisit(8, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>J8<input type="text" name="posicion_v9" id="posicion_v9" value="9" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v9" id="capi_v9" value="1" ${acta.capi_v9=='1'? "checked" : ""} onClick="marcaCapitanVisit(9);"></td>
                            <td><input type="text" name="codlic_v9" id="codlic_v9" value="${acta.codlic_v9}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v9" id="cat_v9" value="${acta.cat_v9}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v9" id="nom_cognoms_v9" value="${acta.nom_cognoms_v9}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v9" id="num_v9" value="${acta.num_v9}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v9" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(9);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v9" onClick="deleteJugadorvisit(9, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td>P2<input type="text" name="posicion_v10" id="posicion_v10" value="10" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="checkbox" name="capi_v10" id="capi_v10" value="1" ${acta.capi_v10=='1'? "checked" : ""} onClick="marcaCapitanVisit(10);"></td>
                            <td><input type="text" name="codlic_v10" id="codlic_v10" value="${acta.codlic_v10}" READONLY style="width:160px;"></td>
                            <td><input type="text" name="cat_v10" id="cat_v10" value="${acta.cat_v10}" READONLY style="width:55px;"></td>
                            <td><input type="text" name="nom_cognoms_v10" id="nom_cognoms_v10" value="${acta.nom_cognoms_v10}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v10" id="num_v10" value="${acta.num_v10}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v10" src="<%= request.getContextPath()%>/add.png" onClick="addJugadorvisit(10);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v10" onClick="deleteJugadorvisit(10, '${acta.nomequipvisit}');" style="cursor:pointer;"></td>
                        </tr>
                        <tr style="border-top: 3px solid #bbb;">
                            <td colspan=2>T<input type="text" name="posicion_v11" id="posicion_v11" value="11" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v11" id="codlic_v11" value="${acta.codlic_v11}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarTecnicovisit();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v11" id="nom_cognoms_v11" value="${acta.nom_cognoms_v11}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v11" id="num_v11" value="${acta.num_v11}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v11" src="<%= request.getContextPath()%>/add.png" onClick="addTecnicovisit(11);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v11" onClick="deleteTecnicovisit(11, '${acta.nomequiplocal}', 'entrenador');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>D<input type="text" name="posicion_v12" id="posicion_v12" value="12" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v12" id="codlic_v12" value="${acta.codlic_v12}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadovisit(12);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v12" id="nom_cognoms_v12" value="${acta.nom_cognoms_v12}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v12" id="num_v12" value="${acta.num_v12}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v12" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadovisit(12);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v12" onClick="deleteTecnicovisit(12, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>A<input type="text" name="posicion_v13" id="posicion_v13" value="13" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v13" id="codlic_v13" value="${acta.codlic_v13}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarAuxiliarvisit(13);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v13" id="nom_cognoms_v13" value="${acta.nom_cognoms_v13}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v13" id="num_v13" value="${acta.num_v13}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v13" src="<%= request.getContextPath()%>/add.png" onClick="addAuxiliarvisit(13);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v13" onClick="deleteTecnicovisit(13, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT2<input type="text" name="posicion_v16" id="posicion_v16" value="16" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v16" id="codlic_v16" value="${acta.codlic_v16}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicovisit(16);" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v16" id="nom_cognoms_v16" value="${acta.nom_cognoms_v16}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v16" id="num_v16" value="${acta.num_v16}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v16" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicovisit(16);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v16" onClick="deleteTecnicovisit(16, '${acta.nomequiplocal}', 'delegat');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT3<input type="text" name="posicion_v14" id="posicion_v14" value="14" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v14" id="codlic_v14" value="${acta.codlic_v14}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicovisit();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v14" id="nom_cognoms_v14" value="${acta.nom_cognoms_v14}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v14" id="num_v14" value="${acta.num_v14}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v14" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicovisit(14);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v14" onClick="deleteTecnicovisit(14, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT4<input type="text" name="posicion_v15" id="posicion_v15" value="15" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v15" id="codlic_v15" value="${acta.codlic_v15}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicovisit();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v15" id="nom_cognoms_v15" value="${acta.nom_cognoms_v15}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v15" id="num_v15" value="${acta.num_v15}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v15" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicovisit(15);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v15" onClick="deleteTecnicovisit(15, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                        <tr>
                            <td colspan=2>DAT5<input type="text" name="posicion_v17" id="posicion_v17" value="17" READONLY style="width: 18px;display:none;"></td>
                            <td><input type="text" name="codlic_v17" id="codlic_v17" value="${acta.codlic_v17}" READONLY style="width:160px;"></td>
                            <td><img src="<%= request.getContextPath()%>/help.png" onClick="mostrarDelegadoAuxiliarTecnicovisit();" style="cursor:pointer;"></td>
                            <td><input type="text" name="nom_cognoms_v17" id="nom_cognoms_v17" value="${acta.nom_cognoms_v17}" READONLY style="width: 346px;"></td>
                            <td><input type="text" name="num_v17" id="num_v17" value="${acta.num_v17}" style="background-color: #FFFFFF;width:40px;" onfocus="focusDorsal(this);" onBlur="blurDorsal(this);" onChange="cambiarDorsal(this);"></td>
                            <td style="text-align: right;"><img class="mas" id="add_v17" src="<%= request.getContextPath()%>/add.png" onClick="addDelegadoAuxiliarTecnicovisit(17);" style="cursor:pointer;">
                                <img src="<%= request.getContextPath()%>/minus.png" id="del_v17" onClick="deleteTecnicovisit(17, '${acta.nomequiplocal}', 'auxiliar');" style="cursor:pointer;">
                            </td>
                        </tr>
                    </table>
                    <br> 
                    <!--                        	<input id="tornar_equipovisit" class="btn" type="button" value="Tornar" onClick="guardaEventsVisitant('form_acta');" /> -->
                    <input id="tornar_equipovisit" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                </div>
                <div id="boton_firmaspre">
                    <br>
                    <input class="btn2" type="button" value="Signatures Pre-Partit" onClick="mostrarAnteriores('firmaspre'); mostrar('firmaspre');" /> 
                </div>
                <div id="firmaspre" style="display:none;">
                    <table>
                        <tr>
                            <td colspan=4>INICI DE PARTIT </td>
                        </tr>
                        <tr>
                            <td style="width: 300px;">Delegat de pista</td>
                            <td colspan=3 style="text-align: right;">
<!--                                 <div id="prova" style="display:'none'" value="prova"> </div>-->
                                <div id="lbfirma1pin_dp" style="display:${acta.firma1_dp==''?'initial':'none'};">Pin:</div>
                                <input type="password" name="firma1pin_dp" id="firma1pin_dp" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma1_dp==''?'initial':'none'};" />
<!--                                <input class="btn" name="btfirma1pin_dp" id="btfirma1pin_dp" type="button" value="Signar" onClick="firmarDelegadospista('form_acta');" style="width: 150px;display:${acta.firma1_dp==''?'initial':'none'};" />-->
                                <div id="txtfirma1pin_dp" style="font-style: italic;font-size:12pt;">${acta.firma1_dp}</div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan=4>ALINEACIÓ</td>
                        </tr>
                        <tr>
                            <td>Capità local</td>
                            <td colspan=3 style="text-align: right;">
                                <span id="prfirma1pin_cl" style="display:${acta.firma1_cl==''?"initial":"none"};"><input type="checkbox" name="protesto1_cl" id="protesto1_cl" value="1">Protesto</input><input type="checkbox" name="assabentat1_cl" id="assabentat1_cl" value="1">Assabentat</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <br><div id="lbfirma1pin_cl" style="display:${acta.firma1_cl==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma1pin_cl" id="firma1pin_cl" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma1_cl==''?"initial":"none"};" />
<!--                                <input name="btfirma1pin_cl" id="btfirma1pin_cl" class="btn" type="button" value="Signar" onClick="firmar1Capitanlocal('form_acta');" style="width: 150px;display:${acta.firma1_cl==''?"initial":"none"};" />-->
                                <div id="txtfirma1pin_cl" style="font-style: italic;font-size:12pt;">${acta.firma1_cl}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>Tècnic local</td>
                            <td colspan=3 style="text-align: right;">
                                <div id="lbfirma1pin_el" style="display:${acta.firma1_el==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma1pin_el" id="firma1pin_el" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma1_el==''?"initial":"none"};" />
<!--                                <input name="btfirma1pin_el" id="btfirma1pin_el" class="btn" type="button" value="Signar" onClick="firmar1Entrenadorlocal('form_acta');" style="width: 150px;display:${acta.firma1_el==''?"initial":"none"};" />-->
                                <div id="txtfirma1pin_el" style="font-style: italic;font-size:12pt;">${acta.firma1_el}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>Capità visitant</td>
                            <td colspan=3 style="text-align: right;">
                                <span id="prfirma1pin_cv" style="display:${acta.firma1_cv==''?"initial":"none"};"><input type="checkbox" name="protesto1_cv" id="protesto1_cv" value="1">Protesto</input><input type="checkbox" name="assabentat1_cv" id="assabentat1_cv" value="1">Assabentat</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <br><div id="lbfirma1pin_cv" style="display:${acta.firma1_cv==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma1pin_cv" id="firma1pin_cv" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma1_cv==''?"initial":"none"};" />
<!--                                <input id="btfirma1pin_cv" class="btn" type="button" value="Signar" onClick="firmar1Capitanvisit('form_acta');" style="width: 150px;display:${acta.firma1_cv==''?"initial":"none"};" />-->
                                <div id="txtfirma1pin_cv" style="font-style: italic;font-size:12pt;">${acta.firma1_cv}</div>
                            </td>
                        </tr>
                        <tr> 
                            <td>Tècnic visitant</td>
                            <td colspan=3 style="text-align: right;">
                                <div id="lbfirma1pin_ev"  style="display:${acta.firma1_ev==''?"initial":"none"};">Pin:</div>
                                <input name="firma1pin_ev" id="firma1pin_ev" type="password" name="firma1pin_ev" id="firma1pin_ev" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma1_ev==''?"initial":"none"};" />
<!--                                <input name="btfirma1pin_ev" id="btfirma1pin_ev" class="btn" type="button" value="Signar" onClick="firmar1Entrenadorvisit('form_acta');" style="width: 150px;display:${acta.firma1_ev==''?'initial':'none'};" />-->
                                <div id="txtfirma1pin_ev" style="font-style: italic;font-size:12pt;">${acta.firma1_ev}</div>
                            </td>
                        </tr>                       
                    </table>
                    <br>
                     <input   name="btfirma1pin_cl" id="btfirma1pin_cl" class="btn2" type="button" value="Signar" onClick="firmas1('form_acta');"  />
                     &nbsp;&nbsp;
                    <input class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
<!--                    <input class="btn" type="button" value="Tornar" onClick="tornarFirmas1('form_acta');" />-->
                </div>
                <div id="boton_eventos">
                    <br>
                    <input id="boton_eventos" class="btn" type="button" value="Llista d'Events" onClick="mostrar('eventos'); mostrar('tornar_eventos');" /> 
                </div>
                <div id="eventos" style="display:none;">
                    <table id="taulaEvents">
                        <tr>
                            <td colspan=7>EVENTS DEL PARTIT </td>
                            <td style="width: 20px;"><img src="<%= request.getContextPath()%>/add.png" id="add_event" onClick="addEvento();" style="cursor:pointer;"></td>
                        </tr>
                        <tr>
                            <td style="width: 20px;">Part</td>
                            <td style="width: 20px;">Crono</td>
                            <td style="width: 200px;">Event</td>
                            <td style="width: 20px;">Equip</td>
                            <td style="width: 20px;">Dor.</td>
                            <td style="width: 180px;">Atribut</td>
                            <td style="width: 20px;"></td>
                            <td style="width: 20px;"></td>
                        </tr>
                        <tr>
                            <td style="line-height: 35px;font-weight: normal;">
                                <c:forEach items="${acta.evento_parte}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;">
                                <c:forEach items="${acta.evento_crono}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;" >
                                <c:forEach items="${acta.evento_tipo}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;">
                                <c:forEach items="${acta.evento_equipo}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;">
                                <c:forEach items="${acta.evento_dorsal}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;"> 
                                <c:forEach items="${acta.evento_atributop}" var="item">
                                    ${item}<br>
                                </c:forEach>
                            </td>
                            <td style="line-height: 35px;font-weight: normal;"> 
                                <c:forEach items="${acta.evento_atributo}" var="item">
                                    <img src="<%= request.getContextPath()%>/info.png" title="${item}" style="cursor:pointer;padding-top:6px;padding-bottom:6px;">
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${acta.evento_id}" var="item"> 
                                    <img src="<%= request.getContextPath()%>/minus.png" id="del_event" onClick="deleteEvento('form_acta', ${item});" style="cursor:pointer;padding-top:6px;padding-bottom:6px;">
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>		
                    <br>
                    <!--                        <input id="tornar_eventos" class="btn" type="button" value="Tornar" onClick="guardaTornar('form_acta', 'tornarLlistaEvents');" /> -->
                    <input id="tornar_eventos" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" />  
                </div>
                <div id="nuevoevento" style="display:none;">
                    <table>
                        <tr>
                            <td  style="width: 50px;">Part</td>
                            <td style="width: 368px;">
                                <input type="radio" name="neventoparte" id="neventoparte" value="1" ${acta.neventoparte=='1'||acta.neventoparte==null? "checked" : ""}>1
                                <input type="radio" name="neventoparte" id="neventoparte" value="2" ${acta.neventoparte=='2'? "checked" : ""}>2
                                <input type="radio" name="neventoparte" id="neventoparte" value="3" ${acta.neventoparte=='3'? "checked" : ""}>3
                                <input type="radio" name="neventoparte" id="neventoparte" value="A" ${acta.neventoparte=='A'? "checked" : ""}>A
                                <input type="radio" name="neventoparte" id="neventoparte" value="B" ${acta.neventoparte=='B'? "checked" : ""}>B
                                <input type="radio" name="neventoparte" id="neventoparte" value="P" ${acta.neventoparte=='P'? "checked" : ""}>P
                            </td>
                            <td style="width: 215px;">Crono (00:00)</td>
                            <td><input type="text" name="neventocrono" id="neventocrono" value="" style="width:70px;background-color: #FFFFFF;"></td>
                        </tr>

                        <tr>
                            <td>Equip</td>
                            <td>
                                <input type="radio" name="neventoequipo" id="neventoequipo" value="L" checked="checked">Local
                                <input type="radio" name="neventoequipo" id="neventoequipo" value="V">Visitant
                            </td>
                            <td>Dorsal</td>
                            <td><input type="text" name="neventodorsal" id="neventodorsal" value="" style="width:70px;background-color: #FFFFFF;"></td>
                        </tr>
                        <tr>
                            <td>Event</td>
                            <td colspan=3>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Hora Inici Real">Hora Inici Real</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Servei Inicial">Servei inicial</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Gol" checked="checked">Gol</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Directa">Directa</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Penal">Penal</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Tarja Blava">Blava
                                <input type="radio" name="neventotipo" id="neventotipo" value="Tarja Vermella">Vermella</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Temps Mort">Temps Mort</br>
                                <input type="radio" name="neventotipo" id="neventotipo" value="Total Faltes">Total Faltes</br>
                            </td>
                        </tr>
                        <tr>
                            <td>Atribut </td>
                            <td colspan=3><input type="text" name="neventoatribut" id="neventoatribut" value="" style="width:635px;background-color: #FFFFFF;">
                                <img src="<%= request.getContextPath()%>/help.png" onClick="ayudaEventos();" style="cursor:pointer;"></td>
                        </tr>
                    </table>
                    <br><br>
                    <input class="btn" type="button" value="Afegir" onClick="aceptarEvento('form_acta');" /> 
                    <input class="btn" type="button" value="Descartar" onClick="descartarEvento();" /> 
                </div>
                <div id="boton_geneventos">
                    <br>
                    <input id="boton_geneventos" class="btn" type="button" value="Events del partit" onClick="mostrar('geneventos'); mostrar('tornar_geneventos');" /> 
                </div>
                <div id="geneventos" style="display:none;">
                    <table style="border:2px solid #3498db;"><tr><td>
                                <table>
                                    <tr>
                                        <td colspan=6>EVENTS DEL PARTIT</td>
                                    </tr>
                                    <tr>
                                        <td>Hora d'Inici Real</td>
                                        <td style="width: 20px;" colspan=5><input type="text" name="horainicio" id="horainicio" value="${acta.horainicio}" style="width: 70px;" onFocus="this.select();"></td>
                                    </tr>
                                    <tr>
                                        <td>Servei Inicial</td>
                                        <td colspan=5>
                                            <input type="radio" name="saqueinicial" id="saqueinicial" value="L" ${acta.saqueinicial=='L'? "checked" : ""}>Local
                                            <input type="radio" name="saqueinicial" id="saqueinicial" value="V" ${acta.saqueinicial=='V'? "checked" : ""}>Visitant
                                        </td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td colspan=2>Local</td>
                                        <td></td>
                                        <td colspan=2>Visitant</td>
                                    </tr>
                                    <tr>
                                        <td>Temps mort</td>
                                        <td style="width: 110px;"><input type="text" name="tiempomuertol1" id="tiempomuertol1" value="${acta.tiempomuertol1}" style="width: 70px;" onFocus="this.select();"></td>
                                        <td style="width: 110px;"><input type="text" name="tiempomuertol2" id="tiempomuertol2" value="${acta.tiempomuertol2}" style="width: 70px;" onFocus="this.select();"></td>
                                        <td></td>
                                        <td style="width: 110px;"><input type="text" name="tiempomuertov1" id="tiempomuertov1" value="${acta.tiempomuertov1}" style="width: 70px;" onFocus="this.select();"></td>
                                        <td style="width: 110px;"><input type="text" name="tiempomuertov2" id="tiempomuertov2" value="${acta.tiempomuertov2}" style="width: 70px;" onFocus="this.select();"></td>
                                    </tr>
                                    <tr>
                                        <td>Total faltes</td>
                                        <td></td>
                                        <td><input type="text" name="totalfaltasl" id="totalfaltasl" value="${acta.totalfaltasl}" style="width: 70px;"></td>
                                        <td></td>
                                        <td></td>
                                        <td><input type="text" name="totalfaltasv" id="totalfaltasv" value="${acta.totalfaltasv}" style="width: 70px;"></td>
                                    </tr> 
                                    <tr>
                                        <td>Resultat (gols)</td>
                                        <td></td>
                                        <td><input type="text" name="totalgolesl" id="totalgolesl" value="${acta.totalgolesl}" style="width: 70px;" onChange="recalcularCamposGolesL(this)" onFocus="focusTotalGolesL(this)"></td>
                                        <td></td>
                                        <td></td>
                                        <td><input type="text" name="totalgolesv" id="totalgolesv" value="${acta.totalgolesv}" style="width: 70px;" onChange="recalcularCamposGolesV(this)" onFocus="focusTotalGolesV(this)"></td>

                                    </tr>
                                </table>		
                                <br>
                                <table>
                                    <tr>
                                        <td colspan=7>GOLS</td>
                                    </tr>
                                    <tr>
                                        <td colspan=3>Local</td>
                                        <td></td>
                                        <td colspan=3>Visitant</td>
                                    </tr>
                                    <tr>
                                        <td style="width: 110px;">Part</td>
                                        <td style="width: 110px;">Crono</td>
                                        <td style="width: 110px;">Dorsal</td>
                                        <td style="width: 350px;"></td>
                                        <td style="width: 110px;">Part</td>
                                        <td style="width: 110px;">Crono</td>
                                        <td style="width: 110px;">Dorsal</td>
                                    </tr>
                                    <tr style="display:none;">
                                        <td><input type="text" name="cgolespl" id="cgolespl" value="${acta.golespl}" style="width: 100px;"></td>
                                        <td><input type="text" name="cgolesml" id="cgolesml" value="${acta.golesml}" style="width: 70px;"></td>
                                        <td><input type="text" name="cgolesdl" id="cgolesdl" value="${acta.golesdl}" style="width: 70px;"></td>
                                        <td></td>
                                        <td><input type="text" name="cgolespv" id="cgolespv" value="${acta.golespv}" style="width: 70px;"></td>
                                        <td><input type="text" name="cgolesmv" id="cgolesmv" value="${acta.golesmv}" style="width: 70px;"></td>
                                        <td><input type="text" name="cgolesdv" id="cgolesdv" value="${acta.golesdv}" style="width: 70px;"></td>
                                    </tr>
                                    <tr style="vertical-align:top;">
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golespl}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolespl_${count}" id="cgolespl_${count}" value="${item}" style="width: 70px;">
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golesml}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolesml_${count}" id="cgolesml_${count}" value="${item}" style="width: 70px;"   onFocus="this.select();">
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golesdl}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolesdl_${count}" id="cgolesdl_${count}" value="${item}" style="width: 70px;">
                                            </c:forEach>
                                        </td>
                                        <td></td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golespv}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolespv_${count}" id="cgolespv_${count}" value="${item}" style="width: 70px;">
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golesmv}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolesmv_${count}" id="cgolesmv_${count}" value="${item}" style="width: 70px;" onFocus="this.select();">
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:set var="count" value="0" scope="page" />
                                            <c:forEach items="${acta.golesdv}" var="item">
                                                <c:set var="count" value="${count + 1}" scope="page"/>
                                                <input type="text" name="cgolesdv_${count}" id="cgolesdv_${count}" value="${item}" style="width: 70px;">
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                                <br>
                                <table style="border:0px;">
                                    <tr>
                                                  <td style="text-align:center;border:0px;"><input id="boton_guardar" class="btn2" type="button" value="Guardar" onclick="eventsPartit()" style="width:160px;font-size:17pt;"/></td>
<!--                                        <td style="text-align:center;border:0px;"><input id="boton_guardar" class="btn2" type="button" value="Guardar" onClick="enviarForm('form_acta', 'guardar')" style="width:160px;font-size:17pt;"/></td>-->
                                    </tr>
                                </table>
                            </td></tr></table>
                    <br>
                    <table style="border:2px solid #3498db;"><tr><td>
                                <table>
                                    <tr>
                                        <td colspan=5>TARJETES BLAVES</td>
                                    </tr>
                                    <tr>
                                        <td style="width: 140px;">Equip</td>
                                        <td style="width: 110px;">Part</td>
                                        <td style="width: 110px;">Crono</td>
                                        <td style="width: 110px;">Dorsal</td>
                                        <td style="width: 350px;">Motiu</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="radio" name="faltasaequipo" id="faltasaequipo" value="L" checked="checked">L
                                            <input type="radio" name="faltasaequipo" id="faltasaequipo" value="V">V
                                        </td>
                                        <td><input type="text" name="cfaltasap" id="cfaltasap" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasam" id="cfaltasam" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasad" id="cfaltasad" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasar" id="cfaltasar" style="width: 300px;"></td>
                                    </tr>
                                    <tr>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasae}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasap}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasam}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasad}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasar}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                                <br>
                                <table style="border:0px;">
                                    <tr>
                                        <td style="text-align:center;border:0px;"><input id="add_event" class="btn2" type="button" value="Guardar" onClick="addEventoTAzul('form_acta');" style="width:160px;font-size:17pt;"/></td>
                                    </tr>
                                </table>
                            </td></tr></table>
                    <br>
                    <table style="border:2px solid #3498db;"><tr><td>
                                <table>
                                    <tr>
                                        <td colspan=5>TARJETES VERMELLES</td>
                                    </tr>
                                    <tr>
                                        <td style="width: 140px;">Equip</td>
                                        <td style="width: 110px;">Part</td>
                                        <td style="width: 110px;">Crono</td>
                                        <td style="width: 110px;">Dorsal</td>
                                        <td style="width: 350px;">Motiu</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="radio" name="faltasrequipo" id="faltasrequipo" value="L" checked="checked">L
                                            <input type="radio" name="faltasrequipo" id="faltasrequipo" value="V">V
                                        </td>
                                        <td><input type="text" name="cfaltasrp" id="cfaltasrp" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasrm" id="cfaltasrm" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasrd" id="cfaltasrd" style="width: 70px;"></td>
                                        <td><input type="text" name="cfaltasrr" id="cfaltasrr" style="width: 300px;"></td>
                                    </tr>
                                    <tr>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasre}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasrp}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasrm}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasrd}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                        <td style="line-height: 35px;font-weight: normal;">
                                            <c:forEach items="${acta.faltasrr}" var="item">
                                                ${item}<br>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </table>
                                <br>
                                <table style="border:0px;">
                                    <tr>
                                        <td style="text-align:center;border:0px;"><input id="add_event" class="btn2" type="button" value="Guardar" onClick="addEventoTRoja('form_acta');" style="width:160px;font-size:17pt;"/></td>
                                    </tr>
                                </table>
                            </td></tr></table>
                    <br>
                    <input id="tornar_geneventos" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                </div>
                <div id="boton_observaciones">
                    <br>
                    <input id="boton_observaciones" class="btn" type="button" value="Observacions" onClick="mostrar('observaciones'); mostrar('tornar_observaciones');"  />
                </div> 
                <div id="observaciones" style="display:none;">
                    <table>
                        <tr>
                            <td colspan=6>OBSERVACIONS FORMULADES PER L'ARBITRE</td>
                        </tr>
                        <tr>
                            <td colspan=6><textarea  rows="10" cols="40" maxlength="999" name="observacions" id="observacions" style="width:98%;">${acta.observacions}</textarea></td>
                        </tr>
                    </table>
                    <br>
                    <!--                        <input id="tornar_observaciones" class="btn" type="button" value="Tornar" onClick="guardaObservacions('form_acta');" /> -->
                    <input id="tornar_observaciones" class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                </div>
                <div id="boton_firmas">
                    <br>
                    <input class="btn" type="button" value="Signatures final partit" onClick="mostrarAnteriores('firmas'); mostrar('firmas');" /> 
                </div>
                <div id="firmas" style="display:none;">
                    <table>
                        <tr>
                            <td colspan=4>RESULTAT</td>
                        </tr>
                        <tr>
                            <td style="width: 300px;">Capità local</td>
                            <td colspan=3 style="text-align: right;">
                                <span id="prfirma2pin_cl" style="display:${acta.firma2_cl==''?"initial":"none"};"><input type="checkbox" name="protesto2_cl" id="protesto2_cl" value="1" >Protesto</input><input type="checkbox" name="assabentat2_cl" id="assabentat2_cl" value="1">Assabentat</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <div id="lbfirma2pin_cl" style="display:${acta.firma2_cl==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma2pin_cl" id="firma2pin_cl" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma2_cl==''?"initial":"none"};" />
<!--                                <input name="btfirma2pin_cl" id="btfirma2pin_cl" class="btn" type="button" value="Signar" onClick="firmar2Capitanlocal('form_acta');" style="width: 150px;display:${acta.firma2_cl==''?"initial":"none"};" />-->
               
                                <div id="txtfirma2pin_cl" style="font-style: italic;font-size:12pt;">${acta.firma2_cl}</div>
                            </td>
                        </tr>                     
                        <tr>
                            <td>Arbitre</td>
                            <td colspan=3 style="text-align: right;">
                                <div id="lbfirma2pin_ar" style="display:${acta.firma2_ar==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma2pin_ar" id="firma2pin_ar" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma2_ar==''?"initial":"none"};" />
<!--                                <input name="btfirma2pin_ar" id="btfirma2pin_ar" class="btn" type="button" value="Signar" onClick="firmar2Arbitro('form_acta');" style="width: 150px;display:${acta.firma2_ar==''?"initial":"none"};" />-->
                                <div id="txtfirma2pin_ar" style="font-style: italic;font-size:12pt;">${acta.firma2_ar}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>Arbitre auxiliar</td>
                            <td colspan=3 style="text-align: right;">
                                <div id="lbfirma2pin_ar2" style="display:${acta.firma2_ar2==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma2pin_ar2" id="firma2pin_ar2" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma2_ar2==''?"initial":"none"};" />
<!--                                <input name="btfirma2pin_ar2" id="btfirma2pin_ar2" class="btn" type="button" value="Signar" onClick="firmar2Arbitro2('form_acta');" style="width: 150px;display:${acta.firma2_ar2==''?"initial":"none"};" />-->
                                <div id="txtfirma2pin_ar2" style="font-style: italic;font-size:12pt;">${acta.firma2_ar2}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>Capità visitant</td>
                            <td colspan=3 style="text-align: right;">
                                <span id="prfirma2pin_cv" style="display:${acta.firma2_cv==''?"initial":"none"};"><input type="checkbox" name="protesto2_cv" id="protesto2_cv" value="1" >Protesto</input><input type="checkbox" name="assabentat2_cv" id="assabentat2_cv" value="1">Assabentat</input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                                <div id="lbfirma2pin_cv" style="display:${acta.firma2_cv==''?"initial":"none"};">Pin:</div>
                                <input type="password" name="firma2pin_cv" id="firma2pin_cv" value="" style="width:60px;background-color: #FFFFFF;display:${acta.firma2_cv==''?"initial":"none"};" />
<!--                                <input name="btfirma2pin_cv" id="btfirma2pin_cv" class="btn" type="button" value="Signar" onClick="firmar2Capitanvisit('form_acta');" style="width: 150px;display:${acta.firma2_cv==''?"initial":"none"};" />-->
                                <div id="txtfirma2pin_cv" style="font-style: italic;font-size:12pt;">${acta.firma2_cv}</div>
                            </td>
                        </tr>
                    </table> 
                    <br>
                    <input   name="btfirma2pin_cv" id="btfirma2pin_cv" class="btn2" type="button" value="Signar" onClick="firmas2('form_acta');"  />
                     &nbsp;&nbsp;
                    <input class="btn" type="button" value="Tornar" onClick="mostrarBotones();" /> 
                </div>
                <br>
                <div style="display:${acta.estado=='A'?'visible':'none'};">
                    <input id="boton_preview" class="btn2" type="button" value="Previsualitzar Acta" onClick="enviarForm('form_acta', 'exportar')" style="display:${acta.estado=='A'?'visible':'none'};"/> 
<!--                    <input id="boton_preview" class="btn2" type="button" value="Previsualitzar Acta"  style="display:${acta.estado=='A'?'visible':'none'};"/>-->
                    <br><br>
                </div>

                <div style="display:${acta.estado=='C'?'none':'visible'};">
                    <input id="boton_cerrar" class="btn2" type="button" value="Finalitzar Acta" onClick="enviarForm('form_acta', 'cerrar')" style="display:${acta.estado=='C'?'none':'visible'};"/> 
                    <br><br>
                </div>                       

                <div style="display:${acta.estado=='A'?'none':'visible'};">
                    <!--                    <input id="boton_descargar" class="btn2" type="button" value="Descarregar Acta" 
                                               onClick="window.open('http://31.47.76.83:8080/pdf/ACTA_${acta.codpartit}.pdf')" style="display:${acta.estado=='A'?'none':'visible'};"/> -->

                    <input id="boton_descargar" class="btn2" type="button" value="Descarregar Acta"  onClick="window.open('../pdf/ACTA_${acta.codpartit}.pdf')"
                           style="display:${acta.estado=='A'?'none':'visible'};"/> 
                    <br><br>
                </div>


                <div style="display:${acta.estado=='A'?'visible':'none'};">
                    <input id="boton_salirguardar" class="btn2" type="button" value="Guardar i Sortir" onClick="enviarForm('form_acta', 'guardarsalir')" style="display:${acta.estado=='A'?'visible':'none'};"/> 
                </div>
                <br>
                <input id="boton_salir" class="btn2" type="button" value="Sortir sense guardar" onClick="enviarForm('form_acta', 'salir')" /> 

                <div style="display:none;">	
                    <font color=red size=1> ************* DEBUG *************<br>
                        ACCION:<input type="text" name="accion" id="accion"><BR>
                        SECCION:<input type="text" name="seccion" id="seccion" value="${acta.seccion}"><BR>
                        USUARIO:<input type="text" name="username" id="username" value="${acta.username}"><BR>
                        CONFIGURACION:<input type="text" name="config" id="config" value="${acta.config}"><BR>
                        ESTADO:<input type="text" name="estado" id="estado" value="${acta.estado}"><BR>
                        DELETE EVENTO:<input type="text" name="delevento" id="delevento" ><BR>
                        CODTEMP:<input type="text" name="codtemp" id="codtemp" value="${acta.codtemp}"><BR>
                        COMPETICIO: <input type="text" name="competicio" id="competicio" value="${acta.competicio}"><BR>
                        MAILEQUIPLOCAL: <input type="text" name="mailequiplocal" id="mailequiplocal" value="${acta.mailequiplocal}"><BR>
                        MAILEQUIPVISIT: <input type="text" name="mailequipvisit" id="mailequipvisit" value="${acta.mailequipvisit}"><BR>
                        CODEENTITATLOCAL: <input type="text" name="codentitatlocal" id="codentitatlocal" value="${acta.codentitatlocal}"><BR>
                        CODEENTITATVISIT: <input type="text" name="codentitatvisit" id="codentitatvisit" value="${acta.codentitatvisit}"><BR>
                        PINEQUIPLOCAL: <input type="text" name="pin_clubl" id="pin_clubl" value="${acta.pin_clubl}"><BR>
                        PINEQUIPVISITANT: <input type="text" name="pin_clubv" id="pin_clubv" value="${acta.pin_clubv}"><BR>
                        CODCATEGORIALOCAL:<input type="text" name="codcategorialocal" id="codcategorialocal" value="${acta.codcategorialocal}"><BR>
                        CODCATEGORIAVISIT:<input type="text" name="codcategoriavisit" id="codcategoriavisit" value="${acta.codcategoriavisit}"><BR>
                        <br> 
                        <table>
                            <tr>
                                <td colspan=5>Jugadores locales para seleccionar </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Categoria</td>
                                <td>Nom i Cognom</td>
                                <td>Sancionado</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="jugadoreslocal_codlic" id="jugadoreslocal_codlic" value="
                                           <c:forEach items="${acta.datosactalocal}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoreslocal_nif" id="jugadoreslocal_nif" value="
                                           <c:forEach items="${acta.datosactalocal}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoreslocal_cat" id="jugadoreslocal_cat" value="
                                           <c:forEach items="${acta.datosactalocal}" var="jugador">
                                               ${jugador.categoria},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoreslocal_nom_cognoms" id="jugadoreslocal_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactalocal}" var="jugador">
                                               ${jugador.nom}, 
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoreslocal_sancion" id="jugadoreslocal_sancion" value="
                                           <c:forEach items="${acta.datosactalocal}" var="jugador">
                                               ${jugador.sancionat}, 
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <br>
                        <table>
                            <tr>
                                <td colspan=5>Jugadores visitantes para seleccionar </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Categoria</td>
                                <td>Nom i Cognom</td>
                                <td>Sancionado</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="jugadoresvisit_codlic" id="jugadoresvisit_codlic" value="
                                           <c:forEach items="${acta.datosactavisit}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoresvisit_nif" id="jugadoresvisit_nif" value="
                                           <c:forEach items="${acta.datosactavisit}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoresvisit_cat" id="jugadoresvisit_cat" value="
                                           <c:forEach items="${acta.datosactavisit}" var="jugador">
                                               ${jugador.categoria},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoresvisit_nom_cognoms" id="jugadoresvisit_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactavisit}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="jugadoresvisit_sancion" id="jugadoresvisit_sancion" value="
                                           <c:forEach items="${acta.datosactavisit}" var="jugador">
                                               ${jugador.sancionat}, 
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <br>
                        <table>
                            <tr>
                                <td colspan=4>Tecnicos locales para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Categoria</td>
                                <td>Nom i Cognom</td>
                                <td>Pin</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="tecnicoslocal_codlic" id="tecnicoslocal_codlic" value="
                                           <c:forEach items="${acta.datosactaTlocal}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicoslocal_nif" id="tecnicoslocal_nif" value="
                                           <c:forEach items="${acta.datosactaTlocal}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicoslocal_cat" id="tecnicoslocal_cat" value="
                                           <c:forEach items="${acta.datosactaTlocal}" var="jugador">
                                               ${jugador.categoria},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicoslocal_nom_cognoms" id="tecnicoslocal_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaTlocal}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicoslocal_pin" id="tecnicoslocal_pin" value="
                                           <c:forEach items="${acta.datosactaTlocal}" var="jugador">
                                               ${jugador.pin},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <br>
                        <table>
                            <tr>
                                <td colspan=4>Tecnicos visitantes para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Categoria</td>
                                <td>Nom i Cognom</td>
                                <td>Pin</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="tecnicosvisit_codlic" id="tecnicosvisit_codlic" value="
                                           <c:forEach items="${acta.datosactaTvisit}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosvisit_nif" id="tecnicosvisit_nif" value="
                                           <c:forEach items="${acta.datosactaTvisit}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosvisit_cat" id="tecnicosvisit_cat" value="
                                           <c:forEach items="${acta.datosactaTvisit}" var="jugador">
                                               ${jugador.categoria},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosvisit_nom_cognoms" id="tecnicosvisit_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaTvisit}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosvisit_pin" id="tecnicosvisit_pin" value="
                                           <c:forEach items="${acta.datosactaTvisit}" var="jugador">
                                               ${jugador.pin},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Tecnicos federacion para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="tecnicosfede_codlic" id="tecnicosfede_codlic" value="
                                           <c:forEach items="${acta.datosactaTfede}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosfede_nif" id="tecnicosfede_nif" value="
                                           <c:forEach items="${acta.datosactaTfede}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="tecnicosf_nom_cognoms" id="tecnicosf_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaTfede}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Delegados locales para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                                <td>Pin</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="delegadoslocal_codlic" id="delegadoslocal_codlic" value="
                                           <c:forEach items="${acta.datosactaDlocal}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadoslocal_nif" id="delegadoslocal_nif" value="
                                           <c:forEach items="${acta.datosactaDlocal}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadoslocal_nom_cognoms" id="delegadoslocal_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaDlocal}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadoslocal_pin" id="delegadoslocal_pin" value="
                                           <c:forEach items="${acta.datosactaDlocal}" var="jugador">
                                               ${jugador.pin},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Delegados visitantes para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="delegadosvisit_codlic" id="delegadosvisit_codlic" value="
                                           <c:forEach items="${acta.datosactaDvisit}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadosvisit_nif" id="delegadosvisit_nif" value="
                                           <c:forEach items="${acta.datosactaDvisit}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadosvisit_nom_cognoms" id="delegadosvisit_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaDvisit}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Delegados federacion para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                                <td>Pin</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="delegadosfede_codlic" id="delegadosfede_codlic" value="
                                           <c:forEach items="${acta.datosactaDfede}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadosfede_nif" id="delegadosfede_nif" value="
                                           <c:forEach items="${acta.datosactaDfede}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadosfede_nom_cognoms" id="delegadosfede_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaDfede}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="delegadosfede_pin" id="delegadosfede_pin" value="
                                           <c:forEach items="${acta.datosactaDfede}" var="jugador">
                                               ${jugador.pin},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Auxiliares locales para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="auxiliareslocal_codlic" id="auxiliareslocal_codlic" value="
                                           <c:forEach items="${acta.datosactaAlocal}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="auxiliareslocal_nif" id="auxiliareslocal_nif" value="
                                           <c:forEach items="${acta.datosactaAlocal}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="auxiliareslocal_nom_cognoms" id="auxiliareslocal_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaAlocal}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Auxiliares visit para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="auxiliaresvisit_codlic" id="auxiliaresvisit_codlic" value="
                                           <c:forEach items="${acta.datosactaAvisit}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="auxiliaresvisit_nif" id="auxiliaresvisit_nif" value="
                                           <c:forEach items="${acta.datosactaAvisit}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="auxiliaresvisit_nom_cognoms" id="auxiliaresvisit_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaAvisit}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td colspan=4>Arbitros para seleccionar  </td>
                            </tr>
                            <tr>
                                <td>Licencia</td>
                                <td>Nif</td>
                                <td>Nom i Cognom</td>
                                <td>Pin</td>
                            </tr>
                            <tr>
                                <td><input type="text" name="arbitros_codlic" id="arbitros_codlic" value="
                                           <c:forEach items="${acta.datosactaArbitro}" var="jugador">
                                               ${jugador.codlic},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="arbitros_nif" id="arbitros_nif" value="
                                           <c:forEach items="${acta.datosactaArbitro}" var="jugador">
                                               ${jugador.nif},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="arbitros_nom_cognoms" id="arbitros_nom_cognoms" value="
                                           <c:forEach items="${acta.datosactaArbitro}" var="jugador">
                                               ${jugador.nom},
                                           </c:forEach>
                                           "></td>
                                <td><input type="text" name="arbitros_pin" id="arbitros_pin" value="
                                           <c:forEach items="${acta.datosactaArbitro}" var="jugador">
                                               ${jugador.pin},
                                           </c:forEach>
                                           "></td>
                            </tr>
                        </table>
                        <br>
                        <table> 
                            <tr>
                                <td colspan=4>Valores jugadores locales</td>
                            </tr>
                            <tr>
                                <td>Nifs</td>
                                <td>cats</td>
                                <td>Sancionado</td>
                                <td>pin</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="nif_l1" id="nif_l1" value="${acta.nif_l1}" READONLY>
                                    <input type="text" name="nif_l2" id="nif_l2" value="${acta.nif_l2}" READONLY>
                                    <input type="text" name="nif_l3" id="nif_l3" value="${acta.nif_l3}" READONLY>
                                    <input type="text" name="nif_l4" id="nif_l4" value="${acta.nif_l4}" READONLY>
                                    <input type="text" name="nif_l5" id="nif_l5" value="${acta.nif_l5}" READONLY>
                                    <input type="text" name="nif_l6" id="nif_l6" value="${acta.nif_l6}" READONLY>
                                    <input type="text" name="nif_l7" id="nif_l7" value="${acta.nif_l7}" READONLY>
                                    <input type="text" name="nif_l8" id="nif_l8" value="${acta.nif_l8}" READONLY>
                                    <input type="text" name="nif_l9" id="nif_l9" value="${acta.nif_l9}" READONLY>
                                    <input type="text" name="nif_l10" id="nif_l10" value="${acta.nif_l10}" READONLY>
                                    <input type="text" name="nif_l11" id="nif_l11" value="${acta.nif_l11}" READONLY>
                                    <input type="text" name="nif_l12" id="nif_l12" value="${acta.nif_l12}" READONLY>
                                    <input type="text" name="nif_l13" id="nif_l13" value="${acta.nif_l13}" READONLY>
                                    <input type="text" name="nif_l14" id="nif_l14" value="${acta.nif_l14}" READONLY>
                                    <input type="text" name="nif_l15" id="nif_l15" value="${acta.nif_l15}" READONLY>
                                    <input type="text" name="nif_l16" id="nif_l16" value="${acta.nif_l16}" READONLY>
                                    <input type="text" name="nif_l17" id="nif_l17" value="${acta.nif_l17}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="cat_l11" id="cat_l11" value="${acta.cat_l11}" READONLY>
                                    <input type="text" name="cat_l12" id="cat_l12" value="${acta.cat_l12}" READONLY>
                                    <input type="text" name="cat_l13" id="cat_l13" value="${acta.cat_l13}" READONLY>
                                    <input type="text" name="cat_l14" id="cat_l14" value="${acta.cat_l14}" READONLY>
                                    <input type="text" name="cat_l15" id="cat_l15" value="${acta.cat_l15}" READONLY>
                                    <input type="text" name="cat_l16" id="cat_l16" value="${acta.cat_l16}" READONLY>
                                    <input type="text" name="cat_l17" id="cat_l17" value="${acta.cat_l17}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="sancion_l1" id="sancion_l1" value="${acta.sancion_l1}" READONLY>
                                    <input type="text" name="sancion_l2" id="sancion_l2" value="${acta.sancion_l2}" READONLY>
                                    <input type="text" name="sancion_l3" id="sancion_l3" value="${acta.sancion_l3}" READONLY>
                                    <input type="text" name="sancion_l4" id="sancion_l4" value="${acta.sancion_l4}" READONLY>
                                    <input type="text" name="sancion_l5" id="sancion_l5" value="${acta.sancion_l5}" READONLY>
                                    <input type="text" name="sancion_l6" id="sancion_l6" value="${acta.sancion_l6}" READONLY>
                                    <input type="text" name="sancion_l7" id="sancion_l7" value="${acta.sancion_l7}" READONLY>
                                    <input type="text" name="sancion_l8" id="sancion_l8" value="${acta.sancion_l8}" READONLY>
                                    <input type="text" name="sancion_l9" id="sancion_l9" value="${acta.sancion_l9}" READONLY>
                                    <input type="text" name="sancion_l10" id="sancion_l10" value="${acta.sancion_l10}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="pin_l1" id="pin_l1" value="${acta.pin_l1}" READONLY>
                                    <input type="text" name="pin_l2" id="pin_l2" value="${acta.pin_l2}" READONLY>
                                    <input type="text" name="pin_l3" id="pin_l3" value="${acta.pin_l3}" READONLY>
                                    <input type="text" name="pin_l4" id="pin_l4" value="${acta.pin_l4}" READONLY>
                                    <input type="text" name="pin_l5" id="pin_l5" value="${acta.pin_l5}" READONLY>
                                    <input type="text" name="pin_l6" id="pin_l6" value="${acta.pin_l6}" READONLY>
                                    <input type="text" name="pin_l7" id="pin_l7" value="${acta.pin_l7}" READONLY>
                                    <input type="text" name="pin_l8" id="pin_l8" value="${acta.pin_l8}" READONLY>
                                    <input type="text" name="pin_l9" id="pin_l9" value="${acta.pin_l9}" READONLY>
                                    <input type="text" name="pin_l10" id="pin_l10" value="${acta.pin_l10}" READONLY>
                                    <input type="text" name="pin_l11" id="pin_l11" value="${acta.pin_l11}" READONLY>
                                </td>
                            </tr>
                        </table>
                        <table> 
                            <tr>
                                <td colspan=4>Valores jugadores visitantes</td>
                            </tr>
                            <tr>
                                <td>Nifs</td>
                                <td>cats</td>
                                <td>Sancionado</td>
                                <td>pin</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="nif_v1" id="nif_v1" value="${acta.nif_v1}" READONLY>
                                    <input type="text" name="nif_v2" id="nif_v2" value="${acta.nif_v2}" READONLY>
                                    <input type="text" name="nif_v3" id="nif_v3" value="${acta.nif_v3}" READONLY>
                                    <input type="text" name="nif_v4" id="nif_v4" value="${acta.nif_v4}" READONLY>
                                    <input type="text" name="nif_v5" id="nif_v5" value="${acta.nif_v5}" READONLY>
                                    <input type="text" name="nif_v6" id="nif_v6" value="${acta.nif_v6}" READONLY>
                                    <input type="text" name="nif_v7" id="nif_v7" value="${acta.nif_v7}" READONLY>
                                    <input type="text" name="nif_v8" id="nif_v8" value="${acta.nif_v8}" READONLY>
                                    <input type="text" name="nif_v9" id="nif_v9" value="${acta.nif_v9}" READONLY>
                                    <input type="text" name="nif_v10" id="nif_v10" value="${acta.nif_v10}" READONLY>
                                    <input type="text" name="nif_v11" id="nif_v11" value="${acta.nif_v11}" READONLY>
                                    <input type="text" name="nif_v12" id="nif_v12" value="${acta.nif_v12}" READONLY>
                                    <input type="text" name="nif_v13" id="nif_v13" value="${acta.nif_v13}" READONLY>
                                    <input type="text" name="nif_v14" id="nif_v14" value="${acta.nif_v14}" READONLY>
                                    <input type="text" name="nif_v15" id="nif_v15" value="${acta.nif_v15}" READONLY>
                                    <input type="text" name="nif_v16" id="nif_v16" value="${acta.nif_v16}" READONLY>
                                    <input type="text" name="nif_v17" id="nif_v17" value="${acta.nif_v17}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="cat_v11" id="cat_v11" value="${acta.cat_v11}" READONLY>
                                    <input type="text" name="cat_v12" id="cat_v12" value="${acta.cat_v12}" READONLY>
                                    <input type="text" name="cat_v13" id="cat_v13" value="${acta.cat_v13}" READONLY>
                                    <input type="text" name="cat_v14" id="cat_v14" value="${acta.cat_v14}" READONLY>
                                    <input type="text" name="cat_v15" id="cat_v15" value="${acta.cat_v15}" READONLY>
                                    <input type="text" name="cat_v16" id="cat_v16" value="${acta.cat_v16}" READONLY>
                                    <input type="text" name="cat_v17" id="cat_v17" value="${acta.cat_v17}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="sancion_v1" id="sancion_v1" value="${acta.sancion_v1}" READONLY>
                                    <input type="text" name="sancion_v2" id="sancion_v2" value="${acta.sancion_v2}" READONLY>
                                    <input type="text" name="sancion_v3" id="sancion_v3" value="${acta.sancion_v3}" READONLY>
                                    <input type="text" name="sancion_v4" id="sancion_v4" value="${acta.sancion_v4}" READONLY>
                                    <input type="text" name="sancion_v5" id="sancion_v5" value="${acta.sancion_v5}" READONLY>
                                    <input type="text" name="sancion_v6" id="sancion_v6" value="${acta.sancion_v6}" READONLY>
                                    <input type="text" name="sancion_v7" id="sancion_v7" value="${acta.sancion_v7}" READONLY>
                                    <input type="text" name="sancion_v8" id="sancion_v8" value="${acta.sancion_v8}" READONLY>
                                    <input type="text" name="sancion_v9" id="sancion_v9" value="${acta.sancion_v9}" READONLY>
                                    <input type="text" name="sancion_v10" id="sancion_v10" value="${acta.sancion_v10}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="pin_v1" id="pin_v1" value="${acta.pin_v1}" READONLY>
                                    <input type="text" name="pin_v2" id="pin_v2" value="${acta.pin_v2}" READONLY>
                                    <input type="text" name="pin_v3" id="pin_v3" value="${acta.pin_v3}" READONLY>
                                    <input type="text" name="pin_v4" id="pin_v4" value="${acta.pin_v4}" READONLY>
                                    <input type="text" name="pin_v5" id="pin_v5" value="${acta.pin_v5}" READONLY>
                                    <input type="text" name="pin_v6" id="pin_v6" value="${acta.pin_v6}" READONLY>
                                    <input type="text" name="pin_v7" id="pin_v7" value="${acta.pin_v7}" READONLY>
                                    <input type="text" name="pin_v8" id="pin_v8" value="${acta.pin_v8}" READONLY>
                                    <input type="text" name="pin_v9" id="pin_v9" value="${acta.pin_v9}" READONLY>
                                    <input type="text" name="pin_v10" id="pin_v10" value="${acta.pin_v10}" READONLY>
                                    <input type="text" name="pin_v11" id="pin_v11" value="${acta.pin_v11}" READONLY>
                                </td>
                            </tr>
                        </table>
                        <table> 
                            <tr>
                                <td colspan=4>Valores Firmas</td>
                            </tr>
                            <tr>
                                <td>Delegado</td>
                                <td>Cronometrador</td>
                                <td>arbitro 1</td>
                                <td>arbitro 2</td>
                                <td>firmas</td>
                                <td>pines</td>
                                <td>actualizar firma?</td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="text" name="nif_dp" id="nif_dp" value="${acta.nif_dp}" READONLY>
                                </td>
                                <td> 
                                    <input type="text" name="nif_cr" id="nif_cr" value="${acta.nif_cr}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="nif_ar1" id="nif_ar1" value="${acta.nif_ar1}" READONLY>
                                    <input type="text" name="pin_ar1" id="pin_ar1" value="${acta.pin_ar1}" READONLY>
                                    <input type="text" name="email_ar1" id="email_ar1" value="${acta.email_ar1}" READONLY>
                                    <input type="text" name="cat_ar1" id="cat_ar1" value="${acta.cat_ar1}" READONLY>
                                    <input type="text" name="territorial_ar1" id="territorial_ar1" value="${acta.territorial_ar1}" READONLY>
                                    <input type="text" name="telefono_ar1" id="telefono_ar1" value="${acta.telefono_ar1}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="nif_ar2" id="nif_ar2" value="${acta.nif_ar2}" READONLY>
                                    <input type="text" name="pin_ar2" id="pin_ar2" value="${acta.pin_ar2}" READONLY>
                                    <input type="text" name="territorial_ar2" id="territorial_ar2" value="${acta.territorial_ar2}" READONLY>
                                </td>
                                <td>
                                    <input type="text" name="firma1_dp" id="firma1_dp" value="${acta.firma1_dp}">
                                    <input type="text" name="firma1_cl" id="firma1_cl" value="${acta.firma1_cl}">
                                    <input type="text" name="firma1_el" id="firma1_el" value="${acta.firma1_el}">
                                    <input type="text" name="firma1_cv" id="firma1_cv" value="${acta.firma1_cv}">
                                    <input type="text" name="firma1_ev" id="firma1_ev" value="${acta.firma1_ev}">
                                    <input type="text" name="firma2_cl" id="firma2_cl" value="${acta.firma2_cl}">
                                    <input type="text" name="firma2_ar" id="firma2_ar" value="${acta.firma2_ar}">
                                    <input type="text" name="firma2_ar2" id="firma2_ar2" value="${acta.firma2_ar2}">
                                    <input type="text" name="firma2_cv" id="firma2_cv" value="${acta.firma2_cv}">
                                </td>
                                <td>
                                    dp:<input type="text" name="pin_dp" id="pin_dp" value="${acta.pin_dp}" >
                                    el:<input type="text" name="pin_el" id="pin_el" value="${acta.pin_el}" >
                                    ev:<input type="text" name="pin_ev" id="pin_ev" value="${acta.pin_ev}" >
                                </td>
                                <td>
                                    <input type="text" name="firma1_dp_actualizar" id="firma1_dp_actualizar" value="${acta.firma1_dp_actualizar}">
                                    <input type="text" name="firma1_cl_actualizar" id="firma1_cl_actualizar" value="${acta.firma1_cl_actualizar}">
                                    <input type="text" name="firma1_el_actualizar" id="firma1_el_actualizar" value="${acta.firma1_el_actualizar}">
                                    <input type="text" name="firma1_cv_actualizar" id="firma1_cv_actualizar" value="${acta.firma1_cv_actualizar}">
                                    <input type="text" name="firma1_ev_actualizar" id="firma1_ev_actualizar" value="${acta.firma1_ev_actualizar}">
                                    <input type="text" name="firma2_cl_actualizar" id="firma2_cl_actualizar" value="${acta.firma2_cl_actualizar}">
                                    <input type="text" name="firma2_ar_actualizar" id="firma2_ar_actualizar" value="${acta.firma2_ar_actualizar}">
                                    <input type="text" name="firma2_ar2_actualizar" id="firma2_ar2_actualizar" value="${acta.firma2_ar2_actualizar}">
                                    <input type="text" name="firma2_cv_actualizar" id="firma2_cv_actualizar" value="${acta.firma2_cv_actualizar}">
                                </td>
                            </tr>
                        </table>
                    </font>
                </div>
            </form>

            <h3><font color=gray>${message}</font></h3> 
            <font style="font-family: verdana;font-size: 10pt;font-style: italic;color: gray;">Versió 20161026</font>
            <!--	<font style="font-family: verdana;font-size: 10pt;font-style: italic;color: gray;">Versió 20160930</font>-->
        </center> 

        <c:if test="${!empty destino}">
            <c:if test="${destino == 'ERROR'}">
                <script>
                    alert("No se ha podido realizar la exportacion.");
                </script>
            </c:if>
            <c:if test="${destino != 'ERROR'}">
                <iframe id="iframeIS" name="iframeIS" src="" style="display:none;" ></iframe>
                <form id="DescargaForm" name="DescargaForm" METHOD="POST">
                    <input type="hidden" name="destino" id="destino" value="${destino}">
                </form>
                <script>
                    window.document.getElementById('DescargaForm').action = "ObtenerFichero";
                    window.document.getElementById('DescargaForm').target = 'iframeIS';
                    window.document.getElementById('DescargaForm').submit();
                </script>
            </c:if>	
        </c:if>
                  <div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
   <span class="close" id="sp" onclick="tanca();">×</span>
      <h2>Opcions tancament</h2>
    </div>
    <div class="modal-body">
        <p>Enviar a comité de disciplina<input type="checkbox" id="comite"  /></p>         
<!--      <p>Guardar dorsals locals<input type="checkbox"  id="dorsalsLocals"/></p>
        <p>Guardar dorsals visitants<input type="checkbox" id="dorsalsVisitants"/></p>-->
         <p>Partit no finalitzat<input type="checkbox" id="finalitzat"/></p>
<!--          <input type="button" value="Acceptar" id="modalAcceptar" onclick="modalAcceptar();" /><input id="modalCancelar" onclick="modalCancelar();" type="button" value="Cancelar" style="margin-left:50px"/>-->
    </div>
    <div class="modal-footer" align="center">
       <input type="button" value="Acceptar" onclick="modalAcceptar();" style="margin-top:20px;margin-bottom:20px" />
       <input type="button" value="Cancelar" onclick="modalCancelar();" style="margin-left:50px"/>
<!--      <h3>eACTA</h3>-->
    </div>
  </div>
</div>

<script>
// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
//var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
//btn.onclick = function() {
//    modal.style.display = "block";
//}

//$('#provaModal').on('click', function () 
//{
//    modal.style.display = "none";
//});


// When the user clicks on <span> (x), close the modal
//sp.onclick = function() {
//    modal.style.display = "none";
//}

// When the user clicks anywhere outside of the modal, close it
//window.onclick = function(event) {
//    if (event.target == modal) {
//        modal.style.display = "none";
//    }
//}



</script>
                
                

    </body>
</html>