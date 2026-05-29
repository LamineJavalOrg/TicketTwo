package it.unipv.posw.Model;

import java.time.LocalDate;

public class Organizzatore extends Utente {
	private String nome_organizzazione;
	/**
	 * @author gpelle
	 */
	public Organizzatore(String nome, String cognome, LocalDate data_nascita, String email, String password,
			String nome_organizzazione) {
		super(nome, cognome, data_nascita, email, password);
		this.nome_organizzazione = nome_organizzazione;
	}
	
	
	

	

}
