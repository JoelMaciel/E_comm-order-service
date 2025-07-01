package com.joel.order_service.domain.exceptions;


public class ProductOutOfStockException extends BusinessException {

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
