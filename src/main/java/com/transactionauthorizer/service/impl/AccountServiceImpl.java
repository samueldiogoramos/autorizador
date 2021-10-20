package com.transactionauthorizer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.model.OperationType;
import com.transactionauthorizer.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public AccountWrapper createAccount(final String input) throws JsonProcessingException {
		final AccountWrapper accountWrapper = mapper.readValue(input, AccountWrapper.class);
		accountWrapper.setOperationType(OperationType.ACCOUNT);
		
		logger.debug(String.format("Created account successfully -> %s", accountWrapper));

		return accountWrapper;
	}

	@Override
	public boolean accountIsNull(AccountWrapper account) {
		return account == null ||
				account.getAccount() == null;
	}
	
}
