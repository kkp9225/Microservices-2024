package com.kkp.product_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kkp.product_service.dto.ProductRequest;
import com.kkp.product_service.dto.ProductResponse;
import com.kkp.product_service.model.Product;
import com.kkp.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
	
		productRepository.save(product);
		log.info("Product {} saved successfully", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> mapAllProducts(product)).collect(Collectors.toList());
	}

	private ProductResponse mapAllProducts(Product product) {
		ProductResponse productResponse = ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
		return productResponse;
	}
}
