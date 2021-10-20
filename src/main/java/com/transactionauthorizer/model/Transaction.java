package com.transactionauthorizer.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transaction {
	private String merchant;
	private BigDecimal amount;
	private LocalDateTime time;
}
