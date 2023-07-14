package com.restapi.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.RersponseHandler.ResponseHandler;
import com.restapi.dto.OrderOperation;
import com.restapi.entity.Order;
import com.restapi.entity.User;
import com.restapi.service.OrderServiceImple;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(originPatterns = "*")
public class OrderController {

	@Autowired
	private OrderServiceImple orderService;

	@PostMapping("order/placeOrder")
	public ResponseEntity<Object> placeOrder(@RequestBody Order order) {

		Order savedData = orderService.save(order);

		return ResponseHandler.generateResponse("Order placed successfully", HttpStatus.OK, savedData);

	}

	@GetMapping("order/getAllOrder")
	public ResponseEntity<Object> getAllOrder() throws Exception {

		List<Order> orderList = orderService.orderList();

		return ResponseHandler.generateResponse("Order fetched successfully", HttpStatus.OK, orderList);

	}

	@PutMapping("order/changeStatus")
	public ResponseEntity<Object> changeStatus(@RequestParam("id") long id, @RequestParam("no") int no) {

		int status = orderService.changeOrderStatus(id, no);

		return ResponseHandler.generateResponse("Status changed Byy Admin", HttpStatus.OK, status);

	}

	@GetMapping("order/getOrderByUserId")
	public ResponseEntity<Object> getOrderByUserId() throws Exception {

		List<Order> orderList = orderService.getOrdersByUserId();

		return ResponseHandler.generateResponse("Order fetched By User Id...!!", HttpStatus.OK, orderList);

	}

	@GetMapping("order/filterByOrderStatus")
	public ResponseEntity<Object> filterByOrderStatus(@RequestParam("status") String status) {
		List<Order> order = orderService.filterByOrderStatus(status);
		if (order.size() > 0) {

			return ResponseHandler.generateResponse("Order By Status..", HttpStatus.OK, order);
		} else {
			return ResponseHandler.generateResponse("Orders Not Found..", HttpStatus.OK, order);
		}
	}

	@GetMapping("order/filterByDate")
	public ResponseEntity<Object> filterByDate(@RequestParam("from") Date from, @RequestParam("to") Date to) {
		List<Order> order = orderService.filterByDate(from, to);

		return ResponseHandler.generateResponse("Get Order's By Date..", HttpStatus.OK, order);

	}
	

	

}
