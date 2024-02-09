package me.helioalbano.broker.orderbook.vibranium.domain.repository;

import java.util.Optional;

import me.helioalbano.broker.orderbook.vibranium.domain.entity.Ask;

public interface AskRepository {

    public Optional<Ask> top();
    public Ask save(Ask ask);
    public void delete(Ask ask);
    public Optional<Ask> findById(Long id);

}
