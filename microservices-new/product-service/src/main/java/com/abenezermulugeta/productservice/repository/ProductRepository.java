package com.abenezermulugeta.productservice.repository;

import com.abenezermulugeta.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
