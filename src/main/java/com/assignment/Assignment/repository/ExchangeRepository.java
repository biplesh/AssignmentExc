package com.assignment.Assignment.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.Assignment.model.ExchangeRateModel;

@Repository
public interface ExchangeRepository extends CrudRepository<ExchangeRateModel, Long> {

	//List<ExchangeRateModel> findAllByDateAndRateModelsCurrency(Date date, String currency);

	List<ExchangeRateModel> findAllByDateGreaterThanEqualAndDateLessThanOrderByDateAsc(Date form_date,Date to_date);
		

		
}
