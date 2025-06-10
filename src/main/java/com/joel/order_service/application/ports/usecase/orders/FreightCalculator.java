package com.joel.order_service.application.ports.usecase.orders;

import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.domain.entities.Address;

import java.math.BigDecimal;
import java.util.List;

public interface FreightCalculator {
    BigDecimal calculate(Address shippingAddress, List<CreateOrderItemsCommand> items);
}