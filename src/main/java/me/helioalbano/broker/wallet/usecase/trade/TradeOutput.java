package me.helioalbano.broker.wallet.usecase.trade;

import lombok.Getter;

@Getter
public class TradeOutput {

    private String status;
    private String error;

    public TradeOutput withSuccess() {
        this.status = "success";
        return this;
    }

    public TradeOutput withError(String message) {
        this.status = "error";
        this.error = message;
        return this;
    }

}
