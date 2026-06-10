package it.unipv.posw.model.service.ricerca;

import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.RicercaType;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;

/**
 * @author rkomi-dev
 */

public class RicercaPerEventoStrategy implements IRicercaStrategy {

	public RicercaPerEventoStrategy() {
	}

	@Override
	public List<Evento> ricerca(String query) {
		
		return MYSQLDAOFactory.getInstance().getEventoDAO().trovaEventiPerNome(query);
	}

	

	@Override
	public String getEtichettaSuggerimento(Object o) {
		Evento e = (Evento)o;
		return e.getNome();
	}

	@Override
	public RicercaType getDestinazione() {
		
		return RicercaType.PER_EVENTO;
	}

}
