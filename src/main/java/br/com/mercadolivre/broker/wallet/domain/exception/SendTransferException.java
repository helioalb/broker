package br.com.mercadolivre.broker.wallet.domain.exception;

public class SendTransferException extends RuntimeException {

    public SendTransferException(String message) {
        super(message);
    }

}
