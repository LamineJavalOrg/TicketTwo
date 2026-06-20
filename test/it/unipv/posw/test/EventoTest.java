package it.unipv.posw.test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.EventoSenzaTappeException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.service.CreaEventoService;

/**
 * @author gpelle
 */

public class EventoTest {

	private CreaEventoService service;

	@Before
	public void iniTest() {
		service = new CreaEventoService();
	}

	// metodo di aiuto che costruisce una tariffa valida
	private Tariffa creaTariffaValida() {
		return new Tariffa(0, 1, TipologiaBiglietto.STANDARD, 25, 100, 0);
	}

	// metodo di aiuto che costruisce una tappa valida con una tariffa e data futura
	private Tappa creaTappaValida() {
		Tappa tappa = new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().plusDays(10));
		tappa.getTariffe().add(creaTariffaValida());
		return tappa;
	}

	// metodo di aiuto che costruisce una lista con una tappa valida
	private List<Tappa> creaTappeValide() {
		List<Tappa> tappe = new ArrayList<>();
		tappe.add(creaTappaValida());
		return tappe;
	}


	// evento valido
	@Test
	public void testEventoOK()
			throws EmptyFieldException, EventoException, TariffaNonValidaException, DataPassataException {
		service.validaEvento("Tour Estate", TipologiaEvento.CONCERTO, "marco@rossi.it", "Vasco Rossi", creaTappeValide());
		assertTrue(true);
	}

	// nome evento vuoto
	@Test
	public void testEventoKO1()
			throws EventoException, TariffaNonValidaException, DataPassataException {
		boolean result = true;

		try {
			service.validaEvento(" ", TipologiaEvento.CONCERTO, "marco@rossi.it", "Vasco Rossi", creaTappeValide());
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// nome artista vuoto
	@Test
	public void testEventoKO2()
			throws EventoException, TariffaNonValidaException, DataPassataException {
		boolean result = true;

		try {
			service.validaEvento("Tour Estate", TipologiaEvento.CONCERTO, "marco@rossi.it", " ", creaTappeValide());
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// email organizzatore vuota
	@Test
	public void testEventoKO3()
			throws EventoException, TariffaNonValidaException, DataPassataException {
		boolean result = true;

		try {
			service.validaEvento("Tour Estate", TipologiaEvento.CONCERTO, " ", "Vasco Rossi", creaTappeValide());
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// tipologia evento mancante
	@Test
	public void testEventoKO4()
			throws EventoException, TariffaNonValidaException, DataPassataException {
		boolean result = true;

		try {
			service.validaEvento("Tour Estate", null, "marco@rossi.it", "Vasco Rossi", creaTappeValide());
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// nessuna tappa configurata
	@Test
	public void testEventoKO5()
			throws EmptyFieldException, EventoException, TariffaNonValidaException, DataPassataException {
		boolean result = true;

		try {
			service.validaEvento("Tour Estate", TipologiaEvento.CONCERTO, "marco@rossi.it", "Vasco Rossi", new ArrayList<Tappa>());
		} catch (EventoSenzaTappeException ex) {
			result = false;
		}

		assertFalse(result);
	}
}