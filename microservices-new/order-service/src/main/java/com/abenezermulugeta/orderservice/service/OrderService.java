package com.abenezermulugeta.orderservice.service;

import com.abenezermulugeta.orderservice.service.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
