package it.unipv.posw.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unipv.posw.model.entities.Artista;
import it.unipv.posw.model.entities.Evento;
import it.unipv.posw.model.entities.Tappa;
import it.unipv.posw.model.entities.Tariffa;
import it.unipv.posw.model.enums.TipologiaEvento;
import it.unipv.posw.model.exception.DataPassataException;
import it.unipv.posw.model.exception.DataTappaDuplicataException;
import it.unipv.posw.model.exception.EmptyFieldException;
import it.unipv.posw.model.exception.EventoException;
import it.unipv.posw.model.exception.EventoSalvataggioException;
import it.unipv.posw.model.exception.EventoSenzaTappeException;
import it.unipv.posw.model.exception.TariffaNonValidaException;
import it.unipv.posw.model.persistence.DBConnection;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.persistence.dao.interfaces.IArtistaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IEventoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.IPostoDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITappaDAO;
import it.unipv.posw.model.persistence.dao.interfaces.ITariffaDAO;

/**
 * Classe del model che orchestra la creazione di un evento e di tutte le entità collegate.
 * Effettua la validazione di dominio all'interno del metodo {@link #validaEvento(String, TipologiaEvento, String, String, List)}.
 * Coordina le operazioni sui DAO, garantendo la consistenza dei dati e l'esecuzione atomica delle transazioni.
 * @author gpelle
 */

public class CreaEventoService {
	
	/**
	 * Crea un evento a partire dai dati forniti, dopo averne validato la consistenza, e
	 * lo persiste insieme alle sue tappe in modo transazionale.
	 * 
	 * @param nome Il nome dell'evento o del tour
	 * @param tipo Tipologia dell'evento (Concerto o Teatro)
	 * @param emailOrganizzatore Email dell'organizzatore che pubblica l'evento
	 * @param nomeArtista Nome d'arte del performer o della band 
	 * @param tappe Tappe dell'evento
	 * @return L'evento creato e persistito.
	 * @throws EmptyFieldException Se un campo obbligatorio è assente.
	 * @throws EventoException Se l'evento è privo di tappe o la persistenza fallisce.
	 * @throws TariffaNonValidaException Se una tariffa di una tappa non è valida.
	 * @throws DataPassataException Se una tappa ha data non futura.
	 */
	public Evento creaEvento(String nome, TipologiaEvento tipo, String emailOrganizzatore,
			String nomeArtista, List<Tappa> tappe)
			throws EmptyFieldException, EventoException,
			TariffaNonValidaException, DataPassataException {

		
		validaEvento(nome, tipo, emailOrganizzatore, nomeArtista, tappe);

		int idArtista = ritornaIdArtista(nomeArtista);
		Evento evento = new Evento(0, nome, tipo, emailOrganizzatore, idArtista);

		return salvaInTransazione(evento, tappe);
	}

	/**
	 * Valida i dati di un evento: presenza dei campi obbligatori, esistenza di almeno una tappa 
	 * e validità di ciascuna tappa. Metodo pubblico per consentire unit test isolati dalla persistenza.
	 * @param nome Il nome dell'evento o del tour
	 * @param tipo Tipologia dell'evento (Concerto o Teatro)
	 * @param emailOrganizzatore Email dell'organizzatore che pubblica l'evento
	 * @param nomeArtista Nome d'arte del performer o della band 
	 * @param tappe Tappe dell'evento
	 * @throws EmptyFieldException Se un campo obbligatorio è assente.
	 * @throws EventoException Se l'evento è privo di tappe ({@link EventoSenzaTappeException}.
	 * @throws TariffaNonValidaException Se una tariffa non è valida.
	 * @throws DataPassataException Se una tappa ha data non futura.
	 */
	public void validaEvento(String nome, TipologiaEvento tipo, String emailOrganizzatore,
			String nomeArtista, List<Tappa> tappe)
			throws EmptyFieldException, EventoException,
			TariffaNonValidaException, DataPassataException {
 
		if (nome == null || nome.trim().isEmpty() || nomeArtista == null || nomeArtista.trim().isEmpty()
				|| emailOrganizzatore == null || emailOrganizzatore.trim().isEmpty() || tipo == null) {
			throw new EmptyFieldException();
		}
 
		if (tappe == null || tappe.isEmpty()) {
			throw new EventoSenzaTappeException();
		}
 
		for (Tappa tappa : tappe) {
			validaTappa(tappa);
		}
	}
	
	/**
	 * Valida una singola tappa: presenza di sede e data, data futura e tariffe valide.
	 * @param tappa La {@link Tappa} da validare
	 * @throws EmptyFieldException Se sede o data sono assenti.
	 * @throws TariffaNonValidaException Se le tariffe della tappa non sono valide.
	 * @throws DataPassataException Se la data della tappa non è futura.
	 */
	public void validaTappa(Tappa tappa) throws EmptyFieldException, TariffaNonValidaException, DataPassataException {
		if (tappa == null || tappa.getId_sede() <= 0 || tappa.getData_ora() == null) {
			throw new EmptyFieldException();
		}
		if (tappa.getData_ora().isBefore(LocalDateTime.now())) {
	        throw new DataPassataException();
	    }
		
		validaTariffe(tappa.getTariffe());
	}

	/**
	 * Valida le tariffe di una tappa: almeno una tariffa, tipologia presente, prezzo e quantità positivi.
	 * @param tariffe La lista di tariffe da validare
	 * @throws TariffaNonValidaException Se una regola di validità sulle tariffe è violata
	 */
	private void validaTariffe(List<Tariffa> tariffe) throws TariffaNonValidaException {
		if (tariffe == null || tariffe.isEmpty()) {
			throw new TariffaNonValidaException("Configura almeno una tariffa per la tappa.");
		}
		for (Tariffa t : tariffe) {
			if (t.getTipob() == null) {
				throw new TariffaNonValidaException("Seleziona la tipologia di biglietto per ogni settore.");
			}
			if (t.getPrezzo() <= 0) {
				throw new TariffaNonValidaException("Il prezzo deve essere maggiore di 0.");
			}
			if (t.getQuantita_massima() <= 0) {
				throw new TariffaNonValidaException("La quantità deve essere maggiore di 0.");
			}
		}
	}

