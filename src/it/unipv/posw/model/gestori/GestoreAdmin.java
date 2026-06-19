package it.unipv.posw.model.gestori;

import it.unipv.posw.model.service.ArOrganizzatoreService;
import it.unipv.posw.model.service.CreaEventoService;
import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class GestoreAdmin {

	private static GestoreAdmin instance;
	private SedeService sedeService;
	private CreaEventoService creaEventoService;
	private ArOrganizzatoreService arOrganizzatoreService;
	
	public static GestoreAdmin getInstance() {
		if (instance == null) {
			instance = new GestoreAdmin();
	    }
	    return instance;
	}

	public GestoreAdmin() {
		this.sedeService = new SedeService();
		this.creaEventoService = new CreaEventoService();
		this.arOrganizzatoreService = new ArOrganizzatoreService();
	}

	public SedeService getSedeService() {
		return sedeService;
	}

	public CreaEventoService getCreaEventoService() {
		return creaEventoService;
	}

	public ArOrganizzatoreService getArOrganizzatoreService() {
		return arOrganizzatoreService;
	}
}
