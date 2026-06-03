package it.unipv.posw.model;

public class SessioneCliente {
	
	private static SessioneCliente instance;
    private Cliente utenteLoggato; 
    private String qr_attuale;
    
    /** Classe per tenere traccia del Cliente loggato durante l'uso del software
	 * @author rkomi-dev
	 */
    
    private SessioneCliente() {} // Costruttore privato per il Singleton

    public static SessioneCliente getInstance() {
        if (instance == null) {
            instance = new SessioneCliente();
        }
        return instance;
    }

    // Metodo per il Login: salva il cliente qui dentro
    public void login(Cliente c) {
        this.utenteLoggato = c;
    }

    // Metodo per il Logout: svuota il riferimento
    public void logout() {
        this.utenteLoggato = null;
    }

    public boolean isLoggato() {
        return utenteLoggato != null;
    }
    
    public Cliente getUtenteLoggato() {
        return utenteLoggato;
    }

	public void setUtenteLoggato(Cliente utenteLoggato) {
		this.utenteLoggato = utenteLoggato;
	}
    
	
    public void setQr_attuale(String qr_attuale) {
		this.qr_attuale = qr_attuale;
	}
    
    
	public String getQr_attuale() {
		return qr_attuale;
	}

	public void svuotaSessione() {
    	this.utenteLoggato = null;
    	this.qr_attuale = null;
    }

}
