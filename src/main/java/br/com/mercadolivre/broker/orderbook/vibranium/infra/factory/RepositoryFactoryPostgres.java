package br.com.mercadolivre.broker.orderbook.vibranium.infra.factory;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

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
