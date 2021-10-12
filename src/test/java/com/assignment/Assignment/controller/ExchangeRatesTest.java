package com.assignment.Assignment.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.assignment.Assignment.model.ExchangeRateModel;
import com.assignment.Assignment.model.RateModel;
import com.assignment.Assignment.repository.ExchangeRepository;
import com.assignment.Assignment.repository.RateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ExchangeRatesTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	@Autowired
	RestTemplate restTemplate;

	@MockBean
	@Autowired
	private RateRepository rateRepository;

	@MockBean
	@Autowired
	private ExchangeRepository exchangeRepository;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getExchangeRateGBPByDateTest() throws Exception {
		RateModel rates = new RateModel();
		rates.setCurrency("GBP");
		rates.setRate(0.853913);
		rates.setId(1L);

		String startDate = "2021-09-01";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

		Mockito.when(rateRepository.findByCurrencyAndExchangeRateModelDate(Mockito.anyString(), sqlStartDate))
				.thenReturn(rates);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gbp_exchange_rate_by_date/date")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"id\":\"1\",\"currency\":\"GBP\",\"rate\":\"0.890496\"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	void getExchangeRateGBPByDateNoDataFound() throws Exception {

		String startDate = "2021-09-01";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf1.parse(startDate);
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		Mockito.when(rateRepository.findByCurrencyAndExchangeRateModelDate(Mockito.anyString(), sqlStartDate))
				.thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gbp_exchange_rate_by_date/date")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getContentAsString().isEmpty());
	}

	@Test
	void getExchangeRateGBPByDateByInvalidDate() throws Exception {
		Mockito.when(rateRepository.findByCurrencyAndExchangeRateModelDate(null, null)).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gbp_exchange_rate_by_date/date")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getContentAsString().isEmpty());
	}

	@Test
	void getExchangeRateGBPByDateByInvalidDateWithData() throws Exception {
		RateModel rates = new RateModel();
		rates.setCurrency("GBP");
		rates.setRate(0.853913);
		rates.setId(1L);

		Mockito.when(rateRepository.findByCurrencyAndExchangeRateModelDate(rates.getCurrency(), null))
				.thenReturn(rates);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/gbp_exchange_rate_by_date/date")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getContentAsString().isEmpty());
	}

	void getExchangeRatesByDateRange() throws Exception {
		List<ExchangeRateModel> rates = new ArrayList<ExchangeRateModel>();
		List<RateModel> rate = new ArrayList<RateModel>();

		ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
		RateModel rateModel = new RateModel();
		rateModel.setCurrency("AED");
		rateModel.setRate(4.472405);
		rateModel.setId(1L);
		rate.add(rateModel);
		exchangeRateModel.setBase("EUR");
		exchangeRateModel.setDate(java.sql.Date.valueOf("2021-08-01"));
		exchangeRateModel.setEx_id(11L);
		exchangeRateModel.setRateModels(rate);

		java.sql.Date form_date = java.sql.Date.valueOf("2021-01-01");
		java.sql.Date to_date = java.sql.Date.valueOf("2021-08-01");

		Mockito.when(exchangeRepository.findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(form_date, to_date))
				.thenReturn(rates);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all_exchange_rate_by_date_range/fromDate/toDate")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[{\"ex_id\":\"11\",\"base\":\"EUR\",\"date\":\"2021-08-01\",\"rateModels\":{\"id\":\"1\",\"currency\":\"AED\",\"rate\":\"4.472405\"}}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	void getExchangeRatesByDateRangeWithNoData() throws Exception {
		java.sql.Date form_date = java.sql.Date.valueOf("2021-01-01");
		java.sql.Date to_date = java.sql.Date.valueOf("2021-08-01");
		Mockito.when(exchangeRepository.findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(form_date, to_date))
				.thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/all_exchange_rate_by_date_range/fromDate/toDate")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertTrue(result.getResponse().getContentAsString().isEmpty());
	}
	
	

}
