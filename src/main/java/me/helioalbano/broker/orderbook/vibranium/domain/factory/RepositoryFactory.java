package me.helioalbano.broker.orderbook.vibranium.domain.factory;

import me.helioalbano.broker.orderbook.vibranium.domain.repository.AskRepository;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.BidRepository;
import me.helioalbano.broker.wallet.domain.repository.WalletRepository;

public interface RepositoryFactory {
    WalletRepository createWalletRepository();
    BidRepository createBidRepository();
    AskRepository createAskRepository();
}
