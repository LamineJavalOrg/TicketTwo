package it.unipv.posw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.service.SedeService;



public class SettoreTest {
	private SedeService service;

	@Before
	public void iniTest() {
		service = new SedeService();
	}


	// settore numerato valido
	@Test
	public void testSettoreOK1() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.PLATEA, "A", TipologiaPosto.NUMERATO, 5, 10, 0);
		assertTrue(true);
	}

	// settore non numerato valido
	@Test
	public void testSettoreOK2() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.CURVA, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 200);
		assertTrue(true);
	}
	
	// settore parterre valido
	@Test
	public void testSettoreOK3() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.PARTERRE, "P", TipologiaPosto.NON_NUMERATO, 0, 0, 200);
		assertTrue(true);
	}

	// tipo settore mancante
	@Test
	public void testSettoreKO1() {
		boolean result = true;

		try {
			service.creaSettore(null, "A", TipologiaPosto.NUMERATO, 5, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// prefisso vuoto
	@Test
	public void testSettoreKO2() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.PLATEA, " ", TipologiaPosto.NUMERATO, 5, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// settore numerato con file/colonne <= 0
	@Test
	public void testSettoreKO3() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.PLATEA, "A", TipologiaPosto.NUMERATO, 0, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// settore non numerato con capienza <= 0
	@Test
	public void testSettoreKO4() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.CURVA, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}
	
	// settore parterre con posto numerato
	@Test
	public void testSettoreKO5() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.PARTERRE, "P", TipologiaPosto.NUMERATO, 5, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}
}
