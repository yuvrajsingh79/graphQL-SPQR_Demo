package com.tricon.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;
import io.leangen.graphql.annotations.GraphQLQuery;

@Document(collection = "customer")
@GraphQLProperty(name="customer", arguments = {
		@GraphQLArgument(name="id", type="String")
})
public class Customer {
	@Id
	@GraphQLQuery(name = "id")
	private String id;	

	private String name;
	private String emailId;
	private String password;
	private String address;
	
	public Customer(String id, String name, String emailId, String password, String address) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.address = address;
	}
	
	public Customer(String name, String emailId, String password, String address) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.address = address;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
	
}