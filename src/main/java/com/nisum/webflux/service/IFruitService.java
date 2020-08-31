package com.nisum.webflux.service;

import com.nisum.webflux.model.Fruit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFruitService {

	Mono<Fruit> save(Fruit fruit);
	Flux<Fruit> getAll();
	Flux<Fruit> getFruitsByWebClient();
	Mono<Fruit> saveFruitsByWebClient();
	Mono<Fruit> getFruitById(String id);
	Mono<Fruit> getFruitByPriceAndName(String price, String name);
	

}
