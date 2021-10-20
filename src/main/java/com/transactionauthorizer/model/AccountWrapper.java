package com.transactionauthorizer.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class AccountWrapper extends OperationAbstract implements Serializable{
	private static final long serialVersionUID = -3362015297565403027L;
	
	private Account account;
	private List<String> violations = Lists.newArrayList();
	
	public AccountWrapper clone() throws CloneNotSupportedException {
		return (AccountWrapper) SerializationUtils.clone(this);
	}
}
