package br.com.mercadolivre.broker.wallet.domain.repository;

import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsException;
import br.com.mercadolivre.broker.wallet.domain.exception.TradeException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

public interface WalletRepository{

    String create() throws WalletNotCreatedException;
    Wallet findByCode(String code);
    Wallet getLast();
    void persistPendingTransactions(Wallet wallet) throws PendingTransactionsException;
    void realize(TradeService trade) throws TradeException;

}
