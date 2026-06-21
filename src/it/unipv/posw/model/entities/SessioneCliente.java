package it.unipv.posw.model.entities;

/**
 * Classe preposta alla gestione dello stato della sessione dell'utente corrente.
 * Implementa il pattern Singleton per garantire un unico punto di accesso globale 
 * alle informazioni del cliente autenticato 
 * @see Cliente
 * @author rkomi-dev
 */
public class SessioneCliente {
	
    private static SessioneCliente instance;
    private Cliente clienteLoggato;
    private String qr_attuale;
    
    /**
     * Costruttore privato per impedire l'istanziazione diretta dall'esterno 
     * e garantire l'integrità del pattern Singleton.
     */
    private SessioneCliente() {}

    /**
     * Restituisce l'unica istanza globale della sessione corrente.
     * @return L'istanza univoca di {@link SessioneCliente}.
     */
    public static SessioneCliente getInstance() {
        if (instance == null) {
            instance = new SessioneCliente();
        }
        return instance;
    }

    /**
     * Esegue l'autenticazione del cliente memorizzandone il riferimento nella sessione globale.
     * @param c Il {@link Cliente} che ha effettuato correttamente il logi
     */
    public void login(Cliente c) {
        this.clienteLoggato = c;
    }

    /**
     * Svuota il riferimento del cliente loggato
     */
    public void logout() {
        this.clienteLoggato = null;
    }

    /**
     * Verifica se è presente un utente attualmente autenticato nel sistema.
     * @return {@code true} se un cliente è loggato, {@code false} altrimenti
     */
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

    /**
     * Ripristina completamente lo stato iniziale della sessione,
     * invalidando sia le informazioni del cliente che i codici transazionali (QR)
     */
    public void svuotaSessione() {
        this.clienteLoggato = null;
        this.qr_attuale = null;
    }
}