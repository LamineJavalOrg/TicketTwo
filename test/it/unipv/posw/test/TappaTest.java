package it.unipv.posw.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.DataTappaDuplicataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.service.CreaEventoService;

public class TappaTest {
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
 
 
	// tappa valida
	@Test
	public void testTappaOK1() throws EmptyFieldException, TariffaNonValidaException, DataPassataException {
		service.validaTappa(creaTappaValida());
		assertTrue(true);
	}
	
	// nuova tappa con data diversa da quelle esistenti: nessuna eccezione
	@Test
	public void testTappaOK2() throws DataTappaDuplicataException {
		List<Tappa> esistenti = new ArrayList<>();
		esistenti.add(new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().plusDays(10)));
	
		Tappa nuova = new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().plusDays(20));
	
		service.validaDataNonDuplicata(nuova, esistenti);
		assertTrue(true);
	}
 
	// id sede non valido
	@Test
	public void testTappaKO1() throws TariffaNonValidaException, DataPassataException {
		Tappa tappa = new Tappa(0, 0, 0, "Forum Milano", LocalDateTime.now().plusDays(10));
		tappa.getTariffe().add(creaTariffaValida());
 
		boolean result = true;
 
		try {
			service.validaTappa(tappa);
		} catch (EmptyFieldException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
 
	// data e ora mancanti
	@Test
	public void testTappaKO2() throws TariffaNonValidaException, DataPassataException {
		Tappa tappa = new Tappa(0, 0, 1, "Forum Milano", null);
		tappa.getTariffe().add(creaTariffaValida());
 
		boolean result = true;
 
		try {
			service.validaTappa(tappa);
		} catch (EmptyFieldException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
 
	// data nel passato
	@Test
	public void testTappaKO3() throws EmptyFieldException, TariffaNonValidaException {
		Tappa tappa = new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().minusDays(1));
		tappa.getTariffe().add(creaTariffaValida());
 
		boolean result = true;
 
		try {
			service.validaTappa(tappa);
		} catch (DataPassataException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
  
	// nessuna tariffa configurata
	@Test
	public void testTappaKO4() throws EmptyFieldException, DataPassataException {
		Tappa tappa = new Tappa(0, 0, 1, "Forum Milano", LocalDateTime.now().plusDays(10));
 
		boolean result = true;
 
		try {
			service.validaTappa(tappa);
		} catch (TariffaNonValidaException ex) {
			result = false;
		}
 
		assertFalse(result);
	}
	
	// nuova tappa nello stesso giorno di una esistente (ora diversa)
	@Test
	public void testTappaKO5() {
		LocalDateTime data = LocalDateTime.now().plusDays(10);
	
		List<Tappa> esistenti = new ArrayList<>();
		esistenti.add(new Tappa(0, 0, 1, "Forum Milano", data));
	
		Tappa nuova = new Tappa(0, 0, 1, "Forum Milano", data.plusHours(3));
	
		boolean result = true;
	
		try {
			service.validaDataNonDuplicata(nuova, esistenti);
		} catch (DataTappaDuplicataException ex) {
			result = false;
		}
	
		assertFalse(result);
	}
}