	/**
	 * Verifica che la data di una nuova tappa non coincida con quella di tappe già
	 * presenti per lo stesso evento
	 * @param nuova La {@link Tappa} da analizzare
	 * @param esistenti Le tappe già configurate
	 * @throws DataTappaDuplicataException Se esiste già una tappa nella stessa data.
	 */
	public void validaDataNonDuplicata(Tappa nuova, List<Tappa> esistenti) throws DataTappaDuplicataException {
		if (nuova == null || esistenti == null) {
			return;
		}
		for (Tappa t : esistenti) {
			if (stessaData(t, nuova)) {
				throw new DataTappaDuplicataException();
			}
		}
	}

	/**
	 * Confronta due tappe sulla sola data, ignorando l'orario.
	 * @param a Prima {@link Tappa}
	 * @param b Seconda {@link Tappa}
	 * @return true se le due tappe cadono nello stesso giorno.
	 */
	private boolean stessaData(Tappa a, Tappa b) {
		if (a.getData_ora() == null || b.getData_ora() == null) {
			return false;
		}
		return a.getData_ora().toLocalDate().equals(b.getData_ora().toLocalDate());
	}
	
	/**
	 * Restituisce dal nome testuale l'id dell'artista se esiste un'artista con quel nome, 
	 * altrimenti lo crea e ne restituisce l'id generato.
	 * @param nomeArtista Nome d'arte da risolvere
	 * @return L'id dell'artista esistente o appena creato
	 */
	private int ritornaIdArtista(String nomeArtista) {
		String nome = nomeArtista.trim();
		IArtistaDAO artistaDAO = MYSQLDAOFactory.getInstance().getArtistaDAO();
 
		List<Artista> trovati = artistaDAO.trovaArtisti(nome);
		for (Artista a : trovati) {
			if (a.getNome_darte().equalsIgnoreCase(nome)) {
				return a.getId_artista();
			}
		}
		return artistaDAO.salvaArtista(new Artista(0, nome));
	}

	/**
	 * Persiste evento, tappe, tariffe e biglietti in un'unica transazione: in caso di
	 * errore esegue il rollback e solleva un'eccezione di dominio.
	 * @param evento L'{@link Evento} da salvare
	 * @param tappe Le tappe dell'evento
	 * @return L'evento persistito con id valorizzato e tappe associate.
	 * @throws EventoException Se la persistenza fallisce ({@link EventoSalvataggioException}
	 */
	// connessione condivisa + transazione
	private Evento salvaInTransazione(Evento evento, List<Tappa> tappe) throws EventoException {
		IEventoDAO eventoDAO = MYSQLDAOFactory.getInstance().getEventoDAO();
		ITappaDAO tappaDAO = MYSQLDAOFactory.getInstance().getTappaDAO();
		ITariffaDAO tariffaDAO = MYSQLDAOFactory.getInstance().getTariffaDAO();
		IPostoDAO postoDAO = MYSQLDAOFactory.getInstance().getPostoDAO();

		Connection c = null;
		try {
			c = DBConnection.getInstance().startConnection();
			c.setAutoCommit(false);

			int idEvento = eventoDAO.salvaEvento(evento, c);

			for (Tappa tappa : tappe) {
				int idTappa = tappaDAO.salvaTappa(tappa, idEvento, c);

				List<Tariffa> tariffe = tappa.getTariffe();
				Map<Integer, List<Integer>> postiPerSettore = caricaPostiPerSettore(tariffe, postoDAO, c);
				tariffaDAO.inserisciTariffa(tariffe, idTappa, idEvento, c, postiPerSettore);
			}

			c.commit();

			Evento salvato = new Evento(idEvento, evento.getNome(), evento.getTipo(),
					evento.getEmail_organizzatore(), evento.getId_artista());
			for (Tappa tappa : tappe) {
				salvato.aggiungiTappa(tappa);
			}
			return salvato;

		} catch (Exception e) {
			DBConnection.getInstance().rollback(c);
			e.printStackTrace();
			throw new EventoSalvataggioException("Errore imprevisto del database durante la creazione dell'evento", e);
		} finally {
			DBConnection.getInstance().setAutoCommit(c, true);
			DBConnection.getInstance().closeConnection(c);
		}
	}

	/**
	 * Costruisce, per i settori numerati coinvolti dalle tariffe, la mappa di 
	 * settore-lista di id posto, usata per associare i biglietti ai posti.
	 * @param tariffe Le tariffe della tappa
	 * @param postoDAO Il DAO dei posti
	 * @param c Connessione transazionale corrente
	 * @return La mappa dei posti per settore (vuota per i soli settori non numerati).
	 * @throws SQLException Se avviene errore di lettura dei posti.
	 */
	private Map<Integer, List<Integer>> caricaPostiPerSettore(List<Tariffa> tariffe, IPostoDAO postoDAO, Connection c) throws SQLException {
		Map<Integer, List<Integer>> postiPerSettore = new HashMap<>();
		for (Tariffa t : tariffe) {
			int idSettore = t.getId_settore();
			if (!postiPerSettore.containsKey(idSettore)) {
				List<Integer> posti = postoDAO.getIdPostiPerSettore(idSettore, c);
				if (!posti.isEmpty()) {
					postiPerSettore.put(idSettore, posti);
				}
			}
		}
		return postiPerSettore;
	}
}