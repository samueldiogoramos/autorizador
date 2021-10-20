package com.transactionauthorizer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.transactionauthorizer.model.AccountWrapper;

public interface AccountService {
	AccountWrapper createAccount(String input) throws JsonProcessingException;
	
	boolean accountIsNull(AccountWrapper account);
}
