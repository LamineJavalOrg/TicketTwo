package it.unipv.posw.model.gestori;

import it.unipv.posw.model.service.EventoService;
import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class GestoreAdmin {

	private static GestoreAdmin instance;
	private SedeService sedeService;
	private EventoService eventoService;
	
	public static GestoreAdmin getInstance() {
		if (instance == null) {
			instance = new GestoreAdmin();
	    }
	    return instance;
	}

	public GestoreAdmin() {
		this.sedeService = new SedeService();
		this.eventoService = new EventoService();
	}

	public SedeService getSedeService() {
		return sedeService;
	}

	public EventoService getEventoService() {
		return eventoService;
	}
}
