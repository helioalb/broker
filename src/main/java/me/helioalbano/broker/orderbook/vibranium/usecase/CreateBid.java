package me.helioalbano.broker.orderbook.vibranium.usecase;

import java.math.BigDecimal;

import me.helioalbano.broker.orderbook.vibranium.domain.service.MatcherEngine;

public class CreateBid {

    private MatcherEngine matcher;

    public CreateBid(MatcherEngine matcher) {
        this.matcher = matcher;
    }

    public void execute(String walletCode, BigDecimal quantity, BigDecimal price) {
        matcher.processBid(walletCode, quantity, price);
    }

}
