package com.tricon.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.leangen.graphql.annotations.GraphQLQuery;

@Document
public class Product {
	@Id
	@GraphQLQuery(name = "id")
	private String id;
	@GraphQLQuery(name = "productName")
	private String productName;
	@GraphQLQuery(name = "description")
	private String description;
	@GraphQLQuery(name = "pricePerUnit")
	private Float pricePerUnit;
	@GraphQLQuery(name = "availableUnits")
	private int availableUnits;
	@GraphQLQuery(name = "quantity")
	private int quantity;

	public Product(String productName, String description, Float pricePerUnit, int availableUnits) {
		super();
		this.productName = productName;
		this.description = description;
		this.pricePerUnit = pricePerUnit;
		this.availableUnits = availableUnits;
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

	public int getAvailableUnits() {
		return availableUnits;
	}

	public void setAvailableUnits(int availableUnits) {
		this.availableUnits = availableUnits;
	}	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
