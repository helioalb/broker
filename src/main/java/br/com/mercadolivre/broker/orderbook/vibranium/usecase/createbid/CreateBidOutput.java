package br.com.mercadolivre.broker.orderbook.vibranium.usecase.createbid;

import lombok.Getter;

@Getter
public class CreateBidOutput {

    private String status;
    private String error;

    public CreateBidOutput withSuccess() {
        this.status = "sucesso";
        return this;
    }

    public CreateBidOutput withError(String message) {
        this.error = message;
        return this;
    }

}
