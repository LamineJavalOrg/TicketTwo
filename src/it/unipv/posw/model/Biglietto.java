package it.unipv.posw.model;

/**
 * @author rkomi-dev
 */

public class Biglietto {
	
	private int id_biglietto;
	private int id_evento;
	private Integer id_posto;
	private int id_settore;
	private String email_cliente;
	private String nominativo;
	private String stato; 
    private String qrCode;
    private double prezzo; 
    private String tipo;

	public Biglietto(int id_biglietto, int id_evento, Integer id_posto, int id_settore, String email_cliente,
			String nominativo, String stato, String qrCode, double prezzo, String tipo) {
		super();
		this.id_biglietto = id_biglietto;
		this.id_evento = id_evento;
		this.id_posto = id_posto;
		this.id_settore = id_settore;
		this.email_cliente = email_cliente;
		this.nominativo = nominativo;
		this.stato = stato;
		this.qrCode = qrCode;
		this.prezzo = prezzo;
		this.tipo = tipo;
	}
	public double getPrezzo() {
		return prezzo;
	}
    
    
    
    

}
