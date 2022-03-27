package com.flightapp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flightapp.model.Flightapp;
import com.flightapp.model.ResponseMessage;


public class FlightppUtility {

	public static List<String> validateFlightapp(Flightapp flightapp) {

		List<String> error = new ArrayList<>();
		
		if (flightapp.getAirline() == null) {
			error.add("AirLine");
		}
		if (flightapp.getFromplace() == null) {
			error.add("From Place");
		}
		if (flightapp.getToplace() == null) {
			error.add("TO Place");
		}
		if (flightapp.getMealType() == null) {
			error.add("Meal Type");
		}

		if (flightapp.getTotalBusinessClassSeats() == null  ) {
			error.add("Business class Seats cann't be Null");
		}else if(flightapp.getTotalBusinessClassSeats() >21  ) {
			error.add("Business class Seats Should  be lessthan 20");
		}
		if(flightapp.getTotalNonBusinessClassSeats() == null ) {
			error.add("Non-Business class Seats");
		}else if(flightapp.getTotalNonBusinessClassSeats() >21  ) {
			error.add("Non-Business class Seats Should  be lessthan 20");
		}
		if(flightapp.getTicketCost() ==  null) {
			error.add("Ticket Cost");
		}

		return error;

	}

	public static List<String> validatesearchFlight(Flightapp flightapp) {
		List<String> error = new ArrayList<>();
		
		if (flightapp.getToplace() == null) {
			error.add("To Place");
		}
		if (flightapp.getFromplace() == null) {
			error.add("From Place");
		}
		if(flightapp.getStartDate() == null) {
			error.add("Start Date");
		}
		return error;
		
	}

	public static ResponseMessage prepareErrorMessage(List<String> errors) {

		String finalMessage = String.join(", ", errors) + " feilds are mandatory";
		return new ResponseMessage(finalMessage, LocalDateTime.now(), HttpStatus.BAD_REQUEST);

	}
	
	public static ResponseEntity<Object> prepareBadRequest(String message){
		return new ResponseEntity<>(new ResponseMessage(message,
				LocalDateTime.now(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

	}
	
	
}
