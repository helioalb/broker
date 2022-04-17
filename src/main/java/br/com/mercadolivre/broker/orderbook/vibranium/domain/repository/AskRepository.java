package br.com.mercadolivre.broker.orderbook.vibranium.domain.repository;

import java.util.List;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;

public interface AskRepository {

    public List<Ask> availablePage(int page);
    public void save(Ask ask);

}
