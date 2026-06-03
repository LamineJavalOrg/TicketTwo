package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.DAO.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IOrganizzatoreDAO;

/**
 * @author gpelle
 */
public interface IDAOFactory {
	IClienteDAO getClienteDAO();
	IOrganizzatoreDAO getOrganizzatoreDAO();
}
