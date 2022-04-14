package br.com.mercadolivre.broker.wallet.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepositoryJpa extends JpaRepository<WalletEntity, Long> {

}
