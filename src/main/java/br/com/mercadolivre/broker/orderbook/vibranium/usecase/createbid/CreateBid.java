package br.com.mercadolivre.broker.orderbook.vibranium.usecase.createbid;

import java.math.BigDecimal;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.exception.BidException;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.MatcherEngine;

public class CreateBid {

    private MatcherEngine matcher;

    public CreateBid(MatcherEngine matcher) {
        this.matcher = matcher;
    }

    public CreateBidOutput execute(String walletCode, BigDecimal quantity, BigDecimal price) {
        try {
            matcher.processBid(walletCode, quantity, price);
            return new CreateBidOutput().withSuccess();
        } catch (BidException e) {
            return new CreateBidOutput().withError(e.getMessage());
        }
    }

}
