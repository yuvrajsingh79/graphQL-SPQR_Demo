package com.tricon.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tricon.order.model.Order;

@Repository
public interface orderRepository extends MongoRepository<Order, String>{

}
