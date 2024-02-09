package me.helioalbano.broker.orderbook.vibranium.domain.service;

import me.helioalbano.broker.orderbook.vibranium.domain.entity.Trade;

public interface TradeSender {
    void send(Trade trade);
}
