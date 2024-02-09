package me.helioalbano.broker.wallet.usecase.withdraw;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.wallet.domain.entity.Partition;
import me.helioalbano.broker.wallet.domain.entity.Wallet;
import me.helioalbano.broker.wallet.domain.exception.WithdrawException;
import me.helioalbano.broker.wallet.domain.repository.WalletRepository;
import me.helioalbano.broker.wallet.usecase.withdraw.Withdraw;
import me.helioalbano.broker.wallet.usecase.withdraw.WithdrawInput;

public class WithdrawTest {
    @Test
    void withdrawInAnExistentPartitionError() {
        String walletCode = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        WalletRepository repository = buildRepository(walletCode);

        WithdrawInput input = new WithdrawInput(walletCode, Asset.BRL, new BigDecimal("3"));

        Withdraw withdraw = new Withdraw(repository);
        Exception e = assertThrows(WithdrawException.class, () -> withdraw.execute(input));

        assertEquals("insufficient balance", e.getMessage());
    }

    @Test
    void withdrawInAnExistentPartitionSuccess() {
        String walletCode = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        WalletRepository repository = buildRepository(walletCode);

        WithdrawInput input = new WithdrawInput(walletCode, Asset.BRL, new BigDecimal("2"));
        assertDoesNotThrow(() -> new Withdraw(repository).execute(input));
    }

    private WalletRepository buildRepository(String walletCode) {
        Partition partition = new Partition(Asset.BRL, new BigDecimal("2"));
        Set<Partition> partitions = new HashSet<Partition>();
        partitions.add(partition);
        Wallet wallet = new Wallet(walletCode, partitions);
        WalletRepository repository = mock(WalletRepository.class);
        when(repository.findByCode(anyString())).thenReturn(Optional.of(wallet));
        return repository;
    }
}
