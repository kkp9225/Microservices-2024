package com.kkp.order_service.exceptionHandler;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = OutOfStockException.class)
	public String handleException() {
		return "Out of stock!!!";
	}

}
