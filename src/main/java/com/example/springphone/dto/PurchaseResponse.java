package com.example.springphone.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseResponse(
    Long purchaseId,
    Long appId,
    String appName,
    String category,
    BigDecimal price,
    LocalDateTime purchaseTime
) {
}
