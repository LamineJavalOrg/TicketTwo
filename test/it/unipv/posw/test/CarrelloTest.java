package it.unipv.posw.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.enums.TipologiaBiglietto;
import it.unipv.posw.model.exception.IndisponibilitàException;
import it.unipv.posw.model.exception.SuperamentoLimiteBigliettiException;
import it.unipv.posw.model.service.CarrelloService;

/**
 * @author rkomi-dev
 */

public class CarrelloTest {
	
	private CarrelloService service;
	
	// dati di test inseriti tramite uno script popola.sql nel db
	private final int ID_EVENTO = 99;
	private final int ID_TAPPA = 999;
	private final int ID_SETTORE = 9;
	private final TipologiaBiglietto TIPO_BIGLIETTO = TipologiaBiglietto.STANDARD; 
	
	@Before
    public void iniTest() {
		
        service = new CarrelloService();
        Carrello.getInstance().svuota();
         
    }
	
	@Test
    public void testCarrelloOK() throws IndisponibilitàException, SuperamentoLimiteBigliettiException {
        int quantita = 2;
        service.aggiungiAlCarrello(ID_EVENTO, ID_TAPPA, ID_SETTORE, TIPO_BIGLIETTO, quantita);
        assertEquals(quantita, Carrello.getInstance().getItems().size());
    }
	
	@Test
	public void testCarrelloKO1() throws IndisponibilitàException {
	   boolean result = true;
	   int quantitaOltreLimite = 5; // Supera il limite MAX_BIGLIETTI = 4 
	        
	   try {
	        service.aggiungiAlCarrello(ID_EVENTO, ID_TAPPA, ID_SETTORE, TIPO_BIGLIETTO, quantitaOltreLimite);
	   } catch (SuperamentoLimiteBigliettiException ex) {
	      result = false; 
	   }
	   assertFalse(result);
	}
	   
	@Test
    public void testCarrelloKO2() throws SuperamentoLimiteBigliettiException {
        boolean result = true;
        int quantitaEccessiva = 10; // inseriti 5 nel db
        
        try {
            service.aggiungiAlCarrello(ID_EVENTO, ID_TAPPA, ID_SETTORE, TIPO_BIGLIETTO, quantitaEccessiva);
        } catch (IndisponibilitàException ex) {
            result = false; 
        } 
        
        assertFalse(result);
    }

    @After
    public void pulisciCarrello() {
        Carrello.getInstance().svuota();
    }

}
