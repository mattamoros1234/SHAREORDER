package it.unibo.paw.application;

import java.io.Serializable;

public class Pacco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String statoPacco;
	private String emailDestinatario;
	private String indirizzoMittente;


	public Pacco() {
	}


	public Pacco(int i, String string) {
		this.id=i;
		this.statoPacco=string;
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

