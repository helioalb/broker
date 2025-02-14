package me.helioalbano.broker.orderbook.vibranium.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.orderbook.vibranium.domain.entity.Ask;
import me.helioalbano.broker.orderbook.vibranium.domain.entity.Bid;

public class IntentTest {
    @Test
    void comparePriceWith() {
        Bid bid = new Bid("abc", new BigDecimal("1"), new BigDecimal("1"));
        Ask ask = new Ask("def", new BigDecimal("1"), new BigDecimal("1"));

        assertEquals(0, bid.comparePriceWith(ask));
    }

    @Test
    void compareQuantityWith() {
        Bid bid = new Bid("abc", new BigDecimal("1"), new BigDecimal("1"));
        Ask ask = new Ask("def", new BigDecimal("2"), new BigDecimal("1"));

        assertEquals(1, ask.compareQuantityWith(bid));
    }

    @Test
    void amountForQuantityWithInvalidQuantity() {
        Bid bid = new Bid("abc", new BigDecimal("1"), new BigDecimal("1"));
        BigDecimal three = new BigDecimal("3");
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> bid.amountForQuantity(three));

        assertEquals("this quantity is not available", e.getMessage());
    }

    @Test
    void amountForQuantityWithValidQuantity() {
        Bid bid = new Bid("abc", new BigDecimal("10"), new BigDecimal("1"));
        BigDecimal three = new BigDecimal("3");
        assertEquals(three, bid.amountForQuantity(three));
    }

    @Test
    void decreaseQuantityBasedOn() {
        Bid bid = new Bid("abc", new BigDecimal("2"), new BigDecimal("1"));
        Ask ask = new Ask("def", new BigDecimal("1"), new BigDecimal("1"));

        BigDecimal newQuantity = bid.decreasedQuantityBasedOn(ask);

        assertEquals(new BigDecimal("1"), newQuantity);
    }

    @Test
    void decreaseQuantityBasedOnInvalid() {
        Bid bid = new Bid("abc", new BigDecimal("2"), new BigDecimal("1"));
        Ask ask = new Ask("def", new BigDecimal("3"), new BigDecimal("1"));

        Exception e = assertThrows(IllegalStateException.class,
            () -> bid.decreasedQuantityBasedOn(ask));

        assertEquals("decrease cannot result in a negative quantity", e.getMessage());
    }

}
