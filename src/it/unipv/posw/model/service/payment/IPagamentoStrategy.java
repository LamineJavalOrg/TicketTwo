package it.unipv.posw.model.service.payment;

/** Interface per le strategie di pagamento
 * @author rkomi-dev
 * @see PayPalStrategy
 * @see MastercardStrategy
 */

public interface IPagamentoStrategy {
	
	IPagamentoAdapter getMetodoPagamento();

}
