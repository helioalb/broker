package br.com.mercadolivre.broker.wallet.infra.repository.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.mercadolivre.broker.common.enums.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partitions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartitionEntity {

    public PartitionEntity(Asset asset, WalletEntity wallet) {
        this.asset = asset;
        this.wallet = wallet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset", nullable = false, updatable = false)
    private Asset asset;

    @ManyToOne
    private WalletEntity wallet;

    @OneToMany(mappedBy = "partition")
    private List<TransactionVO> transactions;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
