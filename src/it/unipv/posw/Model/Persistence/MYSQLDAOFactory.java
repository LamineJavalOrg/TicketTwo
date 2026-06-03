package it.unipv.posw.Model.Persistence;

import it.unipv.posw.Model.Persistence.DAO.ClienteDAO;
import it.unipv.posw.Model.Persistence.DAO.OrganizzatoreDAO;
import it.unipv.posw.Model.Persistence.DAO.Interface.IClienteDAO;
import it.unipv.posw.Model.Persistence.DAO.Interface.IOrganizzatoreDAO;

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
