package com.transactionauthorizer.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.model.OperationAbstract;
import com.transactionauthorizer.model.OperationType;
import com.transactionauthorizer.model.Transaction;
import com.transactionauthorizer.model.TransactionWrapper;
import com.transactionauthorizer.service.AccountService;
import com.transactionauthorizer.service.AuthorizationService;
import com.transactionauthorizer.service.TransactionService;
import com.transactionauthorizer.validation.AccountNotInitializedValidation;
import com.transactionauthorizer.validation.AlreadyInitializedAccountValidation;
import com.transactionauthorizer.validation.CardNotActiveValidation;
import com.transactionauthorizer.validation.DoubledTransactionValidation;
import com.transactionauthorizer.validation.HighFrequencySmallIntervalValidation;
import com.transactionauthorizer.validation.InsufficientLimitValidation;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AlreadyInitializedAccountValidation alreadyInitializedAccountValidation;

	@Autowired
	private AccountNotInitializedValidation accountNotInitializedValidation;

	@Autowired
	private CardNotActiveValidation cardNotActiveValidation;

	@Autowired
	private InsufficientLimitValidation insufficientLimitValidation;

	@Autowired
	private HighFrequencySmallIntervalValidation highFrequencySmallIntervalValidation;

	@Autowired
	private DoubledTransactionValidation doubledTransactionValidation;

	private AccountWrapper accountStatus;
	
	@Override
	public List<AccountWrapper> transaction(final List<String> commands) throws CloneNotSupportedException {

		final List<OperationAbstract> operations = convertCommandsToOperation(commands);

		return applyRulesTransaction(operations);
	}

	private List<AccountWrapper> applyRulesTransaction(final List<OperationAbstract> operations) throws CloneNotSupportedException {
		final List<AccountWrapper> accountsSuccess = Lists.newArrayList();
		final List<Transaction> transactionsSuccess = Lists.newArrayList();
		final List<AccountWrapper> results = Lists.newArrayList();

		AccountWrapper account = null;
		int countSuccessIn2min = 0;
		LocalDateTime firstOperationSuccessTime = null;

		for (final OperationAbstract operation : operations) {
			final List<String> violations = Lists.newArrayList();

			if (OperationType.ACCOUNT.equals(operation.getOperationType())) {
				account = alreadyInitializedAccountValidation.validade(account, operation, violations);
				saveStatusAccount(account);
			} else {
				final Transaction transaction = ((TransactionWrapper) operation).getTransaction();
				final BigDecimal amount = transaction.getAmount();
				final LocalDateTime time = transaction.getTime();

				account = accountNotInitializedValidation.validate(account, violations);
				saveStatusAccount(account);

				account = cardNotActiveValidation.validate(account, violations);
				saveStatusAccount(account);
				
				insufficientLimitValidation.validate(account, amount, violations);
				highFrequencySmallIntervalValidation.validate(firstOperationSuccessTime, time, countSuccessIn2min, violations);
				doubledTransactionValidation.validate(transactionsSuccess, transaction, violations);

				if (transactionSuccess(violations)) {
					calculateTransaction(account, amount);
					
					saveStatusAccount(account);

					countSuccessIn2min++;

					firstOperationSuccessTime = setFirstOperationSuccessTime(firstOperationSuccessTime, time);

					accountsSuccess.add(accountStatus);
					
					transactionsSuccess.add(transaction);
				}
			}

			accountStatus.setViolations(violations);

			results.add(accountStatus);
		}

		return results;
	}

	private LocalDateTime setFirstOperationSuccessTime(LocalDateTime firstOperationSuccessTime, final LocalDateTime time) {
		if (firstOperationSuccessTime == null) {
			firstOperationSuccessTime = time;
		}
		
		return firstOperationSuccessTime;
	}

	private void calculateTransaction(final AccountWrapper account, final BigDecimal amount) {
		BigDecimal newLimit = account.getAccount().getAvailableLimit().subtract(amount);
		account.getAccount().setAvailableLimit(newLimit);
	}

	private boolean transactionSuccess(final List<String> violations) {
		return violations.isEmpty();
	}
	
	private void saveStatusAccount(final AccountWrapper account) throws CloneNotSupportedException {
		this.accountStatus = account.clone();
	}

	private List<OperationAbstract> convertCommandsToOperation(final List<String> commands) {
		final List<OperationAbstract> operations = Lists.newArrayList();

		try {
			commands.forEach(command -> {
				String operationName = getOperationFromCommand(command);
				OperationType operationType = OperationType.getOperationTypeByDesc(operationName);
				OperationAbstract operation = getOperation(operationType, command);

				operations.add(operation);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return operations;
	}

	private String getOperationFromCommand(final String command) {
		int start = command.indexOf('{');
		int end = command.indexOf(':');

		return command.substring(start, end)
				.replace('{', ' ')
				.replace('\"', ' ')
				.trim();
	}

	private OperationAbstract getOperation(final OperationType operationType, final String commands) {
		OperationAbstract wrapperAbstract = null;

		try {
			if (OperationType.ACCOUNT.equals(operationType)) {
				wrapperAbstract = accountService.createAccount(commands);
			} else if (OperationType.TRANSACTION.equals(operationType)) {
				wrapperAbstract = transactionService.createTransaction(commands);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wrapperAbstract;
	}

}
