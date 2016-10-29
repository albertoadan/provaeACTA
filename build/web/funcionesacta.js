//CONTROLAS LAS ACCIONES CON FLUJO HACIA EL CONTROLER EActa_controler.java

var modificat = false;
var protest = false;
var preProtest = false;
var vermella = false;

var firma1Delegat = false;
var firma1CapitaLocal = false;
var firma1CapitaVisitant = false;
var firma1TecnicLocal = false;
var firma1TecnicVisitant = false;

var firma2CapitaLocal = false;
var firma2Arbitre = false;
var firma2ArbitreAuxiliar = false;
var firma2CapitaVisitant = false;



function descarregaActa(text) {
    var link = "../pdf/ACTA_" + text + ".pdf";
    location.href = link;
}

//function guardaEventsLocal(form) {
//    if (modificat == true) {
//        enviarForm(form, "tornarLocal");
//        mostrarBotones();
//        cargaInicial();
//        modificat = false;
//    } else {
//        mostrarBotones();
//    }
//}
//
//function guardaEventsVisitant(form) {
//    if (modificat == true) {
//        enviarForm(form, "tornarVisitant");
//        mostrarBotones();
//        cargaInicial();
//        modificat = false;
//    } else {
//        mostrarBotones();
//    }
//}
//
//function guardaObservacions(form) {
//enviarForm(form, "tornarObservacions");
//        mostrarBotones();
//        cargaInicial();
//}
//
///*
// * funcio per guardar les dades a la base de dades cada cop que se li dona al boto de tornar si s´ha modificat alguna cosa.
// */
//function guardaTornar(form, action) {
//    if (modificat == true) {
//        enviarForm(form, action);
//        mostrarBotones();
//        cargaInicial();
//        modificat = false;
//    } else {
//        mostrarBotones();
//    }
//}

function afegirAlerta(texte) {
    var newParagraph = document.createElement('p');
    newParagraph.textContent = texte;
    document.getElementById("alertes").appendChild(newParagraph);
}

function firmas1(form) {
    firmarDelegadospista(form);
    firmar1Capitanlocal(form);
    firmar1Capitanvisit(form);
    firmar1Entrenadorlocal(form);
    firmar1Entrenadorvisit(form);


    if (firma1CapitaLocal == true || firma1CapitaVisitant == true || firma1TecnicLocal == true || firma1Delegat == true || firma1TecnicVisitant == true) {
        enviarForm(form, "firmas1");
    } else {
        if (!validar(form, "firma")) {
            return false;
        } else {
            if ((document.getElementById("firma1pin_dp").value == "") && (document.getElementById("firma1pin_cl").value == "") &&
                    (document.getElementById("firma1pin_cv").value == "") && (document.getElementById("firma1pin_el").value == "") && (document.getElementById("firma1pin_ev").value == "")) {
                alert("Ha d\'indicar el pin.");
            }
        }
    }
    firma1Delegat = false;
    firma1CapitaLocal = false;
    firma1CapitaVisitant = false;
    firma1TecnicLocal = false;
    firma1TecnicVisitant = false;
}


function firmas2(form) {
    firmar2Arbitro(form);
    firmar2Arbitro2(form);
    firmar2Capitanlocal(form);
    firmar2Capitanvisit(form);

    if (firma2CapitaLocal == true || firma2Arbitre == true || firma2ArbitreAuxiliar == true || firma2CapitaVisitant == true) {
        enviarForm(form, "firmas2");
    } else {
        if (!validar(form, "firma")) {
            return false;
        } else {
            if ((document.getElementById("firma2pin_ar").value == "") && (document.getElementById("firma2pin_ar2").value == "") &&
                    (document.getElementById("firma2pin_cv").value == "") && (document.getElementById("firma2pin_cl").value == "")) {
                alert("Ha d\'indicar el pin.");
            }
        }
    }
    firma2CapitaLocal = false;
    firma2Arbitre = false;
    firma2ArbitreAuxiliar = false;
    firma2CapitaVisitant = false;

}

function tanca() {
    modal.style.display = "none";
    return;
}

function modalAcceptar(event) {
    var p = "cerrar_";

    if (document.getElementById('comite').checked) {
        p = p + "comite_";
    }
    if (document.getElementById('finalitzat').checked) {
        p = p + "finalitzat_";
    }
//    if (document.getElementById('dorsalsLocals').checked) {
//        p = p + "locals_";
//    }
//    if (document.getElementById('dorsalsVisitans').checked) {
//       p = p +  "visitants_";
//    }

    modal.style.display = "none";
    document.getElementById("accion").value = p;
    document.getElementById('form_acta').submit();
}

function modalCancelar(event) {
    modal.style.display = "none";
    return;
}

function events(form, action) {    
//    if (hayAlgunaFirma()) {
//        return false
//    }
   borrarFirma();
   document.getElementById("accion").value = 'guardar';
   document.getElementById('form_acta').submit();
  // enviarForm(form, action);
 
}

function eventsPartit () {   
        if (hayAlgunaFirma()) {
        return false
    }
    borrarFirma();   
    enviarForm('form_acta', 'guardar');
    
}

function enviarForm(form, action) {
    document.getElementById("accion").value = action;

    if (action == "guardar") {
        //Local
        array = document.getElementById("cgolespl").value.split(",");
        if (document.getElementById("cgolespl_1") != null) {
            document.getElementById("cgolespl").value = document.getElementById("cgolespl_1").value;
            for (var int = 1; int < array.length; int++) {
                document.getElementById("cgolespl").value = document.getElementById("cgolespl").value + "," + document.getElementById("cgolespl_" + (int + 1)).value;
            }
        }
        array = document.getElementById("cgolesml").value.split(",");
        if (document.getElementById("cgolesml_1") != null) {
            if (document.getElementById("cgolesml_1").value.indexOf(":") == -1) {
                document.getElementById("cgolesml_1").value = document.getElementById("cgolesml_1").value.substring(0, 2) + ":" + document.getElementById("cgolesml_1").value.substring(2, 4);
            }
            document.getElementById("cgolesml").value = document.getElementById("cgolesml_1").value;
            for (var int = 1; int < array.length; int++) {
                if (document.getElementById("cgolesml_" + (int + 1)).value.indexOf(":") == -1) {
                    document.getElementById("cgolesml_" + (int + 1)).value = document.getElementById("cgolesml_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesml_" + (int + 1)).value.substring(2, 4);
                }
                document.getElementById("cgolesml").value = document.getElementById("cgolesml").value + "," + document.getElementById("cgolesml_" + (int + 1)).value;
            }
        }
        array = document.getElementById("cgolesdl").value.split(",");
        if (document.getElementById("cgolesdl_1") != null) {
            document.getElementById("cgolesdl").value = document.getElementById("cgolesdl_1").value;
            for (var int = 1; int < array.length; int++) {
                document.getElementById("cgolesdl").value = document.getElementById("cgolesdl").value + "," + document.getElementById("cgolesdl_" + (int + 1)).value;
            }
        }
        //Visitante
        array = document.getElementById("cgolespv").value.split(",");
        if (document.getElementById("cgolespv_1") != null) {
            document.getElementById("cgolespv").value = document.getElementById("cgolespv_1").value;
            for (var int = 1; int < array.length; int++) {
                document.getElementById("cgolespv").value = document.getElementById("cgolespv").value + "," + document.getElementById("cgolespv_" + (int + 1)).value;
            }
        }
        array = document.getElementById("cgolesmv").value.split(",");
        if (document.getElementById("cgolesmv_1") != null) {
            if (document.getElementById("cgolesmv_1").value.indexOf(":") == -1) {
                document.getElementById("cgolesmv_1").value = document.getElementById("cgolesmv_1").value.substring(0, 2) + ":" + document.getElementById("cgolesmv_1").value.substring(2, 4);
            }
            document.getElementById("cgolesmv").value = document.getElementById("cgolesmv_1").value;
            for (var int = 1; int < array.length; int++) {
                if (document.getElementById("cgolesmv_" + (int + 1)).value.indexOf(":") == -1) {
                    document.getElementById("cgolesmv_" + (int + 1)).value = document.getElementById("cgolesmv_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesmv_" + (int + 1)).value.substring(2, 4);
                }
                document.getElementById("cgolesmv").value = document.getElementById("cgolesmv").value + "," + document.getElementById("cgolesmv_" + (int + 1)).value;
            }
        }
        array = document.getElementById("cgolesdv").value.split(",");
        if (document.getElementById("cgolesdv_1") != null) {
            document.getElementById("cgolesdv").value = document.getElementById("cgolesdv_1").value;
            for (var int = 1; int < array.length; int++) {
                document.getElementById("cgolesdv").value = document.getElementById("cgolesdv").value + "," + document.getElementById("cgolesdv_" + (int + 1)).value;
            }
        }
    }

    if (action === "firmas1") {
        document.getElementById(form).submit();
    }
    if (action === "firmas2") {
        document.getElementById(form).submit();
    }

//    
//   if (action === "firma1_delegadopista") {        
//        document.getElementById(form).submit();
//    }


//    // Tros de codi per provar si en comptes de guardar tota l´acta d´un cop ho podem fer cada cop que es clicka el boto de tornar.
//    if (action == "tornarLocal") {
//        array = document.getElementById("cgolespl").value.split(",");
//        if (document.getElementById("cgolespl_1") != null) {
//            document.getElementById("cgolespl").value = document.getElementById("cgolespl_1").value;
//            for (var int = 1; int < array.length; int++) {
//                document.getElementById("cgolespl").value = document.getElementById("cgolespl").value + "," + document.getElementById("cgolespl_" + (int + 1)).value;
//            }
//        }
//        array = document.getElementById("cgolesml").value.split(",");
//        if (document.getElementById("cgolesml_1") != null) {
//            if (document.getElementById("cgolesml_1").value.indexOf(":") == -1) {
//                document.getElementById("cgolesml_1").value = document.getElementById("cgolesml_1").value.substring(0, 2) + ":" + document.getElementById("cgolesml_1").value.substring(2, 4);
//            }
//            document.getElementById("cgolesml").value = document.getElementById("cgolesml_1").value;
//            for (var int = 1; int < array.length; int++) {
//                if (document.getElementById("cgolesml_" + (int + 1)).value.indexOf(":") == -1) {
//                    document.getElementById("cgolesml_" + (int + 1)).value = document.getElementById("cgolesml_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesml_" + (int + 1)).value.substring(2, 4);
//                }
//                document.getElementById("cgolesml").value = document.getElementById("cgolesml").value + "," + document.getElementById("cgolesml_" + (int + 1)).value;
//            }
//        }
//        array = document.getElementById("cgolesdl").value.split(",");
//        if (document.getElementById("cgolesdl_1") != null) {
//            document.getElementById("cgolesdl").value = document.getElementById("cgolesdl_1").value;
//            for (var int = 1; int < array.length; int++) {
//                document.getElementById("cgolesdl").value = document.getElementById("cgolesdl").value + "," + document.getElementById("cgolesdl_" + (int + 1)).value;
//            }
//        }
//        document.getElementById(form).submit();
//    }
//    if (action == "tornarVisitant") {
//        //Visitante
//        array = document.getElementById("cgolespv").value.split(",");
//        if (document.getElementById("cgolespv_1") != null) {
//            document.getElementById("cgolespv").value = document.getElementById("cgolespv_1").value;
//            for (var int = 1; int < array.length; int++) {
//                document.getElementById("cgolespv").value = document.getElementById("cgolespv").value + "," + document.getElementById("cgolespv_" + (int + 1)).value;
//            }
//        }
//        array = document.getElementById("cgolesmv").value.split(",");
//        if (document.getElementById("cgolesmv_1") != null) {
//            if (document.getElementById("cgolesmv_1").value.indexOf(":") == -1) {
//                document.getElementById("cgolesmv_1").value = document.getElementById("cgolesmv_1").value.substring(0, 2) + ":" + document.getElementById("cgolesmv_1").value.substring(2, 4);
//            }
//            document.getElementById("cgolesmv").value = document.getElementById("cgolesmv_1").value;
//            for (var int = 1; int < array.length; int++) {
//                if (document.getElementById("cgolesmv_" + (int + 1)).value.indexOf(":") == -1) {
//                    document.getElementById("cgolesmv_" + (int + 1)).value = document.getElementById("cgolesmv_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesmv_" + (int + 1)).value.substring(2, 4);
//                }
//                document.getElementById("cgolesmv").value = document.getElementById("cgolesmv").value + "," + document.getElementById("cgolesmv_" + (int + 1)).value;
//            }
//        }
//        array = document.getElementById("cgolesdv").value.split(",");
//        if (document.getElementById("cgolesdv_1") != null) {
//            document.getElementById("cgolesdv").value = document.getElementById("cgolesdv_1").value;
//            for (var int = 1; int < array.length; int++) {
//                document.getElementById("cgolesdv").value = document.getElementById("cgolesdv").value + "," + document.getElementById("cgolesdv_" + (int + 1)).value;
//            }
//        }
//        document.getElementById(form).submit();
//    }
//    if (action == "tornarObservacions") {
//        document.getElementById(form).submit();
//    }
////    if (action == "tornarLlistaEvents") {
////        
////    }
//    if (action =="mostrarEvents") {
//       document.getElementById(form).submit();
//    }
//    
//    if (action == "tornarCapcalera") {
//        document.getElementById(form).submit();
//    }
//    if (action == "tornarLlistaEvents") {
//         document.getElementById(form).submit();
//    }





    if (validar(form, action)) {
        if (action == "cerrar") {
            //   if (confirm("Un cop tancada, l\'acta no podr\xE0 tornar a modificar-se i s\'enviar\xE0 per correu electr\xF2nic a l\'\xE0rbitre i als clubs . Voleu continuar?")) {
            if (validar(form)) {
                modal.style.display = "block";
                document.getElementById("comite").checked = false;
//                if ((document.getElementById("txtfirma1pin_cl").innerHTML.indexOf("PROTESTO") != -1) || (document.getElementById("txtfirma1pin_cv").innerHTML.indexOf("PROTESTO") != -1) ||
//                        (document.getElementById("txtfirma2pin_cv").innerHTML.indexOf("PROTESTO") != -1) || (protest == true) ||
//                        (document.getElementById("txtfirma2pin_cl").innerHTML.indexOf("PROTESTO") != -1)) {
//                    document.getElementById("comite").checked = true;
//                } else {
//                    document.getElementById("comite").checked = false;
//                }
//                $('#taulaEvents').each(function (i, row) {
//                    var $row = $(row), $f = $row.find('tr');
//                    $row.each(function (i, tr) {
//                        var $tr = $(tr), $g = $tr.find('td');
//                        $tr.each(function (i, c) {
//                            var x = $(c).text();
//                            if (x.includes("Tarja Vermella")) {
//                                document.getElementById("comite").checked = true;
//                            } else {
//                                document.getElementById("comite").checked = false;
//                            }
//                        });
//                    });
//                });


                //  document.getElementById(form).submit();
            }
            //   }
        } else {
            if (validar(form)) {
                document.getElementById(form).submit();
            }
        }
    }
}



function tieneformatoCrono(crono) {
    //Maximo 25:00	
    var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(crono);
    var isValid2 = /25:00?$/;
    if (!isValid)
        isValid = isValid2.test(crono);
    var isValid2 = /^([0-1]?[0-9]|2[0-4])([0-5][0-9])([0-5][0-9])?$/;
    if (!isValid)
        isValid = isValid2.test(crono);
    var isValid2 = /2500?$/;
    if (!isValid)
        isValid = isValid2.test(crono);
    return isValid;
}

