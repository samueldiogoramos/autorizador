package com.transactionauthorizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ViolationType {
	ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
	ACCOUNT_NOT_INITIALIZED("account-not-initialized"),
	CARD_NOT_ACTIVE("card-not-active"),
	INSUFFICIENT_LIMIT("insufficient-limit"),
	HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
	DOUBLE_TRANSACTION("double-transaction");
	
	protected String desc;
}
