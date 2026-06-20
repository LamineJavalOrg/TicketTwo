package it.unipv.posw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.persistence.dao.SedeDAO;
import it.unipv.posw.model.service.SedeService;

public class ConfigurasedeintegrationTest {
	private SedeService service;
	private SedeDAO sedeDAO;
	private Sede salvata;
 
	@Before
	public void iniTest() {
		service = new SedeService();
		sedeDAO = new SedeDAO();
		salvata = null;
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
 
 
	// configurazione completa: la sede e i suoi settori vengono persistiti e committati
	@Test
	public void testConfiguraSedeOK() throws EmptyFieldException, SedeException, SettoreNonValidoException {
		Sede sede = creaSedeValida("Forum Milano", "Via Vittorio 6");
 
		salvata = service.configuraSede(sede);
 
		assertNotNull(salvata);
		assertTrue(salvata.getId_sede() > 0);
 
		assertTrue(sedeDAO.isSedeEsistente("Forum Milano", "Via Vittorio 6"));
	}
 
	// sede gia esistente
	@Test
	public void testConfiguraSedeKO1()
			throws EmptyFieldException, SedeException, SettoreNonValidoException {
 
		salvata = service.configuraSede(creaSedeValida("Forum Milano", "Via Vittorio 6"));
 
		boolean result = true;
 
		try {
			service.configuraSede(creaSedeValida("Forum Milano", "Via Vittorio 6"));
		} catch (SedeException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
 
	@After
	public void pulisci() {
		if (salvata != null) {
			sedeDAO.eliminaSede(salvata.getId_sede());
		}
	}
}
