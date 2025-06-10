package com.joel.order_service.domain.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItems {

    private UUID id;
    private String name;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal weight;
}
