package br.com.mercadolivre.broker.wallet.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDAO extends JpaRepository<TransactionVO, Long>{

}
