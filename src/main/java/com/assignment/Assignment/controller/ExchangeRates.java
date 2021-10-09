package com.assignment.Assignment.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.assignment.Assignment.beans.ExchangeBean;
import com.assignment.Assignment.model.ExchangeRateModel;
import com.assignment.Assignment.model.RateModel;
import com.assignment.Assignment.repository.ExchangeRepository;
import com.assignment.Assignment.repository.RateRepository;

@RestController
public class ExchangeRates {

	private final String GLOBAL_ERROR = "Some technical Error Please try again after some time";

	private static String URL = "http://api.exchangeratesapi.io/v1/";

	private static final String access_key = "3333aeed56ddf356fcf26c83269cd537";

	private static final String base = "EUR";

	@Value("symbols=" + "${api.symbols}")
	private String[] symbols;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private RateRepository rateRepository;

	@Autowired
	private ExchangeRepository exchangeRepository;

	/**
	 * This Rest API end point is for loading exchange 
	 * rate for last 12 months from current month
	 * 
	 * @return
	 */

	@GetMapping("loadExchangeRateLast12Months")
	public String loadExchangeRateLast12Months() {
		try {

			ExchangeBean response = null;
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);

			List<String> allDates = new ArrayList<>();
			String maxDate = year + "-" + month + "-01"; // "2016-01-01";

			System.out.println("maxDate" + maxDate);

			SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.setTime(monthDate.parse(maxDate));
			for (int i = 1; i <= 12; i++) {
				String month_name1 = monthDate.format(cal.getTime());

				System.out.println("month_name1" + month_name1);

				response = restTemplate.getForObject(
						this.URL + month_name1 + "?access_key=" + access_key + "&base=" + base + "&" + symbols,
						ExchangeBean.class);

				System.out.println(month_name1 + ":::::" + response.toString());

				if (response.isSuccess()) {

					saveExchangeRate(response);

				}

				allDates.add(month_name1);
				cal.add(Calendar.MONTH, -1);

			}
			System.out.println(allDates);

			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return GLOBAL_ERROR;
		}
	}

	/**
	 * This rest API is for getting exchange rate of 
	 * all currency for a particular year
	 *  
	 * @param year
	 * @return
	 */

	@GetMapping("getExchangeRateForYear/{year}")
	public String getExchangeRateForYear(@PathVariable("year") String year) {
		try {

			ExchangeBean response = null;
			Calendar c = Calendar.getInstance();
			// int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);

			List<String> allDates = new ArrayList<>();
			String maxDate = year + "-" + "01" + "-01"; // "2016-01-01";

			System.out.println("maxDate" + maxDate);

			SimpleDateFormat monthDate = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.setTime(monthDate.parse(maxDate));
			for (int i = 1; i <= 12; i++) {
				String month_name1 = monthDate.format(cal.getTime());

				System.out.println("month_name1" + month_name1);

				response = restTemplate.getForObject(
						this.URL + month_name1 + "?access_key=" + access_key + "&base=" + base, ExchangeBean.class);

				System.out.println(month_name1 + ":::::" + response.toString());

				if (response.isSuccess()) {

					saveExchangeRate(response);

				}

				allDates.add(month_name1);
				cal.add(Calendar.MONTH, +1);

			}
			System.out.println(allDates);

			return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return GLOBAL_ERROR;
		}
	}

	/**
	 * This Rest API end point is for loading exchange rate for particular date.It's
	 * take one input parameter
	 * 
	 * @param date
	 * @return @ExchangeBean
	 */

	@GetMapping("loadExchangeRateByDate/{date}")
	public String loadExchangeRateSpecficDate(@PathVariable("date") String date) {
		ExchangeBean response = null;
		try {

			System.out.println("URL::" + this.URL + "/" + date + "?" + access_key);

			if (!isValidFormat("yyyy-MM-dd", "2017-18-15", Locale.ENGLISH))
				response = restTemplate.getForObject(this.URL + date + "?access_key=" + access_key, ExchangeBean.class);
			else
				return "Invalid date format";
			System.out.println(response.toString());

			if (response.isSuccess()) {
				saveExchangeRate(response);
			}

			return "SUCCESS"; // response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return GLOBAL_ERROR;
		}
	}

	/**
	 * This API is for getting exchange rate of GBP on particular date
	 * 
	 * @param date
	 * @return
	 */
	@RequestMapping(value = "/getExchangeRateGBPByDate/{date}", method = RequestMethod.GET)
	public ResponseEntity<Object> getExchangeRateGBPByDate(@PathVariable("date") String date1) {
		RateModel rates = null;
		String errorMsg = null;
		java.sql.Date date = null;
		try {
			date = java.sql.Date.valueOf(date1);
		} catch (Exception e1) {
			e1.printStackTrace();
			errorMsg = "Invalid date, required format is YYYY-MM-DD ";
			return new ResponseEntity<Object>(errorMsg, HttpStatus.BAD_REQUEST);
		}
		try {
			rates = rateRepository.findByCurrencyAndExchangeRateModelDate("GBP", date);
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg = "Some Internal Error: " + e.getMessage();
			return new ResponseEntity<Object>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (rates == null) {
			errorMsg = "No Record found";
			return new ResponseEntity<Object>(errorMsg, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Object>(rates, HttpStatus.OK);
	}

	/**
	 * This API is for getting exchange rate for all currency
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */

	@RequestMapping(value = "/getExchangeRateByDateRange/{fromDate}/{toDate}", method = RequestMethod.GET)
	public ResponseEntity<List<ExchangeRateModel>> getAllByDateRange(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate) {

		List<ExchangeRateModel> rates = null;
		java.sql.Date form_date = null;
		java.sql.Date to_date = null;

		try {
			form_date = java.sql.Date.valueOf(fromDate);
			to_date = java.sql.Date.valueOf(toDate);
		} catch (Exception e1) {
			e1.printStackTrace();

		}

		try {
			rates = exchangeRepository.findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(form_date, to_date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (rates == null || rates.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<ExchangeRateModel>>(rates, HttpStatus.OK);
	}

	/**
	 * This method is use to save the exchange bean
	 * 
	 * @param @ExchangeBean
	 */
	public void saveExchangeRate(ExchangeBean exchangeBean) {

		ExchangeRateModel erm = new ExchangeRateModel();

		try {
			erm.setBase(exchangeBean.getBase());
			erm.setDate(java.sql.Date.valueOf(exchangeBean.getDate()));

			ExchangeRateModel save = null;
			try {
				save = exchangeRepository.save(erm);
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<RateModel> rm = new ArrayList<RateModel>();
			Map<String, Double> rates = exchangeBean.getRates();

			RateModel em = null;

			for (Map.Entry<String, Double> entry : rates.entrySet()) {
				em = new RateModel();
				em.setCurrency(entry.getKey());
				em.setRate(entry.getValue());

				try {
					em.setExchangeRateModel(save);
				} catch (Exception e) {
					e.printStackTrace();
				}

				rm.add(em);
				System.out.println("---" + entry.getKey() + ":" + entry.getValue());
			}

			rateRepository.saveAll(rm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * List<RateModel> findAll = (List<RateModel>) rateRepository.findAll();
		 * 
		 * for (RateModel m : findAll) { System.out.println(m.toString()); }
		 */

	}

	/**
	 * This method is for valid given date format in yyyy-MM-dd 
	 * @param format
	 * @param value
	 * @param locale
	 * @return 
	 */
	
	
	public static boolean isValidFormat(String format, String value, Locale locale) {
		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);

		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					// Debugging purposes
					// e2.printStackTrace();
				}
			}
		}

		return false;
	}

}
