package it.unipv.posw.model.entities;

import java.time.LocalDateTime;

/**
 * @author rkomi-dev
 */

public class RiepilogoAcquisto {
	
	private int id_biglietto;
	private String nomeEvento;
	private LocalDateTime dataOraEvento;
	private String nominativo;
	private double prezzo;

	public RiepilogoAcquisto(int id_biglietto, String nomeEvento, LocalDateTime dataOraEvento, String nominativo, double prezzo) {
		
		this.id_biglietto = id_biglietto;
		this.nomeEvento = nomeEvento;
	    this.dataOraEvento = dataOraEvento;
	    this.nominativo = nominativo;
	    this.prezzo = prezzo;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	public LocalDateTime getDataOraEvento() {
		return dataOraEvento;
	}

	public String getNominativo() {
		return nominativo;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public int getId_biglietto() {
		return id_biglietto;
	}
	
	
}
