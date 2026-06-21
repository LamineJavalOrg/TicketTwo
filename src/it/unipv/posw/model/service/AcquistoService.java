package it.unipv.posw.model.service;

import it.unipv.posw.model.entities.Biglietto;
import it.unipv.posw.model.entities.Carrello;
import it.unipv.posw.model.entities.SessioneCliente;
import it.unipv.posw.model.persistence.MYSQLDAOFactory;
import it.unipv.posw.model.service.payment.IPagamentoAdapter;

/** Classe del model che gestisce l'acquisto
 * @author rkomi-dev
 */

public class AcquistoService {
	
	private QRService qrservice;
	
	public AcquistoService() {
	}
	
	/** Metodo che finalizza l'acquisto
	 * Si assume che la validazione del pagamento venga fatta da un servizio esterno per cui 
	 * l'acquisto va sempre a buon fine
	 * @see QRService
	 * @see IPagamentoAdapter
	 * @param metodo Metodo di pagamento che si vuole utilizzare
	 */
	
	public void Acquista(IPagamentoAdapter metodo) {
		
		metodo.Paga(Carrello.getInstance().getTotale());
		
		qrservice = new QRService();
		
		qrservice.Genera();
		SessioneCliente.getInstance().setQr_attuale(qrservice.getCodice());
		
		for(Biglietto b : Carrello.getInstance().getItems()) {
			
			String email = SessioneCliente.getInstance().getClienteLoggato().getEmail();
			String nome = SessioneCliente.getInstance().getClienteLoggato().getNome();
			String cognome = SessioneCliente.getInstance().getClienteLoggato().getCognome();
			String qr = SessioneCliente.getInstance().getQr_attuale();
			
			MYSQLDAOFactory.getInstance().getBigliettoDAO().updatePostAcquisto(b.getId_biglietto(), email, nome + " " + cognome, qr, b.getTariffa().getPrezzo());
		}
		
		
	}
}
