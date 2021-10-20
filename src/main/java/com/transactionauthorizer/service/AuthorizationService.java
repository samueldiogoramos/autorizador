package com.transactionauthorizer.service;

import java.util.List;

import com.transactionauthorizer.model.AccountWrapper;

public interface AuthorizationService {
	List<AccountWrapper> transaction(final List<String> operations) throws CloneNotSupportedException;
}
