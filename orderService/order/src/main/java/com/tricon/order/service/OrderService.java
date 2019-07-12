package com.tricon.order.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tricon.order.model.Customer;
//import com.tricon.order.model.Customer;
import com.tricon.order.model.Order;
import com.tricon.order.model.Product;
import com.tricon.order.repository.orderRepository;

import io.aexp.nodes.graphql.Argument;
import io.aexp.nodes.graphql.Arguments;
import io.aexp.nodes.graphql.GraphQLRequestEntity;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import io.aexp.nodes.graphql.GraphQLTemplate;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class OrderService {
	@Autowired
	private orderRepository orderRepository;
	
	@GraphQLQuery(name = "getAllOrders")
	public List<Order> getAllOrders(){
		return orderRepository.findAll();
	}
	
	@GraphQLQuery(name = "getOrderById")
	public Order getOrderById(@GraphQLArgument(name="id") String id) throws IllegalStateException, MalformedURLException{
		Order order = orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order with id:"+id+" not found!"));
		Arguments args = new Arguments("getCustomerById", new Argument<String>("id", order.getCustomerId()));
		order.setCustomer((Customer)mapObjectData("http://localhost:8081/graphql", args, Customer.class));
		List<Product> products = new ArrayList<Product>();
		if(order.getProduct() != null) {
			order.getProduct().forEach(product -> {
				Arguments arg = new Arguments("getProductById", new Argument<String>("id", product.getId()));
				products.add((Product) mapObjectData("http://localhost:8082/graphql", arg, Product.class));
			});
			order.setProduct(products);
		}		
		return order;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object mapObjectData(String uri, Arguments args, Class myClass) {
		GraphQLTemplate gqt = new GraphQLTemplate();
		GraphQLRequestEntity requestEntiry = null;
		try {
			requestEntiry = GraphQLRequestEntity.Builder()
					.url(uri)
					.arguments(args)
					.request(myClass)
					.build();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Request Entity: "+requestEntiry.getRequest());
		GraphQLResponseEntity responseEntity = gqt.query(requestEntiry, myClass);
		System.out.println("Response Entity:"+ responseEntity.getResponse());
		return responseEntity.getResponse();
	}
	
	@GraphQLMutation(name = "saveOrder")
	public Order saveOrder(@GraphQLArgument(name="customerId") String customerId, 
			@GraphQLArgument(name="products") List<Product> products, 
			@GraphQLArgument(name="orderAmount")Float orderAmount, 
			@GraphQLArgument(name="orderDate")String orderDate) {
		return orderRepository.save(new Order(customerId, products, orderAmount, orderDate));
	}
	
	@GraphQLMutation(name = "updateOrder")
	public Order updateOrder(@GraphQLArgument(name="id") String id, 
			@GraphQLArgument(name="customerId") String customerId) {
		Order order = null;
		try {
			order = getOrderById(id);
		} catch (IllegalStateException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setCustomerId(customerId);
		return orderRepository.save(order);
	}
	
	@GraphQLMutation(name = "deleteOrder")
	public String deleteOrder(@GraphQLArgument(name="id") String id) {
		Order Order = null;
		try {
			Order = getOrderById(id);
		} catch (IllegalStateException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderRepository.delete(Order);
		return "Deleted Successfully";
	}

}
