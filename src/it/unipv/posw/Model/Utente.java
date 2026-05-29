package it.unipv.posw.Model;

import java.time.LocalDate;

public abstract class Utente {
	private String nome;
	private String cognome;
	private LocalDate data_nascita;
	private String email;
	private String password;
	/**
	 * @author gpelle
	 */
	public Utente(String nome, String cognome, LocalDate data_nascita, String email, String password) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.data_nascita = data_nascita;
		this.email = email;
		this.password = password;
	}
}
