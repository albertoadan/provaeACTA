package es.fecapa.eacta.bean;

import java.util.ArrayList;

public class Acta {

    private String username;
    private String message;
    private String config;
    private String accion;
    private String seccion;
    private String delevento;
//    private String alerta;

    //Datos acta
    private long codpartit;
    private String codtemp;
    private String temporada;
    private String datajoc;
    private String horajoc;
    private Long jornada;
    private String competicio;
    private String nomcompeticio;
    private Long codequiplocal;
    private String nomequiplocal;
    private String mailequiplocal;
    private Long codequipvisit;
    private String nomequipvisit;
    private String mailequipvisit;
    private String poblaciojoc;
    private String codipostaljoc;
    private String adressajoc;
    private String observacions;

    private String finalitzat;
    private String modalitat;
    private Long codentitatlocal;
    private String codcategorialocal;
    private Long codclasselocal;
    private String classelocal;
    private Long codentitatvisit;
    private String codcategoriavisit;
    private Long codclassevisit;
    private String classevisit;
    private String pin_clubl;
    private String pin_clubv;

    //Delegado de pista
    private String codlic_dp;
    private String nom_cognoms_dp;
    private String nif_dp;

    //Cronometrador
    private String codlic_cr;
    private String nom_cognoms_cr;
    private String nif_cr;

    //Arbitro 1
    private long codtecnic;
    private String codlic_ar1;
    private String nom_cognoms_ar1;
    private String nif_ar1;
    private String pin_ar1;
    private String cat_ar1;
    private String email_ar1;
    private String territorial_ar1;
    private String telefono_ar1;

    //Arbitro 2
    private long codtecnic2;
    private String codlic_ar2;
    private String nom_cognoms_ar2;
    private String nif_ar2;
    private String pin_ar2;
    private String territorial_ar2;

    private String estado;

    //Para seleccionar jugadores
    private ArrayList<DatosActa> datosactalocal;	//jugadores local
    private ArrayList<DatosActa> datosactavisit;	//jugadores visit	
    private ArrayList<DatosActa> datosactaTlocal;	//tecnicos local
    private ArrayList<DatosActa> datosactaTvisit;	//tecnicos visit
    private ArrayList<DatosActa> datosactaTfede;	//tecnicos federacion
    private ArrayList<DatosActa> datosactaDlocal;	//delegados local
    private ArrayList<DatosActa> datosactaDvisit;	//delegados visit
    private ArrayList<DatosActa> datosactaDfede;	//delegados federacion
    private ArrayList<DatosActa> datosactaAlocal;	//auxiliares local
    private ArrayList<DatosActa> datosactaAvisit;	//auxiliares visit
    private ArrayList<DatosActa> datosactaArbitro;	//arbitros

    //Equipo local
    private String codlic_l1;
    private String codlic_l2;
    private String codlic_l3;
    private String codlic_l4;
    private String codlic_l5;
    private String codlic_l6;
    private String codlic_l7;
    private String codlic_l8;
    private String codlic_l9;
    private String codlic_l10;
    private String codlic_l11;
    private String codlic_l12;
    private String codlic_l13;
    private String codlic_l14;
    private String codlic_l15;
    private String codlic_l16;
    private String codlic_l17;

    private String cat_l1;
    private String cat_l2;
    private String cat_l3;
    private String cat_l4;
    private String cat_l5;
    private String cat_l6;
    private String cat_l7;
    private String cat_l8;
    private String cat_l9;
    private String cat_l10;
    private String cat_l11;
    private String cat_l12;
    private String cat_l13;
    private String cat_l14;
    private String cat_l15;
    private String cat_l16;
    private String cat_l17;

    private String nom_cognoms_l1;
    private String nom_cognoms_l2;
    private String nom_cognoms_l3;
    private String nom_cognoms_l4;
    private String nom_cognoms_l5;
    private String nom_cognoms_l6;
    private String nom_cognoms_l7;
    private String nom_cognoms_l8;
    private String nom_cognoms_l9;
    private String nom_cognoms_l10;
    private String nom_cognoms_l11;
    private String nom_cognoms_l12;
    private String nom_cognoms_l13;
    private String nom_cognoms_l14;
    private String nom_cognoms_l15;
    private String nom_cognoms_l16;
    private String nom_cognoms_l17;

    private long num_l1;
    private long num_l2;
    private long num_l3;
    private long num_l4;
    private long num_l5;
    private long num_l6;
    private long num_l7;
    private long num_l8;
    private long num_l9;
    private long num_l10;
    private long num_l11;
    private long num_l12;
    private long num_l13;
    private long num_l14;
    private long num_l15;
    private long num_l16;
    private long num_l17;

    private String sancion_l1;
    private String sancion_l2;
    private String sancion_l3;
    private String sancion_l4;
    private String sancion_l5;
    private String sancion_l6;
    private String sancion_l7;
    private String sancion_l8;
    private String sancion_l9;
    private String sancion_l10;

    private String nif_l1;
    private String nif_l2;
    private String nif_l3;
    private String nif_l4;
    private String nif_l5;
    private String nif_l6;
    private String nif_l7;
    private String nif_l8;
    private String nif_l9;
    private String nif_l10;
    private String nif_l11;
    private String nif_l12;
    private String nif_l13;
    private String nif_l14;
    private String nif_l15;
    private String nif_l16;
    private String nif_l17;

    private String capi_l1;
    private String capi_l2;
    private String capi_l3;
    private String capi_l4;
    private String capi_l5;
    private String capi_l6;
    private String capi_l7;
    private String capi_l8;
    private String capi_l9;
    private String capi_l10;

    private String pin_l1;
    private String pin_l2;
    private String pin_l3;
    private String pin_l4;
    private String pin_l5;
    private String pin_l6;
    private String pin_l7;
    private String pin_l8;
    private String pin_l9;
    private String pin_l10;
    private String pin_l11;

    //Equipo visitante
    private String codlic_v1;
    private String codlic_v2;
    private String codlic_v3;
    private String codlic_v4;
    private String codlic_v5;
    private String codlic_v6;
    private String codlic_v7;
    private String codlic_v8;
    private String codlic_v9;
    private String codlic_v10;
    private String codlic_v11;
    private String codlic_v12;
    private String codlic_v13;
    private String codlic_v14;
    private String codlic_v15;
    private String codlic_v16;
    private String codlic_v17;

    private String cat_v1;
    private String cat_v2;
    private String cat_v3;
    private String cat_v4;
    private String cat_v5;
    private String cat_v6;
    private String cat_v7;
    private String cat_v8;
    private String cat_v9;
    private String cat_v10;
    private String cat_v11;
    private String cat_v12;
    private String cat_v13;
    private String cat_v14;
    private String cat_v15;
    private String cat_v16;
    private String cat_v17;

    private String nom_cognoms_v1;
    private String nom_cognoms_v2;
    private String nom_cognoms_v3;
    private String nom_cognoms_v4;
    private String nom_cognoms_v5;
    private String nom_cognoms_v6;
    private String nom_cognoms_v7;
    private String nom_cognoms_v8;
    private String nom_cognoms_v9;
    private String nom_cognoms_v10;
    private String nom_cognoms_v11;
    private String nom_cognoms_v12;
    private String nom_cognoms_v13;
    private String nom_cognoms_v14;
    private String nom_cognoms_v15;
    private String nom_cognoms_v16;
    private String nom_cognoms_v17;

    private long num_v1;
    private long num_v2;
    private long num_v3;
    private long num_v4;
    private long num_v5;
    private long num_v6;
    private long num_v7;
    private long num_v8;
    private long num_v9;
    private long num_v10;
    private long num_v11;
    private long num_v12;
    private long num_v13;
    private long num_v14;
    private long num_v15;
    private long num_v16;
    private long num_v17;

    private String sancion_v1;
    private String sancion_v2;
    private String sancion_v3;
    private String sancion_v4;
    private String sancion_v5;
    private String sancion_v6;
    private String sancion_v7;
    private String sancion_v8;
    private String sancion_v9;
    private String sancion_v10;

    private String nif_v1;
    private String nif_v2;
    private String nif_v3;
    private String nif_v4;
    private String nif_v5;
    private String nif_v6;
    private String nif_v7;
    private String nif_v8;
    private String nif_v9;
    private String nif_v10;
    private String nif_v11;
    private String nif_v12;
    private String nif_v13;
    private String nif_v14;
    private String nif_v15;
    private String nif_v16;
    private String nif_v17;

    private String capi_v1;
    private String capi_v2;
    private String capi_v3;
    private String capi_v4;
    private String capi_v5;
    private String capi_v6;
    private String capi_v7;
    private String capi_v8;
    private String capi_v9;
    private String capi_v10;

    private String pin_v1;
    private String pin_v2;
    private String pin_v3;
    private String pin_v4;
    private String pin_v5;
    private String pin_v6;
    private String pin_v7;
    private String pin_v8;
    private String pin_v9;
    private String pin_v10;
    private String pin_v11;

    //Firmas prepartido
    private String firma1_dp;
    private String firma1_cl;
    private String firma1_el;
    private String firma1_cv;
    private String firma1_ev;

    private String firma1_dp_actualizar;
    private String firma1_cl_actualizar;
    private String firma1_el_actualizar;
    private String firma1_cv_actualizar;
    private String firma1_ev_actualizar;

    //Firmas
    private String firma2_cl;
    private String firma2_ar;
    private String firma2_ar2;
    private String firma2_cv;

    private String firma2_cl_actualizar;
    private String firma2_ar_actualizar;
    private String firma2_ar2_actualizar;
    private String firma2_cv_actualizar;

    //Pin
    private String pin_dp;
    private String pin_cl;
    private String pin_el;
    private String pin_cv;
    private String pin_ev;

