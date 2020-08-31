package com.nisum.webflux;

import com.nisum.webflux.model.Product;

public class BuilderConcept {

	public static void main(String[] args) {
		Product product =new Product.ProductBuilder("1").name("Mobile").price("30000").build();
		Product product1 =new Product.ProductBuilder("1").name("Mobile").price("30000").build();
		System.out.println(product);
		System.out.println(product1);
	}

}
