package com.tricon.customer.service;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tricon.customer.model.Customer;
import com.tricon.customer.model.CustomerES;
import com.tricon.customer.repository.customerElasticRepository;
import com.tricon.customer.repository.customerRepository;

@Component
public class EsLoader {
	@Autowired
	private customerRepository customerRepository;
	
	@Autowired
	private customerElasticRepository cer;
	
//	@PostConstruct
//	public void loadData() {
//		
////		List<Customer> custList = customerRepository.findAll();
////		List<CustomerES> elasList = new LinkedList<CustomerES>();
////		custList.forEach(customer -> {
////			elasList.add(new CustomerES(customer.getid(),customer.getName(),
////					customer.getEmailId(),customer.getPassword(),customer.getAddress()));
////		});
////		cer.saveAll(elasList);
//		System.out.println("Customer Data loaded to mongoDB");
//	}
}
