package com.tricon.customer.service;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tricon.customer.model.Customer;
import com.tricon.customer.repository.customerRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
@GraphQLApi
public class CustomerService {
	@Autowired
	private customerRepository customerRepository;
	
    private final ConcurrentMultiMap<String, FluxSink<Customer>> subscribers = new ConcurrentMultiMap<>();

	
	@GraphQLQuery(name = "getAllCustomers")
	public List<Customer> getAllCustomers(){
		return customerRepository.findAll();
	}
	
	@GraphQLQuery(name = "getCustomerById")
	public Customer getCustomerById(@GraphQLArgument(name="id") String id) {
		return customerRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Customer with id:"+id+" not found!"));
	}
	
	@GraphQLMutation(name = "addCustomer")
	public Customer addCustomer(@GraphQLArgument(name="name") String name,
			@GraphQLArgument(name="emailId") String emailId,@GraphQLArgument(name="password") String password,
			@GraphQLArgument(name="address") String address) {
		return customerRepository.save(new Customer(name,emailId, password, address));
	}
	
	@GraphQLSubscription(name = "monitorCustomer")
	public Publisher<Customer> customerMonitor(String code){
		return Flux.create(subscriber -> subscribers.add(code, subscriber.
				onDispose(() -> subscribers.remove(code, subscriber))), FluxSink.OverflowStrategy.LATEST);
	}
	
	@GraphQLMutation(name = "updateCustomer")
	public Customer updateCustomer(String id, String name, String emailId, String password, String address) {
		Customer customer = getCustomerById(id);
		customer.setName(name);
		customer.setEmailId(emailId);
		customer.setAddress(address);
		customer.setPassword(password);
		subscribers.get("1").forEach(subscriber -> subscriber.next(customer));
		return customerRepository.save(customer);
	}
	
//	public String deleteCustomer(String id) {
//		Customer customer = getCustomerById(id);
//		customerRepository.delete(customer);
//		return "Deleted Successfully";
//	} 

}
