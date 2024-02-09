package me.helioalbano.broker.wallet.domain.exception;

public class PendingTransactionsException extends RuntimeException {

    public PendingTransactionsException(String message) {
        super(message);
    }

}
