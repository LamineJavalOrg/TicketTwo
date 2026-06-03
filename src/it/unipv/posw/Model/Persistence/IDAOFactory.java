package it.unipv.posw.Model.Persistence;

import it.unipv.posw.Model.Persistence.DAO.Interface.IClienteDAO;

/**
 * @author gpelle
 */
public interface IDAOFactory {
	IClienteDAO getClienteDAO();
}
