package com.flightapp.service;

import org.springframework.http.ResponseEntity;

import com.flightapp.model.Flightapp;
import com.flightapp.model.FlightappUpdate;

public interface FlightappService {

	ResponseEntity<Object> saveFlightInfo(Flightapp flightapp);

//	Boolean adminLogin(AdminLoginDetails adminlogin);

	ResponseEntity<Object> searchFlight(Flightapp flightapp);

	ResponseEntity<Object> saveInventory(FlightappUpdate flightapp);
}
