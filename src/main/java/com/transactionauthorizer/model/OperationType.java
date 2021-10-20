package com.transactionauthorizer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperationType {
	ACCOUNT("account"), 
	TRANSACTION("transaction");
	
	protected String desc;
	
	public static OperationType getOperationTypeByDesc(final String desc) {
		OperationType operationType = null;
		
		for (OperationType type : OperationType.values()) {
			if (type.getDesc().equals(desc)) {
				operationType = type;
				break;
			}
		}
		
		return operationType;
	}
}
