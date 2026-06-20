package it.unipv.posw.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Organizzatore;
import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.persistence.dao.EventoDAO;
import it.unipv.posw.model.persistence.dao.OrganizzatoreDAO;
import it.unipv.posw.model.persistence.dao.SedeDAO;
import it.unipv.posw.model.service.CreaEventoService;
import it.unipv.posw.model.service.SedeService;

public class CreaeventointegrationTest {
	private CreaEventoService service;
	private SedeService sedeService;
	private SedeDAO sedeDAO;
	private EventoDAO eventoDAO;
	private OrganizzatoreDAO organizzatoreDAO;
 
	private Organizzatore org;
	private Sede sede;
	private int idSettore;
	private Evento eventoCreato;
 
	@Before
	public void iniTest() throws EmptyFieldException, SedeException, SettoreNonValidoException {
		service = new CreaEventoService();
		sedeService = new SedeService();
		sedeDAO = new SedeDAO();
		eventoDAO = new EventoDAO();
		organizzatoreDAO = new OrganizzatoreDAO();
		eventoCreato = null;
 
		// organizzatore reale: l'evento referenzia la sua email
		org = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20),
				"mario.rossi@eventi.it", "prova123", "VivaConcerti");
		organizzatoreDAO.salvaOrganizzatore(org);
 
		// sede reale con un settore: precondizione del caso d'uso
		Sede daSalvare = new Sede();
		daSalvare.setNome("Forum Milano");
		daSalvare.setIndirizzo("Via Vittorio, 6");
		Settore curva = sedeService.creaSettore(TipologiaSettore.CURVA, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 200);
		daSalvare.aggiungiSettore(curva);
		sede = sedeService.configuraSede(daSalvare);
 
		// id reale del settore appena salvato
		Tappa perSettori = new Tappa(0, 0, sede.getId_sede(), sede.getNome(), LocalDateTime.now().plusDays(10));
		idSettore = sedeService.getSettoriPerSede(perSettori).get(0).getId_settore();
	}
 
	// creazione completa: l'evento, la tappa e le tariffe vengono persistiti e committati
	@Test
	public void testCreaEventoOK()
			throws EmptyFieldException, EventoException, TariffaNonValidaException, DataPassataException {
 
		Tappa tappa = new Tappa(0, 0, sede.getId_sede(), sede.getNome(), LocalDateTime.now().plusDays(10));
		tappa.getTariffe().add(new Tariffa(0, idSettore, TipologiaBiglietto.STANDARD, 25, 10, 0));
 
		List<Tappa> tappe = new ArrayList<>();
		tappe.add(tappa);
 
		eventoCreato = service.creaEvento("Concerto Vasco", TipologiaEvento.CONCERTO,
				org.getEmail(), "Vasco Rossi", tappe);
 
		// l'evento e tornato con un id generato dal database
		assertNotNull(eventoCreato);
		assertTrue(eventoCreato.getId_evento() > 0);
 
		// verifica che sia stato davvero persistito: lo si ritrova rileggendolo dal database
		List<Evento> trovati = eventoDAO.trovaEventiPerNome("Concerto Vasco");
		boolean presente = false;
		for (Evento e : trovati) {
			if (e.getId_evento() == eventoCreato.getId_evento()) {
				presente = true;
			}
		}
		assertTrue(presente);
	}
 
	@After
	public void pulisci() {
		if (eventoCreato != null) {
			eventoDAO.eliminaEvento(eventoCreato.getId_evento());
		}
		sedeDAO.eliminaSede(sede.getId_sede());
		organizzatoreDAO.deleteOrganizzatore(org.getEmail());
	}

}
