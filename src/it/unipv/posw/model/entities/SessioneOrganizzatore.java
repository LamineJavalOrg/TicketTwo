package it.unipv.posw.model.entities;

/**
 * Classe che mantiene lo stato della sessione dell'organizzatore corrente.
 * Implementa Pattern Singleton, l'accesso avviene tramite {@link #getInstance()} che garantisce
 * un'unica istanza condivisa.
 * @see Organizzatore
 * @author gpelle
 */
public class SessioneOrganizzatore {
	private static SessioneOrganizzatore instance;
    private Organizzatore organizzatoreLoggato;

    /**
     * Costruttore privato per impedire l'istanziazione diretta dall'esterno
     * e garantire l'integrità del Pattern Singleton
     */
	private SessioneOrganizzatore() {} 
    
	/**
	 * Restituisce l'unica istanza disponibile della sessione.
	 * Se l'istanza non esiste ancora, viene creata.
	 * @return L'istanza corrente di {@link SessioneOrganizzatore}
	 */
	public static SessioneOrganizzatore getInstance() {
        if (instance == null) {
        	instance = new SessioneOrganizzatore();
        }
        return instance;
    }

	/**
	 * Effettua il login dell'organizzatore memorizzandolo nella sessione corrente.
	 * @param o L'oggetto {@link Organizzatore} che sta effettuando l'accesso.
	 */
    public void login(Organizzatore o) {
    	this.organizzatoreLoggato = o; 
    }
    
    /**
     * Effettua il logout dell'organizzatore corrente azzerandone il riferimento.
     */
    public void logout() { 
    	this.organizzatoreLoggato = null;
    }
    
    /**
     * Verifica se è presente un organizzatore autenticato nella sessione corrente.
     * @return true se un'organizzatore è loggato, false altrimenti.
     */
    public boolean isLoggato() {
    	return organizzatoreLoggato != null; 
    }
    
    /**
     * Svuota la sessione azzerando il riferimento all'organizzatore loggato
     */
    public void svuotaSessione() {
    	this.organizzatoreLoggato = null; 
    }
    
    /**
     * Restituisce l'organizzatore attualmente autenticato.
     * @return L'organizzatore loggato, oppure null se nessuno è autenticato.
     */
    public Organizzatore getOrganizzatoreLoggato() { 
    	return organizzatoreLoggato; 
    }
    
    /**
     * Imposta l'organizzatore autenticato nella sessione corrente.
     * @param organizzatoreLoggato l'organizzatore da registrare come autenticato.
     */
	public void setOrganizzatoreLoggato(Organizzatore organizzatoreLoggato) {
		this.organizzatoreLoggato = organizzatoreLoggato;
	}
}
