package com.payment.response;

public class ProductLabel {

	private String labelId;
	private String labelName;
	private String labelColor;
	private String labelIcon;
	private String labelState;
	private boolean isDeleted;

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	public String getLabelIcon() {
		return labelIcon;
	}

	public void setLabelIcon(String labelIcon) {
		this.labelIcon = labelIcon;
	}

	public String getLabelState() {
		return labelState;
	}

	public void setLabelState(String labelState) {
		this.labelState = labelState;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
