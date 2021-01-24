package com.example.service;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.example.model.TransactionInformation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BalanceServiceImpl implements BalanceService{

	
	/*Method for getting the Latest Balance based on account Number Provided*/
	@Override
	public Long getBalanceInformation(String acNumber) {
		
		List<TransactionInformation> test = new ArrayList<TransactionInformation>();
		test = getTransactionDetail();
		List<TransactionInformation> sortedList = getSortedDetailsByDateDesc(test);
		long latestBalance = sortedList.stream().filter(s-> acNumber.equals(s.getAccountNumber())).findFirst().get().getAmount();
		System.out.println("Balance is "+latestBalance);
		return latestBalance;
		
		
	}
	
	/*Method for getting the All Transactions Based on Date Time Range (Start &END) .
		Also given flexibility under same operation if type provided then can see based
		on type (WITHDRAW/DEPOSIT)
	*/
	public List<TransactionInformation> getFullDetails(String acNumber, String startTimeRange, String endTimeRange, String type){
		
		List<TransactionInformation> mapJSONToJAVA = new ArrayList<TransactionInformation>();
		List<TransactionInformation> getListbyType = new ArrayList<TransactionInformation>();
		List<TransactionInformation> resultList = new ArrayList<>();
		
		//Consider a JSON File as source of Data. Converting JSON file to Java Object
		mapJSONToJAVA = getTransactionDetail();
		
		//Sort the JAVA Object in decreasing order of the transaction Date.
		List<TransactionInformation> sortedList = getSortedDetailsByDateDesc(mapJSONToJAVA);
		
		//Get the Transactions Based on the Account Number provided.
		List<TransactionInformation> getListbyAcnumber = sortedList.stream()
				.filter(s->acNumber.equals(s.getAccountNumber())).collect(Collectors.toList()); 
		
		//Get the Transactions by Type. If Type present then IF BLOCK and Empty then ELSE BLOCK.
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
	
	/*
	 * Sorting the transactions Based on the Transactions Date
	 */
	public List<TransactionInformation> getSortedDetailsByDateDesc(List<TransactionInformation> transactions){
		
		List<TransactionInformation> updatedList = transactions.stream()
				  .sorted(Comparator.comparing(TransactionInformation::getTransactionTs).reversed())
				  .collect(Collectors.toList());
		return updatedList;
		
	}
	
	/*
	 * Method for mapping the Source File which is in JSON to JAVA Objects.
	 */
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
