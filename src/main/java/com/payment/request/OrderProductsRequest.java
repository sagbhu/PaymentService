package com.payment.request;

import java.util.List;

public class OrderProductsRequest {

	private List<String> productIds;

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

}
