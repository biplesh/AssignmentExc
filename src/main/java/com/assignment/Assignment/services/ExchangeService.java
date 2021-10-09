package com.assignment.Assignment.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.Assignment.model.ExchangeRateModel;
import com.assignment.Assignment.model.RateModel;
import com.assignment.Assignment.repository.ExchangeRepository;
import com.assignment.Assignment.repository.RateRepository;

@Service
public class ExchangeService {
	
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private RateRepository rateRepository;
	 
	
	@Transactional
	public void save(ExchangeRateModel exchangeRateModel) {		
		try {			
			exchangeRepository.save(exchangeRateModel);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	
	
	

}
