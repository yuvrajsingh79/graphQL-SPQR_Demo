package com.tricon.order.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document
public class Order {
	@Id
	@GraphQLQuery(name = "id")
	private String id;
	@GraphQLQuery(name = "customerId")
	private String customerId;
	@GraphQLQuery(name = "customer")
	private Customer customer;
	@GraphQLQuery(name = "products")
	private List<Product> products;
	@GraphQLQuery(name = "orderAmount")
	private Float orderAmount;
	@GraphQLQuery(name = "orderDate")
	private String orderDate;

	public Order(String customerId, List<Product> products, Float orderAmount, String orderDate) {
		super();
		this.customerId = customerId;
		this.products = products;
		this.orderAmount = orderAmount;
		this.orderDate = orderDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

//	public String[] getProductId() {
//		return productId;
//	}
//
//	public void setProductId(String[] productId) {
//		this.productId = productId;
//	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Product> getProduct() {
		return products;
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}

	public Float getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Float orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}
