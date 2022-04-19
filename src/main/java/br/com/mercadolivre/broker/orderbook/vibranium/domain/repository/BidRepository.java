package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;

public interface BidRepository {

    public Bid top();
    public void save(Bid bid);

}
