package me.helioalbano.broker.trade.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.common.enums.Asset;
import me.helioalbano.broker.trade.domain.entity.Trade;
import me.helioalbano.broker.trade.repository.TradeRepository;
import me.helioalbano.broker.trade.usecase.CreateTrade;

public class CreateTradeTest {

    @Test
    void createTrade() {
        TradeRepository repository = mock(TradeRepository.class);
        String buyerWalletCode = "8260ada4-5442-44ca-9e80-12796b7c3b76";
        String sellerWalletCode = "a13deff4-4e55-4df1-981d-a830012ec084";
        BigDecimal quantity = new BigDecimal("1");
        BigDecimal amount = new BigDecimal("1");
        new CreateTrade(repository).execute(Asset.VIB,
                                            Asset.BRL,
                                            buyerWalletCode,
                                            sellerWalletCode,
                                            quantity,
                                            amount);
        verify(repository).save(any(Trade.class));
    }
}
