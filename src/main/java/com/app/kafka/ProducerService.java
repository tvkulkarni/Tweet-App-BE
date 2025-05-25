//package com.app.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProducerService {
//	
//	public static final String TOPIC = "tweets";
//	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
//	
//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//	
//	public void sendMessage(String msg)
//	{
//		logger.info("Inside Kafka Producer Service");
//		logger.info("Publishing message to topic:"+TOPIC);
//		this.kafkaTemplate.send(TOPIC, msg);
//	}
//
//}
