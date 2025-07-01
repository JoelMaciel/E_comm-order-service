package com.joel.order_service.application.validator;

import com.joel.order_service.application.dtos.response.StockDTO;
import com.joel.order_service.domain.entities.Order;
import com.joel.order_service.domain.entities.OrderItems;
import com.joel.order_service.domain.exceptions.ProductOutOfStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    public static final String PRODUCTS_ARE_NOT_IN_STOCK = "None of the products were found in the stock service.";
    private final WebClient webClient;

    @Value("${app.stock-service.url}")
    private String stockServiceUrl;

    public void validate(Order order) {
        validateStockAvailability(order);
    }

    private void validateStockAvailability(Order order) {
        List<String> skuCodes = order.getItems().stream()
                .map(OrderItems::getSkuCode)
                .toList();

        StockDTO[] stockDTOS = webClient.get()
                .uri(stockServiceUrl, uriBuilder -> uriBuilder
                        .queryParam("skuCode", skuCodes)
                        .build())
                .retrieve()
                .bodyToMono(StockDTO[].class)
                .block();

        if (stockDTOS == null || stockDTOS.length == 0) {
            throw new ProductOutOfStockException(PRODUCTS_ARE_NOT_IN_STOCK);
        }

        boolean allProductsInStock = Arrays.stream(stockDTOS)
                .allMatch(StockDTO::isInStock);

        if (!allProductsInStock) {
            throw new ProductOutOfStockException(PRODUCTS_ARE_NOT_IN_STOCK);
        }
    }
}
