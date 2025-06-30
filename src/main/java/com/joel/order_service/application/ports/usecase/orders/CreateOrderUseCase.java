package com.joel.order_service.application.ports.usecase.orders;

import com.joel.order_service.application.commands.CreateOrderCommand;
import com.joel.order_service.domain.entities.Order;

public interface CreateOrderUseCase {

    Order execute(CreateOrderCommand createOrderCommand);
}
