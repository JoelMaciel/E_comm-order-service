package com.joel.order_service.application.mapper;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.entities.OrderItems;
import com.joel.order_service.infrastructure.adapters.jpa.OrderEntity;
import com.joel.order_service.infrastructure.adapters.jpa.OrderItemsEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public OrderEntity toEntityFromDomain(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .orderNumber(UUID.randomUUID().toString())
                .items(order.getItems().stream()
                        .map(this::toOrderItemsEntity)
                        .toList())
                .build();
    }

    private OrderItemsEntity toOrderItemsEntity(OrderItems orderItem) {
        return OrderItemsEntity.builder()
                .id(orderItem.getId())
                .skuCode(orderItem.getSkuCode())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public Order toDomainFromEntity(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .orderNumber(orderEntity.getOrderNumber())
                .items(orderEntity.getItems().stream()
                        .map(this::toOrderItemsDomain)
                        .toList())
                .build();

    }

    private OrderItems toOrderItemsDomain(OrderItemsEntity orderItems) {
        return OrderItems.builder()
                .id(orderItems.getId())
                .skuCode(orderItems.getSkuCode())
                .price(orderItems.getPrice())
                .quantity(orderItems.getQuantity())
                .build();
    }
}
