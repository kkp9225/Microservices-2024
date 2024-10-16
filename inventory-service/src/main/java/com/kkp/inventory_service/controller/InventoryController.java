package com.kkp.inventory_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kkp.inventory_service.dto.InventoryResponse;
import com.kkp.inventory_service.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryService inventoryService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<InventoryResponse> inStock(@RequestParam List<String> cuCodes) {
		return inventoryService.inStock(cuCodes);
	}
}
