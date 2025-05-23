package com.joel.order_service.domain.entities;

import com.joel.order_service.domain.enums.OrderStatus;
import com.joel.order_service.domain.enums.PaymentMethod;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID id;
    private String orderNumber;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal discount;
    private BigDecimal total;
    private PaymentMethod paymentMethod;
    private List<OrderItems> items;
    private OrderStatus status;
    private String trackingNumber;
    private String invoiceNumber;
    private OffsetDateTime paymentDate;
    private OffsetDateTime shippingDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime creationDate;
    private OffsetDateTime updateDate;
    private Address shippingAddress;
}
