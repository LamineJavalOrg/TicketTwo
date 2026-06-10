package it.unipv.posw.model.entities;

import java.time.LocalDate;

/**
 * @author rkomi-dev
 */

public class Cliente extends Utente {

	public Cliente(String nome, String cognome, LocalDate data_nascita, String email, String password) {
		super(nome, cognome, data_nascita, email, password);
	}
	
	
	
}
