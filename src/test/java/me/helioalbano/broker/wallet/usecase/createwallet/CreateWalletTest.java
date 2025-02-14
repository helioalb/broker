package me.helioalbano.broker.wallet.usecase.createwallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import me.helioalbano.broker.wallet.domain.repository.WalletRepository;
import me.helioalbano.broker.wallet.usecase.createwallet.CreateWallet;

public class CreateWalletTest {
    @Test
    void createWalletSuccess() {
        WalletRepository repository = mock(WalletRepository.class);
        String code = "93d1ab2f-968d-4d7f-8050-b92b22c11e64";
        when(repository.create()).thenReturn(code);

        assertEquals(code, new CreateWallet(repository).execute());
    }
}