    //Eventos
    private ArrayList<String> evento_id;
    private ArrayList<String> evento_tipo;
    private ArrayList<String> evento_parte;
    private ArrayList<String> evento_crono;
    private ArrayList<String> evento_equipo;
    private ArrayList<String> evento_dorsal;
    private ArrayList<String> evento_atributo;
    private ArrayList<String> evento_atributop;

    //Nuevo evento
    private String neventocrono;
    private String neventoparte;
    private String neventoequipo;
    private String neventodorsal;
    private String neventotipo;
    private String neventoatribut;

    //Eventos del partido
    private String totalfaltasl;
    private String totalfaltasv;
    private String totalgolesl;
    private String totalgolesv;
    private String saqueinicial;
    private String horainicio;
    private String tiempomuertol1;
    private String tiempomuertol2;
    private String tiempomuertov1;
    private String tiempomuertov2;

    private ArrayList<String> golespl;
    private ArrayList<String> golesml;
    private ArrayList<String> golesdl;
    private ArrayList<String> golesel;

    private ArrayList<String> golespv;
    private ArrayList<String> golesmv;
    private ArrayList<String> golesdv;
    private ArrayList<String> golesev;

    private String cgolespl;
    private String cgolesml;
    private String cgolesdl;
    private String golesequipo;
    private String cgolespv;
    private String cgolesmv;
    private String cgolesdv;
    //private String golesequipo;

    private ArrayList<String> faltasap;
    private ArrayList<String> faltasam;
    private ArrayList<String> faltasad;
    private ArrayList<String> faltasar;
    private ArrayList<String> faltasae;

    private String cfaltasap;
    private String cfaltasam;
    private String cfaltasad;
    private String cfaltasar;
    private String faltasaequipo;

    private ArrayList<String> faltasrp;
    private ArrayList<String> faltasrm;
    private ArrayList<String> faltasrd;
    private ArrayList<String> faltasrr;
    private ArrayList<String> faltasre;

