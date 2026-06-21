package it.unipv.posw.model.service.payment;

/** Interface per gli adapter
 * @author rkomi-dev
 * @see PayPalAdapter
 * @see MastercardAdapter
 */

public interface IPagamentoAdapter {
	
	/**
	 * Metodo che permette di effettuare il pagamento
	 * @param importo Importo da pagare
	 * @return true se va a buon fine il pagamento
	 */
	boolean Paga(double importo);

}
