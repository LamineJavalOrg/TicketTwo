package it.unipv.posw.model.persistence;

import it.unipv.posw.model.persistence.dao.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IBigliettoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IClienteDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IOrganizzatoreDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IRiepilogoAcquistoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISettoreDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITariffaDAO;

/**
 * @author gpelle
 * @author rkomi-dev
 */
public interface IDAOFactory {
	IClienteDAO getClienteDAO();
	IOrganizzatoreDAO getOrganizzatoreDAO();
	IArtistaDAO getArtistaDAO();
	IEventoDAO getEventoDAO();
	ISedeDAO getSedeDAO();
	IBigliettoDAO getBigliettoDAO();
	ISettoreDAO getSettoreDAO();
	IPostoDAO getPostoDAO();
	ITappaDAO getTappaDAO();
	ITariffaDAO getTariffaDAO();
	IRiepilogoAcquistoDAO getRiepilogoAcquistoDAO();
}
