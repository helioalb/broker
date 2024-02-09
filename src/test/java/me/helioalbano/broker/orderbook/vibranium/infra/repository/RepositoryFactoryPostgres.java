package me.helioalbano.broker.orderbook.vibranium.infra.repository;

import org.springframework.beans.factory.annotation.Autowired;

import me.helioalbano.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.AskRepository;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.BidRepository;
import me.helioalbano.broker.wallet.domain.repository.WalletRepository;

public class RepositoryFactoryPostgres implements RepositoryFactory {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private AskRepository askRepository;

    @Autowired
    private BidRepository bidRepository;

    @Override
    public WalletRepository createWalletRepository() {
        return walletRepository;
    }

    @Override
    public BidRepository createBidRepository() {
        return bidRepository;
    }

    @Override
    public AskRepository createAskRepository() {
        return askRepository;
    }

}
