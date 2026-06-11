package it.unipv.posw.model.service.ricerca;

import java.util.List;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.RicercaType;

/**
 * @author rkomi-dev
 */

public interface IRicercaStrategy {
	
	List<?> ricerca(String query);
	RicercaType getDestinazione();
	String getEtichettaSuggerimento(Object o);
	List<Evento> eseguiPostRicerca(Object scelta);
	
}
