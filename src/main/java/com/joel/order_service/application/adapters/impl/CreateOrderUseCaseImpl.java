package com.joel.order_service.application.adapters.impl;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.application.commands.OrderCalculationContext;
import com.joel.order_service.application.mapper.OrderMapper;
import com.joel.order_service.application.ports.usecase.orders.CreateOrderUseCase;
import com.joel.order_service.application.ports.usecase.orders.FreightCalculator;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.enums.DiscountType;
import com.joel.order_service.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final FreightCalculator freightCalculator;

    @Override
    public Order execute(CreateOrderCommand createOrderCommand) {
        BigDecimal subtotal = calculateSubtotal(createOrderCommand.items());

        BigDecimal freightRate = freightCalculator
                .calculate(createOrderCommand.shippingAddress(), createOrderCommand.items());

        OrderCalculationContext context = new OrderCalculationContext(
                subtotal,
                freightRate,
                createOrderCommand.items(),
                createOrderCommand.paymentMethod(),
                createOrderCommand.customerId(),
                BigDecimal.ZERO
        );

        BigDecimal totalDiscount = applyAllDiscounts(createOrderCommand, context);

        if (createOrderCommand.discountTypes() != null &&
                createOrderCommand.discountTypes().contains(DiscountType.FREE_SHIPPING)) {
            freightRate = BigDecimal.ZERO;
        }


        BigDecimal total = subtotal.add(freightRate).subtract(totalDiscount);

        Order order = mapper.toDomainFromCommand(createOrderCommand, subtotal, freightRate, totalDiscount, total);
        return orderRepository.save(order);
    }


    private BigDecimal calculateSubtotal(List<CreateOrderItemsCommand> items) {
        return items.stream()
                .map(item -> item.price()
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal applyAllDiscounts(CreateOrderCommand command, OrderCalculationContext context) {
        BigDecimal totalDiscount = BigDecimal.ZERO;

        if (command.discountTypes() != null) {
            for (DiscountType discount : command.discountTypes()) {
                if (discount != null && discount.isApplicable(command)) {
                    BigDecimal discountValue = discount.applyDiscount(context.subtotal(), context);
                    totalDiscount = totalDiscount.add(discountValue);
                    context = context.withCurrentDiscount(totalDiscount);
                }
            }
        }

        return totalDiscount;
    }

}
