package it.unibo.paw.hibernate;

import java.io.Serializable;

public class Pacco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String statoPacco;
	private String emailMittente;
	private String emailDestinatario;
	private String indirizzoMittente;
	private String indirizzoDestinatario;
	private String emailCorriere;


	public Pacco() {
	}
	
	
	
	
	public String getEmailCorriere() {
		return emailCorriere;
	}




	public void setEmailCorriere(String emailCorriere) {
		this.emailCorriere = emailCorriere;
	}




	public String getIndirizzoDestinatario() {
		return indirizzoDestinatario;
	}




	public void setIndirizzoDestinatario(String indirizzoDestinatario) {
		this.indirizzoDestinatario = indirizzoDestinatario;
	}




	public String getEmailMittente() {
		return emailMittente;
	}



	public void setEmailMittente(String emailMittente) {
		this.emailMittente = emailMittente;
	}



	public String getEmailDestinatario() {
		return emailDestinatario;
	}


	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}


	public String getIndirizzoMittente() {
		return indirizzoMittente;
	}


	public void setIndirizzoMittente(String indirizzoMittente) {
		this.indirizzoMittente = indirizzoMittente;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStatoPacco() {
		return statoPacco;
	}


	public void setStatoPacco(String statoPacco) {
		this.statoPacco = statoPacco;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	

}
