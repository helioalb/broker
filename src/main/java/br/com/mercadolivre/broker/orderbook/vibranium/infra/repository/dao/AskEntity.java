package br.com.mercadolivre.broker.orderbook.vibranium.infra.repository.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "asks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskEntity {

    public AskEntity(Long id, String walletCode, BigDecimal quantity, BigDecimal price) {
        this.id = id;
        this.walletCode = walletCode;
        this.quantity = quantity;
        this.price = price;
    }

    public AskEntity(Long id, String walletCode, BigDecimal quantity,
                     BigDecimal price, String tradedWith, BigDecimal tradedQuantity) {
        this.id = id;
        this.walletCode = walletCode;
        this.quantity = quantity;
        this.price = price;
        this.tradedWith = tradedWith;
        this.tradedQuantity = tradedQuantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_code", nullable = false, updatable = false)
    private String walletCode;

    @Column(name = "quantity", nullable = false, updatable = false)
    private BigDecimal quantity;

    @Column(name = "price", nullable = false, updatable = false)
    private BigDecimal price;

    @Column(name = "traded_with", nullable = true, updatable = true)
    private String tradedWith;

    @Column(name = "traded_quantity", nullable = true, updatable = true)
    private BigDecimal tradedQuantity;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
