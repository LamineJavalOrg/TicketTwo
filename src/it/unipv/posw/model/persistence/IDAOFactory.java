package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.DAO.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IOrganizzatoreDAO;

/**
 * @author gpelle
 * @author rkomi-dev
 */
public interface IDAOFactory {
	IClienteDAO getClienteDAO();
	IOrganizzatoreDAO getOrganizzatoreDAO();
	IArtistaDAO getArtistaDAO();
	IEventoDAO getEventoDAO();
}
