package com.tricon.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tricon.product.model.Product;

@Repository
public interface productRepository extends MongoRepository<Product, String>{

}
