package com.nisum.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nisum.webflux.model.Fruit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FruitRepository extends ReactiveMongoRepository<Fruit, String> {

	Mono<Fruit> findByNameAndPrice(String e, String price);
	

}
