package it.unipv.posw.model.entities;

/** Classe per tenere traccia del Cliente loggato durante l'uso del software
* @author rkomi-dev
*/

public class SessioneCliente {
	
	private static SessioneCliente instance;
    private Cliente clienteLoggato;
    private String qr_attuale;
    
    private SessioneCliente() {} // Costruttore privato per il Singleton

    public static SessioneCliente getInstance() {
        if (instance == null) {
            instance = new SessioneCliente();
        }
        return instance;
    }

    // Metodo per il Login: salva il cliente qui dentro
    public void login(Cliente c) {
        this.clienteLoggato = c;
    }

    // Metodo per il Logout: svuota il riferimento
    public void logout() {
        this.clienteLoggato = null;
    }

    public boolean isLoggato() {
        return clienteLoggato != null;
    }
    
    public Cliente getClienteLoggato() {
        return clienteLoggato;
    }

	public void setClienteLoggato(Cliente utenteLoggato) {
		this.clienteLoggato = utenteLoggato;
	}
    
	
    public void setQr_attuale(String qr_attuale) {
		this.qr_attuale = qr_attuale;
	}
    
    
	public String getQr_attuale() {
		return qr_attuale;
	}

	public void svuotaSessione() {
    	this.clienteLoggato = null;
    	this.qr_attuale = null;
    }

}
