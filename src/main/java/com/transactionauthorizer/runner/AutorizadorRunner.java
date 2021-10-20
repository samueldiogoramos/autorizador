package com.transactionauthorizer.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transactionauthorizer.model.AccountWrapper;
import com.transactionauthorizer.service.AuthorizationService;

@Component
public class AutorizadorRunner implements CommandLineRunner {

	@Autowired
	private AuthorizationService authorizationService;
	
	@Autowired
	private ObjectMapper mapper;
	
	private List<String> commands = new ArrayList<String>();;

	@Override
	public void run(String... args) throws Exception {
		final Scanner scanner = new Scanner(System.in);
		
		readerInput(scanner);
		
		final List<AccountWrapper> results = authorizationService.transaction(commands);
		
		printerResult(results);
	}
	
	private void readerInput(final Scanner scanner) {
		String comando = scanner.nextLine();
		
		if(StringUtils.isBlank(comando)) {
			return;
		}
		
		commands.add(comando);
		
		readerInput(scanner);
	}
	
	private void printerResult(final List<AccountWrapper> results) {
		try {
			for (AccountWrapper result : results) {
				System.out.println(mapper.writeValueAsString(result));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
