package com.kkp.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.kkp.order_service.dto.InventoryResponse;
import com.kkp.order_service.dto.OrderLinesDto;
import com.kkp.order_service.dto.OrderRequest;
import com.kkp.order_service.event.OrderPlacedEvent;
import com.kkp.order_service.exceptionHandler.OutOfStockException;
import com.kkp.order_service.model.OrderLineItems;
import com.kkp.order_service.model.Orders;
import com.kkp.order_service.repository.OrderRepository;
import com.kkp.order_service.util.FeignClientInterface;

import lombok.RequiredArgsConstructor;
import zipkin2.internal.Trace;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final WebClient.Builder webClientBuilder;
	
	private final Tracer tracer;
	
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	@Autowired
	private final FeignClientInterface feignClientInterface;
	
	public String placeOrder(OrderRequest orderRequest) {
		Orders orders = new Orders();
		orders.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
				.stream()
				.map(orderLinesDto -> mapOrderLineItems(orderLinesDto))
				.collect(Collectors.toList());
		orders.setOrderLineItems(orderLineItems);
		
		List<String> cuCodes = orderLineItems.stream()
				.map(orderLineItem -> orderLineItem.getCuCode()).collect(Collectors.toList());
		
		Span inventoryLookupSpan = tracer.nextSpan().name("InventoryLookup");
		
		try(Tracer.SpanInScope isInScope = tracer.withSpan(inventoryLookupSpan.start())){
			//Call Inventory service to check stock
			/*
			 * InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
			 * .uri("http://inventory-service/api/v1/inventory", uriBuilder ->
			 * uriBuilder.queryParam("cuCodes", cuCodes).build()) .retrieve()
			 * .bodyToMono(InventoryResponse[].class) .block();
			 */
			List<InventoryResponse> inventoryResponsesArray = feignClientInterface.inStock(cuCodes);
			
			boolean result = inventoryResponsesArray.stream().allMatch(inventory -> inventory.getIsInStock());
			if(result) {
				orderRepository.save(orders);
				kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(orders.getOrderNumber()));
				return "Order placed succefully!";
			} else
				throw new OutOfStockException("Product is out of stock, please try later.");
		} finally {
			inventoryLookupSpan.end();
		}
	}

	private OrderLineItems mapOrderLineItems(OrderLinesDto orderLinesDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLinesDto.getPrice());
		orderLineItems.setCuCode(orderLinesDto.getCuCode());
		orderLineItems.setQuantity(orderLinesDto.getQuantity());
		return orderLineItems;
	}
}
