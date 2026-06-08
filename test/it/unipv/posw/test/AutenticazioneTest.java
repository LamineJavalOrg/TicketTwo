package it.unipv.posw.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.persistence.DAO.ClienteDAO;
import it.unipv.posw.model.service.AutenticazioneService;

/**
 * @author rkomi-dev
 */

public class AutenticazioneTest {
	
	private AutenticazioneService service;
	private ClienteDAO dao;
	private Cliente c;
	
	@Before
	public void iniTest() {
		service = new AutenticazioneService();
		dao = new ClienteDAO();
		c = new Cliente("Luigi", "Bianchi", LocalDate.of(1984, 11, 9), "luigi.bianchi@gmail.com", "prova123" );
		
		dao.salvaCliente(c);
	}

	@Test
	public void testAutenticazioneClienteOK() throws CredenzialiErrateException {
		
		service.loginCliente(c.getEmail(), c.getPassword());

		assertTrue(true);
	}
	
	// il cliente non esiste
	@Test
	public void testAutenticazioneClienteKO1() {
		boolean result = true;
		
		try {
			service.loginCliente("email@inesistente.it", "qualsiasi");
		}catch (CredenzialiErrateException ex) {
			result = false;
		}
		
		assertFalse(result);
	}
	
	//credenziale errata
	@Test
	public void testAutenticazioneClienteKO2() {
		boolean result = true;
		
		try {
			service.loginCliente(c.getEmail(), "password errata");
		} catch (CredenzialiErrateException ex) {
			result = false;
		}
		
		assertFalse(result);
	}
	
	
	@After
	public void deleteCliente() {
		dao.deleteCliente(c.getEmail());
	}

}
