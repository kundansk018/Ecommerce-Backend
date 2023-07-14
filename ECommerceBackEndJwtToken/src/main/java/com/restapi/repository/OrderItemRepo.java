package com.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.entity.OrderItem;


public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
