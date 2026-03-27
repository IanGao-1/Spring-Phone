package com.example.springphone.service;

import com.example.springphone.dto.PurchaseResponse;
import java.util.List;

public interface PurchaseService {

    PurchaseResponse purchaseApp(Long userId, Long appId);

    List<PurchaseResponse> getPurchasedApps(Long userId);
}
