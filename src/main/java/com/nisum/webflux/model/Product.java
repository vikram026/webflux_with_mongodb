package com.nisum.webflux.model;

import org.springframework.data.mongodb.core.mapping.Document;

//Builder Design Pattern
@Document
public class Product {
	
	private final String id;
	private final String name;
	private final String price;
	
	private Product(ProductBuilder builder) {
		this.id=builder.id;
		this.name=builder.name;
		this.price=builder.price;
		
	}
	public static class ProductBuilder{
		private final String id;
		private  String name;
		private  String price;
		public ProductBuilder(String id) {
			this.id=id;
		}
		public ProductBuilder name(String name) {
			this.name=name;
			return this;
		}
		public ProductBuilder price(String price) {
			this.price=price;
			return this;
		}
		public Product build() {
			
			Product product=new Product(this);
			validateProduct(product);
			return product;
		}
		private void validateProduct(Product product) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	

}
