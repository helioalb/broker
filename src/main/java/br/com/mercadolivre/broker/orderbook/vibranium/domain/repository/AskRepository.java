package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import java.util.Optional;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;

public interface AskRepository {

    public Optional<Ask> top();
    public void save(Ask ask);

}
