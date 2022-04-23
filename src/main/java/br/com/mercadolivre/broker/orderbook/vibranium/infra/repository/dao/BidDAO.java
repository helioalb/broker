package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BidDAO extends JpaRepository<BidEntity, Long>{
    @Query(value = "SELECT * FROM bids b ORDER BY b.price DESC, b.created_at ASC LIMIT 1",
    nativeQuery = true)
    BidEntity top();
}
