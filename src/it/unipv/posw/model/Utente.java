package it.unipv.posw.model;

import java.time.LocalDate;

/**
 * @author gpelle
 * @author rkomi-dev
 */
public abstract class Utente {
	private String nome;
	private String cognome;
	private LocalDate data_nascita;
	private String email;
	private String password;

	public Utente(String nome, String cognome, LocalDate data_nascita, String email, String password) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.data_nascita = data_nascita;
		this.email = email;
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public LocalDate getData_nascita() {
		return data_nascita;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	
	
}
