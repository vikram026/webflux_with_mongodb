package com.nisum.webflux.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Component
@Configuration
public class KafkaConsumerConfig {
	@Value("${com.kafka.topic}")
	private String topic;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean("simpleConsumer")
    public KafkaReceiver<String,String> simpleConsumer() {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(consumerProps)
                .subscription(Collections.singleton(topic)); //topic name
        KafkaReceiver<String,String> receiver =   KafkaReceiver.create(receiverOptions);
        return receiver;
    }

  
}
