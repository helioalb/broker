package br.com.mercadolivre.broker.trade.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Id;

import br.com.mercadolivre.broker.common.enums.Asset;
import lombok.Getter;

@Getter
public class Trade {

    @Id
    private String id;
    private Asset assetTraded;
    private Asset assetUsedToPay;
    private BigDecimal amount;
    private BigDecimal quantity;
    private String sellerWalletCode;
    private String buyerWalletCode;
    private LocalDateTime createdAt;

    public Trade(Asset assetTraded,
                 Asset assetUsedToPay,
                 String buyerWalletCode,
                 String sellerWalletCode,
                 BigDecimal quantity,
                 BigDecimal amount) {
        this.assetTraded = assetTraded;
        this.assetUsedToPay = assetUsedToPay;
        setBuyerWalletCode(buyerWalletCode);
        setSellerWalletCode(sellerWalletCode);
        setQuantity(quantity);
        setAmount(amount);
        this.createdAt = LocalDateTime.now();
    }

    private void setAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("trade cannot be negative");
        this.amount = amount;
    }

    private void setQuantity(BigDecimal quantity) {
        if (quantity.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("quantity cannot be negative");
        this.quantity = quantity;
    }

    static final int UUID_LENGTH = 36;

    private void setSellerWalletCode(String sellerWalletCode) {
        if (sellerWalletCode.length() != UUID_LENGTH)
            throw new IllegalArgumentException("wallet code with invalid length");
        this.sellerWalletCode = sellerWalletCode;
    }

    private void setBuyerWalletCode(String buyerWalletCode) {
        if (buyerWalletCode.length() != UUID_LENGTH)
            throw new IllegalArgumentException("wallet code with invalid length");
        this.buyerWalletCode = buyerWalletCode;
    }

}
