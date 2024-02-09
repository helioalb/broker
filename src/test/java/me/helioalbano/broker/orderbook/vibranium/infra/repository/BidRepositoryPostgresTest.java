package me.helioalbano.broker.orderbook.vibranium.infra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import me.helioalbano.broker.orderbook.vibranium.domain.entity.Bid;
import me.helioalbano.broker.orderbook.vibranium.domain.repository.BidRepository;

@SpringBootTest
@ActiveProfiles("test")
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

    @Test
    void delete() {
        String walletCode = "244f5744-2630-4b5a-9ae0-617d1a6c5d30";
        Bid bid = repository.save(buildBid(walletCode, "1", "1"));
        repository.delete(bid);
        assertTrue(repository.findById(bid.getId()).isEmpty());
    }

    private Bid buildBid(String walletCode, String quantity, String price) {
        return new Bid(walletCode, new BigDecimal(quantity), new BigDecimal(price));
    }
}
