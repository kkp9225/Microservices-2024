package com.kkp.order_service.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kkp.order_service.model.Orders;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
