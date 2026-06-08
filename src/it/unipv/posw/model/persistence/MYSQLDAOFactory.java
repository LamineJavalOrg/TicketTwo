package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.DAO.ArtistaDAO;
import it.unipv.posw.model.persistence.DAO.ClienteDAO;
import it.unipv.posw.model.persistence.DAO.EventoDAO;
import it.unipv.posw.model.persistence.DAO.OrganizzatoreDAO;
import it.unipv.posw.model.persistence.DAO.SedeDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.IOrganizzatoreDAO;
import it.unipv.posw.model.persistence.DAO.interfaces.ISedeDAO;

/**
 * @author gpelle
 * @author rkomi-dev
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
	
	@Override
	public IArtistaDAO getArtistaDAO() {
		return new ArtistaDAO();
	}

	@Override
	public IEventoDAO getEventoDAO() {
		return new EventoDAO();
	}

	@Override
	public ISedeDAO getSedeDAO() {
		return new SedeDAO();
	}

}
