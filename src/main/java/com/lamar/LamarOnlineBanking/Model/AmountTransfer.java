package com.lamar.LamarOnlineBanking.Model;

public class AmountTransfer {
	
	private User sender;
	private User receiver;
	private int zelAmount;
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public int getZelAmount() {
		return zelAmount;
	}
	public void setZelAmount(int zelAmount) {
		this.zelAmount = zelAmount;
	}
	
}
