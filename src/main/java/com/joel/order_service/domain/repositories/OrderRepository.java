package com.joel.order_service.domain.repositories;

import com.joel.order_service.domain.entities.Order;

public interface OrderRepository {

    Order save(Order order);

}
