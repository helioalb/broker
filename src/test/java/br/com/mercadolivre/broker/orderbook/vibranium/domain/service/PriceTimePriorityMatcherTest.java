package br.com.mercadolivre.broker.orderbook.vibranium.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Ask;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.entity.Bid;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.factory.RepositoryFactory;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.AskRepository;
import br.com.mercadolivre.broker.orderbook.vibranium.domain.repository.BidRepository;
import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import br.com.mercadolivre.broker.wallet.domain.repository.WalletRepository;
import br.com.mercadolivre.broker.wallet.infra.repository.WalletRepositoryMemory;

public class PriceTimePriorityMatcherTest {
    private String askWalletCode;
    private String bidWalletCode;
    private WalletRepository walletRepository;

    @BeforeEach
    void setup() {
        askWalletCode = "askask63-cb68-4335-b5a3-25a269564268";
        bidWalletCode = "bidbid63-cb68-4335-b5a3-25a269564268";
        walletRepository = new WalletRepositoryMemory();
    }


    @Test
    void processBid_WithPriceAndQuantityMatchExactly () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processBid(bidWalletCode, new BigDecimal("1"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("999"), bidBRL);
        assertEquals(new BigDecimal("1001"), bidVIB);
        assertEquals(new BigDecimal("1001"), askBRL);
        assertEquals(new BigDecimal("999"), askVIB);
    }

    @Test
    void processBid_WithBidPriceEqualsAskPriceAndBidQuantityGreatherThanAskQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processBid(bidWalletCode, new BigDecimal("2"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("998"), bidBRL);
        assertEquals(new BigDecimal("1002"), bidVIB);
        assertEquals(new BigDecimal("1002"), askBRL);
        assertEquals(new BigDecimal("998"), askVIB);
    }

    @Test
    void processBid_WithBidPriceEqualsAskPriceAndBidQuantityLessThanAskQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processBid(bidWalletCode, new BigDecimal("0.5"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("999.5"), bidBRL);
        assertEquals(new BigDecimal("1000.5"), bidVIB);
        assertEquals(new BigDecimal("1000.5"), askBRL);
        assertEquals(new BigDecimal("999.5"), askVIB);
    }

    @Test
    void processBid_WithBidPriceLessThanAskPriceAndBidQuantityEqualsAskQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processBid(bidWalletCode, new BigDecimal("1"), new BigDecimal("0.5"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("1000"), bidBRL);
        assertEquals(new BigDecimal("1000"), bidVIB);
        assertEquals(new BigDecimal("1000"), askBRL);
        assertEquals(new BigDecimal("1000"), askVIB);
    }

    @Test
    void processAsk_WithPriceAndQuantityMatchExactly () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processAsk(askWalletCode, new BigDecimal("1"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("999"), bidBRL);
        assertEquals(new BigDecimal("1001"), bidVIB);
        assertEquals(new BigDecimal("1001"), askBRL);
        assertEquals(new BigDecimal("999"), askVIB);
    }

    @Test
    void processAsk_WithAskPriceEqualsBidPriceAndAskQuantityGreatherThanBidQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processAsk(askWalletCode, new BigDecimal("2"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("998"), bidBRL);
        assertEquals(new BigDecimal("1002"), bidVIB);
        assertEquals(new BigDecimal("1002"), askBRL);
        assertEquals(new BigDecimal("998"), askVIB);
    }

    @Test
    void processAsk_WithAskPriceEqualsBidPriceAndAskQuantityLessThanBidQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processAsk(askWalletCode, new BigDecimal("0.5"), new BigDecimal("1"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("999.5"), bidBRL);
        assertEquals(new BigDecimal("1000.5"), bidVIB);
        assertEquals(new BigDecimal("1000.5"), askBRL);
        assertEquals(new BigDecimal("999.5"), askVIB);
    }

    @Test
    void processAsk_WithAskPriceLessThenBidPriceAndAskQuantityEqualsBidQuantity () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processAsk(askWalletCode, new BigDecimal("1"), new BigDecimal("0.5"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("999.5"), bidBRL);
        assertEquals(new BigDecimal("1001"), bidVIB);
        assertEquals(new BigDecimal("1000.5"), askBRL);
        assertEquals(new BigDecimal("999"), askVIB);
    }

    @Test
    void processAsk_WithAskPriceGreaterThenBidPrice () {
        RepositoryFactory repositoryFactory = buildRepositoryFactory(askWalletCode,
            bidWalletCode, walletRepository, "1", "1", "1", "1", "1", "1", "1", "1");

        MatcherEngine priceTimePriority = new PriceTimePriorityMatcher(repositoryFactory);
        priceTimePriority.processAsk(askWalletCode, new BigDecimal("1"), new BigDecimal("2"));

        BigDecimal bidBRL = balanceAfter(walletRepository, bidWalletCode, Asset.BRL);
        BigDecimal bidVIB = balanceAfter(walletRepository, bidWalletCode, Asset.VIB);
        BigDecimal askBRL = balanceAfter(walletRepository, askWalletCode, Asset.BRL);
        BigDecimal askVIB = balanceAfter(walletRepository, askWalletCode, Asset.VIB);

        assertEquals(new BigDecimal("1000"), bidBRL);
        assertEquals(new BigDecimal("1000"), bidVIB);
        assertEquals(new BigDecimal("1000"), askBRL);
        assertEquals(new BigDecimal("1000"), askVIB);
    }

    private BigDecimal balanceAfter(WalletRepository walletRepository,
        String bidWalletCode, Asset asset) {
        return walletRepository.findByCode(bidWalletCode)
                .findPartitionByAsset(asset).getBalance();
    }

    private RepositoryFactory buildRepositoryFactory(String askWalletCode,
        String bidWalletCode, WalletRepository walletRepository,
        String aq1, String ap1, String aq2, String ap2,
        String bq1, String bp1, String bq2, String bp2) {
        RepositoryFactory repositoryFactory = mock(RepositoryFactory.class);


        AskRepository askRepository = buildAskRepository(askWalletCode,
                                                         aq1, ap1, aq2, ap2);
        BidRepository bidRepository = buildBidRepository(bidWalletCode,
                                                         bq1, bp1, bq2, bp2);

        when(repositoryFactory.createWalletRepository()).thenReturn(walletRepository);
        when(repositoryFactory.createAskRepository()).thenReturn(askRepository);
        when(repositoryFactory.createBidRepository()).thenReturn(bidRepository);

        return repositoryFactory;
    }

    private AskRepository buildAskRepository(String walletCode, String q1,
        String p1, String q2, String p2) {
        AskRepository repository = mock(AskRepository.class);
        Ask a1 = new Ask(walletCode, new BigDecimal(q1), new BigDecimal(p1));
        Ask a2 = new Ask(walletCode, new BigDecimal(q2), new BigDecimal(p2));
        when(repository.top()).thenReturn(a1).thenReturn(a2);
        return repository;
    }

    private BidRepository buildBidRepository(String walletCode, String q1,
        String p1, String q2, String p2) {
        BidRepository repository = mock(BidRepository.class);
        Bid b1 = new Bid(walletCode, new BigDecimal(q1), new BigDecimal(p1));
        Bid b2 = new Bid(walletCode, new BigDecimal(q2), new BigDecimal(p2));
        when(repository.top()).thenReturn(b1).thenReturn(b2);
        return repository;
    }

}
