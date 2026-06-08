package it.unipv.posw.model.persistence.DAO.interfaces;

import it.unipv.posw.model.Organizzatore;

/**
 * @author gpelle
 */
public interface IOrganizzatoreDAO {
	boolean salvaOrganizzatore(Organizzatore organizzatore);
	Organizzatore trovaOrganizzatorePerEmail(String email);
	boolean isEmailEsistente(String email);
}
