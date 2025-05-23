package com.joel.order_service.application.mapper;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.application.dtos.request.OrderItemRequest;
import com.joel.order_service.application.dtos.request.OrderRequest;
import com.joel.order_service.application.dtos.response.OrderItemResponseDTO;
import com.joel.order_service.application.dtos.response.OrderResponseDTO;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.entities.OrderItems;
import com.joel.order_service.infrastructure.adapters.jpa.OrderEntity;
import com.joel.order_service.infrastructure.adapters.jpa.OrderItemsEntity;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class OrderMapper {

    public OrderEntity toEntityFromDomain(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .creationDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .orderNumber(UUID.randomUUID().toString())
                .items(new ArrayList<>(order.getItems().stream()
                        .map(this::toOrderItemsEntity)
                        .toList()))
                .build();
    }

    public OrderItemsEntity toOrderItemsEntity(OrderItems orderItem) {
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
                .creationDate(orderEntity.getCreationDate())
                .updateDate(orderEntity.getUpdateDate())
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

    public Order toDomainFromCommand(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .items(createOrderCommand.items().stream()
                        .map(this::toOrderItemsFromCommand)
                        .toList())
                .build();
    }

    private OrderItems toOrderItemsFromCommand(CreateOrderItemsCommand createOrderItemsCommand) {
        return OrderItems.builder()
                .skuCode(createOrderItemsCommand.skuCode())
                .price(createOrderItemsCommand.price())
                .quantity(createOrderItemsCommand.quantity())
                .build();
    }

    public CreateOrderCommand toCommandFromRequest(OrderRequest orderRequest) {
        List<CreateOrderItemsCommand> items = orderRequest.getItems().stream()
                .map(this::toCreateOrderItemsCommand)
                .toList();

        return new CreateOrderCommand(items);
    }

    private CreateOrderItemsCommand toCreateOrderItemsCommand(OrderItemRequest orderItemRequest) {
        return CreateOrderItemsCommand.builder()
                .skuCode(orderItemRequest.getSkuCode())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();
    }

    public OrderResponseDTO toDTOFromDomain(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .creationDate(order.getCreationDate())
                .updateDate(order.getUpdateDate())
                .items(order.getItems().stream()
                        .map(this::toOrderItemResponseDTOFromDomain)
                        .toList())
                .build();

    }

    private OrderItemResponseDTO toOrderItemResponseDTOFromDomain(OrderItems orderItems) {
        return OrderItemResponseDTO.builder()
                .id(orderItems.getId())
                .skuCode(orderItems.getSkuCode())
                .price(orderItems.getPrice())
                .quantity(orderItems.getQuantity())
                .build();
    }
}
