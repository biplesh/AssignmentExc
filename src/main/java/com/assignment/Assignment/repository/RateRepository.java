package com.assignment.Assignment.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.Assignment.model.RateModel;

@Repository
public interface RateRepository extends CrudRepository<RateModel, Long>{

	List<RateModel> findAllByCurrencyAndExchangeRateModelDate(String string, Date date);

	RateModel findByCurrencyAndExchangeRateModelDate(String string, Date date);

}
