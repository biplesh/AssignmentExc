package com.assignment.Assignment.beans;

import java.util.Map;

import org.springframework.beans.factory.DisposableBean;

public class ExchangeBean implements DisposableBean{


	private boolean success;
	private String timestamp; 
	private String base; 
	private String date; 
	private Map<String, Double> rates;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, Double> getRates() {
		return rates;
	}

	@Override
	public String toString() {
		return "ExchangeBean [success=" + success + ", timestamp=" + timestamp + ", base=" + base + ", date=" + date
				+ ", rates=" + rates + "]";
	}

	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
