package br.com.mercadolivre.broker.orderbook.vibranium.usecase;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;

public class CreateAsk {

    private MatcherEngine matcher;

    public CreateAsk(MatcherEngine matcher) {
        this.matcher = matcher;
    }

    public void execute(String walletCode, BigDecimal quantity, BigDecimal price) {
        matcher.processAsk(walletCode, quantity, price);
    }

}
