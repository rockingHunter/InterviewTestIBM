package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TransactionInformation;
import com.example.service.BalanceService;

//@RequestMapping("/BalanceInformationService/v1")
@RestController
public class BalanceController {

	@Autowired
	BalanceService balanceService;
	
	//Given an account number. Return the Latest Balance
	@GetMapping(value="/getBalance/{accountNumber}", produces = "application/json")	
	public ResponseEntity<Long> accountNumber(@PathVariable("accountNumber") @NonNull String accountNumber) {
		
		 Long balance = balanceService.getBalanceInformation(accountNumber);
		 if(balance !=null) {
			 return new ResponseEntity<Long>(balance, HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@GetMapping(value="/getBalance/{accountNumber}/{startTimeRange}/{endTimeRange}/{type}", produces = "application/json")	
	public ResponseEntity<List> balanceByTime(@PathVariable("accountNumber") @NonNull String accountNumber,
			@PathVariable("startTimeRange") @NonNull String startTimeRange,@PathVariable("endTimeRange") @NonNull String endTimeRange,
			@PathVariable("type") @NonNull String type)
			{
		
		 List<TransactionInformation> balance = balanceService.getFullDetails(accountNumber,startTimeRange,endTimeRange,type);
		 if(!balance.isEmpty()) {
			 return new ResponseEntity<List>(balance, HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	@GetMapping(value="/healthcheck", produces="application/json")
	//@RequestMapping(method=RequestMethod.POST, value="/healthcheck")
	public HttpStatus healthcheck() {
		
		
		return HttpStatus.OK;
	}
}
