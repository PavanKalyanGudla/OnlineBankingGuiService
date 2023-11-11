package com.lamar.LamarOnlineBanking.Model;

import java.sql.Date;

public class Transactions {
	private String userId;
    private int transactionId;
    private String accountNumber;
    private Date transactionDate;
    private String transactionType; //('Deposit', 'Withdrawal', 'Transfer', 'Zelle')
    private double beforeAmount;
    private double amountTransfer;
    private double afterAmount;
    private String description;
    private String sender;
    private String receiver;
    private String TransactionStatus; //('Success', 'Fail', 'Denied')
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(double beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	public double getAmountTransfer() {
		return amountTransfer;
	}
	public void setAmountTransfer(double amountTransfer) {
		this.amountTransfer = amountTransfer;
	}
	public double getAfterAmount() {
		return afterAmount;
	}
	public void setAfterAmount(double afterAmount) {
		this.afterAmount = afterAmount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTransactionStatus() {
		return TransactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		TransactionStatus = transactionStatus;
	}
    
}
