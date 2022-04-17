package br.com.mercadolivre.broker.orderbook.vibranium.domain.factory;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public interface RepositoryFactory {
    WalletRepository createWalletRepository();
    BidRepository createBidRepository();
    AskRepository createAskRepository();
}
