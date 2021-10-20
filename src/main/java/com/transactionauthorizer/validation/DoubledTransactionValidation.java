package com.transactionauthorizer.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.transactionauthorizer.model.Transaction;
import com.transactionauthorizer.model.ViolationType;

@Component
public class DoubledTransactionValidation {

	private static final Integer TWO_MINUTOS = 2;
	
	public void validate(final List<Transaction> transactionsSuccess, final Transaction transaction, final List<String> violations) {
		boolean doubleTransaction = transactionsSuccess.stream().anyMatch(trans -> {
			return trans.getMerchant().equals(transaction.getMerchant())
					&& trans.getAmount().compareTo(transaction.getAmount()) == 0
					&& (transaction.getTime().isAfter(trans.getTime()) && 
							transaction.getTime().isBefore(trans.getTime().plusMinutes(TWO_MINUTOS)) ||
							transaction.getTime().equals(trans.getTime()));
		});
		
		if(doubleTransaction) {
			violations.add(ViolationType.DOUBLE_TRANSACTION.getDesc());
		}
		
	}

}
