package br.com.mercadolivre.broker.wallet.domain.exception;

public class WalletNotCreatedException extends RuntimeException {

    public WalletNotCreatedException(String message) {
        super(message);
    }
    
}
