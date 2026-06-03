package it.unipv.posw.model;

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
}
