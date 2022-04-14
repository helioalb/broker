package br.com.mercadolivre.broker.wallet.infra.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.jpa.WalletEntity;
import br.com.mercadolivre.broker.wallet.infra.repository.jpa.WalletRepositoryJpa;

public class WalletRepositoryDB implements WalletRepository {
    @Autowired
    private WalletRepositoryJpa repository;

    @Override
    public String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String create(Wallet wallet) throws WalletNotCreatedException {
        try {
            WalletEntity savedWallet =
                repository.save(new WalletEntity(wallet.getCode()));
            return savedWallet.getCode();
        } catch (Exception e) {
            throw new WalletNotCreatedException(e.getMessage());
        }
    }

}
