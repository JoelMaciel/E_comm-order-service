package com.joel.order_service.application.commands;

import com.joel.order_service.domain.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCalculationContext(
        BigDecimal subtotal,
        BigDecimal freightRate,
        List<CreateOrderItemsCommand> items,
        PaymentMethod paymentMethod,
        UUID customerId,
        BigDecimal currentDiscount
) {
    public OrderCalculationContext withCurrentDiscount(BigDecimal newDiscount) {
        return new OrderCalculationContext(
                this.subtotal,
                this.freightRate,
                this.items,
                this.paymentMethod,
                this.customerId,
                newDiscount
        );
    }
}