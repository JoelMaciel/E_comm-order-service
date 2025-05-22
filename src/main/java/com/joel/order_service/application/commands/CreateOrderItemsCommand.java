package com.joel.order_service.application.commands;

import java.math.BigDecimal;

public record CreateOrderItemsCommand(
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
