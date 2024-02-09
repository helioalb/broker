package me.helioalbano.broker.orderbook.vibranium.infra.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import me.helioalbano.broker.orderbook.vibranium.domain.entity.Ask;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.AskRepository;
import me.helioalbano.broker.orderbook.vibranium.infra.repository.dao.AskDAO;
import me.helioalbano.broker.orderbook.vibranium.infra.repository.dao.AskEntity;

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
    public Ask save(Ask ask) {
        AskEntity ae = new AskEntity(ask.getWalletCode(),
                                     ask.getQuantity(),
                                     ask.getPrice());
        ae = dao.save(ae);
        return new Ask(ae.getId(),
                       ae.getWalletCode(),
                       ae.getQuantity(),
                       ae.getPrice());
    }

    @Override
    public void delete(Ask ask) {
        if (ask.getId() != null)
            dao.deleteById(ask.getId());
    }

    @Override
    public Optional<Ask> findById(Long id) {
        Optional<AskEntity> ae = dao.findById(id);
        if (ae.isEmpty()) return Optional.empty();
        Ask ask = new Ask(ae.get().getId(),
                          ae.get().getWalletCode(),
                          ae.get().getQuantity(),
                          ae.get().getPrice());
        return Optional.of(ask);
    }

}
