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
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.persistence.dao.SedeDAO;
import it.unipv.posw.model.service.SedeService;

/**
 * @author gpelle
 */

public class SedeTest {

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


	// configurazione corretta: la sede viene salvata 
	@Test
	public void testSedeOK()
			throws EmptyFieldException, SedeException, SettoreNonValidoException {
		Sede sede = creaSedeValida("Arena Test", "Via Roma 1");
		Sede salvata = service.configuraSede(sede);

		if (salvata != null) {
			sedeDAO.eliminaSede(salvata.getId_sede());
		}

		assertTrue(true);
	}

	// campi obbligatori vuoti
	@Test
	public void testSedeKO1()
			throws SedeException, SettoreNonValidoException {
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
	public void testSedeKO2()
			throws EmptyFieldException, SedeEsistenteException {
		Sede sede = new Sede();
		sede.setNome("Arena Senza Settori");
		sede.setIndirizzo("Via Sola 3");

		boolean result = true;

		try {
			service.configuraSede(sede);
		} catch (SedeException ex) {
			result = false;
		}

		assertFalse(result);
	}

	// sede già esistente
	@Test
	public void testSedeKO3()
			throws EmptyFieldException, SedeSenzaSettoriException, SettoreNonValidoException {
		Sede prima = creaSedeValida("Arena Doppia", "Via Milano 2");
		Sede salvata = null;

		boolean result = true;

		try {
			salvata = service.configuraSede(prima);

			Sede duplicata = creaSedeValida("Arena Doppia", "Via Milano 2");
			service.configuraSede(duplicata);
		} catch (SedeException ex) {
			result = false;
		} finally {
			if (salvata != null) {
				sedeDAO.eliminaSede(salvata.getId_sede());
			}
		}
		assertFalse(result);
	}
}