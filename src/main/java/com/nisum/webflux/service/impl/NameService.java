package com.nisum.webflux.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nisum.webflux.model.Fruit;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class NameService {
	
	private static final Logger log = LoggerFactory.getLogger(NameService.class);

	private String preName;
	public Mono<Fruit> name(Fruit fruit){
		
		return Mono.just(fruit).map(e->{ 
			this.preName=e.getName(); e.setName(preName+"+pro"); 
			return e;
		}).onErrorResume(e->{
			log.error("Error occurred during changing name"+e);
			return Mono.just(new Fruit("0","",""));
		});
	}
	public Mono<Fruit> revertName(Fruit fruit){
		return Mono.just(fruit).map(e->{ 
			e.setName(preName);
			return e;	
		}).onErrorResume(e->{
			log.error("Error occurred during changing name"+e);
			return Mono.just(new Fruit("0","",""));
		});
	}

}
