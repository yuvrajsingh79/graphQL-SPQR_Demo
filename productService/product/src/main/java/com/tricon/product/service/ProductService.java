package com.tricon.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tricon.product.model.Product;
import com.tricon.product.repository.productRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;

@Service
@GraphQLApi
public class ProductService {
	@Autowired
	private productRepository productRepository;
	
	@GraphQLQuery(name = "getAllProducts")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GraphQLQuery(name = "getProductById")
	public Product getProductById(@GraphQLArgument(name="id") String id) {
		return productRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Product id:"+id+" not found!"));
	}
	
	@GraphQLMutation(name = "createProduct")
	public Product createProduct(@GraphQLArgument(name="productName") String productName, 
			@GraphQLArgument(name="descripiton") String descripiton,
			@GraphQLArgument(name="pricePerUnit") Float pricePerUnit, 
			@GraphQLArgument(name="availableUnits")int availableUnits) {
		return productRepository.save(new Product(productName,descripiton, pricePerUnit, availableUnits));
	}
	
	@GraphQLMutation(name = "updateProduct")
	public Product updateProduct(@GraphQLArgument(name="id") String id, 
			@GraphQLArgument(name="productName") String productName,
			@GraphQLArgument(name="descripiton") String descripiton,
			@GraphQLArgument(name="pricePerUnit") Float pricePerUnit, 
			@GraphQLArgument(name="availableUnits")int availableUnits) {
		Product product = getProductById(id);
		product.setProductName(productName);
		product.setDescription(descripiton);
		product.setPricePerUnit(pricePerUnit);
		product.setAvailableUnits(availableUnits);
		return productRepository.save(product);
	}
	
	@GraphQLMutation(name = "deleteProduct")
	public String deleteProduct(@GraphQLArgument(name="id") String id) {
		Product product = getProductById(id);
		productRepository.delete(product);
		return "Deleted Successfully";
	} 

}
