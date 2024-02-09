package me.helioalbano.broker.wallet.infra.repository.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionDAO extends JpaRepository<TransactionVO, Long>{

    @Query("SELECT SUM(t.amount) FROM TransactionVO t WHERE t.partition.id = ?1")
    BigDecimal balanceOfPartition(Long id);

}
