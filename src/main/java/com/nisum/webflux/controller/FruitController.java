package com.nisum.webflux.controller;

import java.util.List;
import java.util.function.Function;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.webflux.model.Fruit;
import com.nisum.webflux.service.IFruitService;
import com.nisum.webflux.service.impl.OperationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

@RestController
public class FruitController {
	
	@Value("${com.kafka.topic}")
	private String topic;
	
	@Autowired
	IFruitService fruitService;
	
	@Autowired
	OperationService operationService;
	
	
	@Qualifier("simpleProducer")
	@Autowired
	KafkaSender<String,String> employeeSender;
	
	
	@Qualifier("simpleConsumer")
	@Autowired
	KafkaReceiver<String, String> receiver;
	
	
	
	
	@PostMapping("/fruit")
	public Mono<Fruit> save(@RequestBody Fruit fruit){

		return Mono.just(fruit)
				.flatMap(operationService::operate)
				.map(operationService::doNothing)
				.flatMap(fruitService::save)
				.flatMapMany(saveToKafka()).collectList().flatMap(getMono(fruit))
			    .flatMap(operationService::revert)
				.onErrorResume(operationService::getErrorResponse);
	}
	


	@PostMapping("/saveTokafka")  
	public Mono<Fruit> normalSave(@RequestBody Fruit fruit){
		     
		
		return fruitService.save(fruit).flatMapMany(h -> {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, null, h.getId(),
					h.toString());
			Mono<SenderRecord<String, String, String>> mono = Mono.just(SenderRecord.create(record, null));
			return employeeSender.send(mono);
		}).collectList().flatMap(m -> Mono.just(fruit));	
	}
	private Function<Fruit,Flux<SenderResult<String>> > saveToKafka() {
		return h -> {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, null, ((Fruit) h).getId(),
					h.toString());
			Mono<SenderRecord<String, String, String>> mono = Mono.just(SenderRecord.create(record, null));
			return employeeSender.send(mono);
		};
	}

	private Function< List<SenderResult<String>>,  Mono<Fruit>> getMono(Fruit fruit) {
		return m -> Mono.just(fruit);
	}
	

	@GetMapping("/getAll")
	public Flux<Fruit> getAll(){
		return fruitService.getAll();
	}
	@GetMapping("/getAllByWebClient")
	public Flux<Fruit> getAllByWebClient(){
		return fruitService.getFruitsByWebClient();
	}
	@GetMapping("/saveFruitByWebClient")
	public Mono<Fruit> saveFruitsByWebClient() {
		return fruitService.saveFruitsByWebClient();
	}
	@PostMapping("/save")
	public Mono<Fruit> savetoMongo(@RequestBody Fruit fruit) {
		return fruitService.save(fruit);
	}
	@GetMapping("/getFruitById/{id}")
	public Mono<Fruit> getFruitById(@PathVariable String id) {
		return fruitService.getFruitById(id);
	}
	@GetMapping("/getFruitByPriceAndName/{price}")
	public Mono<Fruit> getFruitByPriceAndName(@PathVariable String price ,@RequestParam String name) {
		return fruitService.getFruitByPriceAndName(price,name);
	}
}
