package com.transactionauthorizer.validation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.transactionauthorizer.model.ViolationType;

@Component
public class HighFrequencySmallIntervalValidation {

	private static final Integer TWO_MINUTOS = 2;
	private static final Integer LIMIT_COUNT_SUCCESS = 3;
	
	public void validate(final LocalDateTime firstOperationTime, final LocalDateTime timeOperation, final int countSuccessIn2min, final List<String> violations) {
		if(firstOperationTime != null && 
				(timeOperation.isAfter(firstOperationTime) && timeOperation.isBefore(firstOperationTime.plusMinutes(TWO_MINUTOS))) && 
				countSuccessIn2min == LIMIT_COUNT_SUCCESS) {
			violations.add(ViolationType.HIGH_FREQUENCY_SMALL_INTERVAL.getDesc());
		}
	}

}
