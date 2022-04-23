package br.com.mercadolivre.broker.trade.repository;

import br.com.mercadolivre.broker.trade.domain.entity.Trade;

public interface TradeRepository {

    Trade save(Trade trade);
}
