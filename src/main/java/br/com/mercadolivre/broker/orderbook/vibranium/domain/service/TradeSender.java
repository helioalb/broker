package br.com.mercadolivre.broker.orderbook.vibranium.domain.service;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Trade;

public interface TradeSender {
    void send(Trade trade);
}
