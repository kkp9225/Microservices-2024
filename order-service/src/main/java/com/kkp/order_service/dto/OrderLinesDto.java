package com.kkp.order_service.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLinesDto {

	private Long id;
	private String cuCode;
	private BigDecimal price;
	private Integer quantity;
}
