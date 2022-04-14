package br.com.mercadolivre.broker.wallet.usecase.createwallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;

public class CreateWalletTest {
    @Test
    void createWalletSuccess() {
        WalletRepository repository = mock(WalletRepository.class);
        when(repository.generateCode())
            .thenReturn("93d1ab2f-968d-4d7f-8050-b92b22c11e64");
        
        CreateWalletOutput output = new CreateWallet(repository).execute();

        assertEquals("success", output.getStatus());
        assertNull(output.getError());
    }
}
