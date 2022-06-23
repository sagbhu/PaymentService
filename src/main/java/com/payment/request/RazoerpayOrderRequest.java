package com.payment.request;

public class RazoerpayOrderRequest {
	private String amount;
	private String currency;
	private String receipt;
	private boolean partial_payment;
	private String first_payment_min_amount;

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

	public boolean isPartial_payment() {
		return partial_payment;
	}

	public void setPartial_payment(boolean partial_payment) {
		this.partial_payment = partial_payment;
	}

	public String getFirst_payment_min_amount() {
		return first_payment_min_amount;
	}

	public void setFirst_payment_min_amount(String first_payment_min_amount) {
		this.first_payment_min_amount = first_payment_min_amount;
	}
}
