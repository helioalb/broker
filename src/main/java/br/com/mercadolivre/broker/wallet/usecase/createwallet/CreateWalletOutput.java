package br.com.mercadolivre.broker.wallet.usecase.createwallet;

import lombok.Getter;

@Getter
public class CreateWalletOutput {

    private String code;
    private String error;

    public CreateWalletOutput withError(String message) {
        this.error = message;
        return this;
    }

    public CreateWalletOutput withCode(String code) {
        this.code = code;
        return this;
    }
}
