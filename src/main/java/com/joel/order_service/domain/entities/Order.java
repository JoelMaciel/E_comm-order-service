package com.joel.order_service.domain.entities;

import lombok.*;

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
    private List<OrderItems> items;
    private OffsetDateTime creationDate;
    private OffsetDateTime updateDate;
}
