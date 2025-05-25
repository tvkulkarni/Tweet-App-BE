//package com.app.kafka;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ConsumerService {
//	
//	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
//	
//	@KafkaListener(topics = "tweets", groupId = "group_id")
//	public void consume(String msg)
//	{
//		logger.info("Inside Kafka Consumer Service");
//		logger.info("Consumed Message:"+msg);
//		logger.info(String.format("Consumed Message: %s", msg));
//	}
//
//}
