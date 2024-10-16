package com.kkp.inventory_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkp.inventory_service.dto.InventoryResponse;
import com.kkp.inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventoryRepository;
	
	@Transactional(readOnly = true)
	public List<InventoryResponse> inStock(List<String> cuCode) {
		return inventoryRepository.findStockByCuCode(cuCode).stream()
				.map(inventory -> 
					InventoryResponse.builder()
					.cuCode(inventory.getCuCode())
					.isInStock(inventory.getQuantity()>0)
					.build()
				).collect(Collectors.toList());
	}
}
