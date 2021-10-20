package com.transactionauthorizer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Account implements Serializable{
	private static final long serialVersionUID = 6159088582388005491L;

	@JsonProperty("active-card")
	private boolean activeCard;

	@JsonProperty("available-limit")
	private BigDecimal availableLimit;
	
}
