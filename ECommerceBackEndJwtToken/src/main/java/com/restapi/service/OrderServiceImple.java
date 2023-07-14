package com.restapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.restapi.dto.OrderOperation;
import com.restapi.entity.EmailDetails;
import com.restapi.entity.Order;
import com.restapi.entity.OrderItem;
import com.restapi.entity.User;
import com.restapi.repository.CartRepository;
import com.restapi.repository.OrderRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImple implements OrderService {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Override
	public Order save(Order order) {

		Order savedData = orderRepo.save(order);

		Set<OrderItem> orderItems = order.getOrderItems();
		List<Order> orderList = new ArrayList<>();

		orderList.add(savedData);

		EmailDetails email = new EmailDetails(order.getCustomerEmail(), "", "Order Confirmed", orderItems, orderList);
		emailService.sendSimpleMail(email);

		int count = cartRepo.deleteByUserId(1);
		System.out.println("sava method in order service imple" + count);
		return savedData;

	}

	@Override
	public List<Order> orderList() throws Exception {

		return orderRepo.findAll();
	}

	
	public int changeOrderStatus(Long id, int no) {

		if (no == 1) {
			orderRepo.changeStatus(id, "Pending");
			return 1;

		} else if (no == 2) {
			orderRepo.changeStatus(id, "Processing");
			return 2;
		} else if (no == 3) {
			orderRepo.changeStatus(id, "Delivered");	
			return 3;
		}
		else if (no == 4) {
			orderRepo.changeStatus(id, "Cancelled");
			return 4;
		}
		else {
			return 0;
		}

	}

	@Override
	public List<Order> getOrdersByUserId() throws Exception {
		
		Authentication authentication = authenticationFacade.getAuthentication();
		User dbUser = (User) authentication.getPrincipal();
		System.out.println("current user id is " + dbUser.getId()); 

		List<Order> orders = orderRepo.findByUserId(dbUser.getId());
		if (orders != null) {

			return orders;
		} else {
			throw new Exception("Data Not found..");
		}

	}

	@Override
	public List<Order> filterByOrderStatus(String status) {
		
		List<Order> orderByStatus =  orderRepo.findByStatus(status);
		
		return orderByStatus;
	}

	@Override
	public List<Order> filterByDate(Date from, Date to) {
		
		List<Order> orderByDate = orderRepo.sortByDate(from, to);
		
		return orderByDate;
	}



}
