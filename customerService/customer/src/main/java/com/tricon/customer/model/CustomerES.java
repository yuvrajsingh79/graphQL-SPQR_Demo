package com.tricon.customer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "tricon", type = "customers")
public class CustomerES {
	
	@Id
	private String id;	

	private String name;
	private String emailId;
	private String password;
	private String address;
	
	public CustomerES() {
		super();
	}
	public CustomerES(String id, String name, String emailId, String password, String address) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.address = address;
	}
	public CustomerES(String name, String emailId, String password, String address) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.password = password;
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
