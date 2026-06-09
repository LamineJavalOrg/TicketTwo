package it.unipv.posw.model.service.ricerca;

import java.util.List;

import it.unipv.posw.model.enums.RicercaType;

/**
 * @author rkomi-dev
 */

public interface IRicercaStrategy {
	
	List<?> ricerca(String query);
	RicercaType getDestinazione();
	String getEtichettaSuggerimento(Object o);


}
