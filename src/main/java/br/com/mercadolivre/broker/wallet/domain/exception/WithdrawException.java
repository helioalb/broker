package br.com.mercadolivre.broker.wallet.domain.exception;

public class WithdrawException extends RuntimeException {

    public WithdrawException(String message) {
        super(message);
    }

}
