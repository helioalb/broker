package br.com.mercadolivre.broker.orderbook.vibranium.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class BidTest {
    @Test
    void createNewAfterTradeWith() {
        Bid bid = new Bid("abc", new BigDecimal("2"), new BigDecimal("1"));
        Ask ask = new Ask("def", new BigDecimal("1"), new BigDecimal("1"));
        Bid newBid = bid.createNewAfterTradeWith(ask);
        assertEquals(new BigDecimal("1"), newBid.getQuantity());
    }
}
