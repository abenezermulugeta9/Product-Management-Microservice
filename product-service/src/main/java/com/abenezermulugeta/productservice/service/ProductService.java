package com.abenezermulugeta.productservice.service;

import com.abenezermulugeta.productservice.service.dto.ProductRequest;
import com.abenezermulugeta.productservice.service.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();

}
