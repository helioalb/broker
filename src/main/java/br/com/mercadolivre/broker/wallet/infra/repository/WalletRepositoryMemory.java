package br.com.mercadolivre.broker.wallet.infra.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.entity.Partition;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsException;
import br.com.mercadolivre.broker.wallet.domain.exception.TradeException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

public class WalletRepositoryMemory implements WalletRepository {
    private Map<String, Wallet> wallets;

    public WalletRepositoryMemory() {
        String askWalletCode = "askask63-cb68-4335-b5a3-25a269564268";
        String bidWalletCode = "bidbid63-cb68-4335-b5a3-25a269564268";

        this.wallets = new HashMap<>();

        this.wallets.put(askWalletCode, buildWallet(askWalletCode, "1000", "1000"));
        this.wallets.put(bidWalletCode, buildWallet(bidWalletCode, "1000", "1000"));
    }

    @Override
    public String create() throws WalletNotCreatedException {
        return null;
    }

    @Override
    public Optional<Wallet> findByCode(String code) {
        return Optional.ofNullable(this.wallets.get(code));
    }

    @Override
    public Wallet getLast() {
        return null;
    }

    @Override
    public void persistPendingTransactions(Wallet wallet) throws PendingTransactionsException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void realize(TradeService trade) throws TradeException {
        this.wallets.put(trade.getLeftWallet().getCode(), trade.getLeftWallet());
        this.wallets.put(trade.getRightWallet().getCode(), trade.getRightWallet());
    }

    private Wallet buildWallet(String walletCode, String amountBRL, String amountVIB) {
        Partition p1 = new Partition(Asset.BRL, new BigDecimal(amountBRL));
        Partition p2 = new Partition(Asset.VIB, new BigDecimal(amountVIB));

        Set<Partition> partitions = new HashSet<>();
        partitions.add(p1);
        partitions.add(p2);

        return new Wallet(walletCode, partitions);
    }

}
