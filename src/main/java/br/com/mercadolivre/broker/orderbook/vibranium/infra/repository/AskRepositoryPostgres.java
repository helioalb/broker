package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskEntity;

public class AskRepositoryPostgres implements AskRepository {

    @Autowired
    private AskDAO dao;

    @Override
    public Optional<Ask> top() {
        AskEntity ae = dao.top();
        if (ae == null) return Optional.empty();
        Ask ask = new Ask(ae.getId(),
                          ae.getWalletCode(),
                          ae.getQuantity(),
                          ae.getPrice());
        return Optional.of(ask);
    }

    @Override
    public void save(Ask a) {
        dao.save(new AskEntity(a.getId(), a.getWalletCode(), a.getQuantity(),
                               a.getPrice(), a.getTradedWith(), a.getTradedQuantity()));
    }

}
