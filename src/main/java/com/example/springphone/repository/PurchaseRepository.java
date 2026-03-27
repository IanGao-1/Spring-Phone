package com.example.springphone.repository;

import com.example.springphone.entity.PurchaseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    boolean existsByUser_IdAndApp_Id(Long userId, Long appId);

    List<PurchaseEntity> findByUser_IdOrderByPurchaseTimeDesc(Long userId);
}
