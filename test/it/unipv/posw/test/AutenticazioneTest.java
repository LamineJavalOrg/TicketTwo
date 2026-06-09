package it.unipv.posw.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.CredenzialiErrateException;
import it.unipv.posw.model.persistence.dao.ClienteDAO;
import it.unipv.posw.model.persistence.dao.OrganizzatoreDAO;
import it.unipv.posw.model.service.AutenticazioneService;

/**
 * @author rkomi-dev
 * @author gpelle
 */

public class AutenticazioneTest {
	
	private AutenticazioneService service;
	private ClienteDAO dao;
	private Cliente c;
	
	private OrganizzatoreDAO daoOrg;
	private Organizzatore o;
	
	@Before
	public void iniTest() {
		service = new AutenticazioneService();
		dao = new ClienteDAO();
		daoOrg = new OrganizzatoreDAO();
		
		c = new Cliente("Luigi", "Bianchi", LocalDate.of(1984, 11, 9), "luigi.bianchi@gmail.com", "prova123" );
		dao.salvaCliente(c);
		
		o = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20), "mario.rossi@eventi.it", "prova123", "VivaConcerti");
		daoOrg.salvaOrganizzatore(o);
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
	
	
	
	
	
	@Test
	public void testAutenticazioneOrganizzatoreOK() throws CredenzialiErrateException {
		service.loginOrganizzatore(o.getEmail(), o.getPassword());
		
		assertTrue(true);
	}
	
	// organizzatore inesistente
	@Test
	public void testAutenticazioneOrganizzatoreKO1() {
		boolean result = true;
		
		try {
			service.loginOrganizzatore("email@inesistente.com", "qualsiasi");
		} catch (CredenzialiErrateException ex) {
			result = false;
		}
		
		assertFalse(result);
	}
	
	// password organizzatore errata
	@Test
	public void testAutenticazioneOrganizzatoreKO2() {
		boolean result = true;
		
		try {
			service.loginOrganizzatore(o.getEmail(), "password errata");
		} catch (CredenzialiErrateException ex) {
			result = false;
		}
		
		assertFalse(result);
	}
	
	
	@After
	public void deleteCliente() {
		dao.deleteCliente(c.getEmail());
		daoOrg.deleteOrganizzatore(o.getEmail());
	}
}
