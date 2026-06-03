package it.unipv.posw.model;

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
    
	public Evento(int id_evento, String nome, TipologiaEvento tipo, String email_organizzatore, int id_artista,
			List<Tappa> tappe) {
		super();
		this.id_evento = id_evento;
		this.nome = nome;
		this.tipo = tipo;
		this.email_organizzatore = email_organizzatore;
		this.id_artista = id_artista;
		this.tappe = tappe;
	}
	
	public void aggiungiTappa(Tappa tp) {
        this.tappe.add(tp);
    }
}
