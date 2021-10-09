package com.assignment.Assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
@Entity
public class RateModel {

	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ex_id", nullable = false, updatable = false)
	private ExchangeRateModel exchangeRateModel;

	@Column(nullable = false)
	private String currency;

	@Column(nullable = false)
	private Double rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExchangeRateModel getExchangeRateModel() {
		return exchangeRateModel;
	}

	public void setExchangeRateModel(ExchangeRateModel exchangeRateModel) {
		this.exchangeRateModel = exchangeRateModel;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "RateModel [id=" + id + ", exchangeRateModel=" + exchangeRateModel + ", currency=" + currency + ", rate="
				+ rate + "]";
	}

	
}
