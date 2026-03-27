package com.example.springphone.service.impl;

import com.example.springphone.dto.PurchaseResponse;
import com.example.springphone.entity.AppEntity;
import com.example.springphone.entity.PurchaseEntity;
import com.example.springphone.entity.UserEntity;
import com.example.springphone.repository.AppRepository;
import com.example.springphone.repository.PurchaseRepository;
import com.example.springphone.repository.UserRepository;
import com.example.springphone.service.PurchaseService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final AppRepository appRepository;

    public PurchaseServiceImpl(
        PurchaseRepository purchaseRepository,
        UserRepository userRepository,
        AppRepository appRepository
    ) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.appRepository = appRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse purchaseApp(Long userId, Long appId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        AppEntity app = appRepository.findById(appId)
            .orElseThrow(() -> new IllegalArgumentException("App not found: " + appId));

        if (purchaseRepository.existsByUser_IdAndApp_Id(userId, appId)) {
            throw new IllegalStateException("User " + userId + " already purchased app " + appId);
        }

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setUser(user);
        purchase.setApp(app);
        purchase.setPurchaseTime(LocalDateTime.now());

        PurchaseEntity savedPurchase = purchaseRepository.save(purchase);
        return toResponse(savedPurchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getPurchasedApps(Long userId) {
        userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        return purchaseRepository.findByUser_IdOrderByPurchaseTimeDesc(userId)
            .stream()
            .map(this::toResponse)
            .toList();
    }

    private PurchaseResponse toResponse(PurchaseEntity purchase) {
        return new PurchaseResponse(
            purchase.getId(),
            purchase.getApp().getId(),
            purchase.getApp().getName(),
            purchase.getApp().getCategory(),
            purchase.getApp().getPrice(),
            purchase.getPurchaseTime()
        );
    }
}
