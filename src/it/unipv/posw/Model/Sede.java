package it.unipv.posw.Model;

import java.util.ArrayList;
import java.util.List;
/**
 * @author gpelle
 */
public class Sede {
	private int id_sede;
    private String nome;
    private String indirizzo;
    private List<Settore> settori = new ArrayList<>();
    
	public Sede(int id_sede, String nome, String indirizzo, List<Settore> settori) {
		super();
		this.id_sede = id_sede;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.settori = settori;
	}
    
    
}
