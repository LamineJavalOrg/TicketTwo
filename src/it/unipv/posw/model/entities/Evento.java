package it.unipv.posw.model.entities;

import java.util.ArrayList;
import java.util.List;

import it.unipv.posw.model.enums.TipologiaEvento;
/**
 * @author gpelle
 */
public class Evento {
	private int id_evento;
	private String nome;
	private TipologiaEvento tipo;
	private String email_organizzatore;
	private int id_artista; 
    private List<Tappa> tappe;
    
    public Evento() {};
    
	public Evento(int id_evento, String nome, TipologiaEvento tipo, String email_organizzatore, int id_artista) {
		super();
		this.id_evento = id_evento;
		this.nome = nome;
		this.tipo = tipo;
		this.email_organizzatore = email_organizzatore;
		this.id_artista = id_artista;
		this.tappe = new ArrayList<Tappa>();
	}
	

	public String getNome() {
		return nome;
	}

	public List<Tappa> getTappe() {
		return tappe;
	}

	public int getId_evento() {
		return id_evento;
	}

	public TipologiaEvento getTipo() {
		return tipo;
	}

	public String getEmail_organizzatore() {
		return email_organizzatore;
	}

	public int getId_artista() {
		return id_artista;
	}
	
	
	public void aggiungiTappa(Tappa tp) {
        this.tappe.add(tp);
    }

	@Override
	public String toString() {
	    return nome + " - " + tipo;
	}
	
}
