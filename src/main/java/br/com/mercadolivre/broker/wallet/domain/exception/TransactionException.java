package br.com.mercadolivre.broker.wallet.domain.exception;

public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

}
