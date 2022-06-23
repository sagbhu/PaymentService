package com.payment.request;

import java.util.ArrayList;

public class OrderPullRequest {

	private String orderNo;
	private ArrayList<Object> statuses;
	private String fromDate;
	private String toDate;
	private int pageNumber;
	private String order_Location;
	private String isReplacementOrder;
	private String orderSource;
	private ArrayList<Object> paymentType;
	private int filterBy;
	private String fulfillmentLocation;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public ArrayList<Object> getStatuses() {
		return statuses;
	}

	public void setStatuses(ArrayList<Object> statuses) {
		this.statuses = statuses;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getOrder_Location() {
		return order_Location;
	}

	public void setOrder_Location(String order_Location) {
		this.order_Location = order_Location;
	}

	public String getIsReplacementOrder() {
		return isReplacementOrder;
	}

	public void setIsReplacementOrder(String isReplacementOrder) {
		this.isReplacementOrder = isReplacementOrder;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public ArrayList<Object> getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(ArrayList<Object> paymentType) {
		this.paymentType = paymentType;
	}

	public int getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(int filterBy) {
		this.filterBy = filterBy;
	}

	public String getFulfillmentLocation() {
		return fulfillmentLocation;
	}

	public void setFulfillmentLocation(String fulfillmentLocation) {
		this.fulfillmentLocation = fulfillmentLocation;
	}

}
