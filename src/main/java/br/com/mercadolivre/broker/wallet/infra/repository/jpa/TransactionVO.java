package br.com.mercadolivre.broker.wallet.infra.repository.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mercadolivre.broker.wallet.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionVO {
    public TransactionVO(TransactionType type, BigDecimal amount,
                         PartitionEntity partition) {
        this.type = type;
        this.amount = amount;
        this.partition = partition;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private TransactionType type;

    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @ManyToOne
    private PartitionEntity partition;
}
