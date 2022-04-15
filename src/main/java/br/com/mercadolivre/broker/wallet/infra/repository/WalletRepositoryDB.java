package br.com.mercadolivre.broker.wallet.infra.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.wallet.domain.entity.*;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsNotPersistedException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.jpa.*;

public class WalletRepositoryDB implements WalletRepository {
    @Autowired
    private WalletDAO walletDAO;

    @Autowired
    private PartitionDAO partitionDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String create(Wallet wallet) throws WalletNotCreatedException {
        try {
            WalletEntity savedWallet =
                walletDAO.save(new WalletEntity(wallet.getCode()));
            return savedWallet.getCode();
        } catch (Exception e) {
            throw new WalletNotCreatedException(e.getMessage());
        }
    }

    @Override
    public Wallet findByCode(String code) {
        WalletEntity walletEntity = walletDAO.getByCode(code);
        if (walletEntity != null) return new Wallet(walletEntity.getCode());
        return null;
    }

    @Override
    @Transactional
    public void persistPendingTransactions(Wallet wallet) throws PendingTransactionsNotPersistedException {
        try {
            WalletEntity we = walletDAO.getByCode(wallet.getCode());
            for (Partition p : wallet.getPartitions()) {
                PartitionEntity pe = getOrCreatePartitionBy(we, p);

                for (Transaction t : p.pendingTransactions()) {
                    TransactionVO newTransaction =
                        new TransactionVO(t.getType(), t.getAmount(), pe);
                    transactionDAO.save(newTransaction);
                }
            }
        } catch (Exception e) {
            throw new PendingTransactionsNotPersistedException(e.getMessage());
        }
    }

    private PartitionEntity getOrCreatePartitionBy(WalletEntity wallet,
                                                   Partition partition) {
        PartitionEntity pe = partitionDAO.getByWalletIdAndAsset(wallet.getId(),
                                                                partition.getAsset());
        if (pe != null) return pe;
        PartitionEntity newPartition = new PartitionEntity(partition.getAsset(),
                                                           wallet);
        return partitionDAO.save(newPartition);
    }

}
