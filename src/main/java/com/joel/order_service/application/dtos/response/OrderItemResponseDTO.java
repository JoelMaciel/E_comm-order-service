package com.joel.order_service.application.dtos.response;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItemResponseDTO {
    private UUID id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}