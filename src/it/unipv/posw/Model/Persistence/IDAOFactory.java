package it.unipv.posw.Model.Persistence;

import it.unipv.posw.Model.Persistence.DAO.Interface.IClienteDAO;
import it.unipv.posw.Model.Persistence.DAO.Interface.IOrganizzatoreDAO;

/**
 * @author gpelle
 */
public interface IDAOFactory {
	IClienteDAO getClienteDAO();
	IOrganizzatoreDAO getOrganizzatoreDAO();
}
