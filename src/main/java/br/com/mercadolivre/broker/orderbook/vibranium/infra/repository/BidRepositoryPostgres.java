package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidEntity;

public class BidRepositoryPostgres implements BidRepository {

    @Autowired
    private BidDAO dao;

    @Override
    public Optional<Bid> top() {
        BidEntity be = dao.top();
        if (be == null) return Optional.empty();
        Bid bid = new Bid(be.getId(),
                          be.getWalletCode(),
                          be.getQuantity(),
                          be.getPrice());
        return Optional.of(bid);
    }

    @Override
    public void save(Bid b) {
        dao.save(new BidEntity(b.getId(), b.getWalletCode(), b.getQuantity(),
                               b.getPrice(), b.getTradedWith(), b.getTradedQuantity()));
    }

}
