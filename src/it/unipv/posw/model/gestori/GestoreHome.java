package it.unipv.posw.model.gestori;

import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.model.service.RegistrazioneService;

/** Classe che contiene il model necessario per la home
 * @author rkomi-dev
 */

public class GestoreHome {
	
	private static GestoreHome instance;
	private RegistrazioneService registrazioneService;
	private AutenticazioneService autenticazioneService;
	
	public static GestoreHome getInstance() {
		if (instance == null) {
			instance = new GestoreHome();
	    }
	    return instance;
	}
	 
	private GestoreHome() {
		this.registrazioneService = new RegistrazioneService();
	    this.autenticazioneService = new AutenticazioneService();
	}
	 
	public RegistrazioneService getRegistrazioneService() {
		return registrazioneService;
	}
	public AutenticazioneService getAutenticazioneService() {
		return autenticazioneService;
	}
	
	
	

}
