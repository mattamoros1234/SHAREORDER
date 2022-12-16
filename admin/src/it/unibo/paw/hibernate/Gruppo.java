package it.unibo.paw.hibernate;

public class Gruppo {
	
	private int id;
	private String nomeGruppo;
	private String emailCreatore;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomeGruppo() {
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}
	public String getEmailCreatore() {
		return emailCreatore;
	}
	public void setEmailCreatore(String emailCreatore) {
		this.emailCreatore = emailCreatore;
	}
	
}
