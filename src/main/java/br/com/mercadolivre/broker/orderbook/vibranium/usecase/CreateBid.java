package br.com.mercadolivre.broker.orderbook.vibranium.usecase;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;

public class CreateBid {

    private MatcherEngine matcher;

    public CreateBid(MatcherEngine matcher) {
        this.matcher = matcher;
    }

    public void execute(String walletCode, BigDecimal quantity, BigDecimal price) {
        try {
            matcher.processBid(walletCode, quantity, price);
        } catch (Exception e) {
            //logar erro
        }
    }

}
