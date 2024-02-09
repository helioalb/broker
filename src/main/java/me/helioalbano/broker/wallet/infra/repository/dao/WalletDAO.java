package me.helioalbano.broker.wallet.infra.repository.dao;


import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletDAO extends JpaRepository<WalletEntity, Long> {

    WalletEntity getByCode(String code);
    WalletEntity getTop1ByOrderByIdDesc();
}
