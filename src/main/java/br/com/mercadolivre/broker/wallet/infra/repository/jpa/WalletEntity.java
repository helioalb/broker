package br.com.mercadolivre.broker.wallet.infra.repository.jpa;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletEntity {
    public WalletEntity(String code) {
        this.code = code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "wallet")
    private Set<PartitionEntity> partitions;
}
