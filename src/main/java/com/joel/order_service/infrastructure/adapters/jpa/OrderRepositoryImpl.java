package com.joel.order_service.infrastructure.adapters.jpa;

import com.joel.order_service.application.mapper.OrderMapper;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderMapper mapper;

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = mapper.toEntityFromDomain(order);
        OrderEntity savedEntity = orderJpaRepository.save(orderEntity);
        return mapper.toDomainFromEntity(savedEntity);
    }
}
