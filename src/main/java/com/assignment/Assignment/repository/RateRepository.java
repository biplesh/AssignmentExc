package com.assignment.Assignment.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.Assignment.model.RateModel;

@Repository
public interface RateRepository extends JpaRepository<RateModel, Long>{

	//List<RateModel> findAllByCurrencyAndExchangeRateModelDate(String string, Date date);

	RateModel findByCurrencyAndExchangeRateModelDate(String string, Date date);

}
