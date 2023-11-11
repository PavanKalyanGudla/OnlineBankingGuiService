package com.lamar.LamarOnlineBanking.Model;

public class AutoPayApps {
	
	private int autopay_id;
    private String userId;
    private String customer_name;
    private String account_number;
    private String payment_amount;
    private String payment_frequency;
    private String start_date;
    private String account_type;
    private boolean netflix;
    private boolean prime;
    private boolean hulu;
    private boolean appleTv;
    private boolean disneyStar;
    private boolean youtubeTv;
    
	public int getAutopay_id() {
		return autopay_id;
	}
	public void setAutopay_id(int autopay_id) {
		this.autopay_id = autopay_id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}
	public String getPayment_frequency() {
		return payment_frequency;
	}
	public void setPayment_frequency(String payment_frequency) {
		this.payment_frequency = payment_frequency;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public boolean isNetflix() {
		return netflix;
	}
	public void setNetflix(boolean netflix) {
		this.netflix = netflix;
	}
	public boolean isPrime() {
		return prime;
	}
	public void setPrime(boolean prime) {
		this.prime = prime;
	}
	public boolean isHulu() {
		return hulu;
	}
	public void setHulu(boolean hulu) {
		this.hulu = hulu;
	}
	public boolean isAppleTv() {
		return appleTv;
	}
	public void setAppleTv(boolean appleTv) {
		this.appleTv = appleTv;
	}
	public boolean isDisneyStar() {
		return disneyStar;
	}
	public void setDisneyStar(boolean disneyStar) {
		this.disneyStar = disneyStar;
	}
	public boolean isYoutubeTv() {
		return youtubeTv;
	}
	public void setYoutubeTv(boolean youtubeTv) {
		this.youtubeTv = youtubeTv;
	}
    
}
