package com.transactionauthorizer.service;

import com.transactionauthorizer.model.TransactionWrapper;

public interface TransactionService {
	TransactionWrapper createTransaction(String input);
	
}
