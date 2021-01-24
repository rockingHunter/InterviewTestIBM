package com.example.service;

import java.util.List;
import com.example.model.TransactionInformation;

public interface BalanceService {

	public Long getBalanceInformation(String acNumber);
	public List<TransactionInformation> getFullDetails(String acNumber, String startTimeRange, String endTimeRange, String type);
	final String WITHDRAW ="WITHDRAW";
	final String DEPOSIT ="DEPOSIT";
}
