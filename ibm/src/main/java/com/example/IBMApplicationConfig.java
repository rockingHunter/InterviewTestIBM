package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.service.BalanceService;
import com.example.service.BalanceServiceImpl;

@Configuration
public class IBMApplicationConfig {

	@Bean
	public BalanceService balanceService() {
		return new BalanceServiceImpl();
	}
}
