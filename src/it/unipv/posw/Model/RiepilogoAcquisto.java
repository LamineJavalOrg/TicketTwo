package it.unipv.posw.Model;

import java.time.LocalDateTime;

public class RiepilogoAcquisto {
	
	/**
	 * author rkomi-dev
	 */
	
	private String nomeEvento;
	private LocalDateTime dataOraEvento;
	private String nominativo;
	private double prezzo;

	public RiepilogoAcquisto(String nomeEvento, LocalDateTime dataOraEvento, String nominativo, double prezzo) {
		
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

}
