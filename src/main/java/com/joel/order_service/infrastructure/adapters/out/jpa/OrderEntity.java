package com.joel.order_service.infrastructure.adapters.out.jpa;

import com.joel.order_service.domain.enums.OrderStatus;
import com.joel.order_service.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String orderNumber;
    private BigDecimal subtotal;
    private BigDecimal freightRate;
    private BigDecimal discount;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String trackingNumber;
    private String invoiceNumber;
    private OffsetDateTime paymentDate;
    private OffsetDateTime shippingDate;
    private OffsetDateTime deliveryDate;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    private OffsetDateTime updateDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemsEntity> items = new ArrayList<>();

    @Embedded
    private AddressEntity shippingAddress;

}
