package com.joel.order_service.infrastructure.adapters.api.controllers;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.dtos.request.OrderRequest;
import com.joel.order_service.application.dtos.response.OrderResponseDTO;
import com.joel.order_service.application.mapper.OrderMapper;
import com.joel.order_service.application.ports.usecase.products.CreateOrderUseCase;
import com.joel.order_service.domain.entities.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO registerOrder(@RequestBody @Valid OrderRequest orderRequest) {
        CreateOrderCommand orderCommand = orderMapper.toCommandFromRequest(orderRequest);
        Order order = createOrderUseCase.execute(orderCommand);
        return orderMapper.toDTOFromDomain(order);
    }
}