function tieneformatoHora(crono) {
    //Maximo 23:59	
    var isValid = /^([0-1]?[0-9]|2[0-3]):([0-5][0-9])(:[0-5][0-9])?$/.test(crono);
    var isValid2 = /^([0-1]?[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])?$/;
    if (!isValid)
        isValid = isValid2.test(crono);
    return isValid;
}

// VALIDACIONES DEL FORMULARIO ACTA
function validar(form, action) {
    var resultado = true;
    if (action == "guardar") {  //Solo validar eventos del partido
        if (!tieneformatoHora(document.getElementById("horainicio").value)) {
            alert("El format de l'hora d'inici real del partit (" + document.getElementById("horainicio").value + ") no es correcte (hh:mm com a m\xE0xim 23:59).");
            resultado = false;
        } else {
            if (document.getElementById("horainicio").value.indexOf(":") == -1) {
                document.getElementById("horainicio").value = document.getElementById("horainicio").value.substring(0, 2) + ":" + document.getElementById("horainicio").value.substring(2, 4);
            }
        }
        if (!tieneformatoCrono(document.getElementById("tiempomuertol1").value)) {
            alert("El format del primer temps mort local (" + document.getElementById("tiempomuertol1").value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
            resultado = false;
        } else {
            if (document.getElementById("tiempomuertol1").value.indexOf(":") == -1) {
                document.getElementById("tiempomuertol1").value = document.getElementById("tiempomuertol1").value.substring(0, 2) + ":" + document.getElementById("tiempomuertol1").value.substring(2, 4);
            }
        }
        if (!tieneformatoCrono(document.getElementById("tiempomuertol2").value)) {
            alert("El format del segon temps mort local (" + document.getElementById("tiempomuertol2").value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
            resultado = false;
        } else {
            if (document.getElementById("tiempomuertol2").value.indexOf(":") == -1) {
                document.getElementById("tiempomuertol2").value = document.getElementById("tiempomuertol2").value.substring(0, 2) + ":" + document.getElementById("tiempomuertol2").value.substring(2, 4);
            }
        }
        if (!tieneformatoCrono(document.getElementById("tiempomuertov1").value)) {
            alert("El format del primer temps mort visitant (" + document.getElementById("tiempomuertov1").value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
            resultado = false;
        } else {
            if (document.getElementById("tiempomuertov1").value.indexOf(":") == -1) {
                document.getElementById("tiempomuertov1").value = document.getElementById("tiempomuertov1").value.substring(0, 2) + ":" + document.getElementById("tiempomuertov1").value.substring(2, 4);
            }
        }
        if (!tieneformatoCrono(document.getElementById("tiempomuertov2").value)) {
            alert("El format del segon temps mort visitant (" + document.getElementById("tiempomuertov2").value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
            resultado = false;
        } else {
            if (document.getElementById("tiempomuertov2").value.indexOf(":") == -1) {
                document.getElementById("tiempomuertov2").value = document.getElementById("tiempomuertov2").value.substring(0, 2) + ":" + document.getElementById("tiempomuertov2").value.substring(2, 4);
            }
        }
        if (document.getElementById("totalfaltasl").value < 0) {
            alert("El total faltes local no pot se negatiu.");
            resultado = false;
        }
        if (document.getElementById("totalfaltasv").value < 0) {
            alert("El total faltes visitant no pot se negatiu.");
            resultado = false;
        }
        if (document.getElementById("totalgolesl").value < 0) {
            alert("El resultat (gols) local no pot se negatiu.");
            resultado = false;
        }
        if (document.getElementById("totalgolesv").value < 0) {
            alert("El resultat (gols) visitant no pot se negatiu.");
            resultado = false;
        }


        array = document.getElementById("cgolesml").value.split(",");
        for (var int = 0; int < array.length; int++) {
            if (document.getElementById("cgolesml_" + (int + 1)) != null) {
                if (!tieneformatoCrono(document.getElementById("cgolesml_" + (int + 1)).value)) {
                    alert("El format dels cronos de gols locals (" + document.getElementById("cgolesml_" + (int + 1)).value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
                    resultado = false;
                } else {
                    if (document.getElementById("cgolesml_" + (int + 1)).value.indexOf(":") == -1) {
                        document.getElementById("cgolesml_" + (int + 1)).value = document.getElementById("cgolesml_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesml_" + (int + 1)).value.substring(2, 4);
                    }
                }
            }
        }

        array = document.getElementById("cgolesmv").value.split(",");
        for (var int = 0; int < array.length; int++) {
            if (document.getElementById("cgolesmv_" + (int + 1)) != null) {
                if (!tieneformatoCrono(document.getElementById("cgolesmv_" + (int + 1)).value)) {
                    alert("El format dels cronos de gols visitants (" + document.getElementById("cgolesmv_" + (int + 1)).value + ") no es correcte (mm:ss com a m\xE0xim 25:00).");
                    resultado = false;
                } else {
                    if (document.getElementById("cgolesmv_" + (int + 1)).value.indexOf(":") == -1) {
                        document.getElementById("cgolesmv_" + (int + 1)).value = document.getElementById("cgolesmv_" + (int + 1)).value.substring(0, 2) + ":" + document.getElementById("cgolesmv_" + (int + 1)).value.substring(2, 4);
                    }
                }
            }
        }

        return resultado;
    }

    if (action == "guardarsalir" || action == "cerrar" || action == "firma") {
        // Estas validaciones no permiten guardar --> SI LO PERMITEN
        if (estaFlagActivo(document.getElementById("config").value, 4)) { // FLAG 4 Delegado obligatorio
            if (document.getElementById("codlic_dp").value == "") {
                alert("Ha d'indicar el delegat de pista a la cap\xE7alera de l'acta.");
                // resultado = false;
            }
        }
        if (estaFlagActivo(document.getElementById("config").value, 5)) { // FLAG 5 Cronometradro obligatorio
            if (document.getElementById("codlic_cr").value == "") {
                alert("Ha d'indicar el cronometrador a la cap\xE7alera de l'acta.");
                // resultado = false;
            }
        }
        if (estaFlagActivo(document.getElementById("config").value, 1)) { // FLAG 1 Validar que no haya jugadores seleccionados sancionados
            if (document.getElementById("sancion_l1").value == "1"
                    || document.getElementById("sancion_l2").value == "1"
                    || document.getElementById("sancion_l3").value == "1"
                    || document.getElementById("sancion_l4").value == "1"
                    || document.getElementById("sancion_l5").value == "1"
                    || document.getElementById("sancion_l6").value == "1"
                    || document.getElementById("sancion_l7").value == "1"
                    || document.getElementById("sancion_l8").value == "1"
                    || document.getElementById("sancion_l9").value == "1"
                    || document.getElementById("sancion_l10").value == "1") {
                alert("L'equip local t\xE9 jugadors sancionats.");
                // resultado = false;
            }
        }
        if (estaFlagActivo(document.getElementById("config").value, 1)) { // FLAG 1 Validar que no haya jugadores seleccionados sancionados
            if (document.getElementById("sancion_v1").value == "1"
                    || document.getElementById("sancion_v2").value == "1"
                    || document.getElementById("sancion_v3").value == "1"
                    || document.getElementById("sancion_v4").value == "1"
                    || document.getElementById("sancion_v5").value == "1"
                    || document.getElementById("sancion_v6").value == "1"
                    || document.getElementById("sancion_v7").value == "1"
                    || document.getElementById("sancion_v8").value == "1"
                    || document.getElementById("sancion_v9").value == "1"
                    || document.getElementById("sancion_v10").value == "1") {
                alert("L'equip visitant t\xE9 jugadors sancionats.");
                // resultado = false;
            }
        }
        if (estaFlagActivo(document.getElementById("config").value, 3)) { // FLAG 3 Validar que haya 1 portero y 3 jugadores como minimo
            if (numporteros("L") < 1 || numjugadores("L") < 3) {
                alert("L'equip local t\xE9 menys d'un porter i tres jugadors. No pot comen\xE7ar el partit.")
                // resultado = false;
            }
            if (numporteros("V") < 1 || numjugadores("V") < 3) {
                alert("L'equip visitant t\xE9 menys d'un porter i tres jugadors. No pot comen\xE7ar el partit.")
                // resultado = false;
            }
        }
        if (action == "cerrar" || action == "firma") {
            // Estas validaciones no permiten cerrar
            if (estaFlagActivo(document.getElementById("config").value, 8)) { // FLAG 8 Marcar capitan obligatorio y firmas obligatoria
                if (!document.getElementById("capi_l1").checked
                        && !document.getElementById("capi_l2").checked
                        && !document.getElementById("capi_l3").checked
                        && !document.getElementById("capi_l4").checked
                        && !document.getElementById("capi_l5").checked
                        && !document.getElementById("capi_l6").checked
                        && !document.getElementById("capi_l7").checked
                        && !document.getElementById("capi_l8").checked
                        && !document.getElementById("capi_l9").checked
                        && !document.getElementById("capi_l10").checked) {
                    alert("Ha d'indicar el capit\xE0 de l'equip local per tancar l'acta.");
                    resultado = false;
                } else {
                    if (action != "firma") {
                        if (document.getElementById("firma1_cl").value == "" || document.getElementById("firma2_cl").value == "") {
                            alert("Ha de signar el capit\xE0 de l'equip local per tancar l'acta.");
                            resultado = false;
                        }
                    }
                }
                if (!document.getElementById("capi_v1").checked
                        && !document.getElementById("capi_v2").checked
                        && !document.getElementById("capi_v3").checked
                        && !document.getElementById("capi_v4").checked
                        && !document.getElementById("capi_v5").checked
                        && !document.getElementById("capi_v6").checked
                        && !document.getElementById("capi_v7").checked
                        && !document.getElementById("capi_v8").checked
                        && !document.getElementById("capi_v9").checked
                        && !document.getElementById("capi_v10").checked) {
                    alert("Ha d'indicar el capit\xE0 de l'equip visitant per tancar l'acta.");
                    resultado = false;
                } else {
                    if (action != "firma") {
                        if (document.getElementById("firma1_cv").value == "" || document.getElementById("firma2_cv").value == "") {
                            alert("Ha de signar el capit\xE0 de l'equip visitant per tancar l'acta.");
                            resultado = false;
                        }
                    }
                }
            }
            //Controlar que no haya dorsales repetidos
            if (hayDorsalesRepetidos("L")) {
                alert("Ha d'eliminar els dorsals repetits en l'equip local per tancar l'acta.");
                resultado = false;
            }
            if (hayDorsalesRepetidos("V")) {
                alert("Ha d'eliminar els dorsals repetits en l'equip visitant per tancar l'acta.");
                resultado = false;
            }
            //Controlar que no haya dorsales invalidos del 0 al 99
            if (hayDorsalesFueraRango("L")) {
                alert("Ha d'eliminar els dorsals no valids (0-99) en l'equip local per tancar l'acta.");
                resultado = false;
            }
            if (hayDorsalesFueraRango("V")) {
                alert("Ha d'eliminar els dorsals no valids (0-99) en l'equip visitant per tancar l'acta.");
                resultado = false;
            }
        } else {
            // Estas validaciones permiten guardar
            if (!document.getElementById("capi_l1").checked
                    && !document.getElementById("capi_l2").checked
                    && !document.getElementById("capi_l3").checked
                    && !document.getElementById("capi_l4").checked
                    && !document.getElementById("capi_l5").checked
                    && !document.getElementById("capi_l6").checked
                    && !document.getElementById("capi_l7").checked
                    && !document.getElementById("capi_l8").checked
                    && !document.getElementById("capi_l9").checked
                    && !document.getElementById("capi_l10").checked) {
                alert("L'equip local no ha indicat cap capit\xE0 de l'equip.");
            }
            if (!document.getElementById("capi_v1").checked
                    && !document.getElementById("capi_v2").checked
                    && !document.getElementById("capi_v3").checked
                    && !document.getElementById("capi_v4").checked
                    && !document.getElementById("capi_v5").checked
                    && !document.getElementById("capi_v6").checked
                    && !document.getElementById("capi_v7").checked
                    && !document.getElementById("capi_v8").checked
                    && !document.getElementById("capi_v9").checked
                    && !document.getElementById("capi_v10").checked) {
                alert("L'equip visitant no ha indicat cap capit\xE0 de l'equip.");
            }
        }
//        if (estaFlagActivo(document.getElementById("config").value, 2)) {
//            //FLAG 2 Validar que ho haya mas de 4 jugadores de categoria inferior, excepto junior
//            if (numeroJugadoresCategoria("L", document.getElementById("codcategorialocal").value) > 4)
//              //  alert("No \u00e9s pot alinear m\u00e9s de 4 jugadors de categoria inferior a l'equip local.");
//            if (numeroJugadoresCategoria("V", document.getElementById("codcategoriavisit").value) > 4)
//              //  alert("No \u00e9s pot alinear m\u00e9s de 4 jugadors de categoria inferior a l'equip visitant.");
//        }

        // Estas validaciones permiten guardar
        if (document.getElementById("codlic_l12").value == ""
                && document.getElementById("codlic_l13").value == ""
                && document.getElementById("codlic_l16").value == "") {
            alert("L'equip local no ha indicat cap delegat d'equip. No podr\xE0 sol.licitar temps mort en cap de les parts del partit.");
        }
        if (document.getElementById("codlic_v12").value == ""
                && document.getElementById("codlic_v13").value == ""
                && document.getElementById("codlic_v16").value == "") {
            alert("L'equip visitant no ha indicat cap delegat d'equip. No podr\xE0 sol.licitar temps mort en cap de les parts del partit.");
        }
    }

    if (action == "cerrar") {
        if (resultado == false) {
            if (confirm("S'han trobat incid\u00e8ncies. Voleu tancar l'acta amb incid\u00e8ncies?")) {
                resultado = true;
            }
        }
    }
    if (action == "firma") {
        resultado = true;
    }

    return resultado;
}

function numeroJugadoresCategoria(equipo, categoria) {
    var num = 0;
    if (equipo == "L") { //Local
        if (document.getElementById("cat_l1").value != categoria && document.getElementById("cat_l1").value != "JUN")
            num++;
        if (document.getElementById("cat_l2").value != categoria && document.getElementById("cat_l2").value != "JUN")
            num++;
        if (document.getElementById("cat_l3").value != categoria && document.getElementById("cat_l3").value != "JUN")
            num++;
        if (document.getElementById("cat_l4").value != categoria && document.getElementById("cat_l4").value != "JUN")
            num++;
        if (document.getElementById("cat_l5").value != categoria && document.getElementById("cat_l5").value != "JUN")
            num++;
        if (document.getElementById("cat_l6").value != categoria && document.getElementById("cat_l6").value != "JUN")
            num++;
        if (document.getElementById("cat_l7").value != categoria && document.getElementById("cat_l7").value != "JUN")
            num++;
        if (document.getElementById("cat_l8").value != categoria && document.getElementById("cat_l8").value != "JUN")
            num++;
        if (document.getElementById("cat_l9").value != categoria && document.getElementById("cat_l9").value != "JUN")
            num++;
        if (document.getElementById("cat_l10").value != categoria && document.getElementById("cat_l10").value != "JUN")
            num++;
    } else { //Visitante
        if (document.getElementById("cat_v1").value != categoria && document.getElementById("cat_l1").value != "JUN")
            num++;
        if (document.getElementById("cat_v2").value != categoria && document.getElementById("cat_l2").value != "JUN")
            num++;
        if (document.getElementById("cat_v3").value != categoria && document.getElementById("cat_l3").value != "JUN")
            num++;
        if (document.getElementById("cat_v4").value != categoria && document.getElementById("cat_l4").value != "JUN")
            num++;
        if (document.getElementById("cat_v5").value != categoria && document.getElementById("cat_l5").value != "JUN")
            num++;
        if (document.getElementById("cat_v6").value != categoria && document.getElementById("cat_l6").value != "JUN")
            num++;
        if (document.getElementById("cat_v7").value != categoria && document.getElementById("cat_l7").value != "JUN")
            num++;
        if (document.getElementById("cat_v8").value != categoria && document.getElementById("cat_l8").value != "JUN")
            num++;
        if (document.getElementById("cat_v9").value != categoria && document.getElementById("cat_l9").value != "JUN")
            num++;
        if (document.getElementById("cat_v10").value != categoria && document.getElementById("cat_l10").value != "JUN")
            num++;
    }
    return num;
}

function numporteros(equipo) {
    var num = 0;
    if (equipo == "L") { // Local
        if (document.getElementById("codlic_l1").value != "")
            num++;
        if (document.getElementById("codlic_l10").value != "")
            num++;
    } else { // Visitante
        if (document.getElementById("codlic_v1").value != "")
            num++;
        if (document.getElementById("codlic_v10").value != "")
            num++;
    }
    return num;
}

function numjugadores(equipo) {
    var num = 0;
    if (equipo == "L") { // Local
        if (document.getElementById("codlic_l2").value != "")
            num++;
        if (document.getElementById("codlic_l3").value != "")
            num++;
        if (document.getElementById("codlic_l4").value != "")
            num++;
        if (document.getElementById("codlic_l5").value != "")
            num++;
        if (document.getElementById("codlic_l6").value != "")
            num++;
        if (document.getElementById("codlic_l7").value != "")
            num++;
        if (document.getElementById("codlic_l8").value != "")
            num++;
        if (document.getElementById("codlic_l9").value != "")
            num++;
    } else { // Visitante
        if (document.getElementById("codlic_v2").value != "")
            num++;
        if (document.getElementById("codlic_v3").value != "")
            num++;
        if (document.getElementById("codlic_v4").value != "")
            num++;
        if (document.getElementById("codlic_v5").value != "")
            num++;
        if (document.getElementById("codlic_v6").value != "")
            num++;
        if (document.getElementById("codlic_v7").value != "")
            num++;
        if (document.getElementById("codlic_v8").value != "")
            num++;
        if (document.getElementById("codlic_v9").value != "")
            num++;
    }
    return num;
}

function estaFlagActivo(config, flag) {
    // 16 Flags del campo eactaconf. Si el flag n esta activo...
    // 1 - No se puede añadir un jugador sancionado ni local ni visitante
    // 2 - No se pueden añadir mas de 4 jugadores de categoria inferior a la del partido (excepto junior codecat=7)
    // 3 - Deben haber tres jugadores y un portero mínimo para guardar el acta
    // 4 - Delegado de pista obligatorio
    // 5 - Cronometrador obligatorio
    // 6 - Se validan los permisos del usuario. Debe ser el arbitro del partido o admin
    // 7 - Envia email en tancar l'acta
    // 8 - Control marcar capità i signatura capità obligatòries
    // 9 - Enviar mail a Secretaria Tecnica
    // 10 - El cronometrador es árbitro o federativo
    // 11 - El delegado de pista es árbitro o federativo
    // 12 - 16 - Libres
    config.substr
    if (config.substr(flag - 1, 1) == "1")
        return true;
    else
        return false;
}

function cargaInicial() {

    var el = null;
    if (document.getElementById("codlic_l1").value != "") {
        el = document.getElementById("add_l1");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l2").value != "") {
        el = document.getElementById("add_l2");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l3").value != "") {
        el = document.getElementById("add_l3");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l4").value != "") {
        el = document.getElementById("add_l4");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l5").value != "") {
        el = document.getElementById("add_l5");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l6").value != "") {
        el = document.getElementById("add_l6");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l7").value != "") {
        el = document.getElementById("add_l7");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l8").value != "") {
        el = document.getElementById("add_l8");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l9").value != "") {
        el = document.getElementById("add_l9");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l10").value != "") {
        el = document.getElementById("add_l10");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l11").value != "") {
        el = document.getElementById("add_l11");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l12").value != "") {
        el = document.getElementById("add_l12");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l13").value != "") {
        el = document.getElementById("add_l13");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l14").value != "") {
        el = document.getElementById("add_l14");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l15").value != "") {
        el = document.getElementById("add_l15");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l16").value != "") {
        el = document.getElementById("add_l16");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_l17").value != "") {
        el = document.getElementById("add_l17");
        el.style.display = 'none';
    }
    // VISITANT
    if (document.getElementById("codlic_v1").value != "") {
        el = document.getElementById("add_v1");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v2").value != "") {
        el = document.getElementById("add_v2");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v3").value != "") {
        el = document.getElementById("add_v3");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v4").value != "") {
        el = document.getElementById("add_v4");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v5").value != "") {
        el = document.getElementById("add_v5");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v6").value != "") {
        el = document.getElementById("add_v6");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v7").value != "") {
        el = document.getElementById("add_v7");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v8").value != "") {
        el = document.getElementById("add_v8");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v9").value != "") {
        el = document.getElementById("add_v9");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v10").value != "") {
        el = document.getElementById("add_v10");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v11").value != "") {
        el = document.getElementById("add_v11");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v12").value != "") {
        el = document.getElementById("add_v12");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v13").value != "") {
        el = document.getElementById("add_v13");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v14").value != "") {
        el = document.getElementById("add_v14");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v15").value != "") {
        el = document.getElementById("add_v15");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v16").value != "") {
        el = document.getElementById("add_v16");
        el.style.display = 'none';
    }
    if (document.getElementById("codlic_v17").value != "") {
        el = document.getElementById("add_v17");
        el.style.display = 'none';
    }

    if (document.getElementById("seccion").value == "FIRMA1") {
        mostrarAnteriores('firmaspre');
        mostrar('firmaspre');
    }
    if (document.getElementById("seccion").value == "FIRMA2") {
        mostrarAnteriores('firmas');
        mostrar('firmas');
    }
    if (document.getElementById("seccion").value == "EVENTOS") {
        mostrar('eventos');
    }

    if (document.getElementById("seccion").value == "GENEVENTOS") {
        mostrar('geneventos');
    }

    if (document.getElementById("estado").value == "C") {
        //Acta cerrada no debe poder cambiar nada
        el = document.getElementById("add_ar1");
        el.style.display = 'none';
        el = document.getElementById("del_ar1");
        el.style.display = 'none';
        el = document.getElementById("add_ar2");
        el.style.display = 'none';
        el = document.getElementById("del_ar2");
        el.style.display = 'none';
        el = document.getElementById("add_cr");
        el.style.display = 'none';
        el = document.getElementById("del_cr");
        el.style.display = 'none';
        el = document.getElementById("add_dp");
        el.style.display = 'none';
        el = document.getElementById("del_dp");
        el.style.display = 'none';

        document.getElementById("capi_l1").setAttribute("disabled", "disabled")
        document.getElementById("num_l1").readOnly = true;
        el = document.getElementById("add_l1");
        el.style.display = 'none';
        el = document.getElementById("del_l1");
        el.style.display = 'none';
        document.getElementById("capi_l2").setAttribute("disabled", "disabled")
        document.getElementById("num_l2").readOnly = true;
        el = document.getElementById("add_l2");
        el.style.display = 'none';
        el = document.getElementById("del_l2");
        el.style.display = 'none';
        document.getElementById("capi_l3").setAttribute("disabled", "disabled")
        document.getElementById("num_l3").readOnly = true;
        el = document.getElementById("add_l3");
        el.style.display = 'none';
        el = document.getElementById("del_l3");
        el.style.display = 'none';
        document.getElementById("capi_l4").setAttribute("disabled", "disabled")
        document.getElementById("num_l4").readOnly = true;
        el = document.getElementById("add_l4");
        el.style.display = 'none';
        el = document.getElementById("del_l4");
        el.style.display = 'none';
        document.getElementById("capi_l5").setAttribute("disabled", "disabled")
        document.getElementById("num_l5").readOnly = true;
        el = document.getElementById("add_l5");
        el.style.display = 'none';
        el = document.getElementById("del_l5");
        el.style.display = 'none';
        document.getElementById("capi_l6").setAttribute("disabled", "disabled")
        document.getElementById("num_l6").readOnly = true;
        el = document.getElementById("add_l6");
        el.style.display = 'none';
        el = document.getElementById("del_l6");
        el.style.display = 'none';
        document.getElementById("capi_l7").setAttribute("disabled", "disabled")
        document.getElementById("num_l7").readOnly = true;
        el = document.getElementById("add_l7");
        el.style.display = 'none';
        el = document.getElementById("del_l7");
        el.style.display = 'none';
        document.getElementById("capi_l8").setAttribute("disabled", "disabled")
        document.getElementById("num_l8").readOnly = true;
        el = document.getElementById("add_l8");
        el.style.display = 'none';
        el = document.getElementById("del_l8");
        el.style.display = 'none';
        document.getElementById("capi_l9").setAttribute("disabled", "disabled")
        document.getElementById("num_l9").readOnly = true;
        el = document.getElementById("add_l9");
        el.style.display = 'none';
        el = document.getElementById("del_l9");
        el.style.display = 'none';
        document.getElementById("capi_l10").setAttribute("disabled", "disabled")
        document.getElementById("num_l10").readOnly = true;
        el = document.getElementById("add_l10");
        el.style.display = 'none';
        el = document.getElementById("del_l10");
        el.style.display = 'none';
        el = document.getElementById("add_l11");
        el.style.display = 'none';
        el = document.getElementById("del_l11");
        el.style.display = 'none';
        el = document.getElementById("add_l12");
        el.style.display = 'none';
        el = document.getElementById("del_l12");
        el.style.display = 'none';
        el = document.getElementById("add_l13");
        el.style.display = 'none';
        el = document.getElementById("del_l13");
        el.style.display = 'none';
        el = document.getElementById("add_l14");
        el.style.display = 'none';
        el = document.getElementById("del_l14");
        el.style.display = 'none';
        el = document.getElementById("add_l15");
        el.style.display = 'none';
        el = document.getElementById("del_l15");
        el.style.display = 'none';
        el = document.getElementById("add_l16");
        el.style.display = 'none';
        el = document.getElementById("del_l16");
        el.style.display = 'none';
        el = document.getElementById("add_l17");
        el.style.display = 'none';
        el = document.getElementById("del_l17");
        el.style.display = 'none';

        document.getElementById("capi_v1").setAttribute("disabled", "disabled")
        document.getElementById("num_v1").readOnly = true;
        el = document.getElementById("add_v1");
        el.style.display = 'none';
        el = document.getElementById("del_v1");
        el.style.display = 'none';
        document.getElementById("capi_v2").setAttribute("disabled", "disabled")
        document.getElementById("num_v2").readOnly = true;
        el = document.getElementById("add_v2");
        el.style.display = 'none';
        el = document.getElementById("del_v2");
        el.style.display = 'none';
        document.getElementById("capi_v3").setAttribute("disabled", "disabled")
        document.getElementById("num_v3").readOnly = true;
        el = document.getElementById("add_v3");
        el.style.display = 'none';
        el = document.getElementById("del_v3");
        el.style.display = 'none';
        document.getElementById("capi_v4").setAttribute("disabled", "disabled")
        document.getElementById("num_v4").readOnly = true;
        el = document.getElementById("add_v4");
        el.style.display = 'none';
        el = document.getElementById("del_v4");
        el.style.display = 'none';
        document.getElementById("capi_v5").setAttribute("disabled", "disabled")
        document.getElementById("num_v5").readOnly = true;
        el = document.getElementById("add_v5");
        el.style.display = 'none';
        el = document.getElementById("del_v5");
        el.style.display = 'none';
        document.getElementById("capi_v6").setAttribute("disabled", "disabled")
        document.getElementById("num_v6").readOnly = true;
        el = document.getElementById("add_v6");
        el.style.display = 'none';
        el = document.getElementById("del_v6");
        el.style.display = 'none';
        document.getElementById("capi_v7").setAttribute("disabled", "disabled")
        document.getElementById("num_v7").readOnly = true;
        el = document.getElementById("add_v7");
        el.style.display = 'none';
        el = document.getElementById("del_v7");
        el.style.display = 'none';
        document.getElementById("capi_v8").setAttribute("disabled", "disabled")
        document.getElementById("num_v8").readOnly = true;
        el = document.getElementById("add_v8");
        el.style.display = 'none';
        el = document.getElementById("del_v8");
        el.style.display = 'none';
        document.getElementById("capi_v9").setAttribute("disabled", "disabled")
        document.getElementById("num_v9").readOnly = true;
        el = document.getElementById("add_v9");
        el.style.display = 'none';
        el = document.getElementById("del_v9");
        el.style.display = 'none';
        document.getElementById("capi_v10").setAttribute("disabled", "disabled")
        document.getElementById("num_v10").readOnly = true;
        el = document.getElementById("add_v10");
        el.style.display = 'none';
        el = document.getElementById("del_v10");
        el.style.display = 'none';
        el = document.getElementById("add_v11");
        el.style.display = 'none';
        el = document.getElementById("del_v11");
        el.style.display = 'none';
        el = document.getElementById("add_v12");
        el.style.display = 'none';
        el = document.getElementById("del_v12");
        el.style.display = 'none';
        el = document.getElementById("add_v13");
        el.style.display = 'none';
        el = document.getElementById("del_v13");
        el.style.display = 'none';
        el = document.getElementById("add_v14");
        el.style.display = 'none';
        el = document.getElementById("del_v14");
        el.style.display = 'none';
        el = document.getElementById("add_v15");
        el.style.display = 'none';
        el = document.getElementById("del_v15");
        el.style.display = 'none';
        el = document.getElementById("add_v16");
        el.style.display = 'none';
        el = document.getElementById("del_v16");
        el.style.display = 'none';
        el = document.getElementById("add_v17");
        el.style.display = 'none';
        el = document.getElementById("del_v17");
        el.style.display = 'none';

        el = document.getElementById("add_event");
        el.style.display = 'none';

        document.getElementById("observacions").readOnly = true;
    }

//    if ((document.getElementById("firma1_cl").value != "") || (document.getElementById("firma1_cv").value != "")) {
//        var licencia = document.getElementById("codlic_l11").value;
//        if (licencia == "") {
//            document.getElementById("firma1pin_el").style = "width:60px;background-color: #A0A0A0";             
//             document.getElementById("firma1pin_el").readOnly = true;            
//        }
//        var licencia = document.getElementById("codlic_v11").value;
//        if (licencia == "") {
//            document.getElementById("firma1pin_ev").style = "width:60px;background-color: #A0A0A0";
//             document.getElementById("firma1pin_ev").readOnly = true;
//        }
//    }


}

// DELETES
function deleteJugadorlocal(posicion, equipo) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el jugador " + (posicion + 1) + " de l'equip local " + equipo + ". Voleu continuar?")) {
        document.getElementById("codlic_l" + posicion).value = "";
        document.getElementById("cat_l" + posicion).value = "";
        document.getElementById("nom_cognoms_l" + posicion).value = "";
        document.getElementById("num_l" + posicion).value = "0";
        document.getElementById("pin_l" + posicion).value = "0";
        document.getElementById("capi_l" + posicion).checked = false;
        el = document.getElementById("add_l" + posicion);
        el.style.display = 'block';
        borrarPrefirma();
        modificat = true;
    }

}

function marcaCapitanLocal(posicion) {
    if (hayAlgunaPrefirma()) {
        if (document.getElementById("capi_l" + posicion).checked == true)
            document.getElementById("capi_l" + posicion).checked = false;
        else
            document.getElementById("capi_l" + posicion).checked = true;
        return false;
    }
    if (posicion == 1)
        document.getElementById("capi_l1").checked = true;
    else
        document.getElementById("capi_l1").checked = false;
    if (posicion == 2)
        document.getElementById("capi_l2").checked = true;
    else
        document.getElementById("capi_l2").checked = false;
    if (posicion == 3)
        document.getElementById("capi_l3").checked = true;
    else
        document.getElementById("capi_l3").checked = false;
    if (posicion == 4)
        document.getElementById("capi_l4").checked = true;
    else
        document.getElementById("capi_l4").checked = false;
    if (posicion == 5)
        document.getElementById("capi_l5").checked = true;
    else
        document.getElementById("capi_l5").checked = false;
    if (posicion == 6)
        document.getElementById("capi_l6").checked = true;
    else
        document.getElementById("capi_l6").checked = false;
    if (posicion == 7)
        document.getElementById("capi_l7").checked = true;
    else
        document.getElementById("capi_l7").checked = false;
    if (posicion == 8)
        document.getElementById("capi_l8").checked = true;
    else
        document.getElementById("capi_l8").checked = false;
    if (posicion == 9)
        document.getElementById("capi_l9").checked = true;
    else
        document.getElementById("capi_l9").checked = false;
    if (posicion == 10)
        document.getElementById("capi_l10").checked = true;
    else
        document.getElementById("capi_l10").checked = false;
    borrarPrefirma();
}

function marcaCapitanVisit(posicion) {
    if (hayAlgunaPrefirma()) {
        if (document.getElementById("capi_v" + posicion).checked == true)
            document.getElementById("capi_v" + posicion).checked = false;
        else
            document.getElementById("capi_v" + posicion).checked = true;
        return false;
    }
    if (posicion == 1)
        document.getElementById("capi_v1").checked = true;
    else
        document.getElementById("capi_v1").checked = false;
    if (posicion == 2)
        document.getElementById("capi_v2").checked = true;
    else
        document.getElementById("capi_v2").checked = false;
    if (posicion == 3)
        document.getElementById("capi_v3").checked = true;
    else
        document.getElementById("capi_v3").checked = false;
    if (posicion == 4)
        document.getElementById("capi_v4").checked = true;
    else
        document.getElementById("capi_v4").checked = false;
    if (posicion == 5)
        document.getElementById("capi_v5").checked = true;
    else
        document.getElementById("capi_v5").checked = false;
    if (posicion == 6)
        document.getElementById("capi_v6").checked = true;
    else
        document.getElementById("capi_v6").checked = false;
    if (posicion == 7)
        document.getElementById("capi_v7").checked = true;
    else
        document.getElementById("capi_v7").checked = false;
    if (posicion == 8)
        document.getElementById("capi_v8").checked = true;
    else
        document.getElementById("capi_v8").checked = false;
    if (posicion == 9)
        document.getElementById("capi_v9").checked = true;
    else
        document.getElementById("capi_v9").checked = false;
    if (posicion == 10)
        document.getElementById("capi_v10").checked = true;
    else
        document.getElementById("capi_v10").checked = false;
    borrarPrefirma();
}

function deleteJugadorvisit(posicion, equipo) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el jugador " + (posicion + 1) + " de l'equip visitant " + equipo + ". Voleu continuar?")) {
        document.getElementById("codlic_v" + posicion).value = "";
        document.getElementById("cat_v" + posicion).value = "";
        document.getElementById("nom_cognoms_v" + posicion).value = "";
        document.getElementById("num_v" + posicion).value = "0";
        document.getElementById("pin_v" + posicion).value = "0";
        document.getElementById("capi_v" + posicion).checked = false;
        el = document.getElementById("add_v" + posicion);
        el.style.display = 'block';
        borrarPrefirma();
        modificat = true;
    }

}

function deleteTecnicolocal(posicion, equipo, tipo) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el t\u00E9cnic " + tipo + " de l'equip local " + equipo + ". Voleu continuar?")) {
        document.getElementById("codlic_l" + posicion).value = "";
        document.getElementById("nom_cognoms_l" + posicion).value = "";
        document.getElementById("num_l" + posicion).value = "0";
        el = document.getElementById("add_l" + posicion);
        el.style.display = 'block';
        borrarPrefirma();
        modificat = true;
    }
}

