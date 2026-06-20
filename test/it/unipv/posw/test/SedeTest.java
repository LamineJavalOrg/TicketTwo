package it.unipv.posw.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class SedeTest {

	private SedeService service;

	@Before
	public void iniTest() {
		service = new SedeService();
	}

	// metodo di aiuto che costruisce una sede valida con un settore
	private Sede creaSedeValida(String nome, String indirizzo) throws SettoreNonValidoException {
		Sede sede = new Sede();
		sede.setNome(nome);
		sede.setIndirizzo(indirizzo);
		Settore platea = service.creaSettore(TipologiaSettore.PLATEA, "A", TipologiaPosto.NUMERATO, 5, 10, 0);
		sede.aggiungiSettore(platea);
		return sede;
	}


	// sede valida
	@Test
	public void testSedeOK() throws EmptyFieldException, SedeException, SettoreNonValidoException {
		service.validaSede(creaSedeValida("Forum Milano", "Via Roma 1"));
		assertTrue(true);
	}

	// nome vuoto
	@Test
	public void testSedeKO1() throws SedeSenzaSettoriException, SettoreNonValidoException {
		Sede sede = creaSedeValida(" ", "Via Roma 1");

		boolean result = true;

		try {
			service.validaSede(sede);
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// indirizzo vuoto
	@Test
	public void testSedeKO2() throws SedeSenzaSettoriException, SettoreNonValidoException {
		Sede sede = creaSedeValida("Forum Milano", " ");

		boolean result = true;

		try {
			service.validaSede(sede);
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// nessun settore configurato
	@Test
	public void testSedeKO3() throws EmptyFieldException {
		Sede sede = new Sede();
		sede.setNome("Forum Milano senza settori");
		sede.setIndirizzo("Via Sola 3");

		boolean result = true;

		try {
			service.validaSede(sede);
		} catch (SedeSenzaSettoriException ex) {
			result = false;
		}

		assertFalse(result);
	}
}