package it.unipv.posw.test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.Organizzatore;
import it.unipv.posw.model.exception.DataNascitaException;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.persistence.DAO.ClienteDAO;
import it.unipv.posw.model.persistence.DAO.OrganizzatoreDAO;
import it.unipv.posw.model.service.RegistrazioneService;

/**
 * @author rkomi-dev
 * @author gpelle
 */

public class RegistrazioneTest {

    private RegistrazioneService service;
    private ClienteDAO dao;
    private OrganizzatoreDAO daoOrg;
    
    @Before
    public void iniTest() {
        service = new RegistrazioneService();
        dao = new ClienteDAO();
        daoOrg = new OrganizzatoreDAO();
    }
    
    @Test
    public void testRegistrazioneClienteOK() throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
        Cliente c = new Cliente("Luigi", "Bianchi", LocalDate.of(1984, 11, 9), "luigi.bianchi@gmail.com", "prova123");
        
        boolean result = service.registraNuovoCliente(c);
        
        dao.deleteCliente(c.getEmail());
        assertTrue(result);
    }
    
    @Test
    public void testRegistrazioneClienteKO1() throws WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
        Cliente c = new Cliente("", "Bianchi", LocalDate.of(1984, 11, 9), "", "prova123");

        boolean haErrore = true;
        
        try {
            service.registraNuovoCliente(c);
        } catch (EmptyFieldException ex) {
            haErrore = false; 
        }
        
        assertFalse(haErrore);
    }
    
    @Test
    public void testRegistrazioneClienteKO2() throws EmptyFieldException, EmailEsistenteException, DataNascitaException {
        Cliente c = new Cliente("Luigi", "Bianchi", LocalDate.of(1984, 11, 9), "luigi.bianchigmail.com", "prova123");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoCliente(c);
        } catch (WrongEmailFormatException ex) {
            haErrore = false;
        }
        
        assertFalse(haErrore);
    }
    
    @Test
    public void testRegistrazioneClienteKO3() throws EmptyFieldException, WrongEmailFormatException, DataNascitaException {
        Cliente c = new Cliente("Luigi", "Bianchi", LocalDate.of(1984, 11, 9), "luigi.bianchi@gmail.com", "prova123");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoCliente(c);

            service.registraNuovoCliente(c);
        } catch (EmailEsistenteException ex) {
            haErrore = false;
        } finally {
            dao.deleteCliente(c.getEmail());
        }
        
        assertFalse(haErrore);
    }
    
    @Test
    public void testRegistrazioneClienteKO4() throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException{
        Cliente c = new Cliente("Luigi", "Bianchi", LocalDate.of(2027, 11, 9), "luigi.bianchi@gmail.com", "prova123");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoCliente(c);

        } catch (DataNascitaException ex) {
            haErrore = false;
        } finally {
            dao.deleteCliente(c.getEmail());
        }
        
        assertFalse(haErrore);
    }
    
    
    
    
    @Test
    public void testRegistrazioneOrganizzatoreOK() throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
        Organizzatore o = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20), "mario.rossi@eventi.it", "prova123", "Eventi SRL");
        
        boolean result = service.registraNuovoOrganizzatore(o);
        
        daoOrg.deleteOrganizzatore(o.getEmail());
        assertTrue(result);
    }
    
    // campi obbligatori vuoti
    @Test
    public void testRegistrazioneOrganizzatoreKO1() throws WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
        Organizzatore o = new Organizzatore("", "Rossi", LocalDate.of(1980, 5, 20), "", "prova123", "Eventi SRL");

        boolean haErrore = true;
        
        try {
            service.registraNuovoOrganizzatore(o);
        } catch (EmptyFieldException ex) {
            haErrore = false;
        }
        
        assertFalse(haErrore);
    }
    
    // email senza '@'
    @Test
    public void testRegistrazioneOrganizzatoreKO2() throws EmptyFieldException, EmailEsistenteException, DataNascitaException {
        Organizzatore o = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20), "mario.rossieventi.it", "prova123", "Eventi SRL");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoOrganizzatore(o);
        } catch (WrongEmailFormatException ex) {
            haErrore = false;
        }
        
        assertFalse(haErrore);
    }
    
    // email già esistente
    @Test
    public void testRegistrazioneOrganizzatoreKO3() throws EmptyFieldException, WrongEmailFormatException, DataNascitaException {
        Organizzatore o = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20), "mario.rossi@eventi.it", "prova123", "Eventi SRL");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoOrganizzatore(o);

            service.registraNuovoOrganizzatore(o);
        } catch (EmailEsistenteException ex) {
            haErrore = false;
        } finally {
            daoOrg.deleteOrganizzatore(o.getEmail());
        }
        
        assertFalse(haErrore);
    }
    
    // data di nascita futura
    @Test
    public void testRegistrazioneOrganizzatoreKO4() throws EmptyFieldException, WrongEmailFormatException, EmailEsistenteException {
        Organizzatore o = new Organizzatore("Mario", "Rossi", LocalDate.of(2027, 5, 20), "mario.rossi@eventi.it", "prova123", "Eventi SRL");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoOrganizzatore(o);
        } catch (DataNascitaException ex) {
            haErrore = false;
        } finally {
            daoOrg.deleteOrganizzatore(o.getEmail());
        }
        
        assertFalse(haErrore);
    }
    
    // nome_organizzazione vuoto (validazione specifica dell'organizzatore)
    @Test
    public void testRegistrazioneOrganizzatoreKO5() throws WrongEmailFormatException, EmailEsistenteException, DataNascitaException {
        Organizzatore o = new Organizzatore("Mario", "Rossi", LocalDate.of(1980, 5, 20), "mario.rossi@eventi.it", "prova123", "");
        
        boolean haErrore = true;
        
        try {
            service.registraNuovoOrganizzatore(o);
        } catch (EmptyFieldException ex) {
            haErrore = false;
        }
        
        assertFalse(haErrore);
    }
}