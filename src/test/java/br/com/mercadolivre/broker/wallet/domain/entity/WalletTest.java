package br.com.mercadolivre.broker.wallet.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class WalletTest {
    @Test
    void codeWithInvalidLength() {
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> new Wallet("abcd-efgh")
        );

        assertEquals("wallet code invalid", e.getMessage());
    }
}
