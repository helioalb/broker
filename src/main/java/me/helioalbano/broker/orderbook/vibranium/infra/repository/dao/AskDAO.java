package me.helioalbano.broker.orderbook.vibranium.infra.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AskDAO extends JpaRepository<AskEntity, Long> {

    @Query(value = "SELECT * FROM asks a ORDER BY a.price ASC, a.created_at ASC LIMIT 1",
           nativeQuery = true)
    AskEntity top();
}
