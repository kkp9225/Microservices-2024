package com.kkp.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.kkp.notification_service.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "notificationTopic")
	public void sendNotification(OrderPlacedEvent orderPlacedEvent) {
		//Implement Send email logic here
		log.info("Notification recieved for order number - {} ", orderPlacedEvent.getOrderNumber());
	}
}