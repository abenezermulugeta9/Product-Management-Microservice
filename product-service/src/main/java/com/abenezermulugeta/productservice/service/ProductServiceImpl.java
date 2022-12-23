package com.abenezermulugeta.productservice.service;

import com.abenezermulugeta.productservice.model.Product;
import com.abenezermulugeta.productservice.repository.ProductRepository;
import com.abenezermulugeta.productservice.service.dto.ProductRequest;
import com.abenezermulugeta.productservice.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements  ProductService {
    private final ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        /*
                By using lombok's builder() method we eliminate the need for creating
                a custom adapter class that converts dtos to domain models.
         */
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is created", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

       return products.stream()
                .map(product -> mapToProductResponse(product))
                .toList();
    }

    private static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
