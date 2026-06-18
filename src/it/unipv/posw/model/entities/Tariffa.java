package it.unipv.posw.model.entities;

import it.unipv.posw.model.enums.TipologiaBiglietto;

/**
 * @author gpelle
 */
public class Tariffa {
	private int id_evento;
    private int id_settore;
    private TipologiaBiglietto tipob;
    private double prezzo;
    private int quantita_massima;
    private int id_tappa;
    
	public Tariffa(int id_evento, int id_settore, TipologiaBiglietto tipob, double prezzo, int quantita_massima,
			int id_tappa) {
		super();
		this.id_evento = id_evento;
		this.id_settore = id_settore;
		this.tipob = tipob;
		this.prezzo = prezzo;
		this.quantita_massima = quantita_massima;
		this.id_tappa = id_tappa;
	}

	public int getId_evento() {
		return id_evento;
	}

	public int getId_settore() {
		return id_settore;
	}

	public TipologiaBiglietto getTipob() {
		return tipob;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public int getQuantita_massima() {
		return quantita_massima;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	
}
