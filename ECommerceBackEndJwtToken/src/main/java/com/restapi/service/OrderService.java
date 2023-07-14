package com.restapi.service;

import java.util.Date;
import java.util.List;

import com.restapi.dto.OrderOperation;
import com.restapi.entity.Order;

public interface OrderService {
	
	Order save(Order order);
	List<Order> orderList () throws Exception;
	int changeOrderStatus(Long id , int no);
	
	List<Order> getOrdersByUserId () throws Exception; //get Order By userID..
	
	List<Order> filterByOrderStatus(String status);
	
	List<Order> filterByDate(Date from , Date to);


//	 public List<OrderOperation> filterOrders(String key ,OrderOperation orderOperatin );
}

