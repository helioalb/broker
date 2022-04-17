package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;

public interface AskRepository {

    public Ask top();
    public void save(Ask ask);

}
