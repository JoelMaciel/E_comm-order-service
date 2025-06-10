package com.joel.order_service.application.commands;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateOrderItemsCommand(
        String skuCode,
        String name,
        BigDecimal price,
        Integer quantity,
        BigDecimal weight
) {
}