    private String cfaltasrp;
    private String cfaltasrm;
    private String cfaltasrd;
    private String cfaltasrr;
    private String faltasrequipo;

//    public String getAlerta() {
//        return alerta;
//    }
//
//    public void setAlerta(String alerta) {
//        this.alerta = alerta;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getDelevento() {
        return delevento;
    }

    public void setDelevento(String delevento) {
        this.delevento = delevento;
    }

    public long getCodpartit() {
        return codpartit;
    }

    public void setCodpartit(long codpartit) {
        this.codpartit = codpartit;
    }

    public String getCodtemp() {
        return codtemp;
    }

    public void setCodtemp(String codtemp) {
        this.codtemp = codtemp;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getDatajoc() {
        return datajoc;
    }

    public void setDatajoc(String datajoc) {
        this.datajoc = datajoc;
    }

    public String getHorajoc() {
        return horajoc;
    }

    public void setHorajoc(String horajoc) {
        this.horajoc = horajoc;
    }

    public Long getJornada() {
        return jornada;
    }

    public void setJornada(Long jornada) {
        this.jornada = jornada;
    }

    public String getCompeticio() {
        return competicio;
    }

    public void setCompeticio(String competicio) {
        this.competicio = competicio;
    }

    public String getNomcompeticio() {
        return nomcompeticio;
    }

    public void setNomcompeticio(String nomcompeticio) {
        this.nomcompeticio = nomcompeticio;
    }

    public Long getCodequiplocal() {
        return codequiplocal;
    }

    public void setCodequiplocal(Long codequiplocal) {
        this.codequiplocal = codequiplocal;
    }

    public String getNomequiplocal() {
        return nomequiplocal;
    }

    public void setNomequiplocal(String nomequiplocal) {
        this.nomequiplocal = nomequiplocal;
    }

    public Long getCodequipvisit() {
        return codequipvisit;
    }

    public void setCodequipvisit(Long codequipvisit) {
        this.codequipvisit = codequipvisit;
    }

    public String getNomequipvisit() {
        return nomequipvisit;
    }

    public void setNomequipvisit(String nomequipvisit) {
        this.nomequipvisit = nomequipvisit;
    }

    public String getPoblaciojoc() {
        return poblaciojoc;
    }

    public void setPoblaciojoc(String poblaciojoc) {
        this.poblaciojoc = poblaciojoc;
    }

    public String getCodipostaljoc() {
        return codipostaljoc;
    }

    public void setCodipostaljoc(String codipostaljoc) {
        this.codipostaljoc = codipostaljoc;
    }

    public String getAdressajoc() {
        return adressajoc;
    }

    public void setAdressajoc(String adressajoc) {
        this.adressajoc = adressajoc;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        if (observacions.length() < 399) {
            this.observacions = observacions;
        }

    }

    public String getFinalitzat() {
        return finalitzat;
    }

    public void setFinalitzat(String finalitzat) {
        this.finalitzat = finalitzat;
    }

    public String getModalitat() {
        return modalitat;
    }

    public void setModalitat(String modalitat) {
        this.modalitat = modalitat;
    }

    public String getCodlic_dp() {
        return codlic_dp;
    }

    public void setCodlic_dp(String codlic_dp) {
        this.codlic_dp = codlic_dp;
    }

    public String getNom_cognoms_dp() {
        return nom_cognoms_dp;
    }

    public void setNom_cognoms_dp(String nom_cognoms_dp) {
        this.nom_cognoms_dp = nom_cognoms_dp;
    }

    public String getNif_dp() {
        return nif_dp;
    }

    public void setNif_dp(String nif_dp) {
        this.nif_dp = nif_dp;
    }

    public String getCodlic_cr() {
        return codlic_cr;
    }

    public void setCodlic_cr(String codlic_cr) {
        this.codlic_cr = codlic_cr;
    }

    public String getNom_cognoms_cr() {
        return nom_cognoms_cr;
    }

    public void setNom_cognoms_cr(String nom_cognoms_cr) {
        this.nom_cognoms_cr = nom_cognoms_cr;
    }

    public String getNif_cr() {
        return nif_cr;
    }

    public void setNif_cr(String nif_cr) {
        this.nif_cr = nif_cr;
    }

    public String getCodlic_ar1() {
        return codlic_ar1;
    }

    public void setCodlic_ar1(String codlic_ar1) {
        this.codlic_ar1 = codlic_ar1;
    }

    public String getNom_cognoms_ar1() {
        return nom_cognoms_ar1;
    }

    public void setNom_cognoms_ar1(String nom_cognoms_ar1) {
        this.nom_cognoms_ar1 = nom_cognoms_ar1;
    }

    public String getNif_ar1() {
        return nif_ar1;
    }

    public void setNif_ar1(String nif_ar1) {
        this.nif_ar1 = nif_ar1;
    }

    public String getPin_ar1() {
        return pin_ar1;
    }

    public void setPin_ar1(String pin_ar1) {
        this.pin_ar1 = pin_ar1;
    }

    public String getCat_ar1() {
        return cat_ar1;
    }

    public void setCat_ar1(String cat_ar1) {
        this.cat_ar1 = cat_ar1;
    }

    public String getEmail_ar1() {
        return email_ar1;
    }

    public void setEmail_ar1(String email_ar1) {
        this.email_ar1 = email_ar1;
    }

    public String getTerritorial_ar1() {
        return territorial_ar1;
    }

    public void setTerritorial_ar1(String territorial_ar1) {
        this.territorial_ar1 = territorial_ar1;
    }

    public String getTelefono_ar1() {
        return telefono_ar1;
    }

    public void setTelefono_ar1(String telefono_ar1) {
        this.telefono_ar1 = telefono_ar1;
    }

    public String getCodlic_ar2() {
        return codlic_ar2;
    }

    public void setCodlic_ar2(String codlic_ar2) {
        this.codlic_ar2 = codlic_ar2;
    }

    public String getNom_cognoms_ar2() {
        return nom_cognoms_ar2;
    }

    public void setNom_cognoms_ar2(String nom_cognoms_ar2) {
        this.nom_cognoms_ar2 = nom_cognoms_ar2;
    }

    public String getNif_ar2() {
        return nif_ar2;
    }

    public void setNif_ar2(String nif_ar2) {
        this.nif_ar2 = nif_ar2;
    }

    public String getTerritorial_ar2() {
        return territorial_ar2;
    }

    public void setTerritorial_ar2(String territorial_ar2) {
        this.territorial_ar2 = territorial_ar2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<DatosActa> getDatosactalocal() {
        return datosactalocal;
    }

    public void setDatosactalocal(ArrayList<DatosActa> datosactalocal) {
        this.datosactalocal = datosactalocal;
    }

    public ArrayList<DatosActa> getDatosactavisit() {
        return datosactavisit;
    }

    public void setDatosactavisit(ArrayList<DatosActa> datosactavisit) {
        this.datosactavisit = datosactavisit;
    }

    public ArrayList<DatosActa> getDatosactaTlocal() {
        return datosactaTlocal;
    }

    public void setDatosactaTlocal(ArrayList<DatosActa> datosactaTlocal) {
        this.datosactaTlocal = datosactaTlocal;
    }

    public ArrayList<DatosActa> getDatosactaTvisit() {
        return datosactaTvisit;
    }

    public void setDatosactaTvisit(ArrayList<DatosActa> datosactaTvisit) {
        this.datosactaTvisit = datosactaTvisit;
    }

    public ArrayList<DatosActa> getDatosactaTfede() {
        return datosactaTfede;
    }

    public void setDatosactaTfede(ArrayList<DatosActa> datosactaTfede) {
        this.datosactaTfede = datosactaTfede;
    }

    public ArrayList<DatosActa> getDatosactaDlocal() {
        return datosactaDlocal;
    }

    public void setDatosactaDlocal(ArrayList<DatosActa> datosactaDlocal) {
        this.datosactaDlocal = datosactaDlocal;
    }

    public ArrayList<DatosActa> getDatosactaDvisit() {
        return datosactaDvisit;
    }

    public void setDatosactaDvisit(ArrayList<DatosActa> datosactaDvisit) {
        this.datosactaDvisit = datosactaDvisit;
    }

    public ArrayList<DatosActa> getDatosactaDfede() {
        return datosactaDfede;
    }

    public void setDatosactaDfede(ArrayList<DatosActa> datosactaDfede) {
        this.datosactaDfede = datosactaDfede;
    }

    public ArrayList<DatosActa> getDatosactaAlocal() {
        return datosactaAlocal;
    }

    public void setDatosactaAlocal(ArrayList<DatosActa> datosactaAlocal) {
        this.datosactaAlocal = datosactaAlocal;
    }

    public ArrayList<DatosActa> getDatosactaAvisit() {
        return datosactaAvisit;
    }

    public void setDatosactaAvisit(ArrayList<DatosActa> datosactaAvisit) {
        this.datosactaAvisit = datosactaAvisit;
    }

    public ArrayList<DatosActa> getDatosactaArbitro() {
        return datosactaArbitro;
    }

    public void setDatosactaArbitro(ArrayList<DatosActa> datosactaArbitro) {
        this.datosactaArbitro = datosactaArbitro;
    }

    public String getCodlic_l1() {
        return codlic_l1;
    }

    public void setCodlic_l1(String codlic_l1) {
        this.codlic_l1 = codlic_l1;
    }

    public String getCodlic_l2() {
        return codlic_l2;
    }

    public void setCodlic_l2(String codlic_l2) {
        this.codlic_l2 = codlic_l2;
    }

    public String getCodlic_l3() {
        return codlic_l3;
    }

    public void setCodlic_l3(String codlic_l3) {
        this.codlic_l3 = codlic_l3;
    }

    public String getCodlic_l4() {
        return codlic_l4;
    }

    public void setCodlic_l4(String codlic_l4) {
        this.codlic_l4 = codlic_l4;
    }

    public String getCodlic_l5() {
        return codlic_l5;
    }

    public void setCodlic_l5(String codlic_l5) {
        this.codlic_l5 = codlic_l5;
    }

    public String getCodlic_l6() {
        return codlic_l6;
    }

    public void setCodlic_l6(String codlic_l6) {
        this.codlic_l6 = codlic_l6;
    }

    public String getCodlic_l7() {
        return codlic_l7;
    }

    public void setCodlic_l7(String codlic_l7) {
        this.codlic_l7 = codlic_l7;
    }

    public String getCodlic_l8() {
        return codlic_l8;
    }

    public void setCodlic_l8(String codlic_l8) {
        this.codlic_l8 = codlic_l8;
    }

    public String getCodlic_l9() {
        return codlic_l9;
    }

    public void setCodlic_l9(String codlic_l9) {
        this.codlic_l9 = codlic_l9;
    }

    public String getCodlic_l10() {
        return codlic_l10;
    }

    public void setCodlic_l10(String codlic_l10) {
        this.codlic_l10 = codlic_l10;
    }

    public String getCodlic_l11() {
        return codlic_l11;
    }

    public void setCodlic_l11(String codlic_l11) {
        this.codlic_l11 = codlic_l11;
    }

    public String getCodlic_l12() {
        return codlic_l12;
    }

    public void setCodlic_l12(String codlic_l12) {
        this.codlic_l12 = codlic_l12;
    }

    public String getCodlic_l13() {
        return codlic_l13;
    }

    public void setCodlic_l13(String codlic_l13) {
        this.codlic_l13 = codlic_l13;
    }

    public String getCodlic_l14() {
        return codlic_l14;
    }

    public void setCodlic_l14(String codlic_l14) {
        this.codlic_l14 = codlic_l14;
    }

    public String getCodlic_l15() {
        return codlic_l15;
    }

    public void setCodlic_l15(String codlic_l15) {
        this.codlic_l15 = codlic_l15;
    }

    public String getCodlic_l16() {
        return codlic_l16;
    }

    public void setCodlic_l16(String codlic_l16) {
        this.codlic_l16 = codlic_l16;
    }

    public String getCodlic_l17() {
        return codlic_l17;
    }

    public void setCodlic_l17(String codlic_l17) {
        this.codlic_l17 = codlic_l17;
    }

    public String getCat_l1() {
        return cat_l1;
    }

    public void setCat_l1(String cat_l1) {
        this.cat_l1 = cat_l1;
    }

    public String getCat_l2() {
        return cat_l2;
    }

    public void setCat_l2(String cat_l2) {
        this.cat_l2 = cat_l2;
    }

    public String getCat_l3() {
        return cat_l3;
    }

    public void setCat_l3(String cat_l3) {
        this.cat_l3 = cat_l3;
    }

    public String getCat_l4() {
        return cat_l4;
    }

    public void setCat_l4(String cat_l4) {
        this.cat_l4 = cat_l4;
    }

    public String getCat_l5() {
        return cat_l5;
    }

    public void setCat_l5(String cat_l5) {
        this.cat_l5 = cat_l5;
    }

    public String getCat_l6() {
        return cat_l6;
    }

    public void setCat_l6(String cat_l6) {
        this.cat_l6 = cat_l6;
    }

    public String getCat_l7() {
        return cat_l7;
    }

    public void setCat_l7(String cat_l7) {
        this.cat_l7 = cat_l7;
    }

    public String getCat_l8() {
        return cat_l8;
    }

    public void setCat_l8(String cat_l8) {
        this.cat_l8 = cat_l8;
    }

    public String getCat_l9() {
        return cat_l9;
    }

    public void setCat_l9(String cat_l9) {
        this.cat_l9 = cat_l9;
    }

    public String getCat_l10() {
        return cat_l10;
    }

    public void setCat_l10(String cat_l10) {
        this.cat_l10 = cat_l10;
    }

    public String getCat_l11() {
        return cat_l11;
    }

    public void setCat_l11(String cat_l11) {
        this.cat_l11 = cat_l11;
    }

    public String getCat_l12() {
        return cat_l12;
    }

    public void setCat_l12(String cat_l12) {
        this.cat_l12 = cat_l12;
    }

    public String getCat_l13() {
        return cat_l13;
    }

    public void setCat_l13(String cat_l13) {
        this.cat_l13 = cat_l13;
    }

    public String getCat_l14() {
        return cat_l14;
    }

    public void setCat_l14(String cat_l14) {
        this.cat_l14 = cat_l14;
    }

    public String getCat_l15() {
        return cat_l15;
    }

    public void setCat_l15(String cat_l15) {
        this.cat_l15 = cat_l15;
    }

    public String getCat_l16() {
        return cat_l16;
    }

    public void setCat_l16(String cat_l16) {
        this.cat_l16 = cat_l16;
    }

    public String getCat_l17() {
        return cat_l17;
    }

    public void setCat_l17(String cat_l17) {
        this.cat_l17 = cat_l17;
    }

    public String getNom_cognoms_l1() {
        return nom_cognoms_l1;
    }

    public void setNom_cognoms_l1(String nom_cognoms_l1) {
        this.nom_cognoms_l1 = nom_cognoms_l1;
    }

    public String getNom_cognoms_l2() {
        return nom_cognoms_l2;
    }

    public void setNom_cognoms_l2(String nom_cognoms_l2) {
        this.nom_cognoms_l2 = nom_cognoms_l2;
    }

    public String getNom_cognoms_l3() {
        return nom_cognoms_l3;
    }

    public void setNom_cognoms_l3(String nom_cognoms_l3) {
        this.nom_cognoms_l3 = nom_cognoms_l3;
    }

    public String getNom_cognoms_l4() {
        return nom_cognoms_l4;
    }

    public void setNom_cognoms_l4(String nom_cognoms_l4) {
        this.nom_cognoms_l4 = nom_cognoms_l4;
    }

    public String getNom_cognoms_l5() {
        return nom_cognoms_l5;
    }

    public void setNom_cognoms_l5(String nom_cognoms_l5) {
        this.nom_cognoms_l5 = nom_cognoms_l5;
    }

    public String getNom_cognoms_l6() {
        return nom_cognoms_l6;
    }

    public void setNom_cognoms_l6(String nom_cognoms_l6) {
        this.nom_cognoms_l6 = nom_cognoms_l6;
    }

    public String getNom_cognoms_l7() {
        return nom_cognoms_l7;
    }

    public void setNom_cognoms_l7(String nom_cognoms_l7) {
        this.nom_cognoms_l7 = nom_cognoms_l7;
    }

    public String getNom_cognoms_l8() {
        return nom_cognoms_l8;
    }

    public void setNom_cognoms_l8(String nom_cognoms_l8) {
        this.nom_cognoms_l8 = nom_cognoms_l8;
    }

    public String getNom_cognoms_l9() {
        return nom_cognoms_l9;
    }

    public void setNom_cognoms_l9(String nom_cognoms_l9) {
        this.nom_cognoms_l9 = nom_cognoms_l9;
    }

    public String getNom_cognoms_l10() {
        return nom_cognoms_l10;
    }

    public void setNom_cognoms_l10(String nom_cognoms_l10) {
        this.nom_cognoms_l10 = nom_cognoms_l10;
    }

    public String getNom_cognoms_l11() {
        return nom_cognoms_l11;
    }

    public void setNom_cognoms_l11(String nom_cognoms_l11) {
        this.nom_cognoms_l11 = nom_cognoms_l11;
    }

    public String getNom_cognoms_l12() {
        return nom_cognoms_l12;
    }

    public void setNom_cognoms_l12(String nom_cognoms_l12) {
        this.nom_cognoms_l12 = nom_cognoms_l12;
    }

    public String getNom_cognoms_l13() {
        return nom_cognoms_l13;
    }

    public void setNom_cognoms_l13(String nom_cognoms_l13) {
        this.nom_cognoms_l13 = nom_cognoms_l13;
    }

    public String getNom_cognoms_l14() {
        return nom_cognoms_l14;
    }

    public void setNom_cognoms_l14(String nom_cognoms_l14) {
        this.nom_cognoms_l14 = nom_cognoms_l14;
    }

    public String getNom_cognoms_l15() {
        return nom_cognoms_l15;
    }

    public void setNom_cognoms_l15(String nom_cognoms_l15) {
        this.nom_cognoms_l15 = nom_cognoms_l15;
    }

    public String getNom_cognoms_l16() {
        return nom_cognoms_l16;
    }

    public void setNom_cognoms_l16(String nom_cognoms_l16) {
        this.nom_cognoms_l16 = nom_cognoms_l16;
    }

    public String getNom_cognoms_l17() {
        return nom_cognoms_l17;
    }

    public void setNom_cognoms_l17(String nom_cognoms_l17) {
        this.nom_cognoms_l17 = nom_cognoms_l17;
    }

    public long getNum_l1() {
        return num_l1;
    }

    public void setNum_l1(long num_l1) {
        this.num_l1 = num_l1;
    }

    public long getNum_l2() {
        return num_l2;
    }

    public void setNum_l2(long num_l2) {
        this.num_l2 = num_l2;
    }

    public long getNum_l3() {
        return num_l3;
    }

    public void setNum_l3(long num_l3) {
        this.num_l3 = num_l3;
    }

    public long getNum_l4() {
        return num_l4;
    }

    public void setNum_l4(long num_l4) {
        this.num_l4 = num_l4;
    }

    public long getNum_l5() {
        return num_l5;
    }

    public void setNum_l5(long num_l5) {
        this.num_l5 = num_l5;
    }

    public long getNum_l6() {
        return num_l6;
    }

    public void setNum_l6(long num_l6) {
        this.num_l6 = num_l6;
    }

    public long getNum_l7() {
        return num_l7;
    }

    public void setNum_l7(long num_l7) {
        this.num_l7 = num_l7;
    }

    public long getNum_l8() {
        return num_l8;
    }

    public void setNum_l8(long num_l8) {
        this.num_l8 = num_l8;
    }

    public long getNum_l9() {
        return num_l9;
    }

    public void setNum_l9(long num_l9) {
        this.num_l9 = num_l9;
    }

    public long getNum_l10() {
        return num_l10;
    }

    public void setNum_l10(long num_l10) {
        this.num_l10 = num_l10;
    }

    public String getNif_l1() {
        return nif_l1;
    }

    public void setNif_l1(String nif_l1) {
        this.nif_l1 = nif_l1;
    }

    public String getNif_l2() {
        return nif_l2;
    }

    public void setNif_l2(String nif_l2) {
        this.nif_l2 = nif_l2;
    }

    public String getNif_l3() {
        return nif_l3;
    }

    public void setNif_l3(String nif_l3) {
        this.nif_l3 = nif_l3;
    }

    public String getNif_l4() {
        return nif_l4;
    }

    public void setNif_l4(String nif_l4) {
        this.nif_l4 = nif_l4;
    }

    public String getNif_l5() {
        return nif_l5;
    }

    public void setNif_l5(String nif_l5) {
        this.nif_l5 = nif_l5;
    }

    public String getNif_l6() {
        return nif_l6;
    }

    public void setNif_l6(String nif_l6) {
        this.nif_l6 = nif_l6;
    }

    public String getNif_l7() {
        return nif_l7;
    }

    public void setNif_l7(String nif_l7) {
        this.nif_l7 = nif_l7;
    }

    public String getNif_l8() {
        return nif_l8;
    }

    public void setNif_l8(String nif_l8) {
        this.nif_l8 = nif_l8;
    }

    public String getNif_l9() {
        return nif_l9;
    }

    public void setNif_l9(String nif_l9) {
        this.nif_l9 = nif_l9;
    }

    public String getNif_l10() {
        return nif_l10;
    }

    public void setNif_l10(String nif_l10) {
        this.nif_l10 = nif_l10;
    }

    public String getNif_l11() {
        return nif_l11;
    }

    public void setNif_l11(String nif_l11) {
        this.nif_l11 = nif_l11;
    }

    public String getNif_l12() {
        return nif_l12;
    }

    public void setNif_l12(String nif_l12) {
        this.nif_l12 = nif_l12;
    }

    public String getNif_l13() {
        return nif_l13;
    }

    public void setNif_l13(String nif_l13) {
        this.nif_l13 = nif_l13;
    }

    public String getNif_l14() {
        return nif_l14;
    }

    public void setNif_l14(String nif_l14) {
        this.nif_l14 = nif_l14;
    }

    public String getNif_l15() {
        return nif_l15;
    }

    public void setNif_l15(String nif_l15) {
        this.nif_l15 = nif_l15;
    }

    public String getNif_l16() {
        return nif_l16;
    }

    public void setNif_l16(String nif_l16) {
        this.nif_l16 = nif_l16;
    }

    public String getNif_l17() {
        return nif_l17;
    }

    public void setNif_l17(String nif_l17) {
        this.nif_l17 = nif_l17;
    }

    public String getCapi_l1() {
        return capi_l1;
    }

    public void setCapi_l1(String capi_l1) {
        this.capi_l1 = capi_l1;
    }

    public String getCapi_l2() {
        return capi_l2;
    }

    public void setCapi_l2(String capi_l2) {
        this.capi_l2 = capi_l2;
    }

    public String getCapi_l3() {
        return capi_l3;
    }

    public void setCapi_l3(String capi_l3) {
        this.capi_l3 = capi_l3;
    }

    public String getCapi_l4() {
        return capi_l4;
    }

    public void setCapi_l4(String capi_l4) {
        this.capi_l4 = capi_l4;
    }

    public String getCapi_l5() {
        return capi_l5;
    }

    public void setCapi_l5(String capi_l5) {
        this.capi_l5 = capi_l5;
    }

    public String getCapi_l6() {
        return capi_l6;
    }

    public void setCapi_l6(String capi_l6) {
        this.capi_l6 = capi_l6;
    }

    public String getCapi_l7() {
        return capi_l7;
    }

    public void setCapi_l7(String capi_l7) {
        this.capi_l7 = capi_l7;
    }

    public String getCapi_l8() {
        return capi_l8;
    }

    public void setCapi_l8(String capi_l8) {
        this.capi_l8 = capi_l8;
    }

    public String getCapi_l9() {
        return capi_l9;
    }

    public void setCapi_l9(String capi_l9) {
        this.capi_l9 = capi_l9;
    }

    public String getCapi_l10() {
        return capi_l10;
    }

    public void setCapi_l10(String capi_l10) {
        this.capi_l10 = capi_l10;
    }

    public String getPin_l1() {
        return pin_l1;
    }

    public void setPin_l1(String pin_l1) {
        this.pin_l1 = pin_l1;
    }

    public String getPin_l2() {
        return pin_l2;
    }

    public void setPin_l2(String pin_l2) {
        this.pin_l2 = pin_l2;
    }

    public String getPin_l3() {
        return pin_l3;
    }

    public void setPin_l3(String pin_l3) {
        this.pin_l3 = pin_l3;
    }

    public String getPin_l4() {
        return pin_l4;
    }

    public void setPin_l4(String pin_l4) {
        this.pin_l4 = pin_l4;
    }

    public String getPin_l5() {
        return pin_l5;
    }

    public void setPin_l5(String pin_l5) {
        this.pin_l5 = pin_l5;
    }

    public String getPin_l6() {
        return pin_l6;
    }

    public void setPin_l6(String pin_l6) {
        this.pin_l6 = pin_l6;
    }

    public String getPin_l7() {
        return pin_l7;
    }

    public void setPin_l7(String pin_l7) {
        this.pin_l7 = pin_l7;
    }

    public String getPin_l8() {
        return pin_l8;
    }

    public void setPin_l8(String pin_l8) {
        this.pin_l8 = pin_l8;
    }

    public String getPin_l9() {
        return pin_l9;
    }

    public void setPin_l9(String pin_l9) {
        this.pin_l9 = pin_l9;
    }

    public String getPin_l10() {
        return pin_l10;
    }

    public void setPin_l10(String pin_l10) {
        this.pin_l10 = pin_l10;
    }

    public String getPin_l11() {
        return pin_l11;
    }

    public void setPin_l11(String pin_l11) {
        this.pin_l11 = pin_l11;
    }

    public String getCodlic_v1() {
        return codlic_v1;
    }

    public void setCodlic_v1(String codlic_v1) {
        this.codlic_v1 = codlic_v1;
    }

    public String getCodlic_v2() {
        return codlic_v2;
    }

    public void setCodlic_v2(String codlic_v2) {
        this.codlic_v2 = codlic_v2;
    }

    public String getCodlic_v3() {
        return codlic_v3;
    }

    public void setCodlic_v3(String codlic_v3) {
        this.codlic_v3 = codlic_v3;
    }

    public String getCodlic_v4() {
        return codlic_v4;
    }

    public void setCodlic_v4(String codlic_v4) {
        this.codlic_v4 = codlic_v4;
    }

    public String getCodlic_v5() {
        return codlic_v5;
    }

    public void setCodlic_v5(String codlic_v5) {
        this.codlic_v5 = codlic_v5;
    }

    public String getCodlic_v6() {
        return codlic_v6;
    }

    public void setCodlic_v6(String codlic_v6) {
        this.codlic_v6 = codlic_v6;
    }

    public String getCodlic_v7() {
        return codlic_v7;
    }

    public void setCodlic_v7(String codlic_v7) {
        this.codlic_v7 = codlic_v7;
    }

    public String getCodlic_v8() {
        return codlic_v8;
    }

    public void setCodlic_v8(String codlic_v8) {
        this.codlic_v8 = codlic_v8;
    }

    public String getCodlic_v9() {
        return codlic_v9;
    }

    public void setCodlic_v9(String codlic_v9) {
        this.codlic_v9 = codlic_v9;
    }

    public String getCodlic_v10() {
        return codlic_v10;
    }

    public void setCodlic_v10(String codlic_v10) {
        this.codlic_v10 = codlic_v10;
    }

    public String getCodlic_v11() {
        return codlic_v11;
    }

    public void setCodlic_v11(String codlic_v11) {
        this.codlic_v11 = codlic_v11;
    }

    public String getCodlic_v12() {
        return codlic_v12;
    }

    public void setCodlic_v12(String codlic_v12) {
        this.codlic_v12 = codlic_v12;
    }

    public String getCodlic_v13() {
        return codlic_v13;
    }

    public void setCodlic_v13(String codlic_v13) {
        this.codlic_v13 = codlic_v13;
    }

    public String getCodlic_v14() {
        return codlic_v14;
    }

    public void setCodlic_v14(String codlic_v14) {
        this.codlic_v14 = codlic_v14;
    }

    public String getCodlic_v15() {
        return codlic_v15;
    }

    public void setCodlic_v15(String codlic_v15) {
        this.codlic_v15 = codlic_v15;
    }

    public String getCodlic_v16() {
        return codlic_v16;
    }

    public void setCodlic_v16(String codlic_v16) {
        this.codlic_v16 = codlic_v16;
    }

    public String getCodlic_v17() {
        return codlic_v17;
    }

    public void setCodlic_v17(String codlic_v17) {
        this.codlic_v17 = codlic_v17;
    }

    public String getCat_v1() {
        return cat_v1;
    }

    public void setCat_v1(String cat_v1) {
        this.cat_v1 = cat_v1;
    }

    public String getCat_v2() {
        return cat_v2;
    }

    public void setCat_v2(String cat_v2) {
        this.cat_v2 = cat_v2;
    }

    public String getCat_v3() {
        return cat_v3;
    }

    public void setCat_v3(String cat_v3) {
        this.cat_v3 = cat_v3;
    }

    public String getCat_v4() {
        return cat_v4;
    }

    public void setCat_v4(String cat_v4) {
        this.cat_v4 = cat_v4;
    }

    public String getCat_v5() {
        return cat_v5;
    }

    public void setCat_v5(String cat_v5) {
        this.cat_v5 = cat_v5;
    }

    public String getCat_v6() {
        return cat_v6;
    }

    public void setCat_v6(String cat_v6) {
        this.cat_v6 = cat_v6;
    }

    public String getCat_v7() {
        return cat_v7;
    }

    public void setCat_v7(String cat_v7) {
        this.cat_v7 = cat_v7;
    }

    public String getCat_v8() {
        return cat_v8;
    }

    public void setCat_v8(String cat_v8) {
        this.cat_v8 = cat_v8;
    }

    public String getCat_v9() {
        return cat_v9;
    }

    public void setCat_v9(String cat_v9) {
        this.cat_v9 = cat_v9;
    }

    public String getCat_v10() {
        return cat_v10;
    }

    public void setCat_v10(String cat_v10) {
        this.cat_v10 = cat_v10;
    }

    public String getCat_v11() {
        return cat_v11;
    }

    public void setCat_v11(String cat_v11) {
        this.cat_v11 = cat_v11;
    }

    public String getCat_v12() {
        return cat_v12;
    }

    public void setCat_v12(String cat_v12) {
        this.cat_v12 = cat_v12;
    }

    public String getCat_v13() {
        return cat_v13;
    }

    public void setCat_v13(String cat_v13) {
        this.cat_v13 = cat_v13;
    }

    public String getCat_v14() {
        return cat_v14;
    }

    public void setCat_v14(String cat_v14) {
        this.cat_v14 = cat_v14;
    }

    public String getCat_v15() {
        return cat_v15;
    }

    public void setCat_v15(String cat_v15) {
        this.cat_v15 = cat_v15;
    }

    public String getCat_v16() {
        return cat_v16;
    }

    public void setCat_v16(String cat_v16) {
        this.cat_v16 = cat_v16;
    }

    public String getCat_v17() {
        return cat_v17;
    }

    public void setCat_v17(String cat_v17) {
        this.cat_v17 = cat_v17;
    }

    public String getNom_cognoms_v1() {
        return nom_cognoms_v1;
    }

    public void setNom_cognoms_v1(String nom_cognoms_v1) {
        this.nom_cognoms_v1 = nom_cognoms_v1;
    }

    public String getNom_cognoms_v2() {
        return nom_cognoms_v2;
    }

    public void setNom_cognoms_v2(String nom_cognoms_v2) {
        this.nom_cognoms_v2 = nom_cognoms_v2;
    }

    public String getNom_cognoms_v3() {
        return nom_cognoms_v3;
    }

    public void setNom_cognoms_v3(String nom_cognoms_v3) {
        this.nom_cognoms_v3 = nom_cognoms_v3;
    }

    public String getNom_cognoms_v4() {
        return nom_cognoms_v4;
    }

    public void setNom_cognoms_v4(String nom_cognoms_v4) {
        this.nom_cognoms_v4 = nom_cognoms_v4;
    }

    public String getNom_cognoms_v5() {
        return nom_cognoms_v5;
    }

    public void setNom_cognoms_v5(String nom_cognoms_v5) {
        this.nom_cognoms_v5 = nom_cognoms_v5;
    }

    public String getNom_cognoms_v6() {
        return nom_cognoms_v6;
    }

    public void setNom_cognoms_v6(String nom_cognoms_v6) {
        this.nom_cognoms_v6 = nom_cognoms_v6;
    }

    public String getNom_cognoms_v7() {
        return nom_cognoms_v7;
    }

    public void setNom_cognoms_v7(String nom_cognoms_v7) {
        this.nom_cognoms_v7 = nom_cognoms_v7;
    }

    public String getNom_cognoms_v8() {
        return nom_cognoms_v8;
    }

    public void setNom_cognoms_v8(String nom_cognoms_v8) {
        this.nom_cognoms_v8 = nom_cognoms_v8;
    }

    public String getNom_cognoms_v9() {
        return nom_cognoms_v9;
    }

    public void setNom_cognoms_v9(String nom_cognoms_v9) {
        this.nom_cognoms_v9 = nom_cognoms_v9;
    }

    public String getNom_cognoms_v10() {
        return nom_cognoms_v10;
    }

    public void setNom_cognoms_v10(String nom_cognoms_v10) {
        this.nom_cognoms_v10 = nom_cognoms_v10;
    }

    public String getNom_cognoms_v11() {
        return nom_cognoms_v11;
    }

    public void setNom_cognoms_v11(String nom_cognoms_v11) {
        this.nom_cognoms_v11 = nom_cognoms_v11;
    }

    public String getNom_cognoms_v12() {
        return nom_cognoms_v12;
    }

    public void setNom_cognoms_v12(String nom_cognoms_v12) {
        this.nom_cognoms_v12 = nom_cognoms_v12;
    }

    public String getNom_cognoms_v13() {
        return nom_cognoms_v13;
    }

    public void setNom_cognoms_v13(String nom_cognoms_v13) {
        this.nom_cognoms_v13 = nom_cognoms_v13;
    }

    public String getNom_cognoms_v14() {
        return nom_cognoms_v14;
    }

    public void setNom_cognoms_v14(String nom_cognoms_v14) {
        this.nom_cognoms_v14 = nom_cognoms_v14;
    }

    public String getNom_cognoms_v15() {
        return nom_cognoms_v15;
    }

    public void setNom_cognoms_v15(String nom_cognoms_v15) {
        this.nom_cognoms_v15 = nom_cognoms_v15;
    }

    public String getNom_cognoms_v16() {
        return nom_cognoms_v16;
    }

    public void setNom_cognoms_v16(String nom_cognoms_v16) {
        this.nom_cognoms_v16 = nom_cognoms_v16;
    }

    public String getNom_cognoms_v17() {
        return nom_cognoms_v17;
    }

    public void setNom_cognoms_v17(String nom_cognoms_v17) {
        this.nom_cognoms_v17 = nom_cognoms_v17;
    }

    public long getNum_v1() {
        return num_v1;
    }

    public void setNum_v1(long num_v1) {
        this.num_v1 = num_v1;
    }

    public long getNum_v2() {
        return num_v2;
    }

    public void setNum_v2(long num_v2) {
        this.num_v2 = num_v2;
    }

    public long getNum_v3() {
        return num_v3;
    }

    public void setNum_v3(long num_v3) {
        this.num_v3 = num_v3;
    }

    public long getNum_v4() {
        return num_v4;
    }

    public void setNum_v4(long num_v4) {
        this.num_v4 = num_v4;
    }

    public long getNum_v5() {
        return num_v5;
    }

    public void setNum_v5(long num_v5) {
        this.num_v5 = num_v5;
    }

    public long getNum_v6() {
        return num_v6;
    }

    public void setNum_v6(long num_v6) {
        this.num_v6 = num_v6;
    }

    public long getNum_v7() {
        return num_v7;
    }

    public void setNum_v7(long num_v7) {
        this.num_v7 = num_v7;
    }

    public long getNum_v8() {
        return num_v8;
    }

    public void setNum_v8(long num_v8) {
        this.num_v8 = num_v8;
    }

    public long getNum_v9() {
        return num_v9;
    }

    public void setNum_v9(long num_v9) {
        this.num_v9 = num_v9;
    }

    public long getNum_v10() {
        return num_v10;
    }

    public void setNum_v10(long num_v10) {
        this.num_v10 = num_v10;
    }

    public String getNif_v1() {
        return nif_v1;
    }

    public void setNif_v1(String nif_v1) {
        this.nif_v1 = nif_v1;
    }

    public String getNif_v2() {
        return nif_v2;
    }

    public void setNif_v2(String nif_v2) {
        this.nif_v2 = nif_v2;
    }

    public String getNif_v3() {
        return nif_v3;
    }

    public void setNif_v3(String nif_v3) {
        this.nif_v3 = nif_v3;
    }

    public String getNif_v4() {
        return nif_v4;
    }

    public void setNif_v4(String nif_v4) {
        this.nif_v4 = nif_v4;
    }

    public String getNif_v5() {
        return nif_v5;
    }

    public void setNif_v5(String nif_v5) {
        this.nif_v5 = nif_v5;
    }

    public String getNif_v6() {
        return nif_v6;
    }

    public void setNif_v6(String nif_v6) {
        this.nif_v6 = nif_v6;
    }

    public String getNif_v7() {
        return nif_v7;
    }

    public void setNif_v7(String nif_v7) {
        this.nif_v7 = nif_v7;
    }

    public String getNif_v8() {
        return nif_v8;
    }

    public void setNif_v8(String nif_v8) {
        this.nif_v8 = nif_v8;
    }

    public String getNif_v9() {
        return nif_v9;
    }

    public void setNif_v9(String nif_v9) {
        this.nif_v9 = nif_v9;
    }

    public String getNif_v10() {
        return nif_v10;
    }

    public void setNif_v10(String nif_v10) {
        this.nif_v10 = nif_v10;
    }

    public String getNif_v11() {
        return nif_v11;
    }

    public void setNif_v11(String nif_v11) {
        this.nif_v11 = nif_v11;
    }

    public String getNif_v12() {
        return nif_v12;
    }

    public void setNif_v12(String nif_v12) {
        this.nif_v12 = nif_v12;
    }

    public String getNif_v13() {
        return nif_v13;
    }

    public void setNif_v13(String nif_v13) {
        this.nif_v13 = nif_v13;
    }

    public String getNif_v14() {
        return nif_v14;
    }

    public void setNif_v14(String nif_v14) {
        this.nif_v14 = nif_v14;
    }

    public String getNif_v15() {
        return nif_v15;
    }

    public void setNif_v15(String nif_v15) {
        this.nif_v15 = nif_v15;
    }

    public String getNif_v16() {
        return nif_v16;
    }

    public void setNif_v16(String nif_v16) {
        this.nif_v16 = nif_v16;
    }

    public String getNif_v17() {
        return nif_v17;
    }

    public void setNif_v17(String nif_v17) {
        this.nif_v17 = nif_v17;
    }

    public String getCapi_v1() {
        return capi_v1;
    }

    public void setCapi_v1(String capi_v1) {
        this.capi_v1 = capi_v1;
    }

    public String getCapi_v2() {
        return capi_v2;
    }

    public void setCapi_v2(String capi_v2) {
        this.capi_v2 = capi_v2;
    }

    public String getCapi_v3() {
        return capi_v3;
    }

    public void setCapi_v3(String capi_v3) {
        this.capi_v3 = capi_v3;
    }

    public String getCapi_v4() {
        return capi_v4;
    }

    public void setCapi_v4(String capi_v4) {
        this.capi_v4 = capi_v4;
    }

    public String getCapi_v5() {
        return capi_v5;
    }

    public void setCapi_v5(String capi_v5) {
        this.capi_v5 = capi_v5;
    }

    public String getCapi_v6() {
        return capi_v6;
    }

    public void setCapi_v6(String capi_v6) {
        this.capi_v6 = capi_v6;
    }

    public String getCapi_v7() {
        return capi_v7;
    }

    public void setCapi_v7(String capi_v7) {
        this.capi_v7 = capi_v7;
    }

    public String getCapi_v8() {
        return capi_v8;
    }

    public void setCapi_v8(String capi_v8) {
        this.capi_v8 = capi_v8;
    }

    public String getCapi_v9() {
        return capi_v9;
    }

    public void setCapi_v9(String capi_v9) {
        this.capi_v9 = capi_v9;
    }

    public String getCapi_v10() {
        return capi_v10;
    }

    public void setCapi_v10(String capi_v10) {
        this.capi_v10 = capi_v10;
    }

    public String getPin_v1() {
        return pin_v1;
    }

    public void setPin_v1(String pin_v1) {
        this.pin_v1 = pin_v1;
    }

    public String getPin_v2() {
        return pin_v2;
    }

    public void setPin_v2(String pin_v2) {
        this.pin_v2 = pin_v2;
    }

    public String getPin_v3() {
        return pin_v3;
    }

    public void setPin_v3(String pin_v3) {
        this.pin_v3 = pin_v3;
    }

    public String getPin_v4() {
        return pin_v4;
    }

    public void setPin_v4(String pin_v4) {
        this.pin_v4 = pin_v4;
    }

    public String getPin_v5() {
        return pin_v5;
    }

    public void setPin_v5(String pin_v5) {
        this.pin_v5 = pin_v5;
    }

    public String getPin_v6() {
        return pin_v6;
    }

    public void setPin_v6(String pin_v6) {
        this.pin_v6 = pin_v6;
    }

    public String getPin_v7() {
        return pin_v7;
    }

    public void setPin_v7(String pin_v7) {
        this.pin_v7 = pin_v7;
    }

    public String getPin_v8() {
        return pin_v8;
    }

    public void setPin_v8(String pin_v8) {
        this.pin_v8 = pin_v8;
    }

    public String getPin_v9() {
        return pin_v9;
    }

    public void setPin_v9(String pin_v9) {
        this.pin_v9 = pin_v9;
    }

    public String getPin_v10() {
        return pin_v10;
    }

    public void setPin_v10(String pin_v10) {
        this.pin_v10 = pin_v10;
    }

    public String getPin_v11() {
        return pin_v11;
    }

    public void setPin_v11(String pin_v11) {
        this.pin_v11 = pin_v11;
    }

    public String getFirma1_dp() {
        return firma1_dp;
    }

    public void setFirma1_dp(String firma1_dp) {
        this.firma1_dp = firma1_dp;
    }

    public String getFirma1_cl() {
        return firma1_cl;
    }

    public void setFirma1_cl(String firma1_cl) {
        this.firma1_cl = firma1_cl;
    }

    public String getFirma1_el() {
        return firma1_el;
    }

    public void setFirma1_el(String firma1_el) {
        this.firma1_el = firma1_el;
    }

    public String getFirma1_cv() {
        return firma1_cv;
    }

    public void setFirma1_cv(String firma1_cv) {
        this.firma1_cv = firma1_cv;
    }

    public String getFirma1_ev() {
        return firma1_ev;
    }

    public void setFirma1_ev(String firma1_ev) {
        this.firma1_ev = firma1_ev;
    }

    public String getFirma2_cl() {
        return firma2_cl;
    }

    public void setFirma2_cl(String firma2_cl) {
        this.firma2_cl = firma2_cl;
    }

    public String getFirma2_ar() {
        return firma2_ar;
    }

    public void setFirma2_ar(String firma2_ar) {
        this.firma2_ar = firma2_ar;
    }

    public String getFirma2_cv() {
        return firma2_cv;
    }

    public void setFirma2_cv(String firma2_cv) {
        this.firma2_cv = firma2_cv;
    }

    public String getPin_dp() {
        return pin_dp;
    }

    public void setPin_dp(String pin_dp) {
        this.pin_dp = pin_dp;
    }

    public String getPin_cl() {
        return pin_cl;
    }

    public void setPin_cl(String pin_cl) {
        this.pin_cl = pin_cl;
    }

    public String getPin_el() {
        return pin_el;
    }

    public void setPin_el(String pin_el) {
        this.pin_el = pin_el;
    }

    public String getPin_cv() {
        return pin_cv;
    }

    public void setPin_cv(String pin_cv) {
        this.pin_cv = pin_cv;
    }

    public String getPin_ev() {
        return pin_ev;
    }

    public void setPin_ev(String pin_ev) {
        this.pin_ev = pin_ev;
    }

    public ArrayList<String> getEvento_id() {
        return evento_id;
    }

    public void setEvento_id(ArrayList<String> evento_id) {
        this.evento_id = evento_id;
    }

    public ArrayList<String> getEvento_tipo() {
        return evento_tipo;
    }

    public void setEvento_tipo(ArrayList<String> evento_tipo) {
        this.evento_tipo = evento_tipo;
    }

    public ArrayList<String> getEvento_parte() {
        return evento_parte;
    }

    public void setEvento_parte(ArrayList<String> evento_parte) {
        this.evento_parte = evento_parte;
    }

    public ArrayList<String> getEvento_crono() {
        return evento_crono;
    }

    public void setEvento_crono(ArrayList<String> evento_crono) {
        this.evento_crono = evento_crono;
    }

    public ArrayList<String> getEvento_equipo() {
        return evento_equipo;
    }

    public void setEvento_equipo(ArrayList<String> evento_equipo) {
        this.evento_equipo = evento_equipo;
    }

    public ArrayList<String> getEvento_dorsal() {
        return evento_dorsal;
    }

    public void setEvento_dorsal(ArrayList<String> evento_dorsal) {
        this.evento_dorsal = evento_dorsal;
    }

    public ArrayList<String> getEvento_atributo() {
        return evento_atributo;
    }

    public void setEvento_atributo(ArrayList<String> evento_atributo) {
        this.evento_atributo = evento_atributo;
    }

    public ArrayList<String> getEvento_atributop() {
        return evento_atributop;
    }

    public void setEvento_atributop(ArrayList<String> evento_atributop) {
        this.evento_atributop = evento_atributop;
    }

    public String getNeventocrono() {
        return neventocrono;
    }

    public void setNeventocrono(String neventocrono) {
        this.neventocrono = neventocrono;
    }

    public String getNeventoparte() {
        return neventoparte;
    }

    public void setNeventoparte(String neventoparte) {
        this.neventoparte = neventoparte;
    }

    public String getNeventoequipo() {
        return neventoequipo;
    }

    public void setNeventoequipo(String neventoequipo) {
        this.neventoequipo = neventoequipo;
    }

    public String getNeventodorsal() {
        return neventodorsal;
    }

    public void setNeventodorsal(String neventodorsal) {
        this.neventodorsal = neventodorsal;
    }

    public String getNeventotipo() {
        return neventotipo;
    }

    public void setNeventotipo(String neventotipo) {
        this.neventotipo = neventotipo;
    }

    public String getNeventoatribut() {
        return neventoatribut;
    }

    public void setNeventoatribut(String neventoatribut) {
        this.neventoatribut = neventoatribut;
    }

    public Long getCodentitatlocal() {
        return codentitatlocal;
    }

    public void setCodentitatlocal(Long codentitatlocal) {
        this.codentitatlocal = codentitatlocal;
    }

    public String getCodcategorialocal() {
        return codcategorialocal;
    }

    public void setCodcategorialocal(String codcategorialocal) {
        this.codcategorialocal = codcategorialocal;
    }

    public Long getCodclasselocal() {
        return codclasselocal;
    }

    public void setCodclasselocal(Long codclasselocal) {
        this.codclasselocal = codclasselocal;
    }

    public String getClasselocal() {
        return classelocal;
    }

    public void setClasselocal(String classelocal) {
        this.classelocal = classelocal;
    }

    public Long getCodentitatvisit() {
        return codentitatvisit;
    }

    public void setCodentitatvisit(Long codentitatvisit) {
        this.codentitatvisit = codentitatvisit;
    }

    public String getCodcategoriavisit() {
        return codcategoriavisit;
    }

    public void setCodcategoriavisit(String codcategoriavisit) {
        this.codcategoriavisit = codcategoriavisit;
    }

    public Long getCodclassevisit() {
        return codclassevisit;
    }

    public void setCodclassevisit(Long codclassevisit) {
        this.codclassevisit = codclassevisit;
    }

    public String getMailequiplocal() {
        return mailequiplocal;
    }

    public void setMailequiplocal(String mailequiplocal) {
        this.mailequiplocal = mailequiplocal;
    }

    public String getMailequipvisit() {
        return mailequipvisit;
    }

    public void setMailequipvisit(String mailequipvisit) {
        this.mailequipvisit = mailequipvisit;
    }

    public long getCodtecnic() {
        return codtecnic;
    }

    public String getFirma1_dp_actualizar() {
        return firma1_dp_actualizar;
    }

    public void setFirma1_dp_actualizar(String firma1_dp_actualizar) {
        this.firma1_dp_actualizar = firma1_dp_actualizar;
    }

    public String getFirma1_cl_actualizar() {
        return firma1_cl_actualizar;
    }

    public void setFirma1_cl_actualizar(String firma1_cl_actualizar) {
        this.firma1_cl_actualizar = firma1_cl_actualizar;
    }

    public String getFirma1_el_actualizar() {
        return firma1_el_actualizar;
    }

    public void setFirma1_el_actualizar(String firma1_el_actualizar) {
        this.firma1_el_actualizar = firma1_el_actualizar;
    }

    public String getFirma1_cv_actualizar() {
        return firma1_cv_actualizar;
    }

    public void setFirma1_cv_actualizar(String firma1_cv_actualizar) {
        this.firma1_cv_actualizar = firma1_cv_actualizar;
    }

    public String getFirma1_ev_actualizar() {
        return firma1_ev_actualizar;
    }

    public void setFirma1_ev_actualizar(String firma1_ev_actualizar) {
        this.firma1_ev_actualizar = firma1_ev_actualizar;
    }

    public String getFirma2_cl_actualizar() {
        return firma2_cl_actualizar;
    }

    public void setFirma2_cl_actualizar(String firma2_cl_actualizar) {
        this.firma2_cl_actualizar = firma2_cl_actualizar;
    }

    public String getFirma2_ar_actualizar() {
        return firma2_ar_actualizar;
    }

    public void setFirma2_ar_actualizar(String firma2_ar_actualizar) {
        this.firma2_ar_actualizar = firma2_ar_actualizar;
    }

    public String getFirma2_cv_actualizar() {
        return firma2_cv_actualizar;
    }

    public void setFirma2_cv_actualizar(String firma2_cv_actualizar) {
        this.firma2_cv_actualizar = firma2_cv_actualizar;
    }

    public void setCodtecnic(long codtecnic) {
        this.codtecnic = codtecnic;
    }

    public String getSancion_l1() {
        return sancion_l1;
    }

    public void setSancion_l1(String sancion_l1) {
        this.sancion_l1 = sancion_l1;
    }

    public String getSancion_l2() {
        return sancion_l2;
    }

    public void setSancion_l2(String sancion_l2) {
        this.sancion_l2 = sancion_l2;
    }

    public String getSancion_l3() {
        return sancion_l3;
    }

    public void setSancion_l3(String sancion_l3) {
        this.sancion_l3 = sancion_l3;
    }

    public String getSancion_l4() {
        return sancion_l4;
    }

    public void setSancion_l4(String sancion_l4) {
        this.sancion_l4 = sancion_l4;
    }

    public String getSancion_l5() {
        return sancion_l5;
    }

    public void setSancion_l5(String sancion_l5) {
        this.sancion_l5 = sancion_l5;
    }

    public String getSancion_l6() {
        return sancion_l6;
    }

    public void setSancion_l6(String sancion_l6) {
        this.sancion_l6 = sancion_l6;
    }

    public String getSancion_l7() {
        return sancion_l7;
    }

    public void setSancion_l7(String sancion_l7) {
        this.sancion_l7 = sancion_l7;
    }

    public String getSancion_l8() {
        return sancion_l8;
    }

    public void setSancion_l8(String sancion_l8) {
        this.sancion_l8 = sancion_l8;
    }

    public String getSancion_l9() {
        return sancion_l9;
    }

    public void setSancion_l9(String sancion_l9) {
        this.sancion_l9 = sancion_l9;
    }

    public String getSancion_l10() {
        return sancion_l10;
    }

    public void setSancion_l10(String sancion_l10) {
        this.sancion_l10 = sancion_l10;
    }

    public String getSancion_v1() {
        return sancion_v1;
    }

    public void setSancion_v1(String sancion_v1) {
        this.sancion_v1 = sancion_v1;
    }

    public String getSancion_v2() {
        return sancion_v2;
    }

    public void setSancion_v2(String sancion_v2) {
        this.sancion_v2 = sancion_v2;
    }

    public String getSancion_v3() {
        return sancion_v3;
    }

    public void setSancion_v3(String sancion_v3) {
        this.sancion_v3 = sancion_v3;
    }

    public String getSancion_v4() {
        return sancion_v4;
    }

    public void setSancion_v4(String sancion_v4) {
        this.sancion_v4 = sancion_v4;
    }

    public String getSancion_v5() {
        return sancion_v5;
    }

    public void setSancion_v5(String sancion_v5) {
        this.sancion_v5 = sancion_v5;
    }

    public String getSancion_v6() {
        return sancion_v6;
    }

    public void setSancion_v6(String sancion_v6) {
        this.sancion_v6 = sancion_v6;
    }

    public String getSancion_v7() {
        return sancion_v7;
    }

    public void setSancion_v7(String sancion_v7) {
        this.sancion_v7 = sancion_v7;
    }

    public String getSancion_v8() {
        return sancion_v8;
    }

    public void setSancion_v8(String sancion_v8) {
        this.sancion_v8 = sancion_v8;
    }

    public String getSancion_v9() {
        return sancion_v9;
    }

    public void setSancion_v9(String sancion_v9) {
        this.sancion_v9 = sancion_v9;
    }

    public String getSancion_v10() {
        return sancion_v10;
    }

    public void setSancion_v10(String sancion_v10) {
        this.sancion_v10 = sancion_v10;
    }

    public String getPin_clubl() {
        return pin_clubl;
    }

    public void setPin_clubl(String pin_clubl) {
        this.pin_clubl = pin_clubl;
    }

    public String getPin_clubv() {
        return pin_clubv;
    }

    public void setPin_clubv(String pin_clubv) {
        this.pin_clubv = pin_clubv;
    }

    public String getFirma2_ar2() {
        return firma2_ar2;
    }

    public void setFirma2_ar2(String firma2_ar2) {
        this.firma2_ar2 = firma2_ar2;
    }

    public String getFirma2_ar2_actualizar() {
        return firma2_ar2_actualizar;
    }

    public void setFirma2_ar2_actualizar(String firma2_ar2_actualizar) {
        this.firma2_ar2_actualizar = firma2_ar2_actualizar;
    }

    public String getPin_ar2() {
        return pin_ar2;
    }

    public void setPin_ar2(String pin_ar2) {
        this.pin_ar2 = pin_ar2;
    }

    public long getNum_l11() {
        return num_l11;
    }

    public void setNum_l11(long num_l11) {
        this.num_l11 = num_l11;
    }

    public long getNum_l12() {
        return num_l12;
    }

    public void setNum_l12(long num_l12) {
        this.num_l12 = num_l12;
    }

    public long getNum_l13() {
        return num_l13;
    }

    public void setNum_l13(long num_l13) {
        this.num_l13 = num_l13;
    }

    public long getNum_l14() {
        return num_l14;
    }

    public void setNum_l14(long num_l14) {
        this.num_l14 = num_l14;
    }

    public long getNum_l15() {
        return num_l15;
    }

    public void setNum_l15(long num_l15) {
        this.num_l15 = num_l15;
    }

    public long getNum_l16() {
        return num_l16;
    }

    public void setNum_l16(long num_l16) {
        this.num_l16 = num_l16;
    }

    public long getNum_l17() {
        return num_l17;
    }

    public void setNum_l17(long num_l17) {
        this.num_l17 = num_l17;
    }

    public long getNum_v11() {
        return num_v11;
    }

    public void setNum_v11(long num_v11) {
        this.num_v11 = num_v11;
    }

    public long getNum_v12() {
        return num_v12;
    }

    public void setNum_v12(long num_v12) {
        this.num_v12 = num_v12;
    }

    public long getNum_v13() {
        return num_v13;
    }

    public void setNum_v13(long num_v13) {
        this.num_v13 = num_v13;
    }

    public long getNum_v14() {
        return num_v14;
    }

    public void setNum_v14(long num_v14) {
        this.num_v14 = num_v14;
    }

    public long getNum_v15() {
        return num_v15;
    }

    public void setNum_v15(long num_v15) {
        this.num_v15 = num_v15;
    }

    public long getNum_v16() {
        return num_v16;
    }

    public void setNum_v16(long num_v16) {
        this.num_v16 = num_v16;
    }

    public long getNum_v17() {
        return num_v17;
    }

    public void setNum_v17(long num_v17) {
        this.num_v17 = num_v17;
    }

    public String getTotalfaltasl() {
        return totalfaltasl;
    }

    public void setTotalfaltasl(String totalfaltasl) {
        this.totalfaltasl = totalfaltasl;
    }

    public String getTotalfaltasv() {
        return totalfaltasv;
    }

    public void setTotalfaltasv(String totalfaltasv) {
        this.totalfaltasv = totalfaltasv;
    }

    public String getTotalgolesl() {
        return totalgolesl;
    }

    public void setTotalgolesl(String totalgolesl) {
        this.totalgolesl = totalgolesl;
    }

    public String getTotalgolesv() {
        return totalgolesv;
    }

    public void setTotalgolesv(String totalgolesv) {
        this.totalgolesv = totalgolesv;
    }

    public String getSaqueinicial() {
        return saqueinicial;
    }

    public void setSaqueinicial(String saqueinicial) {
        this.saqueinicial = saqueinicial;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getTiempomuertol1() {
        return tiempomuertol1;
    }

    public void setTiempomuertol1(String tiempomuertol1) {
        this.tiempomuertol1 = tiempomuertol1;
    }

    public String getTiempomuertol2() {
        return tiempomuertol2;
    }

    public void setTiempomuertol2(String tiempomuertol2) {
        this.tiempomuertol2 = tiempomuertol2;
    }

    public String getTiempomuertov1() {
        return tiempomuertov1;
    }

    public void setTiempomuertov1(String tiempomuertov1) {
        this.tiempomuertov1 = tiempomuertov1;
    }

    public String getTiempomuertov2() {
        return tiempomuertov2;
    }

    public void setTiempomuertov2(String tiempomuertov2) {
        this.tiempomuertov2 = tiempomuertov2;
    }

    public ArrayList<String> getFaltasap() {
        return faltasap;
    }

    public void setFaltasap(ArrayList<String> faltasap) {
        this.faltasap = faltasap;
    }

    public ArrayList<String> getFaltasam() {
        return faltasam;
    }

    public void setFaltasam(ArrayList<String> faltasam) {
        this.faltasam = faltasam;
    }

    public ArrayList<String> getFaltasad() {
        return faltasad;
    }

    public void setFaltasad(ArrayList<String> faltasad) {
        this.faltasad = faltasad;
    }

    public ArrayList<String> getFaltasar() {
        return faltasar;
    }

    public void setFaltasar(ArrayList<String> faltasar) {
        this.faltasar = faltasar;
    }

    public ArrayList<String> getFaltasrp() {
        return faltasrp;
    }

    public void setFaltasrp(ArrayList<String> faltasrp) {
        this.faltasrp = faltasrp;
    }

    public ArrayList<String> getFaltasrm() {
        return faltasrm;
    }

    public void setFaltasrm(ArrayList<String> faltasrm) {
        this.faltasrm = faltasrm;
    }

    public ArrayList<String> getFaltasrd() {
        return faltasrd;
    }

    public void setFaltasrd(ArrayList<String> faltasrd) {
        this.faltasrd = faltasrd;
    }

    public ArrayList<String> getFaltasrr() {
        return faltasrr;
    }

    public void setFaltasrr(ArrayList<String> faltasrr) {
        this.faltasrr = faltasrr;
    }

    public String getCfaltasap() {
        return cfaltasap;
    }

    public void setCfaltasap(String cfaltasap) {
        this.cfaltasap = cfaltasap;
    }

    public String getCfaltasam() {
        return cfaltasam;
    }

    public void setCfaltasam(String cfaltasam) {
        this.cfaltasam = cfaltasam;
    }

    public String getCfaltasad() {
        return cfaltasad;
    }

    public void setCfaltasad(String cfaltasad) {
        this.cfaltasad = cfaltasad;
    }

    public String getCfaltasar() {
        return cfaltasar;
    }

    public void setCfaltasar(String cfaltasar) {
        this.cfaltasar = cfaltasar;
    }

    public String getCfaltasrp() {
        return cfaltasrp;
    }

    public void setCfaltasrp(String cfaltasrp) {
        this.cfaltasrp = cfaltasrp;
    }

    public String getCfaltasrm() {
        return cfaltasrm;
    }

    public void setCfaltasrm(String cfaltasrm) {
        this.cfaltasrm = cfaltasrm;
    }

    public String getCfaltasrd() {
        return cfaltasrd;
    }

    public void setCfaltasrd(String cfaltasrd) {
        this.cfaltasrd = cfaltasrd;
    }

    public String getCfaltasrr() {
        return cfaltasrr;
    }

    public void setCfaltasrr(String cfaltasrr) {
        this.cfaltasrr = cfaltasrr;
    }

    public String getGolesequipo() {
        return golesequipo;
    }

    public void setGolesequipo(String golesequipo) {
        this.golesequipo = golesequipo;
    }

    public String getFaltasaequipo() {
        return faltasaequipo;
    }

    public void setFaltasaequipo(String faltasaequipo) {
        this.faltasaequipo = faltasaequipo;
    }

    public String getFaltasrequipo() {
        return faltasrequipo;
    }

    public void setFaltasrequipo(String faltasrequipo) {
        this.faltasrequipo = faltasrequipo;
    }

    public ArrayList<String> getFaltasae() {
        return faltasae;
    }

    public void setFaltasae(ArrayList<String> faltasae) {
        this.faltasae = faltasae;
    }

    public ArrayList<String> getFaltasre() {
        return faltasre;
    }

    public void setFaltasre(ArrayList<String> faltasre) {
        this.faltasre = faltasre;
    }

    public String getCgolespl() {
        return cgolespl;
    }

    public void setCgolespl(String cgolespl) {
        this.cgolespl = cgolespl;
    }

    public String getCgolesml() {
        return cgolesml;
    }

    public void setCgolesml(String cgolesml) {
        this.cgolesml = cgolesml;
    }

    public String getCgolesdl() {
        return cgolesdl;
    }

    public void setCgolesdl(String cgolesdl) {
        this.cgolesdl = cgolesdl;
    }

    public String getCgolespv() {
        return cgolespv;
    }

    public void setCgolespv(String cgolespv) {
        this.cgolespv = cgolespv;
    }

    public String getCgolesmv() {
        return cgolesmv;
    }

    public void setCgolesmv(String cgolesmv) {
        this.cgolesmv = cgolesmv;
    }

    public String getCgolesdv() {
        return cgolesdv;
    }

    public void setCgolesdv(String cgolesdv) {
        this.cgolesdv = cgolesdv;
    }

    public ArrayList<String> getGolespl() {
        return golespl;
    }

    public void setGolespl(ArrayList<String> golespl) {
        this.golespl = golespl;
    }

    public ArrayList<String> getGolesml() {
        return golesml;
    }

    public void setGolesml(ArrayList<String> golesml) {
        this.golesml = golesml;
    }

    public ArrayList<String> getGolesdl() {
        return golesdl;
    }

    public void setGolesdl(ArrayList<String> golesdl) {
        this.golesdl = golesdl;
    }

    public ArrayList<String> getGolesel() {
        return golesel;
    }

    public void setGolesel(ArrayList<String> golesel) {
        this.golesel = golesel;
    }

    public ArrayList<String> getGolespv() {
        return golespv;
    }

    public void setGolespv(ArrayList<String> golespv) {
        this.golespv = golespv;
    }

    public ArrayList<String> getGolesmv() {
        return golesmv;
    }

    public void setGolesmv(ArrayList<String> golesmv) {
        this.golesmv = golesmv;
    }

    public ArrayList<String> getGolesdv() {
        return golesdv;
    }

    public void setGolesdv(ArrayList<String> golesdv) {
        this.golesdv = golesdv;
    }

    public ArrayList<String> getGolesev() {
        return golesev;
    }

    public void setGolesev(ArrayList<String> golesev) {
        this.golesev = golesev;
    }

    public long getCodtecnic2() {
        return codtecnic2;
    }

    public void setCodtecnic2(long codtecnic2) {
        this.codtecnic2 = codtecnic2;
    }

    public String getClassevisit() {
        return classevisit;
    }

    public void setClassevisit(String classevisit) {
        this.classevisit = classevisit;
    }
}
