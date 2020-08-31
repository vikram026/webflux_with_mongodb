package com.nisum.webflux.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.nisum.webflux.model.Employee;
import com.nisum.webflux.model.Fruit;
import com.nisum.webflux.service.impl.TestConfig;

import reactor.core.publisher.Mono;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
//@ContextConfiguration(classes = TestConfig.class)
public class FruitControllerComponentTest {
	
	@Autowired
	WebTestClient webTestClient;
	
	
	
	@Test
    public void save_UsingWebTestClient() {
		
		Fruit fruit=new Fruit("1","apple","300");
		webTestClient.post().uri("/fruit").contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(fruit))
    	.exchange()
    	.expectStatus().isOk().expectBody().jsonPath("$.id").isEqualTo("1");
//    	.jsonPath("$.description").isEqualTo("xx").jsonPath("$.price").isEqualTo(34.4);
    	}

}
