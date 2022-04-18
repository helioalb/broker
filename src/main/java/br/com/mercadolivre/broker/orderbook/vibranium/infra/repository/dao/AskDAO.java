package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AskDAO extends JpaRepository<AskEntity, Long> {

    @Query(value = "SELECT * FROM asks a WHERE a.traded_with is null ORDER BY a.traded_with NULLS FIRST, a.price ASC, a.updated_at ASC LIMIT 1",
           nativeQuery = true)
    AskEntity top();
}
