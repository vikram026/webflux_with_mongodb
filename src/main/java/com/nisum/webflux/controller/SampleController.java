package com.nisum.webflux.controller;
import com.nisum.webflux.config.KafkaServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	@Autowired
	KafkaServer kafkaServer;
	
	@GetMapping("/send")
	public String producer(@RequestParam String msg) {
		System.out.println("into Controller");
		return kafkaServer.send(msg);		
	}
	
	

}
