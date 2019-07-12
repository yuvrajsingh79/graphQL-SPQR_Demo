package com.tricon.customer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tricon.customer.model.Customer;

@Repository
public interface customerRepository extends MongoRepository<Customer, String>{

}
