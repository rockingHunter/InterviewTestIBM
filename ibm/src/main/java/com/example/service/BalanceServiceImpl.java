package com.example.service;


import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.model.ClientTransactions;
import com.example.model.TransactionInformation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BalanceServiceImpl implements BalanceService{

	@Override
	public Long getBalanceInformation(String acNumber) {
		
		List<TransactionInformation> test = new ArrayList<TransactionInformation>();
		test = getTransactionDetail();
		List<TransactionInformation> sortedList = getSortedDetailsByDateDesc(test);
		long latestBalance = sortedList.stream().filter(s-> acNumber.equals(s.getAccountNumber())).findFirst().get().getAmount();
		System.out.println("Balance is "+latestBalance);
		return latestBalance;
		
		
	}
	
	public List<TransactionInformation> getFullDetails(String acNumber, String startTimeRange, String endTimeRange, String type){
		
		List<TransactionInformation> test = new ArrayList<TransactionInformation>();
		List<TransactionInformation> getListbyType = new ArrayList<TransactionInformation>();
		List<TransactionInformation> resultList = new ArrayList<>();
		
		test = getTransactionDetail();
		List<TransactionInformation> sortedList = getSortedDetailsByDateDesc(test);
		
		List<TransactionInformation> getListbyAcnumber = sortedList.stream()
				.filter(s->acNumber.equals(s.getAccountNumber())).collect(Collectors.toList()); 
		if(!type.isEmpty()) {
			getListbyType = getListbyAcnumber.stream().filter(k->k.getType().equalsIgnoreCase(WITHDRAW)).collect(Collectors.toList());
			getListbyType.stream().forEach(s->{
				if(s.getTransactionTs().substring(0,10).compareTo(endTimeRange)<=0 && 
						(s.getTransactionTs().substring(0,10).compareTo(startTimeRange)>=0) ) {
					resultList.add(s);
				}
			});
		}
		else {
			getListbyAcnumber.stream().forEach(s->{
				if(s.getTransactionTs().substring(0,10).compareTo(endTimeRange)<=0 && 
						(s.getTransactionTs().substring(0,10).compareTo(startTimeRange)>=0) ) {
					resultList.add(s);
				}
			});
		}
		
		
		
		return resultList;
	}
	
	public List<TransactionInformation> getSortedDetailsByDateDesc(List<TransactionInformation> test){
		
		List<TransactionInformation> updatedList = test.stream()
				  .sorted(Comparator.comparing(TransactionInformation::getTransactionTs).reversed())
				  .collect(Collectors.toList());
		return updatedList;
		
	}
	
	public List<TransactionInformation> getTransactionDetail() {
		List<TransactionInformation> transactionDetail = new ArrayList<TransactionInformation>();
		try {
		ObjectMapper mapper = new ObjectMapper();
		 transactionDetail = mapper.readValue(Paths.get("src/main/resources/Transaction.json").toFile(), 
				 new TypeReference<List<TransactionInformation>>() {});
		}
		catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		
		return transactionDetail;
		
	}
}
