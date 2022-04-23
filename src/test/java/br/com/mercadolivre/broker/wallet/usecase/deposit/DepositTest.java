package br.com.mercadolivre.broker.wallet.usecase.deposit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.common.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class DepositTest {

    @Test
    void depositInAnInexistentWalletPartition() {
        String walletCode = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        Asset asset = Asset.BRL;
        BigDecimal amount = new BigDecimal("1200.12");
        Wallet wallet = new Wallet(walletCode, new HashSet<>());
        WalletRepository repository = mock(WalletRepository.class);
        when(repository.findByCode(anyString())).thenReturn(Optional.of(wallet));

        DepositInput input = new DepositInput(walletCode, asset, amount);
        new Deposit(repository).execute(input);

        verify(repository, times(1))
            .persistPendingTransactions(wallet);
    }
}
