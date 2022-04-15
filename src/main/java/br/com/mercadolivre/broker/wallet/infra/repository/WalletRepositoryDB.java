package br.com.mercadolivre.broker.wallet.infra.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.wallet.domain.entity.Partition;
import br.com.mercadolivre.broker.wallet.domain.entity.Transaction;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsNotPersistedException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.PartitionDAO;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.PartitionEntity;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.TransactionDAO;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.TransactionVO;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.WalletDAO;
import br.com.mercadolivre.broker.wallet.infra.repository.dao.WalletEntity;

public class WalletRepositoryDB implements WalletRepository {
    @Autowired
    private WalletDAO walletDAO;

    @Autowired
    private PartitionDAO partitionDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public String create() throws WalletNotCreatedException {
        try {
            return walletDAO.save(new WalletEntity(generateCode())).getCode();
        } catch (Exception e) {
            throw new WalletNotCreatedException(e.getMessage());
        }
    }

    private String generateCode() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Wallet findByCode(String code) {
        WalletEntity we = walletDAO.getByCode(code);
        return buildWalletFrom(we);
    }

    @Override
    public void persistPendingTransactions(Wallet wallet) throws PendingTransactionsNotPersistedException {
        try {
            WalletEntity walletEntity = walletDAO.getByCode(wallet.getCode());
            if (walletEntity == null) throw new PendingTransactionsNotPersistedException("wallet not exist");
            for (Partition partition : wallet.getPartitions()) {
                if (partition.hasPendingTransactions()) {
                    PartitionEntity partitionEntity = partitionDAO.getByWalletIdAndAsset(walletEntity.getId(), partition.getAsset());
                    if (partitionEntity == null) {
                        partitionEntity = new PartitionEntity(partition.getAsset(), walletEntity);
                        partitionDAO.save(partitionEntity);
                    }

                    for (Transaction transaction : partition.pendingTransactions()) {
                        BigDecimal currentBalance = transactionDAO.balanceOfPartition(partitionEntity.getId());
                        BigDecimal signedAmount = transaction.getType().isIncome() ? transaction.getAmount() : transaction.getAmount().negate();

                        if (currentBalance == null) {
                            if (signedAmount.compareTo(BigDecimal.ZERO) < 0) {
                                throw new PendingTransactionsNotPersistedException("insufficient balance");
                            }
                        } else {
                            if (signedAmount.add(currentBalance).compareTo(BigDecimal.ZERO) < 0) {
                                throw new PendingTransactionsNotPersistedException("insufficient balance");
                            }
                        }

                        TransactionVO transactionVO = new TransactionVO(transaction.getType(), signedAmount, partitionEntity);
                        transactionDAO.save(transactionVO);
                    }
                }
            }
        } catch (Exception e) {
            throw new PendingTransactionsNotPersistedException(e.getMessage());
        }
    }

    @Override
    public Wallet getLast() {
        WalletEntity we = walletDAO.getTop1ByOrderByIdDesc();
        return buildWalletFrom(we);
    }

    private Wallet buildWalletFrom(WalletEntity we) {
        if (we == null) return null;
        List<PartitionEntity> pes = partitionDAO.findByWalletId(we.getId());
        Set<Partition> partitions = new HashSet<>();
        for (PartitionEntity pe : pes) {
            BigDecimal currentBalance = transactionDAO.balanceOfPartition(pe.getId());
            partitions.add(new Partition(pe.getAsset(), currentBalance));
        }
        return new Wallet(we.getCode(), partitions);
    }

}
