package com.nisum.webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServer {
	
	@Autowired
	KafkaTemplate<String, String> template;
	
	@Value("${com.kafka.topic}")
	private String topic;
	
	
	public String send(String message) {
		template.send(topic,message);
		return message;
	}

}
