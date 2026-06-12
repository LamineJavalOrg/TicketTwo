package it.unipv.posw.model.gestori;

import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class GestoreAdmin {

	private static GestoreAdmin instance;
	private SedeService sedeService;
	
	public static GestoreAdmin getInstance() {
		if (instance == null) {
			instance = new GestoreAdmin();
	    }
	    return instance;
	}

	public GestoreAdmin() {
		this.sedeService = new SedeService();
	}

	public SedeService getSedeService() {
		return sedeService;
	}
	
	
	
	
}
