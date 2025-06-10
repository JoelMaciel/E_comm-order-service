package com.joel.order_service.application.dtos.request;

import com.joel.order_service.domain.enums.DiscountType;
import com.joel.order_service.domain.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotNull
    @Size(min = 1)
    private List<@Valid OrderItemRequest> items;

    @NotNull
    private PaymentMethod paymentMethod;

    private List<DiscountType> discountTypes;

    @NotNull
    private UUID customerId;

    @Valid
    @NotNull
    private AddressRequest shippingAddress;
}
