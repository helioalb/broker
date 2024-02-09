package me.helioalbano.broker.wallet.domain.repository;

import java.util.Optional;

import me.helioalbano.broker.wallet.domain.entity.Wallet;
import me.helioalbano.broker.wallet.domain.exception.PendingTransactionsException;
import me.helioalbano.broker.wallet.domain.exception.TradeException;
import me.helioalbano.broker.wallet.domain.service.TradeService;

public interface WalletRepository{

    String create();
    Optional<Wallet> findByCode(String code);
    Wallet getLast();
    void persistPendingTransactions(Wallet wallet) throws PendingTransactionsException;
    void realize(TradeService trade) throws TradeException;

}
