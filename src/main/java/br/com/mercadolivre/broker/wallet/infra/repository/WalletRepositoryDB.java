package br.com.mercadolivre.broker.wallet.infra.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadolivre.broker.wallet.domain.entity.Partition;
import br.com.mercadolivre.broker.wallet.domain.entity.Transaction;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;
import br.com.mercadolivre.broker.wallet.domain.exception.PendingTransactionsException;
import br.com.mercadolivre.broker.wallet.domain.exception.TradeException;
import br.com.mercadolivre.broker.wallet.domain.exception.WalletNotCreatedException;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;
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
    @Transactional(rollbackOn = Exception.class)
    public void persistPendingTransactions(Wallet wallet) throws PendingTransactionsException {
        try {
            persistPendingTransactionOnWallet(wallet);
        } catch (Exception e) {
            throw new PendingTransactionsException(e.getMessage());
        }
    }

    private void persistPendingTransactionOnWallet(Wallet wallet) {
        WalletEntity walletEntity = walletDAO.getByCode(wallet.getCode());
        if (walletEntity == null) throw new PendingTransactionsException("wallet not exist");
        for (Partition partition : wallet.getPartitions()) {
            if (partition.hasPendingTransactions()) {
                persistPendingTransactions(walletEntity, partition);
            }
        }
    }

    private void persistPendingTransactions(WalletEntity walletEntity,
                                            Partition partition) {
        PartitionEntity partitionEntity = findOrCreatePartitionEntity(partition,
                                                                      walletEntity);
        for (Transaction transaction : partition.pendingTransactions()) {
            TransactionType type = transaction.getType();
            BigDecimal amount = transaction.getAmount();
            BigDecimal signedAmount = type.isIncome() ? amount : amount.negate();
            ensureNonNegativeBalance(partitionEntity, signedAmount);
            TransactionVO transactionVO = new TransactionVO(type, signedAmount,
                                                            partitionEntity);
            transactionDAO.save(transactionVO);
        }
    }

    private PartitionEntity findOrCreatePartitionEntity(Partition partition,
                                                        WalletEntity walletEntity) {
        PartitionEntity partitionEntity =
            partitionDAO.getByWalletIdAndAsset(walletEntity.getId(),
                                               partition.getAsset());
        if (partitionEntity == null) {
            partitionEntity = new PartitionEntity(partition.getAsset(),
                                                  walletEntity);
            partitionDAO.save(partitionEntity);
        }
        return partitionEntity;
    }

    private void ensureNonNegativeBalance(PartitionEntity partitionEntity, BigDecimal signedAmount) {
        BigDecimal currentBalance =
            transactionDAO.balanceOfPartition(partitionEntity.getId());
        if (currentBalance == null) {
            if (signedAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new PendingTransactionsException("insufficient balance");
            }
        } else {
            if (signedAmount.add(currentBalance).compareTo(BigDecimal.ZERO) < 0) {
                throw new PendingTransactionsException("insufficient balance");
            }
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

    @Override
    public void realize(TradeService trade) throws TradeException {
        // TODO Auto-generated method stub

    }

}
