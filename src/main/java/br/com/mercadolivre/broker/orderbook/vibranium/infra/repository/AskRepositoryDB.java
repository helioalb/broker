package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskDAO;
import br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao.AskEntity;

public class AskRepositoryDB implements AskRepository {

    @Autowired
    private AskDAO dao;

    @Override
    public Ask top() {
        AskEntity ae = dao.top();
        if (ae == null) return null;
        return new Ask(ae.getWalletCode(), ae.getQuantity(), ae.getPrice());
    }

    @Override
    public void save(Ask a) {
        dao.save(new AskEntity(a.getWalletCode(), a.getQuantity(), a.getPrice()));
    }

}
