package com.transactionauthorizer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class TransactionWrapper extends OperationAbstract{
	@JsonProperty("transaction")
	private Transaction transaction;
}
