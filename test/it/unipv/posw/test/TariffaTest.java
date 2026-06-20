package it.unipv.posw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.service.CreaEventoService;

public class TariffaTest {
	private CreaEventoService service;
	 
	@Before
	public void iniTest() {
		service = new CreaEventoService();
	}
 
	// metodo di aiuto che crea tappa valida con la tariffa da verificare
	private Tappa creaTappaConTariffa(Tariffa tariffa) {
		Tappa tappa = new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().plusDays(10));
		tappa.getTariffe().add(tariffa);
		return tappa;
	}
 
 
	// tariffa valida
	@Test
	public void testTariffaOK() throws EmptyFieldException, TariffaNonValidaException, DataPassataException {
		Tariffa tariffa = new Tariffa(0, 1, TipologiaBiglietto.STANDARD, 25, 100, 0);
		service.validaTappa(creaTappaConTariffa(tariffa));
		assertTrue(true);
	}
 
	// tipologia biglietto mancante
	@Test
	public void testTariffaKO1() throws EmptyFieldException, DataPassataException {
		Tariffa tariffa = new Tariffa(0, 1, null, 25, 100, 0);
 
		boolean result = true;
 
		try {
			service.validaTappa(creaTappaConTariffa(tariffa));
		} catch (TariffaNonValidaException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
 
	// prezzo non maggiore di 0
	@Test
	public void testTariffaKO2() throws EmptyFieldException, DataPassataException {
		Tariffa tariffa = new Tariffa(0, 1, TipologiaBiglietto.STANDARD, 0, 100, 0);
 
		boolean result = true;
 
		try {
			service.validaTappa(creaTappaConTariffa(tariffa));
		} catch (TariffaNonValidaException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
 
	// quantità massima non maggiore di 0
	@Test
	public void testTariffaKO3() throws EmptyFieldException, DataPassataException {
		Tariffa tariffa = new Tariffa(0, 1, TipologiaBiglietto.STANDARD, 25, 0, 0);
 
		boolean result = true;
 
		try {
			service.validaTappa(creaTappaConTariffa(tariffa));
		} catch (TariffaNonValidaException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
}