function deleteTecnicovisit(posicion, equipo, tipo) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el t\u00E9cnic " + tipo + " de l'equip visitant " + equipo + ". Voleu continuar?")) {
        document.getElementById("codlic_v" + posicion).value = "";
        document.getElementById("nom_cognoms_v" + posicion).value = "";
        document.getElementById("num_v" + posicion).value = "0";
        el = document.getElementById("add_v" + posicion);
        el.style.display = 'block';
        borrarPrefirma();
        modificat = true;
    }
}

function cambiarDorsal(dorsal) {
    if (hayAlgunaPrefirma()) {
        dorsal.value = dorsal.oldvalue;
        return false;
    }
    borrarPrefirma();
}

function focusDorsal(dorsal) {
    dorsal.oldvalue = dorsal.value;
    if (dorsal.value == 0) {
        dorsal.value = '';
    }
}

function blurDorsal(dorsal) {
    if (dorsal.value == '') {
        dorsal.value = 0;
    }
}

function deleteDelegatpista() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el Delegat de pista. Voleu continuar?")) {
        document.getElementById("codlic_dp").value = "";
        document.getElementById("nom_cognoms_dp").value = "";
        document.getElementById("nif_dp").value = "";
        document.getElementById("pin_dp");
        borrarPrefirma();
        modificat = true;
    }
}

