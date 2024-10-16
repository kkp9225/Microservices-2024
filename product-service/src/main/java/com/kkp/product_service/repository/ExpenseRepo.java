package com.kkp.product_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kkp.product_service.model.Expense;

public interface ExpenseRepo extends MongoRepository<Expense, String> {
	
	

}
