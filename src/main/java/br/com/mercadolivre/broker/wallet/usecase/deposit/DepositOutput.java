package br.com.mercadolivre.broker.wallet.usecase.deposit;

public class DepositOutput {

    private String status;
    private String error;

    public String getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public DepositOutput withSuccess() {
        this.status = "success";
        return this;
    }

    public DepositOutput withError(String message) {
        this.status = "error";
        this.error = message;
        return this;
    }

    public DepositOutput withWalletNotFoundError() {
        this.status = "error";
        this.error = "wallet not found";
        return this;
    }

}
