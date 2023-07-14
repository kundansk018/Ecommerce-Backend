package com.restapi.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.restapi.dto.CartResponse;
import com.restapi.dto.OrderOperation;
import com.restapi.entity.Order;
import com.restapi.entity.User;

import jakarta.transaction.Transactional;

public interface OrderRepo extends JpaRepository<Order, Long> {
	
	
	List<Order> findByUserId(Long userId);
	
	@Transactional
	@Modifying
	@Query("update Order o set o.status=:status where o.id=:id")
	int changeStatus(Long id , String status);
	
	List<Order> findByStatus(String status);
	
	@Query("select o from Order o where o.dateCreated BETWEEN :from AND :to")
	List<Order> sortByDate(Date from , Date to);
	

	

}