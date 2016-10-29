package es.fecapa.eacta.bean;

import javax.persistence.Entity;

@Entity
public class DatosActa {
	//Datos jugadores
	private String codlic;
	private String nif;
	private String nom;
	private String categoria;
	private String submodalitat;
	private long codenti;
	private long codcate;
	private long classe;
	private long codequip;
	private long codequip2;
	private long codequip3;
	private String dorsal;
	private String pin;
	private long sancionat;
	public String getCodlic() {
		return codlic;
	}
	public void setCodlic(String codlic) {
		this.codlic = codlic;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSubmodalitat() {
		return submodalitat;
	}
	public void setSubmodalitat(String submodalitat) {
		this.submodalitat = submodalitat;
	}
	public long getCodenti() {
		return codenti;
	}
	public void setCodenti(long codenti) {
		this.codenti = codenti;
	}
	public long getCodcate() {
		return codcate;
	}
	public void setCodcate(long codcate) {
		this.codcate = codcate;
	}
	public long getClasse() {
		return classe;
	}
	public void setClasse(long classe) {
		this.classe = classe;
	}
	public long getCodequip() {
		return codequip;
	}
	public void setCodequip(long codequip) {
		this.codequip = codequip;
	}
	public long getCodequip2() {
		return codequip2;
	}
	public void setCodequip2(long codequip2) {
		this.codequip2 = codequip2;
	}
	public long getCodequip3() {
		return codequip3;
	}
	public void setCodequip3(long codequip3) {
		this.codequip3 = codequip3;
	}
	public String getDorsal() {
		return dorsal;
	}
	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public long getSancionat() {
		return sancionat;
	}
	public void setSancionat(long sancionat) {
		this.sancionat = sancionat;
	}

	
}
