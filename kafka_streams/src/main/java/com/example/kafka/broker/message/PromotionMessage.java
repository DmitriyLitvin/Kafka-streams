package com.example.kafka.broker.message;

import java.io.Serializable;

public class PromotionMessage implements Serializable {

	private String promotionCode;

	public PromotionMessage() {
	}

	public PromotionMessage(String promotionCode) {
		super();
		this.promotionCode = promotionCode;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	@Override
	public String toString() {
		return "PromotionMessage [promotionCode=" + promotionCode + "]";
	}

}
