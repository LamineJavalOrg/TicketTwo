package it.unipv.posw.model.service;

import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.EventoNonEliminabileException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeNonEliminabileException;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;

public class ArOrganizzatoreService {
	
	public boolean eliminaSede(Sede sede) throws SedeException {
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		
		if (tappaDAO.contaTappePerSede(sede.getId_sede()) > 0) {
			throw new SedeNonEliminabileException();
		}
        return sedeDAO.eliminaSede(sede.getId_sede());
	}
	
	public boolean eliminaEvento(Evento evento) throws EventoException {
		int idEvento = evento.getId_evento();
		int bigliettiVenduti = MYSQLDAOFactory.getInstance().getBigliettoDAO().contaBigliettiVenduti(idEvento);
		
		if (bigliettiVenduti > 0) {
			throw new EventoNonEliminabileException();
		}
		return MYSQLDAOFactory.getInstance().getEventoDAO().eliminaEvento(idEvento);
	}
	
	public List<Sede> getSediPerOrganizzatore(String email) {
    	return MYSQLDAOFactory.getInstance().getSedeDAO().getSediPerOrganizzatore(email);
    }
	
	public List<Evento> getEventiPerOrganizzatore(String email) {
		return MYSQLDAOFactory.getInstance().getEventoDAO().trovaEventiPerOrganizzatore(email);
	}
	
}
