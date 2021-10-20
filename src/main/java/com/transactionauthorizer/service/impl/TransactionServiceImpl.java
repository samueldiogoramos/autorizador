package com.transactionauthorizer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionauthorizer.model.OperationType;
import com.transactionauthorizer.model.TransactionWrapper;
import com.transactionauthorizer.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private ObjectMapper mapper;

	@Override
	public TransactionWrapper createTransaction(String input) {
		TransactionWrapper transactionWrapper = null;
		
		try {
			transactionWrapper = mapper.readValue(input, TransactionWrapper.class);
			transactionWrapper.setOperationType(OperationType.TRANSACTION);
			
			logger.debug(String.format("Created transaction successfully -> %s", transactionWrapper));
		}catch (JsonProcessingException e) {
			logger.error(String.format("Failed to create user. Exception: %s", e.getMessage()));
		}
		
		return transactionWrapper;
	}

}
