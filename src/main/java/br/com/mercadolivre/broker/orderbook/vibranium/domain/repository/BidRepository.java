package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import java.util.Optional;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;

public interface BidRepository {

    public Optional<Bid> top();
    public void save(Bid bid);

}
