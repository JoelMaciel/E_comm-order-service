package com.joel.order_service.domain.enums;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.application.commands.CreateOrderItemsCommand;
import com.joel.order_service.application.commands.OrderCalculationContext;

import java.math.BigDecimal;
import java.util.List;

public enum DiscountType {

    CUPON_10(0.10, "10% discount coupon") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return true;
        }
    },
    CUPON_15(0.15, "15% discount coupon ") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return true;
        }
    },
    PROMOTION_20(0.20, "Promoção 20% de desconto") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return calculateSubtotal(command.items())
                    .compareTo(new BigDecimal("100")) > 0;
        }
    },
    VOLUME_5(0.05, "Volume 5% para +3 items") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return command.items().size() >= 3;
        }
    },
    VOLUME_10(0.10, "Volume 10% para +5 items") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return command.items().size() >= 5;
        }
    },
    FREE_SHIPPING(0.00, "Frete grátis") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return true;
        }

        @Override
        public BigDecimal applyDiscount(BigDecimal value, OrderCalculationContext context) {
            return BigDecimal.ZERO;
        }
    },
    PAYMENT_METHOD_PIX(0.05, "5% de desconto no PIX") {
        @Override
        public boolean isApplicable(CreateOrderCommand command) {
            return command.paymentMethod() == PaymentMethod.PIX;
        }
    };

    private final BigDecimal percentagem;

    DiscountType(double percentagem, String description) {
        this.percentagem = BigDecimal.valueOf(percentagem);
    }

    public BigDecimal applyDiscount(BigDecimal value, OrderCalculationContext context) {
        return value.multiply(percentagem);
    }

    public abstract boolean isApplicable(CreateOrderCommand command);


    private static BigDecimal calculateSubtotal(List<CreateOrderItemsCommand> items) {
        return items.stream()
                .map(item -> item.price()
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}