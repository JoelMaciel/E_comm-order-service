package com.joel.order_service.application.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.joel.order_service.domain.enums.OrderStatus;
import com.joel.order_service.domain.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class OrderResponseDTO {

    private UUID id;
    private String orderNumber;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal total;
    private BigDecimal discount;
    private PaymentMethod paymentMethod;
    private OrderStatus status;
    private String trackingNumber;
    private String invoiceNumber;
    private List<OrderItemResponseDTO> items;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss")
    private OffsetDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY HH:mm:ss")
    private OffsetDateTime updateDate;
}
