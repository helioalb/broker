package br.com.mercadolivre.broker.wallet.domain.repository;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsNotPersistedException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;

public interface WalletRepository{

    String generateCode();
    String create(Wallet wallet) throws WalletNotCreatedException;
    Wallet findByCode(String code);
    void persistPendingTransactions(Wallet wallet) throws PendingTransactionsNotPersistedException;

}
