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
    
	public Evento(int id_evento, String nome, TipologiaEvento tipo, String email_organizzatore, int id_artista) {
		super();
		this.id_evento = id_evento;
		this.nome = nome;
		this.tipo = tipo;
		this.email_organizzatore = email_organizzatore;
		this.id_artista = id_artista;
		this.tappe = new ArrayList<Tappa>();
	}
	
	public void aggiungiTappa(Tappa tp) {
        this.tappe.add(tp);
    }

	public String getNome() {
		return nome;
	}
	
	
}
