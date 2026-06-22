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

/**
 * Classe del model dell'area riservata dell'organizzatore per la rimozione di sedi ed eventi.
 * @author gpelle
 */
public class ArOrganizzatoreService {
	
	/**
	 * Elimina una sede, previa verifica che non sia occupata da alcuna tappa.
	 * @param sede La {@link Sede} da eliminare
	 * @return true se l'eliminazione è riuscita.
	 * @throws SedeException Se la sede è occupata e quindi non eliminabile 
	 * ({@link SedeNonEliminabileException}).
	 */
	public boolean eliminaSede(Sede sede) throws SedeException {
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		
		if (tappaDAO.contaTappePerSede(sede.getId_sede()) > 0) {
			throw new SedeNonEliminabileException();
		}
        return sedeDAO.eliminaSede(sede.getId_sede());
	}
	
	/**
	 * Elimina un evento, previa verifica che non abbia biglietti già venduti.
	 * @param evento L'{@link Evento} da eliminare
	 * @return true se l'eliminazione è riuscita.
	 * @throws EventoException Se l'evento ha biglietti venduti e quindi non è eliminabile
	 * ({@link EventoNonEliminabileException}).
	 */
	public boolean eliminaEvento(Evento evento) throws EventoException {
		int idEvento = evento.getId_evento();
		int bigliettiVenduti = MYSQLDAOFactory.getInstance().getBigliettoDAO().contaBigliettiVenduti(idEvento);
		
		if (bigliettiVenduti > 0) {
			throw new EventoNonEliminabileException();
		}
		return MYSQLDAOFactory.getInstance().getEventoDAO().eliminaEvento(idEvento);
	}
	
	/**
	 * Restituisce le sedi appartenenti all'organizzatore indicato.
	 * @param email L'email dell'organizzatore
	 * @return La lista delle sedi dell'organizzatore.
	 */
	public List<Sede> getSediPerOrganizzatore(String email) {
    	return MYSQLDAOFactory.getInstance().getSedeDAO().getSediPerOrganizzatore(email);
    }
	
	/**
	 * Restituisce gli eventi creati dall'organizzatore indicato.
	 * @param email L'mail dell'organizzatore
	 * @return La lista degli eventi dell'organizzatore
	 */
	public List<Evento> getEventiPerOrganizzatore(String email) {
		return MYSQLDAOFactory.getInstance().getEventoDAO().trovaEventiPerOrganizzatore(email);
	}
}
