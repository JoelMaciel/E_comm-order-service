package com.joel.order_service.application.adapters.impl;

import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.application.ports.usecase.orders.FreightCalculator;
import com.joel.order_service.domain.entities.Address;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FreightCalculatorImpl implements FreightCalculator {

    private static final BigDecimal FREIGHT_PER_KG = BigDecimal.valueOf(3.0);
    private static final BigDecimal MIN_FREIGHT = BigDecimal.valueOf(10.0);
    private static final BigDecimal MAX_FREIGHT = BigDecimal.valueOf(150.0);

    @Override
    public BigDecimal calculate(Address shippingAddress, List<CreateOrderItemsCommand> items) {
        if (items == null || items.isEmpty()) return BigDecimal.ZERO;

        BigDecimal totalWeight = calculateTotalWeight(items);
        BigDecimal baseFreight = totalWeight.multiply(FREIGHT_PER_KG);

        return applyFreightLimits(baseFreight);
    }

    private BigDecimal calculateTotalWeight(List<CreateOrderItemsCommand> items) {
        return items.stream()
                .map(item -> item.weight()
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    private BigDecimal applyFreightLimits(BigDecimal freight) {
        if (freight.compareTo(MIN_FREIGHT) < 0) {
            return MIN_FREIGHT;
        }
        if (freight.compareTo(MAX_FREIGHT) > 0) {
            return MAX_FREIGHT;
        }
        return freight;
    }

}