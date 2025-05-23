package com.joel.order_service.application.adapters.impl;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.mapper.OrderMapper;
import com.joel.order_service.application.ports.usecase.products.CreateOrderUseCase;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    @Override
    public Order execute(CreateOrderCommand createOrderCommand) {
        Order order = mapper.toDomainFromCommand(createOrderCommand);
        return orderRepository.save(order);
    }
}
