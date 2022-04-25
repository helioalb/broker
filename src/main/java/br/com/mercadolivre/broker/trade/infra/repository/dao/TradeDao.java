package br.com.mercadolivre.broker.trade.infra.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.mercadolivre.broker.trade.domain.entity.Trade;

public interface TradeDao extends MongoRepository<Trade, String> {
    Page<Trade> findAll(Pageable pageable);
}
