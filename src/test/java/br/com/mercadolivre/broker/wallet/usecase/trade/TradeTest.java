package br.com.mercadolivre.broker.wallet.usecase.trade;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.wallet.domain.entity.Partition;
import br.com.mercadolivre.broker.wallet.domain.entity.Wallet;
import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.domain.service.TradeService;

public class TradeTest {
    @Test
    void tradeDoesntHaveRestrictions() {
        String leftWalletCode = "leftfb82-6a47-4ccf-a494-201c8df1874b";
        String rightWalletCode = "rightb82-6a47-4ccf-a494-201c8df1874b";
        WalletRepository repository = mock(WalletRepository.class);
        when(repository.findByCode(leftWalletCode))
            .thenReturn(buildWallet(leftWalletCode, "100", "100"));
        when(repository.findByCode(rightWalletCode))
            .thenReturn(buildWallet(rightWalletCode, "100", "100"));

        new Trade(repository)
            .leftWalletCode(leftWalletCode)
            .leftAssetOut("BRL")
            .leftAmountOut(new BigDecimal("20"))
            .rightWalletCode(rightWalletCode)
            .rightAssetOut("VIB")
            .rightAmountOut(new BigDecimal("30"))
            .execute();

            verify(repository, times(1))
                .realize(any(TradeService.class));
    }

    @Test
    void walletAvailability() {
        String walletCode = "leftfb82-6a47-4ccf-a494-201c8df1874b";
        Wallet wallet = new Wallet(walletCode);
        WalletRepository repository = mock(WalletRepository.class);

        when(repository.findByCode(anyString())).thenReturn(Optional.of(wallet));
        Trade trade = new Trade(repository);
        assertTrue(trade.isAvailableFor(walletCode));
    }

    private Optional<Wallet> buildWallet(String leftWalletCode, String amountBRL, String amountVIB) {
        Partition p1 = new Partition(Asset.BRL, new BigDecimal(amountBRL));
        Partition p2 = new Partition(Asset.VIB, new BigDecimal(amountBRL));
        Set<Partition> partitions = new HashSet<>();
        partitions.add(p1);
        partitions.add(p2);
        return Optional.of(new Wallet(leftWalletCode, partitions));
    }
}
