package br.com.mercadolivre.broker.wallet.domain.exception;

public class WithdrawNotRealizedException extends RuntimeException {

    public WithdrawNotRealizedException(String message) {
        super(message);
    }

}
