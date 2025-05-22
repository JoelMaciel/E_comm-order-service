package com.joel.order_service.application.commands;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateOrderCommand(List<CreateOrderItemsCommand> items) {
}
