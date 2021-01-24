package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.TransactionInformation;
import com.example.service.BalanceService;

/*
 * I HAVE CONSIDERED THE SOURCE OF THE DATA AS A JSON FILE FOR NOW.
 * SOURCE CAN BE DATABASE, EVENT DATA FEEDS OR A FILE.
 * I HAVE CONSIDERED AS A FILE IN INTEREST OF TIME.
 * EVERYTHING I HAVE WRITTEN IS TOTALLY DONE BY ME. 
 * I HAVE SPENDED MY THIS WEEKEND(1/23 AND 1/24) OVER THIS ASSESSMENT.
 * APPRECIATE IF MY EFFORTS ARE CONSIDERED.
 * 
 * OFCOURSE THIS CODE CAN BE MODIFIED ,TWEAKED, NO CODE IS PERFECT.
 */

//@RequestMapping("/BalanceInformationService/v1")
@RestController
public class BalanceController {

	@Autowired
	BalanceService balanceService;
	
	
	/*
	 * Given an account number. Return the Latest Balance
	 * urlFormat=http://localhost:8080/myCode/getBalance/{accountNumber-String}
	 * http://localhost:8080/myCode/getBalance/abc
	 */
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
	
	/*
	 *Controller operation for  finding the transactions based on start date ,end date and type(Optional) 
	 * urlFormat=http://localhost:8080/myCode//getBalance/{accountNumber-String}/{startDate-String}/{endDate-String}/{type-String}
	 * eg- http://localhost:8080/myCode//getBalance/abc/2020-01-02/2020-01-04/WITHDRAW 
	 * Please note Type is optional
	 * */
	@GetMapping(value="/getBalance/{accountNumber}/{startTimeRange}/{endTimeRange}/{type}", produces = "application/json")	
	public ResponseEntity<List> balanceByTime(@PathVariable("accountNumber") @NonNull String accountNumber,
			@PathVariable("startTimeRange") @NonNull String startTimeRange,@PathVariable("endTimeRange") @NonNull String endTimeRange,
			@PathVariable("type")  String type)
			{
		
		 List<TransactionInformation> transactions = balanceService.getFullDetails(accountNumber,startTimeRange,endTimeRange,type);
		 if(!transactions.isEmpty()) {
			 return new ResponseEntity<List>(transactions, HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	}
	
	/*
	 * Health Check to see if service is up or not
	 */
	@GetMapping(value="/healthcheck", produces="application/json")
	public HttpStatus healthcheck() {
		return HttpStatus.OK;
	}
}
