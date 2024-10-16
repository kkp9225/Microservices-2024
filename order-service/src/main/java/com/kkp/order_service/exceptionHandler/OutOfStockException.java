package com.kkp.order_service.exceptionHandler;

public class OutOfStockException extends RuntimeException{
	
	public OutOfStockException(String msg) {
		super(msg);
	}

}
