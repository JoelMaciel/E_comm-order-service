package com.joel.order_service.application.mapper;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.application.dtos.request.AddressRequest;
import com.joel.order_service.application.dtos.request.OrderItemRequest;
import com.joel.order_service.application.dtos.request.OrderRequest;
import com.joel.order_service.application.dtos.response.OrderItemResponseDTO;
import com.joel.order_service.application.dtos.response.OrderResponseDTO;
import com.joel.order_service.domain.entities.Address;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.entities.OrderItems;
import com.joel.order_service.domain.enums.OrderStatus;
import com.joel.order_service.infrastructure.adapters.out.jpa.AddressEntity;
import com.joel.order_service.infrastructure.adapters.out.jpa.OrderEntity;
import com.joel.order_service.infrastructure.adapters.out.jpa.OrderItemsEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class OrderMapper {

    public OrderEntity toEntityFromDomain(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .subtotal(order.getSubtotal())
                .freightRate(order.getFreightRate())
                .discount(order.getDiscount())
                .total(order.getTotal())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .trackingNumber(order.getTrackingNumber())
                .invoiceNumber(order.getInvoiceNumber())
                .paymentDate(order.getPaymentDate())
                .shippingDate(order.getShippingDate())
                .deliveryDate(order.getDeliveryDate())
                .creationDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .orderNumber(UUID.randomUUID().toString())
                .items(new ArrayList<>(order.getItems().stream()
                        .map(this::toOrderItemsEntity)
                        .toList()))
                .shippingAddress(toAddressEntity(order.getShippingAddress()))
                .build();
    }

    private AddressEntity toAddressEntity(Address address) {
        if (address == null) return null;

        return AddressEntity.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .referencePoint(address.getReferencePoint())
                .build();
    }

    public OrderItemsEntity toOrderItemsEntity(OrderItems orderItem) {
        return OrderItemsEntity.builder()
                .id(orderItem.getId())
                .name(orderItem.getName())
                .skuCode(orderItem.getSkuCode())
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .weight(orderItem.getWeight())
                .build();
    }

    public Order toDomainFromEntity(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .orderNumber(orderEntity.getOrderNumber())
                .subtotal(orderEntity.getSubtotal())
                .freightRate(orderEntity.getFreightRate())
                .discount(orderEntity.getDiscount())
                .total(orderEntity.getTotal())
                .status(orderEntity.getStatus())
                .trackingNumber(orderEntity.getTrackingNumber())
                .invoiceNumber(orderEntity.getInvoiceNumber())
                .paymentMethod(orderEntity.getPaymentMethod())
                .discount(orderEntity.getDiscount())
                .paymentDate(orderEntity.getPaymentDate())
                .shippingDate(orderEntity.getShippingDate())
                .deliveryDate(orderEntity.getDeliveryDate())
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
                .name(orderItems.getName())
                .skuCode(orderItems.getSkuCode())
                .price(orderItems.getPrice())
                .quantity(orderItems.getQuantity())
                .weight(orderItems.getWeight())
                .build();
    }

    public Order toDomainFromCommand(
            CreateOrderCommand createOrderCommand,
            BigDecimal subtotal,
            BigDecimal freightRate,
            BigDecimal discount,
            BigDecimal total) {
        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .subtotal(subtotal)
                .freightRate(freightRate)
                .discount(discount)
                .total(total)
                .paymentMethod(createOrderCommand.paymentMethod())
                .items(createOrderCommand.items().stream()
                        .map(this::toOrderItemsFromCommand)
                        .toList())
                .status(OrderStatus.PENDING)
                .trackingNumber(UUID.randomUUID().toString())
                .invoiceNumber(UUID.randomUUID().toString())
                .paymentDate(null)
                .shippingDate(null)
                .deliveryDate(null)
                .creationDate(OffsetDateTime.now())
                .updateDate(OffsetDateTime.now())
                .shippingAddress(createOrderCommand.shippingAddress())
                .build();
    }

    private OrderItems toOrderItemsFromCommand(CreateOrderItemsCommand createOrderItemsCommand) {
        return OrderItems.builder()
                .skuCode(createOrderItemsCommand.skuCode())
                .name(createOrderItemsCommand.name())
                .price(createOrderItemsCommand.price())
                .quantity(createOrderItemsCommand.quantity())
                .weight(createOrderItemsCommand.weight())
                .build();
    }

    public CreateOrderCommand toCommandFromRequest(OrderRequest orderRequest) {
        List<CreateOrderItemsCommand> items = orderRequest.getItems().stream()
                .map(this::toCreateOrderItemsCommand)
                .toList();

        Address shippingAddressDomain = toAddressDomain(orderRequest.getShippingAddress());

        return CreateOrderCommand.builder()
                .items(items)
                .paymentMethod(orderRequest.getPaymentMethod())
                .shippingAddress(shippingAddressDomain)
                .discountTypes(orderRequest.getDiscountTypes())
                .customerId(orderRequest.getCustomerId())
                .build();
    }

    private Address toAddressDomain(AddressRequest addressRequest) {
        return Address.builder()
                .street(addressRequest.getStreet())
                .number(addressRequest.getNumber())
                .complement(addressRequest.getComplement())
                .district(addressRequest.getDistrict())
                .city(addressRequest.getCity())
                .state(addressRequest.getState())
                .zipCode(addressRequest.getZipCode())
                .country(addressRequest.getCountry())
                .referencePoint(addressRequest.getReferencePoint())
                .build();
    }

    private CreateOrderItemsCommand toCreateOrderItemsCommand(OrderItemRequest orderItemRequest) {
        return CreateOrderItemsCommand.builder()
                .skuCode(orderItemRequest.getSkuCode())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .weight(orderItemRequest.getWeight())
                .build();
    }

    public OrderResponseDTO toDTOFromDomain(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .creationDate(order.getCreationDate())
                .updateDate(order.getUpdateDate())
                .subtotal(order.getSubtotal())
                .freightRate(order.getFreightRate())
                .total(order.getTotal())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .trackingNumber(order.getTrackingNumber())
                .invoiceNumber(order.getInvoiceNumber())
                .items(order.getItems().stream()
                        .map(this::toOrderItemResponseDTOFromDomain)
                        .toList())
                .discount(order.getDiscount())
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
