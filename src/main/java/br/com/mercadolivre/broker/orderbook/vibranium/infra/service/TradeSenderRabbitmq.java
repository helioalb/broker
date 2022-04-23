package br.com.mercadolivre.broker.orderbook.vibranium.infra.service;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Trade;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.service.TradeSender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeSenderRabbitmq implements TradeSender {

    @Override
    public void send(Trade trade) {
        log.info("[TRADE][SENT]");
    }

}
