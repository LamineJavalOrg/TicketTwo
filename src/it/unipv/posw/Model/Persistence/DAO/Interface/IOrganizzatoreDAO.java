package it.unipv.posw.Model.Persistence.DAO.Interface;

import it.unipv.posw.Model.Organizzatore;

/**
 * @author gpelle
 */
public interface IOrganizzatoreDAO {
	boolean salvaOrganizzatore(Organizzatore organizzatore);
	Organizzatore trovaOrganizzatorePerEmail(String email);
}
