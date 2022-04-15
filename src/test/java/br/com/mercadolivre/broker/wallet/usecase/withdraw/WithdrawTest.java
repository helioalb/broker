package br.com.mercadolivre.broker.wallet.usecase.withdraw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.wallet.domain.entity.Partition;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class WithdrawTest {
    @Test
    void withdrawInAnExistentPartitionError() {
        String walletCode = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        WalletRepository repository = buildRepository(walletCode);

        WithdrawInput input = new WithdrawInput(walletCode, "BRL", "3");
        WithdrawOutput output = new Withdraw(repository).execute(input);

        assertEquals("error", output.getStatus());
        assertEquals("insufficient balance", output.getError());
    }

    @Test
    void withdrawInAnExistentPartitionSuccess() {
        String walletCode = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        WalletRepository repository = buildRepository(walletCode);

        WithdrawInput input = new WithdrawInput(walletCode, "BRL", "2");
        WithdrawOutput output = new Withdraw(repository).execute(input);

        assertEquals("success", output.getStatus());
        assertNull(output.getError());
    }

    private WalletRepository buildRepository(String walletCode) {
        Partition partition = new Partition(Asset.BRL, new BigDecimal("2"));
        Set<Partition> partitions = new HashSet<Partition>();
        partitions.add(partition);
        Wallet wallet = new Wallet(walletCode, partitions);
        WalletRepository repository = mock(WalletRepository.class);
        when(repository.findByCode(anyString())).thenReturn(wallet);
        return repository;
    }
}
