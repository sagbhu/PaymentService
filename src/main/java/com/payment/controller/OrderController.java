package com.payment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.constant.PaymentServiceConstant;
import com.payment.entity.Order;
import com.payment.entity.PaymentDetails;
import com.payment.request.OrderRequest;
import com.payment.request.OrderSearchRequest;
import com.payment.service.OrderService;

@RestController
@RequestMapping(PaymentServiceConstant.PAYMENT_SERVICE_URL)
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
		logger.debug("placeOrder method start in OrderController");

		Order order = orderService.placeOrder(orderRequest);

		logger.debug("placeOrder method start in OrderController");
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PostMapping("/verifyPayment")
	public ResponseEntity<Boolean> verifySignature(@RequestBody PaymentDetails paymentDetails) {
		logger.debug("verifySignature method start in OrderController");

		boolean verfied = orderService.verifySignature(paymentDetails);

		logger.debug("verifySignature method start in OrderController");
		return new ResponseEntity<>(verfied, HttpStatus.CREATED);
	}
	
	@PostMapping("/getOrders")
	public ResponseEntity<List<Order>> getOrders(@RequestBody OrderSearchRequest orderSearchRequest) {
		List<Order> orders = orderService.getOrders(orderSearchRequest);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@PutMapping("/updateOrder")
	public ResponseEntity<Order> updateOrder(@RequestBody OrderRequest orderRequest){
		Order order=orderService.updateOrder(orderRequest);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
