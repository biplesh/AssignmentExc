package com.assignment.Assignment.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table
@Entity
public class ExchangeRateModel {

	@Id
	@GeneratedValue
	private Long ex_id;

	@Column(nullable = false)
	private String base;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@OneToMany(mappedBy = "exchangeRateModel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RateModel> rateModels;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getEx_id() {
		return ex_id;
	}

	public void setEx_id(Long ex_id) {
		this.ex_id = ex_id;
	}

	public List<RateModel> getRateModels() {
		return rateModels;
	}

	public void setRateModels(List<RateModel> rateModels) {
		this.rateModels = rateModels;
	}

	

}
