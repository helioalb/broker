package br.com.mercadolivre.broker.wallet.infra.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletDAO extends JpaRepository<WalletEntity, Long> {

    WalletEntity getByCode(String code);

}
