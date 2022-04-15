package br.com.mercadolivre.broker.wallet.domain.exception;

public class TransactionNotPermitedException extends RuntimeException {

    public TransactionNotPermitedException(String message) {
        super(message);
    }

}
