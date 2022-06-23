package com.payment.service;

import java.util.List;

import com.payment.entity.Order;
import com.payment.entity.PaymentDetails;
import com.payment.request.OrderRequest;
import com.payment.request.OrderSearchRequest;

public interface OrderService {

	Order placeOrder(OrderRequest orderRequest);
	
	boolean verifySignature(PaymentDetails paymentDetails);

	List<Order> getOrders(OrderSearchRequest orderSearchRequest);

	Order updateOrder(OrderRequest orderRequest);

}
