package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.BidEntity;

public class BidRepositoryDB implements BidRepository {

    @Autowired
    private BidDAO dao;

    @Override
    public Bid top() {
        BidEntity be = dao.top();
        if (be == null) return null;
        return new Bid(be.getWalletCode(), be.getQuantity(), be.getPrice());
    }

    @Override
    public void save(Bid b) {
        dao.save(new BidEntity(b.getWalletCode(), b.getQuantity(), b.getPrice()));
    }

}
