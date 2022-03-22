package com.flightapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.model.AdminLoginDetails;
import com.flightapp.model.Flightapp;

public interface FlightappService {

	ResponseEntity<Object> saveFlightInfo(Flightapp flightapp);
	
	Boolean adminLogin(AdminLoginDetails adminlogin);
	
	ResponseEntity<Object> searchFlight(Flightapp flightapp);

	String saveInventory(Flightapp flightapp);
}
