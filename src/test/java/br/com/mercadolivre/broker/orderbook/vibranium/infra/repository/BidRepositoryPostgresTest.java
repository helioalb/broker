package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;

@SpringBootTest
public class BidRepositoryPostgresTest {

    @Autowired
    private BidRepository repository;

    @Test
    void top() {
        String walletCode = "244f5744-2630-4b5a-9ae0-617d1a6c5d30";
        repository.save(buildBid(walletCode, "1", "1"));
        repository.save(buildBid(walletCode, "1", "2"));
        repository.save(buildBid(walletCode, "1", "2"));

        assertEquals(new BigDecimal("2.0000"), repository.top().get().getPrice());
    }

    private Bid buildBid(String walletCode, String quantity, String price) {
        return new Bid(walletCode, new BigDecimal(quantity), new BigDecimal(price));
    }
}
