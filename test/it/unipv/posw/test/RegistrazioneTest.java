package it.unipv.posw.test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import it.unipv.posw.model.Cliente;
import it.unipv.posw.model.exception.DataNascitaException;
import it.unipv.posw.model.exception.EmailEsistenteException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.WrongEmailFormatException;
import it.unipv.posw.model.persistence.DAO.ClienteDAO;
import it.unipv.posw.model.service.RegistrazioneService;

/**
 * @author rkomi-dev
 */

public class RegistrazioneTest {

    private RegistrazioneService service;
    private ClienteDAO dao;
    
    @Before
    public void iniTest() {
        service = new RegistrazioneService();
        dao = new ClienteDAO();
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
}