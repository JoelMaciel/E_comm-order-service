package com.joel.order_service.application.commands;

import com.joel.order_service.domain.entities.Address;
import com.joel.order_service.domain.enums.DiscountType;
import com.joel.order_service.domain.enums.PaymentMethod;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CreateOrderCommand(
        List<CreateOrderItemsCommand> items,
        PaymentMethod paymentMethod,
        Address shippingAddress,
        List<DiscountType> discountTypes,
        UUID customerId
) {

}
