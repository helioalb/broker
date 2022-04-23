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
    public Bid save(Bid bid) {
        BidEntity be = new BidEntity(bid.getWalletCode(),
                                     bid.getQuantity(),
                                     bid.getPrice());
        be = dao.save(be);
        return new Bid(be.getId(),
                       be.getWalletCode(),
                       be.getQuantity(),
                       be.getPrice());
    }

    @Override
    public void delete(Bid bid) {
        if (bid.getId() != null)
            dao.deleteById(bid.getId());
    }

    @Override
    public Optional<Bid> findById(Long id) {
        Optional<BidEntity> be = dao.findById(id);
        if (be.isEmpty()) return Optional.empty();
        Bid bid = new Bid(be.get().getId(),
                            be.get().getWalletCode(),
                            be.get().getQuantity(),
                            be.get().getPrice());
        return Optional.of(bid);
    }
}
