package com.nisum.webflux.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.webflux.model.Fruit;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OperationService {
	private static final Logger log = LoggerFactory.getLogger(OperationService.class);
	@Autowired
	NameService nameService;

	public Mono<Fruit> operate(Fruit fruit) {
		log.info("Into operate method of OperationService" + fruit);
		return Mono.just(fruit).flatMap(nameService::name).onErrorResume(e -> {
			log.error("Error occurred during changing name");
			return Mono.just(new Fruit("0", "", ""));
		}).map(e -> {
			e.setPrice(Integer.parseInt(e.getPrice()) + 1000 + "");
			return e;
		});
	}

	public Mono<Fruit> revert(Fruit fruit) {
		log.info("into revertMethod of OperationService" + fruit);
		return Mono.just(fruit).flatMap(nameService::revertName).onErrorResume(e -> {
			log.error("Error occurred during changing name");
			return Mono.just(new Fruit("0", "", ""));
		}).map(e -> {
			e.setPrice(Integer.parseInt(e.getPrice()) - 1000 + "");
			return e;
		});
	}

	public Mono<Fruit> getErrorResponse(Throwable th) {
		log.error("SomeThing Error has happened ::" + th);
		return Mono.just(new Fruit("1", "ErrorFruit", "3"));
	}

	public Fruit doNothing(Fruit fruit) {
		log.info("into doNothing method of OperationService " + fruit);
		return fruit;
	}

}
