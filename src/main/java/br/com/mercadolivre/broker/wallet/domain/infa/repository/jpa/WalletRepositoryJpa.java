package br.com.mercadolivre.broker.wallet.domain.infa.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepositoryJpa extends JpaRepository<WalletEntity, Long> {
    
}
