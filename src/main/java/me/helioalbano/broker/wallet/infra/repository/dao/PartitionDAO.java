package me.helioalbano.broker.wallet.infra.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.helioalbano.broker.common.enums.Asset;

public interface PartitionDAO extends JpaRepository<PartitionEntity, Long> {
    PartitionEntity getByWalletIdAndAsset(Long id, Asset asset);

    @Query("SELECT p FROM PartitionEntity p WHERE p.wallet.id = ?1")
    List<PartitionEntity> findByWalletId(Long id);
}
