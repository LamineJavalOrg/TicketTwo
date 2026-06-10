package it.unipv.posw.model.entities;

import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;

/**
 * @author gpelle
 */
public class Settore {
	private int id_settore;
	private int id_sede;
    private TipologiaSettore nome_settore; 
    private TipologiaPosto tipo;    
    private int capienza_max;
    private int num_file;
    private int posti_per_fila;
    private String prefisso;
    
	public Settore(int id_settore, int id_sede, TipologiaSettore nome_settore, TipologiaPosto tipo, int capienza_max,
			int num_file, int posti_per_fila, String prefisso) {
		super();
		this.id_settore = id_settore;
		this.id_sede = id_sede;
		this.nome_settore = nome_settore;
		this.tipo = tipo;
		this.capienza_max = capienza_max;
		this.num_file = num_file;
		this.posti_per_fila = posti_per_fila;
		this.prefisso = prefisso;
	}

	public int getId_settore() {
		return id_settore;
	}

	public int getId_sede() {
		return id_sede;
	}

	public TipologiaSettore getNome_settore() {
		return nome_settore;
	}

	public TipologiaPosto getTipo() {
		return tipo;
	}

	public int getCapienza_max() {
		return capienza_max;
	}

	public int getNum_file() {
		return num_file;
	}

	public int getPosti_per_fila() {
		return posti_per_fila;
	}

	public String getPrefisso() {
		return prefisso;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}

	public void setId_settore(int id_settore) {
		this.id_settore = id_settore;
	}

	
	
	
}
