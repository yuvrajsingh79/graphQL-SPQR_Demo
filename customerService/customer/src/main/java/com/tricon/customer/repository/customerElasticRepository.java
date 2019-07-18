package com.tricon.customer.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.tricon.customer.model.CustomerES;

@Repository
public interface customerElasticRepository extends ElasticsearchRepository<CustomerES, String> {

	Iterable<CustomerES> findByName(String name);

	Iterable<CustomerES> findByAddress(String address);

}
