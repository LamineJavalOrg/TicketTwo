package it.unipv.posw.model.gestori;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.service.EventoService;
import it.unipv.posw.model.service.SedeService;

/**
 * Classe che contiene il model necessario per l' evento.
 * Implementa il Pattern Singleton, l'accesso avviene tramite {@link #getInstance()}.
 * @author gpelle
 */
public class GestoreEvento {
	
	private static GestoreEvento instance;
	private SedeService sedeService;
    private EventoService eventoService;
    private Evento evento;
    private TipologiaBiglietto tipologiaCorrente;
    
    public static GestoreEvento getInstance() {
		if (instance == null) {
			instance = new GestoreEvento();
	    }
	    return instance;
	}
    
    public GestoreEvento() {
		this.sedeService = new SedeService();
		this.eventoService = new EventoService();
		this.evento = new Evento();
		this.tipologiaCorrente = null;
	}

	public SedeService getSedeService() {
		return sedeService;
	}

	public EventoService getEventoService() {
		return eventoService;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public TipologiaBiglietto getTipologiaCorrente() {
		return tipologiaCorrente;
	}

	public void setTipologiaCorrente(TipologiaBiglietto tipologiaCorrente) {
		this.tipologiaCorrente = tipologiaCorrente;
	}
}
