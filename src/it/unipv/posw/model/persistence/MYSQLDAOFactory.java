package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.DAO.ClienteDAO;
import it.unipv.posw.model.persistence.DAO.OrganizzatoreDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IOrganizzatoreDAO;

/**
 * @author gpelle
 */
public class MYSQLDAOFactory implements IDAOFactory {
	
	private static MYSQLDAOFactory instance;

    private MYSQLDAOFactory() {}

    public static MYSQLDAOFactory getInstance() {
        if (instance == null) {
            instance = new MYSQLDAOFactory();
        }
        return instance;
    }

	@Override
	public IClienteDAO getClienteDAO() {
		return new ClienteDAO();
	}

	@Override
	public IOrganizzatoreDAO getOrganizzatoreDAO() {
		return new OrganizzatoreDAO();
	}

}
