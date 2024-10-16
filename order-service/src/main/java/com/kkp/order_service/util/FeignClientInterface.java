package com.kkp.order_service.util;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kkp.order_service.dto.InventoryResponse;

@FeignClient(name = "inventory-service")
public interface FeignClientInterface {
	
	@GetMapping("api/v1/inventory")
	public List<InventoryResponse> inStock(@RequestParam List<String> cuCodes);

}
