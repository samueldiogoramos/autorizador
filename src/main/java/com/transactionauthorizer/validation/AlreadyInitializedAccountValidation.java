package com.transactionauthorizer.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.model.OperationAbstract;
import com.transactionauthorizer.model.ViolationType;
import com.transactionauthorizer.service.AccountService;

@Component
public class AlreadyInitializedAccountValidation {
	
	@Autowired
	private AccountService accountService;
	
	public AccountWrapper validade(AccountWrapper account, final OperationAbstract operation, final List<String> violations) {
		if (accountService.accountIsNull(account)) {
			account = (AccountWrapper) operation;
		} else {
			violations.add(ViolationType.ACCOUNT_ALREADY_INITIALIZED.getDesc());
		}

		return account;
	}

}
