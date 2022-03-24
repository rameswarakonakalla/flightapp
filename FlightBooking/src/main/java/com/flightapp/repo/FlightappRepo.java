package com.flightapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import com.flightapp.model.Flightapp;

@Repository
public interface FlightappRepo extends JpaRepository<Flightapp,Integer> {

	
	List<Flightapp> findByFromplaceAndToplaceAndStartDate(String from , String to , LocalDate startDate);
	
	Flightapp findByFlightNumber(Integer flightNumber);
	
}
