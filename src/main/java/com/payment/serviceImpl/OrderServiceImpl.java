package com.payment.serviceImpl;

import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.constant.PaymentServiceConstant;
import com.payment.entity.Order;
import com.payment.entity.PaymentDetails;
import com.payment.repository.OrderRepository;
import com.payment.repository.PaymentDetailsRepository;
import com.payment.request.OrderProductsRequest;
import com.payment.request.OrderPullRequest;
import com.payment.request.OrderRequest;
import com.payment.request.OrderSearchRequest;
import com.payment.request.OrderStatusRequest;
import com.payment.request.RazoerpayOrderRequest;
import com.payment.response.OrderProductResponse;
import com.payment.response.RazorPayOrderResponse;
import com.payment.service.OrderService;
import com.payment.utils.APIUtils;
import com.payment.utils.HttpPostForm;
import com.payment.utils.SignatureVerificationUtil;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	PaymentDetailsRepository paymentDetailsRepository;

	@Override
	public Order placeOrder(OrderRequest orderRequest) {
		logger.debug("Start of placeOrder OrderServiceImpl");
		// save order json in order table before post razorpay order api.
		Order order = new Order();
		order.setUserId(orderRequest.getUserId());

		if (null != orderRequest.getProductIds() && !orderRequest.getProductIds().isEmpty()) {
			StringBuilder productIds = new StringBuilder();
			List<String> prodcutIdList = orderRequest.getProductIds();
			for (int i = 0; i < prodcutIdList.size(); i++) {
				productIds.append(prodcutIdList.get(i) + ",");
			}
			order.setProductId(productIds.toString());
		}

		order.setQuantity(orderRequest.getQuantity());
		order.setAmount(orderRequest.getAmount());
		order.setCurrency(orderRequest.getCurrency());
		order.setReceipt(orderRequest.getReceipt());
		order.setPartialPayment(orderRequest.getPartialPayment());
		order.setFirstPaymentMinAmount(orderRequest.getFirstPaymentMinAmount());
		Order savedOrder = orderRepository.save(order);

		RazoerpayOrderRequest razoerpayOrderRequest = new RazoerpayOrderRequest();
		razoerpayOrderRequest.setAmount(orderRequest.getAmount());
		razoerpayOrderRequest.setCurrency(orderRequest.getCurrency());
		razoerpayOrderRequest.setFirst_payment_min_amount(orderRequest.getFirstPaymentMinAmount());
		razoerpayOrderRequest.setPartial_payment(Boolean.parseBoolean(orderRequest.getPartialPayment()));
		razoerpayOrderRequest.setReceipt(orderRequest.getReceipt());
		String orderResponse = APIUtils.doPost(razoerpayOrderRequest);

		try {
			// update orzder response in order table after getting razorpay order api
			// response .
			RazorPayOrderResponse razorPayOrderResponse = new ObjectMapper().readValue(orderResponse,
					RazorPayOrderResponse.class);
			savedOrder.setOrderId(razorPayOrderResponse.getId());
			savedOrder.setEntity(razorPayOrderResponse.getEntity());
			savedOrder.setAmount(razorPayOrderResponse.getAmount());
			savedOrder.setAmountPaid(razorPayOrderResponse.getAmount_paid());
			savedOrder.setAmountDue(razorPayOrderResponse.getAmount_due());
			savedOrder.setCurrency(razorPayOrderResponse.getCurrency());
			savedOrder.setReceipt(razorPayOrderResponse.getReceipt());
			savedOrder.setOfferId(razorPayOrderResponse.getOffer_id());
			savedOrder.setStatus(razorPayOrderResponse.getStatus());
			savedOrder.setAttempts(razorPayOrderResponse.getAttempts());
			savedOrder.setCreatedAt(razorPayOrderResponse.getCreated_at());
			orderRepository.save(savedOrder);
		} catch (JsonProcessingException e) {
			logger.debug("Error while converting RazorPayOrderResponse :" + e.getMessage());
		}
		logger.debug("End of placeOrder OrderServiceImpl");
		return order;
	}

	@Override
	public boolean verifySignature(PaymentDetails paymentDetails) {
		logger.debug("Start of verifySignature OrderServiceImpl");
		// save payment Details
		paymentDetailsRepository.save(paymentDetails);

		// verify payment
		boolean isVerified = false;
		String data = paymentDetails.getOrderId() + "|" + paymentDetails.getRazorpay_payment_id();
		try {
			isVerified = SignatureVerificationUtil.calculateRFC2104HMAC(data, "LaXCI7lzvenXJA9LpyHKCh36",
					paymentDetails.getRazorpay_signature());
		} catch (SignatureException e) {
			logger.debug("Error while verifing payment signature :" + e.getMessage());
		}
		logger.debug("End of verifySignature OrderServiceImpl");
		return isVerified;
	}

	@Override
	public List<Order> getOrders(OrderSearchRequest orderSearchRequest) {
		List<Order> orders = orderRepository.getOrdersByUserId(orderSearchRequest.getUserId());
		OrderProductsRequest orderProductsRequest = new OrderProductsRequest();
		for (int i = 0; i < orders.size(); i++) {
			Order order = orders.get(i);
			if (null != order.getProductId()) {
				String[] productId = order.getProductId().split(",");
				List<String> list = Arrays.asList(productId);
				orderProductsRequest.setProductIds(list);
				try {
					String productsJson = APIUtils.doPost(orderProductsRequest);

					// fetching products
					OrderProductResponse orderProductResponse = new ObjectMapper().readValue(productsJson,
							OrderProductResponse.class);
					order.setProducts(orderProductResponse.getProducts());

					// fetching order from viniculam

					String orderPullRequest = createOrderPullRequest(order.getId(), orderSearchRequest.getFromDate(),
							orderSearchRequest.getToDate(), orderSearchRequest.getPageNumber());
					String ordersJson = generateRequest(orderPullRequest,
							PaymentServiceConstant.VINERETAIL_ORDER_PULL_ENDPOINT);
					order.setOrderMap(ordersJson);

					// fetching order status from vinculam
					// converting string to array
					String[] id = { order.getId() };
					// converting array to list
					ArrayList<String> ids = new ArrayList<String>(Arrays.asList(id));
					String orderStatusRequest = createOrderStatusRequest(ids, orderSearchRequest.getFromDate(),
							orderSearchRequest.getToDate(), orderSearchRequest.getPageNumber());
					String statusJson = generateRequest(orderStatusRequest,
							PaymentServiceConstant.VINERETAIL_ORDER_STATUS_ENDPOINT);
					order.setOrderStatus(statusJson);
				} catch (Exception e) {
					logger.error("Error while getting user orders in getOrders method");
				}
			}
		}

		return orders;
	}

	private String createOrderPullRequest(String orderId, String fromDate, String toDate, String pageNumber)
			throws Exception {
		OrderPullRequest orderPullRequest = new OrderPullRequest();
		orderPullRequest.setOrderNo(orderId);
		orderPullRequest.setStatuses(new ArrayList());
		orderPullRequest.setFromDate(fromDate);
		orderPullRequest.setToDate(toDate);
		orderPullRequest.setPageNumber(0);
		orderPullRequest.setOrder_Location("W01");
		orderPullRequest.setIsReplacementOrder("");
		orderPullRequest.setOrderSource("");
		orderPullRequest.setPaymentType(new ArrayList());
		orderPullRequest.setFilterBy(Integer.parseInt(pageNumber));
		orderPullRequest.setFulfillmentLocation("H01");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(orderPullRequest);
	}

	private String createOrderStatusRequest(ArrayList<String> orderIds, String fromDate, String toDate,
			String pageNumber) throws JsonProcessingException {
		OrderStatusRequest orderStatusRequest = new OrderStatusRequest();
		orderStatusRequest.setOrder_no(orderIds);
		orderStatusRequest.setDate_from(fromDate);
		orderStatusRequest.setDate_to(toDate);
		orderStatusRequest.setOrder_location("W01");
		orderStatusRequest.setPageNumber(pageNumber);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(orderStatusRequest);
	}

	@Override
	public Order updateOrder(OrderRequest orderRequest) {
		Optional<Order> DbOrder = orderRepository.findById(orderRequest.getId());
		Order order = null;
		if (DbOrder.isPresent()) {
			order = DbOrder.get();
			if (null != orderRequest.getProductIds() && !orderRequest.getProductIds().isEmpty()) {
				StringBuffer productIds = new StringBuffer();
				List<String> productIdList = orderRequest.getProductIds();
				for (int i = 0; i < productIdList.size() - 1; i++) {
					productIds.append(productIdList.get(i));
				}
				order.setProductId(productIds.toString());
			}
			order.setQuantity(orderRequest.getQuantity());
			order.setAmount(orderRequest.getAmount());
			order.setCurrency(orderRequest.getCurrency());
			order.setReceipt(orderRequest.getReceipt());
			order.setPartialPayment(orderRequest.getPartialPayment());
			order.setFirstPaymentMinAmount(orderRequest.getFirstPaymentMinAmount());
			orderRepository.save(order);
		}
		return order;
	}

	private String generateRequest(String json, String endPoint) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("User-Agent", PaymentServiceConstant.HEADER);
			HttpPostForm httpPostForm;
			httpPostForm = new HttpPostForm(PaymentServiceConstant.HOST + endPoint, "utf-8", headers);
			httpPostForm.addFormField("ApiOwner", PaymentServiceConstant.ORDER_API_OWNER);
			httpPostForm.addFormField("ApiKey", PaymentServiceConstant.ORDER_API_KEY);
			httpPostForm.addFormField("RequestBody", json);
			return httpPostForm.finish();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
