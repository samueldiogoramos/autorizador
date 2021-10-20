package com.transactionauthorizer.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.model.ViolationType;
import com.transactionauthorizer.service.AccountService;

@Component
public class AccountNotInitializedValidation {
	
	@Autowired
	private AccountService accountService;
	
	public AccountWrapper validate(AccountWrapper account, final List<String> violations) {
		if (accountService.accountIsNull(account) || !account.getAccount().isActiveCard()) {
			account = new AccountWrapper();
			violations.add(ViolationType.ACCOUNT_NOT_INITIALIZED.getDesc());
		}
		
		return account;
	}

}
