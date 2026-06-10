package it.unipv.posw.model.entities;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public int getId_sede() {
		return id_sede;
	}

	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}
	
	
	
	
    
    
}
