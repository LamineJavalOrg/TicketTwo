package it.unipv.posw.model.entities;

/**
 * @author gpelle
 */
public class SessioneOrganizzatore {
	private static SessioneOrganizzatore instance;
    private Organizzatore organizzatoreLoggato;

	public SessioneOrganizzatore() {} // Costruttore privato per il Singleton
    
	public static SessioneOrganizzatore getInstance() {
        if (instance == null) {
        	instance = new SessioneOrganizzatore();
        }
        return instance;
    }

    public void login(Organizzatore o) {
    	this.organizzatoreLoggato = o; 
    }
    
    public void logout() { 
    	this.organizzatoreLoggato = null;
    }
    
    public boolean isLoggato() {
    	return organizzatoreLoggato != null; 
    }
    
    public void svuotaSessione() {
    	this.organizzatoreLoggato = null; 
    }
    
    public Organizzatore getOrganizzatoreLoggato() { 
    	return organizzatoreLoggato; 
    }
    

	public void setOrganizzatoreLoggato(Organizzatore organizzatoreLoggato) {
		this.organizzatoreLoggato = organizzatoreLoggato;
	}
    
    

}
