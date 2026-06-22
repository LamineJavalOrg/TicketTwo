package it.unipv.posw.model.service;

import java.sql.Connection;
import java.util.List;

import it.unipv.posw.model.entities.Sede;
import it.unipv.posw.model.entities.Settore;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.enums.TipologiaPosto;
import it.unipv.posw.model.enums.TipologiaSettore;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.SedeEsistenteException;
import it.unipv.posw.model.exception.SedeException;
import it.unipv.posw.model.exception.SedeSalvataggioException;
import it.unipv.posw.model.exception.SedeSenzaSettoriException;
import it.unipv.posw.model.exception.SettoreNonValidoException;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISedeDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ISettoreDAO;

/**
 * Classe del model che orchestra la configurazione e la persistenza delle sedi.
 * Delega la creazione dei settori ai factory method di {@link Settore} ed effettua la validazione anagrafica
 * della sede.
 * Coordina le operazioni sui DAO, garantendo la consistenza dei dati e l'esecuzione atomica delle transazioni.
 * @author gpelle
 */

public class SedeService {
	/**
	 * Crea un settore delegando al factory method appropriato in base alla modalità dei posti 
	 * (numerato o non numerato).
	 * @param nome Tipo di settore
	 * @param prefisso Prefisso identificativo
	 * @param tipoPosto Modalità dei posti (numerato/non numerato)
	 * @param numFile Numero di file (per i settori numerati)
	 * @param numColonne Numero di colonne (per i settori non numerati)
	 * @param capienza Capienza per i settori non numerati
	 * @return Il settore creato.
	 * @throws SettoreNonValidoException Se i dati del settore non sono validi
	 */
	public Settore creaSettore(TipologiaSettore nome, String prefisso, TipologiaPosto tipoPosto,
			int numFile, int numColonne, int capienza) throws SettoreNonValidoException {
 
		if (tipoPosto == TipologiaPosto.NUMERATO) {
			return Settore.creaNumerato(nome, prefisso, numFile, numColonne);
		}
		return Settore.creaNonNumerato(nome, prefisso, capienza);
	}
	
	/**
	 * Valida tramite {@link #validaSede(Sede)} e persiste in modo transazionale una sede completa di settori e,
	 * per i settori numerati, dei relativi posti.
	 * @param sede La sede da configurare e salvare
	 * @return La sede salvata, con id valorizzato.
	 * @throws EmptyFieldException Se nome o indirizzo sono assenti
	 * @throws SedeException Se la sede è priva di settori {@link SedeSenzaSettoriException}, 
	 * esiste già {@link SedeEsistenteException} o la persistenza fallisce {@link SedeSalvataggioException}.
	 */
	public Sede configuraSede(Sede sede) throws EmptyFieldException, SedeException {
		validaSede(sede);
		
		ISedeDAO sedeDAO = MYSQLDAOFactory.getInstance().getSedeDAO();
		ISettoreDAO settoreDAO = MYSQLDAOFactory.getInstance().getSettoreDAO();
		IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();
		
        if (sedeDAO.isSedeEsistente(sede.getNome(), sede.getIndirizzo())) {
        	throw new SedeEsistenteException();
        }
        
        
        // connessione condivisa + transazione
        Connection c = null;
        try {
        	c = DBConnection.getInstance().startConnection();
        	c.setAutoCommit(false);
        	     		
        	Sede sedeSalvata = sedeDAO.salvaSede(sede, c);
        		
        	for (Settore s : sede.getSettori()) {
        		s.setId_sede(sedeSalvata.getId_sede()); 
                settoreDAO.salvaSettore(s, c);
                    
                if (s.getTipo() == TipologiaPosto.NUMERATO) {
                  	postoDAO.salvaPostiPerSettore(s, c);
                }
            }
        	c.commit();
            return sedeSalvata;
            
        } catch (Exception e) {
        	DBConnection.getInstance().rollback(c);
			e.printStackTrace();
			throw new SedeSalvataggioException("Errore imprevisto del database durante il salvataggio della sede.", e);
		} finally {
			DBConnection.getInstance().setAutoCommit(c, true);
			DBConnection.getInstance().closeConnection(c);
		}
	}
	
	/**
	 * Valida i dati anagrafici della sede: presenza di nome e indirizzo ed esistenza di almeno un settore. 
	 * Metodo pubblico per consentire unit test isolati dalla persistenza.
	 * @param sede La sede da validare
	 * @throws EmptyFieldException Se nome o indirizzo sono assenti.
	 * @throws SedeSenzaSettoriException Se la sede non possiede settori.
	 */
	public void validaSede(Sede sede) throws EmptyFieldException, SedeSenzaSettoriException {
		if (sede.getNome() == null || sede.getNome().trim().isEmpty()
				|| sede.getIndirizzo() == null || sede.getIndirizzo().trim().isEmpty()) {
			throw new EmptyFieldException();
		}
		if (!sede.possiedeSettori()) {
			throw new SedeSenzaSettoriException();
		}
	}
    
	/**
	 * Restituisce tutte le sedi disponibili.
	 * @return La lista di tutte le sedi.
	 */
    public List<Sede> getTutteLeSedi() {
        return MYSQLDAOFactory.getInstance().getSedeDAO().getTutteLeSedi();
    }
    
    /**
     * Recupera l'elenco di tutti i settori associati alla sede di una specifica tappa di un evento.
     * @param tappa L'oggetto {@link Tappa} da cui estrarre l'identificativo della sede di riferimento. 
     * @return La lista di entità {@link Settore} appartenenti a quella specifica sede.
     */
    public List<Settore> getSettoriPerSede(Tappa tappa) {
		return MYSQLDAOFactory.getInstance().getSettoreDAO().getSettoriDaSede(tappa.getId_sede());
	}	
}
