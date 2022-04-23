package br.com.mercadolivre.broker.trade.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.trade.domain.entity.Trade;
import br.com.mercadolivre.broker.trade.infra.repository.dao.TradeDao;
import br.com.mercadolivre.broker.trade.repository.TradeRepository;

public class TradeRepositoryMongo implements TradeRepository {

    @Autowired
    private TradeDao dao;

    @Override
    public Trade save(Trade trade) {
        return dao.save(trade);
    }

}
