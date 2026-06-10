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
	
    
    
    


	

    
    
    
    

}
