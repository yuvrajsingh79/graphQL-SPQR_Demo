package com.tricon.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tricon.customer.model.Customer;
import com.tricon.customer.model.CustomerES;
import com.tricon.customer.repository.customerElasticRepository;
import com.tricon.customer.repository.customerRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.annotations.GraphQLSubscription;
import io.leangen.graphql.execution.ResolutionEnvironment;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import io.leangen.graphql.spqr.spring.util.ConcurrentMultiMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
@GraphQLApi
public class CustomerService {
	@Autowired
	private customerRepository customerRepository;
	
	@Autowired
	private customerElasticRepository cer;
	
    private final ConcurrentMultiMap<String, FluxSink<String>> subscribers = new ConcurrentMultiMap<>();

	@GraphQLQuery(name = "getAllCustomers")
	public Page<Customer> getAllCustomers(@GraphQLArgument(name = "offset") int offset, 
			@GraphQLArgument(name = "limit") int limit){
		return customerRepository.findAll(PageRequest.of(offset, limit));
	}
	
	@GraphQLQuery(name = "getCustomers")
	public List<Customer> getCustomers(@GraphQLArgument(name = "current") int current, 
			@GraphQLArgument(name = "limit") int limit,
			@GraphQLArgument(name = "prev") boolean prev){
		if(!prev)
			return customerRepository.findAll().stream().skip((int) current).limit(limit).collect(Collectors.toList());
		
		return customerRepository.findAll().stream().skip((int) current-limit-1).limit(limit).collect(Collectors.toList());
	}
	
	@GraphQLQuery(name = "getESCustomers")
	public Iterable<CustomerES> getESCustomers(){
		return cer.findAll();
	}
	
	@GraphQLQuery(name = "getESCustomerByName")
	public Iterable<CustomerES> getESCustomerByName(@GraphQLArgument(name = "name") String name){
		return cer.findByName(name);
	}
	
	@GraphQLQuery(name = "getESCustomerById")
	public Optional<CustomerES> getESCustomerById(@GraphQLArgument(name = "id") String id){
		return cer.findById(id);
	}
	
	@GraphQLQuery(name = "getESCustomerByAddress")
	public Iterable<CustomerES> getESCustomerByAddress(@GraphQLArgument(name = "address") String address){
		return cer.findByAddress(address);
	}
	
	@GraphQLQuery(name = "getCustomerById")
	public Customer getCustomerById(@GraphQLArgument(name="id") String id) {
		return customerRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Customer with id:"+id+" not found!"));
	}
	
	@Transactional
	@GraphQLMutation(name = "addCustomer")
	public Customer addCustomer(@GraphQLArgument(name="name") String name,
			@GraphQLArgument(name="emailId") String emailId,@GraphQLArgument(name="password") String password,
			@GraphQLArgument(name="address") String address) {
		Customer customer = new Customer(name,emailId, password, address);
		CustomerES cust = new CustomerES(name,emailId, password, address);
		cer.save(cust);
		return customerRepository.save(customer);
	}
	
	@GraphQLSubscription(name = "monitorCustomer")
	public Publisher<String> customerMonitor(String code){
//		return Flux.create(subscriber -> subscribers.add(code, subscriber.
//				onDispose(() -> {
////					subscribers.remove(code, subscriber);
//				})), FluxSink.OverflowStrategy.LATEST);
		return Flux.create(subscriber -> subscribers
				.add(code, subscriber
						.onDispose(() -> subscribers.
								remove(code, subscriber))));
	}
	
	@Transactional
	@GraphQLMutation(name = "updateCustomer")
	public Customer updateCustomer(String id, String name, String emailId, String password, String address, 
			@GraphQLEnvironment ResolutionEnvironment env) {
		Customer customer = getCustomerById(id);
		customer.setName(name);
		customer.setEmailId(emailId);
		customer.setAddress(address);
		customer.setPassword(password);
		subscribers.get("1").forEach(subscriber -> {
			subscriber.next(env.fields.toString());
//			subscribrs.get("1").forEach(subs -> subs.next(env.fields.toString()));
		});
//		Optional<CustomerES> cust = cer.findById(id);
//		cust.setName(name);
//		customer.setEmailId(emailId);
//		customer.setAddress(address);
//		customer.setPassword(password);
//		cer.save(customer);
		return customerRepository.save(customer);
	}
	
	@Transactional
	@GraphQLMutation(name = "deleteCustomer")
	public String deleteCustomer(String id, @GraphQLEnvironment ResolutionEnvironment env) {
		Customer customer = getCustomerById(id);
		subscribers.get("1").forEach(subscriber -> {
			subscriber.next(env.fields.toString());
		});
		customerRepository.delete(customer);
//		cer.delete(customer);
		return "Deleted Successfully";
	} 

}
