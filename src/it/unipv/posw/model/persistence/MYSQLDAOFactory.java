package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.dao.ArtistaDAO;
import it.unipv.posw.model.persistence.dao.BigliettoDAO;
import it.unipv.posw.model.persistence.dao.ClienteDAO;
import it.unipv.posw.model.persistence.dao.EventoDAO;
import it.unipv.posw.model.persistence.dao.OrganizzatoreDAO;
import it.unipv.posw.model.persistence.dao.SedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IBigliettoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IOrganizzatoreDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;

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
	
	@Override
	public IBigliettoDAO getBigliettoDAO() {
		return new BigliettoDAO();
	}

	
	
}
