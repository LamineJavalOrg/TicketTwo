package it.unipv.posw.model.gestori;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Sede;
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
	private Evento evento;
	private Sede sede;
	
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
