package com.nisum.webflux.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nisum.webflux.model.Fruit;
import com.nisum.webflux.service.IFruitService;
import com.nisum.webflux.service.impl.OperationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
public class FruitControllerTest {
	@Autowired
	WebTestClient webTestClient;
	
	@Mock
	IFruitService fruitService;
	@Mock
	OperationService operationService;
	
	@Mock
	KafkaSender<String,String> employeeSender;
	
	@Mock
	KafkaReceiver<String, String> receiver;
	
	@InjectMocks
	FruitController fruitController;
	
	@Test
	public void saveTest() {
		Fruit fruit=new Fruit("1","apple","300");
		when(operationService.operate(fruit)).thenReturn(Mono.just(fruit));
		when(operationService.doNothing(fruit)).thenReturn(fruit);
		when(fruitService.save(fruit)).thenReturn(Mono.just(fruit));
		
		@SuppressWarnings("unchecked")
		SenderResult<Object> result = mock(SenderResult.class);
		when(employeeSender.send(Mockito.any())).thenReturn(Flux.just(result));
		when(operationService.revert(fruit)).thenReturn(Mono.just(fruit));
		when(operationService.getErrorResponse(Mockito.mock(Throwable.class))).thenReturn(Mono.just(fruit));
		
//		Mono<Fruit> fruitMono=fruitController.save(fruit);
//		Fruit fruit2=fruitMono.block();
//		assertEquals(fruit2,fruit);
		StepVerifier.create(fruitController.save(fruit).log()).expectSubscription().expectNext(fruit).verifyComplete();
	}
	

}
