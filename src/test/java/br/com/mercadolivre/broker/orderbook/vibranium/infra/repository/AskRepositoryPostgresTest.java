package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;

@SpringBootTest
public class AskRepositoryPostgresTest {
    @Autowired
    private AskRepository repository;

    @Test
    void top() {
        String walletCode = "244f5744-2630-4b5a-9ae0-617d1a6c5d30";
        repository.save(buildAsk(walletCode, "1", "1"));
        repository.save(buildAsk(walletCode, "1", "1"));
        repository.save(buildAsk(walletCode, "1", "2"));

        assertEquals(new BigDecimal("1.0000"), repository.top().get().getPrice());
    }

    @Test
    void delete() {
        String walletCode = "244f5744-2630-4b5a-9ae0-617d1a6c5d30";
        Ask ask = repository.save(buildAsk(walletCode, "1", "1"));
        repository.delete(ask);
        assertTrue(repository.findById(ask.getId()).isEmpty());
    }

    private Ask buildAsk(String walletCode, String quantity, String price) {
        return new Ask(walletCode, new BigDecimal(quantity), new BigDecimal(price));
    }
}
