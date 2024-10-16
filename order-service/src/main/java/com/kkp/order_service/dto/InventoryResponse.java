package com.kkp.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {

	private String cuCode;
	private Boolean isInStock;
	
}
