package com.payment.request;

import java.util.ArrayList;

public class OrderStatusRequest {

	private ArrayList<String> order_no;
	private String date_from;
	private String date_to;
	private String order_location;
	private String pageNumber;

	public ArrayList<String> getOrder_no() {
		return order_no;
	}

	public void setOrder_no(ArrayList<String> order_no) {
		this.order_no = order_no;
	}

	public String getDate_from() {
		return date_from;
	}

	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}

	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}

	public String getOrder_location() {
		return order_location;
	}

	public void setOrder_location(String order_location) {
		this.order_location = order_location;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

}
