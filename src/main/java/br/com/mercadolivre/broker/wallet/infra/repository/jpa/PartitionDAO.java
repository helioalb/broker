package br.com.mercadolivre.broker.wallet.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mercadolivre.broker.wallet.domain.enums.Asset;

public interface PartitionDAO extends JpaRepository<PartitionEntity, Long> {
    PartitionEntity getByWalletIdAndAsset(Long walletId, Asset asset);
}
