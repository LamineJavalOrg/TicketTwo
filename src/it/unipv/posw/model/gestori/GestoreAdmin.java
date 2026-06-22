package it.unipv.posw.model.gestori;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.service.ArOrganizzatoreService;
import it.unipv.posw.model.service.CreaEventoService;
import it.unipv.posw.model.service.SedeService;

/**
 * Classe che contiene il model necessario per tutte le operazioni amministrative degli organizzatori.
 * Implementa il Pattern Singleton, l'accesso avviene tramite {@link #getInstance()}.
 * @author gpelle
 */
public class GestoreAdmin {

	private static GestoreAdmin instance;
	private SedeService sedeService;
	private CreaEventoService creaEventoService;
	private ArOrganizzatoreService arOrganizzatoreService;
	private Evento evento;
	private Sede sede;
	
	/**
	 * Restituisce l'unica istanza del gestore, creandola alla prima invocazione.
	 * @return L'istanza condivisa del gestore admin.
	 */
	public static GestoreAdmin getInstance() {
		if (instance == null) {
			instance = new GestoreAdmin();
	    }
	    return instance;
	}

	/**
	 * Costruttore che inizializza i service e gli oggetti di lavoro.
	 */
	public GestoreAdmin() {
		this.sedeService = new SedeService();
		this.creaEventoService = new CreaEventoService();
		this.arOrganizzatoreService = new ArOrganizzatoreService();
		this.evento = new Evento();
		this.sede = new Sede();
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

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Sede getSede() {
		return sede;
	}

	public void setSede(Sede sede) {
		this.sede = sede;
	}
}
