package br.com.mercadolivre.broker.wallet.usecase.createwallet;

public class CreateWalletOutput {

    private String status;
    private String error;

    public CreateWalletOutput withSucess() {
        this.status = "success";
        return this;
    }

    public CreateWalletOutput withError(String message) {
        this.status = "error";
        this.error = message;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public Object getError() {
        return this.error;
    }
    
}
