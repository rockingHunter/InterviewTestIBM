package com.example.model;

public class ClientTransactions {

	public String accountNumber;
	public String transactionTs;
	public String type;
	public long amount;
	
	public ClientTransactions() {
		
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getTransactionTs() {
		return transactionTs;
	}
	public void setTransactionTs(String transactionTs) {
		this.transactionTs = transactionTs;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	
}
