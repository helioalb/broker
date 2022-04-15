package br.com.mercadolivre.broker.wallet.usecase.withdraw;

public class WithdrawOutput {

    private String status;
    private String error;

    public String getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public WithdrawOutput withSuccess() {
        this.status = "success";
        return this;
    }

    public WithdrawOutput withError(String message) {
        this.status = "error";
        this.error = message;
        return this;
    }

    public WithdrawOutput withWalletNotFoundError() {
        this.status = "error";
        this.error = "wallet not found";
        return this;
    }

}