function deleteCronometrador() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 el Cronometrador. Voleu continuar?")) {
        document.getElementById("codlic_cr").value = "";
        document.getElementById("nom_cognoms_cr").value = "";
        document.getElementById("nif_cr").value = "";
        borrarPrefirma();
        modificat = true;
    }
}

function deleteArbitro1() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 l'arbibre 1. Voleu continuar?")) {
        document.getElementById("codlic_ar1").value = "";
        document.getElementById("nom_cognoms_ar1").value = "";
        document.getElementById("nif_ar1").value = "";
        borrarPrefirma();
        modificat = true;
    }
}

function deleteArbitro2() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    if (confirm("S'eliminar\u00E0 l'arbibre 2. Voleu continuar?")) {
        document.getElementById("codlic_ar2").value = "";
        document.getElementById("nom_cognoms_ar2").value = "";
        document.getElementById("nif_ar2").value = "";
        document.getElementById("pin_ar2").value = "";
        borrarPrefirma();
        modificat = true;
    }
}

// VALIDACIONES
function esJugadorAsignadolocal(jugador) {
    for (var i = 1; i <= 10; i++) {
        if (document.getElementById("codlic_l" + i).value.trim().toUpperCase() == jugador
                .trim().toUpperCase())
            return true;
    }
    for (var i = 1; i <= 10; i++) {
        if (document.getElementById("nif_l" + i).value.trim().toUpperCase() == jugador
                .trim().toUpperCase())
            return true;
    }
    return false;
}

function esJugadorAsignadovisit(jugador) {
    for (var i = 1; i <= 10; i++) {
        if (document.getElementById("codlic_v" + i).value.trim().toUpperCase() == jugador
                .trim().toUpperCase())
            return true;
    }
    for (var i = 1; i <= 10; i++) {
        if (document.getElementById("nif_v" + i).value.trim().toUpperCase() == jugador
                .trim().toUpperCase())
            return true;
    }
    return false;
}

function esJugador(codlic, nif, jugador) {
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
            return true;
        }
    }
    for (i = 0; i < nif.length; i++) {
        if (nif[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
            return true;
        }
    }
    return false;
}

function esCronometradorAsignado(jugador) {
    return false;
    //Permitimos que el cronometrador sea un delegado o auxiliar asignado
//	if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
//			.trim().toUpperCase())
//		return true;
//	if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
//			.trim().toUpperCase())
//		return true;
//	return false;
}

function esArbitroAsignado(jugador) {
    if (document.getElementById("codlic_ar1").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_ar1").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_ar2").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_ar2").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    return false;
}

