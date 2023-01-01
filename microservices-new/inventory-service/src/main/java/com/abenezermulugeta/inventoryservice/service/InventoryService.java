package com.abenezermulugeta.inventoryservice.service;

import com.abenezermulugeta.inventoryservice.service.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
