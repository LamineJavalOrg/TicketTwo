package it.unipv.posw.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.persistence.dao.SedeDAO;
import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class ConfiguraSedeTest {

	private SedeService service;
	private SedeDAO sedeDAO;

	@Before
	public void iniTest() {
		service = new SedeService();
		sedeDAO = new SedeDAO();
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

	// settore numerato valido
	@Test
	public void testCreaSettoreOK1() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.PLATEA, "A", TipologiaPosto.NUMERATO, 5, 10, 0);
		assertTrue(true);
	}

	// settore non numerato valido
	@Test
	public void testCreaSettoreOK2() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.CURVA, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 200);
		assertTrue(true);
	}
	
	// settore parterre valido
	@Test
	public void testCreaSettoreOK3() throws SettoreNonValidoException {
		service.creaSettore(TipologiaSettore.PARTERRE, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 200);
		assertTrue(true);
	}

	// tipo settore mancante
	@Test
	public void testCreaSettoreKO1() {
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
	public void testCreaSettoreKO2() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.PLATEA, "  ", TipologiaPosto.NUMERATO, 5, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// settore numerato con file/colonne <= 0
	@Test
	public void testCreaSettoreKO3() {
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
	public void testCreaSettoreKO4() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.CURVA, "C", TipologiaPosto.NON_NUMERATO, 0, 0, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}
	
	// settore parterre con posto non numerato
	@Test
	public void testCreaSettoreKO5() {
		boolean result = true;

		try {
			service.creaSettore(TipologiaSettore.PARTERRE, "P", TipologiaPosto.NUMERATO, 5, 10, 0);
		} catch (SettoreNonValidoException ex) {
			result = false;
		}

		assertFalse(result);
	}

	


	// configurazione corretta: la sede viene salvata e restituita con id
	@Test
	public void testConfiguraSedeOK()
			throws EmptyFieldException, SedeSenzaSettoriException, SedeEsistenteException, SettoreNonValidoException {
		Sede sede = creaSedeValida("Arena Test", "Via Roma 1");
		Sede salvata = service.configuraSede(sede);

		if (salvata != null) {
			sedeDAO.eliminaSede(salvata.getId_sede());
		}

		assertTrue(true);
	}

	// campi obbligatori vuoti
	@Test
	public void testConfiguraSedeKO1()
			throws SedeSenzaSettoriException, SedeEsistenteException, SettoreNonValidoException {
		Sede sede = creaSedeValida("", "Via Vuota 1");
		boolean result = true;

		try {
			service.configuraSede(sede);
		} catch (EmptyFieldException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// nessun settore configurato
	@Test
	public void testConfiguraSedeKO2()
			throws EmptyFieldException, SedeEsistenteException {
		Sede sede = new Sede();
		sede.setNome("Arena Senza Settori");
		sede.setIndirizzo("Via Sola 3");

		boolean result = true;

		try {
			service.configuraSede(sede);
		} catch (SedeSenzaSettoriException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// sede già esistente
	@Test
	public void testConfiguraSedeKO3()
			throws EmptyFieldException, SedeSenzaSettoriException, SettoreNonValidoException {
		Sede prima = creaSedeValida("Arena Doppia", "Via Milano 2");
		Sede salvata = null;

		boolean result = true;

		try {
			salvata = service.configuraSede(prima);

			Sede duplicata = creaSedeValida("Arena Doppia", "Via Milano 2");
			service.configuraSede(duplicata);
		} catch (SedeEsistenteException ex) {
			result = false;
		} finally {
			if (salvata != null) {
				sedeDAO.eliminaSede(salvata.getId_sede());
			}
		}

		assertFalse(result);
	}
}