package com.assignment.Assignment.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.assignment.Assignment.model.ExchangeRateModel;
import com.assignment.Assignment.model.RateModel;

@DataJpaTest
public class ExchangeRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private RateRepository rateRepository;
	
	
	@Test
	public void testFindAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc() throws Exception {
		
		ExchangeRateModel erm = new ExchangeRateModel();
		RateModel em = null;
		List<RateModel> rm = new ArrayList<RateModel>();
		erm.setBase("EUR");
		erm.setDate(java.sql.Date.valueOf("2021-08-01"));
		ExchangeRateModel exId=exchangeRepository.save(erm);
		em = new RateModel();
		em.setCurrency("GBP");
		em.setRate(0.890496);
		em.setExchangeRateModel(exId);
		rm.add(em);
		
		ExchangeRateModel erm1 = exchangeRepository.save(erm);
		assertEquals(erm.getDate(), erm1.getDate());
		
	}
	
	@Test
	void getExchangeRatesBydatesRange() {
		ExchangeRateModel erm = new ExchangeRateModel();
		RateModel em = null;
		List<RateModel> rm = new ArrayList<RateModel>();
		erm.setBase("EUR");
		erm.setDate(java.sql.Date.valueOf("2021-08-01"));
		ExchangeRateModel exId=exchangeRepository.save(erm);
		em = new RateModel();
		em.setCurrency("GBP");
		em.setRate(0.890496);
		em.setExchangeRateModel(exId);
		rm.add(em);
	entityManager.persist(erm);
	List<ExchangeRateModel> exchangeRateList1 = exchangeRepository.findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(java.sql.Date.valueOf("2021-01-01"), java.sql.Date.valueOf("2021-10-01"));
	assertTrue(!exchangeRateList1.isEmpty());
	}
	

	@Test
	void getExchangeRatesByCurrencyAndDate() {
		ExchangeRateModel erm = new ExchangeRateModel();
		RateModel em = null;
		List<RateModel> rm = new ArrayList<RateModel>();
		erm.setBase("EUR");
		erm.setDate(java.sql.Date.valueOf("2021-08-01"));
		ExchangeRateModel exId=exchangeRepository.save(erm);
		em = new RateModel();
		em.setCurrency("GBP");
		em.setRate(0.890496);
		em.setExchangeRateModel(exId);
		rm.add(em);
	entityManager.persist(erm);
	RateModel rateModel1 = rateRepository.findByCurrencyAndExchangeRateModelDate(em.getCurrency(),java.sql.Date.valueOf("2021-10-01"));
	assertEquals(em.getRate(), rateModel1.getRate());
	}
	

}
