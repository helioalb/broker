package br.com.mercadolivre.broker.wallet.domain.exception;

public class PendingTransactionsNotPersistedException extends RuntimeException {

    public PendingTransactionsNotPersistedException(String message) {
        super(message);
    }

}
