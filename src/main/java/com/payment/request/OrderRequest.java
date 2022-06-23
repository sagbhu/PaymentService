package com.payment.request;

import java.util.List;
import java.util.Map;

public class OrderRequest {

	private String userId;
	private List<String> productIds;
	private String quantity;
	private String amount;
	private String currency;
	private String receipt;
	private String partialPayment;
	private String firstPaymentMinAmount;
	private String id;
	Map<String, String> paramMap;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getPartialPayment() {
		return partialPayment;
	}

	public void setPartialPayment(String partialPayment) {
		this.partialPayment = partialPayment;
	}

	public String getFirstPaymentMinAmount() {
		return firstPaymentMinAmount;
	}

	public void setFirstPaymentMinAmount(String firstPaymentMinAmount) {
		this.firstPaymentMinAmount = firstPaymentMinAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
