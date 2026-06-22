package it.unipv.posw.model.entities;


/**
 * @author rkomi-dev
 * @author gpelle
 */

public class Biglietto {
	
	private int id_biglietto;
	private Integer id_posto;
	private String email_cliente;
	private String nominativo;
	private String stato; 
    private String qrCode;
    private Tariffa tariffa;
    private double prezzoAcquisto;
    
	public Biglietto(int id_biglietto, Integer id_posto, String email_cliente, String nominativo, String stato,
			String qrCode, Tariffa tariffa, double prezzoAcquisto) {
		super();
		this.id_biglietto = id_biglietto;
		this.id_posto = id_posto;
		this.email_cliente = email_cliente;
		this.nominativo = nominativo;
		this.stato = stato;
		this.qrCode = qrCode;
		this.tariffa = tariffa;
		this.prezzoAcquisto = prezzoAcquisto;
	}

	public double getPrezzoAcquisto() {
		return prezzoAcquisto;
	}

	public Tariffa getTariffa() {
		return tariffa;
	}

	public void setPrezzoAcquisto(double prezzoAcquisto) {
		this.prezzoAcquisto = prezzoAcquisto;
	}

	public int getId_biglietto() {
		return id_biglietto;
	}

	public Integer getId_posto() {
		return id_posto;
	}

	public void setId_posto(Integer id_posto) {
		this.id_posto = id_posto;
	}

	public String getEmail_cliente() {
		return email_cliente;
	}

	public void setEmail_cliente(String email_cliente) {
		this.email_cliente = email_cliente;
	}

	public String getNominativo() {
		return nominativo;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public void setId_biglietto(int id_biglietto) {
		this.id_biglietto = id_biglietto;
	}

	public void setTariffa(Tariffa tariffa) {
		this.tariffa = tariffa;
	}
	
}
