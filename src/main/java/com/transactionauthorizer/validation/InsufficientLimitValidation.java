package com.transactionauthorizer.validation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.model.ViolationType;
import com.transactionauthorizer.service.AccountService;

@Component
public class InsufficientLimitValidation {
	
	@Autowired
	private AccountService accountService;

	public void validate(final AccountWrapper account, final BigDecimal amount, final List<String> violations) {
		boolean validateLimit = false;
		
		if (!accountService.accountIsNull(account)) {
			BigDecimal result = account.getAccount().getAvailableLimit().subtract(amount);
			
			if(result.compareTo(BigDecimal.ZERO) == -1) {
				validateLimit = true;
			}
		}
		
		if(validateLimit) {
			violations.add(ViolationType.INSUFFICIENT_LIMIT.getDesc());
		}
	}

}
