package com.tricon.order.model;

import io.aexp.nodes.graphql.annotations.GraphQLArgument;
import io.aexp.nodes.graphql.annotations.GraphQLProperty;

@GraphQLProperty(name = "getCustomerById", arguments = {
		@GraphQLArgument(name = "id", type = "String")
})
public class Customer {
//	private String id;	
	private String name;
	private String emailId;
	private String password;
	private String address;
	
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

//	public String getid() {
//		return id;
//	}
//
//	public void setid(String id) {
//		this.id = id;
//	}

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
