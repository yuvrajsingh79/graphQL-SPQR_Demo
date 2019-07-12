package com.tricon.order.model;


import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "getProductById", arguments = {
		@GraphQLArgument(name = "id", type = "String")
})
public class Product {
	private String id;
	private String productName;
	private String description;
	private Float pricePerUnit;
	private int quantity;
	
	public Product(String productName, String description, Float pricePerUnit, int quantity) {
		super();
		this.productName = productName;
		this.description = description;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
	}
	
	public Product() {
		//empty cstr---todo
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(Float pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}