function esDelegadoPistaAsignado(jugador) {
    if (document.getElementById("codlic_l12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_cr").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_cr").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esTecnicoAsignadolocal(jugador) {
    // Si ya esta asignado como delegado local
    if (document.getElementById("codlic_l11").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l11").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esTecnicoAsignadovisit(jugador) {
    // Si ya esta asignado como delegado visit
    if (document.getElementById("codlic_v11").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v11").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esDelegadoAsignadolocal(jugador) {
    // Si ya esta asignado como delegado local
    if (document.getElementById("codlic_l12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    if (document.getElementById("nif_l12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esDelegadoAsignadovisit(jugador) {
    // Si ya esta asignado como delegado visit
    if (document.getElementById("codlic_v12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    if (document.getElementById("nif_v12").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v13").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v16").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esAuxiliarAsignadolocal(jugador) {
    // Si ya esta asignado como delegado local
    if (document.getElementById("codlic_l14").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l15").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_l17").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    if (document.getElementById("nif_l14").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l15").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_l17").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

function esAuxiliarAsignadovisit(jugador) {
    // Si ya esta asignado como delegado visit
    if (document.getElementById("codlic_v14").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v15").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("codlic_v17").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    if (document.getElementById("nif_v14").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v15").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_v17").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    // O se ha asignado como delegado de pista
    if (document.getElementById("codlic_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;
    if (document.getElementById("nif_dp").value.trim().toUpperCase() == jugador
            .trim().toUpperCase())
        return true;

    return false;
}

// AÑADIR
function addJugadorlocal(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("jugadoreslocal_codlic").value.split(",");
        var nif = document.getElementById("jugadoreslocal_nif").value.split(",");
        var cat = document.getElementById("jugadoreslocal_cat").value.split(",");
        var nom = document.getElementById("jugadoreslocal_nom_cognoms").value.split(",");
        var san = document.getElementById("jugadoreslocal_sancion").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        cat_final = "";
        nom_final = "";
        san_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esJugadorAsignadolocal(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        cat_final = cat[i];
                        nom_final = nom[i];
                        san_final = san[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final
                        .trim();
                if (document.getElementById("codlic_l" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            cat_final = cat[i];
                            nom_final = nom[i];
                            san_final = san[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                document.getElementById("nif_l" + posicion).value = nif_final.trim();
                document.getElementById("cat_l" + posicion).value = cat_final.trim();
                document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                document.getElementById("num_l" + posicion).value = "0";
                document.getElementById("sancion_l" + posicion).value = san_final.trim();
                el = document.getElementById("add_l" + posicion);
                el.style.display = 'none';
                borrarPrefirma();

                //variable afegida per controlar si aquesta part ha sigut modificada
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un jugador ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un jugador seleccionable. ")
        }
    }

}

function mostrarJugadorlocal() {
    var result = "Jugadors locals:\n\n";
    var codlic = document.getElementById("jugadoreslocal_codlic").value
            .split(",");
    var nif = document.getElementById("jugadoreslocal_nif").value.split(",");
    var cat = document.getElementById("jugadoreslocal_cat").value.split(",");
    var nom = document.getElementById("jugadoreslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + " (" + cat[i].trim() + ")\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addJugadorvisit(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("jugadoresvisit_codlic").value.split(",");
        var nif = document.getElementById("jugadoresvisit_nif").value.split(",");
        var cat = document.getElementById("jugadoresvisit_cat").value.split(",");
        var nom = document.getElementById("jugadoresvisit_nom_cognoms").value.split(",");
        var san = document.getElementById("jugadoresvisit_sancion").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        san_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esJugadorAsignadovisit(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        cat_final = cat[i];
                        nom_final = nom[i];
                        san_final = san[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final
                        .trim();
                if (document.getElementById("codlic_v" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            cat_final = cat[i];
                            nom_final = nom[i];
                            san_final = san[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                document.getElementById("nif_v" + posicion).value = nif_final.trim();
                document.getElementById("cat_v" + posicion).value = cat_final.trim();
                document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                document.getElementById("num_v" + posicion).value = "0";
                document.getElementById("sancion_v" + posicion).value = nom_final.trim();
                el = document.getElementById("add_l" + posicion);
                el.style.display = 'none';
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un jugador ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un jugador seleccionable. ")
        }
    }

}

function mostrarJugadorvisit() {
    var result = "Jugadors visitants:\n\n";
    var codlic = document.getElementById("jugadoresvisit_codlic").value
            .split(",");
    var nif = document.getElementById("jugadoresvisit_nif").value.split(",");
    var cat = document.getElementById("jugadoresvisit_cat").value.split(",");
    var nom = document.getElementById("jugadoresvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + " (" + cat[i].trim() + ")\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    alert(result);
}

function addDelegatpista() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        if (estaFlagActivo(document.getElementById("config").value, 11)) {
            // FLAG 11 - El delegado de pista es árbitro o federativo
            var codlic = document.getElementById("arbitros_codlic").value.split(",");
            var nif = document.getElementById("arbitros_nif").value.split(",");
            var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");
            var pin = document.getElementById("arbitros_pin").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            pin_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esDelegadoPistaAsignado(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_dp").value = codlic_final.trim();
                    if (document.getElementById("codlic_dp").value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pin_final = pin[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_dp").value = codlic_final.trim();
                    document.getElementById("nif_dp").value = nif_final.trim();
                    document.getElementById("nom_cognoms_dp").value = nom_final.trim();
                    document.getElementById("pin_dp").value = pin_final.trim();
                    borrarPrefirma();
                    modificat = true;
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un arbitre ja assignat. ")
                }
            } else {
                var codlic = document.getElementById("delegadosfede_codlic").value.split(",");
                var nif = document.getElementById("delegadosfede_nif").value.split(",");
                var nom = document.getElementById("delegadosfede_nom_cognoms").value.split(",");
                var pin = document.getElementById("delegadosfede_pin").value.split(",");
                // asignamos el nuevo
                pos = 0;
                nif_final = "";
                codlic_final = "";
                nom_final = "";
                pin_final = "";
                if (esJugador(codlic, nif, jugador)) {
                    if (!esDelegadoPistaAsignado(jugador)) {
                        for (var i = 0; i < codlic.length; i++) {
                            if (codlic[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pin_final = pin[i];
                                pos = i;
                            }
                        }
                        document.getElementById("codlic_dp").value = codlic_final.trim();
                        if (document.getElementById("codlic_dp").value == "") {
                            for (var i = 0; i < nif.length; i++) {
                                if (nif[i].trim().toUpperCase() == jugador.trim()
                                        .toUpperCase()) {
                                    codlic_final = codlic[i];
                                    nif_final = nif[i];
                                    nom_final = nom[i];
                                    pin_final = pin[i];
                                    pos = i;
                                }
                            }
                        }
                        alert(codlic_final.trim());
                        document.getElementById("codlic_dp").value = codlic_final.trim();
                        document.getElementById("nif_dp").value = nif_final.trim();
                        document.getElementById("nom_cognoms_dp").value = nom_final.trim();
                        document.getElementById("pin_dp").value = pin_final.trim();
                        modificat = true;
                    } else {
                        alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat federatiu ja assignat. ")
                    }
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un arbitre o delegat federatiu seleccionable. ")
                }
            }
        } else {
            var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
            var nif = document.getElementById("delegadoslocal_nif").value.split(",");
            var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");
            var pin = document.getElementById("delegadoslocal_pin").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            pin_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esDelegadoPistaAsignado(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_dp").value = codlic_final.trim();
                    if (document.getElementById("codlic_dp").value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pin_final = pin[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_dp").value = codlic_final.trim();
                    document.getElementById("nif_dp").value = nif_final.trim();
                    document.getElementById("nom_cognoms_dp").value = nom_final.trim();
                    document.getElementById("pin_dp").value = pin_final.trim();
                    borrarPrefirma();
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un t\u00E9cnic ja assignat. ")
                }
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un t\u00E9cnic seleccionable. ")
            }
        }
    }
}

function mostrarDelegatpista() {
    var result = "Delegats de pista:\n\n";
    if (estaFlagActivo(document.getElementById("config").value, 11)) {
        // 11 - El delegado de pista es árbitro o federativo
        var codlic = document.getElementById("arbitros_codlic").value.split(",");
        var nif = document.getElementById("arbitros_nif").value.split(",");
        var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }

        var codlic = document.getElementById("delegadosfede_codlic").value.split(",");
        var nif = document.getElementById("delegadosfede_nif").value.split(",");
        var nom = document.getElementById("delegadosfede_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }
        alert(result);
    } else {
        var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
        var nif = document.getElementById("delegadoslocal_nif").value.split(",");
        var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }

        alert(result);
    }
}

function addCronometrador() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        if (estaFlagActivo(document.getElementById("config").value, 10)) {
            // 10 - El cronometrador es árbitro o federativo
            var codlic = document.getElementById("arbitros_codlic").value.split(",");
            var nif = document.getElementById("arbitros_nif").value.split(",");
            var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esCronometradorAsignado(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_cr").value = codlic_final.trim();
                    if (document.getElementById("codlic_cr").value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_cr").value = codlic_final.trim();
                    document.getElementById("nif_cr").value = nif_final.trim();
                    document.getElementById("nom_cognoms_cr").value = nom_final.trim();
                    borrarPrefirma();
                    modificat = true;
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un arbitre ja assignat. ")
                }
            } else {
                var codlic = document.getElementById("delegadosfede_codlic").value.split(",");
                var nif = document.getElementById("delegadosfede_nif").value.split(",");
                var nom = document.getElementById("delegadosfede_nom_cognoms").value.split(",");

                // asignamos el nuevo
                pos = 0;
                nif_final = "";
                codlic_final = "";
                nom_final = "";
                if (esJugador(codlic, nif, jugador)) {
                    if (!esCronometradorAsignado(jugador)) {
                        for (var i = 0; i < codlic.length; i++) {
                            if (codlic[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pos = i;
                            }
                        }
                        document.getElementById("codlic_cr").value = codlic_final.trim();
                        if (document.getElementById("codlic_cr").value == "") {
                            for (var i = 0; i < nif.length; i++) {
                                if (nif[i].trim().toUpperCase() == jugador.trim()
                                        .toUpperCase()) {
                                    codlic_final = codlic[i];
                                    nif_final = nif[i];
                                    nom_final = nom[i];
                                    pos = i;
                                }
                            }
                        }
                        document.getElementById("codlic_cr").value = codlic_final.trim();
                        document.getElementById("nif_cr").value = nif_final.trim();
                        document.getElementById("nom_cognoms_cr").value = nom_final.trim();
                        modificat = true;
                    } else {
                        alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat federatiu ja assignat. ")
                    }
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un arbitre o delegat federatiu seleccionable. ")
                }
            }
        } else {
            // El cronometrador es delegado local
            var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
            var nif = document.getElementById("delegadoslocal_nif").value.split(",");
            var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esCronometradorAsignado(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_cr").value = codlic_final.trim();
                    if (document.getElementById("codlic_cr").value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_cr").value = codlic_final.trim();
                    document.getElementById("nif_cr").value = nif_final.trim();
                    document.getElementById("nom_cognoms_cr").value = nom_final.trim();
                    borrarPrefirma();
                    modificat = true;
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat ja assignat. ")
                }
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un delegat seleccionable. ")
            }
        }
    }
}

function mostrarCronometrador() {
    var result = "Cronometradors:\n\n";
    if (estaFlagActivo(document.getElementById("config").value, 10)) {
        // FLAG 10 - El cronometrador es árbitro o federativo
        var codlic = document.getElementById("arbitros_codlic").value.split(",");
        var nif = document.getElementById("arbitros_nif").value.split(",");
        var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }

        var codlic = document.getElementById("delegadosfede_codlic").value.split(",");
        var nif = document.getElementById("delegadosfede_nif").value.split(",");
        var nom = document.getElementById("delegadosfede_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }
        alert(result);
    } else {
        var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
        var nif = document.getElementById("delegadoslocal_nif").value.split(",");
        var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

        var lista = new Array("");
        for (var i = 0; i < codlic.length; i++) {
            if (codlic[i].trim() != "")
                lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
        }
        lista.sort();
        for (var i = 0; i < lista.length; i++) {
            result = result + lista[i];
        }
        alert(result);
    }
}

function addArbitro1() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("arbitros_codlic").value.split(",");
        var nif = document.getElementById("arbitros_nif").value.split(",");
        var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");
        var pin = document.getElementById("arbitros_pin").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        pin_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esArbitroAsignado(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pin_final = pin[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_ar1").value = codlic_final.trim();
                if (document.getElementById("codlic_ar1").value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_ar1").value = codlic_final.trim();
                document.getElementById("nif_ar1").value = nif_final.trim();
                document.getElementById("nom_cognoms_ar1").value = nom_final.trim();
                document.getElementById("pin_ar1").value = pin_final.trim();
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un arbitre ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un arbitre seleccionable. ")
        }
    }
}

function addArbitro2() {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("arbitros_codlic").value.split(",");
        var nif = document.getElementById("arbitros_nif").value.split(",");
        var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");
        var pin = document.getElementById("arbitros_pin").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        pin_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esArbitroAsignado(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pin_final = pin[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_ar2").value = codlic_final
                        .trim();
                if (document.getElementById("codlic_ar2").value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_ar2").value = codlic_final.trim();
                document.getElementById("nif_ar2").value = nif_final.trim();
                document.getElementById("nom_cognoms_ar2").value = nom_final.trim();
                document.getElementById("pin_ar2").value = pin_final.trim();
                //alert(document.getElementById("pin_ar2").value);
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un arbitre ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un arbitre seleccionable. ")
        }
    }
}

function mostrarArbitro() {
    var result = "Arbitres:\n\n";
    var codlic = document.getElementById("arbitros_codlic").value.split(",");
    var nif = document.getElementById("arbitros_nif").value.split(",");
    var nom = document.getElementById("arbitros_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addTecnicolocal(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("tecnicoslocal_codlic").value.split(",");
        var nif = document.getElementById("tecnicoslocal_nif").value.split(",");
        var nom = document.getElementById("tecnicoslocal_nom_cognoms").value.split(",");
        var cat = document.getElementById("tecnicoslocal_cat").value.split(",");
        var pin = document.getElementById("tecnicoslocal_pin").value.split(",");
        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        cat_final = "";
        pin_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esTecnicoAsignadolocal(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        cat_final = cat[i];
                        pin_final = pin[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                if (document.getElementById("codlic_l" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim() == jugador.trim()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            cat_final = cat[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                document.getElementById("nif_l" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                document.getElementById("cat_l" + posicion).value = cat_final.trim();
                document.getElementById("pin_l" + posicion).value = pin_final.trim();
                document.getElementById("pin_el").value = pin_final.trim();
                document.getElementById("num_l" + posicion).value = "100";
                el = document.getElementById("add_l" + posicion);
                el.style.display = 'none';
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un t\u00E9cnic ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un t\u00E9cnic seleccionable. ")
        }
    }
}

function mostrarTecnicolocal() {
    var result = "T\u00E9cnics locals:\n\n";
    var codlic = document.getElementById("tecnicoslocal_codlic").value.split(",");
    var nif = document.getElementById("tecnicoslocal_nif").value.split(",");
    var nom = document.getElementById("tecnicoslocal_nom_cognoms").value.split(",");
    var cat = document.getElementById("tecnicoslocal_cat").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + " (" + cat[i].trim() + ")\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addTecnicovisit(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("tecnicosvisit_codlic").value.split(",");
        var nif = document.getElementById("tecnicosvisit_nif").value.split(",");
        var nom = document.getElementById("tecnicosvisit_nom_cognoms").value.split(",");
        var cat = document.getElementById("tecnicosvisit_cat").value.split(",");
        var pin = document.getElementById("tecnicosvisit_pin").value.split(",");
        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        cat_final = "";
        pin_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esTecnicoAsignadovisit(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        cat_final = cat[i];
                        pin_final = pin[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                if (document.getElementById("codlic_v" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            cat_final = cat[i];
                            pin_final = pin[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                document.getElementById("nif_v" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                document.getElementById("cat_v" + posicion).value = cat_final.trim();
                document.getElementById("pin_v" + posicion).value = pin_final.trim();
                document.getElementById("pin_ev").value = pin_final.trim();
                document.getElementById("num_v" + posicion).value = "100";
                el = document.getElementById("add_l" + posicion);
                el.style.display = 'none';
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un t\u00E9cnic ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un t\u00E9cnic seleccionable. ")
        }
    }
}

function mostrarTecnicovisit() {
    var result = "T\u00E9cnics visitants:\n\n";
    var codlic = document.getElementById("tecnicosvisit_codlic").value.split(",");
    var nif = document.getElementById("tecnicosvisit_nif").value.split(",");
    var nom = document.getElementById("tecnicosvisit_nom_cognoms").value.split(",");
    var cat = document.getElementById("tecnicosvisit_cat").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + " (" + cat[i].trim() + ")\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addDelegadolocal(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
        var nif = document.getElementById("delegadoslocal_nif").value.split(",");
        var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esDelegadoAsignadolocal(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                if (document.getElementById("codlic_l" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                document.getElementById("nif_l" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                document.getElementById("num_l" + posicion).value = "101";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un delegat seleccionable. ")
        }
    }
}

function mostrarDelegadolocal() {
    var result = "Delegats locals:\n\n";
    var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
    var nif = document.getElementById("delegadoslocal_nif").value.split(",");
    var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addDelegadovisit(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("delegadosvisit_codlic").value.split(",");
        var nif = document.getElementById("delegadosvisit_nif").value.split(",");
        var nom = document.getElementById("delegadosvisit_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esDelegadoAsignadovisit(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                if (document.getElementById("codlic_v" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                document.getElementById("nif_v" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                document.getElementById("num_v" + posicion).value = "101";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un delegat seleccionable. ")
        }
    }
}

function mostrarDelegadovisit() {
    var result = "Delegats visitants:\n\n";
    var codlic = document.getElementById("delegadosvisit_codlic").value.split(",");
    var nif = document.getElementById("delegadosvisit_nif").value.split(",");
    var nom = document.getElementById("delegadosvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function addAuxiliarlocal(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("auxiliareslocal_codlic").value.split(",");
        var nif = document.getElementById("auxiliareslocal_nif").value.split(",");
        var nom = document.getElementById("auxiliareslocal_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esAuxiliarAsignadolocal(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                if (document.getElementById("codlic_l" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                document.getElementById("nif_l" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                document.getElementById("cat_l" + posicion).value = "AUXILIAR";
                document.getElementById("num_l" + posicion).value = "102";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un auxiliar ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un auxiliar seleccionable. ")
        }
    }
}

function addDelegadoAuxiliarTecnicolocal(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
        var nif = document.getElementById("delegadoslocal_nif").value.split(",");
        var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esDelegadoAsignadolocal(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final
                        .trim();
                if (document.getElementById("codlic_l" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                document.getElementById("nif_l" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                document.getElementById("cat_l" + posicion).value = "DELEGAT/DA";
                if (posicion == 16)
                    document.getElementById("num_l" + posicion).value = "103";
                if (posicion == 14)
                    document.getElementById("num_l" + posicion).value = "104";
                if (posicion == 15)
                    document.getElementById("num_l" + posicion).value = "105";
                if (posicion == 17)
                    document.getElementById("num_l" + posicion).value = "106";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat ja assignat. ")
            }
        } else {
            var codlic = document.getElementById("auxiliareslocal_codlic").value.split(",");
            var nif = document.getElementById("auxiliareslocal_nif").value.split(",");
            var nom = document.getElementById("auxiliareslocal_nom_cognoms").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esAuxiliarAsignadolocal(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_l" + posicion).value = codlic_final
                            .trim();
                    if (document.getElementById("codlic_l" + posicion).value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                    document.getElementById("nif_l" + posicion).value = nif_final.trim();
                    document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                    document.getElementById("cat_l" + posicion).value = "AUXILIAR";
                    if (posicion == 16)
                        document.getElementById("num_l" + posicion).value = "103";
                    if (posicion == 14)
                        document.getElementById("num_l" + posicion).value = "104";
                    if (posicion == 15)
                        document.getElementById("num_l" + posicion).value = "105";
                    if (posicion == 17)
                        document.getElementById("num_l" + posicion).value = "106";
                    borrarPrefirma();
                    modificat = true;
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un auxiliar ja assignat. ")
                }
            } else {
                var codlic = document.getElementById("tecnicoslocal_codlic").value.split(",");
                var nif = document.getElementById("tecnicoslocal_nif").value.split(",");
                var nom = document.getElementById("tecnicoslocal_nom_cognoms").value.split(",");
                var cat = document.getElementById("tecnicoslocal_cat").value.split(",");
                // asignamos el nuevo
                pos = 0;
                nif_final = "";
                codlic_final = "";
                nom_final = "";
                cat_final = "";
                if (esJugador(codlic, nif, jugador)) {
                    if (!esTecnicoAsignadolocal(jugador)) {
                        for (var i = 0; i < codlic.length; i++) {
                            if (codlic[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                cat_final = cat[i];
                                pos = i;
                            }
                        }
                        document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                        if (document.getElementById("codlic_l" + posicion).value == "") {
                            for (var i = 0; i < nif.length; i++) {
                                if (nif[i].trim() == jugador.trim()) {
                                    codlic_final = codlic[i];
                                    nif_final = nif[i];
                                    nom_final = nom[i];
                                    cat_final = cat[i];
                                    pos = i;
                                }
                            }
                        }
                        document.getElementById("codlic_l" + posicion).value = codlic_final.trim();
                        document.getElementById("nif_l" + posicion).value = nif_final.trim();
                        document.getElementById("nom_cognoms_l" + posicion).value = nom_final.trim();
                        document.getElementById("cat_l" + posicion).value = cat_final.trim();
                        if (posicion == 16)
                            document.getElementById("num_l" + posicion).value = "103";
                        if (posicion == 14)
                            document.getElementById("num_l" + posicion).value = "104";
                        if (posicion == 15)
                            document.getElementById("num_l" + posicion).value = "105";
                        if (posicion == 17)
                            document.getElementById("num_l" + posicion).value = "106";
                        borrarPrefirma();
                        modificat = true;
                    } else {
                        alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un t\u00E9cnic ja assignat. ")
                    }
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un delegat, auxiliar o t\u00E9cnic seleccionable. ")
                }
            }
        }
    }
}

function mostrarAuxiliarlocal() {
    var result = "Auxiliars locals:\n\n";
    var codlic = document.getElementById("auxiliareslocal_codlic").value.split(",");
    var nif = document.getElementById("auxiliareslocal_nif").value.split(",");
    var nom = document.getElementById("auxiliareslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function mostrarDelegadoAuxiliarTecnicolocal() {
    var result = "Delegats, Auxiliars i T\u00E9cnics locals:\n\n";
    var codlic = document.getElementById("delegadoslocal_codlic").value.split(",");
    var nif = document.getElementById("delegadoslocal_nif").value.split(",");
    var nom = document.getElementById("delegadoslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    var codlic = document.getElementById("auxiliareslocal_codlic").value.split(",");
    var nif = document.getElementById("auxiliareslocal_nif").value.split(",");
    var nom = document.getElementById("auxiliareslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    var codlic = document.getElementById("tecnicoslocal_codlic").value.split(",");
    var nif = document.getElementById("tecnicoslocal_nif").value.split(",");
    var nom = document.getElementById("tecnicoslocal_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    alert(result);
}

function addAuxiliarvisit(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("auxiliaresvisit_codlic").value.split(",");
        var nif = document.getElementById("auxiliaresvisit_nif").value.split(",");
        var nom = document.getElementById("auxiliaresvisit_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esAuxiliarAsignadovisit(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final
                        .trim();
                if (document.getElementById("codlic_v" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                document.getElementById("nif_v" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                document.getElementById("cat_v" + posicion).value = "AUXILIAR";
                document.getElementById("num_v" + posicion).value = "102";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un auxiliar ja assignat. ")
            }
        } else {
            alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un auxiliar seleccionable. ")
        }
    }
}

function addDelegadoAuxiliarTecnicovisit(posicion) {
    if (hayAlgunaPrefirma()) {
        return false;
    }
    var jugador = prompt("Introdu\u00EFu DNI o n\u00FAmero de llic\u00E8ncia:", "");
    if (jugador != null) {
        var codlic = document.getElementById("delegadosvisit_codlic").value.split(",");
        var nif = document.getElementById("delegadosvisit_nif").value.split(",");
        var nom = document.getElementById("delegadosvisit_nom_cognoms").value.split(",");

        // asignamos el nuevo
        pos = 0;
        nif_final = "";
        codlic_final = "";
        nom_final = "";
        if (esJugador(codlic, nif, jugador)) {
            if (!esDelegadoAsignadovisit(jugador)) {
                for (var i = 0; i < codlic.length; i++) {
                    if (codlic[i].trim().toUpperCase() == jugador.trim()
                            .toUpperCase()) {
                        codlic_final = codlic[i];
                        nif_final = nif[i];
                        nom_final = nom[i];
                        pos = i;
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final
                        .trim();
                if (document.getElementById("codlic_v" + posicion).value == "") {
                    for (var i = 0; i < nif.length; i++) {
                        if (nif[i].trim().toUpperCase() == jugador.trim().toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                }
                document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                document.getElementById("nif_v" + posicion).value = nif_final.trim();
                document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                document.getElementById("cat_v" + posicion).value = "DELEGAT/DA";
                if (posicion == 16)
                    document.getElementById("num_v" + posicion).value = "103";
                if (posicion == 14)
                    document.getElementById("num_v" + posicion).value = "104";
                if (posicion == 15)
                    document.getElementById("num_v" + posicion).value = "105";
                if (posicion == 17)
                    document.getElementById("num_v" + posicion).value = "106";
                borrarPrefirma();
                modificat = true;
            } else {
                alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un delegat ja assignat. ")
            }
        } else {
            var codlic = document.getElementById("auxiliaresvisit_codlic").value.split(",");
            var nif = document.getElementById("auxiliaresvisit_nif").value.split(",");
            var nom = document.getElementById("auxiliaresvisit_nom_cognoms").value.split(",");

            // asignamos el nuevo
            pos = 0;
            nif_final = "";
            codlic_final = "";
            nom_final = "";
            if (esJugador(codlic, nif, jugador)) {
                if (!esAuxiliarAsignadovisit(jugador)) {
                    for (var i = 0; i < codlic.length; i++) {
                        if (codlic[i].trim().toUpperCase() == jugador.trim()
                                .toUpperCase()) {
                            codlic_final = codlic[i];
                            nif_final = nif[i];
                            nom_final = nom[i];
                            pos = i;
                        }
                    }
                    document.getElementById("codlic_v" + posicion).value = codlic_final
                            .trim();
                    if (document.getElementById("codlic_v" + posicion).value == "") {
                        for (var i = 0; i < nif.length; i++) {
                            if (nif[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                pos = i;
                            }
                        }
                    }
                    document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                    document.getElementById("nif_v" + posicion).value = nif_final.trim();
                    document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                    document.getElementById("cat_v" + posicion).value = "AUXILIAR";
                    if (posicion == 16)
                        document.getElementById("num_v" + posicion).value = "103";
                    if (posicion == 14)
                        document.getElementById("num_v" + posicion).value = "104";
                    if (posicion == 15)
                        document.getElementById("num_v" + posicion).value = "105";
                    if (posicion == 17)
                        document.getElementById("num_v" + posicion).value = "106";
                    borrarPrefirma();
                    modificat = true;
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un auxiliar ja assignat. ")
                }
            } else {
                var codlic = document.getElementById("tecnicosvisit_codlic").value.split(",");
                var nif = document.getElementById("tecnicosvisit_nif").value.split(",");
                var nom = document.getElementById("tecnicosvisit_nom_cognoms").value.split(",");
                var cat = document.getElementById("tecnicosvisit_cat").value.split(",");
                // asignamos el nuevo
                pos = 0;
                nif_final = "";
                codlic_final = "";
                nom_final = "";
                cat_final = "";
                if (esJugador(codlic, nif, jugador)) {
                    if (!esTecnicoAsignadovisit(jugador)) {
                        for (var i = 0; i < codlic.length; i++) {
                            if (codlic[i].trim().toUpperCase() == jugador.trim()
                                    .toUpperCase()) {
                                codlic_final = codlic[i];
                                nif_final = nif[i];
                                nom_final = nom[i];
                                cat_final = cat[i];
                                pos = i;
                            }
                        }
                        document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                        if (document.getElementById("codlic_v" + posicion).value == "") {
                            for (var i = 0; i < nif.length; i++) {
                                if (nif[i].trim().toUpperCase() == jugador.trim()
                                        .toUpperCase()) {
                                    codlic_final = codlic[i];
                                    nif_final = nif[i];
                                    nom_final = nom[i];
                                    cat_final = cat[i];
                                    pos = i;
                                }
                            }
                        }
                        document.getElementById("codlic_v" + posicion).value = codlic_final.trim();
                        document.getElementById("nif_v" + posicion).value = nif_final.trim();
                        document.getElementById("nom_cognoms_v" + posicion).value = nom_final.trim();
                        document.getElementById("cat_v" + posicion).value = cat_final.trim();
                        if (posicion == 16)
                            document.getElementById("num_v" + posicion).value = "103";
                        if (posicion == 14)
                            document.getElementById("num_v" + posicion).value = "104";
                        if (posicion == 15)
                            document.getElementById("num_v" + posicion).value = "105";
                        if (posicion == 17)
                            document.getElementById("num_v" + posicion).value = "106";
                        borrarPrefirma();
                        modificat = true;
                    } else {
                        alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat es d'un t\u00E9cnic ja assignat. ")
                    }
                } else {
                    alert("El DNI o n\u00FAmero de llic\u00E8ncia indicat no pertany a un delegat, auxiliar o t\u00E9cnic seleccionable. ")
                }
            }
        }
    }
}

function mostrarAuxiliarvisit() {
    var result = "Auxiliars visitants\n\n";
    var codlic = document.getElementById("auxiliaresvisit_codlic").value.split(",");
    var nif = document.getElementById("auxiliaresvisit_nif").value.split(",");
    var nom = document.getElementById("auxiliaresvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function mostrarDelegadoAuxiliarTecnicovisit() {
    var result = "Delegats, Auxiliars i T\u00E9cnics visitants:\n\n";
    var codlic = document.getElementById("delegadosvisit_codlic").value.split(",");
    var nif = document.getElementById("delegadosvisit_nif").value.split(",");
    var nom = document.getElementById("delegadosvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    var codlic = document.getElementById("auxiliaresvisit_codlic").value.split(",");
    var nif = document.getElementById("auxiliaresvisit_nif").value.split(",");
    var nom = document.getElementById("auxiliaresvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }

    var codlic = document.getElementById("tecnicosvisit_codlic").value.split(",");
    var nif = document.getElementById("tecnicosvisit_nif").value.split(",");
    var nom = document.getElementById("tecnicosvisit_nom_cognoms").value.split(",");

    var lista = new Array("");
    for (var i = 0; i < codlic.length; i++) {
        if (codlic[i].trim() != "")
            lista[i] = codlic[i].trim() + " / " + nif[i].trim() + " - " + nom[i].trim() + "\n";
    }
    lista.sort();
    for (var i = 0; i < lista.length; i++) {
        result = result + lista[i];
    }
    alert(result);
}

function mostrar(id) {

    if (document.getElementById(id)) {
        var el = document.getElementById(id);
        el.style.display = 'block';
        ocultarBotones();
    }
}

function ocultar(id) {

    if (document.getElementById(id)) {
        var el = document.getElementById(id);
        el.style.display = 'none';
    }
}

function mostrarAnteriores(id) {
    if (id == "firmaspre") {
        mostrar('cabecera');
        mostrar('equipolocal');
        mostrar('equipovisit');
        ocultar('tornar_cabecera');
        ocultar('tornar_equipolocal');
        ocultar('tornar_equipovisit');

    }
    if (id == "firmas") {
        mostrar('cabecera');
        mostrar('equipolocal');
        mostrar('equipovisit');
        mostrar('eventos');
        mostrar('geneventos');
        mostrar('observaciones');
        ocultar('tornar_cabecera');
        ocultar('tornar_equipolocal');
        ocultar('tornar_equipovisit');
        ocultar('tornar_eventos');
        ocultar('tornar_geneventos');
        ocultar('tornar_observaciones');
    }
}

function ocultarBotones() {

    // Ocultar
    var el = document.getElementById('boton_cabecera');
    el.style.display = 'none';
    el = document.getElementById('boton_equipolocal');
    el.style.display = 'none';
    el = document.getElementById('boton_equipovisit');
    el.style.display = 'none';
    el = document.getElementById('boton_firmaspre');
    el.style.display = 'none';
    el = document.getElementById('boton_eventos');
    el.style.display = 'none';
    el = document.getElementById('boton_geneventos');
    el.style.display = 'none';
    el = document.getElementById('boton_observaciones');
    el.style.display = 'none';
    el = document.getElementById('boton_firmas');
    el.style.display = 'none';
    // el = document.getElementById('boton_guardar');
    // el.style.display = 'none';
    el = document.getElementById('boton_cerrar');
    el.style.display = 'none';
    el = document.getElementById('boton_descargar');
    el.style.display = 'none';
    el = document.getElementById('boton_preview');
    el.style.display = 'none';
    el = document.getElementById('boton_salir');
    el.style.display = 'none';
    el = document.getElementById('boton_salirguardar');
    el.style.display = 'none';

}



function mostrarBotones() {


    // modal.style.display = "block";
    // Mostrar
    var el = document.getElementById('boton_cabecera');
    el.style.display = 'block';
    el = document.getElementById('boton_equipolocal');
    el.style.display = 'block';
    el = document.getElementById('boton_equipovisit');
    el.style.display = 'block';
    el = document.getElementById('boton_firmaspre');
    el.style.display = 'block';
    el = document.getElementById('boton_eventos');
    el.style.display = 'block';
    el = document.getElementById('boton_geneventos');
    el.style.display = 'block';
    el = document.getElementById('boton_observaciones');
    el.style.display = 'block';
    el = document.getElementById('boton_firmas');
    el.style.display = 'block';
    // el = document.getElementById('boton_guardar');
    // el.style.display = 'block';
    el = document.getElementById('boton_cerrar');
    if (document.getElementById('estado').value == "A")
        el.style.display = 'block';
    else
        el.style.display = 'none';
    el = document.getElementById('boton_descargar');
    if (document.getElementById('estado').value == "C")
        el.style.display = 'block';
    else
        el.style.display = 'none';
    el = document.getElementById('boton_preview');
    if (document.getElementById('estado').value == "A")
        el.style.display = 'block';
    else
        el.style.display = 'none';
    el = document.getElementById('boton_salir');
    el.style.display = 'block';
    el = document.getElementById('boton_salirguardar');
    if (document.getElementById('estado').value == "A")
        el.style.display = 'block';
    else
        el.style.display = 'none';

    // Ocultar
    var el = document.getElementById('cabecera');
    el.style.display = 'none';
    el = document.getElementById('equipolocal');
    el.style.display = 'none';
    el = document.getElementById('equipovisit');
    el.style.display = 'none';
    el = document.getElementById('firmaspre');
    el.style.display = 'none';
    el = document.getElementById('eventos');
    el.style.display = 'none';
    el = document.getElementById('geneventos');
    el.style.display = 'none';
    el = document.getElementById('observaciones');
    el.style.display = 'none';
    el = document.getElementById('firmas');
    el.style.display = 'none';

    document.getElementById("seccion").value = "";
}
function hoy() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; // January is 0!
    var yyyy = today.getFullYear();
    var hh = today.getHours();
    var zz = today.getMinutes()

    if (dd < 10) {
        dd = '0' + dd
    }

    if (mm < 10) {
        mm = '0' + mm
    }

    if (hh < 10) {
        hh = '0' + hh
    }

    if (zz < 10) {
        zz = '0' + zz
    }
    today = dd + '/' + mm + '/' + yyyy + ' ' + hh + ":" + zz;
    return today;
}

function firmarDelegadospista(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }
    if (document.getElementById("codlic_dp").value == "") {
        //  alert("Ha d'indicar el delegat de pista a la cap\xE7alera de l'acta.");
    } else {
        var nombre = document.getElementById("nom_cognoms_dp").value;
        var licencia = document.getElementById("codlic_dp").value;
        if (document.getElementById("firma1pin_dp").value == "") {
            // alert("Ha d\'indicar el pin del delegat de pista.");
            firma1Delegat = false;
        } else {
            if (document.getElementById("firma1pin_dp").value == document
                    .getElementById("pin_dp").value) {
                document.getElementById("firma1_dp").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy();
//                lbfirma1pin_dp.style.display = "none";
//                firma1pin_dp.style.display = "none";
//                txtfirma1pin_dp.textContent = "Signat per "
//                        + nombre
//                        + " ("
//                        + licencia
//                        + ") a " + hoy();
                firma1Delegat = true;
//                txtfirma1pin_dp.style.display="block";
                //   enviarForm(form, "firma1_delegadopista");
            } else {
                alert("El pin del delegat de pista \xE9s incorrecte.");
            }
        }
    }
}




function firmar1Capitanlocal(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }
    var licencia = licenciaCapitan("local");
    var protesto = "";
    if (document.getElementById("protesto1_cl").checked)
        protesto = "PROTESTO ALINEACI\xD3";
    preProtest = true;
    if (document.getElementById("assabentat1_cl").checked)
        protesto = "PROTESTO ASSABENTAT ALINEACI\xD3";


    if (licencia == "") {
        //  alert("Ha d'indicar el capit\xE0 de l\'equip local.");
    } else {
        var nombre = nombreCapitan("local")
        if (document.getElementById("firma1pin_cl").value == "") {
            //  alert("Ha d\'indicar el pin del capit\xE0 de l\'equip local.");
            firma1CapitaLocal = false;
        } else {
            if (document.getElementById("firma1pin_cl").value == pinCapitan("local")
                    || document.getElementById("firma1pin_cl").value == pinEquipo("local")) {
                document.getElementById("firma1_cl").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy() + ". "
                        + protesto;
                firma1CapitaLocal = true;

            } else {
                alert("El pin del capit\xE0 de l\'equip local \xE9s incorrecte.");
            }
        }
    }
}

function firmar1Capitanvisit(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }
    var licencia = licenciaCapitan("visit");
    var protesto = "";
    if (document.getElementById("protesto1_cv").checked)
        protesto = "PROTESTO ALINEACI\xD3";
    preProtest = true;
    if (document.getElementById("assabentat1_cv").checked)
        protesto = "PROTESTO ASSABENTAT ALINEACI\xD3";

    if (licencia == "") {
        //  alert("Ha d'indicar el capit\xE0 de l\'equip visitant.");
    } else {
        var nombre = nombreCapitan("visit")
        if (document.getElementById("firma1pin_cv").value == "") {
            //  alert("Ha d\'indicar el pin del capit\xE0 de l\'equip visitant.");
            firma1CapitaVisitant = false;
        } else {
            if (document.getElementById("firma1pin_cv").value == pinCapitan("visit")
                    || document.getElementById("firma1pin_cv").value == pinEquipo("visit")) {
                document.getElementById("firma1_cv").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy() + ". "
                        + protesto;
                firma1CapitaVisitant = true;
//                prfirma1pin_cv.style.display = "none";
//                firma1pin_cv.style.display = "none";
//                lbfirma1pin_cv.style.display = "none";
//                txtfirma1pin_cv.textContent = "Signat per" + nombre
//                        + " ("
//                        + licencia
//                        + ") a " + hoy() + ". "
//                        + protesto;
//                txtfirma1pin_cv.style.display="block";
                //   enviarForm(form, "firma1_capitanvisit");
            } else {
                alert("El pin del capit\xE0 de l\'equip visitant \xE9s incorrecte.");
            }
        }
    }
}

function nombreCapitan(equipo) {
    if (equipo == "local") {
        if (document.getElementById("capi_l1").checked == true)
            return document.getElementById("nom_cognoms_l1").value;
        if (document.getElementById("capi_l2").checked == true)
            return document.getElementById("nom_cognoms_l2").value;
        if (document.getElementById("capi_l3").checked == true)
            return document.getElementById("nom_cognoms_l3").value;
        if (document.getElementById("capi_l4").checked == true)
            return document.getElementById("nom_cognoms_l4").value;
        if (document.getElementById("capi_l5").checked == true)
            return document.getElementById("nom_cognoms_l5").value;
        if (document.getElementById("capi_l6").checked == true)
            return document.getElementById("nom_cognoms_l6").value;
        if (document.getElementById("capi_l7").checked == true)
            return document.getElementById("nom_cognoms_l7").value;
        if (document.getElementById("capi_l8").checked == true)
            return document.getElementById("nom_cognoms_l8").value;
        if (document.getElementById("capi_l9").checked == true)
            return document.getElementById("nom_cognoms_l9").value;
        if (document.getElementById("capi_l10").checked == true)
            return document.getElementById("nom_cognoms_l10").value;
    } else {
        if (document.getElementById("capi_v1").checked == true)
            return document.getElementById("nom_cognoms_v1").value;
        if (document.getElementById("capi_v2").checked == true)
            return document.getElementById("nom_cognoms_v2").value;
        if (document.getElementById("capi_v3").checked == true)
            return document.getElementById("nom_cognoms_v3").value;
        if (document.getElementById("capi_v4").checked == true)
            return document.getElementById("nom_cognoms_v4").value;
        if (document.getElementById("capi_v5").checked == true)
            return document.getElementById("nom_cognoms_v5").value;
        if (document.getElementById("capi_v6").checked == true)
            return document.getElementById("nom_cognoms_v6").value;
        if (document.getElementById("capi_v7").checked == true)
            return document.getElementById("nom_cognoms_v7").value;
        if (document.getElementById("capi_v8").checked == true)
            return document.getElementById("nom_cognoms_v8").value;
        if (document.getElementById("capi_v9").checked == true)
            return document.getElementById("nom_cognoms_v9").value;
        if (document.getElementById("capi_v10").checked == true)
            return document.getElementById("nom_cognoms_v10").value;
    }
    return "";
}

function licenciaCapitan(equipo) {
    if (equipo == "local") {
        if (document.getElementById("capi_l1").checked == true)
            return document.getElementById("codlic_l1").value;
        if (document.getElementById("capi_l2").checked == true)
            return document.getElementById("codlic_l2").value;
        if (document.getElementById("capi_l3").checked == true)
            return document.getElementById("codlic_l3").value;
        if (document.getElementById("capi_l4").checked == true)
            return document.getElementById("codlic_l4").value;
        if (document.getElementById("capi_l5").checked == true)
            return document.getElementById("codlic_l5").value;
        if (document.getElementById("capi_l6").checked == true)
            return document.getElementById("codlic_l6").value;
        if (document.getElementById("capi_l7").checked == true)
            return document.getElementById("codlic_l7").value;
        if (document.getElementById("capi_l8").checked == true)
            return document.getElementById("codlic_l8").value;
        if (document.getElementById("capi_l9").checked == true)
            return document.getElementById("codlic_l9").value;
        if (document.getElementById("capi_l10").checked == true)
            return document.getElementById("codlic_l10").value;
    } else {
        if (document.getElementById("capi_v1").checked == true)
            return document.getElementById("codlic_v1").value;
        if (document.getElementById("capi_v2").checked == true)
            return document.getElementById("codlic_v2").value;
        if (document.getElementById("capi_v3").checked == true)
            return document.getElementById("codlic_v3").value;
        if (document.getElementById("capi_v4").checked == true)
            return document.getElementById("codlic_v4").value;
        if (document.getElementById("capi_v5").checked == true)
            return document.getElementById("codlic_v5").value;
        if (document.getElementById("capi_v6").checked == true)
            return document.getElementById("codlic_v6").value;
        if (document.getElementById("capi_v7").checked == true)
            return document.getElementById("codlic_v7").value;
        if (document.getElementById("capi_v8").checked == true)
            return document.getElementById("codlic_v8").value;
        if (document.getElementById("capi_v9").checked == true)
            return document.getElementById("codlic_v9").value;
        if (document.getElementById("capi_v10").checked == true)
            return document.getElementById("codlic_v10").value;
    }
    return "";
}

function pinCapitan(equipo) {
    if (equipo == "local") {
        if (document.getElementById("capi_l1").checked)
            return document.getElementById("pin_l1").value;
        if (document.getElementById("capi_l2").checked)
            return document.getElementById("pin_l2").value;
        if (document.getElementById("capi_l3").checked)
            return document.getElementById("pin_l3").value;
        if (document.getElementById("capi_l4").checked)
            return document.getElementById("pin_l4").value;
        if (document.getElementById("capi_l5").checked)
            return document.getElementById("pin_l5").value;
        if (document.getElementById("capi_l6").checked)
            return document.getElementById("pin_l6").value;
        if (document.getElementById("capi_l7").checked)
            return document.getElementById("pin_l7").value;
        if (document.getElementById("capi_l8").checked)
            return document.getElementById("pin_l8").value;
        if (document.getElementById("capi_l9").checked)
            return document.getElementById("pin_l9").value;
        if (document.getElementById("capi_10").checked)
            return document.getElementById("pin_l10").value;
    } else {
        if (document.getElementById("capi_v1").checked)
            return document.getElementById("pin_v1").value;
        if (document.getElementById("capi_v2").checke)
            return document.getElementById("pin_v2").value;
        if (document.getElementById("capi_v3").checke)
            return document.getElementById("pin_v3").value;
        if (document.getElementById("capi_v4").checke)
            return document.getElementById("pin_v4").value;
        if (document.getElementById("capi_v5").checke)
            return document.getElementById("pin_v5").value;
        if (document.getElementById("capi_v6").checke)
            return document.getElementById("pin_v6").value;
        if (document.getElementById("capi_v7").checke)
            return document.getElementById("pin_v7").value;
        if (document.getElementById("capi_v8").checke)
            return document.getElementById("pin_v8").value;
        if (document.getElementById("capi_v9").checke)
            return document.getElementById("pin_v9").value;
        if (document.getElementById("capi_v10").checke)
            return document.getElementById("pin_v10").value;
    }
    return "";
}

function pinEquipo(equipo) {
    if (equipo == "local") {
        return document.getElementById("pin_clubl").value;
    } else {
        return document.getElementById("pin_clubv").value;
    }
}

function firmar1Entrenadorlocal(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }
    var licencia = document.getElementById("codlic_l11").value;
    if (licencia == "") {
        //  alert("Ha d'indicar el t\xE8cnic de l\'equip local.");
    } else {
        var nombre = document.getElementById("nom_cognoms_l11").value;
        if (document.getElementById("firma1pin_el").value == "") {
            //  alert("Ha d\'indicar el pin del t\xE8cnic de l\'equip local.");
            firma1TecnicLocal = false;
        } else {
            if (document.getElementById("firma1pin_el").value == document.getElementById("pin_el").value) {
                document.getElementById("firma1_el").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy();
                firma1TecnicLocal = true;
//                lbfirma1pin_el.style.display = "none";
//                firma1pin_el.style.display = "none";
//                txtfirma1pin_el.textContent = "Signat per "
//                        + nombre
//                        + " ("
//                        + licencia
//                        + ") a " + hoy();
//                txtfirma1pin_el.style.display="block";
                //   enviarForm(form, "firma1_entrenadorlocal");
            } else {
                alert("El pin del t\xE8cnic de l\'equip local \xE9s incorrecte.");
            }
        }
    }
}

function firmar1Entrenadorvisit(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }
    var licencia = document.getElementById("codlic_v11").value;
    if (licencia == "") {
        // alert("Ha d'indicar el t\xE8cnic de l\'equip visitant.");
    } else {
        var nombre = document.getElementById("nom_cognoms_v11").value;
        if (document.getElementById("firma1pin_ev").value == "") {
            //   alert("Ha d\'indicar el pin del t\xE8cnic de l\'equip visitant.");
            firma1TecnicVisitant = false;
        } else {
            if (document.getElementById("firma1pin_ev").value == document
                    .getElementById("pin_ev").value) {
                document.getElementById("firma1_ev").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy();
                firma1TecnicVisitant = true;
//                lbfirma1pin_ev.style.display = "none";
//                firma1pin_ev.style.display = "none";
//                txtfirma1pin_ev.textContent = "Signat per "
//                        + nombre
//                        + " ("
//                        + licencia
//                        + ") a " + hoy();
//                txtfirma1pin_ev.style.display="block";
                //   enviarForm(form, "firma1_entrenadorvisit");
            } else {
                alert("El pin del t\xE8cnic de l\'equip visitant \xE9s incorrecte.");
            }
        }
    }
}

function firmar2Capitanlocal(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }


    var licencia = licenciaCapitan("local");
    var protesto = "";
    if (document.getElementById("protesto2_cl").checked)
        protesto = "PROTESTO RESULTAT";
    protest = true;
    if (document.getElementById("assabentat2_cl").checked)
        protesto = "PROTESTO ASSABENTAT RESULTAT";

    if (licencia == "") {
        // alert("Ha d'indicar el capit\xE0 de l\'equip local.");
    } else {
        var nombre = nombreCapitan("local")
        if (document.getElementById("firma2pin_cl").value == "") {
            firma2CapitaLocal = false;
            //  alert("Ha d\'indicar el pin del capit\xE0 de l\'equip local.");
        } else {
            if (document.getElementById("firma2pin_cl").value == pinCapitan("local")
                    || document.getElementById("firma2pin_cl").value == pinEquipo("local")) {
                document.getElementById("firma2_cl").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy() + ". "
                        + protesto;
                firma2CapitaLocal = true;
                //  enviarForm(form, "firma2_capitanlocal");             


            } else {
                alert("El pin del capit\xE0 de l\'equip local \xE9s incorrecte.");
            }
        }
    }
}

function firmar2Capitanvisit(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }

    var licencia = licenciaCapitan("visit");
    var protesto = "";
    if (document.getElementById("protesto2_cv").checked)
        protesto = "PROTESTO RESULTAT";
    protest = true;
    if (document.getElementById("assabentat2_cv").checked)
        protesto = "PROTESTO ASSABENTAT RESULTAT";

    if (licencia == "") {
        //  alert("Ha d'indicar el capit\xE0 de l\'equip visitant.");
    } else {
        var nombre = nombreCapitan("visit")
        if (document.getElementById("firma2pin_cv").value == "") {
            firma2CapitaVisitant = false;
            // alert("Ha d\'indicar el pin del capit\xE0 de l\'equip visitant.");
        } else {
            if (document.getElementById("firma2pin_cv").value == pinCapitan("visit")
                    || document.getElementById("firma2pin_cv").value == pinEquipo("visit")) {
                document.getElementById("firma2_cv").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy() + ". "
                        + protesto;
                firma2CapitaVisitant = true;
                //   enviarForm(form, "firma2_capitanvisit");
            } else {
                alert("El pin del capit\xE0 de l\'equip visitant \xE9s incorrecte.");
            }
        }
    }
}

function firmar2Arbitro(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }

    if (document.getElementById("codlic_ar1").value == "") {
        //  alert("Ha d'indicar l\'arbitre a la cap\xE7alera de l'acta.");
    } else {
        var nombre = document.getElementById("nom_cognoms_ar1").value;
        var licencia = document.getElementById("codlic_ar1").value;
        if (document.getElementById("firma2pin_ar").value == "") {
            firma2Arbitre = false;
            // alert("Ha d\'indicar el pin de l\'arbitre.");
        } else {
            if (document.getElementById("firma2pin_ar").value == document
                    .getElementById("pin_ar1").value) {
                document.getElementById("firma2_ar").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy();
                firma2Arbitre = true;
                //  enviarForm(form, "firma2_arbitro");
            } else {
                alert("El pin de l\'arbitre \xE9s incorrecte.");
            }
        }
    }
}

function firmar2Arbitro2(form) {
//    if (!validar(form, "firma")) {
//        return false;
//    }

    if (document.getElementById("codlic_ar2").value == "") {
        //  alert("Ha d'indicar l\'arbitre auxiliar a la cap\xE7alera de l'acta.");
    } else {
        var nombre = document.getElementById("nom_cognoms_ar2").value;
        var licencia = document.getElementById("codlic_ar2").value;
        if (document.getElementById("firma2pin_ar2").value == "") {
            firma2ArbitreAuxiliar = false;
            //alert("Ha d\'indicar el pin de l\'arbitre auxiliar.");
        } else {
            if (document.getElementById("firma2pin_ar2").value == document
                    .getElementById("pin_ar2").value) {
                document.getElementById("firma2_ar2").value = "Signat per "
                        + nombre
                        + " ("
                        + licencia
                        + ") a " + hoy();
                firma2ArbitreAuxiliar = true;
                //   enviarForm(form, "firma2_arbitro2");
            } else {
                alert("El pin de l\'arbitre auxiliar \xE9s incorrecte.");
            }
        }
    }
}

function deleteEvento(form, idevento) {
    if (document.getElementById("estado").value == "A") {
        if (hayAlgunaFirma()) {
            return false;
        }
        if (confirm("S'eliminar\u00E0 el event. Voleu continuar?")) {
            document.getElementById("delevento").value = idevento;
            borrarFirma();
            enviarForm(form, "eliminarevento");
            borrarFirma();
            modificat = true;
        }
    }
}

function addEvento(idevento) {
    var el = document.getElementById('nuevoevento');
    el.style.display = 'block';
    var el = document.getElementById('eventos');
    el.style.display = 'none';
}

function aceptarEvento(form) {
    if (hayAlgunaFirma()) {
        return false;
    }
    continuar = true;
    if (document.getElementById('neventodorsal').value != "") {
        if (document.getElementById('neventoequipo').checked)
            equipo = "L";
        else
            equipo = "V";
        if (existeDorsal(equipo, document.getElementById('neventodorsal').value)) {

        } else {
            alert('No es troba el dorsal indicat.');
            continuar = false;
        }
    }
    if (continuar == true) {
        var el = document.getElementById('nuevoevento');
        el.style.display = 'none';
        if (el.value == "Tarja Vermella") {
            vermella = true;
        }
        var el = document.getElementById('eventos');
        el.style.display = 'block';
        borrarFirma();
        modificat = true;
        enviarForm(form, "nuevoevento");
    }
}

function descartarEvento() {
    if (confirm("Es descartar\u00E0 el nou event. Voleu continuar?")) {
        var el = document.getElementById('nuevoevento');
        el.style.display = 'none';
        var el = document.getElementById('eventos');
        el.style.display = 'block';
        document.getElementById("neventocrono").value = "";
        document.getElementById("neventodorsal").value = "";
        document.getElementById("neventoatribut").value = "";
    }
}

//function addEventoGol(form) {
//	if (hayAlgunaFirma()) {
//		return false;
//	}
//	continuar = true;
//	if (document.getElementById('cgolesd').value != "") {
//		if (document.getElementById('golesequipo').checked) equipo = "L";
//		else equipo = "V";
//		if (existeDorsal(equipo, document.getElementById('cgolesd').value)) {
//			
//		} else {
//			alert('No es troba el dorsal indicat.');
//			continuar = false;
//		}
//	} else {
//		alert('Indiqui les dades necesaries.');
//		continuar = false;
//	}
//	if (continuar == true) {
//		borrarFirma();
//		enviarForm(form, "nuevoeventogol");
//	}
//}

function addEventoTAzul(form) {
    if (hayAlgunaFirma()) {
        return false;
    }
    borrarFirma();
    continuar = true;
    if (document.getElementById('cfaltasad').value != "") {
        if (document.getElementById('faltasaequipo').checked)
            equipo = "L";
        else
            equipo = "V";
        if (existeDorsal(equipo, document.getElementById('cfaltasad').value)) {

        } else {
            alert('No es troba el dorsal indicat.');
            continuar = false;
        }
    } else {
        alert('Indiqui les dades necesaries.');
        continuar = false;
    }
    if (continuar == true) {
        borrarFirma();
        enviarForm(form, "nuevoeventotazul");
    }
}

function addEventoTRoja(form) {
    if (hayAlgunaFirma()) {
        return false;
    }
    borrarFirma();
    continuar = true;
    if (document.getElementById('cfaltasrd').value != "") {
        if (document.getElementById('faltasrequipo').checked)
            equipo = "L";
        else
            equipo = "V";
        if (existeDorsal(equipo, document.getElementById('cfaltasrd').value)) {

        } else {
            alert('No es troba el dorsal indicat.');
            continuar = false;
        }
    } else {
        alert('Indiqui les dades necesaries.');
        continuar = false;
    }
    if (continuar == true) {
        vermella = true;
        borrarFirma();
        enviarForm(form, "nuevoeventotroja");
    }
}

function mostrar_initial(id) {
    if (document.getElementById(id)) {
        var el = document.getElementById(id);
        el.style.display = 'initial';
        ocultarBotones();
    }
}

function borrarPrefirma() {
    prfirma1pin_cl.style.display = "block";
    firma1pin_cl.style.display = "block";
    lbfirma1pin_cl.style.display = "bloc";
    txtfirma1pin_cl.textContent = "";

    prfirma1pin_cv.style.display = "block";
    firma1pin_cv.style.display = "block";
    lbfirma1pin_cv.style.display = "block";
    txtfirma1pin_cv.textContent = "";

    lbfirma1pin_el.style.display = "block";
    firma1pin_el.style.display = "block";
    txtfirma1pin_el.textContent = "";

    lbfirma1pin_ev.style.display = "block";
    firma1pin_ev.style.display = "block";
    txtfirma1pin_ev.textContent = "";

    lbfirma1pin_dp.style.display = "block";
    firma1pin_dp.style.display = "block";
    txtfirma1pin_dp.textContent = "";



    document.getElementById("firma1_dp").value = "";
    document.getElementById("firma1_cl").value = "";
    document.getElementById("firma1_el").value = "";
    document.getElementById("firma1_cv").value = "";
    document.getElementById("firma1_ev").value = "";

    document.getElementById("firma1pin_dp").value = "";
    document.getElementById("firma1pin_cl").value = "";
    document.getElementById("firma1pin_el").value = "";
    document.getElementById("firma1pin_cv").value = "";
    document.getElementById("firma1pin_ev").value = "";

    mostrar_initial("lbfirma1pin_dp");
    mostrar_initial("firma1pin_dp");
    mostrar_initial("btfirma1pin_dp");
    ocultar("txtfirma1pin_dp");
    mostrar_initial("prfirma1pin_cl");
    mostrar_initial("lbfirma1pin_cl");
    mostrar_initial("firma1pin_cl");
    mostrar_initial("btfirma1pin_cl");
    ocultar("txtfirma1pin_cl");
    mostrar_initial("lbfirma1pin_el");
    mostrar_initial("firma1pin_el");
    mostrar_initial("btfirma1pin_el");
    ocultar("txtfirma1pin_el");
    mostrar_initial("prfirma1pin_cv");
    mostrar_initial("lbfirma1pin_cv");
    mostrar_initial("firma1pin_cv");
    mostrar_initial("btfirma1pin_cv");
    ocultar("txtfirma1pin_cv");
    mostrar_initial("lbfirma1pin_ev");
    mostrar_initial("firma1pin_ev");
    mostrar_initial("btfirma1pin_ev");
    ocultar("txtfirma1pin_ev");

    document.getElementById("firma1_dp_actualizar").value = "1";
    document.getElementById("firma1_cl_actualizar").value = "1";
    document.getElementById("firma1_el_actualizar").value = "1";
    document.getElementById("firma1_cv_actualizar").value = "1";
    document.getElementById("firma1_ev_actualizar").value = "1";

    preProtest = false;

    borrarFirma();
}

function borrarFirma() {
    document.getElementById("firma2pin_cl").value = "";
    document.getElementById("firma2pin_ar").value = "";
    document.getElementById("firma2pin_ar2").value = "";
    document.getElementById("firma2pin_cv").value = "";

    document.getElementById("firma2_cl").value = "";
    document.getElementById("firma2_ar").value = "";
    document.getElementById("firma2_ar2").value = "";
    document.getElementById("firma2_cv").value = "";

    mostrar_initial("prfirma2pin_cl");
    mostrar_initial("lbfirma2pin_cl");
    mostrar_initial("firma2pin_cl");
    mostrar_initial("btfirma2pin_cl");
    ocultar("txtfirma2pin_cl");

    mostrar_initial("lbfirma2pin_ar");
    mostrar_initial("firma2pin_ar");
    mostrar_initial("btfirma2pin_ar");
    ocultar("txtfirma2pin_ar");

    mostrar_initial("lbfirma2pin_ar2");
    mostrar_initial("firma2pin_ar2");
    mostrar_initial("btfirma2pin_ar2");
    ocultar("txtfirma2pin_ar2");

    mostrar_initial("prfirma2pin_cv");
    mostrar_initial("lbfirma2pin_cv");
    mostrar_initial("firma2pin_cv");
    mostrar_initial("btfirma2pin_cv");
    ocultar("txtfirma2pin_cv");

    document.getElementById("firma2_cl_actualizar").value = "1";
    document.getElementById("firma2_ar_actualizar").value = "1";
    document.getElementById("firma2_ar2_actualizar").value = "1";
    document.getElementById("firma2_cv_actualizar").value = "1";

//        document.getElementById("firma1pin_el").style = "width:60px;background-color: #FFFFFF";             
//             document.getElementById("firma1pin_el").readOnly = false;
//                document.getElementById("firma1pin_ev").style = "width:60px;background-color: #FFFFFF";             
//             document.getElementById("firma1pin_ev").readOnly = false;
    protest = false;

}

function ayudaEventos() {
    alert("AYUDA ATRIBUTO EVENTOS");
}

function hayDorsalesRepetidos(equipo) {
    resultado = false;
    if (equipo == "L") {
        for (var int1 = 1; int1 <= 10; int1++) {
            if (document.getElementById('codlic_l' + int1).value != "") {
                dorsal1 = document.getElementById("num_l" + int1).value
                for (var int2 = 1; int2 <= 10; int2++) {
                    if (int1 != int2) {
                        dorsal2 = document.getElementById("num_l" + int2).value
                        if (dorsal1 == dorsal2) {
                            resultado = true;
                            return resultado;
                        }
                    }
                }
            }
        }
    } else {
        for (var int1 = 1; int1 <= 10; int1++) {
            if (document.getElementById('codlic_v' + int1).value != "") {
                dorsal1 = document.getElementById("num_v" + int1).value
                for (var int2 = 1; int2 <= 10; int2++) {
                    if (int1 != int2) {
                        dorsal2 = document.getElementById("num_v" + int2).value
                        if (dorsal1 == dorsal2) {
                            resultado = true;
                            return resultado;
                        }
                    }
                }
            }
        }
    }
    return resultado;
}

function hayDorsalesFueraRango(equipo) {
    resultado = false;
    if (equipo == "L") {
        for (var int = 1; int <= 10; int++) {
            dorsal = document.getElementById("num_l" + int).value
            if (dorsal < 0 || dorsal > 99) {
                resultado = true;
                return resultado;
            }
        }
    } else {
        for (var int = 1; int <= 10; int++) {
            dorsal = document.getElementById("num_v" + int).value
            if (dorsal < 0 || dorsal > 99) {
                resultado = true;
                return resultado;
            }
        }
    }
    return resultado;
}

function existeDorsal(equipo, introdorsal) {
    resultado = false;
    if (equipo == "L") {
        for (var int = 1; int <= 17; int++) {
            dorsal = document.getElementById("num_l" + int).value
            if (dorsal == introdorsal) {
                resultado = true;
                return resultado;
            }
        }
    } else {
        for (var int = 1; int <= 17; int++) {
            dorsal = document.getElementById("num_v" + int).value
            if (dorsal == introdorsal) {
                resultado = true;
                return resultado;
            }
        }
    }
    return resultado;
}

function hayAlgunaPrefirma() {
    resultado = false;
    if (document.getElementById("firma1_dp").value != "" ||
            document.getElementById("firma1_cl").value != "" ||
            document.getElementById("firma1_el").value != "" ||
            document.getElementById("firma1_cv").value != "" ||
            document.getElementById("firma1_ev").value != "") {
        resultado = true;
    }
    if (resultado == true) {
        if (confirm("Si modifica l'acta s'eliminen les signatures. Voleu continuar?")) {
           // borrarFirma();
            resultado = false;
        }
    }
    return resultado;
}

function hayAlgunaFirma() {
    resultado = false;
    if (document.getElementById("firma2_cl").value != "" ||
            document.getElementById("firma2_ar").value != "" ||
            document.getElementById("firma2_cv").value != "") {
        resultado = true;
    }
    if (resultado == true) {
        if (confirm("Si modifica l'acta s'eliminen les signatures. Voleu continuar?")) {
          //  borrarFirma();
           
            resultado = false;
        }
    }
    return resultado;
}

function recalcularCamposGolesL(golesl) {
    if (golesl.value < golesl.oldvalue) {
        for (var int = golesl.value; int <= golesl.oldvalue; int++) {
            if (int > golesl.value) {
                var element = document.getElementById("cgolespl_" + int);
                element.parentNode.removeChild(element);

                var element = document.getElementById("cgolesml_" + int);
                element.parentNode.removeChild(element);

                var element = document.getElementById("cgolesdl_" + int);
                element.parentNode.removeChild(element);
            }
        }
        document.getElementById("cgolespl").value = "[" + document.getElementById("cgolespl_1").value;
        document.getElementById("cgolesml").value = "[" + document.getElementById("cgolespl_1").value;
        document.getElementById("cgolesdl").value = "[" + document.getElementById("cgolespl_1").value;
        for (var int = 2; int <= golesl.value; int++) {
            document.getElementById("cgolespl").value = document.getElementById("cgolespl").value + ", " + document.getElementById("cgolespl_" + int).value;
            document.getElementById("cgolesml").value = document.getElementById("cgolesml").value + ", " + document.getElementById("cgolesml_" + int).value;
            document.getElementById("cgolesdl").value = document.getElementById("cgolesdl").value + ", " + document.getElementById("cgolesdl_" + int).value;
        }
        document.getElementById("cgolespl").value = document.getElementById("cgolespl").value + "]";
        document.getElementById("cgolesml").value = document.getElementById("cgolesml").value + "]";
        document.getElementById("cgolesdl").value = document.getElementById("cgolesdl").value + "]";
    }
    return true;
}

function focusTotalGolesL(golesl) {
    golesl.oldvalue = golesl.value;
}

function recalcularCamposGolesV(golesv) {
    if (golesv.value < golesv.oldvalue) {
        for (var int = golesv.value; int <= golesv.oldvalue; int++) {
            if (int > golesv.value) {
                var element = document.getElementById("cgolespv_" + int);
                element.parentNode.removeChild(element);

                var element = document.getElementById("cgolesmv_" + int);
                element.parentNode.removeChild(element);

                var element = document.getElementById("cgolesdv_" + int);
                element.parentNode.removeChild(element);
            }
        }
        document.getElementById("cgolespv").value = "[" + document.getElementById("cgolespv_1").value;
        document.getElementById("cgolesmv").value = "[" + document.getElementById("cgolespv_1").value;
        document.getElementById("cgolesdv").value = "[" + document.getElementById("cgolespv_1").value;
        for (var int = 2; int <= golesv.value; int++) {
            document.getElementById("cgolespv").value = document.getElementById("cgolespv").value + ", " + document.getElementById("cgolespv_" + int).value;
            document.getElementById("cgolesmv").value = document.getElementById("cgolesmv").value + ", " + document.getElementById("cgolesmv_" + int).value;
            document.getElementById("cgolesdv").value = document.getElementById("cgolesdv").value + ", " + document.getElementById("cgolesdv_" + int).value;
        }
        document.getElementById("cgolespv").value = document.getElementById("cgolespv").value + "]";
        document.getElementById("cgolesmv").value = document.getElementById("cgolesmv").value + "]";
        document.getElementById("cgolesdv").value = document.getElementById("cgolesdv").value + "]";
    }
    return true;
}

function focusTotalGolesV(golesv) {
    golesv.oldvalue = golesv.value;
}