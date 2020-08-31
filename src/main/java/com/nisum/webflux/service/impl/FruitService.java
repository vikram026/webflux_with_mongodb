package com.nisum.webflux.service.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.nisum.webflux.model.Fruit;
import com.nisum.webflux.repository.FruitRepository;
import com.nisum.webflux.service.IFruitService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FruitService implements IFruitService{
	
	private static final Logger log = LoggerFactory.getLogger(FruitService.class);

	
	private final WebClient webClient;
	
	@Autowired
	public FruitService(   	@Qualifier("webclient") final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

	@Autowired
	FruitRepository fruitRepository;

	@Override
	public Mono<Fruit> save(Fruit fruit) {
		
		return fruitRepository.save(fruit);
	}
	public Mono<Fruit> get(Fruit fruit) {
		
//		Comparator<Fruit> compareFruit=Comparator.comparing(e->Integer.parseInt(e.getPrice()));
//		return fruitRepository.findAll().collect(Collectors.maxBy(compareFruit)).map(e->e.get());
		
		return fruitRepository.findAll().collect(Collectors.maxBy(Comparator.comparing(e->Integer.parseInt(e.getPrice())))).map(Optional::get);
	}
	
	@Override
	public Flux<Fruit> getFruitsByWebClient() {
		
		return this.webClient.get().uri("http://localhost:8080/getAll").retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse-> clientResponse.bodyToMono(Map.class).flatMap(e->{
					log.error(" 4XX error is occurred ");
					return Mono.error(new Exception());
				}))
				.onStatus(HttpStatus::is5xxServerError, serverResponse->serverResponse.bodyToMono(Map.class).flatMap(e->{
					log.error("there is 5XX error ");
					return Mono.error(new Exception());
				}))
				.bodyToFlux(Fruit.class);
	}
	
	@Override
	public Mono<Fruit> saveFruitsByWebClient() {
		Fruit fruit=new Fruit();
		fruit.setId("45");
		fruit.setName("PineApple");
		fruit.setPrice("70");
		
		return this.webClient.post().uri("http://localhost:8080/save")
				.body(BodyInserters.fromObject(fruit))
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse-> clientResponse.bodyToMono(Map.class).flatMap(e->{
					log.error(" 4XX error is occurred ");
					return Mono.error(new Exception());
				}))
				.onStatus(HttpStatus::is5xxServerError, serverResponse->serverResponse.bodyToMono(Map.class).flatMap(e->{
					log.error("there is 5XX error ");
					return Mono.error(new Exception());
				}))
				.bodyToMono(Fruit.class);
	}
	@Override
	public Flux<Fruit> getAll() {
		// TODO Auto-generated method stub
		return fruitRepository.findAll();
	}
	@Override
	public Mono<Fruit> getFruitById(String id) {
		return Optional.ofNullable(id).map(fruitRepository::findById).get();//.or(null);		
	}
	@Override
	public Mono<Fruit> getFruitByPriceAndName(String price, String name) {
		return Optional.ofNullable(name).map(e->fruitRepository.findByNameAndPrice(e,price)).get();
	}
}
