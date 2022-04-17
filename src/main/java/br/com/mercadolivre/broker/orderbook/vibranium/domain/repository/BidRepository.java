package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import java.util.List;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;

public interface BidRepository {

    public List<Bid> availablePage(int page);
    public void save(Bid bid);

}
