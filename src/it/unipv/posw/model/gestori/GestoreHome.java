package it.unipv.posw.model.gestori;

import it.unipv.posw.model.enums.RicercaType;
import it.unipv.posw.model.service.AutenticazioneService;
import it.unipv.posw.model.service.RegistrazioneService;
import it.unipv.posw.model.service.ricerca.IRicercaStrategy;
import it.unipv.posw.model.service.ricerca.RicercaFactory;

/** Classe che contiene il model necessario per la home
 * @author rkomi-dev
 */

public class GestoreHome {
	
	private static GestoreHome instance;
	private RegistrazioneService registrazioneService;
	private AutenticazioneService autenticazioneService;
	private IRicercaStrategy strategyDefault;

	
	public static GestoreHome getInstance() {
		if (instance == null) {
			instance = new GestoreHome();
	    }
	    return instance;
	}
	 
	private GestoreHome() {
		this.registrazioneService = new RegistrazioneService();
	    this.autenticazioneService = new AutenticazioneService();
	    this.strategyDefault = RicercaFactory.getRicercaStrategy(RicercaType.PER_EVENTO);
	}
	 
	public RegistrazioneService getRegistrazioneService() {
		return registrazioneService;
	}
	
	public AutenticazioneService getAutenticazioneService() {
		return autenticazioneService;
	}
	
	public IRicercaStrategy getStrategyDefault() {
		return strategyDefault;
	}
}
