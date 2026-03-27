package com.example.springphone.dto;

import java.math.BigDecimal;

public record AppResponse(
    Long id,
    String name,
    String category,
    BigDecimal price,
    String description
) {
}
