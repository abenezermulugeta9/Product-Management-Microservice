package com.abenezermulugeta.orderservice.service;

import com.abenezermulugeta.orderservice.model.Order;
import com.abenezermulugeta.orderservice.model.OrderLineItems;
import com.abenezermulugeta.orderservice.repository.OrderRepository;
import com.abenezermulugeta.orderservice.service.dto.InventoryResponse;
import com.abenezermulugeta.orderservice.service.dto.OrderLineItemsDto;
import com.abenezermulugeta.orderservice.service.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // call Inventory Service and place order if product is in stock
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean areAllProductsFound = Arrays
                .stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);

        if (areAllProductsFound) orderRepository.save(order);
        else throw new IllegalArgumentException("One or more Products are not in stock, please try again.");
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
