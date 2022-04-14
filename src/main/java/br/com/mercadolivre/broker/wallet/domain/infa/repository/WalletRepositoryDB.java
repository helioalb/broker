package br.com.mercadolivre.broker.wallet.domain.infa.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.infa.repository.jpa.WalletEntity;
import br.com.mercadolivre.broker.wallet.domain.infa.repository.jpa.WalletRepositoryJpa;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class WalletRepositoryDB implements WalletRepository {
    @Autowired
    private WalletRepositoryJpa repository;

    @Override
    public String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void create(Wallet wallet) throws WalletNotCreatedException {
        try {
            repository.save(new WalletEntity(wallet.getCode()));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    
}
