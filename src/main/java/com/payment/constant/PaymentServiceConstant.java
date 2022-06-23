package com.payment.constant;

public interface PaymentServiceConstant {

	String PAYMENT_SERVICE_URL = "/paymentService";

	String RAZORPAY_ORDER_URL = "https://api.razorpay.com/v1/orders";
	
	String ORDER_PRODUCTS_URL ="http://localhost:8082/productService/getOrderProductsDetail";
	
	String HEADER = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36";

	String HOST = "https://dhybrid.vineretail.com";
	
	String ORDER_API_OWNER="sa";
	
	String ORDER_API_KEY="be5f07cf82ae4b6d9817708a19b3c7cf0de1065774c54b52aa4dbf9";
	
	String VINERETAIL_ORDER_PULL_ENDPOINT = "/RestWS/api/eretail/v1/order/orderPull";
	
	String VINERETAIL_ORDER_STATUS_ENDPOINT = "/RestWS/api/eretail/v2/order/status";
}
