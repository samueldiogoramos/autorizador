package com.transactionauthorizer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public abstract class OperationAbstract {
	@JsonIgnore
	private OperationType operationType;
}
